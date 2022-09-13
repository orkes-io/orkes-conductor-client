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

/** TerminateWorkflow */
public class TerminateWorkflow {
    @SerializedName("terminationReason")
    private String terminationReason = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    public TerminateWorkflow terminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
        return this;
    }

    /**
     * Get terminationReason
     *
     * @return terminationReason
     */
    @Schema(description = "")
    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public TerminateWorkflow workflowId(String workflowId) {
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
        TerminateWorkflow terminateWorkflow = (TerminateWorkflow) o;
        return Objects.equals(this.terminationReason, terminateWorkflow.terminationReason)
                && Objects.equals(this.workflowId, terminateWorkflow.workflowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminationReason, workflowId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TerminateWorkflow {\n");

        sb.append("    terminationReason: ")
                .append(toIndentedString(terminationReason))
                .append("\n");
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
