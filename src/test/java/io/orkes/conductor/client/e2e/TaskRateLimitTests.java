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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.sdk.examples.ApiUtil;

import com.google.common.util.concurrent.Uninterruptibles;

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
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch(Exception e){}
        });

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, false, false);

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

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Task task1 = taskClient.pollTask(taskName, "test", null);
            Task task2 = taskClient.pollTask(taskName, "test", null);
            assertNotNull(task1);
            assertNull(task2);
            TaskResult taskResult = new TaskResult();
            taskResult.setTaskId(task1.getTaskId());
            taskResult.setStatus(TaskResult.Status.COMPLETED);
            taskResult.setWorkflowInstanceId(task1.getWorkflowInstanceId());
            taskClient.updateTask(taskResult);
        });

        Uninterruptibles.sleepUninterruptibly(13, TimeUnit.SECONDS);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(1, taskClient.getQueueSizeForTask(taskName));
        });
        // Task2 should be available to poll
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Task task2 = taskClient.pollTask(taskName, "test", null);
            assertNotNull(task2);
            TaskResult taskResult = new TaskResult();
            taskResult.setTaskId(task2.getTaskId());
            taskResult.setStatus(TaskResult.Status.COMPLETED);
            taskResult.setWorkflowInstanceId(task2.getWorkflowInstanceId());
            taskClient.updateTask(taskResult);
            // Assert both workflows completed
            assertEquals(workflowClient.getWorkflow(workflowId1, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
            assertEquals(workflowClient.getWorkflow(workflowId2, false).getStatus(), Workflow.WorkflowStatus.COMPLETED);
        });
    }

    @Test
    @DisplayName("Check workflow with concurrent exec limit")
    public void testConcurrentExeclimit() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "concurrency-limit-test";
        String taskName = "task-concurrency-limit-test";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch(Exception e){}
        });
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, true, false);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        //Start 10 workflows. Only 5 workflows task should be scheduled.
        List<String> workflowIds = new ArrayList<>();
        for(int i=0;i<10;i++) {
            workflowIds.add(workflowClient.startWorkflow(startWorkflowRequest));
        }

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Task> tasks = taskClient.batchPollTasksByTaskType(taskName, "test", 10, 1000);
            assertEquals(tasks.size(), 5);
            for(int j=0;j<5;j++) {
                TaskResult taskResult = new TaskResult();
                taskResult.setTaskId(tasks.get(j).getTaskId());
                taskResult.setStatus(TaskResult.Status.COMPLETED);
                taskResult.setWorkflowInstanceId(tasks.get(0).getWorkflowInstanceId());
                taskClient.updateTask(taskResult);
            }
        });

        Uninterruptibles.sleepUninterruptibly(60, TimeUnit.SECONDS);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Task> tasks = taskClient.batchPollTasksByTaskType(taskName, "test", 10, 1000);
            assertEquals(tasks.size(), 5);
            for(int j=0;j<5;j++) {
                TaskResult taskResult = new TaskResult();
                taskResult.setTaskId(tasks.get(j).getTaskId());
                taskResult.setStatus(TaskResult.Status.COMPLETED);
                taskResult.setWorkflowInstanceId(tasks.get(0).getWorkflowInstanceId());
                taskClient.updateTask(taskResult);
            }
        });
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
        registerWorkflowDef(workflowName, taskName, metadataClient, false, false);
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

    private static void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient, boolean isExecLimit, boolean multivalue) {
        try {
            if (metadataClient.getWorkflowDef(workflowName, 1) != null && metadataClient.getTaskDef(taskName) != null) {
                return;
            }
        }catch (Exception e){}
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);
        if (isExecLimit) {
            taskDef.setConcurrentExecLimit(5);
        } else {
            if (multivalue) {
                taskDef.setRateLimitPerFrequency(2);
                taskDef.setRateLimitFrequencyInSeconds(20);
            } else {
                taskDef.setRateLimitPerFrequency(1);
                taskDef.setRateLimitFrequencyInSeconds(10);
            }
        }

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName(taskName);
        simpleTask.setName(taskName);
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(simpleTask));
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by name for multiple iteration")
    public void testRateLimitByPerFrequencyForMultipleIteration() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "task-rate-limit-test-multiple-iteration";
        String taskName = "rate-limited-task-multiple-iteration";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch(Exception e){}
        });

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, false, true);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        //Start two workflows. Only first workflow task should be in_progress

        List<String> workflowIds = new ArrayList<>();
        for(int i=0;i<20;i++) {
            workflowIds.add(workflowClient.startWorkflow(startWorkflowRequest));
        }

        for(int i =0;i<10;i++) {

            await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
                List<Task> tasks = taskClient.batchPollTasksByTaskType(taskName, "test", 5, 1000);
                assertEquals(tasks.size(), 2);
                TaskResult taskResult = new TaskResult();
                taskResult.setTaskId(tasks.get(0).getTaskId());
                taskResult.setStatus(TaskResult.Status.COMPLETED);
                taskResult.setWorkflowInstanceId(tasks.get(0).getWorkflowInstanceId());
                taskClient.updateTask(taskResult);
                taskResult = new TaskResult();
                taskResult.setTaskId(tasks.get(1).getTaskId());
                taskResult.setStatus(TaskResult.Status.COMPLETED);
                taskResult.setWorkflowInstanceId(tasks.get(1).getWorkflowInstanceId());
                taskClient.updateTask(taskResult);
            });

            if (i!=9) {
                // Do not sleep after iteration
                Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            }
        }
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by name to validate limit")
    public void testRateLimitByPerFrequencyToValidateLimitSligingWindow() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "task-rate-limit-test-sliding-window";
        String taskName = "rate-limited-task-sliding-window";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch(Exception e){}
        });

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient, false, true);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        //Start two workflows. Only first workflow task should be in_progress

        //Start a workflow and poll for a task.
        workflowClient.startWorkflow(startWorkflowRequest);
        List<Task> tasks = taskClient.batchPollTasksByTaskType(taskName, "test", 5, 1000);
        assertEquals(1, tasks.size());

        //Sleep for 7 seconds and start another workflow and poll for the task.
        Uninterruptibles.sleepUninterruptibly(7, TimeUnit.SECONDS);
        workflowClient.startWorkflow(startWorkflowRequest);
        List<Task> tasks2 = taskClient.batchPollTasksByTaskType(taskName, "test", 5, 1000);
        assertEquals(1, tasks2.size());

        //Complete both the workflow.
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskId(tasks.get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setWorkflowInstanceId(tasks.get(0).getWorkflowInstanceId());

        taskClient.updateTask(taskResult);
        taskResult = new TaskResult();
        taskResult.setTaskId(tasks2.get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setWorkflowInstanceId(tasks2.get(0).getWorkflowInstanceId());
        taskClient.updateTask(taskResult);

        // Sleep for 3 seconds to move the tick to next window.
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        //Start two workflow and poll for that. Both tasks should not be available to poll.
        workflowClient.startWorkflow(startWorkflowRequest);
        workflowClient.startWorkflow(startWorkflowRequest);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            //Tasks should not be poll able since the window is not over yet.
            assertEquals(0, taskClient.batchPollTasksByTaskType(taskName, "test", 5, 1000).size());
        });
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Task> tasks3 = taskClient.batchPollTasksByTaskType(taskName, "test", 5, 1000);
            assertTrue(tasks3.size() > 0);
            tasks3.stream().forEach(task -> {
                TaskResult taskResult1 = new TaskResult();
                taskResult1.setTaskId(task.getTaskId());
                taskResult1.setStatus(TaskResult.Status.COMPLETED);
                taskResult1.setWorkflowInstanceId(task.getWorkflowInstanceId());
                taskClient.updateTask(taskResult1);
            });
        });
    }
}
