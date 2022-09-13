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

import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** SubWorkflowParams */
public class SubWorkflowParams {
    @SerializedName("name")
    private String name = null;

    @SerializedName("taskToDomain")
    private Map<String, String> taskToDomain = null;

    @SerializedName("version")
    private Integer version = null;

    @SerializedName("workflowDefinition")
    private WorkflowDef workflowDefinition = null;

    public SubWorkflowParams name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    @Schema(required = true, description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubWorkflowParams taskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
        return this;
    }

    public SubWorkflowParams putTaskToDomainItem(String key, String taskToDomainItem) {
        if (this.taskToDomain == null) {
            this.taskToDomain = new HashMap<String, String>();
        }
        this.taskToDomain.put(key, taskToDomainItem);
        return this;
    }

    /**
     * Get taskToDomain
     *
     * @return taskToDomain
     */
    @Schema(description = "")
    public Map<String, String> getTaskToDomain() {
        return taskToDomain;
    }

    public void setTaskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
    }

    public SubWorkflowParams version(Integer version) {
        this.version = version;
        return this;
    }

    /**
     * Get version
     *
     * @return version
     */
    @Schema(description = "")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public SubWorkflowParams workflowDefinition(WorkflowDef workflowDefinition) {
        this.workflowDefinition = workflowDefinition;
        return this;
    }

    /**
     * Get workflowDefinition
     *
     * @return workflowDefinition
     */
    @Schema(description = "")
    public WorkflowDef getWorkflowDefinition() {
        return workflowDefinition;
    }

    public void setWorkflowDefinition(WorkflowDef workflowDefinition) {
        this.workflowDefinition = workflowDefinition;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubWorkflowParams subWorkflowParams = (SubWorkflowParams) o;
        return Objects.equals(this.name, subWorkflowParams.name)
                && Objects.equals(this.taskToDomain, subWorkflowParams.taskToDomain)
                && Objects.equals(this.version, subWorkflowParams.version)
                && Objects.equals(this.workflowDefinition, subWorkflowParams.workflowDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, taskToDomain, version, workflowDefinition);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SubWorkflowParams {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    taskToDomain: ").append(toIndentedString(taskToDomain)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    workflowDefinition: ")
                .append(toIndentedString(workflowDefinition))
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
