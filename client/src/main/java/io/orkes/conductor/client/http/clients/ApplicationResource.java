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
import java.util.Objects;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.AccessKeyResponse;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.CreateAccessKeyResponse;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.TagObject;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.PUT;

class ApplicationResource extends Resource {

    ApplicationResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    void addRoleToApplicationUser(String applicationId, String role)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/applications/{applicationId}/roles/{role}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("role", role)
                .build();
        httpClient.doRequest(request);
    }

    CreateAccessKeyResponse createAccessKey(String applicationId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/applications/{applicationId}/accessKeys")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<CreateAccessKeyResponse> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    ConductorApplication upsertApplication(CreateOrUpdateApplicationRequest body, String id)  {
        Objects.requireNonNull(body, "CreateOrUpdateApplicationRequest cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/applications" + (id != null ? "/{id}" : ""))
                .addPathParam("id", id)
                .body(body)
                .build();

        ApiResponse<ConductorApplication> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void deleteAccessKey(String applicationId, String keyId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/applications/{applicationId}/accessKeys/{keyId}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("keyId", keyId)
                .build();
        httpClient.doRequest(request);
    }

    void deleteApplication(String applicationId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/applications/{applicationId}")
                .addPathParam("applicationId", applicationId)
                .build();
        httpClient.doRequest(request);
    }

    List<AccessKeyResponse> getAccessKeys(String applicationId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/applications/{applicationId}/accessKeys")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<List<AccessKeyResponse>> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    ConductorApplication getApplication(String applicationId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/applications/{applicationId}")
                .addPathParam("applicationId", applicationId)
                .build();
        ApiResponse<ConductorApplication> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    List<ConductorApplication> listApplications()  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/applications/{applicationId}")
                .build();
        ApiResponse<List<ConductorApplication>> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void removeRoleFromApplicationUser(String applicationId, String role)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/applications/{applicationId}/roles/{role}")
                .addPathParam("applicationId", applicationId)
                .addPathParam("role", role)
                .build();
        httpClient.doRequest(request);
    }

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/applications/{applicationId}/accessKeys/{keyId}/status")
                .addPathParam("applicationId", applicationId)
                .addPathParam("keyId", keyId)
                .build();

        ApiResponse<AccessKeyResponse> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void putTags(List<TagObject> body, String applicationId)  {
        Objects.requireNonNull(body, "List<TagObject> cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(PUT)
                .path("/applications/{id}/tags")
                .addPathParam("applicationId", applicationId)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    List<TagObject> getTags(String applicationId)  {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/applications/{id}/tags")
                .addPathParam("applicationId", applicationId)
                .build();

        ApiResponse<List<TagObject>> response = httpClient.doRequest(request, new TypeReference<>() {
        });

        return response.getData();
    }

    void deleteTags(List<TagObject> body, String applicationId)  {
        Objects.requireNonNull(body, "List<TagObject> cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/applications/{id}/tags")
                .addPathParam("applicationId", applicationId)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }
}
