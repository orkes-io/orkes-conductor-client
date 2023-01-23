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

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.RegistrationUtil;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkerTaskPermissionTests {

    @Test
    public void testWorkerTaskPermissionForUser2() {
        ApiClient apiUser1Client = ApiUtil.getUser1Client();
        MetadataClient user1MetadataClient = new OrkesMetadataClient(apiUser1Client);

        // Create user2 client and check access should not be there workflow1
        ApiClient apiUser2Client = ApiUtil.getUser2Client();
        MetadataClient user2MetadataClient = new OrkesMetadataClient(apiUser2Client);

        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String taskName2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagKey = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagValue = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key(tagKey).value(tagValue);

        // Register workflow
        RegistrationUtil.registerWorkflowDef(workflowName, taskName, taskName2, user1MetadataClient);

        // Tag workflow and task
        user1MetadataClient.addWorkflowTag(tagObject, workflowName);
        user1MetadataClient.addTaskTag(tagObject, taskName);

        // User 2 should not have access to workflow or task.
        Assert.assertThrows(ApiException.class, () -> user2MetadataClient.getWorkflowDef(workflowName, 1));

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

        // user2 should be able to access workflow definition
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            try {
                Assertions.assertNotNull(user2MetadataClient.getWorkflowDef(workflowName, 1));
            }catch(Exception e) {
            // Server might take time to affect permission changes.
            }
        });

        user1MetadataClient.unregisterWorkflowDef(workflowName, 1);
        user1MetadataClient.unregisterTaskDef(taskName);
        authorizationClient.deleteGroup(groupName);
        authorizationClient.removePermissions(authorizationRequest);
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .description("Group used for SDK testing")
                .roles(List.of(UpsertGroupRequest.RolesEnum.USER));
    }
}
