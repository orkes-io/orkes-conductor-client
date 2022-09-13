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
package io.orkes.conductor.client.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.util.Commons;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WorkflowClientTests extends ClientTest {
    private final WorkflowClient workflowClient;

    public WorkflowClientTests() {
        this.workflowClient = super.orkesClients.getWorkflowClient();
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

    @Test
    public void testWorkflowMethods() {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(Commons.WORKFLOW_NAME);
        startWorkflowRequest.setVersion(1);
        Map<String, Object> input = new HashMap<>();
        startWorkflowRequest.setInput(input);
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, false);
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    workflowClient.populateWorkflowOutput(workflow);
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    workflowClient.resetCallbacksForInProgressTasks(workflowId);
                });
        assertNotNull(workflowClient.getWorkflows(Commons.WORKFLOW_NAME, "abc", false, false));
        workflowClient.terminateWorkflow(workflowId, "reason");
        workflowClient.retryLastFailedTask(workflowId);
        workflowClient.terminateWorkflows(List.of(workflowId), "reason");
        workflowClient.restart(workflowId, true);
        workflowClient.terminateWorkflow(List.of(workflowId), "reason");
        workflowClient.restartWorkflow(List.of(workflowId), true);
        workflowClient.getRunningWorkflow(Commons.WORKFLOW_NAME, Commons.WORKFLOW_VERSION);
        workflowClient.pauseWorkflow(workflowId);
        workflowClient.resumeWorkflow(workflowId);
        workflowClient.pauseWorkflow(List.of(workflowId));
        workflowClient.resumeWorkflow(List.of(workflowId));
        workflowClient.deleteWorkflow(workflowId, false);
    }
}
