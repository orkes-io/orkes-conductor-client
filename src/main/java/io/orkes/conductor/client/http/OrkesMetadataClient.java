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

import com.netflix.conductor.client.config.ConductorClientConfiguration;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import io.orkes.conductor.client.http.api.MetadataResourceApi;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;

import java.util.List;

public class OrkesMetadataClient extends OrkesClient implements MetadataClient  {

    private final MetadataResourceApi metadataResourceApi;

    public OrkesMetadataClient(ApiClient apiClient) {
        super(apiClient);
        this.metadataResourceApi = new MetadataResourceApi(apiClient);
    }

    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef) {
        metadataResourceApi.create(workflowDef, true);
    }

    public void registerWorkflowDef(WorkflowDef workflowDef, boolean overwrite) {
        metadataResourceApi.create(workflowDef, overwrite);
    }

    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs) {
        metadataResourceApi.update(workflowDefs, true);
    }

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

    }

    @Override
    public void registerTaskDefs(List<TaskDef> taskDefs) {

    }

    @Override
    public void updateTaskDef(TaskDef taskDef) {

    }

    @Override
    public TaskDef getTaskDef(String taskType) {
        return null;
    }

    @Override
    public void unregisterTaskDef(String taskType) {

    }
}
