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
package io.orkes.conductor.client.api;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.orkes.conductor.client.model.metadata.tasks.PollData;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.metadata.tasks.TaskExecLog;
import io.orkes.conductor.client.model.metadata.tasks.TaskResult;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.TaskSummary;
import io.orkes.conductor.client.model.run.Workflow;

public interface TaskClient  {

    Task pollTask(String taskType, String workerId, String domain);

    List<Task> batchPollTasksByTaskType(
            String taskType, String workerId, int count, int timeoutInMillisecond);

    List<Task> batchPollTasksInDomain(
            String taskType, String domain, String workerId, int count, int timeoutInMillisecond);

    void updateTask(TaskResult taskResult);

    /**
     * Update the task status and output based given workflow id and task reference name
     * @param workflowId Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status Status of the task
     * @param output Output for the task
     */
    void updateTask(String workflowId, String taskReferenceName, TaskResult.Status status, Object output);

    /**
     * Update the task status and output based given workflow id and task reference name and return back the updated workflow status
     * @param workflowId Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status Status of the task
     * @param output Output for the task
     * @return Status of the workflow after updating the task
     */
    Workflow updateTaskSync(String workflowId, String taskReferenceName, TaskResult.Status status, Object output);

    Optional<String> evaluateAndUploadLargePayload(
            Map<String, Object> taskOutputData, String taskType);

    Boolean ack(String taskId, String workerId);

    void logMessageForTask(String taskId, String logMessage);

    List<TaskExecLog> getTaskLogs(String taskId);

    Task getTaskDetails(String taskId);

    void removeTaskFromQueue(String taskType, String taskId);

    int getQueueSizeForTask(String taskType);

    int getQueueSizeForTask(
            String taskType, String domain, String isolationGroupId, String executionNamespace);

    List<PollData> getPollData(String taskType);

    List<PollData> getAllPollData();

    String requeueAllPendingTasks();

    String requeuePendingTasksByTaskType(String taskType);

    SearchResult<TaskSummary> search(String query);

    SearchResult<Task> searchV2(String query);

    SearchResult<TaskSummary> search(Integer start, Integer size, String sort, String freeText, String query);

    SearchResult<Task> searchV2(Integer start, Integer size, String sort, String freeText, String query);
}
