package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.ConductorUser;
import io.orkes.conductor.client.http.model.GrantedAccessResponse;
import io.orkes.conductor.client.http.model.UpsertUserRequest;

import java.util.List;

public interface UserClient {
    void deleteUser(String id);

    GrantedAccessResponse getGrantedPermissionsForUser(String userId);

    ConductorUser getUser(String id);

    List<ConductorUser> listUsers(Boolean apps);

    void sendInviteEmail(String id, ConductorUser body);

    ConductorUser upsertUser(UpsertUserRequest body, String id);
}
