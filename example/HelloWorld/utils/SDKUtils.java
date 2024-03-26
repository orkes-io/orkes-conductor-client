package io.orkes.samples.quickstart.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;


public class SDKUtils {
    private final WorkflowExecutor workflowExecutor;
    private final MetadataClient metadataClient;
    private final WorkflowClient workflowClient;
    private final TaskClient taskClient;
    private ApiClient apiClient;
    private TaskRunnerConfigurer taskRunner;

    public SDKUtils() {

        String CONDUCTOR_SERVER_URL = System.getenv("CONDUCTOR_SERVER_URL");
        String key = System.getenv("KEY");
        String secret = System.getenv("SECRET");
        String conductorServer = System.getenv("CONDUCTOR_SERVER_URL");
        if (conductorServer == null) {
            conductorServer = CONDUCTOR_SERVER_URL;
        }
        if (StringUtils.isNotBlank(key)) {
            apiClient = new ApiClient(conductorServer, key, secret);
        } else {
            apiClient = new ApiClient(conductorServer);
        }
        apiClient.setReadTimeout(30_000);

        if (StringUtils.isBlank(key) || StringUtils.isBlank(secret)) {
            System.out.println(
                    "\n\nMissing KEY and|or SECRET.  Attempting to connect to "
                            + conductorServer
                            + " without authentication\n\n");
            apiClient = new ApiClient(conductorServer);
        }

        OrkesClients orkesClients = new OrkesClients(apiClient);
        this.metadataClient = orkesClients.getMetadataClient();
        this.workflowClient = orkesClients.getWorkflowClient();
        this.taskClient = orkesClients.getTaskClient();
        this.workflowExecutor =
                new WorkflowExecutor(this.taskClient, this.workflowClient, this.metadataClient, 10);
        this.workflowExecutor.initWorkers("io.orkes.samples.quickstart.workers");
        initWorkers(Arrays.asList());
    }

    private void initWorkers(List<Worker> workers) {

        TaskRunnerConfigurer.Builder builder =
                new TaskRunnerConfigurer.Builder(taskClient, workers);

        taskRunner = builder.withThreadCount(1).withTaskPollTimeout(5).build();

        // Start Polling for tasks and execute them
        taskRunner.init();  
    }

    public WorkflowExecutor getWorkflowExecutor() {
        return workflowExecutor;
    }

    public WorkflowClient getWorkflowClient() {
        return workflowClient;
    }

    public String getUIPath() {
        return apiClient.getBasePath().replaceAll("api", "").replaceAll("8080", "5000")
                + "execution/";
    }

    // Clean up resources
    public void shutdown() {
        this.apiClient.shutdown();
        this.workflowClient.shutdown();
        this.taskRunner.shutdown();
    }
}
