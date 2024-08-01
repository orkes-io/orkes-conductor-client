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
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.DELETE;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;

class TagsResource extends Resource {

    public TagsResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public void addTaskTag(TagObject tagObject, String taskName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/metadata/task/{taskName}/tags")
                .addPathParam("taskName", taskName)
                .body(tagObject)
                .build();

        httpClient.doRequest(request);
    }

    public void addWorkflowTag(TagObject tagObject, String workflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/metadata/workflow/{workflow}/tags")
                .addPathParam("workflow", workflow)
                .body(tagObject)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteTaskTag(TagString tagString, String taskName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/metadata/task/{taskName}/tags")
                .addPathParam("taskName", taskName)
                .body(tagString)
                .build();

        httpClient.doRequest(request);
    }

    public void deleteWorkflowTag(TagObject tagObject, String workflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(DELETE)
                .path("/metadata/workflow/{workflow}/tags")
                .addPathParam("workflow", workflow)
                .body(tagObject)
                .build();

        httpClient.doRequest(request);
    }

    public List<TagObject> getTags() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/tags")
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<TagObject> getTaskTags(String taskName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/task/{taskName}/tags")
                .addPathParam("taskName", taskName)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<TagObject> getWorkflowTags(String name) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/workflow/{name}/tags")
                .addPathParam("name", name)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void setTaskTags(List<TagObject> tagObjects, String taskName) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/metadata/task/{taskName}/tags")
                .addPathParam("taskName", taskName)
                .body(tagObjects)
                .build();

        httpClient.doRequest(request);
    }

    public void setWorkflowTags(List<TagObject> tagObjects, String workflow) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/workflow/{workflow}/tags")
                .addPathParam("workflow", workflow)
                .body(tagObjects)
                .build();

        httpClient.doRequest(request);
    }
}
