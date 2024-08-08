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

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** SaveScheduleRequest */
public class SaveScheduleRequest {
    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("cronExpression")
    private String cronExpression = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("paused")
    private Boolean paused = null;

    @SerializedName("runCatchupScheduleInstances")
    private Boolean runCatchupScheduleInstances = null;

    @SerializedName("scheduleEndTime")
    private Long scheduleEndTime = null;

    @SerializedName("scheduleStartTime")
    private Long scheduleStartTime = null;

    @SerializedName("startWorkflowRequest")
    private StartWorkflowRequest startWorkflowRequest = null;

    @SerializedName("updatedBy")
    private String updatedBy = null;

    @SerializedName("zoneId") // This is just a leftover having used GSON.
    private String zoneId;

    public SaveScheduleRequest createdBy(String createdBy) {
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

    public SaveScheduleRequest cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    /**
     * Get cronExpression
     *
     * @return cronExpression
     */
    @Schema(required = true, description = "")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public SaveScheduleRequest name(String name) {
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

    public SaveScheduleRequest paused(Boolean paused) {
        this.paused = paused;
        return this;
    }

    /**
     * Get paused
     *
     * @return paused
     */
    @Schema(description = "")
    public Boolean isPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public SaveScheduleRequest runCatchupScheduleInstances(Boolean runCatchupScheduleInstances) {
        this.runCatchupScheduleInstances = runCatchupScheduleInstances;
        return this;
    }

    /**
     * Get runCatchupScheduleInstances
     *
     * @return runCatchupScheduleInstances
     */
    @Schema(description = "")
    public Boolean isRunCatchupScheduleInstances() {
        return runCatchupScheduleInstances;
    }

    public void setRunCatchupScheduleInstances(Boolean runCatchupScheduleInstances) {
        this.runCatchupScheduleInstances = runCatchupScheduleInstances;
    }

    public SaveScheduleRequest scheduleEndTime(Long scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
        return this;
    }

    /**
     * Get scheduleEndTime
     *
     * @return scheduleEndTime
     */
    @Schema(description = "")
    public Long getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(Long scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public SaveScheduleRequest scheduleStartTime(Long scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
        return this;
    }

    /**
     * Get scheduleStartTime
     *
     * @return scheduleStartTime
     */
    @Schema(description = "")
    public Long getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(Long scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public SaveScheduleRequest startWorkflowRequest(StartWorkflowRequest startWorkflowRequest) {
        this.startWorkflowRequest = startWorkflowRequest;
        return this;
    }

    /**
     * Get startWorkflowRequest
     *
     * @return startWorkflowRequest
     */
    @Schema(description = "")
    public StartWorkflowRequest getStartWorkflowRequest() {
        return startWorkflowRequest;
    }

    public void setStartWorkflowRequest(StartWorkflowRequest startWorkflowRequest) {
        this.startWorkflowRequest = startWorkflowRequest;
    }

    public SaveScheduleRequest updatedBy(String updatedBy) {
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

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public SaveScheduleRequest zoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SaveScheduleRequest saveScheduleRequest = (SaveScheduleRequest) o;
        return Objects.equals(this.createdBy, saveScheduleRequest.createdBy)
                && Objects.equals(this.cronExpression, saveScheduleRequest.cronExpression)
                && Objects.equals(this.name, saveScheduleRequest.name)
                && Objects.equals(this.paused, saveScheduleRequest.paused)
                && Objects.equals(
                        this.runCatchupScheduleInstances,
                        saveScheduleRequest.runCatchupScheduleInstances)
                && Objects.equals(this.scheduleEndTime, saveScheduleRequest.scheduleEndTime)
                && Objects.equals(this.scheduleStartTime, saveScheduleRequest.scheduleStartTime)
                && Objects.equals(
                        this.startWorkflowRequest, saveScheduleRequest.startWorkflowRequest)
                && Objects.equals(this.updatedBy, saveScheduleRequest.updatedBy)
                && Objects.equals(this.zoneId, saveScheduleRequest.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                createdBy,
                cronExpression,
                name,
                paused,
                runCatchupScheduleInstances,
                scheduleEndTime,
                scheduleStartTime,
                startWorkflowRequest,
                updatedBy,
                zoneId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SaveScheduleRequest {\n");

        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    cronExpression: ").append(toIndentedString(cronExpression)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    paused: ").append(toIndentedString(paused)).append("\n");
        sb.append("    runCatchupScheduleInstances: ")
                .append(toIndentedString(runCatchupScheduleInstances))
                .append("\n");
        sb.append("    scheduleEndTime: ").append(toIndentedString(scheduleEndTime)).append("\n");
        sb.append("    scheduleStartTime: ")
                .append(toIndentedString(scheduleStartTime))
                .append("\n");
        sb.append("    startWorkflowRequest: ")
                .append(toIndentedString(startWorkflowRequest))
                .append("\n");
        sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
        sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
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
