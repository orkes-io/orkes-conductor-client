package io.orkes.conductor.client;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Workers {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationClientFilter.class);

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
        if (rootUri == null) {
            throw new IllegalStateException("RootUri is null");
        }

        if (!started) {
            LOGGER.info("Conductor Server URL: {}", rootUri);
            LOGGER.info("Starting workers : {}", workers);

            OrkesTaskClient taskClient = new OrkesTaskClient();
            taskClient.setRootURI(rootUri);
            if (keyId != null && secret != null) {
                taskClient.withCredentials(keyId, secret);
            }

            TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer
                    .Builder(taskClient, workers)
                    .withThreadCount(Math.max(1, workers.size()))
                    .build();
            runnerConfigurer.init();
            started = true;
        } else {
            LOGGER.warn("Workers have already been started");
        }

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
