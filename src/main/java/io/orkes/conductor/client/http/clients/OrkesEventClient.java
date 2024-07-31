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

import io.orkes.conductor.client.api.EventClient;
import io.orkes.conductor.client.model.event.QueueConfiguration;
import io.orkes.conductor.client.model.metadata.events.EventHandler;

public class OrkesEventClient extends OrkesClient implements EventClient {

    private final EventResource eventResource;

    public OrkesEventClient(OrkesHttpClient httpClient) {
        super(httpClient);
        eventResource = new EventResource(httpClient);
    }

    @Override
    public void registerEventHandler(EventHandler eventHandler) {
        eventResource.addEventHandler(eventHandler);
    }

    @Override
    public void updateEventHandler(EventHandler eventHandler) {
        eventResource.updateEventHandler(eventHandler);
    }

    @Override
    public List<EventHandler> getEventHandlers(String event, boolean activeOnly) {
        return eventResource.getEventHandlersForEvent(event, activeOnly);
    }

    @Override
    public List<EventHandler> getEventHandlers() {
        return eventResource.getEventHandlers();
    }

    @Override
    public void handleIncomingEvent(Map<String, Object> payload) {
        eventResource.handleIncomingEvent(payload);
    }

    @Override
    public void unregisterEventHandler(String name) {
        eventResource.removeEventHandlerStatus(name);
    }

    @Override
    public Map<String, Object> getQueueConfig(QueueConfiguration queueConfiguration) {
        return eventResource.getQueueConfig(queueConfiguration.getQueueType(), queueConfiguration.getQueueName());
    }

    @Override
    public void deleteQueueConfig(QueueConfiguration queueConfiguration) {
        eventResource.deleteQueueConfig(queueConfiguration.getQueueType(), queueConfiguration.getQueueName());
    }
}
