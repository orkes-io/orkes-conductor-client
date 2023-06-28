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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.Http;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.TestUtil;

import com.google.common.util.concurrent.Uninterruptibles;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowClientTests extends ClientTest {
    private static WorkflowClient workflowClient;
    private static MetadataClient metadataClient;
    private static WorkflowExecutor workflowExecutor;

    @BeforeAll
    public static void setup() {
        workflowClient = orkesClients.getWorkflowClient();
        metadataClient = orkesClients.getMetadataClient();
        workflowExecutor = new WorkflowExecutor(
                orkesClients.getTaskClient(),
                orkesClients.getWorkflowClient(),
                orkesClients.getMetadataClient(),
                10);
    }

    @Test
    public void startWorkflow() {
        String workflowId = workflowClient.startWorkflow(getStartWorkflowRequest());
        Workflow workflow = workflowClient.getWorkflow(workflowId, false);
        assertTrue(workflow.getWorkflowName().equals(Commons.WORKFLOW_NAME));
    }

    @Test
    public void testSearchByCorrelationIds() {
        List<String> correlationIds = new ArrayList<>();
        Set<String> workflowNames = new HashSet<>();
        Map<String, Set<String>> correlationIdToWorkflows = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            String correlationId = UUID.randomUUID().toString();
            correlationIds.add(correlationId);
            for (int j = 0; j < 5; j++) {
                ConductorWorkflow<Object> workflow = new ConductorWorkflow<>(workflowExecutor);
                workflow.add(new Http("http").url("https://orkes-api-tester.orkesconductor.com/get"));
                workflow.setName("workflow_" + j);
                workflowNames.add(workflow.getName());
                StartWorkflowRequest request = new StartWorkflowRequest();
                request.setName(workflow.getName());
                request.setWorkflowDef(workflow.toWorkflowDef());
                request.setCorrelationId(correlationId);
                String id = workflowClient.startWorkflow(request);
                System.out.println("started " + id);
                Set<String> ids = correlationIdToWorkflows.getOrDefault(correlationId, new HashSet<>());
                ids.add(id);
                correlationIdToWorkflows.put(correlationId, ids);
            }
        }
        // Let's give couple of seconds for indexing to complete
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        Map<String, List<Workflow>> result = workflowClient.getWorkflowsByNamesAndCorrelationIds(correlationIds,
                workflowNames.stream().collect(Collectors.toList()), true, false);
        assertNotNull(result);
        assertEquals(correlationIds.size(), result.size());
        for (String correlationId : correlationIds) {
            assertEquals(5, result.get(correlationId).size());
            Set<String> ids = result.get(correlationId).stream().map(wf -> wf.getWorkflowId())
                    .collect(Collectors.toSet());
            assertEquals(correlationIdToWorkflows.get(correlationId), ids);
        }
    }

    @Test
    public void testWorkflowMethods() throws Exception {
        var workflowId = workflowClient.startWorkflow(getStartWorkflowRequest());
        var workflows = (List<Workflow>) TestUtil.retryMethodCall(
                () -> workflowClient.getWorkflows(
                        Commons.WORKFLOW_NAME, "askdjbjqhbdjqhbdjqhsbdjqhsbd", false, false));
        assertTrue(workflows.isEmpty());
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflow(workflowId, "reason"));
        TestUtil.retryMethodCall(
                () -> workflowClient.retryLastFailedTask(workflowId));
        TestUtil.retryMethodCall(
                () -> workflowClient.getRunningWorkflow(Commons.WORKFLOW_NAME, Commons.WORKFLOW_VERSION));
        TestUtil.retryMethodCall(
                () -> workflowClient.getWorkflowsByTimePeriod(
                        Commons.WORKFLOW_NAME, Commons.WORKFLOW_VERSION, 0L, 0L));
        TestUtil.retryMethodCall(
                () -> workflowClient.search(2, 5, "", "", Commons.WORKFLOW_NAME));
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflows(List.of(workflowId), "reason"));
        TestUtil.retryMethodCall(
                () -> workflowClient.restart(workflowId, true));
        try {
            TestUtil.retryMethodCall(
                    () -> workflowClient.skipTaskFromWorkflow(workflowId, Commons.TASK_NAME));
        } catch (ApiException e) {
            assertEquals(e.getCode(), "500");
        }
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflow(List.of(workflowId), "reason"));
        TestUtil.retryMethodCall(
                () -> workflowClient.restartWorkflow(List.of(workflowId), true));
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflow(workflowId, "reason"));
        TestUtil.retryMethodCall(
                () -> workflowClient.retryWorkflow(List.of(workflowId)));
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflow(workflowId, "reason"));
        TestUtil.retryMethodCall(
                () -> workflowClient.rerunWorkflow(workflowId, new RerunWorkflowRequest()));
        TestUtil.retryMethodCall(
                () -> workflowClient.pauseWorkflow(workflowId));
        TestUtil.retryMethodCall(
                () -> workflowClient.resumeWorkflow(workflowId));
        TestUtil.retryMethodCall(
                () -> workflowClient.pauseWorkflow(workflowId));
        TestUtil.retryMethodCall(
                () -> workflowClient.resumeWorkflow(workflowId));
        TestUtil.retryMethodCall(
                () -> workflowClient.pauseWorkflow(List.of(workflowId)));
        TestUtil.retryMethodCall(
                () -> workflowClient.resumeWorkflow(List.of(workflowId)));
        TestUtil.retryMethodCall(
                () -> workflowClient.deleteWorkflow(workflowId, false));
        TestUtil.retryMethodCall(
                () -> workflowClient.search(Commons.WORKFLOW_NAME));
        TestUtil.retryMethodCall(
                () -> workflowClient.runDecider(workflowId));
    }

    @Test
    public void testWorkflowTerminate() {
        String workflowId = workflowClient.startWorkflow(getStartWorkflowRequest());
        workflowClient.terminateWorkflowWithFailure(
                workflowId, "testing out some stuff", true);
        var workflow = workflowClient.getWorkflow(workflowId, false);
        assertEquals(Workflow.WorkflowStatus.TERMINATED, workflow.getStatus());
    }

    @Test
    public void testSkipTaskFromWorkflow() throws Exception {
        var workflowName = "random_workflow_name_1hqiuwheiquwhe";
        var taskName1 = "random_task_name_1hqiuwheiquwheajnsdsand";
        var taskName2 = "random_task_name_1hqiuwheiquwheajnsdsandjsadh";

        var taskDef1 = new TaskDef(taskName1);
        taskDef1.setRetryCount(0);
        taskDef1.setOwnerEmail("test@orkes.io");
        var taskDef2 = new TaskDef(taskName2);
        taskDef2.setRetryCount(0);
        taskDef2.setOwnerEmail("test@orkes.io");

        TestUtil.retryMethodCall(
                () -> metadataClient.registerTaskDefs(List.of(taskDef1, taskDef2)));

        var wf = new ConductorWorkflow<>(workflowExecutor);
        wf.setName(workflowName);
        wf.setVersion(1);
        wf.add(new SimpleTask(taskName1, taskName1));
        wf.add(new SimpleTask(taskName2, taskName2));
        TestUtil.retryMethodCall(
                () -> wf.registerWorkflow(true));

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(new HashMap<>());
        var workflowId = (String) TestUtil.retryMethodCall(
                () -> workflowClient.startWorkflow(startWorkflowRequest));
        System.out.println("workflowId: " + workflowId);

        TestUtil.retryMethodCall(
                () -> workflowClient.skipTaskFromWorkflow(workflowId, taskName2));
        TestUtil.retryMethodCall(
                () -> workflowClient.terminateWorkflowsWithFailure(List.of(workflowId), null, false));
    }

    public void testUpdateVariables() {
        ConductorWorkflow<Object> workflow = new ConductorWorkflow<>(workflowExecutor);
        workflow.add(new SimpleTask("simple_task", "simple_task_ref"));
        workflow.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflow.setTimeoutSeconds(60);
        workflow.setName("update_variable_test");
        workflow.setVersion(1);
        workflow.registerWorkflow(true, true);

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName(workflow.getName());
        request.setVersion(workflow.getVersion());
        request.setInput(Map.of());
        String workflowId = workflowClient.startWorkflow(request);
        assertNotNull(workflowId);

        Workflow execution = workflowClient.getWorkflow(workflowId, false);
        assertNotNull(execution);
        assertTrue(execution.getVariables().isEmpty());

        Map<String, Object> variables = Map.of("k1", "v1", "k2", 42, "k3", Arrays.asList(3, 4, 5));
        execution = workflowClient.updateVariables(workflowId, variables);
        assertNotNull(execution);
        assertFalse(execution.getVariables().isEmpty());
        assertEquals(variables.get("k1"), execution.getVariables().get("k1"));
        assertEquals(variables.get("k2").toString(), execution.getVariables().get("k2").toString());
        assertEquals(variables.get("k3").toString(), execution.getVariables().get("k3").toString());

        Map<String, Object> map = new HashMap<>();
        map.put("k1", null);
        map.put("v1", "xyz");
        execution = workflowClient.updateVariables(workflowId, map);
        assertNotNull(execution);
        assertFalse(execution.getVariables().isEmpty());
        assertEquals(null, execution.getVariables().get("k1"));
        assertEquals(variables.get("k2").toString(), execution.getVariables().get("k2").toString());
        assertEquals(variables.get("k3").toString(), execution.getVariables().get("k3").toString());
        assertEquals("xyz", execution.getVariables().get("v1").toString());
    }

    @Test
    void testUnsupportedMethods() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    workflowClient.resetCallbacksForInProgressTasks("");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    workflowClient.searchV2("");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    workflowClient.searchV2(0, 0, "", "", "");
                });
    }

    @Test
    void testExecuteWorkflow() {
        // TODO
    }

    StartWorkflowRequest getStartWorkflowRequest() {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(Commons.WORKFLOW_NAME);
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(new HashMap<>());
        return startWorkflowRequest;
    }
}
