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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        //Create a tag
        TagObject tagObject = new TagObject();
        tagObject.setKey("department");
        tagObject.setValue("HR");

        //Add auth to tags
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.access(Arrays.asList(AuthorizationRequest.AccessEnum.EXECUTE));
//        authorizationResourceApi.grantPermissions();
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
}
