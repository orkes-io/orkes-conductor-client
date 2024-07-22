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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.ObjectMapperProvider;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;
import io.orkes.conductor.client.model.metadata.tasks.PollData;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.metadata.tasks.TaskExecLog;
import io.orkes.conductor.client.model.metadata.tasks.TaskResult;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.TaskSummary;
import io.orkes.conductor.client.model.run.Workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrkesTaskClient extends TaskClient {

    private final TaskResourceApi taskResourceApi;

    private final ObjectMapper objectMapper = new ObjectMapperProvider().getObjectMapper();

    public OrkesTaskClient(ApiClient apiClient) {
        this.taskResourceApi = new TaskResourceApi(apiClient);
    }


    @Override
    public Task pollTask(String taskType, String workerId, String domain) {
        List<Task> tasks = batchPollTasksInDomain(taskType, domain, workerId, 1, 100);
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        return tasks.get(0);
    }

    @Override
    public List<Task> batchPollTasksByTaskType(
            String taskType, String workerId, int count, int timeoutInMillisecond) {
        return batchPollTasksInDomain(taskType, null, workerId, count, timeoutInMillisecond);
    }

    @Override
    public List<Task> batchPollTasksInDomain(
            String taskType, String domain, String workerId, int count, int timeoutInMillisecond) {
        return taskResourceApi.batchPoll(taskType, workerId, domain, count, timeoutInMillisecond);
    }

    @Override
    public void updateTask(TaskResult taskResult) {
        taskResourceApi.updateTask(taskResult);
    }


    /**
     * Update the task status and output based given workflow id and task reference name
     *
     * @param workflowId        Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status            Status of the task
     * @param output            Output for the task
     */
    public void updateTask(String workflowId, String taskReferenceName, TaskResult.Status status, Object output) {
        Map<String, Object> outputMap = new HashMap<>();
        try {
            outputMap = objectMapper.convertValue(output, Map.class);
        } catch (Exception e) {
            outputMap.put("result", output);
        }
        taskResourceApi.updateTaskByRefName(outputMap, workflowId, taskReferenceName, status.toString());
    }

    /**
     * Update the task status and output based given workflow id and task reference name and return back the updated workflow status
     *
     * @param workflowId        Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status            Status of the task
     * @param output            Output for the task
     * @return Status of the workflow after updating the task
     */
    public Workflow updateTaskSync(String workflowId, String taskReferenceName, TaskResult.Status status, Object output) {
        Map<String, Object> outputMap = new HashMap<>();
        try {
            outputMap = objectMapper.convertValue(output, Map.class);
            ;
        } catch (Exception e) {
            outputMap.put("result", output);
        }
        return taskResourceApi.updateTaskSync(outputMap, workflowId, taskReferenceName, status.toString());
    }

    @Override
    public Optional<String> evaluateAndUploadLargePayload(
            Map<String, Object> taskOutputData, String taskType) {
        return Optional.empty();
    }

    @Override
    public Boolean ack(String taskId, String workerId) {
        throw new UnsupportedOperationException("ack is no longer required");
    }

    @Override
    public void logMessageForTask(String taskId, String logMessage) {
        taskResourceApi.log(logMessage, taskId);
    }

    @Override
    public List<TaskExecLog> getTaskLogs(String taskId) {
        return taskResourceApi.getTaskLogs(taskId);
    }

    @Override
    public Task getTaskDetails(String taskId) {
        return taskResourceApi.getTask(taskId);
    }

    @Override
    public void removeTaskFromQueue(String taskType, String taskId) {
        throw new UnsupportedOperationException("remove task from queue is no longer supported");
    }

    @Override
    public int getQueueSizeForTask(String taskType) {
        return taskResourceApi.size(List.of(taskType)).get(taskType);
    }

    @Override
    public int getQueueSizeForTask(String taskType, String domain, String isolationGroupId, String executionNamespace) {
        return taskResourceApi.size(List.of(taskType)).get(taskType);
    }

    @Override
    public List<PollData> getPollData(String taskType) {
        throw new UnsupportedOperationException("get poll data is no longer supported");
    }

    @Override
    public List<PollData> getAllPollData() {
        throw new UnsupportedOperationException("get poll data is no longer supported");
    }

    @Override
    public String requeueAllPendingTasks() {
        throw new UnsupportedOperationException("requeue all pending task is no longer supported");
    }

    @Override
    public String requeuePendingTasksByTaskType(String taskType) {
        return taskResourceApi.requeuePendingTask(taskType);
    }

    @Override
    public SearchResult<TaskSummary> search(String query) {
        throw new UnsupportedOperationException("search operation on tasks is not supported");
    }

    @Override
    public SearchResult<Task> searchV2(String query) {
        throw new UnsupportedOperationException("search operation on tasks is not supported");
    }

    @Override
    public SearchResult<TaskSummary> search(Integer start, Integer size, String sort, String freeText, String query) {
        return taskResourceApi.searchTasks(start, size, sort, freeText, query);
    }

    @Override
    public SearchResult<Task> searchV2(Integer start, Integer size, String sort, String freeText, String query) {
        throw new UnsupportedOperationException("search operation on tasks is not supported");
    }
}
