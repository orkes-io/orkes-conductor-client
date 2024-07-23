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

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;
import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowDef;

import java.util.List;

public class OrkesMetadataClient extends OrkesClient implements MetadataClient {

    private final MetadataResource metadataResource;
    private final TagsResource tagsResource;

    public OrkesMetadataClient(OrkesHttpClient httpClient) {
        super(httpClient);
        this.metadataResource = new MetadataResource(httpClient);
        this.tagsResource = new TagsResource(httpClient);
    }

    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef) {
        metadataResource.create(workflowDef, true);
    }

    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef, boolean overwrite) {
        metadataResource.create(workflowDef, overwrite);
    }

    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs) {
        metadataResource.update(workflowDefs, true);
    }

    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs, boolean overwrite) {
        metadataResource.update(workflowDefs, overwrite);
    }

    @Override
    public WorkflowDef getWorkflowDef(String name, Integer version) {
        return metadataResource.get(name, version, false);
    }

    public WorkflowDef getWorkflowDefWithMetadata(String name, Integer version) {
        return metadataResource.get(name, version, true);
    }

    @Override
    public void unregisterWorkflowDef(String name, Integer version) {
        metadataResource.unregisterWorkflowDef(name, version);
    }

    @Override
    public List<TaskDef> getAllTaskDefs() {
        return metadataResource.getTaskDefs(null, false, null, null);
    }

    @Override
    public void registerTaskDefs(List<TaskDef> taskDefs) {
        metadataResource.registerTaskDef(taskDefs);
    }

    @Override
    public void updateTaskDef(TaskDef taskDef) {
        metadataResource.updateTaskDef(taskDef);
    }

    @Override
    public TaskDef getTaskDef(String taskType) {
        return metadataResource.getTaskDef(taskType, true);
    }

    @Override
    public void unregisterTaskDef(String taskType) {
        metadataResource.unregisterTaskDef(taskType);
    }

    @Override
    public void addTaskTag(TagObject tagObject, String taskName) {
        tagsResource.addTaskTag(tagObject, taskName);
    }

    @Override
    public void addWorkflowTag(TagObject tagObject, String name) {
        tagsResource.addWorkflowTag(tagObject, name);
    }

    @Override
    public void deleteTaskTag(TagString tagString, String taskName) {
        tagsResource.deleteTaskTag(tagString, taskName);
    }

    @Override
    public void deleteWorkflowTag(TagObject tagObject, String name) {
        tagsResource.deleteWorkflowTag(tagObject, name);
    }

    @Override
    public List<TagObject> getTags() {
        return tagsResource.getTags();
    }

    @Override
    public List<TagObject> getTaskTags(String taskName) {
        return tagsResource.getTaskTags(taskName);
    }

    @Override
    public List<TagObject> getWorkflowTags(String name) {
        return tagsResource.getWorkflowTags(name);
    }

    @Override
    public void setTaskTags(List<TagObject> tagObjects, String taskName) {
        tagsResource.setTaskTags(tagObjects, taskName);
    }

    @Override
    public void setWorkflowTags(List<TagObject> tagObjects, String name) {
        tagsResource.setWorkflowTags(tagObjects, name);
    }

    @Override
    public List<WorkflowDef> getAllWorkflowDefs() {
        return metadataResource.getAllWorkflows(null, false, null, null);
    }
}
