package io.orkes.conductor.client.http;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.http.EventClient;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesEventClient extends EventClient implements OrkesClient {
    public OrkesEventClient() {
    }

    public OrkesEventClient(ClientConfig clientConfig) {
        super(clientConfig);
    }

    public OrkesEventClient(ClientConfig clientConfig, ClientHandler clientHandler) {
        super(clientConfig, clientHandler);
    }

    public OrkesEventClient(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        super(config, handler, filters);
    }

    public OrkesEventClient(ClientConfig config, ConductorClientConfiguration clientConfiguration, ClientHandler handler, ClientFilter... filters) {
        super(config, clientConfiguration, handler, filters);
    }

    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
