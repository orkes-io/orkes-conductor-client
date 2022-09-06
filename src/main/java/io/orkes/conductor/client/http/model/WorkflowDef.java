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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;

/** WorkflowDef */
public class WorkflowDef {
    @SerializedName("createTime")
    private Long createTime = null;

    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("description")
    private String description = null;

    @SerializedName("failureWorkflow")
    private String failureWorkflow = null;

    @SerializedName("inputParameters")
    private List<String> inputParameters = null;

    @SerializedName("inputTemplate")
    private Map<String, Object> inputTemplate = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("outputParameters")
    private Map<String, Object> outputParameters = null;

    @SerializedName("ownerApp")
    private String ownerApp = null;

    @SerializedName("ownerEmail")
    private String ownerEmail = null;

    @SerializedName("restartable")
    private Boolean restartable = null;

    @SerializedName("schemaVersion")
    private Integer schemaVersion = null;

    @SerializedName("tasks")
    private List<WorkflowTask> tasks = new ArrayList<WorkflowTask>();

    /** Gets or Sets timeoutPolicy */
    @JsonAdapter(TimeoutPolicyEnum.Adapter.class)
    public enum TimeoutPolicyEnum {
        TIME_OUT_WF("TIME_OUT_WF"),
        ALERT_ONLY("ALERT_ONLY");

        private String value;

        TimeoutPolicyEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static TimeoutPolicyEnum fromValue(String input) {
            for (TimeoutPolicyEnum b : TimeoutPolicyEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

        public static class Adapter extends TypeAdapter<TimeoutPolicyEnum> {
            @Override
            public void write(final JsonWriter jsonWriter, final TimeoutPolicyEnum enumeration)
                    throws IOException {
                jsonWriter.value(String.valueOf(enumeration.getValue()));
            }

            @Override
            public TimeoutPolicyEnum read(final JsonReader jsonReader) throws IOException {
                Object value = jsonReader.nextString();
                return TimeoutPolicyEnum.fromValue((String) (value));
            }
        }
    }

    @SerializedName("timeoutPolicy")
    private TimeoutPolicyEnum timeoutPolicy = null;

    @SerializedName("timeoutSeconds")
    private Long timeoutSeconds = null;

    @SerializedName("updateTime")
    private Long updateTime = null;

    @SerializedName("updatedBy")
    private String updatedBy = null;

    @SerializedName("variables")
    private Map<String, Object> variables = null;

    @SerializedName("version")
    private Integer version = null;

    @SerializedName("workflowStatusListenerEnabled")
    private Boolean workflowStatusListenerEnabled = null;

    public WorkflowDef createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * Get createTime
     *
     * @return createTime
     */
    @Schema(description = "")
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public WorkflowDef createdBy(String createdBy) {
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

    public WorkflowDef description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     */
    @Schema(description = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkflowDef failureWorkflow(String failureWorkflow) {
        this.failureWorkflow = failureWorkflow;
        return this;
    }

    /**
     * Get failureWorkflow
     *
     * @return failureWorkflow
     */
    @Schema(description = "")
    public String getFailureWorkflow() {
        return failureWorkflow;
    }

    public void setFailureWorkflow(String failureWorkflow) {
        this.failureWorkflow = failureWorkflow;
    }

    public WorkflowDef inputParameters(List<String> inputParameters) {
        this.inputParameters = inputParameters;
        return this;
    }

    public WorkflowDef addInputParametersItem(String inputParametersItem) {
        if (this.inputParameters == null) {
            this.inputParameters = new ArrayList<String>();
        }
        this.inputParameters.add(inputParametersItem);
        return this;
    }

    /**
     * Get inputParameters
     *
     * @return inputParameters
     */
    @Schema(description = "")
    public List<String> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<String> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public WorkflowDef inputTemplate(Map<String, Object> inputTemplate) {
        this.inputTemplate = inputTemplate;
        return this;
    }

    public WorkflowDef putInputTemplateItem(String key, Object inputTemplateItem) {
        if (this.inputTemplate == null) {
            this.inputTemplate = new HashMap<String, Object>();
        }
        this.inputTemplate.put(key, inputTemplateItem);
        return this;
    }

    /**
     * Get inputTemplate
     *
     * @return inputTemplate
     */
    @Schema(description = "")
    public Map<String, Object> getInputTemplate() {
        return inputTemplate;
    }

    public void setInputTemplate(Map<String, Object> inputTemplate) {
        this.inputTemplate = inputTemplate;
    }

    public WorkflowDef name(String name) {
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

    public WorkflowDef outputParameters(Map<String, Object> outputParameters) {
        this.outputParameters = outputParameters;
        return this;
    }

    public WorkflowDef putOutputParametersItem(String key, Object outputParametersItem) {
        if (this.outputParameters == null) {
            this.outputParameters = new HashMap<String, Object>();
        }
        this.outputParameters.put(key, outputParametersItem);
        return this;
    }

    /**
     * Get outputParameters
     *
     * @return outputParameters
     */
    @Schema(description = "")
    public Map<String, Object> getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(Map<String, Object> outputParameters) {
        this.outputParameters = outputParameters;
    }

    public WorkflowDef ownerApp(String ownerApp) {
        this.ownerApp = ownerApp;
        return this;
    }

    /**
     * Get ownerApp
     *
     * @return ownerApp
     */
    @Schema(description = "")
    public String getOwnerApp() {
        return ownerApp;
    }

    public void setOwnerApp(String ownerApp) {
        this.ownerApp = ownerApp;
    }

    public WorkflowDef ownerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    /**
     * Get ownerEmail
     *
     * @return ownerEmail
     */
    @Schema(description = "")
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public WorkflowDef restartable(Boolean restartable) {
        this.restartable = restartable;
        return this;
    }

    /**
     * Get restartable
     *
     * @return restartable
     */
    @Schema(description = "")
    public Boolean isRestartable() {
        return restartable;
    }

    public void setRestartable(Boolean restartable) {
        this.restartable = restartable;
    }

    public WorkflowDef schemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
        return this;
    }

    /**
     * Get schemaVersion minimum: 2 maximum: 2
     *
     * @return schemaVersion
     */
    @Schema(description = "")
    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public WorkflowDef tasks(List<WorkflowTask> tasks) {
        this.tasks = tasks;
        return this;
    }

    public WorkflowDef addTasksItem(WorkflowTask tasksItem) {
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     */
    @Schema(required = true, description = "")
    public List<WorkflowTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<WorkflowTask> tasks) {
        this.tasks = tasks;
    }

    public WorkflowDef timeoutPolicy(TimeoutPolicyEnum timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
        return this;
    }

    /**
     * Get timeoutPolicy
     *
     * @return timeoutPolicy
     */
    @Schema(description = "")
    public TimeoutPolicyEnum getTimeoutPolicy() {
        return timeoutPolicy;
    }

    public void setTimeoutPolicy(TimeoutPolicyEnum timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
    }

    public WorkflowDef timeoutSeconds(Long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
        return this;
    }

    /**
     * Get timeoutSeconds
     *
     * @return timeoutSeconds
     */
    @Schema(required = true, description = "")
    public Long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public WorkflowDef updateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * Get updateTime
     *
     * @return updateTime
     */
    @Schema(description = "")
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public WorkflowDef updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    /**
     * Get updatedBy
     *
     * @return updatedBy
     */
    @Schema(description = "")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public WorkflowDef variables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public WorkflowDef putVariablesItem(String key, Object variablesItem) {
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

    public WorkflowDef version(Integer version) {
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

    public WorkflowDef workflowStatusListenerEnabled(Boolean workflowStatusListenerEnabled) {
        this.workflowStatusListenerEnabled = workflowStatusListenerEnabled;
        return this;
    }

    /**
     * Get workflowStatusListenerEnabled
     *
     * @return workflowStatusListenerEnabled
     */
    @Schema(description = "")
    public Boolean isWorkflowStatusListenerEnabled() {
        return workflowStatusListenerEnabled;
    }

    public void setWorkflowStatusListenerEnabled(Boolean workflowStatusListenerEnabled) {
        this.workflowStatusListenerEnabled = workflowStatusListenerEnabled;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkflowDef workflowDef = (WorkflowDef) o;
        return Objects.equals(this.createTime, workflowDef.createTime)
                && Objects.equals(this.createdBy, workflowDef.createdBy)
                && Objects.equals(this.description, workflowDef.description)
                && Objects.equals(this.failureWorkflow, workflowDef.failureWorkflow)
                && Objects.equals(this.inputParameters, workflowDef.inputParameters)
                && Objects.equals(this.inputTemplate, workflowDef.inputTemplate)
                && Objects.equals(this.name, workflowDef.name)
                && Objects.equals(this.outputParameters, workflowDef.outputParameters)
                && Objects.equals(this.ownerApp, workflowDef.ownerApp)
                && Objects.equals(this.ownerEmail, workflowDef.ownerEmail)
                && Objects.equals(this.restartable, workflowDef.restartable)
                && Objects.equals(this.schemaVersion, workflowDef.schemaVersion)
                && Objects.equals(this.tasks, workflowDef.tasks)
                && Objects.equals(this.timeoutPolicy, workflowDef.timeoutPolicy)
                && Objects.equals(this.timeoutSeconds, workflowDef.timeoutSeconds)
                && Objects.equals(this.updateTime, workflowDef.updateTime)
                && Objects.equals(this.updatedBy, workflowDef.updatedBy)
                && Objects.equals(this.variables, workflowDef.variables)
                && Objects.equals(this.version, workflowDef.version)
                && Objects.equals(
                        this.workflowStatusListenerEnabled,
                        workflowDef.workflowStatusListenerEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                createTime,
                createdBy,
                description,
                failureWorkflow,
                inputParameters,
                inputTemplate,
                name,
                outputParameters,
                ownerApp,
                ownerEmail,
                restartable,
                schemaVersion,
                tasks,
                timeoutPolicy,
                timeoutSeconds,
                updateTime,
                updatedBy,
                variables,
                version,
                workflowStatusListenerEnabled);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkflowDef {\n");

        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    failureWorkflow: ").append(toIndentedString(failureWorkflow)).append("\n");
        sb.append("    inputParameters: ").append(toIndentedString(inputParameters)).append("\n");
        sb.append("    inputTemplate: ").append(toIndentedString(inputTemplate)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    outputParameters: ").append(toIndentedString(outputParameters)).append("\n");
        sb.append("    ownerApp: ").append(toIndentedString(ownerApp)).append("\n");
        sb.append("    ownerEmail: ").append(toIndentedString(ownerEmail)).append("\n");
        sb.append("    restartable: ").append(toIndentedString(restartable)).append("\n");
        sb.append("    schemaVersion: ").append(toIndentedString(schemaVersion)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
        sb.append("    timeoutPolicy: ").append(toIndentedString(timeoutPolicy)).append("\n");
        sb.append("    timeoutSeconds: ").append(toIndentedString(timeoutSeconds)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
        sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    workflowStatusListenerEnabled: ")
                .append(toIndentedString(workflowStatusListenerEnabled))
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
