package io.orkes.conductor.client;
import com.google.common.base.Strings;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;
import org.apache.commons.lang3.StringUtils;
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

            @Override
            public int getPollingInterval() {
                return 100;
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


            ApiClient apiClient = new ApiClient(rootUri, keyId, secret);
            TaskResourceApi taskClient = new TaskResourceApi(apiClient);

            TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer
                    .Builder(taskClient, workers)
                    .withThreadCount(Math.max(1, workers.size()))
                    .withTaskPollTimeout(100)
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