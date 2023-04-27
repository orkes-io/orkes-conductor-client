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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
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

public class WorkflowRerunTests {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static TaskClient taskClient;
    static MetadataClient metadataClient;

    List<String> workflowNames = new ArrayList<>();

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient  =new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }

    @Before
    public void initTest() {
        workflowNames = new ArrayList<>();
    }
    @After
    public void cleanUp() {
        try {
            for (String workflowName : workflowNames) {
                metadataClient.unregisterWorkflowDef(workflowName, 1);
            }
        } catch (Exception e) {}
    }

    @Test
    @DisplayName("Check workflow with simple task and rerun functionality")
    public void testRerunSimpleWorkflow() {

        String workflowName = "re-run-workflow";
        String taskName1 = "re-run-task1";
        String taskName2 = "re-run-task2";
        // Register workflow
        registerWorkflowDef(workflowName, taskName1, taskName2, metadataClient);
        workflowNames.add(workflowName);

        // Trigger two workflows
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

        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(workflowId);
        rerunWorkflowRequest.setReRunFromTaskId(taskId);
        // Retry the workflow
        workflowClient.rerunWorkflow(workflowId, rerunWorkflowRequest);
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
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);
        workflowClient.runDecider(workflowId);
        System.out.println("Going to check workflow " + workflowId + " for completion");

        // Wait for workflow to get completed
        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(WorkflowStatus.StatusEnum.COMPLETED.name(), workflow1.getStatus().name(), "Workflow " + workflowId + " did not complete");
        });

        try {
            metadataClient.unregisterWorkflowDef(workflowName, 1);
            metadataClient.unregisterTaskDef(taskName2);
            metadataClient.unregisterTaskDef(taskName2);
        }catch (Exception e){}
    }

    //@Test
    @DisplayName("Check workflow with sub_workflow task and rerun functionality")
    public void testRerunWithSubWorkflow() throws Exception {

        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "workflow-re-run-with-sub-workflow";
        String taskName = "re-run-with-sub-task";
        String subWorkflowName = "workflow-re-run-sub-workflow";
        workflowNames.add(workflowName);
        workflowNames.add(subWorkflowName);

        // Register workflow
        registerWorkflowWithSubWorkflowDef(workflowName, subWorkflowName, taskName, metadataClient);

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
        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        // Retry the sub workflow.
        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(subworkflowId);
        rerunWorkflowRequest.setReRunFromTaskId(taskId);
        workflowClient.rerunWorkflow(subworkflowId, rerunWorkflowRequest);
        // Check the workflow status and few other parameters
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(subworkflowId, true);
            assertEquals(WorkflowStatus.StatusEnum.RUNNING.name(), workflow1.getStatus().name());
            assertEquals(workflow1.getTasks().get(0).getStatus().name(), Task.Status.SCHEDULED.name());
        });
        taskId = workflowClient.getWorkflow(subworkflowId, true).getTasks().get(0).getTaskId();

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(subworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        await().atMost(33, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(WorkflowStatus.StatusEnum.COMPLETED.name(), workflow1.getStatus().name(), "Workflow " + workflowId + " did not complete");
        });

        try {
            metadataClient.unregisterWorkflowDef(workflowName, 1);
            metadataClient.unregisterTaskDef(taskName);
        } catch (Exception e){}
    }

    @Test
    @DisplayName("Check workflow fork join task rerun")
    public void testRerunForkJoinWorkflow() {

        String workflowName = "re-run-fork-workflow";
        // Register workflow
        registerForkJoinWorkflowDef(workflowName, metadataClient);
        workflowNames.add(workflowName);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(3, workflow.getTasks().size());
        // Fail the simple task
        String taskId = workflow.getTasks().get(2).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        taskId = workflow.getTasks().get(1).getTaskId();
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        taskClient.updateTask(taskResult);

        // Wait for workflow to get failed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(workflowId);
        rerunWorkflowRequest.setReRunFromTaskId(taskId);
        // Retry the workflow
        workflowClient.rerunWorkflow(workflowId, rerunWorkflowRequest);
        // Check the workflow status and few other parameters
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(2).getStatus().name(), Task.Status.SCHEDULED.name());
            // Rerun should not affect this task since it was already completed.
            assertEquals(workflow1.getTasks().get(1).getStatus().name(), Task.Status.COMPLETED.name());
        });

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);
        workflowClient.runDecider(workflowId);
        System.out.println("Going to check workflow " + workflowId + " for completion");

        // Wait for workflow to get completed
        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(WorkflowStatus.StatusEnum.COMPLETED.name(), workflow1.getStatus().name(), "Workflow " + workflowId + " did not complete");
        });

        try {
            metadataClient.unregisterWorkflowDef(workflowName, 1);
        }catch (Exception e){}
    }

    private void registerForkJoinWorkflowDef(String workflowName, MetadataClient metadataClient) {

        String fork_task = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String fork_task1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String fork_task2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String join_task = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        WorkflowTask fork_task_1 = new WorkflowTask();
        fork_task_1.setTaskReferenceName(fork_task1);
        fork_task_1.setName(fork_task1);
        fork_task_1.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowTask fork_task_2 = new WorkflowTask();
        fork_task_2.setTaskReferenceName(fork_task2);
        fork_task_2.setName(fork_task2);
        fork_task_2.setWorkflowTaskType(TaskType.SIMPLE);


        WorkflowTask fork_workflow_task = new WorkflowTask();
        fork_workflow_task.setTaskReferenceName(fork_task);
        fork_workflow_task.setName(fork_task);
        fork_workflow_task.setWorkflowTaskType(TaskType.SIMPLE);
        fork_workflow_task.setForkTasks(List.of(List.of(fork_task_1), List.of(fork_task_2)));

        WorkflowTask join = new WorkflowTask();
        join.setTaskReferenceName(join_task);
        join.setName(join_task);
        join.setWorkflowTaskType(TaskType.JOIN);
        join.setJoinOn(List.of("fork_task_1", "fork_task_2"));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test retry");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(fork_workflow_task, join));
        workflowDef.setOwnerEmail("test@orkes.io");
        metadataClient.registerWorkflowDef(workflowDef);
    }
}
