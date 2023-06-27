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
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.config.PropertyFactory;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.discovery.EurekaClient;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.grpc.PooledPoller;
import io.orkes.conductor.client.http.OrkesTaskClient;

import com.google.common.base.Preconditions;

import static io.orkes.conductor.client.automator.TaskRunner.ALL_WORKERS;
import static io.orkes.conductor.client.automator.TaskRunner.DOMAIN;

public class TaskRunnerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);

    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;

    private final ApiClient apiClient;
    private final List<Worker> workers;
    private final int sleepWhenRetry;
    private final int updateRetryCount;
    private final int shutdownGracePeriodSeconds;
    private final String workerNamePrefix;
    private final Map<String /* taskType */, String /* domain */> taskToDomain;
    private final Map<String /* taskType */, Integer /* threadCount */> taskToThreadCount;
    private final Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskPollTimeout;

    private final Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskPollCount;

    private Integer defaultPollTimeout;
    private Integer defaultPollCount;
    private final int threadCount;

    private final List<TaskRunner> taskRunners;

    private ScheduledExecutorService scheduledExecutorService;

    /**
     * @see TaskRunnerConfigurer.Builder
     * @see TaskRunnerConfigurer#init()
     */
    private TaskRunnerConfigurer(TaskRunnerConfigurer.Builder builder) {
        this.eurekaClient = builder.eurekaClient;
        this.taskClient = builder.taskClient;
        this.apiClient = ((OrkesTaskClient) builder.taskClient).getApiClient();
        this.sleepWhenRetry = builder.sleepWhenRetry;
        this.updateRetryCount = builder.updateRetryCount;
        this.workerNamePrefix = builder.workerNamePrefix;
        this.taskToDomain = builder.taskToDomain;
        this.taskToThreadCount = builder.taskToThreadCount;
        this.taskPollTimeout = builder.taskPollTimeout;
        this.taskPollCount = builder.taskPollCount;
        this.defaultPollTimeout = builder.defaultPollTimeout;
        this.defaultPollCount = builder.defaultPollCount;
        this.shutdownGracePeriodSeconds = builder.shutdownGracePeriodSeconds;
        this.workers = new LinkedList<>();
        this.threadCount = builder.threadCount;
        builder.workers.forEach(this.workers::add);
        taskRunners = new LinkedList<>();
    }

    /** Builder used to create the instances of TaskRunnerConfigurer */
    public static class Builder {
        private String workerNamePrefix = "workflow-worker-%d";
        private int sleepWhenRetry = 500;
        private int updateRetryCount = 3;
        private int threadCount = -1;
        private int shutdownGracePeriodSeconds = 10;
        private int defaultPollTimeout = 100;

        private int defaultPollCount = 20;
        private final Iterable<Worker> workers;
        private EurekaClient eurekaClient;
        private final TaskClient taskClient;
        private Map<String /* taskType */, String /* domain */> taskToDomain = new HashMap<>();
        private Map<String /* taskType */, Integer /* threadCount */> taskToThreadCount =
                new HashMap<>();
        private Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskPollTimeout =  new HashMap<>();

        private Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskPollCount = new HashMap<>();

        public Builder(TaskClient taskClient, Iterable<Worker> workers) {
            Preconditions.checkNotNull(taskClient, "TaskClient cannot be null");
            Preconditions.checkNotNull(workers, "Workers cannot be null");
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
         * @param conductorClientConfiguration client configuration to handle external payloads
         * @return Builder instance
         */
        public TaskRunnerConfigurer.Builder withConductorClientConfiguration(
                ConductorClientConfiguration conductorClientConfiguration) {
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
                Map<String, Integer> taskToThreadCount) {
            this.taskToThreadCount = taskToThreadCount;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskToThreadCount(
                Map<String, Integer> taskToThreadCount) {
            this.taskToThreadCount = taskToThreadCount;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskPollTimeout(
                Map<String, Integer> taskPollTimeout) {
            this.taskPollTimeout = taskPollTimeout;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskPollTimeout(Integer taskPollTimeout) {
            this.defaultPollTimeout = taskPollTimeout;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskPollCount(Map<String, Integer> taskPollCount) {
            this.taskPollCount = taskPollCount;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskPollCount(int defaultPollCount) {
            this.defaultPollCount = defaultPollCount;
            return this;
        }

        /**
         * Builds an instance of the TaskRunnerConfigurer.
         *
         * <p>Please see {@link TaskRunnerConfigurer#init()} method. The method must be called after
         * this constructor for the polling to start.
         *
         * @return Builder instance
         */
        public TaskRunnerConfigurer build() {
            return new TaskRunnerConfigurer(this);
        }

        /**
         * @param threadCount # of threads assigned to the workers. Should be at-least the size of
         *     taskWorkers to avoid starvation in a busy system.
         * @return Builder instance
         */
        public Builder withThreadCount(int threadCount) {
            if (threadCount < 1) {
                throw new IllegalArgumentException("No. of threads cannot be less than 1");
            }
            this.threadCount = threadCount;
            return this;
        }
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

    /**
     * Starts the polling. Must be called after {@link TaskRunnerConfigurer.Builder#build()} method.
     */
    public synchronized void init() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(workers.size());
        if (apiClient.isUseGRPC()) {
            LOGGER.info("Using gRPC for task poll/update for ", workers.stream().map(worker -> worker.getTaskDefName()).collect(Collectors.toList()));
            workers.forEach(worker -> scheduledExecutorService.submit(() -> this.startPooledGRPCWorker(worker)));
        } else {
            workers.forEach(worker -> scheduledExecutorService.submit(() -> this.startWorker(worker)));
        }
    }

    /**
     * Invoke this method within a PreDestroy block within your application to facilitate a graceful
     * shutdown of your worker, during process termination.
     */
    public void shutdown() {
        this.taskRunners.forEach(taskRunner -> taskRunner.shutdown(shutdownGracePeriodSeconds));
        this.scheduledExecutorService.shutdown();
    }

    private void startWorker(Worker worker) {
        final Integer threadCountForTask =
                this.taskToThreadCount.getOrDefault(worker.getTaskDefName(), threadCount);
        final Integer taskPollTimeout =
                this.taskPollTimeout.getOrDefault(worker.getTaskDefName(), defaultPollTimeout);
        LOGGER.info("Domain map for tasks = {}", taskToDomain);
        final TaskRunner taskRunner =
                new TaskRunner(
                        worker,
                        eurekaClient,
                        taskClient,
                        updateRetryCount,
                        taskToDomain,
                        workerNamePrefix,
                        threadCountForTask,
                        taskPollTimeout);
        this.taskRunners.add(taskRunner);
        taskRunner.pollAndExecute();
    }

    private void startPooledGRPCWorker(Worker worker) {

        final Integer threadCountForTask = this.taskToThreadCount.getOrDefault(worker.getTaskDefName(), threadCount);
        final Integer taskPollTimeout = this.taskPollTimeout.getOrDefault(worker.getTaskDefName(), defaultPollTimeout);
        String taskType = worker.getTaskDefName();
        String domain =
                Optional.ofNullable(PropertyFactory.getString(taskType, DOMAIN, null))
                        .orElseGet(() -> Optional.ofNullable(PropertyFactory.getString(ALL_WORKERS, DOMAIN, null)).orElse(taskToDomain.get(taskType)));
        LOGGER.info("Starting gRPC worker: {} with {} threads", worker.getTaskDefName(), threadCountForTask);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadCountForTask, threadCountForTask, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(threadCountForTask * 100));
        PooledPoller pooledPoller = new PooledPoller(apiClient, worker, domain, threadCountForTask, taskPollTimeout, executor, threadCountForTask);
        pooledPoller.start();
    }
}
