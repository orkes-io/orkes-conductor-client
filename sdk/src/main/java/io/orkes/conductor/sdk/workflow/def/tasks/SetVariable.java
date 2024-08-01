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

import io.orkes.conductor.client.model.metadata.tasks.TaskType;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowTask;
import io.orkes.conductor.sdk.workflow.def.WorkflowBuilder;

public class SetVariable extends Task<SetVariable> {
    /**
     * Sets the value of the variable in workflow. Used for workflow state management. Workflow
     * state is a Map that is initialized using @see {@link WorkflowBuilder#variables(Object)}
     *
     * @param taskReferenceName Use input methods to set the variable values
     */
    public SetVariable(String taskReferenceName) {
        super(taskReferenceName, TaskType.SET_VARIABLE);
    }

    SetVariable(WorkflowTask workflowTask) {
        super(workflowTask);
    }
}
