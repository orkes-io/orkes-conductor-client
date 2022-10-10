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
package io.orkes.conductor.client.http;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

import com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.SkipTaskRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.model.BulkResponse;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.api.AsyncApiCallback;
import io.orkes.conductor.client.http.api.WorkflowBulkResourceApi;
import io.orkes.conductor.client.http.api.WorkflowResourceApi;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStatus;

import com.google.common.base.Preconditions;

public class OrkesWorkflowClient extends OrkesClient implements WorkflowClient {

    private final WorkflowResourceApi httpClient;

    private final WorkflowBulkResourceApi bulkResourceApi;

    private final ExecutorService executorService;

    public OrkesWorkflowClient(ApiClient apiClient) {
        super(apiClient);
        this.httpClient = new WorkflowResourceApi(apiClient);
        this.bulkResourceApi = new WorkflowBulkResourceApi(apiClient);
        this.executorService = Executors.newFixedThreadPool(apiClient.getExecutorThreads());
    }

    @Override
    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) {
        return httpClient.startWorkflow(startWorkflowRequest);
    }

    @Override
    public CompletableFuture<WorkflowRun> executeWorkflow(
            StartWorkflowRequest startWorkflowRequest, String waitUntilTaskRef) {
        CompletableFuture<WorkflowRun> future = new CompletableFuture<>();
        AsyncApiCallback<WorkflowRun> callback = new AsyncApiCallback<>(future);
        String requestId = UUID.randomUUID().toString();
        executorService.submit(
                () -> {
                    try {
                        WorkflowRun response =
                                httpClient.executeWorkflow(
                                        startWorkflowRequest,
                                        startWorkflowRequest.getName(),
                                        startWorkflowRequest.getVersion(),
                                        waitUntilTaskRef,
                                        requestId);
                        future.complete(response);
                    } catch (Throwable t) {
                        future.completeExceptionally(t);
                    }
                });

        return future;
    }

    @Override
    public Workflow getWorkflow(String workflowId, boolean includeTasks) {
        return httpClient.getExecutionStatus(workflowId, includeTasks);
    }

    @Override
    public List<Workflow> getWorkflows(
            String name, String correlationId, boolean includeClosed, boolean includeTasks) {
        return httpClient.getWorkflowsByCorrelationId(
                name, correlationId, includeClosed, includeTasks);
    }

    @Override
    public void deleteWorkflow(String workflowId, boolean archiveWorkflow) {
        httpClient.delete(workflowId, archiveWorkflow);
    }

    @Override
    public BulkResponse terminateWorkflows(List<String> workflowIds, String reason) {
        Preconditions.checkArgument(!workflowIds.isEmpty(), "workflow id cannot be blank");
        return bulkResourceApi.terminate(workflowIds, reason);
    }

    @Override
    public List<String> getRunningWorkflow(String workflowName, Integer version) {
        return httpClient.getRunningWorkflow(workflowName, version, null, null);
    }

    @Override
    public List<String> getWorkflowsByTimePeriod(
            String workflowName, int version, Long startTime, Long endTime) {
        return httpClient.getRunningWorkflow(workflowName, version, startTime, endTime);
    }

    @Override
    public void runDecider(String workflowId) {
        httpClient.decide(workflowId);
    }

    @Override
    public void pauseWorkflow(String workflowId) {
        httpClient.pauseWorkflow(workflowId);
    }

    @Override
    public void resumeWorkflow(String workflowId) {
        httpClient.resumeWorkflow(workflowId);
    }

    @Override
    public void skipTaskFromWorkflow(String workflowId, String taskReferenceName) {
        Preconditions.checkArgument(
                StringUtils.isNotBlank(workflowId), "workflow id cannot be blank");
        Preconditions.checkArgument(
                StringUtils.isNotBlank(taskReferenceName), "Task reference name cannot be blank");
        SkipTaskRequest skipTaskRequest = new SkipTaskRequest();
        httpClient.skipTaskFromWorkflow(workflowId, taskReferenceName, skipTaskRequest);
    }

    @Override
    public String rerunWorkflow(String workflowId, RerunWorkflowRequest rerunWorkflowRequest) {
        return httpClient.rerun(rerunWorkflowRequest, workflowId);
    }

    @Override
    public void restart(String workflowId, boolean useLatestDefinitions) {
        httpClient.restart(workflowId, useLatestDefinitions);
    }

    @Override
    public void retryLastFailedTask(String workflowId) {
        httpClient.retry(workflowId, true);
    }

    @Override
    public void resetCallbacksForInProgressTasks(String workflowId) {
        throw new UnsupportedOperationException("This call is not required");
    }

    @Override
    public void terminateWorkflow(String workflowId, String reason) {
        httpClient.terminateWithAReason(workflowId, reason);
    }

    @Override
    public SearchResult<WorkflowSummary> search(String query) {
        return httpClient.search(null, null, null, null, "", query, null);
    }

    @Override
    public SearchResult<Workflow> searchV2(String query) {
        throw new UnsupportedOperationException("Please use search() API");
    }

    @Override
    public SearchResult<WorkflowSummary> search(
            Integer start, Integer size, String sort, String freeText, String query) {
        return httpClient.search(null, start, size, sort, freeText, query, null);
    }

    @Override
    public SearchResult<Workflow> searchV2(
            Integer start, Integer size, String sort, String freeText, String query) {
        throw new UnsupportedOperationException("Please use search() API");
    }

    @Override
    public BulkResponse pauseWorkflow(List<String> workflowIds) throws ApiException {
        return bulkResourceApi.pauseWorkflow1(workflowIds);
    }

    @Override
    public BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException {
        return bulkResourceApi.restart1(workflowIds, useLatestDefinitions);
    }

    @Override
    public BulkResponse resumeWorkflow(List<String> workflowIds) throws ApiException {
        return bulkResourceApi.resumeWorkflow1(workflowIds);
    }

    @Override
    public BulkResponse retryWorkflow(List<String> workflowIds) throws ApiException {
        return bulkResourceApi.retry1(workflowIds);
    }

    @Override
    public BulkResponse terminateWorkflow(List<String> workflowIds, String reason)
            throws ApiException {
        return bulkResourceApi.terminate(workflowIds, reason);
    }

    @Override
    public WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables) {
        return httpClient.getWorkflowStatusSummary(workflowId, includeOutput, includeVariables);
    }

    @Override
    public void shutdown() {
        this.executorService.shutdown();
    }
}
