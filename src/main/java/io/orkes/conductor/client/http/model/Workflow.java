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

import com.netflix.conductor.common.metadata.tasks.Task;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;

/** Workflow */
public class Workflow {
    @SerializedName("correlationId")
    private String correlationId = null;

    @SerializedName("createTime")
    private Long createTime = null;

    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("endTime")
    private Long endTime = null;

    @SerializedName("event")
    private String event = null;

    @SerializedName("externalInputPayloadStoragePath")
    private String externalInputPayloadStoragePath = null;

    @SerializedName("externalOutputPayloadStoragePath")
    private String externalOutputPayloadStoragePath = null;

    @SerializedName("failedReferenceTaskNames")
    private List<String> failedReferenceTaskNames = null;

    @SerializedName("input")
    private Map<String, Object> input = null;

    @SerializedName("lastRetriedTime")
    private Long lastRetriedTime = null;

    @SerializedName("output")
    private Map<String, Object> output = null;

    @SerializedName("ownerApp")
    private String ownerApp = null;

    @SerializedName("parentWorkflowId")
    private String parentWorkflowId = null;

    @SerializedName("parentWorkflowTaskId")
    private String parentWorkflowTaskId = null;

    @SerializedName("priority")
    private Integer priority = null;

    @SerializedName("reRunFromWorkflowId")
    private String reRunFromWorkflowId = null;

    @SerializedName("reasonForIncompletion")
    private String reasonForIncompletion = null;

    @SerializedName("startTime")
    private Long startTime = null;

    /** Gets or Sets status */
    @JsonAdapter(StatusEnum.Adapter.class)
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

        public static class Adapter extends TypeAdapter<StatusEnum> {
            @Override
            public void write(final JsonWriter jsonWriter, final StatusEnum enumeration)
                    throws IOException {
                jsonWriter.value(String.valueOf(enumeration.getValue()));
            }

