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

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import lombok.extern.slf4j.Slf4j;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowWithSubWorkflowDef;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public class WorkflowRetryTests {

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
    @DisplayName("Check workflow with simple task and retry functionality")
    public void testRetrySimpleWorkflow() {
        String workflowName = "retry-simple-workflow";
        String taskDefName = "retry-simple-task1";

        terminateExistingRunningWorkflows(workflowName);

        // Register workflow
        registerWorkflowDef(workflowName, taskDefName, taskDefName, metadataClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Fail the simple task
        String taskId = workflow.getTasks().get(1).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("failed");
        taskClient.updateTask(taskResult);

        // Wait for workflow to get failed
        await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Retry the workflow
        workflowClient.retryLastFailedTask(workflowId);
        // Check the workflow status and few other parameters
        await()
                .atMost(5, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertTrue(workflow1.getLastRetriedTime() != 0L);
            assertEquals(workflow1.getTasks().get(2).getStatus().name(), Task.Status.SCHEDULED.name());
        });

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflowClient.getWorkflow(workflowId, true).getTasks().get(2).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });
    }

    @Test
    @DisplayName("Check workflow with sub_workflow task and retry functionality")
    public void testRetryWithSubWorkflow() {

        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "retry-parent-with-sub-workflow";
        String subWorkflowName = "retry-sub-workflow";
        String taskName = "simple-no-retry2";

        terminateExistingRunningWorkflows(workflowName);

        // Register workflow
        registerWorkflowWithSubWorkflowDef(workflowName, subWorkflowName, taskName, metadataClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        System.out.print("Workflow id is " + workflowId);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Fail the simple task
        String subworkflowId = workflow.getTasks().get(0).getSubWorkflowId();
        Workflow subWorkflow = workflowClient.getWorkflow(subworkflowId, true);
        String taskId = subWorkflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(subworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskClient.updateTask(taskResult);

        // Wait for parent workflow to get failed
        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Retry the sub workflow.
        workflowClient.retryLastFailedTask(subworkflowId);
        // Check the workflow status and few other parameters
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(subworkflowId, true);
            assertEquals(WorkflowStatus.StatusEnum.RUNNING.name(), workflow1.getStatus().name());
            assertTrue(workflow1.getLastRetriedTime() != 0L);
            assertEquals(workflow1.getTasks().get(0).getStatus().name(), Task.Status.FAILED.name());
            assertEquals(workflow1.getTasks().get(1).getStatus().name(), Task.Status.SCHEDULED.name());
        });
        taskId = workflowClient.getWorkflow(subworkflowId, true).getTasks().get(1).getTaskId();

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(subworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        await().atMost(33, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(WorkflowStatus.StatusEnum.COMPLETED.name(), workflow1.getStatus().name(), "workflow " + workflowId + " did not complete");
        });


        // Check retry at parent workflow level.
        String newWorkflowId = workflowClient.startWorkflow(startWorkflowRequest);
        System.out.print("Workflow id is " + newWorkflowId);
        Workflow newWorkflow = workflowClient.getWorkflow(newWorkflowId, true);
        // Fail the simple task
        String newSubworkflowId = newWorkflow.getTasks().get(0).getSubWorkflowId();
        Workflow newSubWorkflow = workflowClient.getWorkflow(newSubworkflowId, true);
        taskId = newSubWorkflow.getTasks().get(0).getTaskId();
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(newSubworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskClient.updateTask(taskResult);

        // Wait for parent workflow to get failed
        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(newWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Retry parent workflow.
        workflowClient.retryLastFailedTask(newWorkflowId);

        // Wait for parent workflow to get failed
        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(newWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
        });

        newWorkflow = workflowClient.getWorkflow(newWorkflowId, true);
        newSubworkflowId = newWorkflow.getTasks().get(0).getSubWorkflowId();
        newSubWorkflow = workflowClient.getWorkflow(newSubworkflowId, true);
        taskId = newSubWorkflow.getTasks().get(1).getTaskId();
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(newSubworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(newWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });
    }

    private void terminateExistingRunningWorkflows(String workflowName) {
        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        System.out.println("Found " + found.getResults().size() + " running workflows to be cleaned up");
        found.getResults().forEach(workflowSummary -> {
            try {
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId() + " with status " + workflowSummary.getStatus());
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
            } catch(Exception e){}
        });
    }

}
