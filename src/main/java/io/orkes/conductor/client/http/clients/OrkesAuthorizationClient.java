/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.http.clients;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.SecretsManager;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.*;

public class OrkesAuthorizationClient extends OrkesClient implements AuthorizationClient {

    private final AuthorizationResource authorizationResource;
    private final ApplicationResource applicationResource;
    private final GroupResource groupResource;
    private final UserResource userResource;

    public OrkesAuthorizationClient(OrkesHttpClient httpClient) {
        super(httpClient);
        this.authorizationResource = new AuthorizationResource(httpClient);
        this.applicationResource = new ApplicationResource(httpClient);
        this.groupResource = new GroupResource(httpClient);
        this.userResource = new UserResource(httpClient);
    }

    @Override
    public Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException {
        return authorizationResource.getPermissions(type, id);
    }

    @Override
    public void grantPermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        authorizationResource.grantPermissions(authorizationRequest);
    }

    @Override
    public void removePermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        authorizationResource.removePermissions(authorizationRequest);
    }

    @Override
    public void addUserToGroup(String groupId, String userId) throws ApiException {
        groupResource.addUserToGroup(groupId, userId);
    }

    @Override
    public void deleteGroup(String id) throws ApiException {
        groupResource.deleteGroup(id);
    }

    @Override
    public GrantedAccessResponse getGrantedPermissionsForGroup(String groupId) throws ApiException {
        return groupResource.getGrantedPermissions1(groupId);
    }

    @Override
    public Group getGroup(String id) throws ApiException {
        return groupResource.getGroup(id);
    }

    @Override
    public List<ConductorUser> getUsersInGroup(String id) throws ApiException {
        return groupResource.getUsersInGroup(id);
    }

    @Override
    public List<Group> listGroups() throws ApiException {
        return groupResource.listGroups();
    }

    @Override
    public void removeUserFromGroup(String groupId, String userId) throws ApiException {
        groupResource.removeUserFromGroup(groupId, userId);
    }

    @Override
    public Group upsertGroup(UpsertGroupRequest upsertGroupRequest, String id) throws ApiException {
        return groupResource.upsertGroup(upsertGroupRequest, id);
    }

    @Override
    public void deleteUser(String id) throws ApiException {
        userResource.deleteUser(id);
    }

    @Override
    public GrantedAccessResponse getGrantedPermissionsForUser(String userId) throws ApiException {
        return userResource.getGrantedPermissions(userId);
    }

    @Override
    public ConductorUser getUser(String id) throws ApiException {
        return userResource.getUser(id);
    }

    @Override
    public List<ConductorUser> listUsers(Boolean apps) throws ApiException {
        return userResource.listUsers(apps);
    }

    @Override
    public void sendInviteEmail(String id, ConductorUser conductorUser) throws ApiException {
        userResource.sendInviteEmail(id, conductorUser);
    }

    @Override
    public ConductorUser upsertUser(UpsertUserRequest upsertUserRequest, String id)
            throws ApiException {
        return userResource.upsertUser(upsertUserRequest, id);
    }

    @Override
    public void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        applicationResource.addRoleToApplicationUser(applicationId, role);
    }

    @Override
    public CreateAccessKeyResponse createAccessKey(String id) throws ApiException {
        return applicationResource.createAccessKey(id);
    }

    @Override
    public void createAccessKey(String id, SecretsManager secretsManager, String secretPath) {
        CreateAccessKeyResponse response = applicationResource.createAccessKey(id);
        secretsManager.storeSecret(secretPath, response.getSecret());
    }

    @Override
    public ConductorApplication createApplication(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest) throws ApiException {
        return applicationResource.createApplication(createOrUpdateApplicationRequest);
    }

    @Override
    public void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        applicationResource.deleteAccessKey(applicationId, keyId);
    }

    @Override
    public void deleteApplication(String id) throws ApiException {
        applicationResource.deleteApplication(id);
    }

    @Override
    public List<AccessKeyResponse> getAccessKeys(String id) throws ApiException {
        return applicationResource.getAccessKeys(id);
    }

    @Override
    public ConductorApplication getApplication(String id) throws ApiException {
        return applicationResource.getApplication(id);
    }

    @Override
    public List<ConductorApplication> listApplications() throws ApiException {
        return applicationResource.listApplications();
    }

    @Override
    public void removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        applicationResource.removeRoleFromApplicationUser(applicationId, role);
    }

    @Override
    public AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        return applicationResource.toggleAccessKeyStatus(applicationId, keyId);
    }

    @Override
    public ConductorApplication updateApplication(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest, String id) throws ApiException {
        return applicationResource.updateApplication(createOrUpdateApplicationRequest, id);
    }

    @Override
    public void setApplicationTags(List<TagObject> tags, String applicationId) {
        applicationResource.putTagForApplication(tags, applicationId);
    }

    @Override
    public List<TagObject> getApplicationTags(String applicationId) {
        return applicationResource.getTagsForApplication(applicationId);
    }

    @Override
    public void deleteApplicationTags(List<TagObject> tags, String applicationId) {
        applicationResource.deleteTagForApplication(tags, applicationId);
    }
}
