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
import java.util.Objects;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.AuthorizationRequest;
import io.orkes.conductor.client.model.Subject;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class AuthorizationResource extends Resource {

    AuthorizationResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    Map<String, List<Subject>> getPermissions(String type, String id) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/auth/authorization/{type}/{id}")
                .addPathParam("type", type)
                .addPathParam("id", id)
                .build();

        ApiResponse<Map<String, List<Subject>>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    void grantPermissions(AuthorizationRequest body) {
        Objects.requireNonNull(body, "AuthorizationRequest cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/auth/authorization")
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    void removePermissions(AuthorizationRequest body) {
        Objects.requireNonNull(body, "AuthorizationRequest cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/auth/authorization")
                .body(body)
                .build();

        httpClient.doRequest(request);
    }
}
