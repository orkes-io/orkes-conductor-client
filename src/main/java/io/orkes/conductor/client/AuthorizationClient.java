package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.AuthorizationRequest;
import io.orkes.conductor.client.http.model.Subject;

import java.util.List;
import java.util.Map;

public interface AuthorizationClient {
    Map<String, List<Subject>> getPermissions(String type, String id);

    void grantPermissions(AuthorizationRequest body);

    void removePermissions(AuthorizationRequest body);
}
