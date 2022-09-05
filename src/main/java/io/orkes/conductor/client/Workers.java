package io.orkes.conductor.client;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Workers {
    private static final Logger LOGGER = LoggerFactory.getLogger(Workers.class);

    private final ApiClient apiClient;

    private TaskRunnerConfigurer taskRunnerConfigurer;

    public Workers() {
        this.apiClient = new ApiClient();
    }

    public Workers(String basePath) {
        this.apiClient = new ApiClient(basePath, false);
    }

    public Workers(String basePath, boolean debug) {
        this.apiClient = new ApiClient(basePath, debug);
    }

    public Workers(String basePath, boolean debug, String keyId, String keySecret) {
        this.apiClient = new ApiClient(basePath, debug, keyId, keySecret);
    }

    public void startWorkers(List<Worker> workers) {
        LOGGER.info("Conductor Server URL: {}", this.apiClient.getBasePath());
        LOGGER.info("Starting workers : {}", workers);
        this.taskRunnerConfigurer = new TaskRunnerConfigurer(this.apiClient, workers);
    }

    public void shutdown() {
        this.taskRunnerConfigurer.shutdown();
    }
}