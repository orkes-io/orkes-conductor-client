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
package io.orkes.conductor.client.api;


import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.model.event.QueueConfiguration;
import io.orkes.conductor.client.model.metadata.events.EventHandler;

public interface EventClient {
    void unregisterEventHandler(String name);

    Map<String, Object> getQueueConfig(QueueConfiguration queueConfiguration);

    void deleteQueueConfig(QueueConfiguration queueConfiguration);

    void registerEventHandler(EventHandler eventHandler);

    void updateEventHandler(EventHandler eventHandler);

    List<EventHandler> getEventHandlers(String event, boolean activeOnly);

    List<EventHandler> getEventHandlers();

    void handleIncomingEvent(Map<String, Object> payload);

}
