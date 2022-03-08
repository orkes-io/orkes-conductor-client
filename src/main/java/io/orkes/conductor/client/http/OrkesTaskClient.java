package io.orkes.conductor.client.http;

import com.netflix.conductor.client.http.TaskClient;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

public class OrkesTaskClient extends TaskClient implements OrkesClient {
    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
