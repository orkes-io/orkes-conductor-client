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

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.metadata.events.EventHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Call;

class EventResource {
    private final OrkesHttpClient httpClient;

    public EventResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private Call addEventHandlerCall(EventHandler eventHandler) throws ApiException {
        // create path and map variables
        String localVarPath = "/event";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call addEventHandlerValidateBeforeCall(EventHandler eventHandler) throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling addEventHandler(Async)");
        }

        return addEventHandlerCall(eventHandler);
    }

  
    public void addEventHandler(EventHandler eventHandler) throws ApiException {
        Call call = addEventHandlerValidateBeforeCall(eventHandler);
        httpClient.execute(call);
    }

  
    public Call deleteQueueConfigCall(
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

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call deleteQueueConfigValidateBeforeCall(
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

  
    public Object deleteQueueConfig(String queueType, String queueName) throws ApiException {
        ApiResponse<Object> resp = deleteQueueConfigWithHttpInfo(queueType, queueName);
        return resp.getData();
    }

  
    private ApiResponse<Object> deleteQueueConfigWithHttpInfo(String queueType, String queueName)
            throws ApiException {
        Call call =
                deleteQueueConfigValidateBeforeCall(queueType, queueName);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getEventHandlersCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call getEventHandlersValidateBeforeCall()
            throws ApiException {
        return getEventHandlersCall();
    }

  
    public List<EventHandler> getEventHandlers() throws ApiException {
        ApiResponse<List<EventHandler>> resp = getEventHandlersWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<List<EventHandler>> getEventHandlersWithHttpInfo() throws ApiException {
        Call call = getEventHandlersValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getEventHandlersForEventCall(
            String event,
            Boolean activeOnly)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{event}"
                        .replaceAll(
                                "\\{" + "event" + "\\}", httpClient.escapeString(event));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
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

    private Call getEventHandlersForEventValidateBeforeCall(
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

  
    public List<EventHandler> getEventHandlersForEvent(String event, Boolean activeOnly)
            throws ApiException {
        ApiResponse<List<EventHandler>> resp =
                getEventHandlersForEventWithHttpInfo(event, activeOnly);
        return resp.getData();
    }

  
    private ApiResponse<List<EventHandler>> getEventHandlersForEventWithHttpInfo(
            String event, Boolean activeOnly) throws ApiException {
        Call call =
                getEventHandlersForEventValidateBeforeCall(event, activeOnly);
        Type localVarReturnType = new TypeReference<List<EventHandler>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getQueueConfigCall(
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

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call getQueueConfigValidateBeforeCall(
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

  
    public Map<String, Object> getQueueConfig(String queueType, String queueName) throws ApiException {
        ApiResponse<Map<String, Object>> resp = getQueueConfigWithHttpInfo(queueType, queueName);
        return resp.getData();
    }

  
    private ApiResponse<Map<String, Object>> getQueueConfigWithHttpInfo(String queueType, String queueName)
            throws ApiException {
        Call call =
                getQueueConfigValidateBeforeCall(queueType, queueName);
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getQueueNamesCall() throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/event/queue/config";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call getQueueNamesValidateBeforeCall()
            throws ApiException {
        return getQueueNamesCall();
    }

  
    public Object getQueueNames() throws ApiException {
        ApiResponse<Object> resp = getQueueNamesWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<Object> getQueueNamesWithHttpInfo() throws ApiException {
        Call call = getQueueNamesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }
    public Call putQueueConfigCall(
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

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);

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

    private Call putQueueConfigValidateBeforeCall(
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

        return putQueueConfigCall(body, queueType, queueName);
    }

  
    public Object putQueueConfig(String body, String queueType, String queueName)
            throws ApiException {
        ApiResponse<Object> resp = putQueueConfigWithHttpInfo(body, queueType, queueName);
        return resp.getData();
    }
  
    private ApiResponse<Object> putQueueConfigWithHttpInfo(String body, String queueType, String queueName) throws ApiException {
        Call call =
                putQueueConfigValidateBeforeCall(body, queueType, queueName);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call removeEventHandlerStatusCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/event/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call removeEventHandlerStatusValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling removeEventHandlerStatus(Async)");
        }

        return removeEventHandlerStatusCall(name);
    }

  
    public void removeEventHandlerStatus(String name) throws ApiException {
        removeEventHandlerStatusWithHttpInfo(name);
    }

  
    private ApiResponse<Void> removeEventHandlerStatusWithHttpInfo(String name)
            throws ApiException {
        Call call =
                removeEventHandlerStatusValidateBeforeCall(name);
        return httpClient.execute(call);
    }

  
    public Call updateEventHandlerCall(
            EventHandler eventHandler)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/event";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

    private Call updateEventHandlerValidateBeforeCall(
            EventHandler eventHandler)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (eventHandler == null) {
            throw new ApiException(
                    "Missing the required parameter 'eventHandler' when calling updateEventHandler(Async)");
        }

        return updateEventHandlerCall(eventHandler);
    }

  
    public void updateEventHandler(EventHandler eventHandler) throws ApiException {
        updateEventHandlerWithHttpInfo(eventHandler);
    }


    public void handleIncomingEvent(Map<String, Object> payload) {
        handleIncomingEventWithHttpInfo(payload);
    }


  
    private ApiResponse<Void> updateEventHandlerWithHttpInfo(EventHandler eventHandler)
            throws ApiException {
        Call call =
                updateEventHandlerValidateBeforeCall(eventHandler);
        return httpClient.execute(call);
    }

    private ApiResponse<Void> handleIncomingEventWithHttpInfo(Map<String, Object> payload)
            throws ApiException {
        Call call = handleIncomingEventValidateBeforeCall(payload);
        return httpClient.execute(call);
    }

    private Call handleIncomingEventValidateBeforeCall(
            Map<String, Object> payload) throws ApiException {

        // create path and map variables
        String localVarPath = "/event/handleIncomingEvent";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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
