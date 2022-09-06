package io.orkes.conductor.client;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.WorkflowResourceApi;
import io.orkes.conductor.client.http.model.StartWorkflowRequest;

import java.util.HashMap;
import java.util.Map;

public class WorkflowClientTest {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();

        WorkflowResourceApi workflowClient = new WorkflowResourceApi(apiClient);
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setCorrelationId("virenx");
        Map<String, Object> input = new HashMap<>();
        input.put("input_key", "input value");
        request.setInput(input);
        request.setName("http_perf_test");
        String workflowId = workflowClient.startWorkflow(request);
        System.out.println("workflow Id " + workflowId);

    }
}
