package io.orkes.conductor.sdk.examples;

import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;

public class WorkflowManagement {

    private static WorkflowClient workflowClient;

    public static void main(String[] args) {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        createMetadata();
        WorkflowManagement workflowManagement = new WorkflowManagement();
        workflowClient = orkesClients.getWorkflowClient();
    }

    private static void createMetadata() {
        MetadataManagement metadataManagement = new MetadataManagement();
        metadataManagement.createTaskDefinitions();
        metadataManagement.createWorkflowDefinitions();
    }
}
