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

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.PromptTemplateTestRequest;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class PromptResource extends Resource {

    public PromptResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public void deletePromptTemplate(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/prompts/{name}")
                .addPathParam("name", name)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteTagForPromptTemplate(String name, List<TagObject> body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/prompts/{name}/tags")
                .addPathParam("name", name)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public PromptTemplate getPromptTemplate(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/prompts/{name}")
                .addPathParam("name", name)
                .build();

        ApiResponse<PromptTemplate> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<PromptTemplate> getPromptTemplates() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/prompts")
                .build();

        ApiResponse<List<PromptTemplate>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<TagObject> getTagsForPromptTemplate(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/prompts/{name}/tags")
                .addPathParam("name", name)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void putTagForPromptTemplate(String name, List<TagObject> body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/prompts/{name}/tags")
                .addPathParam("name", name)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public void savePromptTemplate(String name, String body, String description, List<String> models) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/prompts/{name}")
                .addPathParam("name", name)
                .addQueryParam("description", description)
                .addQueryParams("models", models)
                .build();

        httpClient.doRequest(request);
    }

    public String testMessageTemplate(PromptTemplateTestRequest body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/prompts/test")
                .body(body)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
