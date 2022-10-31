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

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.model.BulkResponse;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.common.model.WorkflowRun;

public abstract class WorkflowClient extends com.netflix.conductor.client.http.WorkflowClient {
    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask);

    public abstract WorkflowRun executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Duration waitTimeout) throws ExecutionException, InterruptedException, TimeoutException;

    public abstract BulkResponse pauseWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException;

    public abstract BulkResponse resumeWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse retryWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse terminateWorkflow(List<String> workflowIds, String reason)
            throws ApiException;

    public abstract WorkflowStatus getWorkflowStatusSummary(String workflowId, Boolean includeOutput, Boolean includeVariables);

    public abstract void shutdown();
}
