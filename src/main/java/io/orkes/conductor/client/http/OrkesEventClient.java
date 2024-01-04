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
package io.orkes.conductor.client.http;

import java.util.List;
import java.util.Map;

import com.netflix.conductor.common.metadata.events.EventHandler;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.EventClient;
import io.orkes.conductor.client.http.api.EventResourceApi;
import io.orkes.conductor.client.model.event.QueueConfiguration;

public class OrkesEventClient extends EventClient {

    private EventResourceApi eventResourceApi;

    protected ApiClient apiClient;

    public OrkesEventClient(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.eventResourceApi = new EventResourceApi(apiClient);
    }

    public EventClient withReadTimeout(int readTimeout) {
        apiClient.setReadTimeout(readTimeout);
        return this;
    }

    public EventClient setWriteTimeout(int writeTimeout) {
        apiClient.setWriteTimeout(writeTimeout);
        return this;
    }

    public EventClient withConnectTimeout(int connectTimeout) {
        apiClient.setConnectTimeout(connectTimeout);
        return this;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    @Override
    public void registerEventHandler(EventHandler eventHandler) {
        this.eventResourceApi.addEventHandler(eventHandler);
    }

    @Override
    public void updateEventHandler(EventHandler eventHandler) {
        this.eventResourceApi.updateEventHandler(eventHandler);
    }

    @Override
    public List<EventHandler> getEventHandlers(String event, boolean activeOnly) {
        return eventResourceApi.getEventHandlersForEvent(event, activeOnly);
    }

    @Override
    public List<EventHandler> getEventHandlers() {
        return eventResourceApi.getEventHandlers();
    }

    @Override
    public void handleIncomingEvent(Map<String, Object> payload) {
        eventResourceApi.handleIncomingEvent(payload);
    }

    @Override
    public void unregisterEventHandler(String name) {
        eventResourceApi.removeEventHandlerStatus(name);
    }

    @Override
    public Map<String, Object> getQueueConfig(QueueConfiguration queueConfiguration) {
        return eventResourceApi.getQueueConfig(queueConfiguration.getQueueType(), queueConfiguration.getQueueName());
    }

    @Override
    public void deleteQueueConfig(QueueConfiguration queueConfiguration) {
        eventResourceApi.deleteQueueConfig(queueConfiguration.getQueueType(), queueConfiguration.getQueueName());
    }

    @Override
    public void putQueueConfig(QueueConfiguration queueConfiguration) throws Exception {
        eventResourceApi.putQueueConfig(queueConfiguration.getConfiguration(), queueConfiguration.getQueueType(), queueConfiguration.getQueueName());
    }
}
