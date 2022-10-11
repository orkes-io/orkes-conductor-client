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
import java.util.Map;
import java.util.Optional;

import com.netflix.conductor.common.metadata.tasks.PollData;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.TaskSummary;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.grpc.GrpcTaskClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;

public class OrkesTaskClient extends OrkesClient implements TaskClient {

    private TaskResourceApi taskResourceApi;

    private GrpcTaskClient grpcTaskClient;

    public OrkesTaskClient(ApiClient apiClient) {
        super(apiClient);
        this.taskResourceApi = new TaskResourceApi(apiClient);
        this.grpcTaskClient = new GrpcTaskClient(apiClient);
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
    public int getQueueSizeForTask(
            String taskType, String domain, String isolationGroupId, String executionNamespace) {
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
        throw new UnsupportedOperationException("search operation on tasks is no longer supported");
    }

    @Override
    public SearchResult<Task> searchV2(String query) {
        throw new UnsupportedOperationException("search operation on tasks is no longer supported");
    }

    @Override
    public SearchResult<TaskSummary> search(
            Integer start, Integer size, String sort, String freeText, String query) {
        throw new UnsupportedOperationException("search operation on tasks is no longer supported");
    }

    @Override
    public SearchResult<Task> searchV2(
            Integer start, Integer size, String sort, String freeText, String query) {
        throw new UnsupportedOperationException("search operation on tasks is no longer supported");
    }
}
