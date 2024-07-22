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

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.http.ProgressRequestBody;
import io.orkes.conductor.client.http.ProgressResponseBody;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TagsApi {
    private ApiClient apiClient;

    public TagsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for addTaskTag
     *
     * @param tagObject (required)
     * @param taskName (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call addTaskTagCall(
            TagObject tagObject,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagObject;

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                apiClient.escapeString(taskName.toString()));

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

    private okhttp3.Call addTaskTagValidateBeforeCall(
            TagObject tagObject,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling addTaskTag(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling addTaskTag(Async)");
        }

        okhttp3.Call call =
                addTaskTagCall(tagObject, taskName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Adds the tag to the task
     *
     * @param tagObject (required)
     * @param taskName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void addTaskTag(TagObject tagObject, String taskName) throws ApiException {
        addTaskTagWithHttpInfo(tagObject, taskName);
    }

    /**
     * Adds the tag to the task
     *
     * @param tagObject (required)
     * @param taskName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> addTaskTagWithHttpInfo(TagObject tagObject, String taskName)
            throws ApiException {
        okhttp3.Call call =
                addTaskTagValidateBeforeCall(tagObject, taskName, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for addWorkflowTag
     *
     * @param tagObject (required)
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call addWorkflowTagCall(
            TagObject tagObject,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagObject;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

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

    private okhttp3.Call addWorkflowTagValidateBeforeCall(
            TagObject tagObject,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling addWorkflowTag(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling addWorkflowTag(Async)");
        }

        okhttp3.Call call =
                addWorkflowTagCall(tagObject, name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Adds the tag to the workflow
     *
     * @param tagObject (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void addWorkflowTag(TagObject tagObject, String name) throws ApiException {
        addWorkflowTagWithHttpInfo(tagObject, name);
    }

    /**
     * Adds the tag to the workflow
     *
     * @param tagObject (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> addWorkflowTagWithHttpInfo(TagObject tagObject, String name)
            throws ApiException {
        okhttp3.Call call =
                addWorkflowTagValidateBeforeCall(tagObject, name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for deleteTaskTag
     *
     * @param tagString (required)
     * @param taskName (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call deleteTaskTagCall(
            TagString tagString,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagString;

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                apiClient.escapeString(taskName.toString()));

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
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call deleteTaskTagValidateBeforeCall(
            TagString tagString,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagString == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagString' when calling deleteTaskTag(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling deleteTaskTag(Async)");
        }

        okhttp3.Call call =
                deleteTaskTagCall(tagString, taskName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Removes the tag of the task
     *
     * @param tagString (required)
     * @param taskName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteTaskTag(TagString tagString, String taskName) throws ApiException {
        deleteTaskTagWithHttpInfo(tagString, taskName);
    }

    /**
     * Removes the tag of the task
     *
     * @param tagString (required)
     * @param taskName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> deleteTaskTagWithHttpInfo(TagString tagString, String taskName)
            throws ApiException {
        okhttp3.Call call =
                deleteTaskTagValidateBeforeCall(tagString, taskName, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for deleteWorkflowTag
     *
     * @param tagObject (required)
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call deleteWorkflowTagCall(
            TagObject tagObject,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagObject;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

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
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call deleteWorkflowTagValidateBeforeCall(
            TagObject tagObject,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling deleteWorkflowTag(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling deleteWorkflowTag(Async)");
        }

        okhttp3.Call call =
                deleteWorkflowTagCall(tagObject, name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Removes the tag of the workflow
     *
     * @param tagObject (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteWorkflowTag(TagObject tagObject, String name) throws ApiException {
        deleteWorkflowTagWithHttpInfo(tagObject, name);
    }

    /**
     * Removes the tag of the workflow
     *
     * @param tagObject (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> deleteWorkflowTagWithHttpInfo(TagObject tagObject, String name)
            throws ApiException {
        okhttp3.Call call =
                deleteWorkflowTagValidateBeforeCall(tagObject, name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for getTags
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getTagsCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/tags";

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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call getTagsValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        okhttp3.Call call = getTagsCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * List all tags
     *
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<TagObject> getTags() throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsWithHttpInfo();
        return resp.getData();
    }

    /**
     * List all tags
     *
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<TagObject>> getTagsWithHttpInfo() throws ApiException {
        okhttp3.Call call = getTagsValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTaskTags
     *
     * @param taskName (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getTaskTagsCall(
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                apiClient.escapeString(taskName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call getTaskTagsValidateBeforeCall(
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling getTaskTags(Async)");
        }

        okhttp3.Call call =
                getTaskTagsCall(taskName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Returns all the tags of the task
     *
     * @param taskName (required)
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<TagObject> getTaskTags(String taskName) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTaskTagsWithHttpInfo(taskName);
        return resp.getData();
    }

    /**
     * Returns all the tags of the task
     *
     * @param taskName (required)
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<TagObject>> getTaskTagsWithHttpInfo(String taskName)
            throws ApiException {
        okhttp3.Call call = getTaskTagsValidateBeforeCall(taskName, null, null);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getWorkflowTags
     *
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getWorkflowTagsCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
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
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call getWorkflowTagsValidateBeforeCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getWorkflowTags(Async)");
        }

        okhttp3.Call call =
                getWorkflowTagsCall(name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Returns all the tags of the workflow
     *
     * @param name (required)
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<TagObject> getWorkflowTags(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getWorkflowTagsWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Returns all the tags of the workflow
     *
     * @param name (required)
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<TagObject>> getWorkflowTagsWithHttpInfo(String name)
            throws ApiException {
        okhttp3.Call call = getWorkflowTagsValidateBeforeCall(name, null, null);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for setTaskTags
     *
     * @param tagObjects (required)
     * @param taskName (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call setTaskTagsCall(
            List<TagObject> tagObjects,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagObjects;

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                apiClient.escapeString(taskName.toString()));

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

    private okhttp3.Call setTaskTagsValidateBeforeCall(
            List<TagObject> tagObjects,
            String taskName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObjects == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObjects' when calling setTaskTags(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling setTaskTags(Async)");
        }

        okhttp3.Call call =
                setTaskTagsCall(tagObjects, taskName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Adds the tag to the task
     *
     * @param tagObjects (required)
     * @param taskName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void setTaskTags(List<TagObject> tagObjects, String taskName) throws ApiException {
        setTaskTagsWithHttpInfo(tagObjects, taskName);
    }

    /**
     * Adds the tag to the task
     *
     * @param tagObjects (required)
     * @param taskName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> setTaskTagsWithHttpInfo(List<TagObject> tagObjects, String taskName)
            throws ApiException {
        okhttp3.Call call =
                setTaskTagsValidateBeforeCall(tagObjects, taskName, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for setWorkflowTags
     *
     * @param tagObjects (required)
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call setWorkflowTagsCall(
            List<TagObject> tagObjects,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = tagObjects;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

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

    private okhttp3.Call setWorkflowTagsValidateBeforeCall(
            List<TagObject> tagObjects,
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObjects == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObjects' when calling setWorkflowTags(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling setWorkflowTags(Async)");
        }

        okhttp3.Call call =
                setWorkflowTagsCall(tagObjects, name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Set the tags of the workflow
     *
     * @param tagObjects (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void setWorkflowTags(List<TagObject> tagObjects, String name) throws ApiException {
        setWorkflowTagsWithHttpInfo(tagObjects, name);
    }

    /**
     * Set the tags of the workflow
     *
     * @param tagObjects (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> setWorkflowTagsWithHttpInfo(List<TagObject> tagObjects, String name)
            throws ApiException {
        okhttp3.Call call =
                setWorkflowTagsValidateBeforeCall(tagObjects, name, null, null);
        return apiClient.execute(call);
    }
}
