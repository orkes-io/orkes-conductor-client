package io.orkes.conductor.client.http;

import com.netflix.conductor.client.http.MetadataClient;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesMetadataClient extends MetadataClient implements OrkesClient {
    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
