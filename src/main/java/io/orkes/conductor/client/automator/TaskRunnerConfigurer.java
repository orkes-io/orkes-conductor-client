package io.orkes.conductor.client.automator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.http.ApiClient;
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

    public TaskRunnerConfigurer(ApiClient apiClient, List<Worker> workers) {
        final TaskResourceApi taskClient = new TaskResourceApi(apiClient);
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
        this.shutdownAndAwaitTermination(this.scheduledExecutorService, this.shutdownGracePeriodSeconds);
        this.taskRunners.forEach(
                taskRunner -> this.shutdownAndAwaitTermination(
                        taskRunner.getExecutorService(),
                        this.shutdownGracePeriodSeconds));
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService, int timeout) {
        try {
            executorService.shutdown();
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
}