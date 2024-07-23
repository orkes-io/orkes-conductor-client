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
package io.orkes.conductor.client.http.clients;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ConflictException;
import io.orkes.conductor.client.model.BulkResponse;
import io.orkes.conductor.client.model.CorrelationIdsSearchRequest;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStateUpdate;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.model.metadata.workflow.RerunWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.SkipTaskRequest;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.UpgradeWorkflowRequest;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.client.model.run.WorkflowSummary;
import io.orkes.conductor.client.model.run.WorkflowTestRequest;

import com.google.common.base.Preconditions;

public class OrkesWorkflowClient extends OrkesClient implements AutoCloseable, WorkflowClient {

    private final WorkflowResource httpClient;

    private final WorkflowBulkResource bulkResource;

    private ExecutorService executorService;

    public OrkesWorkflowClient(OrkesHttpClient httpClient) {
        super(httpClient);
        this.httpClient = new WorkflowResource(httpClient);
        this.bulkResource = new WorkflowBulkResource(httpClient);

        int threadCount = httpClient.getExecutorThreadCount();
        if (threadCount < 1) {
            this.executorService = Executors.newCachedThreadPool();
        } else {
            this.executorService = Executors.newFixedThreadPool(threadCount);
        }
    }

    @Override
    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ConflictException {
        return httpClient.startWorkflow(startWorkflowRequest);
    }

    @Override
    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask) throws ConflictException {
        return executeWorkflowHttp(request, waitUntilTask);
    }

    @Override
    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Integer waitForSeconds) throws ConflictException {
        return executeWorkflowHttp(request, waitUntilTask, waitForSeconds);
    }

    @Override
    public WorkflowRun executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Duration waitTimeout) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<WorkflowRun> future = executeWorkflow(request, waitUntilTask);
        return future.get(waitTimeout.get(ChronoUnit.MILLIS), TimeUnit.MILLISECONDS);
    }

    private CompletableFuture<WorkflowRun> executeWorkflowHttp(StartWorkflowRequest startWorkflowRequest, String waitUntilTask) {
        CompletableFuture<WorkflowRun> future = new CompletableFuture<>();
        String requestId = UUID.randomUUID().toString();
        executorService.submit(
                () -> {
                    try {
                        WorkflowRun response =
                                httpClient.executeWorkflow(
                                        startWorkflowRequest,
                                        startWorkflowRequest.getName(),
                                        startWorkflowRequest.getVersion(),
                                        waitUntilTask,
                                        requestId);
                        future.complete(response);
                    } catch (Throwable t) {
                        future.completeExceptionally(t);
                    }
                });

        return future;
    }

    private CompletableFuture<WorkflowRun> executeWorkflowHttp(StartWorkflowRequest startWorkflowRequest, String waitUntilTask, Integer waitForSeconds) {
        CompletableFuture<WorkflowRun> future = new CompletableFuture<>();
        String requestId = UUID.randomUUID().toString();
        executorService.submit(
                () -> {
                    try {
                        WorkflowRun response =
                                httpClient.executeWorkflow(
                                        startWorkflowRequest,
                                        startWorkflowRequest.getName(),
                                        startWorkflowRequest.getVersion(),
                                        waitUntilTask,
                                        requestId,
                                        waitForSeconds);
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
        return bulkResource.terminate(workflowIds, reason, false);
    }

    @Override
    public List<String> getRunningWorkflow(String workflowName, Integer version) {
        return httpClient.getRunningWorkflow(workflowName, version, null, null);
    }

    @Override
    public List<String> getWorkflowsByTimePeriod(String workflowName, int version, Long startTime, Long endTime) {
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
        httpClient.terminateWithAReason(workflowId, reason, false);
    }

    @Override
    public void terminateWorkflowWithFailure(String workflowId, String reason, boolean triggerFailureWorkflow) {
        httpClient.terminateWithAReason(workflowId, reason, triggerFailureWorkflow);
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
        return bulkResource.pauseWorkflow1(workflowIds);
    }

    @Override
    public BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException {
        return bulkResource.restart1(workflowIds, useLatestDefinitions);
    }

    @Override
    public BulkResponse resumeWorkflow(List<String> workflowIds) throws ApiException {
        return bulkResource.resumeWorkflow1(workflowIds);
    }

    @Override
    public BulkResponse retryWorkflow(List<String> workflowIds) throws ApiException {
        return bulkResource.retry1(workflowIds);
    }

    @Override
    public BulkResponse terminateWorkflow(List<String> workflowIds, String reason)
            throws ApiException {
        return bulkResource.terminate(workflowIds, reason, false);
    }

    @Override
    public BulkResponse terminateWorkflowsWithFailure(List<String> workflowIds, String reason, boolean triggerFailureWorkflow)
            throws ApiException {
        return bulkResource.terminate(workflowIds, reason, triggerFailureWorkflow);
    }

    @Override
    public WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables) {
        return httpClient.getWorkflowStatusSummary(workflowId, includeOutput, includeVariables);
    }

    @Override
    public void uploadCompletedWorkflows() {
        httpClient.uploadCompletedWorkflows();
    }

    @Override
    public Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(
            List<String> correlationIds, List<String> workflowNames, Boolean includeClosed, Boolean includeTasks) {
        CorrelationIdsSearchRequest request = new CorrelationIdsSearchRequest(correlationIds, workflowNames);
        return httpClient.getWorkflowsByNamesAndCorrelationIds(request, includeClosed, includeTasks);
    }

    @Override
    public Workflow testWorkflow(WorkflowTestRequest testRequest) {
        return httpClient.testWorkflow(testRequest);
    }

    @Override
    public Workflow updateVariables(String workflowId, Map<String, Object> variables) {
        return httpClient.updateVariables(workflowId, variables);
    }

    @Override
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override
    public void upgradeRunningWorkflow(String workflowId, UpgradeWorkflowRequest upgradeWorkflowRequest) {
        httpClient.upgradeRunningWorkflow(upgradeWorkflowRequest, workflowId);
    }

    @Override
    public WorkflowRun updateWorkflow(String workflowId, List<String> waitUntilTaskRefNames, Integer waitForSeconds, WorkflowStateUpdate updateRequest) {
        String joinedReferenceNames = "";
        if (waitUntilTaskRefNames != null) {
            joinedReferenceNames = String.join(",", waitUntilTaskRefNames);
        }
        return httpClient.updateWorkflowState(updateRequest, UUID.randomUUID().toString(), workflowId, joinedReferenceNames, waitForSeconds);
    }

    @Override
    public void close() {
        shutdown();
    }
}
