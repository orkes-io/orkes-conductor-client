package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.ConductorUser;
import io.orkes.conductor.client.http.model.GrantedAccessResponse;
import io.orkes.conductor.client.http.model.Group;
import io.orkes.conductor.client.http.model.UpsertGroupRequest;

import java.util.List;

public interface GroupClient {
    void addUserToGroup(String groupId, String userId);

    void deleteGroup(String id);

    GrantedAccessResponse getGrantedPermissionsForGroup(String groupId);

    Group getGroup(String id);

    List<ConductorUser> getUsersInGroup(String id);

    List<Group> listGroups();

    void removeUserFromGroup(String groupId, String userId);

    Group upsertGroup(UpsertGroupRequest body, String id);
}
