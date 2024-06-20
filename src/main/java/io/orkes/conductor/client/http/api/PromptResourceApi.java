/*
 * Copyright 2024 Orkes, Inc.
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
import io.orkes.conductor.client.http.ApiCallback;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.http.ProgressRequestBody;
import io.orkes.conductor.client.http.ProgressResponseBody;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.PromptTemplateTestRequest;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.squareup.okhttp.Call;

public class PromptResourceApi {

    private ApiClient apiClient;

    public PromptResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for deletePromptTemplate
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deletePromptTemplateCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call deletePromptTemplateValidateBeforeCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deletePromptTemplate(Async)");
        }

        Call call = deletePromptTemplateCall(name, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Delete Template
     *
     * @param name  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deletePromptTemplate(String name) throws ApiException {
        deletePromptTemplateWithHttpInfo(name);
    }

    /**
     * Delete Template
     *
     * @param name  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deletePromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = deletePromptTemplateValidateBeforeCall(name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Delete Template (asynchronously)
     *
     * @param name  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call deletePromptTemplateAsync(String name, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = deletePromptTemplateValidateBeforeCall(name, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    /**
     * Build call for deleteTagForPromptTemplate
     * @param body  (required)
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteTagForPromptTemplateCall(List<TagObject> body, String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call deleteTagForPromptTemplateValidateBeforeCall(List<TagObject> body, String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForPromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteTagForPromptTemplate(Async)");
        }

        Call call = deleteTagForPromptTemplateCall(body, name, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Delete a tag for Prompt Template
     *
     * @param body  (required)
     * @param name  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForPromptTemplate(List<TagObject> body, String name) throws ApiException {
        deleteTagForPromptTemplateWithHttpInfo(body, name);
    }

    /**
     * Delete a tag for Prompt Template
     *
     * @param body  (required)
     * @param name  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deleteTagForPromptTemplateWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = deleteTagForPromptTemplateValidateBeforeCall(body, name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Delete a tag for Prompt Template (asynchronously)
     *
     * @param body  (required)
     * @param name  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call deleteTagForPromptTemplateAsync(List<TagObject> body, String name, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = deleteTagForPromptTemplateValidateBeforeCall(body, name, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    /**
     * Build call for getPromptTemplate
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getPromptTemplateCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call getPromptTemplateValidateBeforeCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getPromptTemplate(Async)");
        }

        Call call = getPromptTemplateCall(name, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get Template
     *
     * @param name  (required)
     * @return PromptTemplate
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public PromptTemplate getPromptTemplate(String name) throws ApiException {
        ApiResponse<PromptTemplate> resp = getPromptTemplateWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get Template
     *
     * @param name  (required)
     * @return ApiResponse&lt;PromptTemplate&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<PromptTemplate> getPromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = getPromptTemplateValidateBeforeCall(name, null, null);
        Type localVarReturnType = new TypeReference<PromptTemplate>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get Template (asynchronously)
     *
     * @param name  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call getPromptTemplateAsync(String name, final ApiCallback<PromptTemplate> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = getPromptTemplateValidateBeforeCall(name, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeReference<PromptTemplate>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for getPromptTemplates
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getPromptTemplatesCall(final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call getPromptTemplatesValidateBeforeCall(final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        Call call = getPromptTemplatesCall(progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get Templates
     *
     * @return List&lt;PromptTemplate&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<PromptTemplate> getPromptTemplates() throws ApiException {
        ApiResponse<List<PromptTemplate>> resp = getPromptTemplatesWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get Templates
     *
     * @return ApiResponse&lt;List&lt;PromptTemplate&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<PromptTemplate>> getPromptTemplatesWithHttpInfo() throws ApiException {
        Call call = getPromptTemplatesValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<PromptTemplate>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get Templates (asynchronously)
     *
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call getPromptTemplatesAsync(final ApiCallback<List<PromptTemplate>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = getPromptTemplatesValidateBeforeCall(progressListener, progressRequestListener);
        Type localVarReturnType = new TypeReference<List<PromptTemplate>>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for getTagsForPromptTemplate
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getTagsForPromptTemplateCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call getTagsForPromptTemplateValidateBeforeCall(String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTagsForPromptTemplate(Async)");
        }

        Call call = getTagsForPromptTemplateCall(name, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get tags by Prompt Template
     *
     * @param name  (required)
     * @return List&lt;Tag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTagsForPromptTemplate(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForPromptTemplateWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get tags by Prompt Template
     *
     * @param name  (required)
     * @return ApiResponse&lt;List&lt;Tag&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<TagObject>> getTagsForPromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = getTagsForPromptTemplateValidateBeforeCall(name, null, null);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get tags by Prompt Template (asynchronously)
     *
     * @param name  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call getTagsForPromptTemplateAsync(String name, final ApiCallback<List<TagObject>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = getTagsForPromptTemplateValidateBeforeCall(name, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for putTagForPromptTemplate
     * @param body  (required)
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call putTagForPromptTemplateCall(List<TagObject> body, String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call putTagForPromptTemplateValidateBeforeCall(List<TagObject> body, String name, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForPromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling putTagForPromptTemplate(Async)");
        }

        Call call = putTagForPromptTemplateCall(body, name, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Put a tag to Prompt Template
     *
     * @param body  (required)
     * @param name  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForPromptTemplate(List<TagObject> body, String name) throws ApiException {
        putTagForPromptTemplateWithHttpInfo(body, name);
    }

    /**
     * Put a tag to Prompt Template
     *
     * @param body  (required)
     * @param name  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> putTagForPromptTemplateWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = putTagForPromptTemplateValidateBeforeCall(body, name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Put a tag to Prompt Template (asynchronously)
     *
     * @param body  (required)
     * @param name  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call putTagForPromptTemplateAsync(List<TagObject> body, String name, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = putTagForPromptTemplateValidateBeforeCall(body, name, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    /**
     * Build call for savePromptTemplate
     * @param body  (required)
     * @param description  (required)
     * @param name  (required)
     * @param models  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call savePromptTemplateCall(String body, String description, String name, List<String> models,
        final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener)
        throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (description != null) {
            localVarQueryParams.addAll(apiClient.parameterToPair("description", description));
        }
        if (models != null) {
            localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "models", models));
        }

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private Call savePromptTemplateValidateBeforeCall(String body, String description, String name, List<String> models,
        final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener)
        throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling savePromptTemplate(Async)");
        }
        // verify the required parameter 'description' is set
        if (description == null) {
            throw new ApiException("Missing the required parameter 'description' when calling savePromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling savePromptTemplate(Async)");
        }

        Call call = savePromptTemplateCall(body, description, name, models, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Create or Update Template
     *
     * @param body  (required)
     * @param description  (required)
     * @param name  (required)
     * @param models  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void savePromptTemplate(String body, String description, String name, List<String> models) throws ApiException {
        savePromptTemplateWithHttpInfo(body, description, name, models);
    }

    /**
     * Create or Update Template
     *
     * @param body  (required)
     * @param description  (required)
     * @param name  (required)
     * @param models  (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> savePromptTemplateWithHttpInfo(String body, String description, String name, List<String> models) throws ApiException {
        Call call = savePromptTemplateValidateBeforeCall(body, description, name, models, null, null);
        return apiClient.execute(call);
    }

    /**
     * Create or Update Template (asynchronously)
     *
     * @param body  (required)
     * @param description  (required)
     * @param name  (required)
     * @param models  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call savePromptTemplateAsync(String body, String description, String name, List<String> models, final ApiCallback<Void> callback)
        throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = savePromptTemplateValidateBeforeCall(body, description, name, models, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    @SuppressWarnings("rawtypes")
    private Call testMessageTemplateValidateBeforeCall(PromptTemplateTestRequest body, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling testMessageTemplate(Async)");
        }

        Call call = testMessageTemplateCall(body, progressListener, progressRequestListener);
        return call;

    }

    public Call testMessageTemplateCall(PromptTemplateTestRequest body, final ProgressResponseBody.ProgressListener progressListener,
        final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/prompts/test";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                .networkInterceptors()
                .add(new com.squareup.okhttp.Interceptor() {
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
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    /**
     * Test Prompt Template
     *
     * @param body  (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String testMessageTemplate(PromptTemplateTestRequest body) throws ApiException {
        ApiResponse<String> resp = testMessageTemplateWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Test Prompt Template
     *
     * @param body  (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<String> testMessageTemplateWithHttpInfo(PromptTemplateTestRequest body) throws ApiException {
        Call call = testMessageTemplateValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

}
