package io.orkes.conductor.client.automator;

import com.google.common.base.Stopwatch;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.conductor.client.config.PropertyFactory;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.discovery.EurekaClient;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.api.patterns.ThreadPoolMonitor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;

class TaskRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
    private static final Registry REGISTRY = Spectator.globalRegistry();

    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;
    private final int updateRetryCount;
    private final ThreadPoolExecutor executorService;
    private final Map<String /* taskType */, String /* domain */> taskToDomain;
    private final int threadCount;
    private final int taskPollTimeout;

    private static final String DOMAIN = "domain";
    private static final String OVERRIDE_DISCOVERY = "pollOutOfDiscovery";
    private static final String ALL_WORKERS = "all";

    TaskRunner(
            EurekaClient eurekaClient,
            TaskClient taskClient,
            int updateRetryCount,
            Map<String, String> taskToDomain,
            String workerNamePrefix,
            int threadCount,
            int taskPollTimeout) {
        this.eurekaClient = eurekaClient;
        this.taskClient = taskClient;
        this.updateRetryCount = updateRetryCount;
        this.taskToDomain = taskToDomain;
        this.threadCount = threadCount;
        this.taskPollTimeout = taskPollTimeout;
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                threadCount,
                new BasicThreadFactory.Builder()
                        .namingPattern(workerNamePrefix)
                        .uncaughtExceptionHandler(uncaughtExceptionHandler)
                        .build());
        ThreadPoolMonitor.attach(REGISTRY, (ThreadPoolExecutor) executorService, workerNamePrefix);
        LOGGER.info("Initialized the TaskPollExecutor for {} with {} threads", threadCount);
    }

    public void poll(Worker worker) {
        pollTasksForWorker(worker).forEach(
                task -> this.executorService.submit(
                        () -> this.processTask(task, worker)));
    }

    public void shutdown(int timeout) {
        try {
            this.executorService.shutdown();
            if (executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
                LOGGER.info("tasks completed, shutting down");
            } else {
                LOGGER.warn(String.format("forcing shutdown after waiting for %s second", timeout));
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            LOGGER.warn("shutdown interrupted, invoking shutdownNow");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private List<Task> pollTasksForWorker(Worker worker) {
        List<Task> tasks = new LinkedList<>();
        Boolean discoveryOverride = Optional.ofNullable(
                PropertyFactory.getBoolean(
                        worker.getTaskDefName(), OVERRIDE_DISCOVERY, null))
                .orElseGet(
                        () -> PropertyFactory.getBoolean(
                                ALL_WORKERS, OVERRIDE_DISCOVERY, false));
        if (eurekaClient != null
                && !eurekaClient.getInstanceRemoteStatus().equals(InstanceInfo.InstanceStatus.UP)
                && !discoveryOverride) {
            LOGGER.info("Instance is NOT UP in discovery - will not poll");
            return tasks;
        }
        if (worker.paused()) {
            MetricsContainer.incrementTaskPausedCount(worker.getTaskDefName());
            LOGGER.info("Worker {} has been paused. Not polling anymore!", worker.getClass());
            return tasks;
        }
        String taskType = worker.getTaskDefName();
        try {
            String domain = Optional.ofNullable(PropertyFactory.getString(taskType, DOMAIN, null))
                    .orElseGet(
                            () -> Optional.ofNullable(
                                    PropertyFactory.getString(
                                            ALL_WORKERS, DOMAIN, null))
                                    .orElse(taskToDomain.get(taskType)));
            LOGGER.info("Polling task of type: {} in domain: '{}'", taskType, domain);
            List<Task> polledTasks = MetricsContainer.getPollTimer(taskType)
                    .record(
                            () -> pollTask(
                                    taskType,
                                    worker.getIdentity(),
                                    domain,
                                    this.getAvailableWorkers()));
            for (Task task : polledTasks) {
                if (Objects.nonNull(task) && StringUtils.isNotBlank(task.getTaskId())) {
                    LOGGER.info(
                            "Polled task: {} of type: {} in domain: '{}', from worker: {}",
                            task.getTaskId(),
                            taskType,
                            domain,
                            worker.getIdentity());
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            MetricsContainer.incrementTaskPollErrorCount(worker.getTaskDefName(), e);
            LOGGER.error("Error when polling for tasks", e);
        }
        return tasks;
    }

    private int getAvailableWorkers() {
        return this.threadCount - this.executorService.getActiveCount();
    }

    private List<Task> pollTask(String taskType, String workerId, String domain, int count) {
        if (count < 1) {
            return List.of();
        }
        return taskClient.batchPollTasksByTaskType(taskType, workerId, count, this.taskPollTimeout);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (thread, error) -> {
        // JVM may be in unstable state, try to send metrics then exit
        MetricsContainer.incrementUncaughtExceptionCount();
        LOGGER.error("Uncaught exception. Thread {} will exit now", thread, error);
    };

    private void processTask(Task task, Worker worker) {
        LOGGER.info(
                "Executing task: {} of type: {} in worker: {} at {}",
                task.getTaskId(),
                task.getTaskDefName(),
                worker.getClass().getSimpleName(),
                worker.getIdentity());
        try {
            executeTask(worker, task);
        } catch (Throwable t) {
            task.setStatus(Task.Status.FAILED);
            TaskResult result = new TaskResult(task);
            handleException(t, result, worker, task);
        }
    }

    private void executeTask(Worker worker, Task task) {
        if (task == null || task.getTaskDefName().isEmpty()) {
            LOGGER.info("Empty task");
            return;
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        TaskResult result = null;
        try {
            LOGGER.info(
                    "Executing task: {} in worker: {} at {}",
                    task.getTaskId(),
                    worker.getClass().getSimpleName(),
                    worker.getIdentity());
            result = worker.execute(task);
            result.setWorkflowInstanceId(task.getWorkflowInstanceId());
            result.setTaskId(task.getTaskId());
            result.setWorkerId(worker.getIdentity());
        } catch (Exception e) {
            LOGGER.error(
                    "Unable to execute task: {} of type: {}",
                    task.getTaskId(),
                    task.getTaskDefName(),
                    e);
            MetricsContainer.incrementTaskExecutionErrorCount(task.getTaskType(), e.getCause());
            if (result == null) {
                task.setStatus(Task.Status.FAILED);
                result = new TaskResult(task);
            }
            handleException(e, result, worker, task);
        } finally {
            stopwatch.stop();
            MetricsContainer.getExecutionTimer(worker.getTaskDefName())
                    .record(stopwatch.elapsed(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
        }
        LOGGER.info(
                "Task: {} executed by worker: {} at {} with status: {}",
                task.getTaskId(),
                worker.getClass().getSimpleName(),
                worker.getIdentity(),
                result.getStatus());
        updateTaskResult(updateRetryCount, task, result, worker);
    }

    private void updateTaskResult(int count, Task task, TaskResult result, Worker worker) {
        try {
            // upload if necessary
            Optional<String> optionalExternalStorageLocation = retryOperation(
                    (TaskResult taskResult) -> upload(taskResult, task.getTaskType()),
                    count,
                    result,
                    "evaluateAndUploadLargePayload");

            if (optionalExternalStorageLocation.isPresent()) {
                result.setExternalOutputPayloadStoragePath(optionalExternalStorageLocation.get());
                result.setOutputData(null);
            }

            retryOperation(
                    (TaskResult taskResult) -> {
                        taskClient.updateTask(taskResult);
                        return null;
                    },
                    count,
                    result,
                    "updateTask");
        } catch (Exception e) {
            worker.onErrorUpdate(task);
            MetricsContainer.incrementTaskUpdateErrorCount(worker.getTaskDefName(), e);
            LOGGER.error(
                    String.format(
                            "Failed to update result: %s for task: %s in worker: %s",
                            result.toString(), task.getTaskDefName(), worker.getIdentity()),
                    e);
        }
    }

    private Optional<String> upload(TaskResult result, String taskType) {
        try {
            return taskClient.evaluateAndUploadLargePayload(result.getOutputData(), taskType);
        } catch (IllegalArgumentException iae) {
            result.setReasonForIncompletion(iae.getMessage());
            result.setOutputData(null);
            result.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
            return Optional.empty();
        }
    }

    private <T, R> R retryOperation(Function<T, R> operation, int count, T input, String opName) {
        int index = 0;
        while (index < count) {
            try {
                return operation.apply(input);
            } catch (Exception e) {
                index++;
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ie) {
                    LOGGER.error("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("Exhausted retries performing " + opName);
    }

    private void handleException(Throwable t, TaskResult result, Worker worker, Task task) {
        LOGGER.error(String.format("Error while executing task %s", task.toString()), t);
        MetricsContainer.incrementTaskExecutionErrorCount(worker.getTaskDefName(), t);
        result.setStatus(TaskResult.Status.FAILED);
        result.setReasonForIncompletion("Error while executing the task: " + t);
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        result.log(stringWriter.toString());
        updateTaskResult(updateRetryCount, task, result, worker);
    }
}
