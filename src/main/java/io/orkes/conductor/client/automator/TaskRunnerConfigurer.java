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

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.exception.ConductorClientException;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.discovery.EurekaClient;

import io.orkes.conductor.client.TaskClient;

public class TaskRunnerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);
    private static final String INVALID_THREAD_COUNT =
            "Invalid worker thread count specified, use either shared thread pool or config thread count per task";
    private static final String MISSING_TASK_THREAD_COUNT =
            "Missing task thread count config for %s";

    ScheduledExecutorService scheduledExecutorService;

    private final EurekaClient eurekaClient;
    final TaskClient taskClient;
    final List<Worker> workers = new LinkedList<>();
    private final int sleepWhenRetry;
    private final int updateRetryCount;
    private final int threadCount;
    private final int shutdownGracePeriodSeconds;
    private final String workerNamePrefix;
    private final Map<String /*taskType*/, String /*domain*/> taskToDomain;
    private final Map<String /*taskType*/, Integer /*threadCount*/> taskThreadCount;

    /**
     * @see TaskRunnerConfigurer.Builder
     * @see TaskRunnerConfigurer#init()
     */
    TaskRunnerConfigurer(TaskRunnerConfigurer.Builder builder) {
        // only allow either shared thread pool or per task thread pool
        if (builder.threadCount != -1 && !builder.taskThreadCount.isEmpty()) {
            LOGGER.error(INVALID_THREAD_COUNT);
            throw new ConductorClientException(INVALID_THREAD_COUNT);
        } else if (!builder.taskThreadCount.isEmpty()) {
            for (Worker worker : builder.workers) {
                if (!builder.taskThreadCount.containsKey(worker.getTaskDefName())) {
                    String message =
                            String.format(MISSING_TASK_THREAD_COUNT, worker.getTaskDefName());
                    LOGGER.error(message);
                    throw new ConductorClientException(message);
                }
                workers.add(worker);
            }
            this.taskThreadCount = builder.taskThreadCount;
            this.threadCount = -1;
        } else {
            builder.workers.forEach(workers::add);
            this.taskThreadCount = builder.taskThreadCount;
            this.threadCount = (builder.threadCount == -1) ? workers.size() : builder.threadCount;
        }

        this.eurekaClient = builder.eurekaClient;
        this.taskClient = builder.taskClient;
        this.sleepWhenRetry = builder.sleepWhenRetry;
        this.updateRetryCount = builder.updateRetryCount;
        this.workerNamePrefix = builder.workerNamePrefix;
        this.taskToDomain = builder.taskToDomain;
        this.shutdownGracePeriodSeconds = builder.shutdownGracePeriodSeconds;
    }

    /** Builder used to create the instances of TaskRunnerConfigurer */
    public static class Builder {

        private String workerNamePrefix = "workflow-worker-%d";
        private int sleepWhenRetry = 500;
        private int updateRetryCount = 3;
        private int threadCount = -1;
        private int shutdownGracePeriodSeconds = 10;
        private final Iterable<Worker> workers;
        private EurekaClient eurekaClient;
        private final TaskClient taskClient;
        private Map<String /*taskType*/, String /*domain*/> taskToDomain = new HashMap<>();
        private Map<String /*taskType*/, Integer /*threadCount*/> taskThreadCount = new HashMap<>();

        public Builder(TaskClient taskClient, Iterable<Worker> workers) {
            Validate.notNull(taskClient, "TaskClient cannot be null");
            Validate.notNull(workers, "Workers cannot be null");
            this.taskClient = taskClient;
            this.workers = workers;
        }

        /**
         * @param workerNamePrefix prefix to be used for worker names, defaults to workflow-worker-
         *     if not supplied.
         * @return Returns the current instance.
         */
        public TaskRunnerConfigurer.Builder withWorkerNamePrefix(String workerNamePrefix) {
            this.workerNamePrefix = workerNamePrefix;
            return this;
        }

        /**
         * @param sleepWhenRetry time in milliseconds, for which the thread should sleep when task
         *     update call fails, before retrying the operation.
         * @return Returns the current instance.
         */
        public TaskRunnerConfigurer.Builder withSleepWhenRetry(int sleepWhenRetry) {
            this.sleepWhenRetry = sleepWhenRetry;
            return this;
        }

        /**
         * @param updateRetryCount number of times to retry the failed updateTask operation
         * @return Builder instance
         * @see #withSleepWhenRetry(int)
         */
        public TaskRunnerConfigurer.Builder withUpdateRetryCount(int updateRetryCount) {
            this.updateRetryCount = updateRetryCount;
            return this;
        }

        /**
         * @param threadCount # of threads assigned to the workers. Should be at-least the size of
         *     taskWorkers to avoid starvation in a busy system.
         * @return Builder instance
         */
        public TaskRunnerConfigurer.Builder withThreadCount(int threadCount) {
            if (threadCount < 1) {
                throw new IllegalArgumentException("No. of threads cannot be less than 1");
            }
            this.threadCount = threadCount;
            return this;
        }

        /**
         * @param shutdownGracePeriodSeconds waiting seconds before forcing shutdown of your worker
         * @return Builder instance
         */
        public TaskRunnerConfigurer.Builder withShutdownGracePeriodSeconds(
                int shutdownGracePeriodSeconds) {
            if (shutdownGracePeriodSeconds < 1) {
                throw new IllegalArgumentException(
                        "Seconds of shutdownGracePeriod cannot be less than 1");
            }
            this.shutdownGracePeriodSeconds = shutdownGracePeriodSeconds;
            return this;
        }

        /**
         * @param eurekaClient Eureka client - used to identify if the server is in discovery or
         *     not. When the server goes out of discovery, the polling is terminated. If passed
         *     null, discovery check is not done.
         * @return Builder instance
         */
        public TaskRunnerConfigurer.Builder withEurekaClient(EurekaClient eurekaClient) {
            this.eurekaClient = eurekaClient;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskToDomain(Map<String, String> taskToDomain) {
            this.taskToDomain = taskToDomain;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskThreadCount(
                Map<String, Integer> taskThreadCount) {
            this.taskThreadCount = taskThreadCount;
            return this;
        }

        /**
         * Builds an instance of the TaskRunnerConfigurer.
         *
         * <p>Please see {@link TaskRunnerConfigurer#init()} method. The method must be called after
         * this constructor for the polling to start.
         */
        public TaskRunnerConfigurer build() {
            return new TaskRunnerConfigurer(this);
        }
    }

    /**
     * @return Thread Count for the shared executor pool
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * @return Thread Count for individual task type
     */
    public Map<String, Integer> getTaskThreadCount() {
        return taskThreadCount;
    }

    /**
     * @return seconds before forcing shutdown of worker
     */
    public int getShutdownGracePeriodSeconds() {
        return shutdownGracePeriodSeconds;
    }

    /**
     * @return sleep time in millisecond before task update retry is done when receiving error from
     *     the Conductor server
     */
    public int getSleepWhenRetry() {
        return sleepWhenRetry;
    }

    /**
     * @return Number of times updateTask should be retried when receiving error from Conductor
     *     server
     */
    public int getUpdateRetryCount() {
        return updateRetryCount;
    }

    /**
     * @return prefix used for worker names
     */
    public String getWorkerNamePrefix() {
        return workerNamePrefix;
    }

    private List<TaskPollExecutor> taskPollExecutors = new ArrayList<>();

    /**
     * Starts the polling. Must be called after {@link TaskRunnerConfigurer.Builder#build()} method.
     */
    public synchronized void init() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(workers.size());
        workers.forEach(
                worker -> {
                    Integer workerThreadCount = this.taskThreadCount.get(worker.getTaskDefName());
                    if (workerThreadCount == null) {
                        workerThreadCount = this.threadCount;
                    }

                    TaskPollExecutor taskPollExecutor =
                            new TaskPollExecutor(
                                    worker,
                                    eurekaClient,
                                    taskClient,
                                    workerThreadCount,
                                    updateRetryCount,
                                    taskToDomain,
                                    workerNamePrefix);

                    taskPollExecutors.add(taskPollExecutor);

                    scheduledExecutorService.scheduleWithFixedDelay(
                            () -> taskPollExecutor.pollAndExecute(),
                            worker.getPollingInterval(),
                            worker.getPollingInterval(),
                            TimeUnit.MILLISECONDS);
                });
    }

    /**
     * Invoke this method within a PreDestroy block within your application to facilitate a graceful
     * shutdown of your worker, during process termination.
     */
    public void shutdown() {
        taskPollExecutors.forEach(
                taskPollExecutor ->
                        taskPollExecutor.shutdownExecutorService(
                                scheduledExecutorService, shutdownGracePeriodSeconds));
    }
}
