package io.orkes.conductor.client.http.orkesclient;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.api.*;
import io.orkes.conductor.client.http.client.SecretClient;

import java.util.List;

public class OrkesSecretClient extends OrkesClient implements SecretClient {

    private SecretResourceApi secretResourceApi;

    public OrkesSecretClient(ApiClient apiClient) {
        super(apiClient);
        this.secretResourceApi = new SecretResourceApi(apiClient);
    }


    @Override
    public Object deleteSecret(String key) throws ApiException {
        return secretResourceApi.deleteSecret(key);
    }

    @Override
    public Object getSecret(String key) throws ApiException {
        return secretResourceApi.getSecret(key);
    }

    @Override
    public Object listAllSecretNames() throws ApiException {
        return secretResourceApi.listAllSecretNames();
    }

    @Override
    public List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException {
        return secretResourceApi.listSecretsThatUserCanGrantAccessTo();
    }

    @Override
    public Object putSecret(String body, String key) throws ApiException {
        return secretResourceApi.putSecret(body, key);
    }

    @Override
    public Object secretExists(String key) throws ApiException {
        return secretResourceApi.secretExists(key);
    }
}
