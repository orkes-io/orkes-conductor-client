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

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.AccessKeyResponse;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.CreateAccessKeyResponse;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.TagObject;

import com.fasterxml.jackson.core.type.TypeReference;

class ApplicationResource extends Resource {

    private final OrkesHttpClient httpClient;

    public ApplicationResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        validateNonNull(new String[]{"applicationId", "role"}, applicationId, role);
        httpClient.doRequest("POST", "/applications/{applicationId}/roles/{role}", applicationId, role, null);
    }

    CreateAccessKeyResponse createAccessKey(String id) throws ApiException {
        validateNonNull(new String[]{"id"}, id);
        ApiResponse<CreateAccessKeyResponse> response = httpClient.doRequest(
                "POST", "/applications/{id}/accessKeys", id, null, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    ConductorApplication createApplication(CreateOrUpdateApplicationRequest request) throws ApiException {
        validateNonNull(new String[]{"CreateOrUpdateApplicationRequest"}, request);
        ApiResponse<ConductorApplication> response = httpClient.doRequest(
                "POST", "/applications", null, request, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        validateNonNull(new String[]{"applicationId", "keyId"}, applicationId, keyId);
        httpClient.doRequest("DELETE", "/applications/{applicationId}/accessKeys/{keyId}", applicationId, keyId, null);
    }

    void deleteApplication(String id) throws ApiException {
        validateNonNull(new String[]{"id", "keyId"}, id);
        httpClient.doRequest("DELETE", "/applications/{id}", id, null, null);
    }

    List<AccessKeyResponse> getAccessKeys(String id) throws ApiException {
        validateNonNull(new String[]{"id"}, id);
        ApiResponse<List<AccessKeyResponse>> response = httpClient.doRequest(
                "GET", "/applications/{id}/accessKeys", id, null, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    ConductorApplication getApplication(String id) throws ApiException {
        validateNonNull(new String[]{"id"}, id);
        ApiResponse<ConductorApplication> response = httpClient.doRequest(
                "GET", "/applications/{id}", id, null, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    List<ConductorApplication> listApplications() throws ApiException {
        ApiResponse<List<ConductorApplication>> response = httpClient.doRequest(
                "GET", "/applications", null, null, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    void removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        validateNonNull(new String[]{"applicationId", "role"}, applicationId, role);
        httpClient.doRequest("DELETE", "/applications/{applicationId}/roles/{role}", applicationId, role, null);
    }

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        validateNonNull(new String[]{"applicationId", "keyId"}, applicationId, keyId);
        ApiResponse<AccessKeyResponse> response = httpClient.doRequest(
                "POST", "/applications/{applicationId}/accessKeys/{keyId}/status",
                applicationId,
                keyId,
                new TypeReference<>() {
                }
        );
        return response.getData();
    }

    ConductorApplication updateApplication(CreateOrUpdateApplicationRequest request, String id) throws ApiException {
        validateNonNull(new String[]{"request", "id"}, request, id);
        ApiResponse<ConductorApplication> response = httpClient.doRequest(
                "PUT", "/applications/{id}", id, request, new TypeReference<>() {
                }
        );

        return response.getData();
    }

    void putTagForApplication(List<TagObject> tags, String id) throws ApiException {
        validateNonNull(new String[]{"tags", "id"}, tags, id);
        httpClient.doRequest("PUT", "/applications/{id}/tags", id, tags, null);
    }

    List<TagObject> getTagsForApplication(String id) throws ApiException {
        validateNonNull(new String[]{"id"}, id);
        ApiResponse<List<TagObject>> response = httpClient.doRequest(
                "GET", "/applications/{id}/tags", id, null,
                new TypeReference<>() {
                }
        );

        return response.getData();
    }

    void deleteTagForApplication(List<TagObject> tags, String id) throws ApiException {
        validateNonNull(new String[]{"tags", "id"}, tags, id);
        httpClient.doRequest("DELETE", "/applications/{id}/tags", id, tags, null);
    }
}
