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
package com.netflix.conductor.client.http;

import java.util.List;

import com.netflix.conductor.common.metadata.events.EventHandler;

// Client class for all Event Handler operations
public abstract class EventClient{

    /** Creates a default metadata client */
    public EventClient() {
    }



    /**
     * Register an event handler with the server.
     *
     * @param eventHandler the eventHandler definition.
     */
    public abstract void registerEventHandler(EventHandler eventHandler);

    /**
     * Updates an event handler with the server.
     *
     * @param eventHandler the eventHandler definition.
     */
    public abstract void updateEventHandler(EventHandler eventHandler);

    /**
     * @param event name of the event.
     * @param activeOnly if true, returns only the active handlers.
     * @return Returns the list of all the event handlers for a given event.
     */
    public abstract List<EventHandler> getEventHandlers(String event, boolean activeOnly);

    /**
     * Removes the event handler definition from the conductor server
     *
     * @param name the name of the event handler to be unregistered
     */
    public abstract void unregisterEventHandler(String name);
}
