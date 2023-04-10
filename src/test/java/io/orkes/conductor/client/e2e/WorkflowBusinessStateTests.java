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

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
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
import io.orkes.conductor.client.model.BusinessStateSchema;
import io.orkes.conductor.client.model.ExtendedWorkflowDef;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.util.ApiUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.util.concurrent.Uninterruptibles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowBusinessStateTests {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static TaskClient taskClient;
    static OrkesMetadataClient metadataClient;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }
    @Test
    @DisplayName("Check business state is setting task input correctly along with business state schema")
    public void test1() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onScheduled");

        String workflowId = getWorkflowId(workflowName);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());
        Task task = workflow.getTasks().stream().filter(task1 -> "postgres_sql_updates".equals(task1.getTaskType())).collect(Collectors.toList()).get(0);
        assertCommonAttributes(task);
        workflowClient.terminateWorkflow(workflowId, "Terminated");
    }

    @Test
    @DisplayName("Check update business state schema is working fine")
    public void test2() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onScheduled");

        BusinessStateSchema actualBusinessStateSchema = new BusinessStateSchema();
        Map<String, Map<String, Object>> schema = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("dbName", "order_updates");
        map.put("_schema", Map.of("orderValue", "order_value_inr", "status", "order_status_string"));
        schema.put("postgres_sql_updates",map);
        actualBusinessStateSchema.setSchema(schema);
        metadataClient.updateWorkflowBusinessStateSchema(workflowName, actualBusinessStateSchema);
        await().atMost(5, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                Map<String, Map<String, Object>> expectedBusinessStateSchema = metadataClient.getWorkflowBusinessStateSchema(workflowName);
                assertEquals(expectedBusinessStateSchema, actualBusinessStateSchema);
            }catch(Exception e) {}
        });

    }

    @Test
    @DisplayName("Check delete business state schema is working fine")
    public void test3() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onScheduled");
        metadataClient.deleteWorkflowBusinessStateSchema(workflowName, "postgres_sql_updates");
        await().atMost(5, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                Map<String, Map<String, Object>> expectedBusinessStateSchema = metadataClient.getWorkflowBusinessStateSchema(workflowName);
                assertNull(expectedBusinessStateSchema);
            }catch(Exception e) {}
        });
    }

    @Test
    @DisplayName("Check onStart event is working correctly")
    public void test4() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onStart");

        String workflowId = getWorkflowId(workflowName);

        Task task = taskClient.pollTask(taskName, "test_worker", null);
        assertNotNull(task);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());
        task = workflow.getTasks().stream().filter(task1 -> "postgres_sql_updates".equals(task1.getTaskType())).collect(Collectors.toList()).get(0);
        assertCommonAttributes(task);
        workflowClient.terminateWorkflow(workflowId, "Terminated");
    }

    @Test
    @DisplayName("Check onFailed event is working correctly")
    public void test5() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onFailed");

        String workflowId = getWorkflowId(workflowName);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(1, workflow.getTasks().size());

        //Fail the first task
        taskClient.updateTask(getTaskResult(workflow.getTasks().get(0), TaskResult.Status.FAILED));


        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());

        assertCommonAttributes(workflow.getTasks().get(1));
        workflowClient.terminateWorkflow(workflowId, "Terminated");
    }

    @Test
    @DisplayName("Check onCompleted event is working correctly")
    public void test6() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onSuccess");

        String workflowId = getWorkflowId(workflowName);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(1, workflow.getTasks().size());

        //Fail the first task
        taskClient.updateTask(getTaskResult(workflow.getTasks().get(0), TaskResult.Status.COMPLETED));

        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());

        assertCommonAttributes(workflow.getTasks().get(1));
    }

    @Test
    @DisplayName("Check onCancelled event is working correctly")
    public void test7() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onCancelled");

        String workflowId = getWorkflowId(workflowName);

        workflowClient.terminateWorkflow(workflowId, "Terminated");

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());

        assertCommonAttributes(workflow.getTasks().get(1));
    }

    @Test
    @DisplayName("Check onTimedout event is working correctly")
    public void test9() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onFailed");
        TaskDef taskDef = metadataClient.getTaskDef(taskName);
        taskDef.setResponseTimeoutSeconds(1);
        taskDef.setPollTimeoutSeconds(1);
        taskDef.setTimeoutSeconds(3);
        taskDef.setTimeoutPolicy(TaskDef.TimeoutPolicy.TIME_OUT_WF);
        WorkflowDef workflowDef = metadataClient.getWorkflowDef(workflowName, 1);
        workflowDef.getTasks().get(0).setTaskDefinition(taskDef);
        metadataClient.updateWorkflowDefs(List.of(workflowDef));

        String workflowId = getWorkflowId(workflowName);
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        workflowClient.runDecider(workflowId);

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());

        assertCommonAttributes(workflow.getTasks().get(1));

    }

    @Test
    @DisplayName("Check update on event task is working fine.")
    public void test10() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient, "onScheduled");

        String workflowId = getWorkflowId(workflowName);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());
        Task task = workflow.getTasks().stream().filter(task1 -> "postgres_sql_updates".equals(task1.getTaskType())).collect(Collectors.toList()).get(0);
        assertCommonAttributes(task);

        taskClient.updateTask(getTaskResult(workflow.getTasks().get(0), TaskResult.Status.COMPLETED));
        taskClient.updateTask(getTaskResult(workflow.getTasks().get(1), TaskResult.Status.COMPLETED));

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertTrue(workflow.getTasks().stream().allMatch(task1 -> Task.Status.COMPLETED.equals(task1.getStatus())));
    }

    @Test
    @DisplayName("Check values are substituted properly")
    public void test11() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String eventType = "onStart";
        registerWorkflowDef(workflowName, taskName, metadataClient, eventType);

        WorkflowDef workflowDef = metadataClient.getWorkflowDef(workflowName, 1);
        StateChangeEvent stateChangeEvent = workflowDef.getTasks().get(0).getOnStateChange().get(eventType).get(0);
        Map<String, Object> newPayload = Map.of("orderStatus", "${workflow.input.order_status}",
                "task", "${" + taskName + "}",
                "workflow", "${" + "workflow" + "}");
        stateChangeEvent.setPayload(newPayload);
        workflowDef.getTasks().get(0).setOnStateChange(Map.of(eventType, List.of(stateChangeEvent)));
        metadataClient.updateWorkflowDefs(List.of(workflowDef));

        String workflowId = getWorkflowId(workflowName);

        Task task = taskClient.pollTask(taskName, "test_worker", null);
        assertNotNull(task);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(2, workflow.getTasks().size());
        task = workflow.getTasks().stream().filter(task1 -> "postgres_sql_updates".equals(task1.getTaskType())).collect(Collectors.toList()).get(0);
        assertNotNull(task.getInputData().get("task"));
        assertNotNull(task.getInputData().get("workflow"));
    }

    private void assertCommonAttributes(Task task) {
        Map<String, Object> expectedInput = task.getInputData();
        assertNotNull(expectedInput);
        assertEquals("PENDING", expectedInput.get("orderStatus"));
        assertEquals("Ahmedabad", expectedInput.get("city"));
        assertEquals("1234", expectedInput.get("customerId"));
        assertNotNull(task.getInputData().get("_schema"));
        Map<String, Object> map = (Map<String, Object>) task.getInputData().get("_schema");
        assertNotNull(map);
        assertEquals(map.get("dbName"), "order_updates");
        Map<String, String> schema = (Map<String, String>) map.get("_schema");
        assertEquals(schema, Map.of("orderStatus", "order_status", "orderValue", "order_value", "status", "order_status"));
    }

    private String getWorkflowId(String workflowName) {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        HashMap<String, Object> workflowInput = new HashMap<>();
        workflowInput.put("order_status", "PENDING");
        workflowInput.put("city", "Ahmedabad");
        workflowInput.put("customer_id", "1234");
        startWorkflowRequest.setInput(workflowInput);
        return workflowClient.startWorkflow(startWorkflowRequest);
    }


    private static void registerWorkflowDef(String workflowName, String taskName1, MetadataClient metadataClient1, String eventType) {
        TaskDef taskDef = new TaskDef(taskName1);
        taskDef.setRetryCount(0);
        taskDef.setOwnerEmail("test@orkes.io");

        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName(taskName1);
        workflowTask.setName(taskName1);
        workflowTask.setType(taskName1);
        HashMap<String, Object> taskInput = new HashMap<>();
        taskInput.put("orderStatus", "${workflow.input.order_status}");
        taskInput.put("city", "${workflow.input.city}");
        taskInput.put("customerId", "${workflow.input.customer_id}");
        workflowTask.setTaskDefinition(taskDef);
        workflowTask.setInputParameters(Map.of("businessState" ,taskInput));
        StateChangeEvent stateChangeEvent  =new StateChangeEvent();
        stateChangeEvent.setType("postgres_sql_updates");
        stateChangeEvent.setPayload(Map.of("orderStatus", "${workflow.input.order_status}",
                "city", "${workflow.input.city}",
                "customerId", "${workflow.input.customer_id}"));
        workflowTask.setOnStateChange(Map.of(eventType, List.of(stateChangeEvent)));

        registerWorkflowDef(workflowTask, workflowName, metadataClient1);
        registerBusinessStateSchema(workflowName, metadataClient1);

    }

    private static void registerWorkflowDef(WorkflowTask workflowTask, String workflowName, MetadataClient metadataClient) {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setVersion(1);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(workflowTask));
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTimeoutSeconds(100L);
        metadataClient.registerWorkflowDef(workflowDef);
    }

    private static void registerBusinessStateSchema(String workflowName, MetadataClient metadataClient) {
        BusinessStateSchema businessStateSchema = new BusinessStateSchema();
        Map<String, Map<String, Object>> schema = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("dbName", "order_updates");
        map.put("_schema", Map.of("orderStatus", "order_status", "orderValue", "order_value", "status", "order_status"));
        schema.put("postgres_sql_updates",map);
        businessStateSchema.setSchema(schema);
        metadataClient.addWorkflowBusinessState(workflowName, businessStateSchema);
    }

    private static TaskResult getTaskResult(Task task, TaskResult.Status status) {
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(task.getWorkflowInstanceId());
        taskResult.setStatus(status);
        taskResult.setTaskId(task.getTaskId());
        return taskResult;
    }

}
