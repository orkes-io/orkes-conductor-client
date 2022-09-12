package io.orkes.conductor.client;

import java.util.List;

import com.netflix.conductor.client.http.ClientBase;
import org.apache.commons.lang.StringUtils;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.config.DefaultConductorClientConfiguration;
import com.netflix.conductor.common.metadata.events.EventHandler;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;

// Client class for all Event Handler operations
public class EventClientOSS extends ClientBase implements io.orkes.conductor.client.http.EventClient {
    private static final GenericType<List<EventHandler>> eventHandlerList =
            new GenericType<List<EventHandler>>() {};
    /** Creates a default metadata client */
    public EventClientOSS() {
        this(new DefaultClientConfig(), new DefaultConductorClientConfiguration(), null);
    }

    /**
     * @param clientConfig REST Client configuration
     */
    public EventClientOSS(ClientConfig clientConfig) {
        this(clientConfig, new DefaultConductorClientConfiguration(), null);
    }

    /**
     * @param clientConfig REST Client configuration
     * @param clientHandler Jersey client handler. Useful when plugging in various http client
     *     interaction modules (e.g. ribbon)
     */
    public EventClientOSS(ClientConfig clientConfig, ClientHandler clientHandler) {
        this(clientConfig, new DefaultConductorClientConfiguration(), clientHandler);
    }

    /**
     * @param config config REST Client configuration
     * @param handler handler Jersey client handler. Useful when plugging in various http client
     *     interaction modules (e.g. ribbon)
     * @param filters Chain of client side filters to be applied per request
     */
    public EventClientOSS(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        this(config, new DefaultConductorClientConfiguration(), handler, filters);
    }

    /**
     * @param config REST Client configuration
     * @param clientConfiguration Specific properties configured for the client, see {@link
     *     ConductorClientConfiguration}
     * @param handler Jersey client handler. Useful when plugging in various http client interaction
     *     modules (e.g. ribbon)
     * @param filters Chain of client side filters to be applied per request
     */
    public EventClientOSS(
            ClientConfig config,
            ConductorClientConfiguration clientConfiguration,
            ClientHandler handler,
            ClientFilter... filters) {
        super(config, clientConfiguration, handler);
        for (ClientFilter filter : filters) {
            super.client.addFilter(filter);
        }
    }

    /**
     * Register an event handler with the server
     *
     * @param eventHandler the eventHandler definition
     */
    @Override
    public void registerEventHandler(EventHandler eventHandler) {
        Preconditions.checkNotNull(eventHandler, "Event Handler definition cannot be null");
        postForEntityWithRequestOnly("event", eventHandler);
    }

    /**
     * Updates an event handler with the server
     *
     * @param eventHandler the eventHandler definition
     */
    @Override
    public void updateEventHandler(EventHandler eventHandler) {
        Preconditions.checkNotNull(eventHandler, "Event Handler definition cannot be null");
        put("event", null, eventHandler);
    }

    /**
     * @param event name of the event
     * @param activeOnly if true, returns only the active handlers
     * @return Returns the list of all the event handlers for a given event
     */
    @Override
    public List<EventHandler> getEventHandlers(String event, boolean activeOnly) {
        Preconditions.checkArgument(
                org.apache.commons.lang3.StringUtils.isNotBlank(event), "Event cannot be blank");

        return getForEntity(
                "event/{event}", new Object[] {"activeOnly", activeOnly}, eventHandlerList, event);
    }

    /**
     * Removes the event handler definition from the conductor server
     *
     * @param name the name of the event handler to be unregistered
     */
    @Override
    public void unregisterEventHandler(String name) {
        Preconditions.checkArgument(
                StringUtils.isNotBlank(name), "Event handler name cannot be blank");
        delete("event/{name}", name);
    }
}
