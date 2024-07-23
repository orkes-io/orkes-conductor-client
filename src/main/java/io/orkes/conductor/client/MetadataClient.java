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

import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;
import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowDef;

public interface MetadataClient {

    void registerWorkflowDef(WorkflowDef workflowDef);

    void registerWorkflowDef(WorkflowDef workflowDef, boolean overwrite);

    void updateWorkflowDefs(List<WorkflowDef> workflowDefs);

    void updateWorkflowDefs(List<WorkflowDef> workflowDefs, boolean overwrite);

    WorkflowDef getWorkflowDef(String name, Integer version);

    void unregisterWorkflowDef(String name, Integer version);

    List<TaskDef> getAllTaskDefs();

    void registerTaskDefs(List<TaskDef> taskDefs);

    void updateTaskDef(TaskDef taskDef);

    TaskDef getTaskDef(String taskType);

    void unregisterTaskDef(String taskType);

    void addTaskTag(TagObject tagObject, String taskName);

    void addWorkflowTag(TagObject tagObject, String name);

    void deleteTaskTag(TagString tagString, String taskName);

    void deleteWorkflowTag(TagObject tagObject, String name);

    List<TagObject> getTags();

    List<TagObject> getTaskTags(String taskName);

    List<TagObject> getWorkflowTags(String name);

    void setTaskTags(List<TagObject> tagObjects, String taskName);

    void setWorkflowTags(List<TagObject> tagObjects, String name);

    List<WorkflowDef> getAllWorkflowDefs();
}
