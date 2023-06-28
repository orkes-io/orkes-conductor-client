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
package io.orkes.conductor.client.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.SimpleWorker;

import com.google.common.util.concurrent.Uninterruptibles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkflowExecutionTests {
    WorkflowClient workflowResourceApi;
    TaskRunnerConfigurer taskRunnerConfigurer;

    @BeforeEach
    public void init() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        workflowResourceApi = new OrkesClients(apiClient).getWorkflowClient();
        TaskClient taskClient = new OrkesClients(apiClient).getTaskClient();
        Worker worker = new SimpleWorker();
        this.taskRunnerConfigurer =
                new TaskRunnerConfigurer.Builder(taskClient, Collections.singletonList(worker))
                        .withTaskThreadCount(Map.of(Commons.TASK_NAME, 10))
                        .build();
    }

    @Test
    @DisplayName("Test workflow completion")
    public void workflow() throws Exception {
        List<String> workflowIds = startWorkflows(2, Commons.WORKFLOW_NAME);
        workflowIds.add(startWorkflow(Commons.WORKFLOW_NAME));
        this.taskRunnerConfigurer.init();
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        workflowIds.forEach(workflowId -> validateCompletedWorkflow(workflowId));
        this.taskRunnerConfigurer.shutdown();
    }

    String startWorkflow(String workflowName) {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName(workflowName);
        return workflowResourceApi.startWorkflow(request);
    }

    List<String> startWorkflows(int quantity, String workflowName) {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        List<String> workflowIds = new ArrayList<>();
        for (int i = 0; i < quantity; i += 1) {
            String workflowId = workflowResourceApi.startWorkflow(startWorkflowRequest);
            workflowIds.add(workflowId);
        }
        return workflowIds;
    }

    void validateCompletedWorkflow(String workflowId) {
        WorkflowStatus workflowStatus =
                workflowResourceApi.getWorkflowStatusSummary(workflowId, false, false);
        assertEquals(WorkflowStatus.StatusEnum.COMPLETED, workflowStatus.getStatus());
    }
}
