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
package io.orkes.conductor.sdk.workflow.def.tasks;

import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.tasks.TaskType;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowTask;

/** Workflow task executed by a worker */
public class SimpleTask extends Task<SimpleTask> {

    private TaskDef taskDef;

    public SimpleTask(String taskDefName, String taskReferenceName) {
        super(taskReferenceName, TaskType.SIMPLE);
        super.name(taskDefName);
    }

    SimpleTask(WorkflowTask workflowTask) {
        super(workflowTask);
        this.taskDef = workflowTask.getTaskDefinition();
    }

    public TaskDef getTaskDef() {
        return taskDef;
    }

    public SimpleTask setTaskDef(TaskDef taskDef) {
        this.taskDef = taskDef;
        return this;
    }

    @Override
    protected void updateWorkflowTask(WorkflowTask workflowTask) {
        workflowTask.setTaskDefinition(taskDef);
    }
}
