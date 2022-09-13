package io.orkes.conductor.client.api;

import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.util.Commons;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LegacyClientTests extends ClientTest {

    private final WorkflowClient workflowClient;

    public LegacyClientTests() {
        this.workflowClient = super.orkesClients.getWorkflowClientLegacy();
    }

    @Test
    public void startWorkflow() {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(Commons.WORKFLOW_NAME);
        startWorkflowRequest.setVersion(Commons.WORKFLOW_VERSION);
        Map<String, Object> input = new HashMap<>();
        startWorkflowRequest.setInput(input);
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, false);
        assertNotNull(workflow, "Workflow should'n be null");
    }
}
