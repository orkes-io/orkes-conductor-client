package io.orkes.conductor.client;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;
import com.netflix.conductor.client.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Workers {
    private static final Logger LOGGER = LoggerFactory.getLogger(Workers.class);

    public static void main(String ap[]) {
        List<Worker> workers = new ArrayList<>();
        ApiClient apiClient = new ApiClient("https://localhost:8080/api", false);
        com.netflix.conductor.client.worker.Worker worker1 = new SampleWorker("task_1");
        com.netflix.conductor.client.worker.Worker worker2 = new SampleWorker("task_5");
        workers.add(worker2);
        workers.add(worker1);
        TaskResourceApi taskClient = new TaskResourceApi(apiClient);
        TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer(
                taskClient,
                workers);
    }
}