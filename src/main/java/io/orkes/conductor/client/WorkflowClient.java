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
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ConflictException;
import io.orkes.conductor.client.model.BulkResponse;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStateUpdate;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.model.metadata.workflow.RerunWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.UpgradeWorkflowRequest;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.client.model.run.WorkflowSummary;
import io.orkes.conductor.client.model.run.WorkflowTestRequest;

public abstract class WorkflowClient  {

    public abstract String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ConflictException;

    @Deprecated
    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask);

    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Integer waitForSeconds);

    public abstract WorkflowRun executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Duration waitTimeout) throws ExecutionException, InterruptedException, TimeoutException;

    public abstract SearchResult<WorkflowSummary> search(String query);

    public abstract SearchResult<Workflow> searchV2(String query);

    public abstract SearchResult<WorkflowSummary> search(
            Integer start, Integer size, String sort, String freeText, String query);

    public abstract SearchResult<Workflow> searchV2(
            Integer start, Integer size, String sort, String freeText, String query);

    public abstract BulkResponse pauseWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException;

    public abstract BulkResponse resumeWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse retryWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse terminateWorkflow(List<String> workflowIds, String reason)
            throws ApiException;

    public abstract Workflow getWorkflow(String workflowId, boolean includeTasks);

    public abstract List<Workflow> getWorkflows(
            String name, String correlationId, boolean includeClosed, boolean includeTasks);

    public abstract void deleteWorkflow(String workflowId, boolean archiveWorkflow);

    public abstract BulkResponse terminateWorkflows(List<String> workflowIds, String reason);

    public abstract List<String> getRunningWorkflow(String workflowName, Integer version);

    public abstract List<String> getWorkflowsByTimePeriod(
            String workflowName, int version, Long startTime, Long endTime);

    public abstract void runDecider(String workflowId);

    public abstract void pauseWorkflow(String workflowId);

    public abstract void resumeWorkflow(String workflowId);

    public abstract void skipTaskFromWorkflow(String workflowId, String taskReferenceName);

    public abstract String rerunWorkflow(String workflowId, RerunWorkflowRequest rerunWorkflowRequest);

    public abstract void restart(String workflowId, boolean useLatestDefinitions);

    public abstract void retryLastFailedTask(String workflowId);

    public abstract void resetCallbacksForInProgressTasks(String workflowId);

    public abstract void terminateWorkflow(String workflowId, String reason);

    public abstract void terminateWorkflowWithFailure(String workflowId, String reason, boolean triggerWorkflowFailure)
            throws ApiException;

    public abstract BulkResponse terminateWorkflowsWithFailure(List<String> workflowIds, String reason, boolean triggerWorkflowFailure)
            throws ApiException;

    public abstract WorkflowStatus getWorkflowStatusSummary(String workflowId, Boolean includeOutput, Boolean includeVariables);

    /**
     * Search workflows based on correlation ids and names
     * @param correlationIds List of correlation ids to search
     * @param workflowNames List of workflow names to search
     * @param includeClosed if set, includes workflows that are terminal.  Otherwise, only returns RUNNING workflows
     * @param includeTasks if set, returns tasks.
     * @return Map with a key as correlation id and value as a list of matching workflow executions
     */
    public abstract Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(
            List<String> correlationIds, List<String> workflowNames, Boolean includeClosed, Boolean includeTasks);

    public abstract void shutdown();

    public abstract void uploadCompletedWorkflows();

    /**
     * Integration test your workflow using mock data
     * @param testRequest Workflow Start Request with test data
     * @return Workflow
     */
    public abstract Workflow testWorkflow(WorkflowTestRequest testRequest);

    /**
     * Update the workflow by setting variables as given.  This is similar to SET_VARIABLE task except that with
     * this API, the workflow variables can be updated any anytime while the workflow is in RUNNING state.
     * This API is useful for cases where the state of the workflow needs to be updated based on an external trigger,
     * such as terminate a long-running do_while loop with a terminating condition based on the workflow variables.
     *
     * @param workflowId Id of the workflow
     * @param variables Workflow variables.  The variables are merged with existing variables.
     * @return Updated state of the workflow
     */
    public abstract Workflow updateVariables(String workflowId, Map<String, Object> variables);

    public abstract void upgradeRunningWorkflow(String workflowId, UpgradeWorkflowRequest body);

    /**
     *
     * Update a runningw workflow by updating its variables or one of the scheduled task identified by task reference name
     * @param workflowId Id of the workflow to be updated
     * @param waitUntilTaskRefNames List of task reference names to wait for.  The api call will  wait for ANY of these tasks to be availble in workflow.
     * @param waitForSeconds Maximum time to wait for.  If the workflow does not complete or reach one of the tasks listed  in waitUntilTaskRefNames by this time,
     *                       the call will return with the current status of the workflow
     * @param updateRequest Payload for updating state of workflow.
     *
     * @return Returns updated workflow execution
     */
    public abstract WorkflowRun updateWorkflow(String workflowId, List<String> waitUntilTaskRefNames, Integer waitForSeconds,
        WorkflowStateUpdate updateRequest);
}
