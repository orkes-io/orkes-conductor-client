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
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class JavaSDKTests {

    @Test
    public void testSDK() {

        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient  =new OrkesMetadataClient(apiClient);
        String taskName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName1 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // Run workflow search it should return 0 result
        AtomicReference<SearchResult<WorkflowSummary>> workflowSummarySearchResult = new AtomicReference<>(workflowClient.search("workflowType IN (" + workflowName1 + ")"));
        assertEquals(workflowSummarySearchResult.get().getResults().size(), 0);

        // Register workflow
        registerWorkflowDef(workflowName1, taskName1, metadataClient);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName1);
        startWorkflowRequest.setVersion(1);

        workflowClient.startWorkflow(startWorkflowRequest);
        workflowClient.startWorkflow(startWorkflowRequest);
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowClient.search("workflowType IN (" + workflowName1 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 2;
        });

        // Register another workflow
        String taskName2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String workflowName2 = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName2, taskName2, metadataClient);

        startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName2);
        startWorkflowRequest.setVersion(1);

        // Trigger workflow
        workflowClient.startWorkflow(startWorkflowRequest);
        workflowClient.startWorkflow(startWorkflowRequest);
        // In search result when only this workflow searched 2 results should come
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowClient.search("workflowType IN (" + workflowName2 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 2;
        });

        // In search result when both workflow searched then 4 results should come
        await().pollInterval(100, TimeUnit.MILLISECONDS).until(() ->
        {
            workflowSummarySearchResult.set(workflowClient.search("workflowType IN (" + workflowName1 + "," + workflowName2 + ")"));
            return workflowSummarySearchResult.get().getResults().size() == 4;
        });
    }

    private void registerWorkflowDef(String workflowName, String taskName, MetadataClient metadataClient) {
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
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));
    }

}
