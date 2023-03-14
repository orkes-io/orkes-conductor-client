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

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class TaskPayloadLimitTests {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static TaskClient taskClient;
    static MetadataClient metadataClient;

    List<String> workflowNames = new ArrayList<>();

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient  =new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }

    @Before
    public void initTest() {
        workflowNames = new ArrayList<>();
    }
    @After
    public void cleanUp() {
        try {
            for (String workflowName : workflowNames) {
                metadataClient.unregisterWorkflowDef(workflowName, 1);
            }
        } catch (Exception e) {}
    }

    @Test
    @DisplayName("Check task with max payload size")
    public void testTaskWithMaxPayloadSize() {

        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        registerWorkflowDef(workflowName, "simple", "sample", metadataClient);
        workflowNames.add(workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        String taskId = workflow.getTasks().get(1).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        char[] chars = new char[1024*1024];
        Arrays.fill(chars, 'f');
        taskResult.getOutputData().put("payload", chars);
        taskResult.setStatus(TaskResult.Status.COMPLETED);

        // Server does not allow more than 1 MB payload.
        assertThrows(ApiException.class, ()->taskClient.updateTask(taskResult));

        metadataClient.unregisterWorkflowDef(workflowName, 1);
        metadataClient.unregisterTaskDef("simple");
        metadataClient.unregisterTaskDef("sample");
    }
}
