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
package io.orkes.conductor.client.http;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.SecretsManager;
import io.orkes.conductor.client.http.api.ApplicationResourceApi;
import io.orkes.conductor.client.http.api.AuthorizationResourceApi;
import io.orkes.conductor.client.http.api.GroupResourceApi;
import io.orkes.conductor.client.http.api.UserResourceApi;
import io.orkes.conductor.client.model.*;

public class OrkesAuthorizationClient extends OrkesClient implements AuthorizationClient {

    private AuthorizationResourceApi authorizationResourceApi;
    private ApplicationResourceApi applicationResourceApi;
    private GroupResourceApi groupResourceApi;
    private UserResourceApi userResourceApi;

    public OrkesAuthorizationClient(ApiClient apiClient) {
        super(apiClient);
        this.authorizationResourceApi = new AuthorizationResourceApi(apiClient);
        this.applicationResourceApi = new ApplicationResourceApi(apiClient);
        this.groupResourceApi = new GroupResourceApi(apiClient);
        this.userResourceApi = new UserResourceApi(apiClient);
    }

    @Override
    public Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException {
        return authorizationResourceApi.getPermissions(type, id);
    }

    @Override
    public void grantPermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        authorizationResourceApi.grantPermissions(authorizationRequest);
    }

    @Override
    public void removePermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        authorizationResourceApi.removePermissions(authorizationRequest);
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
    public Group upsertGroup(UpsertGroupRequest upsertGroupRequest, String id) throws ApiException {
        return groupResourceApi.upsertGroup(upsertGroupRequest, id);
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
    public void sendInviteEmail(String id, ConductorUser conductorUser) throws ApiException {
        userResourceApi.sendInviteEmail(id, conductorUser);
    }

    @Override
    public ConductorUser upsertUser(UpsertUserRequest upsertUserRequest, String id)
            throws ApiException {
        return userResourceApi.upsertUser(upsertUserRequest, id);
    }

    @Override
    public void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        applicationResourceApi.addRoleToApplicationUser(applicationId, role);
    }

    @Override
    public CreateAccessKeyResponse createAccessKey(String id) throws ApiException {
        return applicationResourceApi.createAccessKey(id);
    }

    @Override
    public void createAccessKey(String id, SecretsManager secretsManager, String secretPath) {
        CreateAccessKeyResponse response = applicationResourceApi.createAccessKey(id);
        secretsManager.storeSecret(secretPath, response.getSecret());
    }

    @Override
    public ConductorApplication createApplication(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest) throws ApiException {
        return applicationResourceApi.createApplication(createOrUpdateApplicationRequest);
    }

    @Override
    public void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        applicationResourceApi.deleteAccessKey(applicationId, keyId);
    }

    @Override
    public void deleteApplication(String id) throws ApiException {
        applicationResourceApi.deleteApplication(id);
    }

    @Override
    public List<AccessKeyResponse> getAccessKeys(String id) throws ApiException {
        return applicationResourceApi.getAccessKeys(id);
    }

    @Override
    public ConductorApplication getApplication(String id) throws ApiException {
        return applicationResourceApi.getApplication(id);
    }

    @Override
    public List<ConductorApplication> listApplications() throws ApiException {
        return applicationResourceApi.listApplications();
    }

    @Override
    public void removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        applicationResourceApi.removeRoleFromApplicationUser(applicationId, role);
    }

    @Override
    public AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        return applicationResourceApi.toggleAccessKeyStatus(applicationId, keyId);
    }

    @Override
    public ConductorApplication updateApplication(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest, String id) throws ApiException {
        return applicationResourceApi.updateApplication(createOrUpdateApplicationRequest, id);
    }

    @Override
    public void setApplicationTags(List<TagObject> tags, String applicationId) {
        applicationResourceApi.putTagForApplication(tags, applicationId);
    }

    @Override
    public List<TagObject> getApplicationTags(String applicationId) {
        return applicationResourceApi.getTagsForApplication(applicationId);
    }

    @Override
    public void deleteApplicationTags(List<TagObject> tags, String applicationId) {
        applicationResourceApi.deleteTagForApplication(tags, applicationId);
    }
}
