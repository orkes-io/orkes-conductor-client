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

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.BulkResponse;

import com.fasterxml.jackson.core.type.TypeReference;

class WorkflowBulkResource {
    private final OrkesHttpClient httpClient;

    public WorkflowBulkResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public okhttp3.Call pauseWorkflow1Call(
            List<String> workflowIds)
            {

        // create path and map variables
        String localVarPath = "/workflow/bulk/pause";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                workflowIds,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call pauseWorkflow1ValidateBeforeCall(
            List<String> workflowIds)
            {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling pauseWorkflow1(Async)");
        }

        return pauseWorkflow1Call(workflowIds);
    }

  
    public BulkResponse pauseWorkflow1(List<String> workflowIds) {
        ApiResponse<BulkResponse> resp = pauseWorkflow1WithHttpInfo(workflowIds);
        return resp.getData();
    }

  
    private ApiResponse<BulkResponse> pauseWorkflow1WithHttpInfo(List<String> workflowIds)
            {
        okhttp3.Call call = pauseWorkflow1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }
  
    public okhttp3.Call restart1Call(
            List<String> workflowIds,
            Boolean useLatestDefinitions)
            {

        // create path and map variables
        String localVarPath = "/workflow/bulk/restart";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (useLatestDefinitions != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("useLatestDefinitions", useLatestDefinitions));

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
                workflowIds,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call restart1ValidateBeforeCall(
            List<String> workflowIds,
            Boolean useLatestDefinitions)
            {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling restart1(Async)");
        }

        return restart1Call(
                workflowIds,
                useLatestDefinitions);
    }

  
    public BulkResponse restart1(List<String> workflowIds, Boolean useLatestDefinitions)
            {
        ApiResponse<BulkResponse> resp = restart1WithHttpInfo(workflowIds, useLatestDefinitions);
        return resp.getData();
    }

  
    private ApiResponse<BulkResponse> restart1WithHttpInfo(
            List<String> workflowIds, Boolean useLatestDefinitions) {
        okhttp3.Call call =
                restart1ValidateBeforeCall(workflowIds, useLatestDefinitions);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call resumeWorkflow1Call(
            List<String> workflowIds)
            {

        // create path and map variables
        String localVarPath = "/workflow/bulk/resume";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                workflowIds,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call resumeWorkflow1ValidateBeforeCall(
            List<String> workflowIds)
            {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling resumeWorkflow1(Async)");
        }

        return resumeWorkflow1Call(workflowIds);
    }

  
    public BulkResponse resumeWorkflow1(List<String> workflowIds) {
        ApiResponse<BulkResponse> resp = resumeWorkflow1WithHttpInfo(workflowIds);
        return resp.getData();
    }

  
    private ApiResponse<BulkResponse> resumeWorkflow1WithHttpInfo(List<String> workflowIds)
            {
        okhttp3.Call call = resumeWorkflow1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call retry1Call(
            List<String> workflowIds)
            {

        // create path and map variables
        String localVarPath = "/workflow/bulk/retry";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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
                workflowIds,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call retry1ValidateBeforeCall(
            List<String> workflowIds)
            {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling retry1(Async)");
        }

        return retry1Call(workflowIds);
    }

  
    public BulkResponse retry1(List<String> workflowIds) {
        ApiResponse<BulkResponse> resp = retry1WithHttpInfo(workflowIds);
        return resp.getData();
    }

  
    private ApiResponse<BulkResponse> retry1WithHttpInfo(List<String> workflowIds)
            {
        okhttp3.Call call = retry1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call terminateCall(
            List<String> workflowIds,
            String reason,
            boolean triggerFailureWorkflow)
            {

        // create path and map variables
        String localVarPath = "/workflow/bulk/terminate";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (reason != null) localVarQueryParams.addAll(httpClient.parameterToPair("reason", reason));
        localVarQueryParams.addAll(httpClient.parameterToPair("triggerFailureWorkflow", triggerFailureWorkflow));

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
                workflowIds,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call terminateValidateBeforeCall(
            List<String> workflowIds,
            String reason,
            boolean triggerFailureWorkflow)
            {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling terminate(Async)");
        }

        return terminateCall(workflowIds, reason, triggerFailureWorkflow);
    }

  
    public BulkResponse terminate(List<String> workflowIds, String reason, boolean triggerFailureWorkflow) {
        ApiResponse<BulkResponse> resp = terminateWithHttpInfo(workflowIds, reason, triggerFailureWorkflow);
        return resp.getData();
    }

  
    private ApiResponse<BulkResponse> terminateWithHttpInfo(List<String> workflowIds, String reason, boolean triggerFailureWorkflow)
            {
        okhttp3.Call call =
                terminateValidateBeforeCall(workflowIds, reason, triggerFailureWorkflow);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }
}