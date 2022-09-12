package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.ConductorUser;
import io.orkes.conductor.client.http.model.GrantedAccessResponse;
import io.orkes.conductor.client.http.model.Group;
import io.orkes.conductor.client.http.model.UpsertGroupRequest;

import java.util.List;

public interface GroupClient {
    void addUserToGroup(String groupId, String userId) throws ApiException;

    void deleteGroup(String id) throws ApiException;

    GrantedAccessResponse getGrantedPermissionsForGroup(String groupId) throws ApiException;

    Group getGroup(String id) throws ApiException;

    List<ConductorUser> getUsersInGroup(String id) throws ApiException;

    List<Group> listGroups() throws ApiException;

    void removeUserFromGroup(String groupId, String userId) throws ApiException;

    Group upsertGroup(UpsertGroupRequest body, String id) throws ApiException;
}
