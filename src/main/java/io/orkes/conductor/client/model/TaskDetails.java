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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** TaskDetails */
public class TaskDetails {
    @SerializedName("output")
    private Map<String, Object> output = null;

    @SerializedName("taskId")
    private String taskId = null;

    @SerializedName("taskRefName")
    private String taskRefName = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    public TaskDetails output(Map<String, Object> output) {
        this.output = output;
        return this;
    }

    public TaskDetails putOutputItem(String key, Object outputItem) {
        if (this.output == null) {
            this.output = new HashMap<String, Object>();
        }
        this.output.put(key, outputItem);
        return this;
    }

    /**
     * Get output
     *
     * @return output
     */
    @Schema(description = "")
    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }

    public TaskDetails taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    /**
     * Get taskId
     *
     * @return taskId
     */
    @Schema(description = "")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskDetails taskRefName(String taskRefName) {
        this.taskRefName = taskRefName;
        return this;
    }

    /**
     * Get taskRefName
     *
     * @return taskRefName
     */
    @Schema(description = "")
    public String getTaskRefName() {
        return taskRefName;
    }

    public void setTaskRefName(String taskRefName) {
        this.taskRefName = taskRefName;
    }

    public TaskDetails workflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    /**
     * Get workflowId
     *
     * @return workflowId
     */
    @Schema(description = "")
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskDetails taskDetails = (TaskDetails) o;
        return Objects.equals(this.output, taskDetails.output)
                && Objects.equals(this.taskId, taskDetails.taskId)
                && Objects.equals(this.taskRefName, taskDetails.taskRefName)
                && Objects.equals(this.workflowId, taskDetails.workflowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(output, taskId, taskRefName, workflowId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TaskDetails {\n");

        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
        sb.append("    taskRefName: ").append(toIndentedString(taskRefName)).append("\n");
        sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
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
