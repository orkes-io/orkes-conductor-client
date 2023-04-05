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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Uninterruptibles;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
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
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.sdk.examples.ApiUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class TaskRateLimitTests {

    @Test
    @DisplayName("Check workflow with simple rate limit by name")
    public void testRateLimitByPerFrequency() throws InterruptedException {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "task-rate-limit-test";
        String taskName = "rate-limited-task";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        workflowClient.terminateWorkflow(found.getResults().stream().map(s ->s.getWorkflowId()).collect(Collectors.toList()), "terminate");
        System.out.println("Going to terminate " + found.getResults().size() + " workflows");

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, false);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        //Start two workflows. Only first workflow task should be in_progress
        String workflowId1 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId2 = workflowClient.startWorkflow(startWorkflowRequest);

        Workflow workflow1 = workflowClient.getWorkflow(workflowId1, true);
        Workflow workflow2 = workflowClient.getWorkflow(workflowId1, true);

        // Assertions
        Assertions.assertEquals(workflow1.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow1.getTasks().size(), 1);
        Assertions.assertEquals(workflow2.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow2.getTasks().size(), 1);

        Task task1 = taskClient.pollTask(taskName, "test", null);
        Task task2 = taskClient.pollTask(taskName, "test", null);

        // Task2 should be null.
        Task finalTask = task2;
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            assertNull(finalTask);
            assertNotNull(task1);
        });

        TaskResult taskResult = new TaskResult();
        taskResult.setTaskId(task1.getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setWorkflowInstanceId(task1.getWorkflowInstanceId());
        taskClient.updateTask(taskResult);

        // Task2 should not be pollable still. It should be available only after 10 seconds.
        task2 = taskClient.pollTask(taskName, "test", null);
        assertNull(task2);

        Uninterruptibles.sleepUninterruptibly(13, TimeUnit.SECONDS);
        // Task2 should be available to poll
        task2 = taskClient.pollTask(taskName, "test", null);
        assertNotNull(task2);
        taskResult = new TaskResult();
        taskResult.setTaskId(task2.getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setWorkflowInstanceId(task2.getWorkflowInstanceId());
        taskClient.updateTask(taskResult);

        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            // Assert both workflows completed
            assertEquals(workflowClient.getWorkflow(workflowId1, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
            assertEquals(workflowClient.getWorkflow(workflowId2, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
        });
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by name")
    public void testConcurrentExeclimit() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "concurrency-limit-test";
        String taskName = "task-concurrency-limit-test";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        workflowClient.terminateWorkflow(found.getResults().stream().map(s ->s.getWorkflowId()).collect(Collectors.toList()), "terminate");
        System.out.println("Going to terminate " + found.getResults().size() + " workflows of type: " + workflowName);
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, true);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        //Start two workflows. Only first workflow task should be in_progress
        String workflowId1 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId2 = workflowClient.startWorkflow(startWorkflowRequest);

        Workflow workflow1 = workflowClient.getWorkflow(workflowId1, true);
        Workflow workflow2 = workflowClient.getWorkflow(workflowId1, true);

        // Assertions
        Assertions.assertEquals(workflow1.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow1.getTasks().size(), 1);
        Assertions.assertEquals(workflow2.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow2.getTasks().size(), 1);

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

        Task task1 = taskClient.pollTask(taskName, "test", null);
        assertNotNull(task1);

        Task task2 = taskClient.pollTask(taskName, "test", null);
        assertNull(task2);


        TaskResult taskResult = new TaskResult();
        taskResult.setTaskId(task1.getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setWorkflowInstanceId(task1.getWorkflowInstanceId());
        taskClient.updateTask(taskResult);
        workflowClient.runDecider(task1.getWorkflowInstanceId());
        Uninterruptibles.sleepUninterruptibly(66, TimeUnit.SECONDS);

        // Task2 should not be pollable still. It should be available only after 10 seconds.
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            Task task3 = taskClient.pollTask(taskName, "test", null);
            assertNotNull(task3);
            TaskResult taskResult1 = new TaskResult();
            taskResult1.setTaskId(task3.getTaskId());
            taskResult1.setStatus(TaskResult.Status.COMPLETED);
            taskResult1.setWorkflowInstanceId(task3.getWorkflowInstanceId());
            taskClient.updateTask(taskResult1);
        });
        workflowClient.runDecider(task1.getWorkflowInstanceId());

        assertEquals(workflowClient.getWorkflow(workflowId1, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
        assertEquals(workflowClient.getWorkflow(workflowId2, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by correlationId")
    public void testRateLimitByWorkflowCorrelationId() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "rate-limit-by-correlationId";
        String taskName = "rate-limit-task-by-correlationId";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        workflowClient.terminateWorkflow(found.getResults().stream().map(s ->s.getWorkflowId()).collect(Collectors.toList()), "terminate");

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, false);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey("${workflow.correlationId}");
        tagObject.setValue(3); // Only 3 invocations are allowed for same correlationId
        metadataClient.addWorkflowTag(tagObject, workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setCorrelationId("rate_limited");
        startWorkflowRequest.setName(workflowName);
        String workflowId1 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId2 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId3 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId4 = workflowClient.startWorkflow(startWorkflowRequest);
        // Trigger workflow5 without correlationId. It should not get rate limited.
        startWorkflowRequest.setCorrelationId("");
        String workflowId5 = workflowClient.startWorkflow(startWorkflowRequest);

        Workflow workflow1 = workflowClient.getWorkflow(workflowId1, true);
        Workflow workflow2 = workflowClient.getWorkflow(workflowId2, true);
        Workflow workflow3 = workflowClient.getWorkflow(workflowId3, true);
        AtomicReference<Workflow> workflow4 = new AtomicReference<>(workflowClient.getWorkflow(workflowId4, true));
        AtomicReference<Workflow> workflow5 = new AtomicReference<>(workflowClient.getWorkflow(workflowId5, true));

        // Assertions
        Assertions.assertEquals(workflow1.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow2.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow3.getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow4.get().getStatus(), Workflow.WorkflowStatus.RUNNING);
        Assertions.assertEquals(workflow5.get().getStatus(), Workflow.WorkflowStatus.RUNNING);
        // Workflow4 and workflow5 tasks should not get scheduled.
        Assertions.assertEquals(workflow1.getTasks().size(), 1);
        Assertions.assertEquals(workflow2.getTasks().size(), 1);
        Assertions.assertEquals(workflow3.getTasks().size(), 1);
        Assertions.assertEquals(workflow4.get().getTasks().size(), 0);
        Assertions.assertEquals(0, workflow5.get().getTasks().size());

        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId1);
        taskResult.setTaskId(workflow1.getTasks().get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Now workflow4 task get scheduled. Workflow5 tasks should not get scheduled.
        // Wait for 1 second to let sweeper run
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            workflow4.set(workflowClient.getWorkflow(workflowId4, true));
            assertEquals(workflow4.get().getTasks().size(), 1);
        });
    }

    private static void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient, boolean isExecLimit) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);
        if (isExecLimit) {
            taskDef.setConcurrentExecLimit(1);
        } else {
            taskDef.setRateLimitPerFrequency(1);
            taskDef.setRateLimitFrequencyInSeconds(10);
        }

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName(taskName);
        simpleTask.setName(taskName);
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);
        simpleTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(simpleTask));
        metadataClient.updateWorkflowDefs(Arrays.asList(workflowDef));
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));
    }
}
