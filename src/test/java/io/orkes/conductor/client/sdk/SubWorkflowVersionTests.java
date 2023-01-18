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

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.RegistrationUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class SubWorkflowVersionTests {

    @Test
    public void testSubWorkflowPermissionsForUser2() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowClient = new OrkesWorkflowClient(apiClient);
        MetadataClient metadataClient = new OrkesMetadataClient(apiClient);
        TaskClient taskClient = new OrkesTaskClient(apiClient);

        String taskName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String parentWorkflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        String subWorkflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

        // Register workflow
        RegistrationUtil.registerWorkflowWithSubWorkflowDef(parentWorkflowName, subWorkflowName, taskName, metadataClient);
        WorkflowDef workflowDef =  metadataClient.getWorkflowDef(parentWorkflowName, 1);
        //Set sub workflow version to 0
        workflowDef.getTasks().get(0).getSubWorkflowParam().setVersion(0);
        metadataClient.registerWorkflowDef(workflowDef, true);

        // Trigger workflow
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(parentWorkflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        // User1 should be able to complete task/workflow
        String subWorkflowId = workflowClient.getWorkflow(workflowId, true).getTasks().get(0).getSubWorkflowId();
        TaskResult taskResult  = new TaskResult();
        taskResult.setWorkflowInstanceId(subWorkflowId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setTaskId(workflowClient.getWorkflow(subWorkflowId, true).getTasks().get(0).getTaskId());
        taskClient.updateTask(taskResult);

        // Wait for workflow to get completed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, false);
            assertEquals(workflow1.getStatus().name(), WorkflowStatus.StatusEnum.COMPLETED.name());
        });

        // Cleanup
        metadataClient.unregisterWorkflowDef(parentWorkflowName, 1);
        metadataClient.unregisterWorkflowDef(subWorkflowName, 1);
        metadataClient.unregisterTaskDef(taskName);
    }
}
