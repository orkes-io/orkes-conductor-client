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

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.BulkResponse;

import com.fasterxml.jackson.core.type.TypeReference;


public class WorkflowBulkResourceApi {
    private ApiClient apiClient;

    public WorkflowBulkResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public WorkflowBulkResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
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
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = workflowIds;

        // create path and map variables
        String localVarPath = "/workflow/bulk/pause";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

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
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
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

    private okhttp3.Call pauseWorkflow1ValidateBeforeCall(
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling pauseWorkflow1(Async)");
        }

        okhttp3.Call call =
                pauseWorkflow1Call(workflowIds, progressListener, progressRequestListener);
        return call;
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
        okhttp3.Call call = pauseWorkflow1ValidateBeforeCall(workflowIds, null, null);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
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
            Boolean useLatestDefinitions,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = workflowIds;

        // create path and map variables
        String localVarPath = "/workflow/bulk/restart";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (useLatestDefinitions != null)
            localVarQueryParams.addAll(
                    apiClient.parameterToPair("useLatestDefinitions", useLatestDefinitions));

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
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
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

    private okhttp3.Call restart1ValidateBeforeCall(
            List<String> workflowIds,
            Boolean useLatestDefinitions,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling restart1(Async)");
        }

        okhttp3.Call call =
                restart1Call(
                        workflowIds,
                        useLatestDefinitions,
                        progressListener,
                        progressRequestListener);
        return call;
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
                restart1ValidateBeforeCall(workflowIds, useLatestDefinitions, null, null);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
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
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = workflowIds;

        // create path and map variables
        String localVarPath = "/workflow/bulk/resume";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

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
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
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

    private okhttp3.Call resumeWorkflow1ValidateBeforeCall(
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling resumeWorkflow1(Async)");
        }

        okhttp3.Call call =
                resumeWorkflow1Call(workflowIds, progressListener, progressRequestListener);
        return call;
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
        okhttp3.Call call = resumeWorkflow1ValidateBeforeCall(workflowIds, null, null);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
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
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = workflowIds;

        // create path and map variables
        String localVarPath = "/workflow/bulk/retry";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

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
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
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

    private okhttp3.Call retry1ValidateBeforeCall(
            List<String> workflowIds,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling retry1(Async)");
        }

        okhttp3.Call call =
                retry1Call(workflowIds, progressListener, progressRequestListener);
        return call;
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
        okhttp3.Call call = retry1ValidateBeforeCall(workflowIds, null, null);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
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
            boolean triggerFailureWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = workflowIds;

        // create path and map variables
        String localVarPath = "/workflow/bulk/terminate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (reason != null) localVarQueryParams.addAll(apiClient.parameterToPair("reason", reason));
        localVarQueryParams.addAll(apiClient.parameterToPair("triggerFailureWorkflow", triggerFailureWorkflow));

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
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
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

    private okhttp3.Call terminateValidateBeforeCall(
            List<String> workflowIds,
            String reason,
            boolean triggerFailureWorkflow,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowIds == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling terminate(Async)");
        }

        okhttp3.Call call =
                terminateCall(workflowIds, reason, triggerFailureWorkflow, progressListener, progressRequestListener);
        return call;
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
                terminateValidateBeforeCall(workflowIds, reason, triggerFailureWorkflow, null, null);
        Type localVarReturnType = new TypeReference<BulkResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }
}
