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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.sdk.examples.ApiUtil;

import com.google.common.util.concurrent.Uninterruptibles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowRateLimiterTests {

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
    @DisplayName("Check workflow with simple rate limit by workflow name")
    public void testRateLimitByWorkflowName() {
        String workflowName = "workflow-rate-limit-by-name";
        String taskName = "task-rate-limit-by-name";
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey(workflowName);
        tagObject.setValue(3); // Only 3 invocations are allowed.
        metadataClient.addWorkflowTag(tagObject, workflowName);

        terminateExistingRunningWorkflows(workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        String workflowId1 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId2 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId3 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId4 = workflowClient.startWorkflow(startWorkflowRequest);
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
        Assertions.assertEquals(workflow5.get().getTasks().size(), 0);

        // Complete workflow1.
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId1);
        taskResult.setTaskId(workflow1.getTasks().get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Now workflow4 task get scheduled. Workflow5 tasks should not get scheduled.
        await().atMost(33, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                workflow4.set(workflowClient.getWorkflow(workflowId4, true));
                assertEquals(workflow4.get().getTasks().size(), 1);
                workflow5.set(workflowClient.getWorkflow(workflowId5, true));
                assertEquals(workflow5.get().getTasks().size(), 0);
            }catch(Exception e) {}
        });

        // Complete workflow2
        taskResult.setWorkflowInstanceId(workflowId2);
        taskResult.setTaskId(workflow2.getTasks().get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            workflow5.set(workflowClient.getWorkflow(workflowId5, true));
            assertEquals(workflow4.get().getTasks().size(), 1);
        });
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by correlationId")
    public void testRateLimitByWorkflowCorrelationId() {
        String workflowName = "workflow-rate-limit-by-correlationId";
        String taskName = "task-rate-limit-by-correlationId";
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
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
        // Triger workflow5 without correlationId. It should not get rate limited.
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
        Assertions.assertEquals(workflow5.get().getTasks().size(), 1);

        // Complete workflow1.
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId1);
        taskResult.setTaskId(workflow1.getTasks().get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

        // Now workflow4 task get scheduled. Workflow5 tasks should not get scheduled.
        // Wait for 1 second to let sweeper run
        await().atMost(41, TimeUnit.SECONDS).pollInterval(1,TimeUnit.SECONDS).untilAsserted(() -> {
             try {
                 workflow4.set(workflowClient.getWorkflow(workflowId4, true));
                 assertEquals(workflow4.get().getTasks().size(), 1);
             }catch(Exception e){}
        });
    }

    private static void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName(taskName);
        simpleTask.setName(taskName);
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);
        simpleTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(simpleTask));
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by workflow name with large value")
    public void testRateLimitByWorkflowNameForLargeValue() {
        String workflowName = "workflow-rate-limit-by-name-high-value";
        String taskName = "task-rate-limit-by-name-high-value";
        int total_workflows = 1000;
        int allowed_workflows = 100;
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey(workflowName);
        tagObject.setValue(allowed_workflows);
        metadataClient.addWorkflowTag(tagObject, workflowName);

        terminateExistingRunningWorkflows(workflowName);
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setCorrelationId("rate_limited");
        startWorkflowRequest.setName(workflowName);
        List<String> workflowIds = new ArrayList<>();
        // Start 100 workflow.
        for(int i=0;i<total_workflows;i++) {
            workflowIds.add(workflowClient.startWorkflow(startWorkflowRequest));
        }

        int iteration = total_workflows / allowed_workflows;

        for(int i=0; i<iteration ; i++) {

            //Only 100 workflows should get slot.
            int finalI = i;
            AtomicInteger scheduled_task_workflow = new AtomicInteger();
            AtomicInteger non_scheduled_task_workflow = new AtomicInteger();
            List<Pair> list = new ArrayList<>();
            await().atMost(200, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).untilAsserted(() -> {
                scheduled_task_workflow.set(0);
                non_scheduled_task_workflow.set(0);
                list.clear();
                List<Workflow> workflows = new ArrayList<>();
                for (int j = finalI *allowed_workflows; j < total_workflows; j++) {
                    workflows.add(workflowClient.getWorkflow(workflowIds.get(j), true));
                }
                workflows.stream().forEach(workflow -> {
                    if (workflow.getTasks().size() == 1) {
                        scheduled_task_workflow.getAndIncrement();
                        list.add(new Pair(workflow.getWorkflowId(), workflow.getTasks().get(0).getTaskId()));
                    } else {
                        non_scheduled_task_workflow.getAndIncrement();
                    }
                });

                assertEquals(total_workflows - allowed_workflows*(finalI+1) , non_scheduled_task_workflow.get());
                assertEquals(allowed_workflows, scheduled_task_workflow.get());
                list.stream().forEach(pair -> {
                    TaskResult taskResult = new TaskResult();
                    taskResult.setStatus(TaskResult.Status.COMPLETED);
                    taskResult.setWorkflowInstanceId(pair.getName());
                    taskResult.setTaskId(pair.getValue());
                    taskClient.updateTask(taskResult);
                });
            });

            //Sleep 5 seconds
            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
            // Do not run for last iteration.
            if (i < iteration - 1) {
                //Fetch top 10 workflows and run decider on that to speed up the test.
                for (int j = (i+1) * allowed_workflows; j < allowed_workflows * (i + 2); j++) {
                    workflowClient.runDecider(workflowIds.get(j));
                }
            }
        }
    }

    @Test
    @DisplayName("Check rate limit in workflow failure scenario")
    public void testRateLimitByFailedWorkflowCase() {
        String workflowName = "workflow-rate-limit-failure-scenario";
        String taskName = "task-rate-limit-failure-scenario";
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey(workflowName);
        tagObject.setValue(2); // Only 2 invocations are allowed.
        metadataClient.addWorkflowTag(tagObject, workflowName);

        terminateExistingRunningWorkflows(workflowName);
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setCorrelationId("rate_limited");
        startWorkflowRequest.setName(workflowName);

        //Start three workflows and failed the second one.
        String workflowId1 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId2 = workflowClient.startWorkflow(startWorkflowRequest);
        String workflowId3 = workflowClient.startWorkflow(startWorkflowRequest);
        await().atMost(3, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId1, true);
            assertEquals(1, workflow1.getTasks().size());
            Workflow workflow2 = workflowClient.getWorkflow(workflowId2, true);
            assertEquals(1, workflow2.getTasks().size());
            Workflow workflow3 = workflowClient.getWorkflow(workflowId3, true);
            assertEquals(0, workflow3.getTasks().size());
        });

        workflowClient.terminateWorkflow(workflowId2, "Terminated");
        // Workflow 3 should get chance.
        await().atMost(2, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow3 = workflowClient.getWorkflow(workflowId3, true);
            assertEquals(1, workflow3.getTasks().size());
        });
    }

    private void terminateExistingRunningWorkflows(String workflowName) {
        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search(" status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch(Exception e){}
        });
    }
}
