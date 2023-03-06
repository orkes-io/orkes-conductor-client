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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class GraaljsTests {
    static ApiClient apiClient;
    static io.orkes.conductor.client.WorkflowClient workflowClient;
    static io.orkes.conductor.client.TaskClient taskClient;
    static io.orkes.conductor.client.MetadataClient metadataClient;

    List<String> workflowNames = new ArrayList<>();

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient  =new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }

    @Test
    public void testInfiniteExecution() throws ExecutionException, InterruptedException, TimeoutException {
        String workflowName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // Register workflow
        registerWorkflowDef(workflowName, "simple", "sample", metadataClient);
        WorkflowDef workflowDef = metadataClient.getWorkflowDef(workflowName, 1);
        workflowDef.getTasks().get(0).setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "function e() { while(true){} }; e();"));
        metadataClient.registerWorkflowDef(workflowDef, true);
        workflowNames.add(workflowName);

        // Trigger two workflows
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        // Wait for workflow to get failed since inline task will failed
        await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertEquals(workflow.getStatus().name(), WorkflowStatus.StatusEnum.FAILED.name());
        });
    }

    @After
    public void cleanUp() {
        try {
            for (String workflowName : workflowNames) {
                metadataClient.unregisterWorkflowDef(workflowName, 1);
            }
        } catch (Exception e) {}
    }


}
