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
package io.orkes.conductor.client;

import java.util.List;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

public interface MetadataClient {
    void registerWorkflowDef(WorkflowDef workflowDef);

    void updateWorkflowDefs(List<WorkflowDef> workflowDefs);

    WorkflowDef getWorkflowDef(String name, Integer version);

    void unregisterWorkflowDef(String name, Integer version);

    void registerTaskDefs(List<TaskDef> taskDefs);

    void updateTaskDef(TaskDef taskDef);

    TaskDef getTaskDef(String taskType);

    void unregisterTaskDef(String taskType);

    void addTaskTag(TagObject body, String taskName) throws ApiException;

    void addWorkflowTag(TagObject body, String name) throws ApiException;

    void deleteTaskTag(TagString body, String taskName) throws ApiException;

    void deleteWorkflowTag(TagObject body, String name) throws ApiException;

    List<TagObject> getTags() throws ApiException;

    List<TagObject> getTaskTags(String taskName) throws ApiException;

    List<TagObject> getWorkflowTags(String name) throws ApiException;

    void setTaskTags(List<TagObject> body, String taskName) throws ApiException;

    void setWorkflowTags(List<TagObject> body, String name) throws ApiException;
}
