package io.orkes.conductor.client.automator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.http.api.TaskResourceApi;
import io.orkes.conductor.client.worker.Worker;

/**
 * Configures automated polling of tasks and execution via the registered
 * {@link Worker}s.
 */
public class TaskRunnerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfigurer.class);

    private String workerNamePrefix = "workflow-worker-%d";
    private int updateRetryCount = 3;
    private int shutdownGracePeriodSeconds = 10;
    private final List<TaskRunner> taskRunners;
    private final ScheduledExecutorService scheduledExecutorService;

    public TaskRunnerConfigurer(TaskResourceApi taskClient, List<Worker> workers) {
        taskRunners = new LinkedList<>();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(workers.size());
        for (Worker worker : workers) {
            TaskRunner taskRunner = new TaskRunner(
                    taskClient,
                    updateRetryCount,
                    workerNamePrefix,
                    1000,
                    worker);
            taskRunners.add(taskRunner);
            scheduledExecutorService.scheduleWithFixedDelay(
                    () -> taskRunner.run(),
                    0,
                    worker.getPollingInterval(),
                    TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Invoke this method within a PreDestroy block within your application to
     * facilitate a graceful
     * shutdown of your worker, during process termination.
     */
    public void shutdown() {
        this.taskRunners.get(0).shutdownAndAwaitTermination(scheduledExecutorService, shutdownGracePeriodSeconds);
        this.taskRunners.forEach(
                taskRunner -> taskRunner.shutdown(shutdownGracePeriodSeconds));
    }
}