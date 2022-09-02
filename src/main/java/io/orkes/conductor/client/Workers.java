package io.orkes.conductor.client;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;
import io.orkes.conductor.client.http.model.Task;
import io.orkes.conductor.client.http.model.TaskResult;
import io.orkes.conductor.client.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Workers {
    private static final Logger LOGGER = LoggerFactory.getLogger(Workers.class);

    private final List<Worker> workers = new ArrayList<>();
    private String rootUri;
    private boolean started = false;
    private String keyId;
    private String secret;

    public Workers register(String name, WorkerFn workerFn) {
        workers.add(new Worker() {
            @Override
            public String getTaskDefName() {
                return name;
            }

            @Override
            public TaskResult execute(Task task) {
                return workerFn.execute(task);
            }
        });
        return this;
    }

    public Workers rootUri(String rootUri) {
        this.rootUri = rootUri;
        return this;
    }

    public Workers keyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    public Workers secret(String secret) {
        this.secret = secret;
        return this;
    }

    public Workers startAll() {
        if (started) {
            LOGGER.warn("Workers have already been started");
            return this;
        }
        LOGGER.info("Conductor Server URL: {}", rootUri);
        LOGGER.info("Starting workers : {}", workers);
        ApiClient apiClient = null;
        if (keyId == null || secret == null) {
            apiClient = new ApiClient();
        } else {
            apiClient = new ApiClient(
                    rootUri,
                    true,
                    keyId,
                    secret);
        }
        TaskResourceApi taskClient = new TaskResourceApi(apiClient);
        TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer(
                taskClient,
                workers);
        started = true;
        return this;
    }

    public void start(String name, WorkerFn workerFn) {
        workers.add(new Worker() {
            @Override
            public String getTaskDefName() {
                return name;
            }

            @Override
            public TaskResult execute(Task task) {
                return workerFn.execute(task);
            }
        });
        startAll();
    }
}