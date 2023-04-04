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
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.SubWorkflowParams;
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
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class DoWhileWithDomainTests {

    @Test
    public void testSubWorkflow0version() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);

        String parentWorkflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        registerInlineWorkflowDef(parentWorkflowName, metadataClient);

        // Trigger workflow
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(parentWorkflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        // User1 should be able to complete task/workflow
        String taskId = workflowClient.getWorkflow(workflowId, true).getTasks().get(0).getTaskId();
        TaskResult taskResult  = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setTaskId(taskId);
        taskClient.updateTask(taskResult);

        // Workflow will be still running state
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.RUNNING.name());
            assertEquals(workflow1.getTasks().get(0).getStatus(), Task.Status.COMPLETED);
            assertEquals(workflow1.getTasks().get(1).getStatus(), Task.Status.IN_PROGRESS);
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        String subWorkflowId = workflow.getTasks().get(1).getSubWorkflowId();
        taskResult  = new TaskResult();
        taskResult.setWorkflowInstanceId(subWorkflowId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setTaskId(workflow.getTasks().get(1).getTaskId());
        taskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow1.getTasks().get(0).getStatus(), Task.Status.COMPLETED);
            assertEquals(workflow1.getTasks().get(1).getStatus(), Task.Status.COMPLETED);
        });

        // Cleanup
        metadataClient.unregisterWorkflowDef(parentWorkflowName, 1);
    }

    private void registerInlineWorkflowDef(String workflowName, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef("dt1");
        taskDef.setOwnerEmail("test@orkes.io");

        TaskDef taskDef2 = new TaskDef("dt2");
        taskDef2.setOwnerEmail("test@orkes.io");

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

        WorkflowTask inlineSubworkflow = new WorkflowTask();
        inlineSubworkflow.setTaskReferenceName("dynamicFork");
        inlineSubworkflow.setName("dynamicFork");
        inlineSubworkflow.setTaskDefinition(taskDef);
        inlineSubworkflow.setWorkflowTaskType(TaskType.SUB_WORKFLOW);

        WorkflowDef inlineWorkflowDef = new WorkflowDef();
        inlineWorkflowDef.setName("inline_test_sub_workflow");
        inlineWorkflowDef.setVersion(1);
        inlineWorkflowDef.setTimeoutSeconds(600);
        inlineWorkflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        inlineWorkflowDef.setTasks(Arrays.asList(inline));
        SubWorkflowParams subWorkflowParams = new SubWorkflowParams();
        subWorkflowParams.setName("inline_test_sub_workflow");
        subWorkflowParams.setVersion(1);
        subWorkflowParams.setWorkflowDef(inlineWorkflowDef);
        inlineSubworkflow.setSubWorkflowParam(subWorkflowParams);


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test inline sub_workflow definition");
        workflowDef.setTasks(Arrays.asList( workflowTask, inlineSubworkflow));
        try {
            metadataClient1.registerWorkflowDef(workflowDef);
            metadataClient1.registerTaskDefs(Arrays.asList(taskDef, taskDef2));
        } catch (Exception e){}
    }
}
