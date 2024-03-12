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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.conductor.common.metadata.tasks.PollData;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;

import com.amazonaws.util.EC2MetadataUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class TaskResourceApi {
    private ApiClient apiClient;

    public TaskResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public TaskResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for all
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call allCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/all";

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call allValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call = allCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the details about each queue
     *
     * @return Map&lt;String, Long&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Long> all() throws ApiException {
        ApiResponse<Map<String, Long>> resp = allWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get the details about each queue
     *
     * @return ApiResponse&lt;Map&lt;String, Long&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Long>> allWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = allValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<Map<String, Long>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for allVerbose
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call allVerboseCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/all/verbose";

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call allVerboseValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call = allVerboseCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the details about each queue
     *
     * @return Map&lt;String, Map&lt;String, Map&lt;String, Long&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Map<String, Map<String, Long>>> allVerbose() throws ApiException {
        ApiResponse<Map<String, Map<String, Map<String, Long>>>> resp = allVerboseWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get the details about each queue
     *
     * @return ApiResponse&lt;Map&lt;String, Map&lt;String, Map&lt;String, Long&gt;&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Map<String, Map<String, Long>>>> allVerboseWithHttpInfo()
            throws ApiException {
        com.squareup.okhttp.Call call = allVerboseValidateBeforeCall(null, null);
        Type localVarReturnType =
                new TypeReference<Map<String, Map<String, Map<String, Long>>>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for batchPoll
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @param count (optional, default to 1)
     * @param timeout (optional, default to 100)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call batchPollCall(
            String tasktype,
            String workerid,
            String domain,
            Integer count,
            Integer timeout,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/poll/batch/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (workerid != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerid));
        if (domain != null) localVarQueryParams.addAll(apiClient.parameterToPair("domain", domain));
        if (count != null) localVarQueryParams.addAll(apiClient.parameterToPair("count", count));
        if (timeout != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("timeout", timeout));

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

    private com.squareup.okhttp.Call batchPollValidateBeforeCall(
            String tasktype,
            String workerid,
            String domain,
            Integer count,
            Integer timeout,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling batchPoll(Async)");
        }

        com.squareup.okhttp.Call call =
                batchPollCall(
                        tasktype,
                        workerid,
                        domain,
                        count,
                        timeout,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Batch poll for a task of a certain type
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @param count (optional, default to 1)
     * @param timeout (optional, default to 100)
     * @return List&lt;Task&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<Task> batchPoll(
            String tasktype, String workerid, String domain, Integer count, Integer timeout)
            throws ApiException {
        ApiResponse<List<Task>> resp =
                batchPollWithHttpInfo(tasktype, workerid, domain, count, timeout);
        return resp.getData();
    }

    /**
     * Batch poll for a task of a certain type
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @param count (optional, default to 1)
     * @param timeout (optional, default to 100)
     * @return ApiResponse&lt;List&lt;Task&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<Task>> batchPollWithHttpInfo(
            String tasktype, String workerid, String domain, Integer count, Integer timeout)
            throws ApiException {
        com.squareup.okhttp.Call call =
                batchPollValidateBeforeCall(tasktype, workerid, domain, count, timeout, null, null);
        Type localVarReturnType = new TypeReference<List<Task>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getAllPollData
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllPollDataCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/polldata/all";

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getAllPollDataValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                getAllPollDataCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the last poll data for all task types
     *
     * @return List&lt;PollData&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<PollData> getAllPollData() throws ApiException {
        ApiResponse<List<PollData>> resp = getAllPollDataWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get the last poll data for all task types
     *
     * @return ApiResponse&lt;List&lt;PollData&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<PollData>> getAllPollDataWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = getAllPollDataValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<PollData>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getExternalStorageLocation1
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getExternalStorageLocation1Call(
            String path,
            String operation,
            String payloadType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/externalstoragelocation";

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

    private com.squareup.okhttp.Call getExternalStorageLocation1ValidateBeforeCall(
            String path,
            String operation,
            String payloadType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException(
                    "Missing the required parameter 'path' when calling getExternalStorageLocation1(Async)");
        }
        // verify the required parameter 'operation' is set
        if (operation == null) {
            throw new ApiException(
                    "Missing the required parameter 'operation' when calling getExternalStorageLocation1(Async)");
        }
        // verify the required parameter 'payloadType' is set
        if (payloadType == null) {
            throw new ApiException(
                    "Missing the required parameter 'payloadType' when calling getExternalStorageLocation1(Async)");
        }

        com.squareup.okhttp.Call call =
                getExternalStorageLocation1Call(
                        path, operation, payloadType, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the external uri where the task payload is to be stored
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @return ExternalStorageLocation
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ExternalStorageLocation getExternalStorageLocation1(
            String path, String operation, String payloadType) throws ApiException {
        ApiResponse<ExternalStorageLocation> resp =
                getExternalStorageLocation1WithHttpInfo(path, operation, payloadType);
        return resp.getData();
    }

    /**
     * Get the external uri where the task payload is to be stored
     *
     * @param path (required)
     * @param operation (required)
     * @param payloadType (required)
     * @return ApiResponse&lt;ExternalStorageLocation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<ExternalStorageLocation> getExternalStorageLocation1WithHttpInfo(
            String path, String operation, String payloadType) throws ApiException {
        com.squareup.okhttp.Call call =
                getExternalStorageLocation1ValidateBeforeCall(
                        path, operation, payloadType, null, null);
        Type localVarReturnType = new TypeReference<ExternalStorageLocation>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getPollData
     *
     * @param taskType (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getPollDataCall(
            String taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/polldata";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (taskType != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("taskType", taskType));

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

    private com.squareup.okhttp.Call getPollDataValidateBeforeCall(
            String taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'taskType' is set
        if (taskType == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskType' when calling getPollData(Async)");
        }

        com.squareup.okhttp.Call call =
                getPollDataCall(taskType, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the last poll data for a given task type
     *
     * @param taskType (required)
     * @return List&lt;PollData&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<PollData> getPollData(String taskType) throws ApiException {
        ApiResponse<List<PollData>> resp = getPollDataWithHttpInfo(taskType);
        return resp.getData();
    }

    /**
     * Get the last poll data for a given task type
     *
     * @param taskType (required)
     * @return ApiResponse&lt;List&lt;PollData&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<PollData>> getPollDataWithHttpInfo(String taskType)
            throws ApiException {
        com.squareup.okhttp.Call call = getPollDataValidateBeforeCall(taskType, null, null);
        Type localVarReturnType = new TypeReference<List<PollData>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTask
     *
     * @param taskId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getTaskCall(
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId.toString()));

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getTaskValidateBeforeCall(
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling getTask(Async)");
        }

        com.squareup.okhttp.Call call =
                getTaskCall(taskId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get task by Id
     *
     * @param taskId (required)
     * @return Task
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Task getTask(String taskId) throws ApiException {
        ApiResponse<Task> resp = getTaskWithHttpInfo(taskId);
        return resp.getData();
    }

    /**
     * Get task by Id
     *
     * @param taskId (required)
     * @return ApiResponse&lt;Task&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Task> getTaskWithHttpInfo(String taskId) throws ApiException {
        com.squareup.okhttp.Call call = getTaskValidateBeforeCall(taskId, null, null);
        Type localVarReturnType = new TypeReference<Task>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTaskLogs
     *
     * @param taskId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getTaskLogsCall(
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}/log"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId.toString()));

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getTaskLogsValidateBeforeCall(
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling getTaskLogs(Async)");
        }

        com.squareup.okhttp.Call call =
                getTaskLogsCall(taskId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get Task Execution Logs
     *
     * @param taskId (required)
     * @return List&lt;TaskExecLog&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<TaskExecLog> getTaskLogs(String taskId) throws ApiException {
        ApiResponse<List<TaskExecLog>> resp = getTaskLogsWithHttpInfo(taskId);
        return resp.getData();
    }

    /**
     * Get Task Execution Logs
     *
     * @param taskId (required)
     * @return ApiResponse&lt;List&lt;TaskExecLog&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<TaskExecLog>> getTaskLogsWithHttpInfo(String taskId)
            throws ApiException {
        com.squareup.okhttp.Call call = getTaskLogsValidateBeforeCall(taskId, null, null);
        Type localVarReturnType = new TypeReference<List<TaskExecLog>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for log
     *
     * @param body (required)
     * @param taskId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call logCall(
            String body,
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}/log"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

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

    private com.squareup.okhttp.Call logValidateBeforeCall(
            String body,
            String taskId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling log(Async)");
        }
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling log(Async)");
        }

        com.squareup.okhttp.Call call =
                logCall(body, taskId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Log Task Execution Details
     *
     * @param body (required)
     * @param taskId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void log(String body, String taskId) throws ApiException {
        logWithHttpInfo(body, taskId);
    }

    /**
     * Log Task Execution Details
     *
     * @param body (required)
     * @param taskId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> logWithHttpInfo(String body, String taskId) throws ApiException {
        com.squareup.okhttp.Call call = logValidateBeforeCall(body, taskId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for poll
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call pollCall(
            String tasktype,
            String workerid,
            String domain,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/poll/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (workerid != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerid));
        if (domain != null) localVarQueryParams.addAll(apiClient.parameterToPair("domain", domain));

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

    private com.squareup.okhttp.Call pollValidateBeforeCall(
            String tasktype,
            String workerid,
            String domain,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling poll(Async)");
        }

        com.squareup.okhttp.Call call =
                pollCall(tasktype, workerid, domain, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Poll for a task of a certain type
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @return Task
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Task poll(String tasktype, String workerid, String domain) throws ApiException {
        ApiResponse<Task> resp = pollWithHttpInfo(tasktype, workerid, domain);
        return resp.getData();
    }

    /**
     * Poll for a task of a certain type
     *
     * @param tasktype (required)
     * @param workerid (optional)
     * @param domain (optional)
     * @return ApiResponse&lt;Task&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Task> pollWithHttpInfo(String tasktype, String workerid, String domain)
            throws ApiException {
        com.squareup.okhttp.Call call =
                pollValidateBeforeCall(tasktype, workerid, domain, null, null);
        Type localVarReturnType = new TypeReference<Task>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for requeuePendingTask
     *
     * @param taskType (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call requeuePendingTaskCall(
            String taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/queue/requeue/{taskType}"
                        .replaceAll(
                                "\\{" + "taskType" + "\\}",
                                apiClient.escapeString(taskType.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
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

    private com.squareup.okhttp.Call requeuePendingTaskValidateBeforeCall(
            String taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'taskType' is set
        if (taskType == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskType' when calling requeuePendingTask(Async)");
        }

        com.squareup.okhttp.Call call =
                requeuePendingTaskCall(taskType, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Requeue pending tasks
     *
     * @param taskType (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public String requeuePendingTask(String taskType) throws ApiException {
        ApiResponse<String> resp = requeuePendingTaskWithHttpInfo(taskType);
        return resp.getData();
    }

    /**
     * Requeue pending tasks
     *
     * @param taskType (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> requeuePendingTaskWithHttpInfo(String taskType)
            throws ApiException {
        com.squareup.okhttp.Call call = requeuePendingTaskValidateBeforeCall(taskType, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for search1
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
    public com.squareup.okhttp.Call searchTasksCall(
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
        String localVarPath = "/tasks/search";

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

    private com.squareup.okhttp.Call searchTasksValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchTasksCall(
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
     * Search for tasks based in payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultTaskSummary
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResult<TaskSummary> searchTasks(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResult<TaskSummary>> resp =
                searchTasksWithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for tasks based in payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultTaskSummary&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResult<TaskSummary>> searchTasksWithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchTasksValidateBeforeCall(start, size, sort, freeText, query, null, null);
        Type localVarReturnType = new TypeReference<SearchResult<TaskSummary>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for searchV21
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
    public com.squareup.okhttp.Call searchV21Call(
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
        String localVarPath = "/tasks/search-v2";

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

    private com.squareup.okhttp.Call searchV21ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                searchV21Call(
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
     * Search for tasks based in payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultTask
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResultTask searchV21(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultTask> resp =
                searchV21WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for tasks based in payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultTask&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResultTask> searchV21WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        com.squareup.okhttp.Call call =
                searchV21ValidateBeforeCall(start, size, sort, freeText, query, null, null);
        Type localVarReturnType = new TypeReference<SearchResultTask>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for size
     *
     * @param taskType (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call sizeCall(
            List<String> taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/sizes";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (taskType != null)
            localVarCollectionQueryParams.addAll(
                    apiClient.parameterToPairs("multi", "taskType", taskType));

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

    private com.squareup.okhttp.Call sizeValidateBeforeCall(
            List<String> taskType,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                sizeCall(taskType, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get Task type queue sizes
     *
     * @param taskType (optional)
     * @return Map&lt;String, Integer&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Integer> size(List<String> taskType) throws ApiException {
        ApiResponse<Map<String, Integer>> resp = sizeWithHttpInfo(taskType);
        return resp.getData();
    }

    /**
     * Get Task type queue sizes
     *
     * @param taskType (optional)
     * @return ApiResponse&lt;Map&lt;String, Integer&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Integer>> sizeWithHttpInfo(List<String> taskType)
            throws ApiException {
        com.squareup.okhttp.Call call = sizeValidateBeforeCall(taskType, null, null);
        Type localVarReturnType = new TypeReference<Map<String, Integer>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for updateTask
     *
     * @param taskResult (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private com.squareup.okhttp.Call updateTaskCall(
            TaskResult taskResult,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = taskResult;

        // create path and map variables
        String localVarPath = "/tasks";

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

    private com.squareup.okhttp.Call updateTaskValidateBeforeCall(
            TaskResult taskResult,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (taskResult == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskResult' when calling updateTask(Async)");
        }

        com.squareup.okhttp.Call call =
                updateTaskCall(taskResult, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Update a task
     *
     * @param taskResult (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public String updateTask(TaskResult taskResult) throws ApiException {
        ApiResponse<String> resp = updateTaskWithHttpInfo(taskResult);
        return resp.getData();
    }

    /**
     * Update a task
     *
     * @param taskResult (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> updateTaskWithHttpInfo(TaskResult taskResult) throws ApiException {
        com.squareup.okhttp.Call call = updateTaskValidateBeforeCall(taskResult, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private com.squareup.okhttp.Call updateTaskByRefNameCall(
            Map<String, Object> body,
            String workflowId,
            String taskRefName,
            String status,
            String workerId,
            boolean sync)
            throws ApiException {
        Object localVarPostBody = body;
        String path =  "/tasks/{workflowId}/{taskRefName}/{status}";
        if(sync) {
            path += "/sync";
        }
        // create path and map variables
        String localVarPath =
                path
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId.toString()))
                        .replaceAll(
                                "\\{" + "taskRefName" + "\\}",
                                apiClient.escapeString(taskRefName.toString()))
                        .replaceAll(
                                "\\{" + "status" + "\\}",
                                apiClient.escapeString(status.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        if (workerId == null) {
                workerId = getIdentity();
        }
        localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerId));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"text/plain"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
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

    private com.squareup.okhttp.Call updateTask1ValidateBeforeCall(
            Map<String, Object> body,
            String workflowId,
            String taskRefName,
            String status,
            boolean sync)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling updateTask1(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling updateTask1(Async)");
        }
        // verify the required parameter 'taskRefName' is set
        if (taskRefName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskRefName' when calling updateTask1(Async)");
        }
        // verify the required parameter 'status' is set
        if (status == null) {
            throw new ApiException(
                    "Missing the required parameter 'status' when calling updateTask1(Async)");
        }

        com.squareup.okhttp.Call call =
                updateTaskByRefNameCall(
                        body,
                        workflowId,
                        taskRefName,
                        status,
                        getIdentity(),
                        sync);
        return call;
    }

    /**
     * Update a task By Ref Name
     *
     * @param body (required)
     * @param workflowId (required)
     * @param taskRefName (required)
     * @param status (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    @Deprecated
    public String updateTask1(
            Map<String, Object> body, String workflowId, String taskRefName, String status)
            throws ApiException {
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        ApiResponse<String> resp = updateTask1WithHttpInfo(body, workflowId, taskRefName, status, false, localVarReturnType);
        return resp.getData();
    }

    /**
     *
     * @param output Task Output
     * @param workflowId Workflow Id
     * @param taskRefName Reference name of the task to be updated
     * @param status Status
     * @return Task Id
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response
     */
    public String updateTaskByRefName(Map<String, Object> output, String workflowId, String taskRefName, String status)  throws ApiException {
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        ApiResponse<String> resp = updateTask1WithHttpInfo(output, workflowId, taskRefName, status, false, localVarReturnType);
        return resp.getData();
    }

    /**
     *
     * @param output Task Output
     * @param workflowId Workflow Id
     * @param taskRefName Reference name of the task to be updated
     * @param status Status
     * @return Status of the workflow after updating the task
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response
     */
    public Workflow updateTaskSync(Map<String, Object> output, String workflowId, String taskRefName, String status)  throws ApiException {
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        ApiResponse<Workflow> resp = updateTask1WithHttpInfo(output, workflowId, taskRefName, status, true, localVarReturnType);
        return resp.getData();
    }

    /**
     * Update a task By Ref Name
     *
     * @param body (required)
     * @param workflowId (required)
     * @param taskRefName (required)
     * @param status (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private <T>ApiResponse<T> updateTask1WithHttpInfo(Map<String, Object> body, String workflowId, String taskRefName, String status, boolean sync, Type returnType)
            throws ApiException {
        com.squareup.okhttp.Call call =
                updateTask1ValidateBeforeCall(body, workflowId, taskRefName, status, sync);
        return apiClient.execute(call, returnType);
    }

    private ApiResponse<Workflow> updateTaskSyncWithHttpInfo(Map<String, Object> body, String workflowId, String taskRefName, String status, boolean sync)
            throws ApiException {
        com.squareup.okhttp.Call call =
                updateTask1ValidateBeforeCall(body, workflowId, taskRefName, status, sync);
        Type localVarReturnType = new TypeReference<Workflow>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }
        if (serverId == null) {
            serverId =
                    (EC2MetadataUtils.getInstanceId() == null)
                            ? System.getProperty("user.name")
                            : EC2MetadataUtils.getInstanceId();
        }
        return serverId;
    }
}
