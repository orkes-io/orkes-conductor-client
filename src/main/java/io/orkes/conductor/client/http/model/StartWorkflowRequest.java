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

/** StartWorkflowRequest */
public class StartWorkflowRequest {
    @SerializedName("correlationId")
    private String correlationId = null;

    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("externalInputPayloadStoragePath")
    private String externalInputPayloadStoragePath = null;

    @SerializedName("input")
    private Map<String, Object> input = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("priority")
    private Integer priority = null;

    @SerializedName("taskToDomain")
    private Map<String, String> taskToDomain = null;

    @SerializedName("version")
    private Integer version = null;

    @SerializedName("workflowDef")
    private WorkflowDef workflowDef = null;

    public StartWorkflowRequest correlationId(String correlationId) {
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

    public StartWorkflowRequest createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get createdBy
     *
     * @return createdBy
     */
    @Schema(description = "")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public StartWorkflowRequest externalInputPayloadStoragePath(
            String externalInputPayloadStoragePath) {
        this.externalInputPayloadStoragePath = externalInputPayloadStoragePath;
        return this;
    }

    /**
     * Get externalInputPayloadStoragePath
     *
     * @return externalInputPayloadStoragePath
     */
    @Schema(description = "")
    public String getExternalInputPayloadStoragePath() {
        return externalInputPayloadStoragePath;
    }

    public void setExternalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
        this.externalInputPayloadStoragePath = externalInputPayloadStoragePath;
    }

    public StartWorkflowRequest input(Map<String, Object> input) {
        this.input = input;
        return this;
    }

    public StartWorkflowRequest putInputItem(String key, Object inputItem) {
        if (this.input == null) {
            this.input = new HashMap<String, Object>();
        }
        this.input.put(key, inputItem);
        return this;
    }

    /**
     * Get input
     *
     * @return input
     */
    @Schema(description = "")
    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public StartWorkflowRequest name(String name) {
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

    public StartWorkflowRequest priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get priority minimum: 0 maximum: 99
     *
     * @return priority
     */
    @Schema(description = "")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public StartWorkflowRequest taskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
        return this;
    }

    public StartWorkflowRequest putTaskToDomainItem(String key, String taskToDomainItem) {
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

    public StartWorkflowRequest version(Integer version) {
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

    public StartWorkflowRequest workflowDef(WorkflowDef workflowDef) {
        this.workflowDef = workflowDef;
        return this;
    }

    /**
     * Get workflowDef
     *
     * @return workflowDef
     */
    @Schema(description = "")
    public WorkflowDef getWorkflowDef() {
        return workflowDef;
    }

    public void setWorkflowDef(WorkflowDef workflowDef) {
        this.workflowDef = workflowDef;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StartWorkflowRequest startWorkflowRequest = (StartWorkflowRequest) o;
        return Objects.equals(this.correlationId, startWorkflowRequest.correlationId)
                && Objects.equals(this.createdBy, startWorkflowRequest.createdBy)
                && Objects.equals(
                        this.externalInputPayloadStoragePath,
                        startWorkflowRequest.externalInputPayloadStoragePath)
                && Objects.equals(this.input, startWorkflowRequest.input)
                && Objects.equals(this.name, startWorkflowRequest.name)
                && Objects.equals(this.priority, startWorkflowRequest.priority)
                && Objects.equals(this.taskToDomain, startWorkflowRequest.taskToDomain)
                && Objects.equals(this.version, startWorkflowRequest.version)
                && Objects.equals(this.workflowDef, startWorkflowRequest.workflowDef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                correlationId,
                createdBy,
                externalInputPayloadStoragePath,
                input,
                name,
                priority,
                taskToDomain,
                version,
                workflowDef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StartWorkflowRequest {\n");

        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    externalInputPayloadStoragePath: ")
                .append(toIndentedString(externalInputPayloadStoragePath))
                .append("\n");
        sb.append("    input: ").append(toIndentedString(input)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
        sb.append("    taskToDomain: ").append(toIndentedString(taskToDomain)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    workflowDef: ").append(toIndentedString(workflowDef)).append("\n");
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
