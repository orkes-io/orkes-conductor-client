package io.orkes.conductor.client.automator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.exception.ConductorClientException;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.discovery.EurekaClient;

/**
 * Configures automated polling of tasks and execution via the registered
 * {@link Worker}s.
 */
public class TaskRunnerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);
    private static final String INVALID_THREAD_COUNT = "Invalid worker thread count specified, use either shared thread pool or config thread count per task";
    private static final String MISSING_TASK_THREAD_COUNT = "Missing task thread count config for %s";

    private ScheduledExecutorService scheduledExecutorService;

    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;
    private final List<Worker> workers = new LinkedList<>();
    private final int sleepWhenRetry;
    private final int updateRetryCount;
    private final int shutdownGracePeriodSeconds;
    private final String workerNamePrefix;
    private final Map<String /* taskType */, String /* domain */> taskToDomain;
    private final Map<String /* taskType */, Integer /* threadCount */> taskThreadCount;

    private TaskRunner taskPollExecutor;

    /**
     * @see TaskRunnerConfigurer.Builder
     * @see TaskRunnerConfigurer#init()
     */
    private TaskRunnerConfigurer(Builder builder) {
        // only allow either shared thread pool or per task thread pool
        if (builder.taskThreadCount.isEmpty()) {
            LOGGER.error(INVALID_THREAD_COUNT);
            throw new ConductorClientException(INVALID_THREAD_COUNT);
        }
        for (Worker worker : builder.workers) {
            if (!builder.taskThreadCount.containsKey(worker.getTaskDefName())) {
                String message = String.format(MISSING_TASK_THREAD_COUNT, worker.getTaskDefName());
                LOGGER.error(message);
                throw new ConductorClientException(message);
            }
            workers.add(worker);
        }
        this.taskThreadCount = builder.taskThreadCount;
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
        private int shutdownGracePeriodSeconds = 10;
        private final Iterable<Worker> workers;
        private EurekaClient eurekaClient;
        private final TaskClient taskClient;
        private Map<String /* taskType */, String /* domain */> taskToDomain = new HashMap<>();
        private Map<String /* taskType */, Integer /* threadCount */> taskThreadCount = new HashMap<>();

        public Builder(TaskClient taskClient, Iterable<Worker> workers) {
            Validate.notNull(taskClient, "TaskClient cannot be null");
            Validate.notNull(workers, "Workers cannot be null");
            this.taskClient = taskClient;
            this.workers = workers;
        }

        /**
         * @param workerNamePrefix prefix to be used for worker names, defaults to
         *                         workflow-worker-
         *                         if not supplied.
         * @return Returns the current instance.
         */
        public Builder withWorkerNamePrefix(String workerNamePrefix) {
            this.workerNamePrefix = workerNamePrefix;
            return this;
        }

        /**
         * @param sleepWhenRetry time in milliseconds, for which the thread should sleep
         *                       when task
         *                       update call fails, before retrying the operation.
         * @return Returns the current instance.
         */
        public Builder withSleepWhenRetry(int sleepWhenRetry) {
            this.sleepWhenRetry = sleepWhenRetry;
            return this;
        }

        /**
         * @param updateRetryCount number of times to retry the failed updateTask
         *                         operation
         * @return Builder instance
         * @see #withSleepWhenRetry(int)
         */
        public Builder withUpdateRetryCount(int updateRetryCount) {
            this.updateRetryCount = updateRetryCount;
            return this;
        }

        /**
         * @param shutdownGracePeriodSeconds waiting seconds before forcing shutdown of
         *                                   your worker
         * @return Builder instance
         */
        public Builder withShutdownGracePeriodSeconds(int shutdownGracePeriodSeconds) {
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
        public Builder withEurekaClient(EurekaClient eurekaClient) {
            this.eurekaClient = eurekaClient;
            return this;
        }

        public Builder withTaskToDomain(Map<String, String> taskToDomain) {
            this.taskToDomain = taskToDomain;
            return this;
        }

        public Builder withTaskThreadCount(Map<String, Integer> taskThreadCount) {
            this.taskThreadCount = taskThreadCount;
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

    /**
     * @return prefix used for worker names
     */
    public String getWorkerNamePrefix() {
        return workerNamePrefix;
    }

    /**
     * Starts the polling. Must be called after
     * {@link TaskRunnerConfigurer.Builder#build()} method.
     */
    public synchronized void init() {
        this.taskPollExecutor = new TaskRunner(
                eurekaClient,
                taskClient,
                updateRetryCount,
                taskToDomain,
                workerNamePrefix,
                taskThreadCount,
                1000);

        this.scheduledExecutorService = Executors.newScheduledThreadPool(workers.size());
        workers.forEach(
                worker -> scheduledExecutorService.scheduleWithFixedDelay(
                        () -> taskPollExecutor.run(worker),
                        0,
                        worker.getPollingInterval(),
                        TimeUnit.MILLISECONDS));
    }

    /**
     * Invoke this method within a PreDestroy block within your application to
     * facilitate a graceful
     * shutdown of your worker, during process termination.
     */
    public void shutdown() {
        taskPollExecutor.shutdownAndAwaitTermination(
                scheduledExecutorService, shutdownGracePeriodSeconds);
        taskPollExecutor.shutdown(shutdownGracePeriodSeconds);
    }
}