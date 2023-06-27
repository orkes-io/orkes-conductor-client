/*
 * Copyright 2020 Orkes, Inc.
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
package com.netflix.conductor.client.http;

import java.util.List;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

public abstract class MetadataClient {

    /** Creates a default metadata client */
    public MetadataClient() {

    }


    // Workflow Metadata Operations

    /**
     * Register a workflow definition with the server
     *
     * @param workflowDef the workflow definition
     */
    public abstract void registerWorkflowDef(WorkflowDef workflowDef);

    /**
     * Updates a list of existing workflow definitions
     *
     * @param workflowDefs List of workflow definitions to be updated
     */
    public abstract void updateWorkflowDefs(List<WorkflowDef> workflowDefs);

    /**
     * Retrieve the workflow definition
     *
     * @param name the name of the workflow
     * @param version the version of the workflow def
     * @return Workflow definition for the given workflow and version
     */
    public abstract WorkflowDef getWorkflowDef(String name, Integer version);

    /**
     * Removes the workflow definition of a workflow from the conductor server. It does not remove
     * associated workflows. Use with caution.
     *
     * @param name Name of the workflow to be unregistered.
     * @param version Version of the workflow definition to be unregistered.
     */
    public abstract void unregisterWorkflowDef(String name, Integer version);

    // Task Metadata Operations

    /**
     * Registers a list of task types with the conductor server
     *
     * @param taskDefs List of task types to be registered.
     */
    public abstract void registerTaskDefs(List<TaskDef> taskDefs);

    /**
     * Updates an existing task definition
     *
     * @param taskDef the task definition to be updated
     */
    public abstract void updateTaskDef(TaskDef taskDef);

    /**
     * Retrieve the task definition of a given task type
     *
     * @param taskType type of task for which to retrieve the definition
     * @return Task Definition for the given task type
     */
    public abstract TaskDef getTaskDef(String taskType);

    /**
     * Removes the task definition of a task type from the conductor server. Use with caution.
     *
     * @param taskType Task type to be unregistered.
     */
    public abstract void unregisterTaskDef(String taskType);

    /**
     *
     * @return All the registered task definitions
     */
    public abstract List<TaskDef> getAllTaskDefs();
}