            @Override
            public StatusEnum read(final JsonReader jsonReader) throws IOException {
                Object value = jsonReader.nextString();
                return StatusEnum.fromValue((String) (value));
            }
        }
    }

    @SerializedName("status")
    private StatusEnum status = null;

    @SerializedName("taskToDomain")
    private Map<String, String> taskToDomain = null;

    @SerializedName("tasks")
    private List<Task> tasks = null;

    @SerializedName("updateTime")
    private Long updateTime = null;

    @SerializedName("updatedBy")
    private String updatedBy = null;

    @SerializedName("variables")
    private Map<String, Object> variables = null;

    @SerializedName("workflowDefinition")
    private WorkflowDef workflowDefinition = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    @SerializedName("workflowName")
    private String workflowName = null;

    @SerializedName("workflowVersion")
    private Integer workflowVersion = null;

    public Workflow correlationId(String correlationId) {
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

    public Workflow createTime(Long createTime) {
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

    public Workflow createdBy(String createdBy) {
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

    public Workflow endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get endTime
     *
     * @return endTime
     */
    @Schema(description = "")
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Workflow event(String event) {
        this.event = event;
        return this;
    }

    /**
     * Get event
     *
     * @return event
     */
    @Schema(description = "")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Workflow externalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
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

    public Workflow externalOutputPayloadStoragePath(String externalOutputPayloadStoragePath) {
        this.externalOutputPayloadStoragePath = externalOutputPayloadStoragePath;
        return this;
    }

    /**
     * Get externalOutputPayloadStoragePath
     *
     * @return externalOutputPayloadStoragePath
     */
    @Schema(description = "")
    public String getExternalOutputPayloadStoragePath() {
        return externalOutputPayloadStoragePath;
    }

    public void setExternalOutputPayloadStoragePath(String externalOutputPayloadStoragePath) {
        this.externalOutputPayloadStoragePath = externalOutputPayloadStoragePath;
    }

    public Workflow failedReferenceTaskNames(List<String> failedReferenceTaskNames) {
        this.failedReferenceTaskNames = failedReferenceTaskNames;
        return this;
    }

    public Workflow addFailedReferenceTaskNamesItem(String failedReferenceTaskNamesItem) {
        if (this.failedReferenceTaskNames == null) {
            this.failedReferenceTaskNames = new ArrayList<String>();
        }
        this.failedReferenceTaskNames.add(failedReferenceTaskNamesItem);
        return this;
    }

    /**
     * Get failedReferenceTaskNames
     *
     * @return failedReferenceTaskNames
     */
    @Schema(description = "")
    public List<String> getFailedReferenceTaskNames() {
        return failedReferenceTaskNames;
    }

    public void setFailedReferenceTaskNames(List<String> failedReferenceTaskNames) {
        this.failedReferenceTaskNames = failedReferenceTaskNames;
    }

    public Workflow input(Map<String, Object> input) {
        this.input = input;
        return this;
    }

    public Workflow putInputItem(String key, Object inputItem) {
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

    public Workflow lastRetriedTime(Long lastRetriedTime) {
        this.lastRetriedTime = lastRetriedTime;
        return this;
    }

    /**
     * Get lastRetriedTime
     *
     * @return lastRetriedTime
     */
    @Schema(description = "")
    public Long getLastRetriedTime() {
        return lastRetriedTime;
    }

    public void setLastRetriedTime(Long lastRetriedTime) {
        this.lastRetriedTime = lastRetriedTime;
    }

    public Workflow output(Map<String, Object> output) {
        this.output = output;
        return this;
    }

    public Workflow putOutputItem(String key, Object outputItem) {
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

    public Workflow ownerApp(String ownerApp) {
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

    public Workflow parentWorkflowId(String parentWorkflowId) {
        this.parentWorkflowId = parentWorkflowId;
        return this;
    }

    /**
     * Get parentWorkflowId
     *
     * @return parentWorkflowId
     */
    @Schema(description = "")
    public String getParentWorkflowId() {
        return parentWorkflowId;
    }

    public void setParentWorkflowId(String parentWorkflowId) {
        this.parentWorkflowId = parentWorkflowId;
    }

    public Workflow parentWorkflowTaskId(String parentWorkflowTaskId) {
        this.parentWorkflowTaskId = parentWorkflowTaskId;
        return this;
    }

    /**
     * Get parentWorkflowTaskId
     *
     * @return parentWorkflowTaskId
     */
    @Schema(description = "")
    public String getParentWorkflowTaskId() {
        return parentWorkflowTaskId;
    }

    public void setParentWorkflowTaskId(String parentWorkflowTaskId) {
        this.parentWorkflowTaskId = parentWorkflowTaskId;
    }

    public Workflow priority(Integer priority) {
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

    public Workflow reRunFromWorkflowId(String reRunFromWorkflowId) {
        this.reRunFromWorkflowId = reRunFromWorkflowId;
        return this;
    }

    /**
     * Get reRunFromWorkflowId
     *
     * @return reRunFromWorkflowId
     */
    @Schema(description = "")
    public String getReRunFromWorkflowId() {
        return reRunFromWorkflowId;
    }

    public void setReRunFromWorkflowId(String reRunFromWorkflowId) {
        this.reRunFromWorkflowId = reRunFromWorkflowId;
    }

    public Workflow reasonForIncompletion(String reasonForIncompletion) {
        this.reasonForIncompletion = reasonForIncompletion;
        return this;
    }

    /**
     * Get reasonForIncompletion
     *
     * @return reasonForIncompletion
     */
    @Schema(description = "")
    public String getReasonForIncompletion() {
        return reasonForIncompletion;
    }

    public void setReasonForIncompletion(String reasonForIncompletion) {
        this.reasonForIncompletion = reasonForIncompletion;
    }

    public Workflow startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     *
     * @return startTime
     */
    @Schema(description = "")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Workflow status(StatusEnum status) {
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

    public Workflow taskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
        return this;
    }

    public Workflow putTaskToDomainItem(String key, String taskToDomainItem) {
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

    public Workflow tasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Workflow addTasksItem(Task tasksItem) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<Task>();
        }
        this.tasks.add(tasksItem);
        return this;
    }

    /**
     * Get tasks
     *
     * @return tasks
     */
    @Schema(description = "")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Workflow updateTime(Long updateTime) {
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

    public Workflow updatedBy(String updatedBy) {
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

    public Workflow variables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public Workflow putVariablesItem(String key, Object variablesItem) {
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

    public Workflow workflowDefinition(WorkflowDef workflowDefinition) {
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

    public Workflow workflowId(String workflowId) {
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

    public Workflow workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    /**
     * Get workflowName
     *
     * @return workflowName
     */
    @Schema(description = "")
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public Workflow workflowVersion(Integer workflowVersion) {
        this.workflowVersion = workflowVersion;
        return this;
    }

    /**
     * Get workflowVersion
     *
     * @return workflowVersion
     */
    @Schema(description = "")
    public Integer getWorkflowVersion() {
        return workflowVersion;
    }

    public void setWorkflowVersion(Integer workflowVersion) {
        this.workflowVersion = workflowVersion;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workflow workflow = (Workflow) o;
        return Objects.equals(this.correlationId, workflow.correlationId)
                && Objects.equals(this.createTime, workflow.createTime)
                && Objects.equals(this.createdBy, workflow.createdBy)
                && Objects.equals(this.endTime, workflow.endTime)
                && Objects.equals(this.event, workflow.event)
                && Objects.equals(
                        this.externalInputPayloadStoragePath,
                        workflow.externalInputPayloadStoragePath)
                && Objects.equals(
                        this.externalOutputPayloadStoragePath,
                        workflow.externalOutputPayloadStoragePath)
                && Objects.equals(this.failedReferenceTaskNames, workflow.failedReferenceTaskNames)
                && Objects.equals(this.input, workflow.input)
                && Objects.equals(this.lastRetriedTime, workflow.lastRetriedTime)
                && Objects.equals(this.output, workflow.output)
                && Objects.equals(this.ownerApp, workflow.ownerApp)
                && Objects.equals(this.parentWorkflowId, workflow.parentWorkflowId)
                && Objects.equals(this.parentWorkflowTaskId, workflow.parentWorkflowTaskId)
                && Objects.equals(this.priority, workflow.priority)
                && Objects.equals(this.reRunFromWorkflowId, workflow.reRunFromWorkflowId)
                && Objects.equals(this.reasonForIncompletion, workflow.reasonForIncompletion)
                && Objects.equals(this.startTime, workflow.startTime)
                && Objects.equals(this.status, workflow.status)
                && Objects.equals(this.taskToDomain, workflow.taskToDomain)
                && Objects.equals(this.tasks, workflow.tasks)
                && Objects.equals(this.updateTime, workflow.updateTime)
                && Objects.equals(this.updatedBy, workflow.updatedBy)
                && Objects.equals(this.variables, workflow.variables)
                && Objects.equals(this.workflowDefinition, workflow.workflowDefinition)
                && Objects.equals(this.workflowId, workflow.workflowId)
                && Objects.equals(this.workflowName, workflow.workflowName)
                && Objects.equals(this.workflowVersion, workflow.workflowVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                correlationId,
                createTime,
                createdBy,
                endTime,
                event,
                externalInputPayloadStoragePath,
                externalOutputPayloadStoragePath,
                failedReferenceTaskNames,
                input,
                lastRetriedTime,
                output,
                ownerApp,
                parentWorkflowId,
                parentWorkflowTaskId,
                priority,
                reRunFromWorkflowId,
                reasonForIncompletion,
                startTime,
                status,
                taskToDomain,
                tasks,
                updateTime,
                updatedBy,
                variables,
                workflowDefinition,
                workflowId,
                workflowName,
                workflowVersion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Workflow {\n");

        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
        sb.append("    event: ").append(toIndentedString(event)).append("\n");
        sb.append("    externalInputPayloadStoragePath: ")
                .append(toIndentedString(externalInputPayloadStoragePath))
                .append("\n");
        sb.append("    externalOutputPayloadStoragePath: ")
                .append(toIndentedString(externalOutputPayloadStoragePath))
                .append("\n");
        sb.append("    failedReferenceTaskNames: ")
                .append(toIndentedString(failedReferenceTaskNames))
                .append("\n");
        sb.append("    input: ").append(toIndentedString(input)).append("\n");
        sb.append("    lastRetriedTime: ").append(toIndentedString(lastRetriedTime)).append("\n");
        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    ownerApp: ").append(toIndentedString(ownerApp)).append("\n");
        sb.append("    parentWorkflowId: ").append(toIndentedString(parentWorkflowId)).append("\n");
        sb.append("    parentWorkflowTaskId: ")
                .append(toIndentedString(parentWorkflowTaskId))
                .append("\n");
        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
        sb.append("    reRunFromWorkflowId: ")
                .append(toIndentedString(reRunFromWorkflowId))
                .append("\n");
        sb.append("    reasonForIncompletion: ")
                .append(toIndentedString(reasonForIncompletion))
                .append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    taskToDomain: ").append(toIndentedString(taskToDomain)).append("\n");
        sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
        sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
        sb.append("    workflowDefinition: ")
                .append(toIndentedString(workflowDefinition))
                .append("\n");
        sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
        sb.append("    workflowName: ").append(toIndentedString(workflowName)).append("\n");
        sb.append("    workflowVersion: ").append(toIndentedString(workflowVersion)).append("\n");
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