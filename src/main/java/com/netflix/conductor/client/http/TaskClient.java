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
package com.netflix.conductor.client.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.netflix.conductor.common.metadata.tasks.PollData;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.utils.ExternalPayloadStorage;

import io.orkes.conductor.client.model.TaskSummary;

/** Client for conductor task management including polling for task, updating task status etc. */
public abstract class TaskClient {



    /** Creates a default task client */
    public TaskClient() {

    }

    /**
     * Perform a poll for a task of a specific task type.
     *
     * @param taskType The taskType to poll for
     * @param domain The domain of the task type
     * @param workerId Name of the client worker. Used for logging.
     * @return Task waiting to be executed.
     */
    public abstract Task pollTask(String taskType, String workerId, String domain);
    /**
     * Perform a batch poll for tasks by task type. Batch size is configurable by count.
     *
     * @param taskType Type of task to poll for
     * @param workerId Name of the client worker. Used for logging.
     * @param count Maximum number of tasks to be returned. Actual number of tasks returned can be
     *     less than this number.
     * @param timeoutInMillisecond Long poll wait timeout.
     * @return List of tasks awaiting to be executed.
     */
    public abstract List<Task> batchPollTasksByTaskType(
            String taskType, String workerId, int count, int timeoutInMillisecond);

    /**
     * Batch poll for tasks in a domain. Batch size is configurable by count.
     *
     * @param taskType Type of task to poll for
     * @param domain The domain of the task type
     * @param workerId Name of the client worker. Used for logging.
     * @param count Maximum number of tasks to be returned. Actual number of tasks returned can be
     *     less than this number.
     * @param timeoutInMillisecond Long poll wait timeout.
     * @return List of tasks awaiting to be executed.
     */
    public abstract List<Task> batchPollTasksInDomain(
            String taskType, String domain, String workerId, int count, int timeoutInMillisecond);

    /**


    /**
     * Updates the result of a task execution. If the size of the task output payload is bigger than
     * {@link ExternalPayloadStorage}, if enabled, else the task is marked as
     * FAILED_WITH_TERMINAL_ERROR.
     *
     * @param taskResult the {@link TaskResult} of the executed task to be updated.
     */
    public abstract void updateTask(TaskResult taskResult);

    public abstract Optional<String> evaluateAndUploadLargePayload(
            Map<String, Object> taskOutputData, String taskType);

    /**
     * Ack for the task poll.
     *
     * @param taskId Id of the task to be polled
     * @param workerId user identified worker.
     * @return true if the task was found with the given ID and acknowledged. False otherwise. If
     *     the server returns false, the client should NOT attempt to ack again.
     */
    public abstract Boolean ack(String taskId, String workerId);

    /**
     * Log execution messages for a task.
     *
     * @param taskId id of the task
     * @param logMessage the message to be logged
     */
    public abstract void logMessageForTask(String taskId, String logMessage);

    /**
     * Fetch execution logs for a task.
     *
     * @param taskId id of the task.
     */
    public abstract List<TaskExecLog> getTaskLogs(String taskId);

    /**
     * Retrieve information about the task
     *
     * @param taskId ID of the task
     * @return Task details
     */
    public abstract Task getTaskDetails(String taskId);

    /**
     * Removes a task from a taskType queue
     *
     * @param taskType the taskType to identify the queue
     * @param taskId the id of the task to be removed
     */
    public abstract void removeTaskFromQueue(String taskType, String taskId);

    public abstract int getQueueSizeForTask(String taskType);

    public abstract int getQueueSizeForTask(
            String taskType, String domain, String isolationGroupId, String executionNamespace);

    /**
     * Get last poll data for a given task type
     *
     * @param taskType the task type for which poll data is to be fetched
     * @return returns the list of poll data for the task type
     */
    public abstract List<PollData> getPollData(String taskType);

    /**
     * Get the last poll data for all task types
     *
     * @return returns a list of poll data for all task types
     */
    public abstract List<PollData> getAllPollData();

    /**
     * Requeue pending tasks for all running workflows
     *
     * @return returns the number of tasks that have been requeued
     */
    public abstract String requeueAllPendingTasks();

    /**
     * Requeue pending tasks of a specific task type
     *
     * @return returns the number of tasks that have been requeued
     */
    public abstract String requeuePendingTasksByTaskType(String taskType);
    /**
     * Search for tasks based on payload
     *
     * @param query the search string
     * @return returns the {@link SearchResult} containing the {@link TaskSummary} matching the
     *     query
     */
    public abstract SearchResult<TaskSummary> search(String query);

    /**
     * Search for tasks based on payload
     *
     * @param query the search string
     * @return returns the {@link SearchResult} containing the {@link Task} matching the query
     */
    public abstract SearchResult<Task> searchV2(String query);

    /**
     * Paginated search for tasks based on payload
     *
     * @param start start value of page
     * @param size number of tasks to be returned
     * @param sort sort order
     * @param freeText additional free text query
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link TaskSummary} that match the query
     */
    public abstract SearchResult<TaskSummary> search(
            Integer start, Integer size, String sort, String freeText, String query);

    /**
     * Paginated search for tasks based on payload
     *
     * @param start start value of page
     * @param size number of tasks to be returned
     * @param sort sort order
     * @param freeText additional free text query
     * @param query the search query
     * @return the {@link SearchResult} containing the {@link Task} that match the query
     */
    public abstract SearchResult<Task> searchV2(
            Integer start, Integer size, String sort, String freeText, String query);
}
