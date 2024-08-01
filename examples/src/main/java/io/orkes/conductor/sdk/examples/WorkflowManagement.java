/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.sdk.examples;

import java.util.Arrays;

import io.orkes.conductor.client.api.WorkflowClient;
import io.orkes.conductor.client.http.OrkesClients;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;

import static io.orkes.conductor.sdk.examples.MetadataManagement.workflowDef;

/**
 * Examples for managing Workflow operations in Conductor
 *
 * <p>1. startWorkflow - Start a new workflow 2. getWorkflow - Get workflow execution status 3.
 * pauseWorkflow - Pause workflow 4. resumeWorkflow - Resume workflow 5. terminateWorkflow -
 * Terminate workflow 6. deleteWorkflow - Delete workflow
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
        workflowClient.getWorkflow(workflowId, true);
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
