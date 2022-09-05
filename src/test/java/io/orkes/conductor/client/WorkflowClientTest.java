package io.orkes.conductor.client;

import com.netflix.conductor.client.http.WorkflowClient;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.WorkflowResourceApi;
import io.orkes.conductor.client.http.model.StartWorkflowRequest;

import java.util.HashMap;
import java.util.Map;

public class WorkflowClientTest {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("https://pg-staging.orkesconductor.com/api","key", "secret");
        WorkflowResourceApi workflowClient = new WorkflowResourceApi(apiClient);
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setCorrelationId("virenx");
        Map<String, Object> input = new HashMap<>();
        input.put("input_key", "input value");
        request.setInput(input);
        request.setName("load_test");
        String workflowId = workflowClient.startWorkflow(request);
        System.out.println("workflow Id " + workflowId);

    }
}
