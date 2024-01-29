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

import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.events.EventHandler;
import com.netflix.conductor.common.metadata.events.EventHandler.Action;
import com.netflix.conductor.common.metadata.events.EventHandler.StartWorkflow;

import io.orkes.conductor.client.EventClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.util.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class EventClientTests extends ClientTest {
    private static final String EVENT_NAME = "test_sdk_java_event_name";
    private static final String EVENT = "test_sdk_java_event";

    private static final String KAFKA_QUEUE_TOPIC_NAME = "test_sdk_java_kafka_queue_name";
    private static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";

    private final EventClient eventClient;

    public EventClientTests() {
        eventClient = super.orkesClients.getEventClient();
    }

    @Test
    void testEventHandler() {
        try {
            eventClient.unregisterEventHandler(EVENT_NAME);
        } catch (ApiException e) {
            if (e.getStatusCode() != 404) {
                throw e;
            }
        }
        EventHandler eventHandler = getEventHandler();
        eventClient.registerEventHandler(eventHandler);
        eventClient.updateEventHandler(eventHandler);
        List<EventHandler> events = eventClient.getEventHandlers(EVENT, false);
        assertEquals(1, events.size());
        events.forEach(
                event -> {
                    assertEquals(eventHandler.getName(), event.getName());
                    assertEquals(eventHandler.getEvent(), event.getEvent());
                });
        eventClient.unregisterEventHandler(EVENT_NAME);
        assertIterableEquals(List.of(), eventClient.getEventHandlers(EVENT, false));
    }





    EventHandler getEventHandler() {
        EventHandler eventHandler = new EventHandler();
        eventHandler.setName(EVENT_NAME);
        eventHandler.setEvent(EVENT);
        eventHandler.setActions(List.of(getEventHandlerAction()));
        return eventHandler;
    }

    Action getEventHandlerAction() {
        Action action = new Action();
        action.setAction(Action.Type.start_workflow);
        action.setStart_workflow(getStartWorkflowAction());
        return action;
    }

    StartWorkflow getStartWorkflowAction() {
        StartWorkflow startWorkflow = new StartWorkflow();
        startWorkflow.setName(Commons.WORKFLOW_NAME);
        startWorkflow.setVersion(Commons.WORKFLOW_VERSION);
        return startWorkflow;
    }
}
