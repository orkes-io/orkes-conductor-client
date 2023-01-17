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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class GroupPermissionTests {

    @Test
    public void testGroupRelatedPermissions() throws Exception {
        ApiClient apiUser1Client = ApiUtil.getUser1Client();
        WorkflowClient user1WorkflowClient = new OrkesWorkflowClient(apiUser1Client);
        MetadataClient user1MetadataClient = new OrkesMetadataClient(apiUser1Client);
        TaskClient user1TaskClient = new OrkesTaskClient(apiUser1Client);

        // Create user2 client and check access should not be there workflow1
        ApiClient apiUser2Client = ApiUtil.getUser2Client();
        WorkflowClient user2WorkflowClient = new OrkesWorkflowClient(apiUser2Client);
        MetadataClient user2MetadataClient = new OrkesMetadataClient(apiUser2Client);
        TaskClient user2TaskClient = new OrkesTaskClient(apiUser2Client);

        String taskName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagKey = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagValue = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key(tagKey).value(tagValue);

        // Register workflow
        registerWorkflowDef(workflowName1, taskName1, user1MetadataClient);

        // Tag workflow and task
        user1MetadataClient.addWorkflowTag(tagObject, workflowName1);
        user1MetadataClient.addTaskTag(tagObject, taskName1);

        // Trigger workflow
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName1);
        startWorkflowRequest.setVersion(1);

        String workflowId = user1WorkflowClient.startWorkflow(startWorkflowRequest);
        String finalWorkflowId = workflowId;
        // User 2 should not have access to workflow or task.
        Assert.assertThrows(ApiException.class, () -> user2TaskClient.pollTask(taskName1, "integration_test", null));
        Assert.assertThrows(ApiException.class, () -> user2WorkflowClient.getWorkflow(finalWorkflowId, false));

        // User1 should be able to complete task/workflow
        TaskResult taskResult  = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setTaskId(user1WorkflowClient.getWorkflow(workflowId, true).getTasks().get(0).getTaskId());
        user1TaskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = user1WorkflowClient.getWorkflow(finalWorkflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        //Trigger workflow again. And give permissions so that user2 can execute workflow/task
        workflowId = user1WorkflowClient.startWorkflow(startWorkflowRequest);

        user1MetadataClient.addWorkflowTag(tagObject, workflowName1);

        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        AuthorizationClient authorizationClient = new OrkesAuthorizationClient(adminClient);

        String groupName = "worker-test-group";
        try {
            authorizationClient.deleteGroup(groupName);
        } catch (Exception e) {
          // Group does not exist.
        }
        // Create group and add these two users in the group
        Group group = authorizationClient.upsertGroup(getUpsertGroupRequest(), groupName);
        authorizationClient.addUserToGroup(groupName, "conductoruser1@gmail.com");
        authorizationClient.addUserToGroup(groupName, "conductoruser2@gmail.com");

        // Give permissions to tag in the group
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setSubject(new SubjectRef().id(groupName).type(SubjectRef.TypeEnum.GROUP));
        authorizationRequest.setAccess(List.of(AuthorizationRequest.AccessEnum.READ, AuthorizationRequest.AccessEnum.EXECUTE,
                AuthorizationRequest.AccessEnum.UPDATE,
                AuthorizationRequest.AccessEnum.DELETE));
        authorizationRequest.setTarget(new TargetRef().id(tagKey + ":" + tagValue ).type(TargetRef.TypeEnum.TAG));
        authorizationClient.grantPermissions(authorizationRequest);

        //Grant permission to execute the task in user2 application.
        authorizationRequest.setSubject(new SubjectRef().id(System.getenv("USER2_APPLICATION_ID")).type(SubjectRef.TypeEnum.USER));
        authorizationClient.grantPermissions(authorizationRequest);

        String finalWorkflowId1 = workflowId;
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                String id = user2WorkflowClient.getWorkflow(finalWorkflowId1, true).getTasks().get(0).getTaskId();
                TaskResult taskResult1 = new TaskResult();
                taskResult1.setWorkflowInstanceId(id);
                taskResult1.setStatus(TaskResult.Status.COMPLETED);
                taskResult1.setTaskId(user2WorkflowClient.getWorkflow(id, true).getTasks().get(0).getTaskId());
                user2TaskClient.updateTask(taskResult1);
            }catch(Exception e){}
        });

        int retryAttemptsLimit = 5;
        for (int retry = 0; retry < retryAttemptsLimit; retry += 1) {
            try{
                // Wait for workflow to get completed
                await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
                    Workflow workflow1 = user2WorkflowClient.getWorkflow(finalWorkflowId, false);
                    assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
                });
                break;
            } catch (Exception e) {
                Thread.sleep((retry + 5) * 1000);
            }
        }
        

        user1MetadataClient.unregisterWorkflowDef(workflowName1, 1);
        user1MetadataClient.unregisterTaskDef(taskName1);
        authorizationClient.deleteGroup(groupName);
        authorizationClient.removePermissions(authorizationRequest);
        authorizationRequest.setSubject(new SubjectRef().id(groupName).type(SubjectRef.TypeEnum.GROUP));
        authorizationClient.removePermissions(authorizationRequest);
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .description("Group used for SDK testing")
                .roles(List.of(UpsertGroupRequest.RolesEnum.USER));
    }

    private void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName(taskName);
        workflowTask.setName(taskName);
        workflowTask.setTaskDefinition(taskDef);
        workflowTask.setWorkflowTaskType(TaskType.SIMPLE);
        workflowTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(workflowTask));
        metadataClient1.registerWorkflowDef(workflowDef);
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef));
    }

}
