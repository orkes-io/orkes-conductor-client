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

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.SaveScheduleRequest;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecution;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.WorkflowSchedule;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class SchedulerResource extends Resource {

    public SchedulerResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public void deleteSchedule(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/scheduler/schedules/{name}")
                .addPathParam("name", name)
                .build();

        httpClient.doRequest(request);
    }

    public List<WorkflowSchedule> getAllSchedules(String workflowName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/schedules")
                .addQueryParam("workflowName", workflowName)
                .build();

        ApiResponse<List<WorkflowSchedule>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<Long> getNextFewSchedules(String cronExpression,
                                          Long scheduleStartTime,
                                          Long scheduleEndTime,
                                          Integer limit) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/nextFewSchedules")
                .addQueryParam("cronExpression", cronExpression)
                .addQueryParam("scheduleStartTime", scheduleStartTime)
                .addQueryParam("scheduleEndTime", scheduleEndTime)
                .addQueryParam("limit", limit)
                .build();

        ApiResponse<List<Long>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public WorkflowSchedule getSchedule(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/schedules/{name}")
                .addPathParam("name", name)
                .build();

        ApiResponse<WorkflowSchedule> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Map<String, Object> pauseAllSchedules() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/admin/pause")
                .build();

        ApiResponse<Map<String, Object>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void pauseSchedule(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/schedules/{name}/pause")
                .addPathParam("name", name)
                .build();

        httpClient.doRequest(request);
    }

    public Map<String, Object> requeueAllExecutionRecords() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/admin/requeue")
                .build();

        ApiResponse<Map<String, Object>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Map<String, Object> resumeAllSchedules() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/admin/resume")
                .build();

        ApiResponse<Map<String, Object>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void resumeSchedule(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/scheduler/schedules/{name}/resume")
                .addPathParam("name", name)
                .build();

        httpClient.doRequest(request);
    }

    public void saveSchedule(SaveScheduleRequest saveScheduleRequest) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/scheduler/schedules")
                .body(saveScheduleRequest)
                .build();

        httpClient.doRequest(request);
    }

    //FIXME Why do we even have search and searchV22?
    public SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/scheduler/search/executions")
                .addQueryParam("start", start)
                .addQueryParam("size", size)
                .addQueryParam("sort", sort)
                .addQueryParam("freeText", freeText)
                .addQueryParam("query", query)
                .build();

        ApiResponse<SearchResultWorkflowScheduleExecutionModel> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public SearchResultWorkflowScheduleExecution search(
            Integer start, Integer size, String sort, String freeText, String query) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/scheduler/search/executions")
                .addQueryParam("start", start)
                .addQueryParam("size", size)
                .addQueryParam("sort", sort)
                .addQueryParam("freeText", freeText)
                .addQueryParam("query", query)
                .build();

        ApiResponse<SearchResultWorkflowScheduleExecution> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void deleteTagForSchedule(String name, List<TagObject> body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/scheduler/schedules/{name}/tags")
                .addPathParam("name", name)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public void putTagForSchedule(String name, List<TagObject> body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/scheduler/schedules/{name}/tags")
                .addPathParam("name", name)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public List<TagObject> getTagsForSchedule(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/scheduler/schedules/{name}/tags")
                .addPathParam("name", name)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
