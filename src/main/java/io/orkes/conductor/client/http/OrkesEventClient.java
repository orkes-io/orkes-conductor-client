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

import com.netflix.conductor.common.metadata.events.EventHandler;

import io.orkes.conductor.client.http.api.EventResourceApi;

public class OrkesEventClient extends OrkesClient implements EventClient {

    private EventResourceApi eventResourceApi;

    public OrkesEventClient(ApiClient apiClient) {
        super(apiClient);
        this.eventResourceApi = new EventResourceApi(apiClient);
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
    public void unregisterEventHandler(String name) {
        eventResourceApi.removeEventHandlerStatus(name);
    }
}
