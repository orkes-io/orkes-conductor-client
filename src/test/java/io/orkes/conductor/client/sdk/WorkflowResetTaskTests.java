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
package io.orkes.conductor.client.sdk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowResetTaskTests {

    @Test
    public void testResetTaskDynamicFork() {

        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowAdminClient = new OrkesWorkflowClient(adminClient);
        MetadataClient metadataAdminClient  =new OrkesMetadataClient(adminClient);
        TaskClient taskClient = new OrkesTaskClient(adminClient);
        String workflowName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerWorkflowDef(workflowName1, metadataAdminClient);

        // Trigger workflow
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName1);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowAdminClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowAdminClient.getWorkflow(workflowId, true);

        // Complete the first task
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflow.getTasks().get(0).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskClient.updateTask(taskResult);

//        // Wait for dynamic fork task to get started
//        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
//            Workflow workflow1 = workflowAdminClient.getWorkflow(workflowId, true);
//            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
//            assertEquals(workflow1.getTasks().get(1).getStatus().name(), Task.Status.SCHEDULED.name());
//        });

        workflow = workflowAdminClient.getWorkflow(workflowId, true);
        taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(workflow.getTasks().get(1).getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);

        WorkflowTask workflowTask2 = new WorkflowTask();
        workflowTask2.setName("integration_task_2");
        workflowTask2.setTaskReferenceName("xdt1");

        WorkflowTask workflowTask3 = new WorkflowTask();
        workflowTask3.setName("integration_task_3");
        workflowTask3.setTaskReferenceName("xdt2");

        Map<String, Object> output = new HashMap<>();
        Map<String, Map<String, Object>> input = new HashMap<>();
        input.put("xdt1", Map.of("k1", "v1"));
        input.put("xdt2", Map.of("k2", "v2"));
        output.put("dynamicTasks", Arrays.asList(workflowTask2, workflowTask3));
        output.put("dynamicTasksInput", input);
        taskResult.setOutputData(output);
        taskClient.updateTask(taskResult);

        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowAdminClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(3).getStatus().name(), Task.Status.SCHEDULED.name());
            assertEquals(workflow1.getTasks().get(4).getStatus().name(), Task.Status.SCHEDULED.name());
            assertEquals(workflow1.getTasks().get(5).getStatus().name(), Task.Status.IN_PROGRESS.name());
        });

        // Reset task 1. It will be put to queue and integration_task_2 and integration_task_3 will be removed from the queue
    }

    private void registerWorkflowDef(String workflowName, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef("dt1");
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);

        TaskDef taskDef4 = new TaskDef("integration_task_2");
        taskDef4.setOwnerEmail("test@orkes.io");
        taskDef4.setRetryCount(0);

        TaskDef taskDef3 = new TaskDef("integration_task_3");
        taskDef3.setOwnerEmail("test@orkes.io");
        taskDef3.setRetryCount(0);

        TaskDef taskDef2 = new TaskDef("dt2");
        taskDef2.setOwnerEmail("test@orkes.io");
        taskDef2.setRetryCount(0);

        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName("dt2");
        workflowTask.setName("dt2");
        workflowTask.setTaskDefinition(taskDef2);
        workflowTask.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName("dt1");
        inline.setName("dt1");
        inline.setTaskDefinition(taskDef);
        inline.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowTask join = new WorkflowTask();
        join.setTaskReferenceName("join_dynamic");
        join.setName("join_dynamic");
        join.setWorkflowTaskType(TaskType.JOIN);

        WorkflowTask dynamicFork = new WorkflowTask();
        dynamicFork.setTaskReferenceName("dynamicFork");
        dynamicFork.setName("dynamicFork");
        dynamicFork.setTaskDefinition(taskDef);
        dynamicFork.setWorkflowTaskType(TaskType.FORK_JOIN_DYNAMIC);
        dynamicFork.setInputParameters(Map.of("dynamicTasks", "${dt1.output.dynamicTasks}",
                "dynamicTasksInput", "${dt1.output.dynamicTasksInput}"));
        dynamicFork.setDynamicForkTasksParam("dynamicTasks");
        dynamicFork.setDynamicForkTasksInputParamName("dynamicTasksInput");

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test retry");
        workflowDef.setTasks(Arrays.asList(workflowTask, inline, dynamicFork, join));
        metadataClient1.registerWorkflowDef(workflowDef);
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef, taskDef2, taskDef3, taskDef4));
    }

}
