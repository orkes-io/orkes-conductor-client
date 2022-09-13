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

/** StartWorkflow */
public class StartWorkflow {
    @SerializedName("correlationId")
    private String correlationId = null;

    @SerializedName("input")
    private Map<String, Object> input = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("taskToDomain")
    private Map<String, String> taskToDomain = null;

    @SerializedName("version")
    private Integer version = null;

    public StartWorkflow correlationId(String correlationId) {
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

    public StartWorkflow input(Map<String, Object> input) {
        this.input = input;
        return this;
    }

    public StartWorkflow putInputItem(String key, Object inputItem) {
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

    public StartWorkflow name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    @Schema(description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StartWorkflow taskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
        return this;
    }

    public StartWorkflow putTaskToDomainItem(String key, String taskToDomainItem) {
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

    public StartWorkflow version(Integer version) {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StartWorkflow startWorkflow = (StartWorkflow) o;
        return Objects.equals(this.correlationId, startWorkflow.correlationId)
                && Objects.equals(this.input, startWorkflow.input)
                && Objects.equals(this.name, startWorkflow.name)
                && Objects.equals(this.taskToDomain, startWorkflow.taskToDomain)
                && Objects.equals(this.version, startWorkflow.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correlationId, input, name, taskToDomain, version);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StartWorkflow {\n");

        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    input: ").append(toIndentedString(input)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    taskToDomain: ").append(toIndentedString(taskToDomain)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
