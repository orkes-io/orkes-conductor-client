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

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;


public class SecretsPermissionTests {

    @Test
    public void testSecretsForUser2() {
        ApiClient apiUser1Client = ApiUtil.getUser1Client();
        SecretClient user1SecretClient = new OrkesSecretClient(apiUser1Client);
        ApiClient apiUser2Client = ApiUtil.getUser2Client();
        SecretClient user2SecretClient = new OrkesSecretClient(apiUser2Client);

        String secretKey = "secret_key";
        String secretValue = "secret_value";
        System.out.println("Secret name " + secretKey);
        System.out.println("Secret key " + secretValue);

        user1SecretClient.putSecret(secretKey, secretValue);

        String tagKey = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagValue = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key(tagKey).value(tagValue);

        // Tag secret
        user1SecretClient.putTagForSecret(Arrays.asList(tagObject), secretValue);

        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        AuthorizationClient authorizationClient = new OrkesAuthorizationClient(adminClient);

        String groupName = "worker-test-group";
        try {
            authorizationClient.deleteGroup(groupName);
            user1SecretClient.deleteSecret(secretValue);
        } catch (Exception e) {
          // Group does not exist or secret does not exist
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

        // Secret is accessible for user2
        Assertions.assertNotNull(user2SecretClient.getSecret(secretValue));

        authorizationClient.removePermissions(authorizationRequest);
        authorizationRequest.setSubject(new SubjectRef().id(groupName).type(SubjectRef.TypeEnum.GROUP));
        authorizationClient.removePermissions(authorizationRequest);
        authorizationClient.deleteGroup(groupName);
        user1SecretClient.deleteSecret(secretValue);
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .description("Group used for SDK testing")
                .roles(List.of(UpsertGroupRequest.RolesEnum.USER));
    }

}
