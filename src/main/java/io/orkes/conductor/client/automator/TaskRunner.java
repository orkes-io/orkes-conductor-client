package io.orkes.conductor.client.automator;

import com.google.common.base.Stopwatch;
import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.api.patterns.ThreadPoolMonitor;

import io.orkes.conductor.client.http.api.TaskResourceApi;
import io.orkes.conductor.client.http.model.Task;
import io.orkes.conductor.client.http.model.TaskExecLog;
import io.orkes.conductor.client.http.model.TaskResult;
import io.orkes.conductor.client.worker.Worker;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;

class TaskRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
    private static final Registry REGISTRY = Spectator.globalRegistry();

    private final TaskResourceApi taskClient;
    private final int updateRetryCount;
    private final ThreadPoolExecutor executorService;
    private final int taskPollTimeout;
    private final Worker worker;

    TaskRunner(TaskResourceApi taskClient,
            int updateRetryCount,
            String workerNamePrefix,
            int taskPollTimeout,
            Worker worker) {
        this.taskClient = taskClient;
        this.updateRetryCount = updateRetryCount;
        this.taskPollTimeout = taskPollTimeout;
        this.worker = worker;
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                worker.getBatchSize(),
                new BasicThreadFactory.Builder()
                        .namingPattern(workerNamePrefix)
                        .uncaughtExceptionHandler(uncaughtExceptionHandler)
                        .build());
        ThreadPoolMonitor.attach(REGISTRY, this.executorService, workerNamePrefix);
        LOGGER.info(
                "Initialized the TaskRunner for {} with {} threads",
                worker.getTaskDefName(),
                worker.getBatchSize());
    }

    public void run() {
        for (Task task : pollTasks()) {
            this.executorService.submit(() -> this.processTask(task));
        }
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    private List<Task> pollTasks() {
        List<Task> tasks = new LinkedList<>();
        if (worker.paused()) {
            MetricsContainer.incrementTaskPausedCount(worker.getTaskDefName());
            LOGGER.info("Worker {} has been paused. Not polling anymore!", worker.getClass());
            return tasks;
        }
        String taskType = worker.getTaskDefName();
        try {
            String domain = worker.getDomain();
            LOGGER.info("Polling task of type: {} in domain: '{}'", taskType, domain);
            List<Task> polledTasks = MetricsContainer.getPollTimer(taskType)
                    .record(
                            () -> pollTask(
                                    taskType,
                                    worker.getIdentity(),
                                    domain,
                                    this.getAvailableWorkers(taskType)));
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

    private int getAvailableWorkers(String taskType) {
        return this.executorService.getMaximumPoolSize() - this.executorService.getActiveCount();
    }

    private List<Task> pollTask(String taskType, String workerId, String domain, int count) throws Exception {
        if (count < 1) {
            return List.of();
        }
        return taskClient.batchPoll(
                taskType,
                workerId,
                worker.getDomain(),
                count,
                this.taskPollTimeout);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (thread, error) -> {
        // JVM may be in unstable state, try to send metrics then exit
        MetricsContainer.incrementUncaughtExceptionCount();
        LOGGER.error("Uncaught exception. Thread {} will exit now", thread, error);
    };

    private void processTask(Task task) {
        LOGGER.info(
                "Executing task: {} of type: {} in worker: {} at {}",
                task.getTaskId(),
                task.getTaskDefName(),
                worker.getClass().getSimpleName(),
                worker.getIdentity());
        try {
            executeTask(task);
        } catch (Throwable t) {
            task.setStatus(Task.StatusEnum.FAILED);
            TaskResult result = new TaskResult(task);
            handleException(t, result, task);
        }
    }

    private void executeTask(Task task) {
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
                task.setStatus(Task.StatusEnum.FAILED);
                result = new TaskResult(task);
            }
            handleException(e, result, task);
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
        updateTaskResult(updateRetryCount, task, result);
    }

    private void updateTaskResult(int count, Task task, TaskResult result) {
        try {
            retryOperation(
                    (TaskResult taskResult) -> {
                        try {
                            taskClient.updateTask(taskResult);
                        } catch (Exception e) {
                            return taskResult;
                        }
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

    private void handleException(Throwable t, TaskResult result, Task task) {
        LOGGER.error(String.format("Error while executing task %s", task.toString()), t);
        MetricsContainer.incrementTaskExecutionErrorCount(worker.getTaskDefName(), t);
        result.setStatus(TaskResult.StatusEnum.FAILED);
        result.setReasonForIncompletion("Error while executing the task: " + t);
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        result.addLogsItem(new TaskExecLog().log(stringWriter.toString()));
        updateTaskResult(updateRetryCount, task, result);
    }
}
