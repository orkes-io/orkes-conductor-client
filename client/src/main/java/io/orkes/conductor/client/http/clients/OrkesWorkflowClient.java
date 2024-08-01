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

import io.orkes.conductor.client.api.WorkflowClient;
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

//TODO reset workflow?
public class OrkesWorkflowClient extends OrkesClient implements AutoCloseable, WorkflowClient {

    private final WorkflowResource workflowResource;

    private final WorkflowBulkResource bulkResource;

    private final ExecutorService executorService;

    public OrkesWorkflowClient(OrkesHttpClient httpClient) {
        this(httpClient, 0);
    }

    public OrkesWorkflowClient(OrkesHttpClient httpClient, int executorThreadCount) {
        super(httpClient);
        this.workflowResource = new WorkflowResource(httpClient);
        this.bulkResource = new WorkflowBulkResource(httpClient);
        if (executorThreadCount < 1) {
            this.executorService = Executors.newCachedThreadPool();
        } else {
            this.executorService = Executors.newFixedThreadPool(executorThreadCount);
        }
    }

    @Override
    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ConflictException {
        return workflowResource.startWorkflow(startWorkflowRequest);
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
                        WorkflowRun response = workflowResource.executeWorkflow(
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
                        WorkflowRun response = workflowResource.executeWorkflow(
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
        return workflowResource.getExecutionStatus(workflowId, includeTasks);
    }

    @Override
    public List<Workflow> getWorkflows(String name, String correlationId, boolean includeClosed, boolean includeTasks) {
        return workflowResource.getWorkflowsByCorrelationId(name, correlationId, includeClosed, includeTasks);
    }

    @Override
    public void deleteWorkflow(String workflowId, boolean archiveWorkflow) {
        workflowResource.delete(workflowId, archiveWorkflow);
    }

    @Override
    public BulkResponse terminateWorkflows(List<String> workflowIds, String reason) {
        //FIXME should we use Preconditions across the board?
        Preconditions.checkArgument(!workflowIds.isEmpty(), "workflow id cannot be blank");
        return bulkResource.terminateWorkflows(workflowIds, reason, false);
    }

    @Override
    public List<String> getRunningWorkflow(String workflowName, Integer version) {
        return workflowResource.getRunningWorkflow(workflowName, version, null, null);
    }

    @Override
    public List<String> getWorkflowsByTimePeriod(String workflowName, int version, Long startTime, Long endTime) {
        return workflowResource.getRunningWorkflow(workflowName, version, startTime, endTime);
    }

    @Override
    public void runDecider(String workflowId) {
        workflowResource.decide(workflowId);
    }

    @Override
    public void pauseWorkflow(String workflowId) {
        workflowResource.pauseWorkflow(workflowId);
    }

    @Override
    public void resumeWorkflow(String workflowId) {
        workflowResource.resumeWorkflow(workflowId);
    }

    @Override
    public void skipTaskFromWorkflow(String workflowId, String taskReferenceName) {
        Preconditions.checkArgument(
                StringUtils.isNotBlank(workflowId), "workflow id cannot be blank");
        Preconditions.checkArgument(
                StringUtils.isNotBlank(taskReferenceName), "Task reference name cannot be blank");
        SkipTaskRequest skipTaskRequest = new SkipTaskRequest();
        workflowResource.skipTaskFromWorkflow(workflowId, taskReferenceName, skipTaskRequest);
    }

    @Override
    public String rerunWorkflow(String workflowId, RerunWorkflowRequest rerunWorkflowRequest) {
        return workflowResource.rerun(rerunWorkflowRequest, workflowId);
    }

    @Override
    public void restart(String workflowId, boolean useLatestDefinitions) {
        workflowResource.restart(workflowId, useLatestDefinitions);
    }

    @Override
    public void retryLastFailedTask(String workflowId) {
        workflowResource.retry(workflowId, true);
    }

    //FIXME why keep this?
    @Override
    public void resetCallbacksForInProgressTasks(String workflowId) {
        throw new UnsupportedOperationException("This call is not required");
    }

    @Override
    public void terminateWorkflow(String workflowId, String reason) {
        workflowResource.terminateWithAReason(workflowId, reason, false);
    }

    @Override
    public void terminateWorkflowWithFailure(String workflowId, String reason, boolean triggerFailureWorkflow) {
        workflowResource.terminateWithAReason(workflowId, reason, triggerFailureWorkflow);
    }

    @Override
    public SearchResult<WorkflowSummary> search(String query) {
        return workflowResource.search(null, null, null, null, "", query, null);
    }

    //FIXME why keep this?
    @Override
    public SearchResult<Workflow> searchV2(String query) {
        throw new UnsupportedOperationException("Please use search() API");
    }

    @Override
    public SearchResult<WorkflowSummary> search(
            Integer start, Integer size, String sort, String freeText, String query) {
        return workflowResource.search(null, start, size, sort, freeText, query, null);
    }

    //FIXME why keep this?
    @Override
    public SearchResult<Workflow> searchV2(
            Integer start, Integer size, String sort, String freeText, String query) {
        throw new UnsupportedOperationException("Please use search() API");
    }

    @Override
    public BulkResponse pauseWorkflow(List<String> workflowIds) {
        return bulkResource.pauseWorkflows(workflowIds);
    }

    @Override
    public BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions) {
        return bulkResource.restartWorkflows(workflowIds, useLatestDefinitions);
    }

    @Override
    public BulkResponse resumeWorkflow(List<String> workflowIds) {
        return bulkResource.resumeWorkflows(workflowIds);
    }

    @Override
    public BulkResponse retryWorkflow(List<String> workflowIds) {
        return bulkResource.retryWorkflows(workflowIds);
    }

    @Override
    public BulkResponse terminateWorkflow(List<String> workflowIds, String reason) {
        return bulkResource.terminateWorkflows(workflowIds, reason, false);
    }

    @Override
    public BulkResponse terminateWorkflowsWithFailure(List<String> workflowIds, String reason, boolean triggerFailureWorkflow) {
        return bulkResource.terminateWorkflows(workflowIds, reason, triggerFailureWorkflow);
    }

    @Override
    public WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables) {
        return workflowResource.getWorkflowStatusSummary(workflowId, includeOutput, includeVariables);
    }

