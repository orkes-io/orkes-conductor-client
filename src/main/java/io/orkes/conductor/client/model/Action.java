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
package io.orkes.conductor.client.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** Action */
public class Action {
    /** Gets or Sets action */
    public enum ActionEnum {
        START_WORKFLOW("start_workflow"),
        COMPLETE_TASK("complete_task"),
        FAIL_TASK("fail_task"),
        TERMINATE_WORKFLOW("terminate_workflow"),
        UPDATE_WORKFLOW_VARIABLES("update_workflow_variables");

        private String value;

        ActionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static ActionEnum fromValue(String input) {
            for (ActionEnum b : ActionEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }
    }

    @SerializedName("action")
    private ActionEnum action = null;

    @SerializedName("complete_task")
    private TaskDetails completeTask = null;

    @SerializedName("expandInlineJSON")
    private Boolean expandInlineJSON = null;

    @SerializedName("fail_task")
    private TaskDetails failTask = null;

    @SerializedName("start_workflow")
    private StartWorkflow startWorkflow = null;

    @SerializedName("terminate_workflow")
    private TerminateWorkflow terminateWorkflow = null;

    @SerializedName("update_workflow_variables")
    private UpdateWorkflowVariables updateWorkflowVariables = null;

    public Action action(ActionEnum action) {
        this.action = action;
        return this;
    }

    /**
     * Get action
     *
     * @return action
     */
    @Schema(description = "")
    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public Action completeTask(TaskDetails completeTask) {
        this.completeTask = completeTask;
        return this;
    }

    /**
     * Get completeTask
     *
     * @return completeTask
     */
    @Schema(description = "")
    public TaskDetails getCompleteTask() {
        return completeTask;
    }

    public void setCompleteTask(TaskDetails completeTask) {
        this.completeTask = completeTask;
    }

    public Action expandInlineJSON(Boolean expandInlineJSON) {
        this.expandInlineJSON = expandInlineJSON;
        return this;
    }

    /**
     * Get expandInlineJSON
     *
     * @return expandInlineJSON
     */
    @Schema(description = "")
    public Boolean isExpandInlineJSON() {
        return expandInlineJSON;
    }

    public void setExpandInlineJSON(Boolean expandInlineJSON) {
        this.expandInlineJSON = expandInlineJSON;
    }

    public Action failTask(TaskDetails failTask) {
        this.failTask = failTask;
        return this;
    }

    /**
     * Get failTask
     *
     * @return failTask
     */
    @Schema(description = "")
    public TaskDetails getFailTask() {
        return failTask;
    }

    public void setFailTask(TaskDetails failTask) {
        this.failTask = failTask;
    }

    public Action startWorkflow(StartWorkflow startWorkflow) {
        this.startWorkflow = startWorkflow;
        return this;
    }

    /**
     * Get startWorkflow
     *
     * @return startWorkflow
     */
    @Schema(description = "")
    public StartWorkflow getStartWorkflow() {
        return startWorkflow;
    }

    public void setStartWorkflow(StartWorkflow startWorkflow) {
        this.startWorkflow = startWorkflow;
    }

    public Action terminateWorkflow(TerminateWorkflow terminateWorkflow) {
        this.terminateWorkflow = terminateWorkflow;
        return this;
    }

    /**
     * Get terminateWorkflow
     *
     * @return terminateWorkflow
     */
    @Schema(description = "")
    public TerminateWorkflow getTerminateWorkflow() {
        return terminateWorkflow;
    }

    public void setTerminateWorkflow(TerminateWorkflow terminateWorkflow) {
        this.terminateWorkflow = terminateWorkflow;
    }

    public Action updateWorkflowVariables(UpdateWorkflowVariables updateWorkflowVariables) {
        this.updateWorkflowVariables = updateWorkflowVariables;
        return this;
    }

    /**
     * Get updateWorkflowVariables
     *
     * @return updateWorkflowVariables
     */
    @Schema(description = "")
    public UpdateWorkflowVariables getUpdateWorkflowVariables() {
        return updateWorkflowVariables;
    }

    public void setUpdateWorkflowVariables(UpdateWorkflowVariables updateWorkflowVariables) {
        this.updateWorkflowVariables = updateWorkflowVariables;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equals(this.action, action.action)
                && Objects.equals(this.completeTask, action.completeTask)
                && Objects.equals(this.expandInlineJSON, action.expandInlineJSON)
                && Objects.equals(this.failTask, action.failTask)
                && Objects.equals(this.startWorkflow, action.startWorkflow)
                && Objects.equals(this.terminateWorkflow, action.terminateWorkflow)
                && Objects.equals(this.updateWorkflowVariables, action.updateWorkflowVariables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                action,
                completeTask,
                expandInlineJSON,
                failTask,
                startWorkflow,
                terminateWorkflow,
                updateWorkflowVariables);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Action {\n");

        sb.append("    action: ").append(toIndentedString(action)).append("\n");
        sb.append("    completeTask: ").append(toIndentedString(completeTask)).append("\n");
        sb.append("    expandInlineJSON: ").append(toIndentedString(expandInlineJSON)).append("\n");
        sb.append("    failTask: ").append(toIndentedString(failTask)).append("\n");
        sb.append("    startWorkflow: ").append(toIndentedString(startWorkflow)).append("\n");
        sb.append("    terminateWorkflow: ")
                .append(toIndentedString(terminateWorkflow))
                .append("\n");
        sb.append("    updateWorkflowVariables: ")
                .append(toIndentedString(updateWorkflowVariables))
                .append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first
     * line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
