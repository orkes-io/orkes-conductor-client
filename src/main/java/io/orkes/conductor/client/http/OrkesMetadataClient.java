package io.orkes.conductor.client.http;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.http.MetadataClient;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesMetadataClient extends MetadataClient implements OrkesClient {
    public OrkesMetadataClient() {
    }

    public OrkesMetadataClient(ClientConfig clientConfig) {
        super(clientConfig);
    }

    public OrkesMetadataClient(ClientConfig clientConfig, ClientHandler clientHandler) {
        super(clientConfig, clientHandler);
    }

    public OrkesMetadataClient(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        super(config, handler, filters);
    }

    public OrkesMetadataClient(ClientConfig config, ConductorClientConfiguration clientConfiguration, ClientHandler handler, ClientFilter... filters) {
        super(config, clientConfiguration, handler, filters);
    }

    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
