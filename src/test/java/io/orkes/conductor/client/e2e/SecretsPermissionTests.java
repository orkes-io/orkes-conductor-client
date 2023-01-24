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
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.Assert.*;


@Slf4j
public class SecretsPermissionTests extends AbstractMultiUserTests {

    @Test
    public void testSecretsForUser2() {
        SecretClient user1SecretClient = new OrkesSecretClient(apiUser1Client);
        SecretClient user2SecretClient = new OrkesSecretClient(apiUser2Client);
        GrantedAccessResponse user1Permissions = authorizationClient.getGrantedPermissionsForUser(user1.getId());
        log.info("permissions before : {}", user1Permissions.getGrantedAccess());

        String secretKey = "secret_key" + UUID.randomUUID();
        String secretValue = "secret_value";

        user1SecretClient.putSecret(secretValue, secretKey);

        user1Permissions = authorizationClient.getGrantedPermissionsForUser(user1.getId());
        log.info("permissions after : {}", user1Permissions.getGrantedAccess());

        String tagKey = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagValue = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key(tagKey).value(tagValue);

        // Tag secret
        user1SecretClient.putTagForSecret(Arrays.asList(tagObject), secretKey);

        String groupName = "worker-test-group";
        try {
            authorizationClient.deleteGroup(groupName);
        } catch (Exception e) {
          // Group does not exist or secret does not exist
        }

        // Create group and add these two users in the group
        Group group = authorizationClient.upsertGroup(getUpsertGroupRequest(), groupName);
        authorizationClient.addUserToGroup(groupName, user1.getId());
        authorizationClient.addUserToGroup(groupName, user2.getId());
        List<ConductorUser> usersInGroup = authorizationClient.getUsersInGroup(group.getId());
        assertNotNull(usersInGroup);
        assertEquals(2, usersInGroup.size());
        Set<String> groupUsers = usersInGroup.stream().map(g -> g.getId()).collect(Collectors.toSet());
        assertTrue(groupUsers.contains(user1.getId()));
        assertTrue(groupUsers.contains(user2.getId()));

        // Give permissions to tag in the group
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setSubject(new SubjectRef().id(groupName).type(SubjectRef.TypeEnum.GROUP));
        authorizationRequest.setAccess(List.of(AuthorizationRequest.AccessEnum.READ, AuthorizationRequest.AccessEnum.EXECUTE,
                AuthorizationRequest.AccessEnum.UPDATE,
                AuthorizationRequest.AccessEnum.DELETE));
        authorizationRequest.setTarget(new TargetRef().id(tagKey + ":" + tagValue).type(TargetRef.TypeEnum.TAG));
        authorizationClient.grantPermissions(authorizationRequest);

        //Grant permission to execute the task in user2 application.
        //authorizationRequest.setSubject(new SubjectRef().id(user2Application.getId()).type(SubjectRef.TypeEnum.USER));
        //authorizationClient.grantPermissions(authorizationRequest);

        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);

        // Secret is accessible for user2
        Assertions.assertNotNull(user2SecretClient.getSecret(secretKey));

        authorizationClient.removePermissions(authorizationRequest);
        authorizationRequest.setSubject(new SubjectRef().id(groupName).type(SubjectRef.TypeEnum.GROUP));
        authorizationClient.removePermissions(authorizationRequest);
        authorizationClient.deleteGroup(groupName);
        user1SecretClient.deleteSecret(secretValue);
    }

    @Test
    public void testGrantTaskExecutePermissions() {

    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .description("Group used for SDK testing")
                .roles(List.of(UpsertGroupRequest.RolesEnum.USER));
    }

}
