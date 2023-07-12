/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.automator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.conductor.client.config.PropertyFactory;
import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.discovery.EurekaClient;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.api.patterns.ThreadPoolMonitor;

import io.orkes.conductor.client.TaskClient;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;

class TaskRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
    private static final Registry REGISTRY = Spectator.globalRegistry();
    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;
    private final int updateRetryCount;
    private final ThreadPoolExecutor executorService;
    private final Map<String /* taskType */, String /* domain */> taskToDomain;
    private final int taskPollTimeout;
    public static final String DOMAIN = "domain";
    private static final String OVERRIDE_DISCOVERY = "pollOutOfDiscovery";
    public static final String ALL_WORKERS = "all";

    private final Semaphore permits;

    private final Worker worker;

    private int pollingIntervalInMillis;

    private String domain;

    private final String taskType;

    private int errorAt;

    private int pollingErrorCount = 0;

    TaskRunner(
            Worker worker,
            EurekaClient eurekaClient,
            TaskClient taskClient,
            int updateRetryCount,
            Map<String, String> taskToDomain,
            String workerNamePrefix,
            int threadCount,
            int taskPollTimeout) {
        this.worker = worker;
        this.eurekaClient = eurekaClient;
        this.taskClient = taskClient;
        this.updateRetryCount = updateRetryCount;
        this.taskToDomain = taskToDomain;
        this.taskPollTimeout = taskPollTimeout;
        this.permits = new Semaphore(threadCount);
        this.pollingIntervalInMillis = worker.getPollingInterval();
        this.taskType = worker.getTaskDefName();

        //1. Is there a worker level override?
        this.domain = PropertyFactory.getString(taskType, DOMAIN, null);
        if(this.domain == null) {
            //2. If not, is there a blanket override?
            this.domain = PropertyFactory.getString(ALL_WORKERS, DOMAIN, null);
        }
        if(this.domain == null) {
            //3. was it supplied as part of the config?
            this.domain = taskToDomain.get(taskType);
        }

        int defaultLoggingInterval = 100;
        int errorInterval = PropertyFactory.getInteger(taskType, "LOG_INTERVAL", 0);
        if(errorInterval == 0) {
            errorInterval = PropertyFactory.getInteger(ALL_WORKERS, "LOG_INTERVAL", 0);
        }
        if(errorInterval == 0) {
            errorInterval = defaultLoggingInterval;
        }
        this.errorAt = errorInterval;
        LOGGER.info("Polling errors will be sampled at every {} error (after the first 100 errors) for taskType {}", this.errorAt, taskType);
        this.executorService =
                (ThreadPoolExecutor)
                        Executors.newFixedThreadPool(
                                threadCount,
                                new BasicThreadFactory.Builder()
                                        .namingPattern(workerNamePrefix)
                                        .uncaughtExceptionHandler(uncaughtExceptionHandler)
                                        .build());
        ThreadPoolMonitor.attach(REGISTRY, (ThreadPoolExecutor) executorService, workerNamePrefix);
        LOGGER.info(
                "Starting Worker for taskType '{}' with {} threads, {} ms polling interval and domain {}",
                taskType,
                threadCount,
                pollingIntervalInMillis,
                domain);
        LOGGER.info("Polling errors for taskType {} will be printed at every {} occurance.", taskType, errorAt);

    }

    public void pollAndExecute() {
        Stopwatch stopwatch = null;
        while (true) {
            try {
                List<Task> tasks = pollTasksForWorker();
                if (tasks.isEmpty()) {
                    if (stopwatch == null) {
                        stopwatch = Stopwatch.createStarted();
                    }
                    Uninterruptibles.sleepUninterruptibly(pollingIntervalInMillis, TimeUnit.MILLISECONDS);
                    continue;
                }
                if (stopwatch != null) {
                    stopwatch.stop();
                    LOGGER.trace("Poller for task {} waited for {} ms before getting {} tasks to execute", taskType, stopwatch.elapsed(TimeUnit.MILLISECONDS), tasks.size());
                    stopwatch = null;
                }
                tasks.forEach(task -> this.executorService.submit(() -> this.processTask(task)));
            } catch (Throwable t) {
                LOGGER.error(t.getMessage(), t);
            }
        }
    }

    public void shutdown(int timeout) {
        try {
            this.executorService.shutdown();
            if (executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
                LOGGER.debug("tasks completed, shutting down");
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

    private List<Task> pollTasksForWorker() {
        List<Task> tasks = new LinkedList<>();

        Boolean discoveryOverride =
                Optional.ofNullable(
                                PropertyFactory.getBoolean(
                                        taskType, OVERRIDE_DISCOVERY, null))
                        .orElseGet(
                                () ->
                                        PropertyFactory.getBoolean(
                                                ALL_WORKERS, OVERRIDE_DISCOVERY, false));
        if (eurekaClient != null
                && !eurekaClient.getInstanceRemoteStatus().equals(InstanceInfo.InstanceStatus.UP)
                && !discoveryOverride) {
            LOGGER.trace("Instance is NOT UP in discovery - will not poll");
            return tasks;
        }
        if (worker.paused()) {
            MetricsContainer.incrementTaskPausedCount(taskType);
            LOGGER.trace("Worker {} has been paused. Not polling anymore!", worker.getClass());
            return tasks;
        }
        int pollCount = 0;
        while(permits.tryAcquire()){
            pollCount++;
        }
        if(pollCount == 0) {
            return tasks;
        }

        try {


            LOGGER.trace("Polling task of type: {} in domain: '{}' with size {}", taskType, domain, pollCount);
            Stopwatch stopwatch = Stopwatch.createStarted();
            int tasksToPoll = pollCount;
            tasks = MetricsContainer.getPollTimer(taskType).record(() -> pollTask(domain, tasksToPoll));
            stopwatch.stop();
            permits.release(pollCount - tasks.size());        //release extra permits
            LOGGER.debug("Time taken to poll {} task with a batch size of {} is {} ms", taskType, tasks.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));

        }  catch (Throwable e) {
            permits.release(pollCount - tasks.size());

            //For the first 100 errors, just print them as is...
            boolean printError = false;
            if(pollingErrorCount < 100 || pollingErrorCount % errorAt == 0) {
                printError = true;
            }
            pollingErrorCount++;
            if(pollingErrorCount > 10_000_000) {
                //Reset after 10 million errors
                pollingErrorCount = 0;
            }
            if(printError) {
                LOGGER.error("Error polling for taskType: {}, error = {}", taskType, e.getMessage(), e);
            }
        }
        return tasks;
    }

    private List<Task> pollTask(String domain, int count) {
        if (count < 1) {
            return Collections.emptyList();
        }
        String workerId = worker.getIdentity();
        LOGGER.debug("poll {} in the domain {} with batch size {}", taskType, domain, count);
        return taskClient.batchPollTasksInDomain(
                taskType, domain, workerId, count, this.taskPollTimeout);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
            (thread, error) -> {
                // JVM may be in unstable state, try to send metrics then exit
                MetricsContainer.incrementUncaughtExceptionCount();
                LOGGER.error("Uncaught exception. Thread {} will exit now", thread, error);
            };

    private void processTask(Task task) {
        LOGGER.trace("Executing task: {} of type: {} in worker: {} at {}", task.getTaskId(), taskType, worker.getClass().getSimpleName(), worker.getIdentity());
        LOGGER.trace("task {} is getting executed after {} ms of getting polled", task.getTaskId(), (System.currentTimeMillis()-task.getStartTime()));
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            executeTask(worker, task);
            stopwatch.stop();
            LOGGER.trace(
                    "Took {} ms to execute and update task with id {}",
                    stopwatch.elapsed(TimeUnit.MILLISECONDS),
                    task.getTaskId());
        } catch (Throwable t) {
            task.setStatus(Task.Status.FAILED);
            TaskResult result = new TaskResult(task);
            handleException(t, result, worker, task);
        } finally {
            permits.release();
        }
    }

    private void executeTask(Worker worker, Task task) {
        if (task == null || task.getTaskDefName().isEmpty()) {
            LOGGER.warn("Empty task {}", worker.getTaskDefName());
            return;
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        TaskResult result = null;
        try {
            LOGGER.trace(
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
            MetricsContainer.incrementTaskExecutionErrorCount(task.getTaskType(), e);
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
        LOGGER.trace(
                "Task: {} executed by worker: {} at {} with status: {}",
                task.getTaskId(),
                worker.getClass().getSimpleName(),
                worker.getIdentity(),
                result.getStatus());
        Stopwatch updateStopWatch = Stopwatch.createStarted();
        updateTaskResult(updateRetryCount, task, result, worker);
        updateStopWatch.stop();
        LOGGER.trace(
                "Time taken to update the {} {} ms",
                task.getTaskType(),
                updateStopWatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private void updateTaskResult(int count, Task task, TaskResult result, Worker worker) {
        try {
            // upload if necessary
            Optional<String> optionalExternalStorageLocation =
                    retryOperation(
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
        // do nothing
        return Optional.empty();
    }

    private <T, R> R retryOperation(Function<T, R> operation, int count, T input, String opName) {
        int index = 0;
        while (index < count) {
            try {
                return operation.apply(input);
            } catch (Exception e) {
                LOGGER.error("Error executing {}", opName, e);
                index++;
                Uninterruptibles.sleepUninterruptibly(500L * (count+1), TimeUnit.MILLISECONDS);
            }
        }
        throw new RuntimeException("Exhausted retries performing " + opName);
    }

    private void handleException(Throwable t, TaskResult result, Worker worker, Task task) {
        LOGGER.error(String.format("Error while executing task %s", task.toString()), t);
        MetricsContainer.incrementTaskExecutionErrorCount(taskType, t);
        result.setStatus(TaskResult.Status.FAILED);
        result.setReasonForIncompletion("Error while executing the task: " + t);
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        result.log(stringWriter.toString());
        updateTaskResult(updateRetryCount, task, result, worker);
    }
}
