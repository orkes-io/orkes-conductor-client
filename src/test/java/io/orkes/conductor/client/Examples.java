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

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.model.AuthorizationRequest;
import io.orkes.conductor.client.model.Group;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.UpsertGroupRequest;
import io.orkes.conductor.client.model.UpsertGroupRequest.RolesEnum;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.WorkflowUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Examples {
    private final MetadataClient metadataClient;
    private final WorkflowClient workflowClient;
    private final AuthorizationClient authorizationClient;

    public Examples() {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        metadataClient = orkesClients.getMetadataClient();
        workflowClient = orkesClients.getWorkflowClient();
        authorizationClient = orkesClients.getAuthorizationClient();
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
        ((OrkesMetadataClient) metadataClient).addTaskTag(tagObject, Commons.TASK_NAME);
        ((OrkesMetadataClient) metadataClient).addWorkflowTag(tagObject, Commons.WORKFLOW_NAME);
    }

    @Test
    @DisplayName("add auth to tags")
    public void addAuthToTags() {
        // Add auth to tags
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.access(
                Collections.singletonList(AuthorizationRequest.AccessEnum.EXECUTE));
        // authorizationResourceApi.grantPermissions(authorizationRequest);
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
        workflowClient.startWorkflow(startWorkflowRequest);
    }

    @Test
    @DisplayName("auto assign group permission on workflow creation by any group member")
    public void autoAssignWorkflowPermissions() {
        giveApplicationPermissions("46f0bf10-b59d-4fbd-a053-935307c8cb86");
        Group group = authorizationClient.upsertGroup(getUpsertGroupRequest(), "sdk-test-group");
        validateGroupPermissions(group.getId());
    }

    void registerTask() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(Commons.TASK_NAME);
        List<TaskDef> taskDefs = new ArrayList<>();
        taskDefs.add(taskDef);
        this.metadataClient.registerTaskDefs(taskDefs);
    }

    void registerWorkflow() {
        WorkflowDef workflowDef = WorkflowUtil.getWorkflowDef();
        metadataClient.registerWorkflowDef(workflowDef);
    }

    void giveApplicationPermissions(String applicationId) {
        authorizationClient.addRoleToApplicationUser(applicationId, "ADMIN");
    }

    void validateGroupPermissions(String id) {
        Group group = authorizationClient.getGroup(id);
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
                .roles(Collections.singletonList(RolesEnum.ADMIN));
    }

    List<String> getAccessListAll() {
        return new ArrayList<String>(
                Arrays.asList("CREATE", "READ", "UPDATE", "EXECUTE", "DELETE"));
    }
}
