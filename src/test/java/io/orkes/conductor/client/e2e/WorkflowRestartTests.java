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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.*;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowWithSubWorkflowDef;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowRestartTests {

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
    @DisplayName("Check workflow with simple task and restart functionality")
    public void testRestartSimpleWorkflow() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowDef(workflowName, "simple", "inline", metadataClient);

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
        taskClient.updateTask(taskResult);

        // Wait for workflow to get failed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Restart the workflow
        workflowClient.restart(workflowId, false);
        // Check the workflow status and few other parameters
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(1).getStatus().name(), Task.Status.SCHEDULED.name());
            assertTrue(workflow1.getTasks().get(0).isExecuted());
            assertFalse(workflow1.getTasks().get(1).isExecuted());
        });

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflowClient.getWorkflow(workflowId, true).getTasks().get(1).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef("simple");
        metadataClient.unregisterTaskDef("inline");
    }

    @Test
    @DisplayName("Check workflow with simple task and restart functionality by changing workflow definition")
    public void testRestartSimpleWorkflowChangeDefinition() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowDef(workflowName,"simple", "inline", metadataClient);

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
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get failed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        // Change the workflow definition.
        WorkflowDef workflowDef = metadataClient.getWorkflowDef(workflowName, 1);
        addTasksInWorkflowDef(workflowDef);

        // Restart the workflow
        workflowClient.restart(workflowId, true);
        // Check the workflow status and few other parameters
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(1).getStatus().name(), Task.Status.SCHEDULED.name());
            assertTrue(workflow1.getTasks().get(0).isExecuted());
            assertFalse(workflow1.getTasks().get(1).isExecuted());
        });
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflowClient.getWorkflow(workflowId, true).getTasks().get(1).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Workflow will still be running since new simple task has been added.
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
        });

        // Complete the newly added simple task.
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflowClient.getWorkflow(workflowId, true).getTasks().get(2).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef("simple");
        metadataClient.unregisterTaskDef("inline");
    }

    private void addTasksInWorkflowDef(WorkflowDef workflowDef) {
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName("added");
        workflowTask.setName("added");
        workflowTask.setType(TaskType.SIMPLE.name());

        TaskDef task = new TaskDef();
        task.setName("added");
        task.setOwnerEmail("test@orkes.io");
        metadataClient.registerTaskDefs(Arrays.asList(task));
        workflowDef.getTasks().add(workflowTask);
        metadataClient.registerWorkflowDef(workflowDef);
    }

    @Test
    @DisplayName("Check workflow with sub_workflow task and restart functionality")
    public void testRestartWithSubWorkflow() {

        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String subWorkflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowWithSubWorkflowDef(workflowName, subWorkflowName, "simple", metadataClient);

        // Trigger two workflows
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
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Retry the workflow.
        workflowClient.restart(workflowId, false);
        // Check the workflow status and few other parameters
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(0).getStatus().name(), Task.Status.IN_PROGRESS.name());
        });

        taskId = workflowClient.getWorkflow(workflowId, true).getTasks().get(0).getTaskId();

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef("simple");
    }

}
