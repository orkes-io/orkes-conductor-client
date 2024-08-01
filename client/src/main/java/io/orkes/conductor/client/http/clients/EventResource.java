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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.metadata.events.EventHandler;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.PUT;

class EventResource extends Resource {

    EventResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    void addEventHandler(EventHandler body) {
        Objects.requireNonNull(body, "EventHandler cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/event")
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    void updateEventHandler(EventHandler body) {
        Objects.requireNonNull(body, "EventHandler cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(PUT)
                .path("/event")
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    List<EventHandler> getEventHandlersForEvent(String event, Boolean activeOnly) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/event/{event}")
                .addPathParam("event", event)
                .addQueryParam("activeOnly", activeOnly)
                .build();

        ApiResponse<List<EventHandler>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    List<EventHandler> getEventHandlers() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/event")
                .build();

        ApiResponse<List<EventHandler>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    void handleIncomingEvent(Map<String, Object> body) {
        Objects.requireNonNull(body, "EventHandler cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/event/handleIncomingEvent")
                .body(body)
                .build();
        httpClient.doRequest(request);
    }

    void removeEventHandlerStatus(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/event/{name}")
                .addPathParam("name", name)
                .build();
        httpClient.doRequest(request);
    }

    Map<String, Object> getQueueConfig(String queueType, String queueName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path( "/event/queue/config/{queueType}/{queueName}")
                .addPathParam("queueType", queueType)
                .addPathParam("queueName", queueName)
                .build();
        ApiResponse<Map<String, Object>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    Object deleteQueueConfig(String queueType, String queueName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path( "/event/queue/config/{queueType}/{queueName}")
                .addPathParam("queueType", queueType)
                .addPathParam("queueName", queueName)
                .build();

        ApiResponse<Object> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
