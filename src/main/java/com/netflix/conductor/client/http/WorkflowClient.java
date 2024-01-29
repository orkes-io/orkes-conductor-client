/*
 * Copyright 2021 Orkes, Inc.
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
package com.netflix.conductor.client.http;

import java.util.List;

import com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.model.BulkResponse;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;
import com.netflix.conductor.common.run.WorkflowTestRequest;
import com.netflix.conductor.common.utils.ExternalPayloadStorage;

import io.orkes.conductor.client.http.ConflictException;

public abstract class WorkflowClient {

    /** Creates a default workflow client */
    public WorkflowClient() {

    }



    /**
     * Starts a workflow. If the size of the workflow input payload is bigger than {@link
     * ExternalPayloadStorage}, if enabled, else the workflow is rejected.
     *
     * @param startWorkflowRequest the {@link StartWorkflowRequest} object to start the workflow
     * @return the id of the workflow instance that can be used for tracking
     */
    public abstract String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ConflictException;

    /**
     * Retrieve a workflow by workflow id
     *
     * @param workflowId the id of the workflow
     * @param includeTasks specify if the tasks in the workflow need to be returned
     * @return the requested workflow
     */
    public abstract Workflow getWorkflow(String workflowId, boolean includeTasks);

    /**
     * Retrieve all workflows for a given correlation id and name
     *
     * @param name the name of the workflow
     * @param correlationId the correlation id
     * @param includeClosed specify if all workflows are to be returned or only running workflows
     * @param includeTasks specify if the tasks in the workflow need to be returned
     * @return list of workflows for the given correlation id and name
     */
    public abstract List<Workflow> getWorkflows(
            String name, String correlationId, boolean includeClosed, boolean includeTasks);


    /**
     * Removes a workflow from the system
     *
     * @param workflowId the id of the workflow to be deleted
     * @param archiveWorkflow flag to indicate if the workflow should be archived before deletion
     */
    public abstract void deleteWorkflow(String workflowId, boolean archiveWorkflow);

    /**
     * Terminates the execution of all given workflows instances
     *
     * @param workflowIds the ids of the workflows to be terminated
     * @param reason the reason to be logged and displayed
     * @return the {@link BulkResponse} contains bulkErrorResults and bulkSuccessfulResults
     */
    public abstract BulkResponse terminateWorkflows(List<String> workflowIds, String reason);

    /**
     * Retrieve all running workflow instances for a given name and version
     *
     * @param workflowName the name of the workflow
     * @param version the version of the wokflow definition. Defaults to 1.
     * @return the list of running workflow instances
     */
    public abstract List<String> getRunningWorkflow(String workflowName, Integer version);

    /**
     * Retrieve all workflow instances for a given workflow name between a specific time period
     *
     * @param workflowName the name of the workflow
     * @param version the version of the workflow definition. Defaults to 1.
     * @param startTime the start time of the period
     * @param endTime the end time of the period
     * @return returns a list of workflows created during the specified during the time period
     */
    public abstract List<String> getWorkflowsByTimePeriod(
            String workflowName, int version, Long startTime, Long endTime);

    /**
     * Starts the decision task for the given workflow instance
     *
     * @param workflowId the id of the workflow instance
     */
    public abstract void runDecider(String workflowId);

    /**
     * Pause a workflow by workflow id
     *
     * @param workflowId the workflow id of the workflow to be paused
     */
    public abstract void pauseWorkflow(String workflowId);

    /**
     * Resume a paused workflow by workflow id
     *
     * @param workflowId the workflow id of the paused workflow
     */
    public abstract void resumeWorkflow(String workflowId);

    /**
     * Skips a given task from a current RUNNING workflow
     *
     * @param workflowId the id of the workflow instance
     * @param taskReferenceName the reference name of the task to be skipped
     */
    public abstract void skipTaskFromWorkflow(String workflowId, String taskReferenceName);

    /**
     * Reruns the workflow from a specific task
     *
     * @param workflowId the id of the workflow
     * @param rerunWorkflowRequest the request containing the task to rerun from
     * @return the id of the workflow
     */
    public abstract String rerunWorkflow(String workflowId, RerunWorkflowRequest rerunWorkflowRequest);

    /**
     * Restart a completed workflow
     *
     * @param workflowId the workflow id of the workflow to be restarted
     * @param useLatestDefinitions if true, use the latest workflow and task definitions when
     *     restarting the workflow if false, use the workflow and task definitions embedded in the
     *     workflow execution when restarting the workflow
     */
    public abstract void restart(String workflowId, boolean useLatestDefinitions);

    /**
     * Retries the last failed task in a workflow
     *
     * @param workflowId the workflow id of the workflow with the failed task
     */
    public abstract void retryLastFailedTask(String workflowId);

    /**
     * Resets the callback times of all IN PROGRESS tasks to 0 for the given workflow
     *
     * @param workflowId the id of the workflow
     */
    public abstract void resetCallbacksForInProgressTasks(String workflowId);

    /**
     * Terminates the execution of the given workflow instance
     *
     * @param workflowId the id of the workflow to be terminated
     * @param reason the reason to be logged and displayed
     */
    public abstract void terminateWorkflow(String workflowId, String reason);

    /**
     * Search for workflows based on payload
     *
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link WorkflowSummary} that match the query
     */
    public abstract SearchResult<WorkflowSummary> search(String query);

    /**
     * Search for workflows based on payload
     *
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link Workflow} that match the query
     */
    public abstract SearchResult<Workflow> searchV2(String query);

    /**
     * Paginated search for workflows based on payload
     *
     * @param start start value of page
     * @param size number of workflows to be returned
     * @param sort sort order
     * @param freeText additional free text query
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link WorkflowSummary} that match the query
     */
    public abstract SearchResult<WorkflowSummary> search(
            Integer start, Integer size, String sort, String freeText, String query);

    /**
     * Paginated search for workflows based on payload
     *
     * @param start start value of page
     * @param size number of workflows to be returned
     * @param sort sort order
     * @param freeText additional free text query
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link Workflow} that match the query
     */
    public abstract SearchResult<Workflow> searchV2(
            Integer start, Integer size, String sort, String freeText, String query);
    public abstract Workflow testWorkflow(WorkflowTestRequest testRequest);
}
