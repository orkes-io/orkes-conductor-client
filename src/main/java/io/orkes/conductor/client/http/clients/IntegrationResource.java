/*
 * Copyright 2024 Orkes, Inc.
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
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.Integration;
import io.orkes.conductor.client.model.integration.IntegrationApi;
import io.orkes.conductor.client.model.integration.IntegrationApiUpdate;
import io.orkes.conductor.client.model.integration.IntegrationUpdate;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.PUT;

class IntegrationResource {

    private final OrkesHttpClient httpClient;

    public IntegrationResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void associatePromptWithIntegration(String integrationProvider, String integrationName, String promptName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}/prompt/{promptName}")
                .addPathParam("integrationProvider", integrationProvider)
                .addPathParam("integrationName", integrationName)
                .addPathParam("promptName", promptName)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteIntegrationApi(String integrationProvider, String integrationName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}")
                .addPathParam("integrationProvider", integrationProvider)
                .addPathParam("integrationName", integrationName)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteIntegrationProvider(String integrationProvider) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/integrations/provider/{integrationProvider}")
                .addPathParam("integrationProvider", integrationProvider)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteTagForIntegrationProvider(List<TagObject> body, String integrationProvider) {
        Objects.requireNonNull(body, "List<TagObject> cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/integrations/provider/{integrationProvider}/tags")
                .addPathParam("integrationProvider", integrationProvider)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public IntegrationApi getIntegrationApi(String integrationProvider, String integrationName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}")
                .addPathParam("integrationProvider", integrationProvider)
                .addPathParam("integrationName", integrationName)
                .build();

        ApiResponse<IntegrationApi> resp = httpClient.doRequest(request, new TypeReference<>() {
        });
        return resp.getData();
    }

    public List<IntegrationApi> getIntegrationApis(String name, Boolean activeOnly) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{name}/integration")
                .addPathParam("name", name)
                .addQueryParam("activeOnly", activeOnly)
                .build();

        ApiResponse<List<IntegrationApi>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Integration getIntegrationProvider(String integrationProvider) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}")
                .addPathParam("integrationProvider", integrationProvider)
                .build();

        ApiResponse<Integration> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<Integration> getIntegrationProviders(String category, Boolean activeOnly) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider")
                .addQueryParam("category", category)
                .addQueryParam("activeOnly", activeOnly)
                .build();
        ApiResponse<List<Integration>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<PromptTemplate> getPromptsWithIntegration(String integrationProvider, String integrationName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}/prompt")
                .addQueryParam("integrationProvider", integrationProvider)
                .addQueryParam("integrationName", integrationName)
                .build();

        ApiResponse<List<PromptTemplate>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<TagObject> getTagsForIntegrationProvider(String integrationProvider) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}/tags")
                .addPathParam("integrationProvider", integrationProvider)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Integer getTokenUsageForIntegration(String integrationProvider, String integrationName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}/metrics")
                .addPathParam("integrationProvider", integrationProvider)
                .addPathParam("integrationName", integrationName)
                .build();
        ApiResponse<Integer> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public Map<String, Integer> getTokenUsageForIntegrationProvider(String integrationProvider) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/integrations/provider/{integrationProvider}/metrics")
                .addPathParam("integrationProvider", integrationProvider)
                .build();
        ApiResponse<Map<String, Integer>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void putTagForIntegrationProvider(List<TagObject> body, String integrationProvider) {
        Objects.requireNonNull(body, "List<TagObject> cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(PUT)
                .path("/integrations/provider/{integrationProvider}/tags")
                .addPathParam("integrationProvider", integrationProvider)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public void saveIntegrationApi(IntegrationApiUpdate body, String integrationProvider, String integrationName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/integrations/provider/{integrationProvider}/integration/{integrationName}")
                .addPathParam("integrationProvider", integrationProvider)
                .addPathParam("integrationName", integrationName)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public void saveIntegrationProvider(IntegrationUpdate body, String integrationProvider) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/integrations/provider/{integrationProvider}")
                .addPathParam("integrationProvider", integrationProvider)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }
}
