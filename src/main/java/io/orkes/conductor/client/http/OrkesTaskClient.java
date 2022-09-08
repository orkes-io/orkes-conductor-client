package io.orkes.conductor.client.http;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.http.TaskClient;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesTaskClient extends TaskClient implements OrkesClient {
    public OrkesTaskClient() {
    }

    public OrkesTaskClient(ClientConfig config) {
        super(config);
    }

    public OrkesTaskClient(ClientConfig config, ClientHandler handler) {
        super(config, handler);
    }

    public OrkesTaskClient(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        super(config, handler, filters);
    }

    public OrkesTaskClient(ClientConfig config, ConductorClientConfiguration clientConfiguration, ClientHandler handler, ClientFilter... filters) {
        super(config, clientConfiguration, handler, filters);
    }

    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
