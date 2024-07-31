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

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.BulkResponse;

import com.fasterxml.jackson.core.type.TypeReference;

class WorkflowBulkResource extends Resource {

    public WorkflowBulkResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public BulkResponse pauseWorkflows(List<String> workflowIds) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/bulk/pause")
                .body(workflowIds)
                .build();

        ApiResponse<BulkResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public BulkResponse restartWorkflows(List<String> workflowIds, Boolean useLatestDefinitions) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/workflow/bulk/restart")
                .addQueryParam("useLatestDefinitions", useLatestDefinitions)
                .body(workflowIds)
                .build();

        ApiResponse<BulkResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public BulkResponse resumeWorkflows(List<String> workflowIds) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/bulk/resume")
                .body(workflowIds)
                .build();

        ApiResponse<BulkResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public BulkResponse retryWorkflows(List<String> workflowIds) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/workflow/bulk/retry")
                .body(workflowIds)
                .build();

        ApiResponse<BulkResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public BulkResponse terminateWorkflows(List<String> workflowIds, String reason, boolean triggerFailureWorkflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/workflow/bulk/terminate")
                .addQueryParam("reason", reason)
                .addQueryParam("triggerFailureWorkflow", triggerFailureWorkflow)
                .body(workflowIds)
                .build();

        ApiResponse<BulkResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
