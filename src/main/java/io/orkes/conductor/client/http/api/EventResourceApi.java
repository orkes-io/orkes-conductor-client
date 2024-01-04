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

import com.netflix.conductor.common.metadata.events.EventHandler;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Configuration;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.http.ProgressRequestBody;
import io.orkes.conductor.client.http.ProgressResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;

public class EventResourceApi {
    private ApiClient apiClient;

    public EventResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public EventResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for addEventHandler
     *
     * @param eventHandler            (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call addEventHandlerCall(
            EventHandler eventHandler,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = eventHandler;

        // create path and map variables
        String localVarPath = "/event";

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call addEventHandlerValidateBeforeCall(
            EventHandler eventHandler,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling addEventHandler(Async)");
        }

        com.squareup.okhttp.Call call =
                addEventHandlerCall(eventHandler, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Add a new event handler.
     *
     * @param eventHandler (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void addEventHandler(EventHandler eventHandler) throws ApiException {
        addEventHandlerWithHttpInfo(eventHandler);
    }

    /**
     * Add a new event handler.
     *
     * @param eventHandler (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Void> addEventHandlerWithHttpInfo(EventHandler eventHandler)
            throws ApiException {
        com.squareup.okhttp.Call call = addEventHandlerValidateBeforeCall(eventHandler, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for deleteQueueConfig
     *
     * @param queueType               (required)
     * @param queueName               (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call deleteQueueConfigCall(
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                apiClient.escapeString(queueType.toString()))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                apiClient.escapeString(queueName.toString()));

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call deleteQueueConfigValidateBeforeCall(
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'queueType' is set
        if (queueType == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueType' when calling deleteQueueConfig(Async)");
        }
        // verify the required parameter 'queueName' is set
        if (queueName == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueName' when calling deleteQueueConfig(Async)");
        }

        com.squareup.okhttp.Call call =
                deleteQueueConfigCall(
                        queueType, queueName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Delete queue config by name
     *
     * @param queueType (required)
     * @param queueName (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public Object deleteQueueConfig(String queueType, String queueName) throws ApiException {
        ApiResponse<Object> resp = deleteQueueConfigWithHttpInfo(queueType, queueName);
        return resp.getData();
    }

    /**
     * Delete queue config by name
     *
     * @param queueType (required)
     * @param queueName (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> deleteQueueConfigWithHttpInfo(String queueType, String queueName)
            throws ApiException {
        com.squareup.okhttp.Call call =
                deleteQueueConfigValidateBeforeCall(queueType, queueName, null, null);
        Type localVarReturnType = new TypeReference<Object>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getEventHandlers
     *
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getEventHandlersCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event";

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call getEventHandlersValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                getEventHandlersCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all the event handlers
     *
     * @return List&lt;EventHandler&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public List<EventHandler> getEventHandlers() throws ApiException {
        ApiResponse<List<EventHandler>> resp = getEventHandlersWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get all the event handlers
     *
     * @return ApiResponse&lt;List&lt;EventHandler&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<List<EventHandler>> getEventHandlersWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = getEventHandlersValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getEventHandlersForEvent
     *
     * @param event                   (required)
     * @param activeOnly              (optional, default to true)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getEventHandlersForEventCall(
            String event,
            Boolean activeOnly,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{event}"
                        .replaceAll(
                                "\\{" + "event" + "\\}", apiClient.escapeString(event.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (activeOnly != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("activeOnly", activeOnly));

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call getEventHandlersForEventValidateBeforeCall(
            String event,
            Boolean activeOnly,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'event' is set
        if (event == null) {
            throw new ApiException(
                    "Missing the required parameter 'event' when calling getEventHandlersForEvent(Async)");
        }

        com.squareup.okhttp.Call call =
                getEventHandlersForEventCall(
                        event, activeOnly, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get event handlers for a given event
     *
     * @param event      (required)
     * @param activeOnly (optional, default to true)
     * @return List&lt;EventHandler&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public List<EventHandler> getEventHandlersForEvent(String event, Boolean activeOnly)
            throws ApiException {
        ApiResponse<List<EventHandler>> resp =
                getEventHandlersForEventWithHttpInfo(event, activeOnly);
        return resp.getData();
    }

    /**
     * Get event handlers for a given event
     *
     * @param event      (required)
     * @param activeOnly (optional, default to true)
     * @return ApiResponse&lt;List&lt;EventHandler&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<List<EventHandler>> getEventHandlersForEventWithHttpInfo(
            String event, Boolean activeOnly) throws ApiException {
        com.squareup.okhttp.Call call =
                getEventHandlersForEventValidateBeforeCall(event, activeOnly, null, null);
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getQueueConfig
     *
     * @param queueType               (required)
     * @param queueName               (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getQueueConfigCall(
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                apiClient.escapeString(queueType.toString()))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                apiClient.escapeString(queueName.toString()));

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call getQueueConfigValidateBeforeCall(
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'queueType' is set
        if (queueType == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueType' when calling getQueueConfig(Async)");
        }
        // verify the required parameter 'queueName' is set
        if (queueName == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueName' when calling getQueueConfig(Async)");
        }

        com.squareup.okhttp.Call call =
                getQueueConfigCall(queueType, queueName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get queue config by name
     *
     * @param queueType (required)
     * @param queueName (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public Map<String, Object> getQueueConfig(String queueType, String queueName) throws ApiException {
        ApiResponse<Map<String, Object>> resp = getQueueConfigWithHttpInfo(queueType, queueName);
        return resp.getData();
    }

    /**
     * Get queue config by name
     *
     * @param queueType (required)
     * @param queueName (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Map<String, Object>> getQueueConfigWithHttpInfo(String queueType, String queueName)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getQueueConfigValidateBeforeCall(queueType, queueName, null, null);
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getQueueNames
     *
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getQueueNamesCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event/queue/config";

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call getQueueNamesValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                getQueueNamesCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all queue configs
     *
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public Object getQueueNames() throws ApiException {
        ApiResponse<Object> resp = getQueueNamesWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get all queue configs
     *
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> getQueueNamesWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = getQueueNamesValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<Object>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for putQueueConfig
     *
     * @param body                    (required)
     * @param queueType               (required)
     * @param queueName               (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call putQueueConfigCall(
            String body,
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                apiClient.escapeString(queueType.toString()))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                apiClient.escapeString(queueName.toString()));

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call putQueueConfigValidateBeforeCall(
            String body,
            String queueType,
            String queueName,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling putQueueConfig(Async)");
        }
        // verify the required parameter 'queueType' is set
        if (queueType == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueType' when calling putQueueConfig(Async)");
        }
        // verify the required parameter 'queueName' is set
        if (queueName == null) {
            throw new ApiException(
                    "Missing the required parameter 'queueName' when calling putQueueConfig(Async)");
        }

        com.squareup.okhttp.Call call =
                putQueueConfigCall(
                        body, queueType, queueName, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create or update queue config by name
     *
     * @param body      (required)
     * @param queueType (required)
     * @param queueName (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public Object putQueueConfig(String body, String queueType, String queueName)
            throws ApiException {
        ApiResponse<Object> resp = putQueueConfigWithHttpInfo(body, queueType, queueName);
        return resp.getData();
    }

    /**
     * Create or update queue config by name
     *
     * @param body      (required)
     * @param queueType (required)
     * @param queueName (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> putQueueConfigWithHttpInfo(
            String body, String queueType, String queueName) throws ApiException {
        com.squareup.okhttp.Call call =
                putQueueConfigValidateBeforeCall(body, queueType, queueName, null, null);
        Type localVarReturnType = new TypeReference<Object>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for removeEventHandlerStatus
     *
     * @param name                    (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call removeEventHandlerStatusCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", apiClient.escapeString(name.toString()));

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call removeEventHandlerStatusValidateBeforeCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling removeEventHandlerStatus(Async)");
        }

        com.squareup.okhttp.Call call =
                removeEventHandlerStatusCall(name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Remove an event handler
     *
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void removeEventHandlerStatus(String name) throws ApiException {
        removeEventHandlerStatusWithHttpInfo(name);
    }

    /**
     * Remove an event handler
     *
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Void> removeEventHandlerStatusWithHttpInfo(String name)
            throws ApiException {
        com.squareup.okhttp.Call call =
                removeEventHandlerStatusValidateBeforeCall(name, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for updateEventHandler
     *
     * @param eventHandler            (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call updateEventHandlerCall(
            EventHandler eventHandler,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = eventHandler;

        // create path and map variables
        String localVarPath = "/event";

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

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private com.squareup.okhttp.Call updateEventHandlerValidateBeforeCall(
            EventHandler eventHandler,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling updateEventHandler(Async)");
        }

        com.squareup.okhttp.Call call =
                updateEventHandlerCall(eventHandler, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Update an existing event handler.
     *
     * @param eventHandler (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void updateEventHandler(EventHandler eventHandler) throws ApiException {
        updateEventHandlerWithHttpInfo(eventHandler);
    }


    public void handleIncomingEvent(Map<String, Object> payload) {
        handleIncomingEventWithHttpInfo(payload);
    }


    /**
     * Update an existing event handler.
     *
     * @param eventHandler (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Void> updateEventHandlerWithHttpInfo(EventHandler eventHandler)
            throws ApiException {
        com.squareup.okhttp.Call call =
                updateEventHandlerValidateBeforeCall(eventHandler, null, null);
        return apiClient.execute(call);
    }

    private ApiResponse<Void> handleIncomingEventWithHttpInfo(Map<String, Object> payload)
            throws ApiException {
        com.squareup.okhttp.Call call = handleIncomingEventValidateBeforeCall(payload, null, null);
        return apiClient.execute(call);
    }

    private com.squareup.okhttp.Call handleIncomingEventValidateBeforeCall(
            Map<String, Object> payload,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = payload;

        // create path and map variables
        String localVarPath = "/event/handleIncomingEvent";

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


}
