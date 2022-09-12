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
import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;

/** WorkflowSummary */
public class WorkflowSummary {
    @SerializedName("correlationId")
    private String correlationId = null;

    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("endTime")
    private String endTime = null;

    @SerializedName("event")
    private String event = null;

    @SerializedName("executionTime")
    private Long executionTime = null;

    @SerializedName("externalInputPayloadStoragePath")
    private String externalInputPayloadStoragePath = null;

    @SerializedName("externalOutputPayloadStoragePath")
    private String externalOutputPayloadStoragePath = null;

    @SerializedName("failedReferenceTaskNames")
    private String failedReferenceTaskNames = null;

    @SerializedName("input")
    private String input = null;

    @SerializedName("inputSize")
    private Long inputSize = null;

    @SerializedName("output")
    private String output = null;

    @SerializedName("outputSize")
    private Long outputSize = null;

    @SerializedName("priority")
    private Integer priority = null;

    @SerializedName("reasonForIncompletion")
    private String reasonForIncompletion = null;

    @SerializedName("startTime")
    private String startTime = null;

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

    @SerializedName("updateTime")
    private String updateTime = null;

    @SerializedName("version")
    private Integer version = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    @SerializedName("workflowType")
    private String workflowType = null;

    public WorkflowSummary correlationId(String correlationId) {
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

    public WorkflowSummary createdBy(String createdBy) {
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

    public WorkflowSummary endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get endTime
     *
     * @return endTime
     */
    @Schema(description = "")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public WorkflowSummary event(String event) {
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

    public WorkflowSummary executionTime(Long executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    /**
     * Get executionTime
     *
     * @return executionTime
     */
    @Schema(description = "")
    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public WorkflowSummary externalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
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

    public WorkflowSummary externalOutputPayloadStoragePath(
            String externalOutputPayloadStoragePath) {
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

    public WorkflowSummary failedReferenceTaskNames(String failedReferenceTaskNames) {
        this.failedReferenceTaskNames = failedReferenceTaskNames;
        return this;
    }

    /**
     * Get failedReferenceTaskNames
     *
     * @return failedReferenceTaskNames
     */
    @Schema(description = "")
    public String getFailedReferenceTaskNames() {
        return failedReferenceTaskNames;
    }

    public void setFailedReferenceTaskNames(String failedReferenceTaskNames) {
        this.failedReferenceTaskNames = failedReferenceTaskNames;
    }

    public WorkflowSummary input(String input) {
        this.input = input;
        return this;
    }

    /**
     * Get input
     *
     * @return input
     */
    @Schema(description = "")
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public WorkflowSummary inputSize(Long inputSize) {
        this.inputSize = inputSize;
        return this;
    }

    /**
     * Get inputSize
     *
     * @return inputSize
     */
    @Schema(description = "")
    public Long getInputSize() {
        return inputSize;
    }

    public void setInputSize(Long inputSize) {
        this.inputSize = inputSize;
    }

    public WorkflowSummary output(String output) {
        this.output = output;
        return this;
    }

    /**
     * Get output
     *
     * @return output
     */
    @Schema(description = "")
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public WorkflowSummary outputSize(Long outputSize) {
        this.outputSize = outputSize;
        return this;
    }

    /**
     * Get outputSize
     *
     * @return outputSize
     */
    @Schema(description = "")
    public Long getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(Long outputSize) {
        this.outputSize = outputSize;
    }

    public WorkflowSummary priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get priority
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

    public WorkflowSummary reasonForIncompletion(String reasonForIncompletion) {
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

    public WorkflowSummary startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     *
     * @return startTime
     */
    @Schema(description = "")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public WorkflowSummary status(StatusEnum status) {
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

    public WorkflowSummary updateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * Get updateTime
     *
     * @return updateTime
     */
    @Schema(description = "")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public WorkflowSummary version(Integer version) {
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

    public WorkflowSummary workflowId(String workflowId) {
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

    public WorkflowSummary workflowType(String workflowType) {
        this.workflowType = workflowType;
        return this;
    }

    /**
     * Get workflowType
     *
     * @return workflowType
     */
    @Schema(description = "")
    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkflowSummary workflowSummary = (WorkflowSummary) o;
        return Objects.equals(this.correlationId, workflowSummary.correlationId)
                && Objects.equals(this.createdBy, workflowSummary.createdBy)
                && Objects.equals(this.endTime, workflowSummary.endTime)
                && Objects.equals(this.event, workflowSummary.event)
                && Objects.equals(this.executionTime, workflowSummary.executionTime)
                && Objects.equals(
                        this.externalInputPayloadStoragePath,
                        workflowSummary.externalInputPayloadStoragePath)
                && Objects.equals(
                        this.externalOutputPayloadStoragePath,
                        workflowSummary.externalOutputPayloadStoragePath)
                && Objects.equals(
                        this.failedReferenceTaskNames, workflowSummary.failedReferenceTaskNames)
                && Objects.equals(this.input, workflowSummary.input)
                && Objects.equals(this.inputSize, workflowSummary.inputSize)
                && Objects.equals(this.output, workflowSummary.output)
                && Objects.equals(this.outputSize, workflowSummary.outputSize)
                && Objects.equals(this.priority, workflowSummary.priority)
                && Objects.equals(this.reasonForIncompletion, workflowSummary.reasonForIncompletion)
                && Objects.equals(this.startTime, workflowSummary.startTime)
                && Objects.equals(this.status, workflowSummary.status)
                && Objects.equals(this.updateTime, workflowSummary.updateTime)
                && Objects.equals(this.version, workflowSummary.version)
                && Objects.equals(this.workflowId, workflowSummary.workflowId)
                && Objects.equals(this.workflowType, workflowSummary.workflowType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                correlationId,
                createdBy,
                endTime,
                event,
                executionTime,
                externalInputPayloadStoragePath,
                externalOutputPayloadStoragePath,
                failedReferenceTaskNames,
                input,
                inputSize,
                output,
                outputSize,
                priority,
                reasonForIncompletion,
                startTime,
                status,
                updateTime,
                version,
                workflowId,
                workflowType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkflowSummary {\n");

        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
        sb.append("    event: ").append(toIndentedString(event)).append("\n");
        sb.append("    executionTime: ").append(toIndentedString(executionTime)).append("\n");
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
        sb.append("    inputSize: ").append(toIndentedString(inputSize)).append("\n");
        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    outputSize: ").append(toIndentedString(outputSize)).append("\n");
        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
        sb.append("    reasonForIncompletion: ")
                .append(toIndentedString(reasonForIncompletion))
                .append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
        sb.append("    workflowType: ").append(toIndentedString(workflowType)).append("\n");
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