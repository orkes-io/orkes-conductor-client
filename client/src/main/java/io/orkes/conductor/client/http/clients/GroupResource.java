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

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.ConductorUser;
import io.orkes.conductor.client.model.GrantedAccessResponse;
import io.orkes.conductor.client.model.Group;
import io.orkes.conductor.client.model.UpsertGroupRequest;

import com.fasterxml.jackson.core.type.TypeReference;

class GroupResource extends Resource {

    public GroupResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public void addUserToGroup(String groupId, String userId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/groups/{groupId}/users/{userId}")
                .addPathParam("groupId", groupId)
                .addPathParam("userId", userId)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteGroup(String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/groups/{id}")
                .addPathParam("id", id)
                .build();

        httpClient.doRequest(request);
    }

    public GrantedAccessResponse getGrantedPermissions(String groupId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/groups/{groupId}/permissions")
                .addPathParam("groupId", groupId)
                .build();

        ApiResponse<GrantedAccessResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Group getGroup(String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/groups/{id}")
                .addPathParam("id", id)
                .build();

        ApiResponse<Group> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<ConductorUser> getUsersInGroup(String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/groups/{id}/users")
                .addPathParam("id", id)
                .build();

        ApiResponse<List<ConductorUser>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<Group> listGroups() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/groups")
                .build();

        ApiResponse<List<Group>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void removeUserFromGroup(String groupId, String userId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/groups/{groupId}/users/{userId}")
                .addPathParam("groupId", groupId)
                .addPathParam("userId", userId)
                .build();

        httpClient.doRequest(request);
    }

    public Group upsertGroup(UpsertGroupRequest upsertGroupRequest, String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/groups/{id}")
                .addPathParam("id", id)
                .body(upsertGroupRequest)
                .build();

        ApiResponse<Group> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
