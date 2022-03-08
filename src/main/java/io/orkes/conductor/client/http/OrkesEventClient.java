package io.orkes.conductor.client.http;

import com.netflix.conductor.client.http.EventClient;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesEventClient extends EventClient implements OrkesClient {
    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
