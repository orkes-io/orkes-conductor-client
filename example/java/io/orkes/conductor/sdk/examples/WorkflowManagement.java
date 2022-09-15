package io.orkes.conductor.sdk.examples;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;

import java.util.Arrays;

import static io.orkes.conductor.sdk.examples.MetadataManagement.workflowDef;

/* This example covers following apis
1. startWorkflow - Start a new workflow
2. getWorkflow - Get workflow execution status
3. pauseWorkflow - Pause workflow
4. resumeWorkflow - Resume workflow
5. terminateWorkflow - Terminate workflow
6. deleteWorkflow - Delete workflow
*/
public class WorkflowManagement {

    private static WorkflowClient workflowClient;

    public static void main(String[] args) {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        createMetadata();
        WorkflowManagement workflowManagement = new WorkflowManagement();
        workflowClient = orkesClients.getWorkflowClient();
        workflowManagement.workflowOperations();
    }

    private static void createMetadata() {
        MetadataManagement metadataManagement = new MetadataManagement();
        metadataManagement.createTaskDefinitions();
        metadataManagement.createWorkflowDefinitions();
    }

    private void workflowOperations() {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowDef.getName());
        startWorkflowRequest.setVersion(workflowDef.getVersion());
        startWorkflowRequest.setCorrelationId("test_workflow");

        // Start the workflow
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        // Get the workflow execution status
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Pause the workflow
        workflowClient.pauseWorkflow(workflowId);
        // Resume the workflow
        workflowClient.resumeWorkflow(workflowId);
        // Terminate the workflow
        workflowClient.terminateWorkflow(workflowId, "Terminated");
        // Retry workflow
        workflowClient.retryWorkflow(Arrays.asList(workflowId));
        // Terminate the workflow
        workflowClient.terminateWorkflow(workflowId, "Terminated");
        // Restart workflow
        workflowClient.restartWorkflow(Arrays.asList(workflowId), false);
        // Terminate the workflow
        workflowClient.terminateWorkflow(workflowId, "Terminated");
        // Restart workflow using latest workflow definitions
        workflowClient.restartWorkflow(Arrays.asList(workflowId), true);
        // Terminate the workflow
        workflowClient.terminateWorkflow(workflowId, "Terminated");
        // Delete the workflow without archiving
        workflowClient.deleteWorkflow(workflowId, false);
        // Delete the workflow with archiving to persistent store.
        workflowClient.deleteWorkflow(workflowId, true);
    }
}
