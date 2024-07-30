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
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowDef;

import java.util.List;
import java.util.Objects;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class MetadataResource extends Resource {

    public MetadataResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public void registerWorkflowDef(WorkflowDef workflowDef, Boolean overwrite) {
        Objects.requireNonNull(workflowDef, "WorkflowDef cannot be null");
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/metadata/workflow")
                .addQueryParam("overwrite", overwrite)
                .body(workflowDef)
                .build();

        httpClient.doRequest(request, null);
    }

    public WorkflowDef getWorkflow(String name, Integer version, Boolean metadata) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/workflow/{name}")
                .addPathParam("name", name)
                .addQueryParam("version", version)
                .addQueryParam("metadata", metadata)
                .build();

        ApiResponse<WorkflowDef> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<WorkflowDef> getAllWorkflows(
            String access, Boolean metadata, String tagKey, String tagValue) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/metadata/workflow")
                .addQueryParam("access", access)
                .addQueryParam("metadata", metadata)
                .addQueryParam("tagKey", tagKey)
                .addQueryParam("tagValue", tagValue)
                .build();

        ApiResponse<List<WorkflowDef>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public TaskDef getTaskDef(String taskType, Boolean metadata) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/metadata/taskdefs/{taskType}")
                .addPathParam("taskType", taskType)
                .addQueryParam("metadata", metadata)
                .build();

        ApiResponse<TaskDef> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public List<TaskDef> getTaskDefs(String access, Boolean metadata, String tagKey, String tagValue) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/metadata/taskdefs")
                .addQueryParam("access", access)
                .addQueryParam("metadata", metadata)
                .addQueryParam("tagKey", tagKey)
                .addQueryParam("tagValue", tagValue)
                .build();

        ApiResponse<List<TaskDef>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void registerTaskDef(List<TaskDef> taskDefs) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.POST)
                .path("/metadata/taskdefs")
                .body(taskDefs)
                .build();

        httpClient.doRequest(request, null);
    }

    public void unregisterTaskDef(String taskType) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/metadata/taskdefs/{taskType}")
                .addPathParam("taskType", taskType)
                .build();

        httpClient.doRequest(request, null);
    }

    public void unregisterWorkflowDef(String name, Integer version) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/metadata/workflow/{name}/{version}")
                .addPathParam("name", name)
                .addPathParam("version", Integer.toString(version))
                .build();

        httpClient.doRequest(request, null);
    }

    public void updateWorkflows(List<WorkflowDef> workflowDefs, Boolean overwrite) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/metadata/workflow")
                .addQueryParam("overwrite", overwrite)
                .body(workflowDefs)
                .build();

        httpClient.doRequest(request, null);
    }

    public void updateTaskDef(TaskDef taskDef) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/metadata/taskdefs")
                .body(taskDef)
                .build();

        httpClient.doRequest(request, null);
    }
}
