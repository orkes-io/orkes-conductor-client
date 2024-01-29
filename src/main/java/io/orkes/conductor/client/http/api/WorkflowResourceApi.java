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
package io.orkes.conductor.client.http.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.conductor.common.metadata.workflow.*;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowTestRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.common.model.WorkflowRun;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.reflect.TypeToken;
import com.squareup.okhttp.Call;

public class WorkflowResourceApi {
    private ApiClient apiClient;

    public WorkflowResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public WorkflowResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Execute a workflow synchronously
     *
     * @param req (required)
     * @param name (required)
     * @param version (required)
     * @param waitUntilTaskRef (optional)
     * @param requestId (optional)
     * @return WorkflowRun
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
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

    /**
     * Execute a workflow synchronously
     *
     * @param req (required)
     * @param name (required)
     * @param version (required)
     * @param waitUntilTaskRef (optional)
     * @param requestId (optional)
     * @param waitForSeconds (optional)
     * @return WorkflowRun
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
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

    /**
     * Execute a workflow synchronously
     *
     * @param body (required)
     * @param name (required)
     * @param version (required)
     * @param waitUntilTaskRef (optional)
     * @param requestId (required)
     * @param waitForSeconds (optional)
     * @return ApiResponse&lt;WorkflowRun&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ApiResponse<WorkflowRun> executeWorkflowWithHttpInfo(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds)
            throws ApiException {
        com.squareup.okhttp.Call call =
                executeWorkflowValidateBeforeCall(
                        body, name, version, waitUntilTaskRef, requestId, waitForSeconds,null, null);
        Type localVarReturnType = new TypeReference<WorkflowRun>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private com.squareup.okhttp.Call executeWorkflowValidateBeforeCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                executeWorkflowCall(
                        body,
                        name,
                        version,
                        waitUntilTaskRef,
                        requestId,
                        waitForSeconds,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Execute a workflow synchronously
     *
     * @param body (required)
     * @param name (required)
     * @param version (required)
     * @param waitUntilTaskRef (optional)
     * @param requestId (required)
     * @return ApiResponse&lt;WorkflowRun&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ApiResponse<WorkflowRun> executeWorkflowWithHttpInfo(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId)
            throws ApiException {
        com.squareup.okhttp.Call call =
                executeWorkflowValidateBeforeCall(
                        body, name, version, waitUntilTaskRef, requestId, null, null);
        Type localVarReturnType = new TypeReference<WorkflowRun>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private com.squareup.okhttp.Call executeWorkflowValidateBeforeCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                executeWorkflowCall(
                        body,
                        name,
                        version,
                        waitUntilTaskRef,
                        requestId,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    public com.squareup.okhttp.Call executeWorkflowCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/workflow/execute/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name.toString()))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                apiClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (requestId != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("requestId", requestId));

        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    public com.squareup.okhttp.Call executeWorkflowCall(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            Integer waitForSeconds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/workflow/execute/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name.toString()))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                apiClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (requestId != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("requestId", requestId));

        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));

        if (waitForSeconds != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("waitForSeconds", waitForSeconds));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    /**
     * Execute a workflow synchronously (asynchronously)
     *
     * @param body (required)
     * @param name (required)
     * @param version (required)
     * @param waitUntilTaskRef (optional)
     * @param requestId (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call executeWorkflowAsync(
            StartWorkflowRequest body,
            String name,
            Integer version,
            String waitUntilTaskRef,
            String requestId,
            final ApiCallback<WorkflowRun> callback)
            throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener =
                    new ProgressResponseBody.ProgressListener() {
                        @Override
                        public void update(long bytesRead, long contentLength, boolean done) {
                            callback.onDownloadProgress(bytesRead, contentLength, done);
                        }
                    };

            progressRequestListener =
                    new ProgressRequestBody.ProgressRequestListener() {
                        @Override
                        public void onRequestProgress(
                                long bytesWritten, long contentLength, boolean done) {
                            callback.onUploadProgress(bytesWritten, contentLength, done);
                        }
                    };
        }

        com.squareup.okhttp.Call call =
                executeWorkflowValidateBeforeCall(
                        body,
                        name,
                        version,
                        waitUntilTaskRef,
                        requestId,
                        progressListener,
                        progressRequestListener);
        Type localVarReturnType = new TypeReference<WorkflowRun>() {}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);

        return call;
    }

    /**
     * Build call for decide
     *
     * @param workflowId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call decideCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/decide/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call decideValidateBeforeCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling decide(Async)");
        }

        com.squareup.okhttp.Call call =
                decideCall(workflowId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Starts the decision task for a workflow
     *
     * @param workflowId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void decide(String workflowId) throws ApiException {
        decideWithHttpInfo(workflowId);
    }

    /**
     * Starts the decision task for a workflow
     *
     * @param workflowId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> decideWithHttpInfo(String workflowId) throws ApiException {
        com.squareup.okhttp.Call call = decideValidateBeforeCall(workflowId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for delete
     *
     * @param workflowId (required)
     * @param archiveWorkflow (optional, default to true)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call deleteCall(
            String workflowId,
            Boolean archiveWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/remove"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (archiveWorkflow != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("archiveWorkflow", archiveWorkflow));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call deleteValidateBeforeCall(
            String workflowId,
            Boolean archiveWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling delete(Async)");
        }

        com.squareup.okhttp.Call call =
                deleteCall(workflowId, archiveWorkflow, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Removes the workflow from the system
     *
     * @param workflowId (required)
     * @param archiveWorkflow (optional, default to true)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void delete(String workflowId, Boolean archiveWorkflow) throws ApiException {
        deleteWithHttpInfo(workflowId, archiveWorkflow);
    }

    /**
     * Removes the workflow from the system
     *
     * @param workflowId (required)
     * @param archiveWorkflow (optional, default to true)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> deleteWithHttpInfo(String workflowId, Boolean archiveWorkflow)
            throws ApiException {
        com.squareup.okhttp.Call call =
                deleteValidateBeforeCall(workflowId, archiveWorkflow, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for getExecutionStatus
     *
     * @param workflowId (required)
     * @param includeTasks (optional, default to true)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getExecutionStatusCall(
            String workflowId,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (includeTasks != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getExecutionStatusValidateBeforeCall(
            String workflowId,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling getExecutionStatus(Async)");
        }

        com.squareup.okhttp.Call call =
                getExecutionStatusCall(
                        workflowId, includeTasks, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Gets the workflow by workflow id
     *
     * @param workflowId (required)
     * @param includeTasks (optional, default to true)
     * @return Workflow
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Workflow getExecutionStatus(String workflowId, Boolean includeTasks)
            throws ApiException {
        ApiResponse<Workflow> resp = getExecutionStatusWithHttpInfo(workflowId, includeTasks);
        return resp.getData();
    }

    /**
     * Gets the workflow by workflow id
     *
     * @param workflowId (required)
     * @param includeTasks (optional, default to true)
     * @return ApiResponse&lt;Workflow&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Workflow> getExecutionStatusWithHttpInfo(
            String workflowId, Boolean includeTasks) throws ApiException {
        com.squareup.okhttp.Call call =
                getExecutionStatusValidateBeforeCall(workflowId, includeTasks, null, null);
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getExternalStorageLocation
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getExternalStorageLocationCall(
            String path,
            String operation,
            String payloadType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/externalstoragelocation";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (path != null) localVarQueryParams.addAll(apiClient.parameterToPair("path", path));
        if (operation != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("operation", operation));
        if (payloadType != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("payloadType", payloadType));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getExternalStorageLocationValidateBeforeCall(
            String path,
            String operation,
            String payloadType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                getExternalStorageLocationCall(
                        path, operation, payloadType, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the uri and path of the external storage where the workflow payload is to be stored
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @return ExternalStorageLocation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ExternalStorageLocation getExternalStorageLocation(
            String path, String operation, String payloadType) throws ApiException {
        ApiResponse<ExternalStorageLocation> resp =
                getExternalStorageLocationWithHttpInfo(path, operation, payloadType);
        return resp.getData();
    }

    /**
     * Get the uri and path of the external storage where the workflow payload is to be stored
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @return ApiResponse&lt;ExternalStorageLocation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<ExternalStorageLocation> getExternalStorageLocationWithHttpInfo(
            String path, String operation, String payloadType) throws ApiException {
        com.squareup.okhttp.Call call =
                getExternalStorageLocationValidateBeforeCall(
                        path, operation, payloadType, null, null);
        Type localVarReturnType = new TypeReference<ExternalStorageLocation>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getRunningWorkflow
     *
     * @param name (required)
     * @param version (optional, default to 1)
     * @param startTime (optional)
     * @param endTime (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getRunningWorkflowCall(
            String name,
            Integer version,
            Long startTime,
            Long endTime,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/running/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (version != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("version", version));
        if (startTime != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("startTime", startTime));
        if (endTime != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("endTime", endTime));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getRunningWorkflowValidateBeforeCall(
            String name,
            Integer version,
            Long startTime,
            Long endTime,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getRunningWorkflow(Async)");
        }

        com.squareup.okhttp.Call call =
                getRunningWorkflowCall(
                        name,
                        version,
                        startTime,
                        endTime,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Retrieve all the running workflows
     *
     * @param name (required)
     * @param version (optional, default to 1)
     * @param startTime (optional)
     * @param endTime (optional)
     * @return List&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<String> getRunningWorkflow(
            String name, Integer version, Long startTime, Long endTime) throws ApiException {
        ApiResponse<List<String>> resp =
                getRunningWorkflowWithHttpInfo(name, version, startTime, endTime);
        return resp.getData();
    }

    /**
     * Retrieve all the running workflows
     *
     * @param name (required)
     * @param version (optional, default to 1)
     * @param startTime (optional)
     * @param endTime (optional)
     * @return ApiResponse&lt;List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<String>> getRunningWorkflowWithHttpInfo(
            String name, Integer version, Long startTime, Long endTime) throws ApiException {
        com.squareup.okhttp.Call call =
                getRunningWorkflowValidateBeforeCall(name, version, startTime, endTime, null, null);
        Type localVarReturnType = new TypeReference<List<String>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getWorkflowStatusSummary
     *
     * @param workflowId (required)
     * @param includeOutput (optional, default to false)
     * @param includeVariables (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getWorkflowStatusSummaryCall(
            String workflowId,
            Boolean includeOutput,
            Boolean includeVariables,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/status"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (includeOutput != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeOutput", includeOutput));
        if (includeVariables != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("includeVariables", includeVariables));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getWorkflowStatusSummaryValidateBeforeCall(
            String workflowId,
            Boolean includeOutput,
            Boolean includeVariables,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling getWorkflowStatusSummary(Async)");
        }

        com.squareup.okhttp.Call call =
                getWorkflowStatusSummaryCall(
                        workflowId,
                        includeOutput,
                        includeVariables,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Gets the workflow by workflow id
     *
     * @param workflowId (required)
     * @param includeOutput (optional, default to false)
     * @param includeVariables (optional, default to false)
     * @return WorkflowStatus
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public WorkflowStatus getWorkflowStatusSummary(
            String workflowId, Boolean includeOutput, Boolean includeVariables)
            throws ApiException {
        ApiResponse<WorkflowStatus> resp =
                getWorkflowStatusSummaryWithHttpInfo(workflowId, includeOutput, includeVariables);
        return resp.getData();
    }

    /**
     * Gets the workflow by workflow id
     *
     * @param workflowId (required)
     * @param includeOutput (optional, default to false)
     * @param includeVariables (optional, default to false)
     * @return ApiResponse&lt;WorkflowStatus&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<WorkflowStatus> getWorkflowStatusSummaryWithHttpInfo(
            String workflowId, Boolean includeOutput, Boolean includeVariables)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getWorkflowStatusSummaryValidateBeforeCall(
                        workflowId, includeOutput, includeVariables, null, null);
        Type localVarReturnType = new TypeReference<WorkflowStatus>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getWorkflows
     *
     * @param body (required)
     * @param name (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getWorkflowsCall(
            List<String> body,
            String name,
            Boolean includeClosed,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/workflow/{name}/correlated"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (includeClosed != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getWorkflowsValidateBeforeCall(
            List<String> body,
            String name,
            Boolean includeClosed,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                getWorkflowsCall(
                        body,
                        name,
                        includeClosed,
                        includeTasks,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Lists workflows for the given correlation id list
     *
     * @param body (required)
     * @param name (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @return Map&lt;String, List&lt;Workflow&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, List<Workflow>> getWorkflows(
            List<String> body, String name, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        ApiResponse<Map<String, List<Workflow>>> resp =
                getWorkflowsWithHttpInfo(body, name, includeClosed, includeTasks);
        return resp.getData();
    }

    /**
     * Lists workflows for the given correlation id list
     *
     * @param body (required)
     * @param name (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @return ApiResponse&lt;Map&lt;String, List&lt;Workflow&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, List<Workflow>>> getWorkflowsWithHttpInfo(
            List<String> body, String name, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getWorkflowsValidateBeforeCall(body, name, includeClosed, includeTasks, null, null);
        Type localVarReturnType = new TypeReference<Map<String, List<Workflow>>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getWorkflows1
     *
     * @param name (required)
     * @param correlationId (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getWorkflows1Call(
            String name,
            String correlationId,
            Boolean includeClosed,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{name}/correlated/{correlationId}"
                        .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name.toString()))
                        .replaceAll(
                                "\\{" + "correlationId" + "\\}",
                                apiClient.escapeString(correlationId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (includeClosed != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    /**
     * Lists workflows for the given correlation id list and workflow name list
     *
     * @param request  (required)
     * @param includeClosed  (optional, default to false)
     * @param includeTasks  (optional, default to false)
     * @return Map of correlation id to workflow list
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Map<String, List<Workflow>> getWorkflowsByNamesAndCorrelationIds(CorrelationIdsSearchRequest request, Boolean includeClosed, Boolean includeTasks) throws ApiException {
        com.squareup.okhttp.Call call = getWorkflowsByNamesAndCorrelationIdsBeforeCall(request, includeClosed, includeTasks, null, null);
        Type localVarReturnType = new TypeReference<Map<String, List<Workflow>>>(){}.getType();
        ApiResponse<Map<String, List<Workflow>>> response = apiClient.execute(call, localVarReturnType);
        return response.getData();
    }

    private com.squareup.okhttp.Call getWorkflowsByNamesAndCorrelationIdsBeforeCall(CorrelationIdsSearchRequest request, Boolean includeClosed, Boolean includeTasks, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (request == null) {
            throw new ApiException("Missing the required parameter 'body' when calling getWorkflows1(Async)");
        }
        com.squareup.okhttp.Call call = getWorkflowsByNamesAndCorrelationIdsCall(request, includeClosed, includeTasks, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Build call for getWorkflows1
     * @param body  (required)
     * @param includeClosed  (optional, default to false)
     * @param includeTasks  (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private com.squareup.okhttp.Call getWorkflowsByNamesAndCorrelationIdsCall(CorrelationIdsSearchRequest body, Boolean includeClosed, Boolean includeTasks, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/workflow/correlated/batch";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (includeClosed != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeClosed", includeClosed));
        if (includeTasks != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("includeTasks", includeTasks));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    private com.squareup.okhttp.Call getWorkflows1ValidateBeforeCall(
            String name,
            String correlationId,
            Boolean includeClosed,
            Boolean includeTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                getWorkflows1Call(
                        name,
                        correlationId,
                        includeClosed,
                        includeTasks,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Lists workflows for the given correlation id
     *
     * @param name (required)
     * @param correlationId (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @return List&lt;Workflow&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<Workflow> getWorkflowsByCorrelationId(
            String name, String correlationId, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        ApiResponse<List<Workflow>> resp =
                getWorkflows1WithHttpInfo(name, correlationId, includeClosed, includeTasks);
        return resp.getData();
    }

    /**
     * Lists workflows for the given correlation id
     *
     * @param name (required)
     * @param correlationId (required)
     * @param includeClosed (optional, default to false)
     * @param includeTasks (optional, default to false)
     * @return ApiResponse&lt;List&lt;Workflow&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<Workflow>> getWorkflows1WithHttpInfo(
            String name, String correlationId, Boolean includeClosed, Boolean includeTasks)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getWorkflows1ValidateBeforeCall(
                        name, correlationId, includeClosed, includeTasks, null, null);
        Type localVarReturnType = new TypeReference<List<Workflow>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for pauseWorkflow
     *
     * @param workflowId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call pauseWorkflowCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/pause"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call pauseWorkflowValidateBeforeCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling pauseWorkflow(Async)");
        }

        com.squareup.okhttp.Call call =
                pauseWorkflowCall(workflowId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Pauses the workflow
     *
     * @param workflowId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void pauseWorkflow(String workflowId) throws ApiException {
        pauseWorkflowWithHttpInfo(workflowId);
    }

    /**
     * Pauses the workflow
     *
     * @param workflowId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> pauseWorkflowWithHttpInfo(String workflowId) throws ApiException {
        com.squareup.okhttp.Call call = pauseWorkflowValidateBeforeCall(workflowId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for rerun
     *
     * @param rerunWorkflowRequest (required)
     * @param workflowId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call rerunCall(
            RerunWorkflowRequest rerunWorkflowRequest,
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = rerunWorkflowRequest;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/rerun"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call rerunValidateBeforeCall(
            RerunWorkflowRequest rerunWorkflowRequest,
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                rerunCall(
                        rerunWorkflowRequest,
                        workflowId,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Reruns the workflow from a specific task
     *
     * @param rerunWorkflowRequest (required)
     * @param workflowId (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public String rerun(RerunWorkflowRequest rerunWorkflowRequest, String workflowId)
            throws ApiException {
        ApiResponse<String> resp = rerunWithHttpInfo(rerunWorkflowRequest, workflowId);
        return resp.getData();
    }

    /**
     * Reruns the workflow from a specific task
     *
     * @param rerunWorkflowRequest (required)
     * @param workflowId (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> rerunWithHttpInfo(
            RerunWorkflowRequest rerunWorkflowRequest, String workflowId) throws ApiException {
        com.squareup.okhttp.Call call =
                rerunValidateBeforeCall(rerunWorkflowRequest, workflowId, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for resetWorkflow
     *
     * @param workflowId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call resetWorkflowCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/resetcallbacks"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call resetWorkflowValidateBeforeCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling resetWorkflow(Async)");
        }

        com.squareup.okhttp.Call call =
                resetWorkflowCall(workflowId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Resets callback times of all non-terminal SIMPLE tasks to 0
     *
     * @param workflowId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void resetWorkflow(String workflowId) throws ApiException {
        resetWorkflowWithHttpInfo(workflowId);
    }

    /**
     * Resets callback times of all non-terminal SIMPLE tasks to 0
     *
     * @param workflowId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> resetWorkflowWithHttpInfo(String workflowId) throws ApiException {
        com.squareup.okhttp.Call call = resetWorkflowValidateBeforeCall(workflowId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for restart
     *
     * @param workflowId (required)
     * @param useLatestDefinitions (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call restartCall(
            String workflowId,
            Boolean useLatestDefinitions,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/restart"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (useLatestDefinitions != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("useLatestDefinitions", useLatestDefinitions));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call restartValidateBeforeCall(
            String workflowId,
            Boolean useLatestDefinitions,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling restart(Async)");
        }

        com.squareup.okhttp.Call call =
                restartCall(
                        workflowId,
                        useLatestDefinitions,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Restarts a completed workflow
     *
     * @param workflowId (required)
     * @param useLatestDefinitions (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void restart(String workflowId, Boolean useLatestDefinitions) throws ApiException {
        restartWithHttpInfo(workflowId, useLatestDefinitions);
    }

    /**
     * Restarts a completed workflow
     *
     * @param workflowId (required)
     * @param useLatestDefinitions (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> restartWithHttpInfo(String workflowId, Boolean useLatestDefinitions)
            throws ApiException {
        com.squareup.okhttp.Call call =
                restartValidateBeforeCall(workflowId, useLatestDefinitions, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for resumeWorkflow
     *
     * @param workflowId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call resumeWorkflowCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/resume"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call resumeWorkflowValidateBeforeCall(
            String workflowId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling resumeWorkflow(Async)");
        }

        com.squareup.okhttp.Call call =
                resumeWorkflowCall(workflowId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Resumes the workflow
     *
     * @param workflowId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void resumeWorkflow(String workflowId) throws ApiException {
        resumeWorkflowWithHttpInfo(workflowId);
    }

    /**
     * Resumes the workflow
     *
     * @param workflowId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> resumeWorkflowWithHttpInfo(String workflowId) throws ApiException {
        com.squareup.okhttp.Call call = resumeWorkflowValidateBeforeCall(workflowId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for retry
     *
     * @param workflowId (required)
     * @param resumeSubworkflowTasks (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call retryCall(
            String workflowId,
            Boolean resumeSubworkflowTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/retry"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (resumeSubworkflowTasks != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("resumeSubworkflowTasks", resumeSubworkflowTasks));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call retryValidateBeforeCall(
            String workflowId,
            Boolean resumeSubworkflowTasks,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling retry(Async)");
        }

        com.squareup.okhttp.Call call =
                retryCall(
                        workflowId,
                        resumeSubworkflowTasks,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Retries the last failed task
     *
     * @param workflowId (required)
     * @param resumeSubworkflowTasks (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void retry(String workflowId, Boolean resumeSubworkflowTasks) throws ApiException {
        retryWithHttpInfo(workflowId, resumeSubworkflowTasks);
    }

    /**
     * Retries the last failed task
     *
     * @param workflowId (required)
     * @param resumeSubworkflowTasks (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> retryWithHttpInfo(String workflowId, Boolean resumeSubworkflowTasks)
            throws ApiException {
        com.squareup.okhttp.Call call =
                retryValidateBeforeCall(workflowId, resumeSubworkflowTasks, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for search
     *
     * @param queryId (optional)
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param skipCache (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call searchCall(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (queryId != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("queryId", queryId));
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));
        if (skipCache != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("skipCache", skipCache));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call searchValidateBeforeCall(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchCall(
                        queryId,
                        start,
                        size,
                        sort,
                        freeText,
                        query,
                        skipCache,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param queryId (optional)
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param skipCache (optional, default to false)
     * @return ScrollableSearchResultWorkflowSummary
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
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

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param queryId (optional)
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param skipCache (optional, default to false)
     * @return ApiResponse&lt;ScrollableSearchResultWorkflowSummary&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<ScrollableSearchResultWorkflowSummary> searchWithHttpInfo(
            String queryId,
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            Boolean skipCache)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchValidateBeforeCall(
                        queryId, start, size, sort, freeText, query, skipCache, null, null);
        Type localVarReturnType =
                new TypeReference<ScrollableSearchResultWorkflowSummary>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for searchV2
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call searchV2Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-v2";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call searchV2ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchV2Call(
                        start,
                        size,
                        sort,
                        freeText,
                        query,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultWorkflow
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResultWorkflow searchV2(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflow> resp =
                searchV2WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultWorkflow&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResultWorkflow> searchV2WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchV2ValidateBeforeCall(start, size, sort, freeText, query, null, null);
        Type localVarReturnType = new TypeReference<SearchResultWorkflow>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for searchWorkflowsByTasks
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call searchWorkflowsByTasksCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-by-tasks";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call searchWorkflowsByTasksValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchWorkflowsByTasksCall(
                        start,
                        size,
                        sort,
                        freeText,
                        query,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Search for workflows based on task parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultWorkflowSummary
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResultWorkflowSummary searchWorkflowsByTasks(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflowSummary> resp =
                searchWorkflowsByTasksWithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for workflows based on task parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultWorkflowSummary&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResultWorkflowSummary> searchWorkflowsByTasksWithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchWorkflowsByTasksValidateBeforeCall(
                        start, size, sort, freeText, query, null, null);
        Type localVarReturnType = new TypeReference<SearchResultWorkflowSummary>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for searchWorkflowsByTasksV2
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call searchWorkflowsByTasksV2Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/search-by-tasks-v2";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call searchWorkflowsByTasksV2ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchWorkflowsByTasksV2Call(
                        start,
                        size,
                        sort,
                        freeText,
                        query,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Search for workflows based on task parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultWorkflow
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResultWorkflow searchWorkflowsByTasksV2(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflow> resp =
                searchWorkflowsByTasksV2WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for workflows based on task parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultWorkflow&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResultWorkflow> searchWorkflowsByTasksV2WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchWorkflowsByTasksV2ValidateBeforeCall(
                        start, size, sort, freeText, query, null, null);
        Type localVarReturnType = new TypeReference<SearchResultWorkflow>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for skipTaskFromWorkflow
     *
     * @param workflowId (required)
     * @param taskReferenceName (required)
     * @param skipTaskRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call skipTaskFromWorkflowCall(
            String workflowId,
            String taskReferenceName,
            SkipTaskRequest skipTaskRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}/skiptask/{taskReferenceName}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()))
                        .replaceAll(
                                "\\{" + "taskReferenceName" + "\\}",
                                apiClient.escapeString(taskReferenceName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (skipTaskRequest != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("skipTaskRequest", skipTaskRequest));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call skipTaskFromWorkflowValidateBeforeCall(
            String workflowId,
            String taskReferenceName,
            SkipTaskRequest skipTaskRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                skipTaskFromWorkflowCall(
                        workflowId,
                        taskReferenceName,
                        skipTaskRequest,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Skips a given task from a current running workflow
     *
     * @param workflowId (required)
     * @param taskReferenceName (required)
     * @param skipTaskRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void skipTaskFromWorkflow(
            String workflowId, String taskReferenceName, SkipTaskRequest skipTaskRequest)
            throws ApiException {
        skipTaskFromWorkflowWithHttpInfo(workflowId, taskReferenceName, skipTaskRequest);
    }

    /**
     * Skips a given task from a current running workflow
     *
     * @param workflowId (required)
     * @param taskReferenceName (required)
     * @param skipTaskRequest (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> skipTaskFromWorkflowWithHttpInfo(
            String workflowId, String taskReferenceName, SkipTaskRequest skipTaskRequest)
            throws ApiException {
        com.squareup.okhttp.Call call =
                skipTaskFromWorkflowValidateBeforeCall(
                        workflowId, taskReferenceName, skipTaskRequest, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for startWorkflow
     *
     * @param startWorkflowRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call startWorkflowCall(
            StartWorkflowRequest startWorkflowRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = startWorkflowRequest;

        // create path and map variables
        String localVarPath = "/workflow";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }


    public com.squareup.okhttp.Call testWorkflowCall(
            WorkflowTestRequest testRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = testRequest;

        // create path and map variables
        String localVarPath = "/workflow/test";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call startWorkflowValidateBeforeCall(
            StartWorkflowRequest startWorkflowRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (startWorkflowRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'startWorkflowRequest' when calling startWorkflow(Async)");
        }

        com.squareup.okhttp.Call call =
                startWorkflowCall(startWorkflowRequest, progressListener, progressRequestListener);
        return call;
    }

    private com.squareup.okhttp.Call testWorkflowValidateBeforeCall(
            WorkflowTestRequest testRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (testRequest == null) {
            throw new ApiException("Missing the required parameter 'testRequest' when calling testWorkflow");
        }

        com.squareup.okhttp.Call call = testWorkflowCall(testRequest, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Test a workflow execution using mock data
     *
     * @param testRequest (required)
     * @return Workflow
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Workflow testWorkflow(WorkflowTestRequest testRequest) throws ApiException {
        ApiResponse<Workflow> resp = testWorkflowWithHttpInfo(testRequest);
        return resp.getData();
    }

    /**
     * Start a new workflow with StartWorkflowRequest, which allows task to be executed in a domain
     *
     * @param startWorkflowRequest (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public String startWorkflow(StartWorkflowRequest startWorkflowRequest) throws ApiException {
        ApiResponse<String> resp = startWorkflowWithHttpInfo(startWorkflowRequest);
        return resp.getData();
    }

    /**
     * Start a new workflow with StartWorkflowRequest, which allows task to be executed in a domain
     *
     * @param startWorkflowRequest (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> startWorkflowWithHttpInfo(StartWorkflowRequest startWorkflowRequest)
            throws ApiException {
        com.squareup.okhttp.Call call =
                startWorkflowValidateBeforeCall(startWorkflowRequest, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private ApiResponse<Workflow> testWorkflowWithHttpInfo(WorkflowTestRequest testRequest) throws ApiException {
        com.squareup.okhttp.Call call =
                testWorkflowValidateBeforeCall(testRequest, null, null);
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for startWorkflow1
     *
     * @param body (required)
     * @param name (required)
     * @param version (optional)
     * @param correlationId (optional)
     * @param priority (optional, default to 0)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call startWorkflow1Call(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/workflow/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (version != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("version", version));
        if (correlationId != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("correlationId", correlationId));
        if (priority != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("priority", priority));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call startWorkflow1ValidateBeforeCall(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                startWorkflow1Call(
                        body,
                        name,
                        version,
                        correlationId,
                        priority,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Start a new workflow. Returns the ID of the workflow instance that can be later used for
     * tracking
     *
     * @param body (required)
     * @param name (required)
     * @param version (optional)
     * @param correlationId (optional)
     * @param priority (optional, default to 0)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
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

    /**
     * Start a new workflow. Returns the ID of the workflow instance that can be later used for
     * tracking
     *
     * @param body (required)
     * @param name (required)
     * @param version (optional)
     * @param correlationId (optional)
     * @param priority (optional, default to 0)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> startWorkflow1WithHttpInfo(
            Map<String, Object> body,
            String name,
            Integer version,
            String correlationId,
            Integer priority)
            throws ApiException {
        com.squareup.okhttp.Call call =
                startWorkflow1ValidateBeforeCall(
                        body, name, version, correlationId, priority, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for terminate1
     *
     * @param workflowId (required)
     * @param reason (optional)
     * @param triggerFailureWorkflow failure workflow is triggered if set
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call terminate1Call(
            String workflowId,
            String reason,
            boolean triggerFailureWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/workflow/{workflowId}"
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (reason != null) localVarQueryParams.addAll(apiClient.parameterToPair("reason", reason));
        localVarQueryParams.addAll(apiClient.parameterToPair("triggerFailureWorkflow", triggerFailureWorkflow));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call terminate1ValidateBeforeCall(
            String workflowId,
            String reason,
            boolean triggerFailureWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling terminate1(Async)");
        }

        com.squareup.okhttp.Call call =
                terminate1Call(workflowId, reason, triggerFailureWorkflow, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Terminate workflow execution
     *
     * @param workflowId (required)
     * @param reason (optional)
     * @param triggerFailureWorkflow (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void terminateWithAReason(String workflowId, String reason, boolean triggerFailureWorkflow) throws ApiException {
        terminate1WithHttpInfo(workflowId, reason, triggerFailureWorkflow);
    }

    /**
     * Terminate workflow execution
     *
     * @param workflowId (required)
     * @param reason (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> terminate1WithHttpInfo(String workflowId, String reason, boolean triggerFailureWorkflow)
            throws ApiException {
        com.squareup.okhttp.Call call =
                terminate1ValidateBeforeCall(workflowId, reason, triggerFailureWorkflow, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for uploadCompletedWorkflows
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call uploadCompletedWorkflowsCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/workflow/document-store/upload";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call uploadCompletedWorkflowsValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                uploadCompletedWorkflowsCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Force upload all completed workflows to document store
     *
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Object uploadCompletedWorkflows() throws ApiException {
        ApiResponse<Object> resp = uploadCompletedWorkflowsWithHttpInfo();
        return resp.getData();
    }

    /**
     * Force upload all completed workflows to document store
     *
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Object> uploadCompletedWorkflowsWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = uploadCompletedWorkflowsValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<Object>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    public Workflow updateVariables(String workflowId, Map<String, Object> variables) {
        com.squareup.okhttp.Call call = updateVariablesCall(workflowId, variables);
        Type returnType = new TypeReference<Workflow>() {}.getType();
        ApiResponse<Workflow> response = apiClient.execute(call, returnType);
        return response.getData();
    }

    private Call updateVariablesCall(String workflowId, Map<String, Object> variables) {
        Object localVarPostBody = variables;
        String localVarPath = "/workflow/{workflowId}/variables".replaceAll(
                "\\{" + "workflowId" + "\\}",
                apiClient.escapeString(workflowId.toString()));

                        ;
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);
        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                null);
    }

    /**
     * Upgrade running workflow to newer version
     * Upgrade running workflow to newer version
     * @param body  (required)
     * @param workflowId  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void upgradeRunningWorkflow(UpgradeWorkflowRequest body, String workflowId) throws ApiException {
        upgradeRunningWorkflowWithHttpInfo(body, workflowId);
    }

    /**
     * Upgrade running workflow to newer version
     * Upgrade running workflow to newer version
     * @param body  (required)
     * @param workflowId  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> upgradeRunningWorkflowWithHttpInfo(UpgradeWorkflowRequest body, String workflowId) throws ApiException {
        com.squareup.okhttp.Call call = upgradeRunningWorkflowValidateBeforeCall(body, workflowId, null, null);
        return apiClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call upgradeRunningWorkflowValidateBeforeCall(UpgradeWorkflowRequest body, String workflowId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling upgradeRunningWorkflow(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException("Missing the required parameter 'workflowId' when calling upgradeRunningWorkflow(Async)");
        }

        com.squareup.okhttp.Call call = upgradeRunningWorkflowCall(body, workflowId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Build call for upgradeRunningWorkflow
     * @param body  (required)
     * @param workflowId  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call upgradeRunningWorkflowCall(UpgradeWorkflowRequest body, String workflowId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/workflow/{workflowId}/upgrade"
                .replaceAll("\\{" + "workflowId" + "\\}", apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }



    /**
     * Update workflow and task status
     * Updates the workflow variables, tasks and triggers evaluation.
     * @param updateRequest  (required)
     * @param requestId  (required)
     * @param workflowId  (required)
     * @param waitUntilTaskRef  (optional)
     * @param waitForSeconds  (optional, default to 10)
     * @return WorkflowRun
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public WorkflowRun updateWorkflowState(WorkflowStateUpdate updateRequest, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        ApiResponse<WorkflowRun> resp = updateWorkflowAndTaskStateWithHttpInfo(updateRequest, requestId, workflowId, waitUntilTaskRef, waitForSeconds);
        return resp.getData();
    }

    /**
     * Update workflow and task status
     * Updates the workflow variables, tasks and triggers evaluation.
     * @param body  (required)
     * @param requestId  (required)
     * @param workflowId  (required)
     * @param waitUntilTaskRef  (optional)
     * @param waitForSeconds  (optional, default to 10)
     * @return ApiResponse&lt;WorkflowRun&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<WorkflowRun> updateWorkflowAndTaskStateWithHttpInfo(WorkflowStateUpdate body, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds) throws ApiException {
        com.squareup.okhttp.Call call = updateWorkflowAndTaskStateValidateBeforeCall(body, requestId, workflowId, waitUntilTaskRef, waitForSeconds, null, null);
        Type localVarReturnType = new TypeToken<WorkflowRun>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }


    public com.squareup.okhttp.Call updateWorkflowAndTaskStateCall(WorkflowStateUpdate body, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/workflow/{workflowId}/state"
            .replaceAll("\\{" + "workflowId" + "\\}", apiClient.escapeString(workflowId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (requestId != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("requestId", requestId));
        if (waitUntilTaskRef != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("waitUntilTaskRef", waitUntilTaskRef));
        if (waitForSeconds != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("waitForSeconds", waitForSeconds));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call updateWorkflowAndTaskStateValidateBeforeCall(WorkflowStateUpdate body, String requestId, String workflowId, String waitUntilTaskRef, Integer waitForSeconds, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
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

        com.squareup.okhttp.Call call = updateWorkflowAndTaskStateCall(body, requestId, workflowId, waitUntilTaskRef, waitForSeconds, progressListener, progressRequestListener);
        return call;
    }
}
