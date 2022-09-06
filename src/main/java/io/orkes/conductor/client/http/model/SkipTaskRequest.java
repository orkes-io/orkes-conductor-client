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
package io.orkes.conductor.client.http.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** SkipTaskRequest */
public class SkipTaskRequest {
    @SerializedName("taskInput")
    private Map<String, Object> taskInput = null;

    @SerializedName("taskOutput")
    private Map<String, Object> taskOutput = null;

    public SkipTaskRequest taskInput(Map<String, Object> taskInput) {
        this.taskInput = taskInput;
        return this;
    }

    public SkipTaskRequest putTaskInputItem(String key, Object taskInputItem) {
        if (this.taskInput == null) {
            this.taskInput = new HashMap<String, Object>();
        }
        this.taskInput.put(key, taskInputItem);
        return this;
    }

    /**
     * Get taskInput
     *
     * @return taskInput
     */
    @Schema(description = "")
    public Map<String, Object> getTaskInput() {
        return taskInput;
    }

    public void setTaskInput(Map<String, Object> taskInput) {
        this.taskInput = taskInput;
    }

    public SkipTaskRequest taskOutput(Map<String, Object> taskOutput) {
        this.taskOutput = taskOutput;
        return this;
    }

    public SkipTaskRequest putTaskOutputItem(String key, Object taskOutputItem) {
        if (this.taskOutput == null) {
            this.taskOutput = new HashMap<String, Object>();
        }
        this.taskOutput.put(key, taskOutputItem);
        return this;
    }

    /**
     * Get taskOutput
     *
     * @return taskOutput
     */
    @Schema(description = "")
    public Map<String, Object> getTaskOutput() {
        return taskOutput;
    }

    public void setTaskOutput(Map<String, Object> taskOutput) {
        this.taskOutput = taskOutput;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkipTaskRequest skipTaskRequest = (SkipTaskRequest) o;
        return Objects.equals(this.taskInput, skipTaskRequest.taskInput)
                && Objects.equals(this.taskOutput, skipTaskRequest.taskOutput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskInput, taskOutput);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkipTaskRequest {\n");

        sb.append("    taskInput: ").append(toIndentedString(taskInput)).append("\n");
        sb.append("    taskOutput: ").append(toIndentedString(taskOutput)).append("\n");
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
