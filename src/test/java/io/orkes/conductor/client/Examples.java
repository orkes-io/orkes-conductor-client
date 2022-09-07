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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.*;
import io.orkes.conductor.client.http.model.*;
import io.orkes.conductor.client.http.model.UpsertGroupRequest.RolesEnum;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.WorkflowUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Examples {
    MetadataResourceApi metadataResourceApi;
    GroupResourceApi groupResourceApi;
    ApplicationResourceApi applicationResourceApi;
    WorkflowResourceApi workflowResourceApi;
    UserResourceApi userResourceApi;
    AuthorizationResourceApi authorizationResourceApi;
    TagsApi tagsApi;

    @BeforeEach
    public void init() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
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
        registerTask();
        registerWorkflow();
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("a");
        tagObject.setValue("b");
        tagsApi.addTaskTag(tagObject, Commons.TASK_NAME);
        tagsApi.addWorkflowTag(tagObject, Commons.WORKFLOW_NAME);
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
        registerTask();
        registerWorkflow();
    }

    @Test
    @DisplayName("start workflow")
    public void startWorkflow() {
        registerTask();
        registerWorkflow();
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(Commons.WORKFLOW_NAME);
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

    void registerTask() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(Commons.TASK_NAME);
        this.metadataResourceApi.registerTaskDef(Arrays.asList(taskDef));
    }

    void registerWorkflow() {
        WorkflowDef workflowDef = WorkflowUtil.getWorkflowDef();
        metadataResourceApi.create(workflowDef, true);
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
                .roles(Arrays.asList(RolesEnum.ADMIN));
    }

    List<String> getAccessListAll() {
        return Arrays.asList("CREATE", "READ", "UPDATE", "EXECUTE", "DELETE");
    }
}
