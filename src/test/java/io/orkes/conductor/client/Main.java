package io.orkes.conductor.client;


import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.WorkflowRun;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        OrkesClients clients = new OrkesClients(apiClient);
        WorkflowClient workflowClient = clients.getWorkflowClient();
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("http_perf_test");
        request.setVersion(1);
        request.setInput(new HashMap<>());
        CompletableFuture<WorkflowRun> future = workflowClient.executeWorkflow(request, null);

        future
                .thenAccept((WorkflowRun workflowRun) -> {
                    System.out.println("Executed " + workflowRun.getWorkflowId());
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

        apiClient.shutdown();
    }
}
