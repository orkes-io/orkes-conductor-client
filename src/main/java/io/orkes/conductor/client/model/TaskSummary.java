/*
 * Copyright 2020 Orkes, Inc.
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.Task.Status;
import com.netflix.conductor.common.utils.SummaryUtil;

public class TaskSummary {

    /** The time should be stored as GMT */
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    private String workflowId;

    private String workflowType;

    private String correlationId;

    private String scheduledTime;

    private String startTime;

    private String updateTime;

    private String endTime;

    private Task.Status status;

    private String reasonForIncompletion;

    private long executionTime;

    private long queueWaitTime;

    private String taskDefName;

    private String taskType;

    private String input;

    private String output;

    private String taskId;

    private String externalInputPayloadStoragePath;

    private String externalOutputPayloadStoragePath;

    private int workflowPriority;

    private String taskReferenceName;

    public TaskSummary() {}

    public TaskSummary(Task task) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(GMT);

        this.taskId = task.getTaskId();
        this.taskDefName = task.getTaskDefName();
        this.taskType = task.getTaskType();
        this.workflowId = task.getWorkflowInstanceId();
        this.workflowType = task.getWorkflowType();
        this.workflowPriority = task.getWorkflowPriority();
        this.correlationId = task.getCorrelationId();
        this.scheduledTime = sdf.format(new Date(task.getScheduledTime()));
        this.startTime = sdf.format(new Date(task.getStartTime()));
        this.updateTime = sdf.format(new Date(task.getUpdateTime()));
        this.endTime = sdf.format(new Date(task.getEndTime()));
        this.status = task.getStatus();
        this.reasonForIncompletion = task.getReasonForIncompletion();
        this.queueWaitTime = task.getQueueWaitTime();
        this.taskReferenceName = task.getReferenceTaskName();
        if (task.getInputData() != null) {
            this.input = SummaryUtil.serializeInputOutput(task.getInputData());
        }

        if (task.getOutputData() != null) {
            this.output = SummaryUtil.serializeInputOutput(task.getOutputData());
        }

        if (task.getEndTime() > 0) {
            this.executionTime = task.getEndTime() - task.getStartTime();
        }

        if (StringUtils.isNotBlank(task.getExternalInputPayloadStoragePath())) {
            this.externalInputPayloadStoragePath = task.getExternalInputPayloadStoragePath();
        }
        if (StringUtils.isNotBlank(task.getExternalOutputPayloadStoragePath())) {
            this.externalOutputPayloadStoragePath = task.getExternalOutputPayloadStoragePath();
        }
    }

    /**
     * @return the workflowId
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * @param workflowId the workflowId to set
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * @return the workflowType
     */
    public String getWorkflowType() {
        return workflowType;
    }

    /**
     * @param workflowType the workflowType to set
     */
    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    /**
     * @return the correlationId
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * @param correlationId the correlationId to set
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * @return the scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the reasonForIncompletion
     */
    public String getReasonForIncompletion() {
        return reasonForIncompletion;
    }

    /**
     * @param reasonForIncompletion the reasonForIncompletion to set
     */
    public void setReasonForIncompletion(String reasonForIncompletion) {
        this.reasonForIncompletion = reasonForIncompletion;
    }

    /**
     * @return the executionTime
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * @param executionTime the executionTime to set
     */
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * @return the queueWaitTime
     */
    public long getQueueWaitTime() {
        return queueWaitTime;
    }

    /**
     * @param queueWaitTime the queueWaitTime to set
     */
    public void setQueueWaitTime(long queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    /**
     * @return the taskDefName
     */
    public String getTaskDefName() {
        return taskDefName;
    }

    /**
     * @param taskDefName the taskDefName to set
     */
    public void setTaskDefName(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    /**
     * @return the taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * @param taskType the taskType to set
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * @return input to the task
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input input to the task
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return output of the task
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output Task output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the external storage path for the task input payload
     */
    public String getExternalInputPayloadStoragePath() {
        return externalInputPayloadStoragePath;
    }

    /**
     * @param externalInputPayloadStoragePath the external storage path where the task input payload
     *     is stored
     */
    public void setExternalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
        this.externalInputPayloadStoragePath = externalInputPayloadStoragePath;
    }

    /**
     * @return the external storage path for the task output payload
     */
    public String getExternalOutputPayloadStoragePath() {
        return externalOutputPayloadStoragePath;
    }

    /**
     * @param externalOutputPayloadStoragePath the external storage path where the task output
     *     payload is stored
     */
    public void setExternalOutputPayloadStoragePath(String externalOutputPayloadStoragePath) {
        this.externalOutputPayloadStoragePath = externalOutputPayloadStoragePath;
    }

    /**
     * @return the priority defined on workflow
     */
    public int getWorkflowPriority() {
        return workflowPriority;
    }

    /**
     * @param workflowPriority Priority defined for workflow
     */
    public void setWorkflowPriority(int workflowPriority) {
        this.workflowPriority = workflowPriority;
    }

    public String getTaskReferenceName() {
        return taskReferenceName;
    }

    public void setTaskReferenceName(String taskReferenceName) {
        this.taskReferenceName = taskReferenceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskSummary that = (TaskSummary) o;
        return getExecutionTime() == that.getExecutionTime()
                && getQueueWaitTime() == that.getQueueWaitTime()
                && getWorkflowPriority() == that.getWorkflowPriority()
                && getWorkflowId().equals(that.getWorkflowId())
                && getWorkflowType().equals(that.getWorkflowType())
                && Objects.equals(getCorrelationId(), that.getCorrelationId())
                && getScheduledTime().equals(that.getScheduledTime())
                && Objects.equals(getStartTime(), that.getStartTime())
                && Objects.equals(getUpdateTime(), that.getUpdateTime())
                && Objects.equals(getEndTime(), that.getEndTime())
                && getStatus() == that.getStatus()
                && Objects.equals(getReasonForIncompletion(), that.getReasonForIncompletion())
                && Objects.equals(getTaskDefName(), that.getTaskDefName())
                && getTaskType().equals(that.getTaskType())
                && getTaskId().equals(that.getTaskId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getWorkflowId(),
                getWorkflowType(),
                getCorrelationId(),
                getScheduledTime(),
                getStartTime(),
                getUpdateTime(),
                getEndTime(),
                getStatus(),
                getReasonForIncompletion(),
                getExecutionTime(),
                getQueueWaitTime(),
                getTaskDefName(),
                getTaskType(),
                getTaskId(),
                getWorkflowPriority());
    }
}
