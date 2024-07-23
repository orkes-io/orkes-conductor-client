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
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.model.BulkResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WorkflowBulkResource {
    private final OrkesHttpClient httpClient;

    public WorkflowBulkResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Build call for pauseWorkflow1
     *
     * @param workflowIds (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call pauseWorkflow1Call(
            List<String> workflowIds)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/bulk/pause";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling pauseWorkflow1(Async)");
        }

        return pauseWorkflow1Call(workflowIds);
    }

    /**
     * Pause the list of workflows
     *
     * @param workflowIds (required)
     * @return BulkResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public BulkResponse pauseWorkflow1(List<String> workflowIds) throws ApiException {
        ApiResponse<BulkResponse> resp = pauseWorkflow1WithHttpInfo(workflowIds);
        return resp.getData();
    }

    /**
     * Pause the list of workflows
     *
     * @param workflowIds (required)
     * @return ApiResponse&lt;BulkResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<BulkResponse> pauseWorkflow1WithHttpInfo(List<String> workflowIds)
            throws ApiException {
        okhttp3.Call call = pauseWorkflow1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }
    /**
     * Build call for restart1
     *
     * @param workflowIds (required)
     * @param useLatestDefinitions (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call restart1Call(
            List<String> workflowIds,
            Boolean useLatestDefinitions)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/bulk/restart";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
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
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling restart1(Async)");
        }

        return restart1Call(
                workflowIds,
                useLatestDefinitions);
    }

    /**
     * Restart the list of completed workflow
     *
     * @param workflowIds (required)
     * @param useLatestDefinitions (optional, default to false)
     * @return BulkResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public BulkResponse restart1(List<String> workflowIds, Boolean useLatestDefinitions)
            throws ApiException {
        ApiResponse<BulkResponse> resp = restart1WithHttpInfo(workflowIds, useLatestDefinitions);
        return resp.getData();
    }

    /**
     * Restart the list of completed workflow
     *
     * @param workflowIds (required)
     * @param useLatestDefinitions (optional, default to false)
     * @return ApiResponse&lt;BulkResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<BulkResponse> restart1WithHttpInfo(
            List<String> workflowIds, Boolean useLatestDefinitions) throws ApiException {
        okhttp3.Call call =
                restart1ValidateBeforeCall(workflowIds, useLatestDefinitions);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for resumeWorkflow1
     *
     * @param workflowIds (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call resumeWorkflow1Call(
            List<String> workflowIds)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/bulk/resume";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling resumeWorkflow1(Async)");
        }

        return resumeWorkflow1Call(workflowIds);
    }

    /**
     * Resume the list of workflows
     *
     * @param workflowIds (required)
     * @return BulkResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public BulkResponse resumeWorkflow1(List<String> workflowIds) throws ApiException {
        ApiResponse<BulkResponse> resp = resumeWorkflow1WithHttpInfo(workflowIds);
        return resp.getData();
    }

    /**
     * Resume the list of workflows
     *
     * @param workflowIds (required)
     * @return ApiResponse&lt;BulkResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<BulkResponse> resumeWorkflow1WithHttpInfo(List<String> workflowIds)
            throws ApiException {
        okhttp3.Call call = resumeWorkflow1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for retry1
     *
     * @param workflowIds (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call retry1Call(
            List<String> workflowIds)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/bulk/retry";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling retry1(Async)");
        }

        return retry1Call(workflowIds);
    }

    /**
     * Retry the last failed task for each workflow from the list
     *
     * @param workflowIds (required)
     * @return BulkResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public BulkResponse retry1(List<String> workflowIds) throws ApiException {
        ApiResponse<BulkResponse> resp = retry1WithHttpInfo(workflowIds);
        return resp.getData();
    }

    /**
     * Retry the last failed task for each workflow from the list
     *
     * @param workflowIds (required)
     * @return ApiResponse&lt;BulkResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<BulkResponse> retry1WithHttpInfo(List<String> workflowIds)
            throws ApiException {
        okhttp3.Call call = retry1ValidateBeforeCall(workflowIds);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for terminate
     *
     * @param workflowIds (required)
     * @param reason (optional)
     * @param triggerFailureWorkflow failure workflow is triggered if set
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call terminateCall(
            List<String> workflowIds,
            String reason,
            boolean triggerFailureWorkflow)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/workflow/bulk/terminate";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
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
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling terminate(Async)");
        }

        return terminateCall(workflowIds, reason, triggerFailureWorkflow);
    }

    /**
     * Terminate workflows execution
     *
     * @param workflowIds (required)
     * @param reason (optional)
     * @param triggerFailureWorkflow (required)
     * @return BulkResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public BulkResponse terminate(List<String> workflowIds, String reason, boolean triggerFailureWorkflow) throws ApiException {
        ApiResponse<BulkResponse> resp = terminateWithHttpInfo(workflowIds, reason, triggerFailureWorkflow);
        return resp.getData();
    }

    /**
     * Terminate workflows execution
     *
     * @param workflowIds (required)
     * @param reason (optional)
     * @return ApiResponse&lt;BulkResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<BulkResponse> terminateWithHttpInfo(List<String> workflowIds, String reason, boolean triggerFailureWorkflow)
            throws ApiException {
        okhttp3.Call call =
                terminateValidateBeforeCall(workflowIds, reason, triggerFailureWorkflow);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }
}
