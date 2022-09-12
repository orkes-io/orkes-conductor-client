package io.orkes.conductor.client.http;

import com.netflix.conductor.common.metadata.events.EventHandler;

import java.util.List;

public interface EventClient {
    void registerEventHandler(EventHandler eventHandler);

    void updateEventHandler(EventHandler eventHandler);

    List<EventHandler> getEventHandlers(String event, boolean activeOnly);

    void unregisterEventHandler(String name);
}
