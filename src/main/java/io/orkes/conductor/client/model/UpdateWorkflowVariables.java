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

/** UpdateWorkflowVariables */
public class UpdateWorkflowVariables {
    @SerializedName("appendArray")
    private Boolean appendArray = null;

    @SerializedName("variables")
    private Map<String, Object> variables = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    public UpdateWorkflowVariables appendArray(Boolean appendArray) {
        this.appendArray = appendArray;
        return this;
    }

    /**
     * Get appendArray
     *
     * @return appendArray
     */
    @Schema(description = "")
    public Boolean isAppendArray() {
        return appendArray;
    }

    public void setAppendArray(Boolean appendArray) {
        this.appendArray = appendArray;
    }

    public UpdateWorkflowVariables variables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public UpdateWorkflowVariables putVariablesItem(String key, Object variablesItem) {
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

    public UpdateWorkflowVariables workflowId(String workflowId) {
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
        UpdateWorkflowVariables updateWorkflowVariables = (UpdateWorkflowVariables) o;
        return Objects.equals(this.appendArray, updateWorkflowVariables.appendArray)
                && Objects.equals(this.variables, updateWorkflowVariables.variables)
                && Objects.equals(this.workflowId, updateWorkflowVariables.workflowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appendArray, variables, workflowId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateWorkflowVariables {\n");

        sb.append("    appendArray: ").append(toIndentedString(appendArray)).append("\n");
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
