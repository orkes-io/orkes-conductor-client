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

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.AccessKeyResponse;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.CreateAccessKeyResponse;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.TagObject;

import java.util.List;

class ApplicationResource extends Resource {

    public ApplicationResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        validateNonNull(new String[]{"applicationId", "role"}, applicationId, role);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("POST")
                .path("/applications/{applicationId}/roles/{role}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("role", role)
                .build();
        httpClient.doRequest(request, null);
    }

    CreateAccessKeyResponse createAccessKey(String applicationId) throws ApiException {
        validateNonNull(new String[]{"applicationId"}, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("POST")
                .path("/applications/{applicationId}/accessKeys")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<CreateAccessKeyResponse> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    ConductorApplication createApplication(CreateOrUpdateApplicationRequest body) throws ApiException {
        validateNonNull(new String[]{"body"}, body);

        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("POST")
                .path("/applications")
                .body(body)
                .build();

        ApiResponse<ConductorApplication> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        validateNonNull(new String[]{"applicationId", "keyId"}, applicationId, keyId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("DELETE")
                .path("/applications/{applicationId}/accessKeys/{keyId}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("keyId", keyId)
                .build();
        httpClient.doRequest(request, null);
    }

    void deleteApplication(String applicationId) throws ApiException {
        validateNonNull(new String[]{"applicationId"}, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("DELETE")
                .path("/applications/{applicationId}")
                .addPathParam("applicationId", applicationId)
                .build();
        httpClient.doRequest(request, null);
    }

    List<AccessKeyResponse> getAccessKeys(String applicationId) throws ApiException {
        validateNonNull(new String[]{"applicationId"}, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("GET")
                .path("/applications/{applicationId}/accessKeys")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<List<AccessKeyResponse>> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    ConductorApplication getApplication(String applicationId) throws ApiException {
        validateNonNull(new String[]{"applicationId"}, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("GET")
                .path("/applications/{applicationId}")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<ConductorApplication> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    List<ConductorApplication> listApplications() throws ApiException {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("GET")
                .path("/applications/{applicationId}")
                .build();
        ApiResponse<List<ConductorApplication>> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        validateNonNull(new String[]{"applicationId", "role"}, applicationId, role);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("DELETE")
                .path("/applications/{applicationId}/roles/{role}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("role", role)
                .build();
        httpClient.doRequest(request, null);
    }

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        validateNonNull(new String[]{"applicationId", "keyId"}, applicationId, keyId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("POST")
                .path("/applications/{applicationId}/accessKeys/{keyId}/status")
                .addPathParam("applicationId", applicationId)
                .addPathParam("keyId", keyId)
                .build();

        ApiResponse<AccessKeyResponse> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    ConductorApplication updateApplication(CreateOrUpdateApplicationRequest body, String applicationId) throws ApiException {
        validateNonNull(new String[]{"body", "applicationId"}, body, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("POST")
                .path("/applications/{id}")
                .addPathParam("applicationId", applicationId)
                .body(body)
                .build();

        ApiResponse<ConductorApplication> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void putTagForApplication(List<TagObject> body, String applicationId) throws ApiException {
        validateNonNull(new String[]{"body", "applicationId"}, body, applicationId);
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method("PUT")
                .path("/applications/{id}/tags")
                .addPathParam("applicationId", applicationId)
                .body(body)
                .build();

        httpClient.doRequest(request, null);
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