    @Override
    public void uploadCompletedWorkflows() {
        workflowResource.uploadCompletedWorkflows();
    }

    @Override
    public Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(
            List<String> correlationIds, List<String> workflowNames, Boolean includeClosed, Boolean includeTasks) {
        CorrelationIdsSearchRequest request = new CorrelationIdsSearchRequest(correlationIds, workflowNames);
        return workflowResource.getWorkflowsByNamesAndCorrelationIds(request, includeClosed, includeTasks);
    }

    @Override
    public Workflow testWorkflow(WorkflowTestRequest testRequest) {
        return workflowResource.testWorkflow(testRequest);
    }

    @Override
    public Workflow updateVariables(String workflowId, Map<String, Object> variables) {
        return workflowResource.updateVariables(workflowId, variables);
    }

    @Override
    public void upgradeRunningWorkflow(String workflowId, UpgradeWorkflowRequest upgradeWorkflowRequest) {
        workflowResource.upgradeRunningWorkflow(upgradeWorkflowRequest, workflowId);
    }

    @Override
    public WorkflowRun updateWorkflow(String workflowId, List<String> waitUntilTaskRefNames, Integer waitForSeconds, WorkflowStateUpdate updateRequest) {
        String joinedReferenceNames = "";
        if (waitUntilTaskRefNames != null) {
            joinedReferenceNames = String.join(",", waitUntilTaskRefNames);
        }
        return workflowResource.updateWorkflowState(updateRequest, UUID.randomUUID().toString(), workflowId, joinedReferenceNames, waitForSeconds);
    }

    @Override
    public void close() {
        shutdown();
    }

    @Override
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
