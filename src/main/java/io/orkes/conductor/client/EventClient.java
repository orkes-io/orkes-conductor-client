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
package io.orkes.conductor.client;



import java.util.List;
import java.util.Map;

import com.netflix.conductor.common.metadata.events.EventHandler;

import io.orkes.conductor.client.model.event.QueueConfiguration;

public abstract class EventClient extends com.netflix.conductor.client.http.EventClient {
    public abstract Map<String, Object> getQueueConfig(QueueConfiguration queueConfiguration);

    public abstract void deleteQueueConfig(QueueConfiguration queueConfiguration);

    public abstract void putQueueConfig(QueueConfiguration queueConfiguration) throws Exception;

    public abstract List<EventHandler> getEventHandlers();

    public abstract void handleIncomingEvent(Map<String, Object> payload);

}
