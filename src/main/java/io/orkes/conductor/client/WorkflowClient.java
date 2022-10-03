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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.model.BulkResponse;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStatus;

public interface WorkflowClient {
    /**
     * Start a new workflow execution
     *
     * @param startWorkflowRequest start workflow request
     * @return Id of the workflow that was started. {@link #getWorkflow(String, boolean)} can be
     *     used to get the status of this workflow execution
     */
    String startWorkflow(StartWorkflowRequest startWorkflowRequest);

    /**
     * Starts a new workflow and waits until it completes.
     *
     * @param startWorkflowRequest start workflow request
     * @param waitUntilTaskRef Wait until the task identified by the reference name completes and
     *     return back the results. Useful when part of the workflow needs to be synchronous and
     *     rest can be monitored asynchronously.
     * @return
     */
    CompletableFuture<WorkflowRun> executeWorkflow(
            StartWorkflowRequest startWorkflowRequest, String waitUntilTaskRef);

    /**
     * Get the workflow execution
     *
     * @param workflowId Id of the workflow
     * @param includeTasks if false, no tasks are returned only input, output and variables.
     * @return
     */
    Workflow getWorkflow(String workflowId, boolean includeTasks);

    /**
     * Return workflows matching the correlation id
     *
     * @param name workflow name
     * @param correlationId correlation id
     * @param includeCompleted if the result should include 
     * @param includeTasks
     * @return
     */
    List<Workflow> getWorkflows(
            String name, String correlationId, boolean includeCompleted, boolean includeTasks);

    /**
     * Removes the workflow from the system permenantly
     *
     * @param workflowId
     * @param archiveWorkflow
     */
    void deleteWorkflow(String workflowId, boolean archiveWorkflow);

    /**
     * Terminates the workflows
     *
     * @param workflowIds
     * @param reason
     * @return
     */
    BulkResponse terminateWorkflows(List<String> workflowIds, String reason);

    /**
     * Return currently running workflow ids
     *
     * @param workflowName
     * @param version
     * @return
     */
    List<String> getRunningWorkflow(String workflowName, Integer version);

    /**
     * Return the ids of the workflow that were started in the given time period
     *
     * @param workflowName
     * @param version
     * @param startTime
     * @param endTime
     * @return
     */
    List<String> getWorkflowsByTimePeriod(
            String workflowName, int version, Long startTime, Long endTime);

    /**
     * Forces workflow state evaluation. Use with Caution, meant only for advanced use cases.
     *
     * @param workflowId
     */
    void runDecider(String workflowId);

    /**
     * Pause a running workflow. No New tasks will be scheduled until resume is called
     *
     * @param workflowId
     */
    void pauseWorkflow(String workflowId);

    /**
     * Resume a paused workflow
     *
     * @param workflowId
     */
    void resumeWorkflow(String workflowId);

    /**
     *
     * @param workflowId
     * @param taskReferenceName
     */
    void skipTaskFromWorkflow(String workflowId, String taskReferenceName);

    String rerunWorkflow(String workflowId, RerunWorkflowRequest rerunWorkflowRequest);

    void restart(String workflowId, boolean useLatestDefinitions);

    void retryLastFailedTask(String workflowId);

    void resetCallbacksForInProgressTasks(String workflowId);

    void terminateWorkflow(String workflowId, String reason);

    SearchResult<WorkflowSummary> search(String query);

    SearchResult<Workflow> searchV2(String query);

    SearchResult<WorkflowSummary> search(
            Integer start, Integer size, String sort, String freeText, String query);

    SearchResult<Workflow> searchV2(
            Integer start, Integer size, String sort, String freeText, String query);

    // Bulk operations
    BulkResponse pauseWorkflow(List<String> workflowIds);

    BulkResponse restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions);

    BulkResponse resumeWorkflow(List<String> workflowIds);

    BulkResponse retryWorkflow(List<String> workflowIds);

    BulkResponse terminateWorkflow(List<String> workflowIds, String reason);

    WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables);
}
