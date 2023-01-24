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
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public abstract class AbstractMultiUserTests {

    protected static AuthorizationClient authorizationClient;
    protected static String applicationId;

    protected static MetadataClient metadataClient;

    protected static ApiClient apiUser1Client;

    protected static ApiClient apiUser2Client;

    protected static ApiClient adminClient;

    protected static ConductorUser user1;

    protected static ConductorUser user2;

    protected static ConductorApplication user1Application;

    protected static ConductorApplication user2Application;

    protected static CreateAccessKeyResponse user1AccessKey;

    protected static CreateAccessKeyResponse user2AccessKey;

    @BeforeAll
    public static void setup() {
        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        authorizationClient = ApiUtil.getOrkesClient().getAuthorizationClient();

        //Register user 1
        UpsertUserRequest user1Request = new UpsertUserRequest();
        user1Request.setName("user1@orkes.io");
        user1Request.setRoles(List.of(UpsertUserRequest.RolesEnum.USER));
        user1 = authorizationClient.upsertUser(user1Request, UUID.randomUUID().toString());


        //Register user 2
        UpsertUserRequest user2Request = new UpsertUserRequest();
        user2Request.setName("viren@baraiya.com");
        user2Request.setRoles(List.of(UpsertUserRequest.RolesEnum.USER));
        user2 = authorizationClient.upsertUser(user2Request, UUID.randomUUID().toString());


        CreateOrUpdateApplicationRequest request = new CreateOrUpdateApplicationRequest();
        request.setName("test-" + UUID.randomUUID().toString());
        ConductorApplication app = authorizationClient.createApplication(request);
        applicationId = app.getId();

        //Create api client for user 1
        CreateOrUpdateApplicationRequest user1AppRequest = new CreateOrUpdateApplicationRequest();
        user1AppRequest.setName("user1-" + UUID.randomUUID().toString());
        user1Application = authorizationClient.createApplication(user1AppRequest, user1.getId());
        authorizationClient.addRoleToApplicationUser(user1Application.getId(), UpsertUserRequest.RolesEnum.USER.getValue());

        user1AccessKey = authorizationClient.createAccessKey(user1Application.getId());
        apiUser1Client = new ApiClient(adminClient.getBasePath(), user1AccessKey.getId(), user1AccessKey.getSecret());


        //Create api client for user 2
        CreateOrUpdateApplicationRequest user2AppRequest = new CreateOrUpdateApplicationRequest();
        user2AppRequest.setName("user2-" + UUID.randomUUID().toString());
        user2Application = authorizationClient.createApplication(user2AppRequest, user2.getId());
        authorizationClient.addRoleToApplicationUser(user2Application.getId(), UpsertUserRequest.RolesEnum.USER.getValue());

        user2AccessKey = authorizationClient.createAccessKey(user2Application.getId());
        apiUser2Client = new ApiClient(adminClient.getBasePath(), user2AccessKey.getId(), user2AccessKey.getSecret());

    }

    //@AfterAll
    public static void cleanup() {
        if(applicationId != null) {
            authorizationClient.deleteApplication(applicationId);

            authorizationClient.deleteAccessKey(user1Application.getId(), user1AccessKey.getId());
            authorizationClient.deleteApplication(user1Application.getId());

            authorizationClient.deleteAccessKey(user2Application.getId(), user2AccessKey.getId());
            authorizationClient.deleteApplication(user2Application.getId());
        }
    }
}
