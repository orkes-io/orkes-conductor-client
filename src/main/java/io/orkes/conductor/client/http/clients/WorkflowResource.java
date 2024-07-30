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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.CorrelationIdsSearchRequest;
import io.orkes.conductor.client.model.ExternalStorageLocation;
import io.orkes.conductor.client.model.ScrollableSearchResultWorkflowSummary;
import io.orkes.conductor.client.model.SearchResultWorkflow;
import io.orkes.conductor.client.model.SearchResultWorkflowSummary;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.conductor.client.model.WorkflowStateUpdate;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.model.metadata.workflow.RerunWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.SkipTaskRequest;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.UpgradeWorkflowRequest;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.client.model.run.WorkflowTestRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.reflect.TypeToken;
import okhttp3.Call;

class WorkflowResource {
    private final OrkesHttpClient httpClient;

    public WorkflowResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public WorkflowRun executeWorkflow(
            StartWorkflowRequest req,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId)
            throws ApiException {
        ApiResponse<WorkflowRun> resp =
                executeWorkflowWithHttpInfo(req, name, version, waitUntilTaskRef, requestId);
        return resp.getData();
    }

  
    public WorkflowRun executeWorkflow(
            StartWorkflowRequest req,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds)
            throws ApiException {
        ApiResponse<WorkflowRun> resp =
                executeWorkflowWithHttpInfo(req, name, version, waitUntilTaskRef, requestId, waitForSeconds);
        return resp.getData();
    }

  
    public ApiResponse<WorkflowRun> executeWorkflowWithHttpInfo(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds)
            throws ApiException {
        okhttp3.Call call = executeWorkflowValidateBeforeCall(body, name, version, waitUntilTaskRef,
                requestId,
                waitForSeconds);
        Type localVarReturnType = new TypeReference<WorkflowRun>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    private okhttp3.Call executeWorkflowValidateBeforeCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling executeWorkflow(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling executeWorkflow(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException(
                    "Missing the required parameter 'version' when calling executeWorkflow(Async)");
        }

        return executeWorkflowCall(
                body,
                name,
                version,
                waitUntilTaskRef,
                requestId,
                waitForSeconds);
    }

  
    public ApiResponse<WorkflowRun> executeWorkflowWithHttpInfo(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId)
            throws ApiException {
        okhttp3.Call call =
                executeWorkflowValidateBeforeCall(
                        body, name, version, waitUntilTaskRef, requestId);
        Type localVarReturnType = new TypeReference<WorkflowRun>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    private okhttp3.Call executeWorkflowValidateBeforeCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling executeWorkflow(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling executeWorkflow(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException(
                    "Missing the required parameter 'version' when calling executeWorkflow(Async)");
        }

        return executeWorkflowCall(
                body,
                name,
                version,
                waitUntilTaskRef,
                requestId);
    }

    public okhttp3.Call executeWorkflowCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/workflow/execute/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                httpClient.escapeString(version.toString()));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (requestId != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("requestId", requestId));

        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    public okhttp3.Call executeWorkflowCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/workflow/execute/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                httpClient.escapeString(version.toString()));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (requestId != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("requestId", requestId));

        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));

        if (waitForSeconds != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("waitForSeconds", waitForSeconds));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

  
    public okhttp3.Call decideCall(
            String workflowId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/decide/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call decideValidateBeforeCall(
            String workflowId)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling decide(Async)");
        }

        return decideCall(workflowId);
    }

  
    public void decide(String workflowId) throws ApiException {
        decideWithHttpInfo(workflowId);
    }

  
    private ApiResponse<Void> decideWithHttpInfo(String workflowId) throws ApiException {
        okhttp3.Call call = decideValidateBeforeCall(workflowId);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call deleteCall(
            String workflowId,
            Boolean archiveWorkflow)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/remove"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (archiveWorkflow != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("archiveWorkflow", archiveWorkflow));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call deleteValidateBeforeCall(
            String workflowId,
            Boolean archiveWorkflow)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling delete(Async)");
        }

        return deleteCall(workflowId, archiveWorkflow);
    }

  
    public void delete(String workflowId, Boolean archiveWorkflow) throws ApiException {
        deleteWithHttpInfo(workflowId, archiveWorkflow);
    }

  
    private ApiResponse<Void> deleteWithHttpInfo(String workflowId, Boolean archiveWorkflow)
            throws ApiException {
        okhttp3.Call call =
                deleteValidateBeforeCall(workflowId, archiveWorkflow);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call getExecutionStatusCall(
            String workflowId,
            Boolean includeTasks)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (includeTasks != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getExecutionStatusValidateBeforeCall(
            String workflowId,
            Boolean includeTasks)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling getExecutionStatus(Async)");
        }

        return getExecutionStatusCall(
                workflowId, includeTasks);
    }

  
    public Workflow getExecutionStatus(String workflowId, Boolean includeTasks)
            throws ApiException {
        ApiResponse<Workflow> resp = getExecutionStatusWithHttpInfo(workflowId, includeTasks);
        return resp.getData();
    }

  
    private ApiResponse<Workflow> getExecutionStatusWithHttpInfo(
            String workflowId, Boolean includeTasks) throws ApiException {
        okhttp3.Call call =
                getExecutionStatusValidateBeforeCall(workflowId, includeTasks);
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getExternalStorageLocationCall(
            String path,
            String operation,
            String payloadType)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/externalstoragelocation";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (path != null) localVarQueryParams.addAll(httpClient.parameterToPair("path", path));
        if (operation != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("operation", operation));
        if (payloadType != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("payloadType", payloadType));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getExternalStorageLocationValidateBeforeCall(
            String path,
            String operation,
            String payloadType)
            throws ApiException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException(
                    "Missing the required parameter 'path' when calling getExternalStorageLocation(Async)");
        }
        // verify the required parameter 'operation' is set
        if (operation == null) {
            throw new ApiException(
                    "Missing the required parameter 'operation' when calling getExternalStorageLocation(Async)");
        }
        // verify the required parameter 'payloadType' is set
        if (payloadType == null) {
            throw new ApiException(
                    "Missing the required parameter 'payloadType' when calling getExternalStorageLocation(Async)");
        }

        return getExternalStorageLocationCall(
                path, operation, payloadType);
    }

  
    public ExternalStorageLocation getExternalStorageLocation(
            String path, String operation, String payloadType) throws ApiException {
        ApiResponse<ExternalStorageLocation> resp =
                getExternalStorageLocationWithHttpInfo(path, operation, payloadType);
        return resp.getData();
    }

  
    private ApiResponse<ExternalStorageLocation> getExternalStorageLocationWithHttpInfo(
            String path, String operation, String payloadType) throws ApiException {
        okhttp3.Call call =
                getExternalStorageLocationValidateBeforeCall(
                        path, operation, payloadType);
        Type localVarReturnType = new TypeReference<ExternalStorageLocation>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getRunningWorkflowCall(
            String name,
            Integer version,
            Long startTime,
            Long endTime)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/running/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (version != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("version", version));
        if (startTime != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("startTime", startTime));
        if (endTime != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("endTime", endTime));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getRunningWorkflowValidateBeforeCall(
            String name,
            Integer version,
            Long startTime,
            Long endTime)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getRunningWorkflow(Async)");
        }

        return getRunningWorkflowCall(
                name,
                version,
                startTime,
                endTime);
    }

  
    public List<String> getRunningWorkflow(
            String name, Integer version, Long startTime, Long endTime) throws ApiException {
        ApiResponse<List<String>> resp =
                getRunningWorkflowWithHttpInfo(name, version, startTime, endTime);
        return resp.getData();
    }

  
    private ApiResponse<List<String>> getRunningWorkflowWithHttpInfo(
            String name, Integer version, Long startTime, Long endTime) throws ApiException {
        okhttp3.Call call =
                getRunningWorkflowValidateBeforeCall(name, version, startTime, endTime);
        Type localVarReturnType = new TypeReference<List<String>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getWorkflowStatusSummaryCall(
            String workflowId,
            Boolean includeOutput,
            Boolean includeVariables)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/status"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (includeOutput != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeOutput", includeOutput));
        if (includeVariables != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("includeVariables", includeVariables));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getWorkflowStatusSummaryValidateBeforeCall(
            String workflowId,
            Boolean includeOutput,
            Boolean includeVariables)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling getWorkflowStatusSummary(Async)");
        }

        return getWorkflowStatusSummaryCall(
                workflowId,
                includeOutput,
                includeVariables);
    }

  
    public WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables)
            throws ApiException {
        ApiResponse<WorkflowStatus> resp =
                getWorkflowStatusSummaryWithHttpInfo(workflowId, includeOutput, includeVariables);
        return resp.getData();
    }

  
    private ApiResponse<WorkflowStatus> getWorkflowStatusSummaryWithHttpInfo(
            String workflowId, Boolean includeOutput, Boolean includeVariables)
            throws ApiException {
        okhttp3.Call call =
                getWorkflowStatusSummaryValidateBeforeCall(
                        workflowId, includeOutput, includeVariables);
        Type localVarReturnType = new TypeReference<WorkflowStatus>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getWorkflowsCall(
            List<String> body,
            String name,
            Boolean includeClosed,
            Boolean includeTasks)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/workflow/{name}/correlated"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (includeClosed != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getWorkflowsValidateBeforeCall(
            List<String> body,
            String name,
            Boolean includeClosed,
            Boolean includeTasks)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling getWorkflows(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getWorkflows(Async)");
        }

        return getWorkflowsCall(
                body,
                name,
                includeClosed,
                includeTasks);
    }

  
    public Map<String, List<Workflow>> getWorkflows(
            List<String> body, String name, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        ApiResponse<Map<String, List<Workflow>>> resp =
                getWorkflowsWithHttpInfo(body, name, includeClosed, includeTasks);
        return resp.getData();
    }

  
    private ApiResponse<Map<String, List<Workflow>>> getWorkflowsWithHttpInfo(
            List<String> body, String name, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        okhttp3.Call call =
                getWorkflowsValidateBeforeCall(body, name, includeClosed, includeTasks);
        Type localVarReturnType = new TypeReference<Map<String, List<Workflow>>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getWorkflows1Call(
            String name,
            String correlationId,
            Boolean includeClosed,
            Boolean includeTasks)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{name}/correlated/{correlationId}"
                        .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name))
                        .replaceAll(
                                "\\{" + "correlationId" + "\\}",
                                httpClient.escapeString(correlationId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (includeClosed != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

  
    public Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(CorrelationIdsSearchRequest request, Boolean includeClosed, Boolean includeTasks) throws ApiException {
        okhttp3.Call call = getWorkflowsByNamesAndCorrelationIdsBeforeCall(request, includeClosed, includeTasks);
        Type localVarReturnType = new TypeReference<Map<String, List<Workflow>>>(){}.getType();
        ApiResponse<Map<String, List<Workflow>>> response = httpClient.execute(call, localVarReturnType);
        return response.getData();
    }

    private okhttp3.Call getWorkflowsByNamesAndCorrelationIdsBeforeCall(CorrelationIdsSearchRequest request, Boolean includeClosed, Boolean includeTasks) throws ApiException {
        // verify the required parameter 'body' is set
        if (request == null) {
            throw new ApiException("Missing the required parameter 'body' when calling getWorkflows1(Async)");
        }
        return getWorkflowsByNamesAndCorrelationIdsCall(request, includeClosed, includeTasks);
    }

  
    private okhttp3.Call getWorkflowsByNamesAndCorrelationIdsCall(CorrelationIdsSearchRequest body, Boolean includeClosed, Boolean includeTasks) throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/correlated/batch";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (includeClosed != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    private okhttp3.Call getWorkflows1ValidateBeforeCall(
            String name,
            String correlationId,
            Boolean includeClosed,
            Boolean includeTasks)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getWorkflows1(Async)");
        }
        // verify the required parameter 'correlationId' is set
        if (correlationId == null) {
            throw new ApiException(
                    "Missing the required parameter 'correlationId' when calling getWorkflows1(Async)");
        }

        return getWorkflows1Call(
                name,
                correlationId,
                includeClosed,
                includeTasks);
    }

  
    public List<Workflow> getWorkflowsByCorrelationId(
            String name, String correlationId, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        ApiResponse<List<Workflow>> resp =
                getWorkflows1WithHttpInfo(name, correlationId, includeClosed, includeTasks);
        return resp.getData();
    }

  
    private ApiResponse<List<Workflow>> getWorkflows1WithHttpInfo(
            String name, String correlationId, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        okhttp3.Call call =
                getWorkflows1ValidateBeforeCall(
                        name, correlationId, includeClosed, includeTasks);
        Type localVarReturnType = new TypeReference<List<Workflow>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call pauseWorkflowCall(
            String workflowId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/pause"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call pauseWorkflowValidateBeforeCall(
            String workflowId)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling pauseWorkflow(Async)");
        }

        return pauseWorkflowCall(workflowId);
    }

  
    public void pauseWorkflow(String workflowId) throws ApiException {
        pauseWorkflowWithHttpInfo(workflowId);
    }

  
    private ApiResponse<Void> pauseWorkflowWithHttpInfo(String workflowId) throws ApiException {
        okhttp3.Call call = pauseWorkflowValidateBeforeCall(workflowId);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call rerunCall(
            RerunWorkflowRequest rerunWorkflowRequest,
            String workflowId)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/rerun"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                rerunWorkflowRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call rerunValidateBeforeCall(
            RerunWorkflowRequest rerunWorkflowRequest,
            String workflowId)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (rerunWorkflowRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'rerunWorkflowRequest' when calling rerun(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling rerun(Async)");
        }

        return rerunCall(
                rerunWorkflowRequest,
                workflowId);
    }

  
    public String rerun(RerunWorkflowRequest rerunWorkflowRequest, String workflowId)
            throws ApiException {
        ApiResponse<String> resp = rerunWithHttpInfo(rerunWorkflowRequest, workflowId);
        return resp.getData();
    }

  
    private ApiResponse<String> rerunWithHttpInfo(
            RerunWorkflowRequest rerunWorkflowRequest, String workflowId) throws ApiException {
        okhttp3.Call call =
                rerunValidateBeforeCall(rerunWorkflowRequest, workflowId);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call resetWorkflowCall(
            String workflowId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/resetcallbacks"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call resetWorkflowValidateBeforeCall(
            String workflowId)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling resetWorkflow(Async)");
        }

        return resetWorkflowCall(workflowId);
    }

  
    public void resetWorkflow(String workflowId) throws ApiException {
        resetWorkflowWithHttpInfo(workflowId);
    }

  
    private ApiResponse<Void> resetWorkflowWithHttpInfo(String workflowId) throws ApiException {
        okhttp3.Call call = resetWorkflowValidateBeforeCall(workflowId);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call restartCall(
            String workflowId,
            Boolean useLatestDefinitions)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/restart"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (useLatestDefinitions != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("useLatestDefinitions", useLatestDefinitions));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call restartValidateBeforeCall(
            String workflowId,
            Boolean useLatestDefinitions)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling restart(Async)");
        }

        return restartCall(
                workflowId,
                useLatestDefinitions);
    }

  
    public void restart(String workflowId, Boolean useLatestDefinitions) throws ApiException {
        restartWithHttpInfo(workflowId, useLatestDefinitions);
    }

  
    private ApiResponse<Void> restartWithHttpInfo(String workflowId, Boolean useLatestDefinitions)
            throws ApiException {
        okhttp3.Call call =
                restartValidateBeforeCall(workflowId, useLatestDefinitions);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call resumeWorkflowCall(
            String workflowId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/resume"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call resumeWorkflowValidateBeforeCall(
            String workflowId)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling resumeWorkflow(Async)");
        }

        return resumeWorkflowCall(workflowId);
    }

  
    public void resumeWorkflow(String workflowId) throws ApiException {
        resumeWorkflowWithHttpInfo(workflowId);
    }

  
    private ApiResponse<Void> resumeWorkflowWithHttpInfo(String workflowId) throws ApiException {
        okhttp3.Call call = resumeWorkflowValidateBeforeCall(workflowId);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call retryCall(
            String workflowId,
            Boolean resumeSubworkflowTasks)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/retry"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (resumeSubworkflowTasks != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("resumeSubworkflowTasks", resumeSubworkflowTasks));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call retryValidateBeforeCall(
            String workflowId,
            Boolean resumeSubworkflowTasks)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling retry(Async)");
        }

        return retryCall(
                workflowId,
                resumeSubworkflowTasks);
    }

  
    public void retry(String workflowId, Boolean resumeSubworkflowTasks) throws ApiException {
        retryWithHttpInfo(workflowId, resumeSubworkflowTasks);
    }

  
    private ApiResponse<Void> retryWithHttpInfo(String workflowId, Boolean resumeSubworkflowTasks)
            throws ApiException {
        okhttp3.Call call =
                retryValidateBeforeCall(workflowId, resumeSubworkflowTasks);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call searchCall(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (queryId != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("queryId", queryId));
        if (start != null) localVarQueryParams.addAll(httpClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(httpClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(httpClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(httpClient.parameterToPair("query", query));
        if (skipCache != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("skipCache", skipCache));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call searchValidateBeforeCall(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache)
            throws ApiException {

        return searchCall(
                queryId,
                start,
                size,
                sort,
                freeText,
                query,
                skipCache);
    }

  
    public ScrollableSearchResultWorkflowSummary search(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache)
            throws ApiException {
        ApiResponse<ScrollableSearchResultWorkflowSummary> resp =
                searchWithHttpInfo(queryId, start, size, sort, freeText, query, skipCache);
        return resp.getData();
    }

  
    private ApiResponse<ScrollableSearchResultWorkflowSummary> searchWithHttpInfo(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache)
            throws ApiException {
        okhttp3.Call call =
                searchValidateBeforeCall(
                        queryId, start, size, sort, freeText, query, skipCache);
        Type localVarReturnType =
                new TypeReference<ScrollableSearchResultWorkflowSummary>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call searchV2Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-v2";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(httpClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(httpClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(httpClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(httpClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call searchV2ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchV2Call(
                start,
                size,
                sort,
                freeText,
                query);
    }

  
    public SearchResultWorkflow searchV2(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflow> resp =
                searchV2WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

  
    private ApiResponse<SearchResultWorkflow> searchV2WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchV2ValidateBeforeCall(start, size, sort, freeText, query);
        Type localVarReturnType = new TypeReference<SearchResultWorkflow>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call searchWorkflowsByTasksCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-by-tasks";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(httpClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(httpClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(httpClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(httpClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call searchWorkflowsByTasksValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchWorkflowsByTasksCall(
                start,
                size,
                sort,
                freeText,
                query);
    }

  
    public SearchResultWorkflowSummary searchWorkflowsByTasks(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflowSummary> resp =
                searchWorkflowsByTasksWithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

  
    private ApiResponse<SearchResultWorkflowSummary> searchWorkflowsByTasksWithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchWorkflowsByTasksValidateBeforeCall(
                        start, size, sort, freeText, query);
        Type localVarReturnType = new TypeReference<SearchResultWorkflowSummary>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call searchWorkflowsByTasksV2Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-by-tasks-v2";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(httpClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(httpClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(httpClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(httpClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call searchWorkflowsByTasksV2ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchWorkflowsByTasksV2Call(
                start,
                size,
                sort,
                freeText,
                query);
    }

  
    public SearchResultWorkflow searchWorkflowsByTasksV2(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflow> resp =
                searchWorkflowsByTasksV2WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

  
    private ApiResponse<SearchResultWorkflow> searchWorkflowsByTasksV2WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchWorkflowsByTasksV2ValidateBeforeCall(
                        start, size, sort, freeText, query);
        Type localVarReturnType = new TypeReference<SearchResultWorkflow>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call skipTaskFromWorkflowCall(
            String workflowId,
            String taskReferenceName,
            SkipTaskRequest skipTaskRequest)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/skiptask/{taskReferenceName}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId))
                        .replaceAll(
                                "\\{" + "taskReferenceName" + "\\}",
                                httpClient.escapeString(taskReferenceName));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (skipTaskRequest != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("skipTaskRequest", skipTaskRequest));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call skipTaskFromWorkflowValidateBeforeCall(
            String workflowId,
            String taskReferenceName,
            SkipTaskRequest skipTaskRequest)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling skipTaskFromWorkflow(Async)");
        }
        // verify the required parameter 'taskReferenceName' is set
        if (taskReferenceName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskReferenceName' when calling skipTaskFromWorkflow(Async)");
        }
        // verify the required parameter 'skipTaskRequest' is set
        if (skipTaskRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'skipTaskRequest' when calling skipTaskFromWorkflow(Async)");
        }

        return skipTaskFromWorkflowCall(
                workflowId,
                taskReferenceName,
                skipTaskRequest);
    }

  
    public void skipTaskFromWorkflow(
            String workflowId, String taskReferenceName, SkipTaskRequest skipTaskRequest)
            throws ApiException {
        skipTaskFromWorkflowWithHttpInfo(workflowId, taskReferenceName, skipTaskRequest);
    }

  
    private ApiResponse<Void> skipTaskFromWorkflowWithHttpInfo(
            String workflowId, String taskReferenceName, SkipTaskRequest skipTaskRequest)
            throws ApiException {
        okhttp3.Call call =
                skipTaskFromWorkflowValidateBeforeCall(
                        workflowId, taskReferenceName, skipTaskRequest);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call startWorkflowCall(
            StartWorkflowRequest startWorkflowRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                startWorkflowRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }


    public okhttp3.Call testWorkflowCall(
            WorkflowTestRequest testRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/test";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                testRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call startWorkflowValidateBeforeCall(
            StartWorkflowRequest startWorkflowRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (startWorkflowRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'startWorkflowRequest' when calling startWorkflow(Async)");
        }

        return startWorkflowCall(startWorkflowRequest);
    }

    private okhttp3.Call testWorkflowValidateBeforeCall(
            WorkflowTestRequest testRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (testRequest == null) {
            throw new ApiException("Missing the required parameter 'testRequest' when calling testWorkflow");
        }

        return testWorkflowCall(testRequest);
    }

  
    public Workflow testWorkflow(WorkflowTestRequest testRequest) throws ApiException {
        ApiResponse<Workflow> resp = testWorkflowWithHttpInfo(testRequest);
        return resp.getData();
    }

  
    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ApiException {
        ApiResponse<String> resp = startWorkflowWithHttpInfo(startWorkflowRequest);
        return resp.getData();
    }

  
    private ApiResponse<String> startWorkflowWithHttpInfo(StartWorkflowRequest startWorkflowRequest)
            throws ApiException {
        okhttp3.Call call =
                startWorkflowValidateBeforeCall(startWorkflowRequest);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    private ApiResponse<Workflow> testWorkflowWithHttpInfo(WorkflowTestRequest testRequest) throws ApiException {
        okhttp3.Call call =
                testWorkflowValidateBeforeCall(testRequest);
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call startWorkflow1Call(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/workflow/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (version != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("version", version));
        if (correlationId != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("correlationId", correlationId));
        if (priority != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("priority", priority));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call startWorkflow1ValidateBeforeCall(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling startWorkflow1(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling startWorkflow1(Async)");
        }

        return startWorkflow1Call(
                body,
                name,
                version,
                correlationId,
                priority);
    }

  
    public String startWorkflow1(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority)
            throws ApiException {
        ApiResponse<String> resp =
                startWorkflow1WithHttpInfo(body, name, version, correlationId, priority);
        return resp.getData();
    }

  
    private ApiResponse<String> startWorkflow1WithHttpInfo(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority)
            throws ApiException {
        okhttp3.Call call =
                startWorkflow1ValidateBeforeCall(
                        body, name, version, correlationId, priority);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call terminate1Call(
            String workflowId,
            String reason,
            boolean triggerFailureWorkflow)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (reason != null) localVarQueryParams.addAll(httpClient.parameterToPair("reason", reason));
        localVarQueryParams.addAll(httpClient.parameterToPair("triggerFailureWorkflow", triggerFailureWorkflow));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call terminate1ValidateBeforeCall(
            String workflowId,
            String reason,
            boolean triggerFailureWorkflow)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling terminate1(Async)");
        }

        return terminate1Call(workflowId, reason, triggerFailureWorkflow);
    }

  
    public void terminateWithAReason(String workflowId, String reason, boolean triggerFailureWorkflow) throws ApiException {
        terminate1WithHttpInfo(workflowId, reason, triggerFailureWorkflow);
    }

  
    private ApiResponse<Void> terminate1WithHttpInfo(String workflowId, String reason, boolean triggerFailureWorkflow)
            throws ApiException {
        okhttp3.Call call =
                terminate1ValidateBeforeCall(workflowId, reason, triggerFailureWorkflow);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call uploadCompletedWorkflowsCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/document-store/upload";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call uploadCompletedWorkflowsValidateBeforeCall()
            throws ApiException {

        return uploadCompletedWorkflowsCall();
    }

  
    public Object uploadCompletedWorkflows() throws ApiException {
        ApiResponse<Object> resp = uploadCompletedWorkflowsWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<Object> uploadCompletedWorkflowsWithHttpInfo() throws ApiException {
        okhttp3.Call call = uploadCompletedWorkflowsValidateBeforeCall();
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    public Workflow updateVariables(String workflowId, Map<String, Object> variables) {
        okhttp3.Call call = updateVariablesCall(workflowId, variables);
        Type returnType = new TypeReference<Workflow>() {}.getType();
        ApiResponse<Workflow> response = httpClient.execute(call, returnType);
        return response.getData();
    }

    private Call updateVariablesCall(String workflowId, Map<String, Object> variables) {
        String localVarPath = "/workflow/{workflowId}/variables".replaceAll(
                "\\{" + "workflowId" + "\\}",
                httpClient.escapeString(workflowId));

                        ;
        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);
        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                (Object) variables,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

  
    public void upgradeRunningWorkflow(UpgradeWorkflowRequest body, String workflowId) throws ApiException {
        upgradeRunningWorkflowWithHttpInfo(body, workflowId);
    }

  
    public ApiResponse<Void> upgradeRunningWorkflowWithHttpInfo(UpgradeWorkflowRequest body, String workflowId) throws ApiException {
        okhttp3.Call call = upgradeRunningWorkflowValidateBeforeCall(body, workflowId);
        return httpClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call upgradeRunningWorkflowValidateBeforeCall(UpgradeWorkflowRequest body, String workflowId) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling upgradeRunningWorkflow(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException("Missing the required parameter 'workflowId' when calling upgradeRunningWorkflow(Async)");
        }

        return upgradeRunningWorkflowCall(body, workflowId);
    }

  
    public okhttp3.Call upgradeRunningWorkflowCall(UpgradeWorkflowRequest body, String workflowId) throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/{workflowId}/upgrade"
                .replaceAll("\\{" + "workflowId" + "\\}", httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }



  
    public WorkflowRun updateWorkflowState(WorkflowStateUpdate updateRequest, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        ApiResponse<WorkflowRun> resp = updateWorkflowAndTaskStateWithHttpInfo(updateRequest, requestId, workflowId, waitUntilTaskRef, waitForSeconds);
        return resp.getData();
    }

  
    public ApiResponse<WorkflowRun> updateWorkflowAndTaskStateWithHttpInfo(WorkflowStateUpdate body, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        okhttp3.Call call = updateWorkflowAndTaskStateValidateBeforeCall(body, requestId, workflowId, waitUntilTaskRef, waitForSeconds);
        Type localVarReturnType = new TypeToken<WorkflowRun>(){}.getType();
        return httpClient.execute(call, localVarReturnType);
    }


    public okhttp3.Call updateWorkflowAndTaskStateCall(WorkflowStateUpdate body,  String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        // create path and map variables
        String localVarPath = "/workflow/{workflowId}/state"
            .replaceAll("\\{" + "workflowId" + "\\}", httpClient.escapeString(workflowId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (requestId != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("requestId", requestId));
        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));
        if (waitForSeconds != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("waitForSeconds", waitForSeconds));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateWorkflowAndTaskStateValidateBeforeCall(WorkflowStateUpdate body,  String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling updateWorkflowAndTaskState(Async)");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new ApiException("Missing the required parameter 'requestId' when calling updateWorkflowAndTaskState(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException("Missing the required parameter 'workflowId' when calling updateWorkflowAndTaskState(Async)");
        }

        return updateWorkflowAndTaskStateCall(body, requestId, workflowId, waitUntilTaskRef, waitForSeconds);
    }
}
