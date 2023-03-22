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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import com.google.common.util.concurrent.Uninterruptibles;
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

    static String taskDefName = UUID.randomUUID().toString();

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
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowDef(workflowName, taskDefName, taskDefName, metadataClient);

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

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        unregisterTaskDef();
    }

    @Test
    @DisplayName("Check workflow with sub_workflow task and retry functionality")
    public void testRetryWithSubWorkflow() {

        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String subWorkflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowWithSubWorkflowDef(workflowName, subWorkflowName, taskDefName, metadataClient);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String parentWorkflowId = workflowClient.startWorkflow(startWorkflowRequest);
        log.info("Parent Workflow id is {}", parentWorkflowId);
        Workflow workflow = workflowClient.getWorkflow(parentWorkflowId, true);
        // Fail the simple task
        String subworkflowId = workflow.getTasks().get(0).getSubWorkflowId();
        Workflow subWorkflow = workflowClient.getWorkflow(subworkflowId, true);
        String taskId = subWorkflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(subworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("failing sub workflow task for testing");
        taskClient.updateTask(taskResult);
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        //Run decider
        workflowClient.runDecider(subworkflowId);

        // Wait for parent workflow to get failed
        await()
                .atMost(42, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(parentWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });

        log.info("Retrying parent workflow {}", parentWorkflowId);
        // Retry the workflow.
        workflowClient.retryLastFailedTask(parentWorkflowId);

        workflowClient.runDecider(parentWorkflowId);
        workflow = workflowClient.getWorkflow(parentWorkflowId, true);
        subworkflowId = workflow.getTasks().get(0).getSubWorkflowId();

        workflowClient.runDecider(subworkflowId);

        // Check the workflow status and few other parameters
        await().atMost(30, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(parentWorkflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertTrue(workflow1.getLastRetriedTime() != 0L);
            assertEquals(workflow1.getTasks().get(0).getStatus().name(), Task.Status.IN_PROGRESS.name());
        });
        workflow = workflowClient.getWorkflow(parentWorkflowId, true);
        assertEquals(workflow.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
        assertTrue(workflow.getLastRetriedTime() != 0L);
        assertEquals(workflow.getTasks().get(0).getStatus().name(), Task.Status.IN_PROGRESS.name());
        workflowClient.runDecider(subworkflowId);

        subworkflowId = workflow.getTasks().get(0).getSubWorkflowId();
        List<Task> subWorkflowTasks = workflowClient.getWorkflow(subworkflowId, true).getTasks();
        taskId = subWorkflowTasks.get(subWorkflowTasks.size()-1).getTaskId();

        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(subworkflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        log.info("Updating task with id {} wf = {}", taskId, subworkflowId);
        taskClient.updateTask(taskResult);

        workflowClient.runDecider(subworkflowId);
        workflowClient.runDecider(parentWorkflowId);

        // Wait for workflow to get completed
        await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(parentWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        unregisterTaskDef();
    }

    void unregisterTaskDef() {
        try {
            metadataClient.unregisterTaskDef(taskDefName);
        }catch (Exception e) {}
    }
}
