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

import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;



/** WorkflowScheduleExecutionModel */
public class WorkflowScheduleExecutionModel {

    private String executionId = null;


    private Long executionTime = null;


    private String reason = null;


    private String scheduleName = null;


    private Long scheduledTime = null;


    private String stackTrace = null;


    private StartWorkflowRequest startWorkflowRequest = null;

    /** Gets or Sets state */
    public enum StateEnum {
        POLLED("POLLED"),
        FAILED("FAILED"),
        EXECUTED("EXECUTED");

        private String value;

        StateEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static StateEnum fromValue(String input) {
            for (StateEnum b : StateEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }
    }


    private StateEnum state = null;


    private String workflowId = null;


    private String workflowName = null;

    public WorkflowScheduleExecutionModel executionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    /**
     * Get executionId
     *
     * @return executionId
     */
    
    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public WorkflowScheduleExecutionModel executionTime(Long executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    /**
     * Get executionTime
     *
     * @return executionTime
     */
    
    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public WorkflowScheduleExecutionModel reason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * Get reason
     *
     * @return reason
     */
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WorkflowScheduleExecutionModel scheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
        return this;
    }

    /**
     * Get scheduleName
     *
     * @return scheduleName
     */
    
    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public WorkflowScheduleExecutionModel scheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
        return this;
    }

    /**
     * Get scheduledTime
     *
     * @return scheduledTime
     */
    
    public Long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public WorkflowScheduleExecutionModel stackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    /**
     * Get stackTrace
     *
     * @return stackTrace
     */
    
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public WorkflowScheduleExecutionModel startWorkflowRequest(
            StartWorkflowRequest startWorkflowRequest) {
        this.startWorkflowRequest = startWorkflowRequest;
        return this;
    }

    /**
     * Get startWorkflowRequest
     *
     * @return startWorkflowRequest
     */
    
    public StartWorkflowRequest getStartWorkflowRequest() {
        return startWorkflowRequest;
    }

    public void setStartWorkflowRequest(StartWorkflowRequest startWorkflowRequest) {
        this.startWorkflowRequest = startWorkflowRequest;
    }

    public WorkflowScheduleExecutionModel state(StateEnum state) {
        this.state = state;
        return this;
    }

    /**
     * Get state
     *
     * @return state
     */
    
    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public WorkflowScheduleExecutionModel workflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    /**
     * Get workflowId
     *
     * @return workflowId
     */
    
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public WorkflowScheduleExecutionModel workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    /**
     * Get workflowName
     *
     * @return workflowName
     */
    
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkflowScheduleExecutionModel workflowScheduleExecutionModel =
                (WorkflowScheduleExecutionModel) o;
        return Objects.equals(this.executionId, workflowScheduleExecutionModel.executionId)
                && Objects.equals(this.executionTime, workflowScheduleExecutionModel.executionTime)
                && Objects.equals(this.reason, workflowScheduleExecutionModel.reason)
                && Objects.equals(this.scheduleName, workflowScheduleExecutionModel.scheduleName)
                && Objects.equals(this.scheduledTime, workflowScheduleExecutionModel.scheduledTime)
                && Objects.equals(this.stackTrace, workflowScheduleExecutionModel.stackTrace)
                && Objects.equals(
                        this.startWorkflowRequest,
                        workflowScheduleExecutionModel.startWorkflowRequest)
                && Objects.equals(this.state, workflowScheduleExecutionModel.state)
                && Objects.equals(this.workflowId, workflowScheduleExecutionModel.workflowId)
                && Objects.equals(this.workflowName, workflowScheduleExecutionModel.workflowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                executionId,
                executionTime,
                reason,
                scheduleName,
                scheduledTime,
                stackTrace,
                startWorkflowRequest,
                state,
                workflowId,
                workflowName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkflowScheduleExecutionModel {\n");

        sb.append("    executionId: ").append(toIndentedString(executionId)).append("\n");
        sb.append("    executionTime: ").append(toIndentedString(executionTime)).append("\n");
        sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
        sb.append("    scheduleName: ").append(toIndentedString(scheduleName)).append("\n");
        sb.append("    scheduledTime: ").append(toIndentedString(scheduledTime)).append("\n");
        sb.append("    stackTrace: ").append(toIndentedString(stackTrace)).append("\n");
        sb.append("    startWorkflowRequest: ")
                .append(toIndentedString(startWorkflowRequest))
                .append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
        sb.append("    workflowName: ").append(toIndentedString(workflowName)).append("\n");
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
