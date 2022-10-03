package io.orkes.conductor.client;


import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.WorkflowRun;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("https://orkes-loadtest.orkesconductor.com/api", "7478920f-e032-48cc-a033-2544f56a346c", "ctTYzti53Es5bULUzKG1m5h2ZUa49fCJ4NPMI0720MTe1JJq");
        apiClient.setReadTimeout(10_000);
        OrkesClients clients = new OrkesClients(apiClient);

        WorkflowClient workflowClient = clients.getWorkflowClient();
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("load_test");
        request.setVersion(1);
        request.setInput(new HashMap<>());
        String id = workflowClient.startWorkflow(request);
        System.out.println("started " + id);
        CompletableFuture<WorkflowRun> future = workflowClient.executeWorkflow(request, UUID.randomUUID().toString());
        System.out.println("started " + future);

        future
                .thenAccept((WorkflowRun workflowRun) -> {
                    System.out.println("Executed " + workflowRun.getWorkflowId() + ":" + workflowRun.getStatus());
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

        apiClient.shutdown();
    }
}
