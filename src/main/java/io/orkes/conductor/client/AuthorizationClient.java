package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.*;

import java.util.List;
import java.util.Map;

public interface AuthorizationClient {

    //Permissions

    Map<String, List<Subject>> getPermissions(String type, String id);

    void grantPermissions(AuthorizationRequest body);

    void removePermissions(AuthorizationRequest body);

    //Users
    void deleteUser(String id);

    GrantedAccessResponse getGrantedPermissionsForUser(String userId);

    ConductorUser getUser(String id);

    List<ConductorUser> listUsers(Boolean apps);

    void sendInviteEmail(String id, ConductorUser body);

    ConductorUser upsertUser(UpsertUserRequest body, String id);

    //Groups
    void addUserToGroup(String groupId, String userId);

    void deleteGroup(String id);

    GrantedAccessResponse getGrantedPermissionsForGroup(String groupId);

    Group getGroup(String id);

    List<ConductorUser> getUsersInGroup(String id);

    List<Group> listGroups();

    void removeUserFromGroup(String groupId, String userId);

    Group upsertGroup(UpsertGroupRequest body, String id);

    //Applications
    void addRoleToApplicationUser(String applicationId, String role);

    CreateAccessKeyResponse createAccessKey(String id);

    ConductorApplication createApplication(CreateOrUpdateApplicationRequest body);

    void deleteAccessKey(String applicationId, String keyId);

    void deleteApplication(String id);

    List<AccessKeyResponse> getAccessKeys(String id);

    ConductorApplication getApplication(String id);

    List<ConductorApplication> listApplications();

    void removeRoleFromApplicationUser(String applicationId, String role)
            ;

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId);

    ConductorApplication updateApplication(CreateOrUpdateApplicationRequest body, String id);
}
