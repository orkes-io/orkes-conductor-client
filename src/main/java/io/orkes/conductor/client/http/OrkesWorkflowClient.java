package io.orkes.conductor.client.http;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.http.WorkflowClient;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesWorkflowClient extends WorkflowClient implements OrkesClient {
    public OrkesWorkflowClient() {
    }

    public OrkesWorkflowClient(ClientConfig config) {
        super(config);
    }

    public OrkesWorkflowClient(ClientConfig config, ClientHandler handler) {
        super(config, handler);
    }

    public OrkesWorkflowClient(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        super(config, handler, filters);
    }

    public OrkesWorkflowClient(ClientConfig config, ConductorClientConfiguration clientConfiguration, ClientHandler handler, ClientFilter... filters) {
        super(config, clientConfiguration, handler, filters);
    }

    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
