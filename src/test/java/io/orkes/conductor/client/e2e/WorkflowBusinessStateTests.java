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

import com.netflix.conductor.common.metadata.tasks.TaskDef;
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
import io.orkes.conductor.client.model.BusinessStateSchema;
import io.orkes.conductor.client.model.ExtendedWorkflowDef;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.util.ApiUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @DisplayName("Check business state is setting task input correctly")
    public void test1() throws InterruptedException {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        setupMetadata(workflowName, taskName);

        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("a");
        tagObject.setValue("b");
        metadataClient.addWorkflowTag(tagObject, workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        HashMap<String, Object> workflowInput = new HashMap<>();
        workflowInput.put("order_status", "PENDING");
        workflowInput.put("city", "Ahmedabad");
        workflowInput.put("customer_id", "1234");
        startWorkflowRequest.setInput(workflowInput);
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        // Assert on the task input.
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(1, workflow.getTasks().size());
        assertNotNull(workflow.getTasks().get(0).getInputData());
        assertTrue(workflow.getTasks().get(0).getInputData().containsKey("businessStateSchema"));
        assertTrue(workflow.getTasks().get(0).getInputData().containsKey("businessState"));
        Map<String, Object> expectedInput = (Map<String, Object>) workflow.getTasks().get(0).getInputData().get("businessState");
        assertNotNull(expectedInput);
        assertEquals("PENDING", expectedInput.get("orderStatus"));
        assertEquals("Ahmedabad", expectedInput.get("city"));
        assertEquals("1234", expectedInput.get("customerId"));
        workflowClient.terminateWorkflow(workflowId, "Terminated");
    }

    @Test
    @DisplayName("Check update business state is working as expected")
    public void test2() {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, taskName, metadataClient);
        BusinessStateSchema businessStateSchema = new BusinessStateSchema();
        businessStateSchema.setDbName("Audit");
        businessStateSchema.setDbType(BusinessStateSchema.DataBaseType.POSTGRESQL);
        HashMap<String, Object> schema = new HashMap<>();
        schema.put("orderStatus","order_status");
        schema.put("city","order_city");
        schema.put("customerId","customer_id");
        businessStateSchema.setSchema(schema);
        registerBusinessStateSchema(workflowName, businessStateSchema, metadataClient);
        businessStateSchema.setDbName("Event");
        businessStateSchema.setDbType(BusinessStateSchema.DataBaseType.MONGODB);
        metadataClient.updateBusinessStateSchema(workflowName, businessStateSchema);
        await().atMost(62, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).untilAsserted(() -> {
            ExtendedWorkflowDef extendedWorkflowDef = metadataClient.getWorkflowDefWithMetadata(workflowName, 1);
            assertEquals("Event", extendedWorkflowDef.getBusinessStateSchema().getDbName());
            assertEquals("MONGODB", extendedWorkflowDef.getBusinessStateSchema().getDbType().toString());
        });

    }

    private static void registerWorkflowDef(String workflowName, String taskName1, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef(taskName1);
        taskDef.setRetryCount(0);
        taskDef.setOwnerEmail("test@orkes.io");

        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName(taskName1);
        inline.setName(taskName1);
        inline.setType("PUBLISH_BUSINESS_STATE");
        HashMap<String, Object> taskInput = new HashMap<>();
        taskInput.put("orderStatus", "${workflow.input.order_status}");
        taskInput.put("city", "${workflow.input.city}");
        taskInput.put("customerId", "${workflow.input.customer_id}");
        inline.setTaskDefinition(taskDef);
        inline.setInputParameters(Map.of("businessState" ,taskInput));

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setVersion(1);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(inline));
        metadataClient1.updateWorkflowDefs(Arrays.asList(workflowDef));
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef));
    }

    private static void registerBusinessStateSchema(String workflowName, BusinessStateSchema businessStateSchema, MetadataClient metadataClient ) {
        metadataClient.addBusinessStateSchema(workflowName, businessStateSchema);
    }

    private static void setupMetadata(String workflowName, String taskName) {
        registerWorkflowDef(workflowName, taskName, metadataClient);
        BusinessStateSchema businessStateSchema = new BusinessStateSchema();
        businessStateSchema.setDbName("Audit");
        businessStateSchema.setDbType(BusinessStateSchema.DataBaseType.POSTGRESQL);
        HashMap<String, Object> schema = new HashMap<>();
        schema.put("orderStatus","order_status");
        schema.put("city","order_city");
        schema.put("customerId","customer_id");
        businessStateSchema.setSchema(schema);
        registerBusinessStateSchema(workflowName, businessStateSchema, metadataClient);

    }


}
