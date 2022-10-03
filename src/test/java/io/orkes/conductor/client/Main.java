package io.orkes.conductor.client;


import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.WorkflowRun;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setReadTimeout(10_000);
        OrkesClients clients = new OrkesClients(apiClient);

        WorkflowClient workflowClient = clients.getWorkflowClient();
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("load_test_4");
        request.setVersion(1);
        request.setInput(new HashMap<>());
        String id = workflowClient.startWorkflow(request);
        System.out.println("started " + id);
        CompletableFuture<WorkflowRun> future = workflowClient.executeWorkflow(request, "bad_simple_task_3");
        System.out.println("started " + future);

        future
                .thenAccept((WorkflowRun workflowRun) -> {
                    System.out.println("Executed " + workflowRun.getWorkflowId() + ":" + workflowRun.getStatus());
                    System.out.println(workflowRun.getTasks().stream().map(Task::getReferenceTaskName).collect(Collectors.toList()));
                    System.out.println(workflowRun.getOutput());
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

        apiClient.shutdown();
    }
}
