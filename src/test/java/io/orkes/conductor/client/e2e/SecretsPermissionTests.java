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
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;


@Slf4j
public class SecretsPermissionTests extends AbstractMultiUserTests {

    @Test
    public void testSecretsForUser2() {
        SecretClient user1SecretClient = new OrkesSecretClient(apiUser1Client);
        SecretClient user2SecretClient = new OrkesSecretClient(apiUser2Client);

        String secretKey = "secret_key_" + UUID.randomUUID();
        String secretValue = "secret_value";

        user1SecretClient.putSecret(secretValue, secretKey);

        String tagKey = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String tagValue = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key(tagKey).value(tagValue);

        // Tag secret
        user1SecretClient.putTagForSecret(Arrays.asList(tagObject), secretKey);


        // Give permissions to tag in the group
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setSubject(new SubjectRef().id("app:" + user2AppId).type(SubjectRef.TypeEnum.USER));
        authorizationRequest.setAccess(List.of(AuthorizationRequest.AccessEnum.READ));
        authorizationRequest.setTarget(new TargetRef().id(tagKey + ":" + tagValue).type(TargetRef.TypeEnum.TAG));
        authorizationClient.grantPermissions(authorizationRequest);

        // Secret is accessible for user2
        Assertions.assertNotNull(user2SecretClient.getSecret(secretKey));

        authorizationClient.removePermissions(authorizationRequest);
        authorizationRequest.setSubject(new SubjectRef().id(user2AppId).type(SubjectRef.TypeEnum.USER));
        authorizationClient.removePermissions(authorizationRequest);

        user1SecretClient.deleteSecret(secretKey);
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
