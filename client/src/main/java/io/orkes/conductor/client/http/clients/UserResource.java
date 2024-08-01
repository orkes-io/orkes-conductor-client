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
import io.orkes.conductor.client.model.UpsertUserRequest;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class UserResource {
    private OrkesHttpClient httpClient;

    public UserResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void deleteUser(String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/users/{id}")
                .addPathParam("id", id)
                .build();

        httpClient.doRequest(request);
    }

    public GrantedAccessResponse getGrantedPermissions(String userId) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/users/{userId}/permissions")
                .addPathParam("userId", userId)
                .build();

        ApiResponse<GrantedAccessResponse> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public ConductorUser getUser(String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/users/{id}")
                .addPathParam("id", id)
                .build();

        ApiResponse<ConductorUser> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }


    public List<ConductorUser> listUsers(Boolean apps) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/users")
                .addQueryParam("apps", apps)
                .build();

        ApiResponse<List<ConductorUser>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void sendInviteEmail(String email) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/users/{email}/sendInviteEmail")
                .addPathParam("email", email)
                .build();

        httpClient.doRequest(request);
    }

    public ConductorUser upsertUser(UpsertUserRequest upsertUserRequest, String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/users/{id}")
                .addPathParam("id", id)
                .body(upsertUserRequest)
                .build();

        ApiResponse<ConductorUser> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
