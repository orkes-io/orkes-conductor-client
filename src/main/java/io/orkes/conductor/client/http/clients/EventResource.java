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
import io.orkes.conductor.client.model.metadata.events.EventHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventResource {
    private final OrkesHttpClient httpClient;

    public EventResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private okhttp3.Call addEventHandlerCall(EventHandler eventHandler) throws ApiException {
        // create path and map variables
        String localVarPath = "/event";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                eventHandler,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call addEventHandlerValidateBeforeCall(EventHandler eventHandler) throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling addEventHandler(Async)");
        }

        return addEventHandlerCall(eventHandler);
    }

    /**
     * Add a new event handler.
     *
     * @param eventHandler (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void addEventHandler(EventHandler eventHandler) throws ApiException {
        okhttp3.Call call = addEventHandlerValidateBeforeCall(eventHandler);
        httpClient.execute(call);
    }

    /**
     * Build call for deleteQueueConfig
     *
     * @param queueType               (required)
     * @param queueName               (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call deleteQueueConfigCall(
            String queueType,
            String queueName)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                httpClient.escapeString(queueType))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                httpClient.escapeString(queueName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call deleteQueueConfigValidateBeforeCall(
            String queueType,
            String queueName)
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

        return deleteQueueConfigCall(
                queueType, queueName);
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
        okhttp3.Call call =
                deleteQueueConfigValidateBeforeCall(queueType, queueName);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getEventHandlers
     *

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getEventHandlersCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call getEventHandlersValidateBeforeCall()
            throws ApiException {
        return getEventHandlersCall();
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
        okhttp3.Call call = getEventHandlersValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getEventHandlersForEvent
     *
     * @param event                   (required)
     * @param activeOnly              (optional, default to true)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getEventHandlersForEventCall(
            String event,
            Boolean activeOnly)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{event}"
                        .replaceAll(
                                "\\{" + "event" + "\\}", httpClient.escapeString(event));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (activeOnly != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("activeOnly", activeOnly));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call getEventHandlersForEventValidateBeforeCall(
            String event,
            Boolean activeOnly)
            throws ApiException {
        // verify the required parameter 'event' is set
        if (event == null) {
            throw new ApiException(
                    "Missing the required parameter 'event' when calling getEventHandlersForEvent(Async)");
        }

        return getEventHandlersForEventCall(
                event, activeOnly);
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
        okhttp3.Call call =
                getEventHandlersForEventValidateBeforeCall(event, activeOnly);
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getQueueConfig
     *
     * @param queueType               (required)
     * @param queueName               (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getQueueConfigCall(
            String queueType,
            String queueName)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                httpClient.escapeString(queueType))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                httpClient.escapeString(queueName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call getQueueConfigValidateBeforeCall(
            String queueType,
            String queueName)
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

        return getQueueConfigCall(queueType, queueName);
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
        okhttp3.Call call =
                getQueueConfigValidateBeforeCall(queueType, queueName);
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getQueueNames
     *

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getQueueNamesCall() throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event/queue/config";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call getQueueNamesValidateBeforeCall()
            throws ApiException {
        return getQueueNamesCall();
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
        okhttp3.Call call = getQueueNamesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for putQueueConfig
     *
     * @param body                    (required)
     * @param queueType               (required)
     * @param queueName               (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call putQueueConfigCall(
            String body,
            String queueType,
            String queueName)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/event/queue/config/{queueType}/{queueName}"
                        .replaceAll(
                                "\\{" + "queueType" + "\\}",
                                httpClient.escapeString(queueType))
                        .replaceAll(
                                "\\{" + "queueName" + "\\}",
                                httpClient.escapeString(queueName));

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



        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call putQueueConfigValidateBeforeCall(
            String body,
            String queueType,
            String queueName)
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

        return putQueueConfigCall(
                body, queueType, queueName);
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
        okhttp3.Call call =
                putQueueConfigValidateBeforeCall(body, queueType, queueName);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for removeEventHandlerStatus
     *
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call removeEventHandlerStatusCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call removeEventHandlerStatusValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling removeEventHandlerStatus(Async)");
        }

        return removeEventHandlerStatusCall(name);
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
        okhttp3.Call call =
                removeEventHandlerStatusValidateBeforeCall(name);
        return httpClient.execute(call);
    }

    /**
     * Build call for updateEventHandler
     *
     * @param eventHandler            (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call updateEventHandlerCall(
            EventHandler eventHandler)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/event";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                eventHandler,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call updateEventHandlerValidateBeforeCall(
            EventHandler eventHandler)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling updateEventHandler(Async)");
        }

        return updateEventHandlerCall(eventHandler);
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
        okhttp3.Call call =
                updateEventHandlerValidateBeforeCall(eventHandler);
        return httpClient.execute(call);
    }

    private ApiResponse<Void> handleIncomingEventWithHttpInfo(Map<String, Object> payload)
            throws ApiException {
        okhttp3.Call call = handleIncomingEventValidateBeforeCall(payload);
        return httpClient.execute(call);
    }

    private okhttp3.Call handleIncomingEventValidateBeforeCall(
            Map<String, Object> payload) throws ApiException {

        // create path and map variables
        String localVarPath = "/event/handleIncomingEvent";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

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
                payload,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }
}
