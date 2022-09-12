package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.AuthorizationRequest;
import io.orkes.conductor.client.http.model.Subject;

import java.util.List;
import java.util.Map;

public interface AuthorizationClient {
    Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException;

    void grantPermissions(AuthorizationRequest body) throws ApiException;

    void removePermissions(AuthorizationRequest body) throws ApiException;
}
