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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesAuthorizationClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowSearchTests {

    @Test
    public void testWorkflowSearchPermissions() {

        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowAdminClient = new OrkesWorkflowClient(adminClient);
        MetadataClient metadataAdminClient  =new OrkesMetadataClient(adminClient);
        String taskName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // Run workflow search it should return 0 result
        AtomicReference<SearchResult<WorkflowSummary>> workflowSummarySearchResult = new AtomicReference<>(workflowAdminClient.search("workflowType IN (" + workflowName1 + ")"));
        assertEquals(workflowSummarySearchResult.get().getResults().size(), 0);

        // Register workflow
        registerWorkflowDef(workflowName1, taskName1, metadataAdminClient);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName1);
        startWorkflowRequest.setVersion(1);

        workflowAdminClient.startWorkflow(startWorkflowRequest);
        workflowAdminClient.startWorkflow(startWorkflowRequest);
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowAdminClient.search("workflowType IN (" + workflowName1 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 2;
        });

        // Register another workflow
        String taskName2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName2, taskName2, metadataAdminClient);

        startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName2);
        startWorkflowRequest.setVersion(1);

        // Trigger workflow
        workflowAdminClient.startWorkflow(startWorkflowRequest);
        workflowAdminClient.startWorkflow(startWorkflowRequest);
        // In search result when only this workflow searched 2 results should come
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowAdminClient.search("workflowType IN (" + workflowName2 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 2;
        });

        // In search result when both workflow searched then 4 results should come
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowAdminClient.search("workflowType IN (" + workflowName1 + "," + workflowName2 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 4;
        });

        // Terminate all the workflows
        workflowSummarySearchResult.get().getResults().forEach(workflowSummary -> workflowAdminClient.terminateWorkflow(workflowSummary.getWorkflowId(), "test"));
        String department = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        TagObject tagObject = new TagObject().type(TagObject.TypeEnum.METADATA).key("department").value(department);
        metadataAdminClient.addWorkflowTag(tagObject, workflowName1);

        AuthorizationClient authorizationClient = new OrkesAuthorizationClient(adminClient);
        ApiClient apiClient2 = ApiUtil.getUser2Client();
        WorkflowClient workflowClient2 = new OrkesWorkflowClient(apiClient2);
        try {
            authorizationClient.deleteGroup("workflow-search-group");
        } catch (Exception e){}
        // Create user2 client and check access should not be there workflow1

        SearchResult<WorkflowSummary> workflowSummarySearchResult1 = workflowClient2.search("workflowType IN (" + workflowName1 + ")");
        // There should be no workflow in search.
        assertTrue(workflowSummarySearchResult1.getResults().size() == 0);

        // Create group and add these two users in the group
        Group group = authorizationClient.upsertGroup(getUpsertGroupRequest(), "workflow-search-group");
        authorizationClient.addUserToGroup("workflow-search-group", "conductoruser1@gmail.com");
        authorizationClient.addUserToGroup("workflow-search-group", "conductoruser2@gmail.com");

        // Give permissions to tag in the group
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setSubject(new SubjectRef().id("workflow-search-group").type(SubjectRef.TypeEnum.GROUP));
        authorizationRequest.setAccess(List.of(AuthorizationRequest.AccessEnum.READ));
        authorizationRequest.setTarget(new TargetRef().id("department:" + department).type(TargetRef.TypeEnum.TAG));
        authorizationClient.grantPermissions(authorizationRequest);

//        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
//            // Search should give results
//            SearchResult<WorkflowSummary> finalWorkflowSummarySearchResult = workflowClient2.search("workflowType IN (" + workflowName1 + ")");
//            // There should be 2 workflow in search.
//            assertTrue(finalWorkflowSummarySearchResult.getResults().size() == 2);
//        });

        metadataAdminClient.unregisterWorkflowDef(workflowName1, 1);
        metadataAdminClient.unregisterWorkflowDef(workflowName2, 1);
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .description("Group used for SDK testing")
                .roles(List.of(UpsertGroupRequest.RolesEnum.USER));
    }

    List<String> getAccessListAll() {
        return List.of("CREATE", "READ", "UPDATE", "EXECUTE", "DELETE");
    }

    private void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName(taskName);
        workflowTask.setName(taskName);
        workflowTask.setTaskDefinition(taskDef);
        workflowTask.setWorkflowTaskType(TaskType.SIMPLE);
        workflowTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(workflowTask));
        metadataClient1.registerWorkflowDef(workflowDef);
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef));
    }

}
