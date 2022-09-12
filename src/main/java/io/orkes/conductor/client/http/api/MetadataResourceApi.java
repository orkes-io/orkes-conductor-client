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

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.http.*;

import com.google.gson.reflect.TypeToken;
import io.orkes.conductor.client.http.orkesclient.ApiClient;

public class MetadataResourceApi {
    private ApiClient apiClient;

    public MetadataResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public MetadataResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for create
     *
     * @param body (required)
     * @param overwrite (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call createCall(
            WorkflowDef body,
            Boolean overwrite,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (overwrite != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("overwrite", overwrite));

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call createValidateBeforeCall(
            WorkflowDef body,
            Boolean overwrite,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling create(Async)");
        }

        com.squareup.okhttp.Call call =
                createCall(body, overwrite, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create a new workflow definition
     *
     * @param body (required)
     * @param overwrite (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void create(WorkflowDef body, Boolean overwrite) throws ApiException {
        createWithHttpInfo(body, overwrite);
    }

    /**
     * Create a new workflow definition
     *
     * @param body (required)
     * @param overwrite (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> createWithHttpInfo(WorkflowDef body, Boolean overwrite)
            throws ApiException {
        com.squareup.okhttp.Call call = createValidateBeforeCall(body, overwrite, null, null);
        return apiClient.execute(call);
    }

    /**
     * Create a new workflow definition (asynchronously)
     *
     * @param body (required)
     * @param overwrite (optional, default to false)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call createAsync(
            WorkflowDef body, Boolean overwrite, final ApiCallback<Void> callback)
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
                createValidateBeforeCall(
                        body, overwrite, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for get
     *
     * @param name (required)
     * @param version (optional)
     * @param metadata (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getCall(
            String name,
            Integer version,
            Boolean metadata,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (version != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("version", version));
        if (metadata != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("metadata", metadata));

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getValidateBeforeCall(
            String name,
            Integer version,
            Boolean metadata,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling get(Async)");
        }

        com.squareup.okhttp.Call call =
                getCall(name, version, metadata, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Retrieves workflow definition along with blueprint
     *
     * @param name (required)
     * @param version (optional)
     * @param metadata (optional, default to false)
     * @return WorkflowDef
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public WorkflowDef get(String name, Integer version, Boolean metadata) throws ApiException {
        ApiResponse<WorkflowDef> resp = getWithHttpInfo(name, version, metadata);
        return resp.getData();
    }

    /**
     * Retrieves workflow definition along with blueprint
     *
     * @param name (required)
     * @param version (optional)
     * @param metadata (optional, default to false)
     * @return ApiResponse&lt;WorkflowDef&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<WorkflowDef> getWithHttpInfo(String name, Integer version, Boolean metadata)
            throws ApiException {
        com.squareup.okhttp.Call call = getValidateBeforeCall(name, version, metadata, null, null);
        Type localVarReturnType = new TypeToken<WorkflowDef>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves workflow definition along with blueprint (asynchronously)
     *
     * @param name (required)
     * @param version (optional)
     * @param metadata (optional, default to false)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call getAsync(
            String name, Integer version, Boolean metadata, final ApiCallback<WorkflowDef> callback)
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
                getValidateBeforeCall(
                        name, version, metadata, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<WorkflowDef>() {}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getAllWorkflows
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAllWorkflowsCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (access != null) localVarQueryParams.addAll(apiClient.parameterToPair("access", access));
        if (metadata != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("metadata", metadata));
        if (tagKey != null) localVarQueryParams.addAll(apiClient.parameterToPair("tagKey", tagKey));
        if (tagValue != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("tagValue", tagValue));

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAllWorkflowsValidateBeforeCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                getAllWorkflowsCall(
                        access,
                        metadata,
                        tagKey,
                        tagValue,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Retrieves all workflow definition along with blueprint
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @return List&lt;WorkflowDef&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<WorkflowDef> getAllWorkflows(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        ApiResponse<List<WorkflowDef>> resp =
                getAllWorkflowsWithHttpInfo(access, metadata, tagKey, tagValue);
        return resp.getData();
    }

    /**
     * Retrieves all workflow definition along with blueprint
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @return ApiResponse&lt;List&lt;WorkflowDef&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<WorkflowDef>> getAllWorkflowsWithHttpInfo(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        com.squareup.okhttp.Call call =
                getAllWorkflowsValidateBeforeCall(access, metadata, tagKey, tagValue, null, null);
        Type localVarReturnType = new TypeToken<List<WorkflowDef>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieves all workflow definition along with blueprint (asynchronously)
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call getAllWorkflowsAsync(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ApiCallback<List<WorkflowDef>> callback)
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
                getAllWorkflowsValidateBeforeCall(
                        access,
                        metadata,
                        tagKey,
                        tagValue,
                        progressListener,
                        progressRequestListener);
        Type localVarReturnType = new TypeToken<List<WorkflowDef>>() {}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getTaskDef
     *
     * @param tasktype (required)
     * @param metadata (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getTaskDefCall(
            String tasktype,
            Boolean metadata,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/taskdefs/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (metadata != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("metadata", metadata));

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getTaskDefValidateBeforeCall(
            String tasktype,
            Boolean metadata,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling getTaskDef(Async)");
        }

        com.squareup.okhttp.Call call =
                getTaskDefCall(tasktype, metadata, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Gets the task definition
     *
     * @param tasktype (required)
     * @param metadata (optional, default to false)
     * @return TaskDef
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public TaskDef getTaskDef(String tasktype, Boolean metadata) throws ApiException {
        ApiResponse<TaskDef> resp = getTaskDefWithHttpInfo(tasktype, metadata);
        return resp.getData();
    }

    /**
     * Gets the task definition
     *
     * @param tasktype (required)
     * @param metadata (optional, default to false)
     * @return ApiResponse&lt;TaskDef&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<TaskDef> getTaskDefWithHttpInfo(String tasktype, Boolean metadata)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getTaskDefValidateBeforeCall(tasktype, metadata, null, null);
        Type localVarReturnType = new TypeToken<TaskDef>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Gets the task definition (asynchronously)
     *
     * @param tasktype (required)
     * @param metadata (optional, default to false)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call getTaskDefAsync(
            String tasktype, Boolean metadata, final ApiCallback<TaskDef> callback)
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
                getTaskDefValidateBeforeCall(
                        tasktype, metadata, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<TaskDef>() {}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getTaskDefs
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getTaskDefsCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (access != null) localVarQueryParams.addAll(apiClient.parameterToPair("access", access));
        if (metadata != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("metadata", metadata));
        if (tagKey != null) localVarQueryParams.addAll(apiClient.parameterToPair("tagKey", tagKey));
        if (tagValue != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("tagValue", tagValue));

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getTaskDefsValidateBeforeCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                getTaskDefsCall(
                        access,
                        metadata,
                        tagKey,
                        tagValue,
                        progressListener,
                        progressRequestListener);
        return call;
    }

    /**
     * Gets all task definition
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @return List&lt;TaskDef&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<TaskDef> getTaskDefs(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        ApiResponse<List<TaskDef>> resp =
                getTaskDefsWithHttpInfo(access, metadata, tagKey, tagValue);
        return resp.getData();
    }

    /**
     * Gets all task definition
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @return ApiResponse&lt;List&lt;TaskDef&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<TaskDef>> getTaskDefsWithHttpInfo(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        com.squareup.okhttp.Call call =
                getTaskDefsValidateBeforeCall(access, metadata, tagKey, tagValue, null, null);
        Type localVarReturnType = new TypeToken<List<TaskDef>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Gets all task definition (asynchronously)
     *
     * @param access (optional, default to READ)
     * @param metadata (optional, default to false)
     * @param tagKey (optional)
     * @param tagValue (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call getTaskDefsAsync(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue,
            final ApiCallback<List<TaskDef>> callback)
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
                getTaskDefsValidateBeforeCall(
                        access,
                        metadata,
                        tagKey,
                        tagValue,
                        progressListener,
                        progressRequestListener);
        Type localVarReturnType = new TypeToken<List<TaskDef>>() {}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for registerTaskDef
     *
     * @param body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call registerTaskDefCall(
            List<TaskDef> body,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

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

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call registerTaskDefValidateBeforeCall(
            List<TaskDef> body,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling registerTaskDef(Async)");
        }

        com.squareup.okhttp.Call call =
                registerTaskDefCall(body, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create or update task definition(s)
     *
     * @param body (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void registerTaskDef(List<TaskDef> body) throws ApiException {
        registerTaskDefWithHttpInfo(body);
    }

    /**
     * Create or update task definition(s)
     *
     * @param body (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> registerTaskDefWithHttpInfo(List<TaskDef> body) throws ApiException {
        com.squareup.okhttp.Call call = registerTaskDefValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * Create or update task definition(s) (asynchronously)
     *
     * @param body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call registerTaskDefAsync(
            List<TaskDef> body, final ApiCallback<Void> callback) throws ApiException {

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
                registerTaskDefValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for unregisterTaskDef
     *
     * @param tasktype (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call unregisterTaskDefCall(
            String tasktype,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/taskdefs/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype.toString()));

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
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call unregisterTaskDefValidateBeforeCall(
            String tasktype,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling unregisterTaskDef(Async)");
        }

        com.squareup.okhttp.Call call =
                unregisterTaskDefCall(tasktype, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Remove a task definition
     *
     * @param tasktype (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void unregisterTaskDef(String tasktype) throws ApiException {
        unregisterTaskDefWithHttpInfo(tasktype);
    }

    /**
     * Remove a task definition
     *
     * @param tasktype (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> unregisterTaskDefWithHttpInfo(String tasktype) throws ApiException {
        com.squareup.okhttp.Call call = unregisterTaskDefValidateBeforeCall(tasktype, null, null);
        return apiClient.execute(call);
    }

    /**
     * Remove a task definition (asynchronously)
     *
     * @param tasktype (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call unregisterTaskDefAsync(
            String tasktype, final ApiCallback<Void> callback) throws ApiException {

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
                unregisterTaskDefValidateBeforeCall(
                        tasktype, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for unregisterWorkflowDef
     *
     * @param name (required)
     * @param version (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call unregisterWorkflowDefCall(
            String name,
            Integer version,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name.toString()))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                apiClient.escapeString(version.toString()));

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
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call unregisterWorkflowDefValidateBeforeCall(
            String name,
            Integer version,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling unregisterWorkflowDef(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException(
                    "Missing the required parameter 'version' when calling unregisterWorkflowDef(Async)");
        }

        com.squareup.okhttp.Call call =
                unregisterWorkflowDefCall(name, version, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Removes workflow definition. It does not remove workflows associated with the definition.
     *
     * @param name (required)
     * @param version (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void unregisterWorkflowDef(String name, Integer version) throws ApiException {
        unregisterWorkflowDefWithHttpInfo(name, version);
    }

    /**
     * Removes workflow definition. It does not remove workflows associated with the definition.
     *
     * @param name (required)
     * @param version (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> unregisterWorkflowDefWithHttpInfo(String name, Integer version)
            throws ApiException {
        com.squareup.okhttp.Call call =
                unregisterWorkflowDefValidateBeforeCall(name, version, null, null);
        return apiClient.execute(call);
    }

    /**
     * Removes workflow definition. It does not remove workflows associated with the definition.
     * (asynchronously)
     *
     * @param name (required)
     * @param version (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call unregisterWorkflowDefAsync(
            String name, Integer version, final ApiCallback<Void> callback) throws ApiException {

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
                unregisterWorkflowDefValidateBeforeCall(
                        name, version, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for update
     *
     * @param body (required)
     * @param overwrite (optional, default to true)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call updateCall(
            List<WorkflowDef> body,
            Boolean overwrite,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (overwrite != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("overwrite", overwrite));

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call updateValidateBeforeCall(
            List<WorkflowDef> body,
            Boolean overwrite,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling update(Async)");
        }

        com.squareup.okhttp.Call call =
                updateCall(body, overwrite, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create or update workflow definition(s)
     *
     * @param body (required)
     * @param overwrite (optional, default to true)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void update(List<WorkflowDef> body, Boolean overwrite) throws ApiException {
        updateWithHttpInfo(body, overwrite);
    }

    /**
     * Create or update workflow definition(s)
     *
     * @param body (required)
     * @param overwrite (optional, default to true)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> updateWithHttpInfo(List<WorkflowDef> body, Boolean overwrite)
            throws ApiException {
        com.squareup.okhttp.Call call = updateValidateBeforeCall(body, overwrite, null, null);
        return apiClient.execute(call);
    }

    /**
     * Create or update workflow definition(s) (asynchronously)
     *
     * @param body (required)
     * @param overwrite (optional, default to true)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call updateAsync(
            List<WorkflowDef> body, Boolean overwrite, final ApiCallback<Void> callback)
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
                updateValidateBeforeCall(
                        body, overwrite, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for updateTaskDef
     *
     * @param body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call updateTaskDefCall(
            TaskDef body,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call updateTaskDefValidateBeforeCall(
            TaskDef body,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling updateTaskDef(Async)");
        }

        com.squareup.okhttp.Call call =
                updateTaskDefCall(body, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Update an existing task
     *
     * @param body (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void updateTaskDef(TaskDef body) throws ApiException {
        updateTaskDefWithHttpInfo(body);
    }

    /**
     * Update an existing task
     *
     * @param body (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> updateTaskDefWithHttpInfo(TaskDef body) throws ApiException {
        com.squareup.okhttp.Call call = updateTaskDefValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * Update an existing task (asynchronously)
     *
     * @param body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body
     *     object
     */
    public com.squareup.okhttp.Call updateTaskDefAsync(
            TaskDef body, final ApiCallback<Void> callback) throws ApiException {

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
                updateTaskDefValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
}
