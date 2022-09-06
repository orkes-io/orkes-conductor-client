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
package io.orkes.conductor.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.*;

public class Examples {

    MetadataResourceApi metadataResourceApi;
    GroupResourceApi groupResourceApi;

    ApplicationResourceApi applicationResourceApi;

    WorkflowResourceApi workflowResourceApi;

    UserResourceApi userResourceApi;

    AuthorizationResourceApi authorizationResourceApi;

    TagsApi tagsApi;

    String keyId = "keyId";
    String keySecret = "keySecret";

    @BeforeEach
    public void init() {
        ApiClient apiClient = new ApiClient("https://play.orkes.io/api", keyId, keySecret);
        metadataResourceApi = new MetadataResourceApi(apiClient);
        groupResourceApi = new GroupResourceApi(apiClient);
        applicationResourceApi = new ApplicationResourceApi(apiClient);
        workflowResourceApi = new WorkflowResourceApi(apiClient);
        tagsApi = new TagsApi(apiClient);
        userResourceApi = new UserResourceApi(apiClient);
        authorizationResourceApi = new AuthorizationResourceApi(apiClient);
    }

    @BeforeEach
    public void setup() {}

    @Test
    @DisplayName("tag a user and group")
    public void tagUserAndGroup() {}

    @Test
    @DisplayName("tag a workflows and task")
    public void tagWorkflowsAndTasks() {}

    @Test
    @DisplayName("add auth to tags")
    public void addAuthToTags() {}

    @Test
    @DisplayName("create workflow definition")
    public void createWorkflowDef() {}

    @Test
    @DisplayName("start workflow")
    public void startWorkflow() {}
}
