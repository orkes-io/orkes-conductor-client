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
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.common.model.WorkflowRun;
import io.orkes.conductor.proto.WorkflowRunPb;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowWithSubWorkflowDef;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class SyncWorkflowExecutionTest {

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
    @DisplayName("Check workflow with simple task and rerun functionality")
    public void testRerunSimpleWorkflow() {

        String workflowName = "load_test_perf";
        // Register workflow
        workflowNames.add(workflowName);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null);
        try {
            WorkflowRun workflowRun = completableFuture.get(3, TimeUnit.SECONDS);
            System.out.println("WorkflowId is " + workflowRun.getWorkflowId());
            assertEquals(Workflow.WorkflowStatus.COMPLETED, workflowRun.getStatus());
        } catch (Exception e) {
            throw new RuntimeException("Workflow " + workflowName + " did not complete in 3 seconds");
        }

    }
}
