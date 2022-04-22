package io.orkes.conductor.client.automator;

import com.google.common.base.Preconditions;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.discovery.EurekaClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskRunnerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);

    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;
    private final List<Worker> workers;
    private final int sleepWhenRetry;
    private final int updateRetryCount;
    private final int shutdownGracePeriodSeconds;
    private final String workerNamePrefix;
    private final Map<String /* taskType */, String /* domain */> taskToDomain;
    private final Map<String /* taskType */, Integer /* threadCount */> taskToThreadCount;
    private final Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskToTimeout;
    private final List<TaskRunner> taskRunners;

    private ScheduledExecutorService scheduledExecutorService;

    /**
     * @see TaskRunnerConfigurer.Builder
     * @see TaskRunnerConfigurer#init()
     */
    private TaskRunnerConfigurer(TaskRunnerConfigurer.Builder builder) {
        this.eurekaClient = builder.eurekaClient;
        this.taskClient = builder.taskClient;
        this.sleepWhenRetry = builder.sleepWhenRetry;
        this.updateRetryCount = builder.updateRetryCount;
        this.workerNamePrefix = builder.workerNamePrefix;
        this.taskToDomain = builder.taskToDomain;
        this.taskToThreadCount = builder.taskToThreadCount;
        this.taskToTimeout = builder.taskToTimeout;
        this.shutdownGracePeriodSeconds = builder.shutdownGracePeriodSeconds;
        this.workers = new LinkedList<>();
        builder.workers.forEach(this.workers::add);
        taskRunners = new LinkedList<>();
    }

    /** Builder used to create the instances of TaskRunnerConfigurer */
    public static class Builder {
        private String workerNamePrefix = "workflow-worker-%d";
        private int sleepWhenRetry = 500;
        private int updateRetryCount = 3;
        private int shutdownGracePeriodSeconds = 10;
        private final Iterable<Worker> workers;
        private EurekaClient eurekaClient;
        private final TaskClient taskClient;
        private Map<String /* taskType */, String /* domain */> taskToDomain = new HashMap<>();
        private Map<String /* taskType */, Integer /* threadCount */> taskToThreadCount = new HashMap<>();
        private Map<String /* taskType */, Integer /* timeoutInMillisecond */> taskToTimeout = new HashMap<>();

        public Builder(TaskClient taskClient, Iterable<Worker> workers) {
            Preconditions.checkNotNull(taskClient, "TaskClient cannot be null");
            Preconditions.checkNotNull(workers, "Workers cannot be null");
            this.taskClient = taskClient;
            this.workers = workers;
        }

        /**
         * @param workerNamePrefix prefix to be used for worker names, defaults to
         *                         workflow-worker-
         *                         if not supplied.
         * @return Returns the current instance.
         */
        public TaskRunnerConfigurer.Builder withWorkerNamePrefix(String workerNamePrefix) {
            this.workerNamePrefix = workerNamePrefix;
            return this;
        }

        /**
         * @param sleepWhenRetry time in milliseconds, for which the thread should sleep
         *                       when task
         *                       update call fails, before retrying the operation.
         * @return Returns the current instance.
         */
        public TaskRunnerConfigurer.Builder withSleepWhenRetry(int sleepWhenRetry) {
            this.sleepWhenRetry = sleepWhenRetry;
            return this;
        }

        /**
         * @param updateRetryCount number of times to retry the failed updateTask
         *                         operation
         * @return Builder instance
         * @see #withSleepWhenRetry(int)
         */
        public TaskRunnerConfigurer.Builder withUpdateRetryCount(int updateRetryCount) {
            this.updateRetryCount = updateRetryCount;
            return this;
        }

        /**
         * @param shutdownGracePeriodSeconds waiting seconds before forcing shutdown of
         *                                   your worker
         * @return Builder instance
         */
        public TaskRunnerConfigurer.Builder withShutdownGracePeriodSeconds(int shutdownGracePeriodSeconds) {
            if (shutdownGracePeriodSeconds < 1) {
                throw new IllegalArgumentException(
                        "Seconds of shutdownGracePeriod cannot be less than 1");
            }
            this.shutdownGracePeriodSeconds = shutdownGracePeriodSeconds;
            return this;
        }

        /**
         * @param eurekaClient Eureka client - used to identify if the server is in
         *                     discovery or
         *                     not. When the server goes out of discovery, the polling
         *                     is terminated. If passed
         *                     null, discovery check is not done.
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

        public TaskRunnerConfigurer.Builder withTaskToThreadCount(Map<String, Integer> taskToThreadCount) {
            this.taskToThreadCount = taskToThreadCount;
            return this;
        }

        public TaskRunnerConfigurer.Builder withTaskToTimeout(Map<String, Integer> taskToTimeout) {
            this.taskToTimeout = taskToTimeout;
            return this;
        }

        /**
         * Builds an instance of the TaskRunnerConfigurer.
         *
         * <p>
         * Please see {@link TaskRunnerConfigurer#init()} method. The method must be
         * called after
         * this constructor for the polling to start.
         */
        public TaskRunnerConfigurer build() {
            return new TaskRunnerConfigurer(this);
        }
    }

    /** @return seconds before forcing shutdown of worker */
    public int getShutdownGracePeriodSeconds() {
        return shutdownGracePeriodSeconds;
    }

    /**
     * @return sleep time in millisecond before task update retry is done when
     *         receiving error from
     *         the Conductor server
     */
    public int getSleepWhenRetry() {
        return sleepWhenRetry;
    }

    /**
     * @return Number of times updateTask should be retried when receiving error
     *         from Conductor
     *         server
     */
    public int getUpdateRetryCount() {
        return updateRetryCount;
    }

    /** @return prefix used for worker names */
    public String getWorkerNamePrefix() {
        return workerNamePrefix;
    }

    /**
     * Starts the polling. Must be called after
     * {@link TaskRunnerConfigurer.Builder#build()} method.
     */
    public synchronized void init() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(
                workers.size());
        workers.forEach(
                worker -> this.startWorker(worker));
    }

    /**
     * Invoke this method within a PreDestroy block within your application to
     * facilitate a graceful
     * shutdown of your worker, during process termination.
     */
    public void shutdown() {
        this.taskRunners.forEach(
                taskRunner -> taskRunner.shutdown(shutdownGracePeriodSeconds));
        this.scheduledExecutorService.shutdown();
    }

    private void startWorker(Worker worker) {
        LOGGER.warn("Starting worker: {} with ", worker.getTaskDefName());
        final Integer threadCountForTask = this.taskToThreadCount.getOrDefault(
                worker.getTaskDefName(),
                1);
        final Integer taskPollTimeout = this.taskToTimeout.getOrDefault(
                worker.getTaskDefName(),
                5000);
        final TaskRunner taskRunner = new TaskRunner(
                eurekaClient,
                taskClient,
                updateRetryCount,
                taskToDomain,
                workerNamePrefix,
                threadCountForTask,
                taskPollTimeout);
        this.taskRunners.add(taskRunner);
        this.scheduledExecutorService.scheduleWithFixedDelay(
                () -> taskRunner.poll(worker),
                0,
                worker.getPollingInterval(),
                TimeUnit.MILLISECONDS);
    }
}
