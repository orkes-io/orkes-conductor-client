package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;

import java.util.List;

public interface SecretClient {
    Object deleteSecret(String key) throws ApiException;

    Object getSecret(String key) throws ApiException;

    Object listAllSecretNames() throws ApiException;

    List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException;

    Object putSecret(String body, String key) throws ApiException;

    Object secretExists(String key) throws ApiException;
}
