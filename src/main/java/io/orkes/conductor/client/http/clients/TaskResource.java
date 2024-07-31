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

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.metadata.tasks.TaskExecLog;
import io.orkes.conductor.client.model.metadata.tasks.TaskResult;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.TaskSummary;
import io.orkes.conductor.client.model.run.Workflow;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

class TaskResource {
    private final OrkesHttpClient httpClient;

    public TaskResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<Task> batchPoll(String taskType, String workerid, String domain, Integer count, Integer timeout) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/tasks/poll/batch/{taskType}")
                .addPathParam("taskType", taskType)
                .addQueryParam("workerid", workerid)
                .addQueryParam("domain", domain)
                .addQueryParam("count", count)
                .addQueryParam("timeout", timeout)
                .build();

        ApiResponse<List<Task>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }


    public Task getTask(String taskId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/tasks/{taskId}")
                .addPathParam("taskId", taskId)
                .build();

        ApiResponse<Task> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();

    }

    public List<TaskExecLog> getTaskLogs(String taskId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/tasks/{taskId}/log")
                .addPathParam("taskId", taskId)
                .build();

        ApiResponse<List<TaskExecLog>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();

    }

    public void log(String body, String taskId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/tasks/{taskId}/log")
                .addPathParam("taskId", taskId)
                .body(body)
                .build();

        httpClient.doRequest(request, null);
    }

    public String requeuePendingTask(String taskType) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/tasks/queue/requeue/{taskType}")
                .addPathParam("taskType", taskType)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public SearchResult<TaskSummary> searchTasks(Integer start,
                                                 Integer size,
                                                 String sort,
                                                 String freeText,
                                                 String query) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/tasks/search")
                .addQueryParam("start", start)
                .addQueryParam("size", size)
                .addQueryParam("sort", sort)
                .addQueryParam("freeText", freeText)
                .addQueryParam("query", query)
                .build();

        ApiResponse<SearchResult<TaskSummary>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Map<String, Integer> size(List<String> taskType) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/tasks/queue/sizes")
                .addQueryParams("taskType", taskType)
                .build();
        ApiResponse<Map<String, Integer>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public String updateTask(TaskResult taskResult) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/tasks")
                .body(taskResult)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public String updateTaskByRefName(Map<String, Object> output, String workflowId, String taskRefName, String status) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/tasks/{workflowId}/{taskRefName}/{status}")
                .addPathParam("workflowId", workflowId)
                .addPathParam("taskRefName", taskRefName)
                .addPathParam("status", status)
                .addQueryParam("workerid", getIdentity())
                .body(output)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Workflow updateTaskSync(Map<String, Object> output, String workflowId, String taskRefName, String status) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/tasks/{workflowId}/{taskRefName}/{status}/sync")
                .addPathParam("workflowId", workflowId)
                .addPathParam("taskRefName", taskRefName)
                .addPathParam("status", status)
                .addQueryParam("workerid", getIdentity())
                .body(output)
                .build();

        ApiResponse<Workflow> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    //TODO extract this to a strategy that will be used by TaskResource
    private String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }

        return serverId;
    }
}
