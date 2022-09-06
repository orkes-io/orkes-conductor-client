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

import io.orkes.conductor.client.http.model.*;
import io.orkes.conductor.client.http.model.UpsertGroupRequest.RolesEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Examples {
    private static final String ENV_ROOT_URI = "SDK_INTEGRATION_TESTS_SERVER_API_URL";
    private static final String ENV_SECRET = "SDK_INTEGRATION_TESTS_SERVER_KEY_SECRET";
    private static final String ENV_KEY_ID = "SDK_INTEGRATION_TESTS_SERVER_KEY_ID";

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
        ApiClient apiClient = getApiClientWithCredentials();
        metadataResourceApi = new MetadataResourceApi(apiClient);
        groupResourceApi = new GroupResourceApi(apiClient);
        applicationResourceApi = new ApplicationResourceApi(apiClient);
        workflowResourceApi = new WorkflowResourceApi(apiClient);
        tagsApi = new TagsApi(apiClient);
        userResourceApi = new UserResourceApi(apiClient);
        authorizationResourceApi = new AuthorizationResourceApi(apiClient);
    }

    @Test
    @DisplayName("tag a workflows and task")
    public void tagWorkflowsAndTasks() {
        // Create workflow definition
        String taskName = "trye";
        String workflowName = "Workflow_klioqa";
        WorkflowDef workflowDef = getWorkflowDef(workflowName, taskName);
        // Create workflow definition.
        metadataResourceApi.create(workflowDef, true);

        // Create task definition
        TaskDef taskDef = new TaskDef();
        taskDef.setName(taskName);
        metadataResourceApi.registerTaskDef(Arrays.asList(taskDef));

        // Tag a task
        TagObject tagObject = new TagObject();
        tagObject.setKey("department");
        tagObject.setValue("account");
        tagsApi.addTaskTag(tagObject, taskName);

        // Tag a workflow
        tagsApi.addWorkflowTag(tagObject, workflowName);
    }

    @Test
    @DisplayName("add auth to tags")
    public void addAuthToTags() {
        // Create a tag
        TagObject tagObject = new TagObject();
        tagObject.setKey("department");
        tagObject.setValue("HR");

        // Add auth to tags
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.access(Arrays.asList(AuthorizationRequest.AccessEnum.EXECUTE));
        // authorizationResourceApi.grantPermissions();
    }

    @Test
    @DisplayName("create workflow definition")
    public void createWorkflowDef() {
        // Create workflow definition
        String workflowName = "Workflow_tweytey";
        String taskName = "Task_wyuw";
        WorkflowDef workflowDef = getWorkflowDef(workflowName, taskName);
        metadataResourceApi.create(workflowDef, true);

        // Create task definition
        TaskDef taskDef = new TaskDef();
        taskDef.setName(taskName);
        metadataResourceApi.registerTaskDef(Arrays.asList(taskDef));
    }

    @Test
    @DisplayName("start workflow")
    public void startWorkflow() {
        // Create workflow definition
        String workflowName = "Workflow_wtyet";
        String taskName = "Task_wyuwew";
        WorkflowDef workflowDef = getWorkflowDef(workflowName, taskName);
        metadataResourceApi.create(workflowDef, true);

        // Create task definition
        TaskDef taskDef = new TaskDef();
        taskDef.setName(taskName);
        metadataResourceApi.registerTaskDef(Arrays.asList(taskDef));
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);
        Map<String, Object> input = new HashMap<>();
        startWorkflowRequest.setInput(input);
        workflowResourceApi.startWorkflow(startWorkflowRequest);
    }

    @Test
    @DisplayName("auto assign group permission on workflow creation by any group member")
    public void autoAssignWorkflowPermissions() {
        giveApplicationPermissions("46f0bf10-b59d-4fbd-a053-935307c8cb86");
        Group group = groupResourceApi.upsertGroup(getUpsertGroupRequest(), "sdk-test-group");
        validateGroupPermissions(group.getId());
    }

    WorkflowDef getWorkflowDef(String workflowName, String taskName) {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setVersion(1);
        workflowDef.setOwnerEmail("manan.bhatt@orkes.io");
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName(taskName);
        workflowDef.setTasks(Arrays.asList(workflowTask));
        return workflowDef;
    }

    void giveApplicationPermissions(String applicationId) {
        applicationResourceApi.addRoleToApplicationUser(applicationId, "ADMIN");
    }

    void validateGroupPermissions(String id) {
        Group group = groupResourceApi.getGroup(id);
        for (Map.Entry<String, List<String>> entry : group.getDefaultAccess().entrySet()) {
            List<String> expectedList = new ArrayList<>(getAccessListAll());
            List<String> actualList = new ArrayList<>(entry.getValue());
            Collections.sort(expectedList);
            Collections.sort(actualList);
            assertEquals(expectedList, actualList);
        }
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .defaultAccess(
                        Map.of(
                                "WORKFLOW_DEF", getAccessListAll(),
                                "TASK_DEF", getAccessListAll()))
                .description("Group used for SDK testing")
                .roles(List.of(RolesEnum.ADMIN));
    }

    List<String> getAccessListAll() {
        return List.of(
                "CREATE",
                "READ",
                "UPDATE",
                "EXECUTE",
                "DELETE");
    }

    ApiClient getApiClientWithCredentials() {
        return new ApiClient(
                getEnv(ENV_ROOT_URI),
                getEnv(ENV_KEY_ID),
                getEnv(ENV_SECRET));
    }

    String getEnv(String key) {
        return System.getenv(key);
    }
}
