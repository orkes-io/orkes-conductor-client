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

import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

public abstract class MetadataClient extends com.netflix.conductor.client.http.MetadataClient {

    public abstract void registerWorkflowDef(WorkflowDef workflowDef, boolean overwrite);

    public abstract void updateWorkflowDefs(List<WorkflowDef> workflowDefs, boolean overwrite);

    public abstract void addTaskTag(TagObject tagObject, String taskName);

    public abstract void addWorkflowTag(TagObject tagObject, String name);

    public abstract void deleteTaskTag(TagString tagString, String taskName);

    public abstract void deleteWorkflowTag(TagObject tagObject, String name);

    public abstract List<TagObject> getTags();

    public abstract List<TagObject> getTaskTags(String taskName);

    public abstract List<TagObject> getWorkflowTags(String name);

    public abstract void setTaskTags(List<TagObject> tagObjects, String taskName);

    public abstract void setWorkflowTags(List<TagObject> tagObjects, String name);

    public abstract List<WorkflowDef> getAllWorkflowDefs();
}
