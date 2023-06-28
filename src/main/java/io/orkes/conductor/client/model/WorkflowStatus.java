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

/** WorkflowStatus */
public class WorkflowStatus {
    @SerializedName("correlationId")
    private String correlationId = null;

    @SerializedName("output")
    private Map<String, Object> output = null;

    /** Gets or Sets status */
    public enum StatusEnum {
        RUNNING("RUNNING"),
        COMPLETED("COMPLETED"),
        FAILED("FAILED"),
        TIMED_OUT("TIMED_OUT"),
        TERMINATED("TERMINATED"),
        PAUSED("PAUSED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static StatusEnum fromValue(String input) {
            for (StatusEnum b : StatusEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }
    }

    @SerializedName("status")
    private StatusEnum status = null;

    @SerializedName("variables")
    private Map<String, Object> variables = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    public WorkflowStatus correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    /**
     * Get correlationId
     *
     * @return correlationId
     */
    @Schema(description = "")
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public WorkflowStatus output(Map<String, Object> output) {
        this.output = output;
        return this;
    }

    public WorkflowStatus putOutputItem(String key, Object outputItem) {
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

    public WorkflowStatus status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     */
    @Schema(description = "")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public WorkflowStatus variables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public WorkflowStatus putVariablesItem(String key, Object variablesItem) {
        if (this.variables == null) {
            this.variables = new HashMap<String, Object>();
        }
        this.variables.put(key, variablesItem);
        return this;
    }

    /**
     * Get variables
     *
     * @return variables
     */
    @Schema(description = "")
    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public WorkflowStatus workflowId(String workflowId) {
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
        WorkflowStatus workflowStatus = (WorkflowStatus) o;
        return Objects.equals(this.correlationId, workflowStatus.correlationId)
                && Objects.equals(this.output, workflowStatus.output)
                && Objects.equals(this.status, workflowStatus.status)
                && Objects.equals(this.variables, workflowStatus.variables)
                && Objects.equals(this.workflowId, workflowStatus.workflowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correlationId, output, status, variables, workflowId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkflowStatus {\n");

        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
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
