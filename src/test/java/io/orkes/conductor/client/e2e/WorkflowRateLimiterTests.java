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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowRateLimiterTests {

//    @AfterEach
//    public void tearDown() {
//        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
//        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
//        while (true) {
//            try {
//                SearchResult<WorkflowSummary> found = workflowClient.search("name IN (test_100_wf)");
//                if (found.getResults().size() == 0) break;
//                found.getResults().forEach(workflowSummary -> {
//                    workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate after test");
//                    workflowClient.deleteWorkflow(workflowSummary.getWorkflowId(), false);
//                    System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
//                });
//            } catch (Exception e) {
//                System.out.println("error " + e);
//                break;
//            }
//        }
//    }

    @Test
    @DisplayName("Check workflow with simple rate limit by workflow name")
    public void testRateLimitByWorkflowName() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey(workflowName);
        tagObject.setValue(3); // Only 3 invocations are allowed.
        metadataClient.addWorkflowTag(tagObject, workflowName);

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
            } catch (Exception e) {
            }
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
        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef(taskName);
    }

    @Test
    @DisplayName("Check 100 workflow with simple rate limit by workflow name")
    public void testRateLimitByWorkflowNameWith100Workflows() {
        // RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        ;
        String taskName = "test_task_100";

        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch (Exception e) {
            }
        });

        // Register workflow
        registerWorkflowDef(workflowName, taskName, metadataClient);
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey(workflowName);
        final int limit = 2;
        tagObject.setValue(limit); // Only 2 invocations are allowed.
        metadataClient.addWorkflowTag(tagObject, workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);

//        String wId1 = workflowClient.startWorkflow(startWorkflowRequest);
//        String wId2 = workflowClient.startWorkflow(startWorkflowRequest);
//
//        Workflow workflow1 = workflowClient.getWorkflow(wId1, true);
//        Workflow workflow2 = workflowClient.getWorkflow(wId2, true);
//
//        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow1.getStatus());
//        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow2.getStatus());
//
//        assertEquals(1, workflow1.getTasks().size());
//        assertEquals(1, workflow2.getTasks().size());

        final int N = 100;

        List<String> workflowIds = new ArrayList<>(N);
        for (int i = 0; i < N; ++i) {
            String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
            workflowIds.add(workflowId);
        }

        List<AtomicReference<Workflow>> workflows = new ArrayList<>(N);
        for (int i = 0; i < N; ++i) {
            Workflow workflow = workflowClient.getWorkflow(workflowIds.get(i), true);
            workflows.add(new AtomicReference<>(workflow));
        }
//
        for (int i = 0; i < N; ++i) {
            Assertions.assertEquals(Workflow.WorkflowStatus.RUNNING, workflows.get(i).get().getStatus());
        }
//
//        System.out.println(getWorkflowsRunningStatus(workflowClient, workflowIds));
//

        for (int i = 0; i < limit; ++i) {
            Assertions.assertEquals(1, workflows.get(0).get().getTasks().size());
            Assertions.assertEquals(1, workflows.get(1).get().getTasks().size());
        }

        for (int i = limit; i < 100; ++i) {
            Assertions.assertEquals(workflows.get(i).get().getTasks().size(), 0);
        }

        for (int i = limit; i < 16; ++i) {
            TaskResult taskResult = new TaskResult();
            taskResult.setWorkflowInstanceId(workflowIds.get(i - limit));
            taskResult.setTaskId(workflows.get(i - limit).get().getTasks().get(0).getTaskId());
            taskResult.setStatus(TaskResult.Status.COMPLETED);
            taskClient.updateTask(taskResult);

//            System.out.println("sec " + getWorkflowsRunningStatus(workflowClient, workflowIds));

            final int id = i;

            await().atMost(3, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).untilAsserted(() -> {
                try {
                        String workflowId = workflowIds.get(id);
                        workflows.get(id).set(workflowClient.getWorkflow(workflowId, true));
                        assertEquals(1, workflows.get(id).get().getTasks().size());

                    if (id + 1 < N) {
                        workflowId = workflowIds.get(id + 1);
                        workflows.get(id + 1).set(workflowClient.getWorkflow(workflowId, true));
                        String message = workflows.get(id + 1).get().getTasks().get(0).getStatus().name();
                        assertEquals(0, workflows.get(id + 1).get().getTasks().size(), message);
                    }
                } catch (Exception e) {
                }
            });
        }

        // clean up resource
        SearchResult<WorkflowSummary> found1 = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        found1.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch (Exception e) {
            }
        });
    }

    private String getWorkflowsRunningStatus(WorkflowClient workflowClient, List<String> workflowIds) {
        String running = "running";
        String idle = "idle";
        Map<String, Integer> count = new HashMap<>();
        count.put(running, 0);
        count.put(idle, 0);

        for (String id : workflowIds) {
            Workflow workflow = workflowClient.getWorkflow(id, true);
            int n = workflow.getTasks().size();
            if (n > 0) {
                count.put(running, count.get(running) + 1);
            } else {
                count.put(idle, count.get(idle) + 1);
            }
        }

        return running + ": " + count.get(running) + ", " + idle + ": " + count.get(idle);
    }

    @Test
    @DisplayName("Check workflow with simple rate limit by correlationId")
    public void testRateLimitByWorkflowCorrelationId() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
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
        await().atMost(41, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                workflow4.set(workflowClient.getWorkflow(workflowId4, true));
                assertEquals(workflow4.get().getTasks().size(), 1);
            } catch (Exception e) {
            }
        });
        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef(taskName);
    }

    private static void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);
//        taskDef.setTimeoutSeconds(0);

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
}
