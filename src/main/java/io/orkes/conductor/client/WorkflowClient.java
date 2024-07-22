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

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.UpgradeWorkflowRequest;
import com.netflix.conductor.common.model.BulkResponse;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowTestRequest;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.WorkflowStateUpdate;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.common.model.WorkflowRun;

public abstract class WorkflowClient extends com.netflix.conductor.client.http.WorkflowClient {

    @Deprecated
    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask);

    /**
     * Synchronously executes a workflow
     * @param request workflow execution request
     * @param waitUntilTask waits until workflow has reached this task.
     *                      Useful for executing it synchronously until this task and then continuing asynchronous execution
     * @param waitForSeconds maximum amount of time to wait before returning
     * @return WorkflowRun
     */
    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Integer waitForSeconds);

    /**
     * Synchronously executes a workflow
     * @param request workflow execution request
     * @param waitUntilTasks waits until workflow has reached one of these tasks.
     *                       Useful for executing it synchronously until this task and then continuing asynchronous execution
     *                       Useful when workflow has multiple branches to wait for any of the branches to reach the task
     * @param waitForSeconds maximum amount of time to wait before returning
     * @return WorkflowRun
     */
    public abstract CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request, List<String> waitUntilTasks, Integer waitForSeconds);

    /**
     * Synchronously executes a workflow
     * @param request workflow execution request
     * @param waitUntilTask waits until workflow has reached one of these tasks.
     *                       Useful for executing it synchronously until this task and then continuing asynchronous execution
     * @param waitTimeout maximum amount of time to wait before returning
     * @return WorkflowRun
     */
    public abstract WorkflowRun executeWorkflow(StartWorkflowRequest request, String waitUntilTask, Duration waitTimeout) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * Pause a running workflow - no new tasks are scheduled until resumed
     * @param workflowIds ids of the workflow to be paused
     * @return
     * @throws ApiException
     */
    public abstract BulkResponse pauseWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException;

    public abstract BulkResponse resumeWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse retryWorkflow(List<String> workflowIds) throws ApiException;

    public abstract BulkResponse terminateWorkflow(List<String> workflowIds, String reason)
            throws ApiException;

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

    public abstract void upgradeRunningWorkflow(String workflowId, UpgradeWorkflowRequest request);

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
