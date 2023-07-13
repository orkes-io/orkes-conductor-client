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
package io.orkes.conductor.client.http;

import java.util.List;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.http.api.MetadataResourceApi;
import io.orkes.conductor.client.http.api.TagsApi;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

public class OrkesMetadataClient extends MetadataClient  {

    protected ApiClient apiClient;

    public OrkesMetadataClient withReadTimeout(int readTimeout) {
        apiClient.setReadTimeout(readTimeout);
        return this;
    }

    public OrkesMetadataClient setWriteTimeout(int writeTimeout) {
        apiClient.setWriteTimeout(writeTimeout);
        return this;
    }

    public OrkesMetadataClient withConnectTimeout(int connectTimeout) {
        apiClient.setConnectTimeout(connectTimeout);
        return this;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    private final MetadataResourceApi metadataResourceApi;
    private final TagsApi tagsApi;

    public OrkesMetadataClient(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.metadataResourceApi = new MetadataResourceApi(apiClient);
        this.tagsApi = new TagsApi(apiClient);
    }

    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef) {
        metadataResourceApi.create(workflowDef, true);
    }

    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef, boolean overwrite) {
        metadataResourceApi.create(workflowDef, overwrite);
    }

    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs) {
        metadataResourceApi.update(workflowDefs, true);
    }

    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs, boolean overwrite) {
        metadataResourceApi.update(workflowDefs, overwrite);
    }

    @Override
    public WorkflowDef getWorkflowDef(String name, Integer version) {
        return metadataResourceApi.get(name, version, false);
    }

    public WorkflowDef getWorkflowDefWithMetadata(String name, Integer version) {
        return metadataResourceApi.get(name, version, true);
    }

    @Override
    public void unregisterWorkflowDef(String name, Integer version) {
        metadataResourceApi.unregisterWorkflowDef(name, version);
    }

    @Override
    public List<TaskDef> getAllTaskDefs() {
        return metadataResourceApi.getTaskDefs(null, false, null, null);
    }

    @Override
    public void registerTaskDefs(List<TaskDef> taskDefs) {
        metadataResourceApi.registerTaskDef(taskDefs);
    }

    @Override
    public void updateTaskDef(TaskDef taskDef) {
        metadataResourceApi.updateTaskDef(taskDef);
    }

    @Override
    public TaskDef getTaskDef(String taskType) {
        return metadataResourceApi.getTaskDef(taskType, true);
    }

    @Override
    public void unregisterTaskDef(String taskType) {
        metadataResourceApi.unregisterTaskDef(taskType);
    }

    @Override
    public void addTaskTag(TagObject tagObject, String taskName) {
        tagsApi.addTaskTag(tagObject, taskName);
    }

    @Override
    public void addWorkflowTag(TagObject tagObject, String name) {
        tagsApi.addWorkflowTag(tagObject, name);
    }

    @Override
    public void deleteTaskTag(TagString tagString, String taskName) {
        tagsApi.deleteTaskTag(tagString, taskName);
    }

    @Override
    public void deleteWorkflowTag(TagObject tagObject, String name) {
        tagsApi.deleteWorkflowTag(tagObject, name);
    }

    @Override
    public List<TagObject> getTags() {
        return tagsApi.getTags();
    }

    @Override
    public List<TagObject> getTaskTags(String taskName) {
        return tagsApi.getTaskTags(taskName);
    }

    @Override
    public List<TagObject> getWorkflowTags(String name) {
        return tagsApi.getWorkflowTags(name);
    }

    @Override
    public void setTaskTags(List<TagObject> tagObjects, String taskName) {
        tagsApi.setTaskTags(tagObjects, taskName);
    }

    @Override
    public void setWorkflowTags(List<TagObject> tagObjects, String name) {
        tagsApi.setWorkflowTags(tagObjects, name);
    }

    @Override
    public List<WorkflowDef> getAllWorkflowDefs() {
        return metadataResourceApi.getAllWorkflows(null, false, null, null);
    }
}
