package io.orkes.conductor.client.http;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.api.*;
import io.orkes.conductor.client.SecretClient;

import java.util.List;
import java.util.Set;

public class OrkesSecretClient extends OrkesClient implements SecretClient {

    private SecretResourceApi secretResourceApi;

    public OrkesSecretClient(ApiClient apiClient) {
        super(apiClient);
        this.secretResourceApi = new SecretResourceApi(apiClient);
    }


    @Override
    public void deleteSecret(String key) throws ApiException {
        secretResourceApi.deleteSecret(key);
    }

    @Override
    public String getSecret(String key) throws ApiException {
        return secretResourceApi.getSecret(key);
    }

    @Override
    public Set<String> listAllSecretNames() throws ApiException {
        return secretResourceApi.listAllSecretNames();
    }

    @Override
    public List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException {
        return secretResourceApi.listSecretsThatUserCanGrantAccessTo();
    }

    @Override
    public void putSecret(String body, String key) throws ApiException {
        secretResourceApi.putSecret(body, key);
    }

    @Override
    public boolean secretExists(String key) throws ApiException {
        return secretResourceApi.secretExists(key);
    }
}
