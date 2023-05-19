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
package io.orkes.conductor.client.e2e;

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public class FailureWorkflowTests {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static TaskClient taskClient;
    static MetadataClient metadataClient;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient  =new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);

    }

    @Test
    @DisplayName("Check failure workflow input as passed properly")
    public void testFailureWorkflowInputs() {
        String workflowName = "failure-workflow-test";
        String taskDefName = "simple-task1";
        String taskDefName2 = "simple-task2";

        // Register workflow
        registerWorkflowDefWithFailureWorkflow(workflowName, taskDefName, taskDefName2, metadataClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Fail the simple task
        String taskId = workflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.getOutputData().put("status", "completed");
        taskResult.getOutputData().put("reason", "inserted");
        taskClient.updateTask(taskResult);

        workflow = workflowClient.getWorkflow(workflowId, true);
        String reason = "Employee not found";
        taskId = workflow.getTasks().get(1).getTaskId();
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        taskResult.setReasonForIncompletion(reason);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get failed
        await().atMost(5, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
                    assertEquals(Workflow.WorkflowStatus.FAILED.name(), workflow1.getStatus().name());
                    assertNotNull(workflow1.getOutput().get("conductor.failure_workflow"));
                });

        // Check failure workflow has complete parent workflow information
        workflow = workflowClient.getWorkflow(workflowId, false);
        String failureWorkflowId = workflow.getOutput().get("conductor.failure_workflow").toString();

        workflow = workflowClient.getWorkflow(failureWorkflowId, false);
        // Assert on input attributes
        assertNotNull(workflow.getInput().get("failedWorkflow"));
        assertNotNull(workflow.getInput().get("failureTaskId"));
        assertNotNull(workflow.getInput().get("workflowId"));
        assertEquals("FAILED", workflow.getInput().get("failureStatus").toString());
        assertEquals(reason, workflow.getInput().get("reason").toString());
        Map<String, Object> input = (Map<String, Object>) workflow.getInput().get("failedWorkflow");

        assertNotNull(input.get("tasks"));
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) input.get("tasks");
        assertNotNull(tasks.get(0).get("outputData"));
        Map<String, String> task1Output = (Map<String, String>) tasks.get(0).get("outputData");
        assertEquals("inserted", task1Output.get("reason"));
        assertEquals("completed", task1Output.get("status"));
        Map<String, Object> failedWorkflowOutput = (Map<String, Object>) input.get("output");
        assertEquals("completed", failedWorkflowOutput.get("status"));
    }

    private void registerWorkflowDefWithFailureWorkflow(String workflowName, String taskName1, String taskName2, MetadataClient metadataClient) {

        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName(taskName1);
        inline.setName(taskName1);
        inline.setWorkflowTaskType(TaskType.SIMPLE);
        inline.setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "true;"));

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName(taskName2);
        simpleTask.setName(taskName2);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowTask simpleTask2 = new WorkflowTask();
        simpleTask2.setTaskReferenceName(taskName2);
        simpleTask2.setName(taskName2);
        simpleTask2.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowDef failureWorkflow = new WorkflowDef();
        failureWorkflow.setName("failure_workflow");
        failureWorkflow.setOwnerEmail("test@orkes.io");
        failureWorkflow.setInputParameters(Arrays.asList("value", "inlineValue"));
        failureWorkflow.setDescription("Workflow to monitor order state");
        failureWorkflow.setTimeoutSeconds(600);
        failureWorkflow.setFailureWorkflow("failure_workflow");
        failureWorkflow.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        failureWorkflow.setTasks(Arrays.asList(simpleTask2));
        metadataClient.registerWorkflowDef(failureWorkflow);

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setFailureWorkflow("failure_workflow");
        workflowDef.getOutputParameters().put("status", "${" + taskName1 + ".output.status}");
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(inline, simpleTask));
        metadataClient.registerWorkflowDef(workflowDef);
    }

}
