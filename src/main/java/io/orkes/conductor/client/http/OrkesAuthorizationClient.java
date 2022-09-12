package io.orkes.conductor.client.http;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.api.AuthorizationResourceApi;
import io.orkes.conductor.client.http.api.GroupResourceApi;
import io.orkes.conductor.client.http.api.UserResourceApi;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.GroupClient;
import io.orkes.conductor.client.UserClient;
import io.orkes.conductor.client.http.model.*;

import java.util.List;
import java.util.Map;

public class OrkesAuthorizationClient extends OrkesClient implements AuthorizationClient, GroupClient, UserClient {

    private AuthorizationResourceApi authorizationResourceApi;
    private GroupResourceApi groupResourceApi;
    private UserResourceApi userResourceApi;

    public OrkesAuthorizationClient(ApiClient apiClient) {
        super(apiClient);
        this.authorizationResourceApi = new AuthorizationResourceApi(apiClient);
        this.groupResourceApi = new GroupResourceApi(apiClient);
        this.userResourceApi = new UserResourceApi(apiClient);
    }

    @Override
    public Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException {
        return authorizationResourceApi.getPermissions(type, id);
    }

    @Override
    public void grantPermissions(AuthorizationRequest body) throws ApiException {
        authorizationResourceApi.grantPermissions(body);
    }

    @Override
    public void removePermissions(AuthorizationRequest body) throws ApiException {
        authorizationResourceApi.removePermissions(body);
    }

    @Override
    public void addUserToGroup(String groupId, String userId) throws ApiException {
        groupResourceApi.addUserToGroup(groupId, userId);
    }

    @Override
    public void deleteGroup(String id) throws ApiException {
        groupResourceApi.deleteGroup(id);
    }

    @Override
    public GrantedAccessResponse getGrantedPermissionsForGroup(String groupId) throws ApiException {
        return groupResourceApi.getGrantedPermissions1(groupId);
    }

    @Override
    public Group getGroup(String id) throws ApiException {
        return groupResourceApi.getGroup(id);
    }

    @Override
    public List<ConductorUser> getUsersInGroup(String id) throws ApiException {
        return groupResourceApi.getUsersInGroup(id);
    }

    @Override
    public List<Group> listGroups() throws ApiException {
        return groupResourceApi.listGroups();
    }

    @Override
    public void removeUserFromGroup(String groupId, String userId) throws ApiException {
        groupResourceApi.removeUserFromGroup(groupId, userId);
    }

    @Override
    public Group upsertGroup(UpsertGroupRequest body, String id) throws ApiException {
        return groupResourceApi.upsertGroup(body, id);
    }

    @Override
    public void deleteUser(String id) throws ApiException {
        userResourceApi.deleteUser(id);
    }

    @Override
    public GrantedAccessResponse getGrantedPermissionsForUser(String userId) throws ApiException {
        return userResourceApi.getGrantedPermissions(userId);
    }

    @Override
    public ConductorUser getUser(String id) throws ApiException {
        return userResourceApi.getUser(id);
    }

    @Override
    public List<ConductorUser> listUsers(Boolean apps) throws ApiException {
        return userResourceApi.listUsers(apps);
    }

    @Override
    public void sendInviteEmail(String id, ConductorUser body) throws ApiException {
        userResourceApi.sendInviteEmail(id, body);
    }

    @Override
    public ConductorUser upsertUser(UpsertUserRequest body, String id) throws ApiException {
        return userResourceApi.upsertUser(body, id);
    }
}
