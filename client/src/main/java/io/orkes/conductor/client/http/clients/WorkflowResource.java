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
import java.util.Objects;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.CorrelationIdsSearchRequest;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStateUpdate;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.model.metadata.workflow.RerunWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.SkipTaskRequest;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.UpgradeWorkflowRequest;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.client.model.run.WorkflowSummary;
import io.orkes.conductor.client.model.run.WorkflowTestRequest;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class WorkflowResource {
    private final OrkesHttpClient httpClient;

    public WorkflowResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public WorkflowRun executeWorkflow(StartWorkflowRequest req,
                                       String name,
                                       Integer version,
                                       String waitUntilTaskRef,
                                       String requestId) {
        return executeWorkflow(req, name, version, waitUntilTaskRef, requestId, null);
    }

    public WorkflowRun executeWorkflow(StartWorkflowRequest req,
                                       String name,
                                       Integer version,
                                       String waitUntilTaskRef,
                                       String requestId,
                                       Integer waitForSeconds) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/execute/{name}/{version}")
                .addPathParam("name", name)
                .addPathParam("version", version)
                .addQueryParam("requestId", requestId)
                .addQueryParam("waitUntilTaskRef", waitUntilTaskRef)
                .addQueryParam("waitForSeconds", waitForSeconds)
                .body(req)
                .build();

        ApiResponse<WorkflowRun> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void decide(String workflowId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/decide/{workflowId}")
                .addPathParam("workflowId", workflowId)
                .build();

        httpClient.doRequest(request);
    }

    public void delete(String workflowId, Boolean archiveWorkflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/workflow/{workflowId}/remove")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("archiveWorkflow", archiveWorkflow)
                .build();

        httpClient.doRequest(request);

    }

    public Workflow getExecutionStatus(String workflowId, Boolean includeTasks) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/workflow/{workflowId}")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("includeTasks", includeTasks)
                .build();

        ApiResponse<Workflow> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<String> getRunningWorkflow(String name, Integer version, Long startTime, Long endTime) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/workflow/running/{name}")
                .addPathParam("name", name)
                .addQueryParam("version", version)
                .addQueryParam("startTime", startTime)
                .addQueryParam("endTime", endTime)
                .build();

        ApiResponse<List<String>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public WorkflowStatus getWorkflowStatusSummary(String workflowId, Boolean includeOutput, Boolean includeVariables) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/workflow/{workflowId}/status")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("includeOutput", includeOutput)
                .addQueryParam("includeVariables", includeVariables)
                .build();

        ApiResponse<WorkflowStatus> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(CorrelationIdsSearchRequest searchRequest,
                                                                            Boolean includeClosed,
                                                                            Boolean includeTasks) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/correlated/batch")
                .addQueryParam("includeClosed", includeClosed)
                .addQueryParam("includeTasks", includeTasks)
                .body(searchRequest)
                .build();

        ApiResponse<Map<String, List<Workflow>>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<Workflow> getWorkflowsByCorrelationId(String name,
                                                      String correlationId,
                                                      Boolean includeClosed,
                                                      Boolean includeTasks) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/workflow/{name}/correlated/{correlationId}")
                .addPathParam("name", name)
                .addPathParam("correlationId", correlationId)
                .addQueryParam("includeClosed", includeClosed)
                .addQueryParam("includeTasks", includeTasks)
                .build();

        ApiResponse<List<Workflow>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void pauseWorkflow(String workflowId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/{workflowId}/pause")
                .addPathParam("workflowId", workflowId)
                .build();

        httpClient.doRequest(request);
    }

    public String rerun(RerunWorkflowRequest rerunWorkflowRequest, String workflowId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/rerun")
                .addPathParam("workflowId", workflowId)
                .body(rerunWorkflowRequest)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();

    }

    public void restart(String workflowId, Boolean useLatestDefinitions) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/restart")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("useLatestDefinitions", useLatestDefinitions)
                .build();

        httpClient.doRequest(request);
    }

    public void resumeWorkflow(String workflowId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/{workflowId}/resume")
                .addPathParam("workflowId", workflowId)
                .build();

        httpClient.doRequest(request);
    }

    public void retry(String workflowId, Boolean resumeSubworkflowTasks) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/retry")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("resumeSubworkflowTasks", resumeSubworkflowTasks)
                .build();

        httpClient.doRequest(request);
    }

    public SearchResult<WorkflowSummary> search(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/workflow/search")
                .addQueryParam("queryId", queryId)
                .addQueryParam("start", start)
                .addQueryParam("size", size)
                .addQueryParam("sort", sort)
                .addQueryParam("freeText", freeText)
                .addQueryParam("query", query)
                .addQueryParam("skipCache", skipCache)
                .build();

        ApiResponse<SearchResult<WorkflowSummary>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void skipTaskFromWorkflow(String workflowId, String taskReferenceName, SkipTaskRequest skipTaskRequest) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/workflow/{workflowId}/skiptask/{taskReferenceName}")
                .addPathParam("workflowId", workflowId)
                .addPathParam("taskReferenceName", taskReferenceName)
                .body(skipTaskRequest) //FIXME review this. It was passed as a query param?!
                .build();

        httpClient.doRequest(request);

    }

    public Workflow testWorkflow(WorkflowTestRequest testRequest) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/test")
                .body(testRequest)
                .build();

        ApiResponse<Workflow> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) {
        // might require accepts "text/plain"
        Objects.requireNonNull(startWorkflowRequest, "StartWorkflowRequest cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow")
                .body(startWorkflowRequest)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void terminateWithAReason(String workflowId, String reason, boolean triggerFailureWorkflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/workflow/{workflowId}")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("reason", reason)
                .addQueryParam("triggerFailureWorkflow", triggerFailureWorkflow)
                .build();

        httpClient.doRequest(request);
    }

    public void uploadCompletedWorkflows() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/document-store/upload")
                .build();

        httpClient.doRequest(request);
    }

    public Workflow updateVariables(String workflowId, Map<String, Object> variables) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/variables")
                .addPathParam("workflowId", workflowId)
                .body(variables)
                .build();

        ApiResponse<Workflow> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();

    }

    public void upgradeRunningWorkflow(UpgradeWorkflowRequest body, String workflowId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/upgrade")
                .addPathParam("workflowId", workflowId)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public WorkflowRun updateWorkflowState(WorkflowStateUpdate updateRequest,
                                           String requestId,
                                           String workflowId,
                                           String waitUntilTaskRef,
                                           Integer waitForSeconds) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/workflow/{workflowId}/state")
                .addPathParam("workflowId", workflowId)
                .addQueryParam("requestId", requestId)
                .addQueryParam("waitUntilTaskRef", waitUntilTaskRef)
                .addQueryParam("waitForSeconds", waitForSeconds)
                .body(updateRequest)
                .build();

        ApiResponse<WorkflowRun> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
