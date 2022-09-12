package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.ConductorUser;
import io.orkes.conductor.client.http.model.Response;
import io.orkes.conductor.client.http.model.UpsertUserRequest;

import java.util.List;

public interface UserClient {
    Response deleteUser(String id) throws ApiException;

    Object getGrantedPermissionsForUser(String userId) throws ApiException;

    Object getUser(String id) throws ApiException;

    List<ConductorUser> listUsers(Boolean apps) throws ApiException;

    Object sendInviteEmail(String id, ConductorUser body) throws ApiException;

    Object upsertUser(UpsertUserRequest body, String id) throws ApiException;
}
