package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.ConductorUser;
import io.orkes.conductor.client.http.model.GrantedAccessResponse;
import io.orkes.conductor.client.http.model.Response;
import io.orkes.conductor.client.http.model.UpsertUserRequest;

import java.util.List;

public interface UserClient {
    void deleteUser(String id) throws ApiException;

    GrantedAccessResponse getGrantedPermissionsForUser(String userId) throws ApiException;

    ConductorUser getUser(String id) throws ApiException;

    List<ConductorUser> listUsers(Boolean apps) throws ApiException;

    void sendInviteEmail(String id, ConductorUser body) throws ApiException;

    ConductorUser upsertUser(UpsertUserRequest body, String id) throws ApiException;
}
