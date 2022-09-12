package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;

import java.util.List;
import java.util.Set;

public interface SecretClient {
    void deleteSecret(String key) throws ApiException;

    String getSecret(String key) throws ApiException;

    Set<String> listAllSecretNames() throws ApiException;

    List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException;

    void putSecret(String body, String key) throws ApiException;

    boolean secretExists(String key) throws ApiException;
}
