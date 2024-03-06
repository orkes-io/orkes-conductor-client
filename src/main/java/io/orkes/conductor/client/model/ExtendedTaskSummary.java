package io.orkes.conductor.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * ExtendedTaskSummary
 */

public class ExtendedTaskSummary {
  @SerializedName("correlationId")
  private String correlationId = null;

  @SerializedName("endTime")
  private String endTime = null;

  @SerializedName("executionTime")
  private Long executionTime = null;

  @SerializedName("externalInputPayloadStoragePath")
  private String externalInputPayloadStoragePath = null;

  @SerializedName("externalOutputPayloadStoragePath")
  private String externalOutputPayloadStoragePath = null;

  @SerializedName("input")
  private String input = null;

  @SerializedName("output")
  private String output = null;

  @SerializedName("queueWaitTime")
  private Long queueWaitTime = null;

  @SerializedName("reasonForIncompletion")
  private String reasonForIncompletion = null;

  @SerializedName("scheduledTime")
  private String scheduledTime = null;

  @SerializedName("startTime")
  private String startTime = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    IN_PROGRESS("IN_PROGRESS"),
    CANCELED("CANCELED"),
    FAILED("FAILED"),
    FAILED_WITH_TERMINAL_ERROR("FAILED_WITH_TERMINAL_ERROR"),
    COMPLETED("COMPLETED"),
    COMPLETED_WITH_ERRORS("COMPLETED_WITH_ERRORS"),
    SCHEDULED("SCHEDULED"),
    TIMED_OUT("TIMED_OUT"),
    SKIPPED("SKIPPED");

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
  }  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("taskDefName")
  private String taskDefName = null;

  @SerializedName("taskId")
  private String taskId = null;

  @SerializedName("taskReferenceName")
  private String taskReferenceName = null;

  @SerializedName("taskType")
  private String taskType = null;

  @SerializedName("updateTime")
  private String updateTime = null;

  @SerializedName("workflowId")
  private String workflowId = null;

  @SerializedName("workflowPriority")
  private Integer workflowPriority = null;

  @SerializedName("workflowType")
  private String workflowType = null;

  public ExtendedTaskSummary correlationId(String correlationId) {
    this.correlationId = correlationId;
    return this;
  }

   /**
   * Get correlationId
   * @return correlationId
  **/
  @Schema(description = "")
  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public ExtendedTaskSummary endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @Schema(description = "")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public ExtendedTaskSummary executionTime(Long executionTime) {
    this.executionTime = executionTime;
    return this;
  }

   /**
   * Get executionTime
   * @return executionTime
  **/
  @Schema(description = "")
  public Long getExecutionTime() {
    return executionTime;
  }

  public void setExecutionTime(Long executionTime) {
    this.executionTime = executionTime;
  }

  public ExtendedTaskSummary externalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
    this.externalInputPayloadStoragePath = externalInputPayloadStoragePath;
    return this;
  }

   /**
   * Get externalInputPayloadStoragePath
   * @return externalInputPayloadStoragePath
  **/
  @Schema(description = "")
  public String getExternalInputPayloadStoragePath() {
    return externalInputPayloadStoragePath;
  }

  public void setExternalInputPayloadStoragePath(String externalInputPayloadStoragePath) {
    this.externalInputPayloadStoragePath = externalInputPayloadStoragePath;
  }

  public ExtendedTaskSummary externalOutputPayloadStoragePath(String externalOutputPayloadStoragePath) {
    this.externalOutputPayloadStoragePath = externalOutputPayloadStoragePath;
    return this;
  }

   /**
   * Get externalOutputPayloadStoragePath
   * @return externalOutputPayloadStoragePath
  **/
  @Schema(description = "")
  public String getExternalOutputPayloadStoragePath() {
    return externalOutputPayloadStoragePath;
  }

  public void setExternalOutputPayloadStoragePath(String externalOutputPayloadStoragePath) {
    this.externalOutputPayloadStoragePath = externalOutputPayloadStoragePath;
  }

  public ExtendedTaskSummary input(String input) {
    this.input = input;
    return this;
  }

   /**
   * Get input
   * @return input
  **/
  @Schema(description = "")
  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public ExtendedTaskSummary output(String output) {
    this.output = output;
    return this;
  }

   /**
   * Get output
   * @return output
  **/
  @Schema(description = "")
  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public ExtendedTaskSummary queueWaitTime(Long queueWaitTime) {
    this.queueWaitTime = queueWaitTime;
    return this;
  }

   /**
   * Get queueWaitTime
   * @return queueWaitTime
  **/
  @Schema(description = "")
  public Long getQueueWaitTime() {
    return queueWaitTime;
  }

  public void setQueueWaitTime(Long queueWaitTime) {
    this.queueWaitTime = queueWaitTime;
  }

  public ExtendedTaskSummary reasonForIncompletion(String reasonForIncompletion) {
    this.reasonForIncompletion = reasonForIncompletion;
    return this;
  }

   /**
   * Get reasonForIncompletion
   * @return reasonForIncompletion
  **/
  @Schema(description = "")
  public String getReasonForIncompletion() {
    return reasonForIncompletion;
  }

  public void setReasonForIncompletion(String reasonForIncompletion) {
    this.reasonForIncompletion = reasonForIncompletion;
  }

  public ExtendedTaskSummary scheduledTime(String scheduledTime) {
    this.scheduledTime = scheduledTime;
    return this;
  }

   /**
   * Get scheduledTime
   * @return scheduledTime
  **/
  @Schema(description = "")
  public String getScheduledTime() {
    return scheduledTime;
  }

  public void setScheduledTime(String scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public ExtendedTaskSummary startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @Schema(description = "")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public ExtendedTaskSummary status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @Schema(description = "")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ExtendedTaskSummary taskDefName(String taskDefName) {
    this.taskDefName = taskDefName;
    return this;
  }

   /**
   * Get taskDefName
   * @return taskDefName
  **/
  @Schema(description = "")
  public String getTaskDefName() {
    return taskDefName;
  }

  public void setTaskDefName(String taskDefName) {
    this.taskDefName = taskDefName;
  }

  public ExtendedTaskSummary taskId(String taskId) {
    this.taskId = taskId;
    return this;
  }

   /**
   * Get taskId
   * @return taskId
  **/
  @Schema(description = "")
  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public ExtendedTaskSummary taskReferenceName(String taskReferenceName) {
    this.taskReferenceName = taskReferenceName;
    return this;
  }

   /**
   * Get taskReferenceName
   * @return taskReferenceName
  **/
  @Schema(description = "")
  public String getTaskReferenceName() {
    return taskReferenceName;
  }

  public void setTaskReferenceName(String taskReferenceName) {
    this.taskReferenceName = taskReferenceName;
  }

  public ExtendedTaskSummary taskType(String taskType) {
    this.taskType = taskType;
    return this;
  }

   /**
   * Get taskType
   * @return taskType
  **/
  @Schema(description = "")
  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public ExtendedTaskSummary updateTime(String updateTime) {
    this.updateTime = updateTime;
    return this;
  }

   /**
   * Get updateTime
   * @return updateTime
  **/
  @Schema(description = "")
  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public ExtendedTaskSummary workflowId(String workflowId) {
    this.workflowId = workflowId;
    return this;
  }

   /**
   * Get workflowId
   * @return workflowId
  **/
  @Schema(description = "")
  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public ExtendedTaskSummary workflowPriority(Integer workflowPriority) {
    this.workflowPriority = workflowPriority;
    return this;
  }

   /**
   * Get workflowPriority
   * @return workflowPriority
  **/
  @Schema(description = "")
  public Integer getWorkflowPriority() {
    return workflowPriority;
  }

  public void setWorkflowPriority(Integer workflowPriority) {
    this.workflowPriority = workflowPriority;
  }

  public ExtendedTaskSummary workflowType(String workflowType) {
    this.workflowType = workflowType;
    return this;
  }

   /**
   * Get workflowType
   * @return workflowType
  **/
  @Schema(description = "")
  public String getWorkflowType() {
    return workflowType;
  }

  public void setWorkflowType(String workflowType) {
    this.workflowType = workflowType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtendedTaskSummary extendedTaskSummary = (ExtendedTaskSummary) o;
    return Objects.equals(this.correlationId, extendedTaskSummary.correlationId) &&
        Objects.equals(this.endTime, extendedTaskSummary.endTime) &&
        Objects.equals(this.executionTime, extendedTaskSummary.executionTime) &&
        Objects.equals(this.externalInputPayloadStoragePath, extendedTaskSummary.externalInputPayloadStoragePath) &&
        Objects.equals(this.externalOutputPayloadStoragePath, extendedTaskSummary.externalOutputPayloadStoragePath) &&
        Objects.equals(this.input, extendedTaskSummary.input) &&
        Objects.equals(this.output, extendedTaskSummary.output) &&
        Objects.equals(this.queueWaitTime, extendedTaskSummary.queueWaitTime) &&
        Objects.equals(this.reasonForIncompletion, extendedTaskSummary.reasonForIncompletion) &&
        Objects.equals(this.scheduledTime, extendedTaskSummary.scheduledTime) &&
        Objects.equals(this.startTime, extendedTaskSummary.startTime) &&
        Objects.equals(this.status, extendedTaskSummary.status) &&
        Objects.equals(this.taskDefName, extendedTaskSummary.taskDefName) &&
        Objects.equals(this.taskId, extendedTaskSummary.taskId) &&
        Objects.equals(this.taskReferenceName, extendedTaskSummary.taskReferenceName) &&
        Objects.equals(this.taskType, extendedTaskSummary.taskType) &&
        Objects.equals(this.updateTime, extendedTaskSummary.updateTime) &&
        Objects.equals(this.workflowId, extendedTaskSummary.workflowId) &&
        Objects.equals(this.workflowPriority, extendedTaskSummary.workflowPriority) &&
        Objects.equals(this.workflowType, extendedTaskSummary.workflowType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correlationId, endTime, executionTime, externalInputPayloadStoragePath, externalOutputPayloadStoragePath, input, output, queueWaitTime, reasonForIncompletion, scheduledTime, startTime, status, taskDefName, taskId, taskReferenceName, taskType, updateTime, workflowId, workflowPriority, workflowType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtendedTaskSummary {\n");
    
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    executionTime: ").append(toIndentedString(executionTime)).append("\n");
    sb.append("    externalInputPayloadStoragePath: ").append(toIndentedString(externalInputPayloadStoragePath)).append("\n");
    sb.append("    externalOutputPayloadStoragePath: ").append(toIndentedString(externalOutputPayloadStoragePath)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    queueWaitTime: ").append(toIndentedString(queueWaitTime)).append("\n");
    sb.append("    reasonForIncompletion: ").append(toIndentedString(reasonForIncompletion)).append("\n");
    sb.append("    scheduledTime: ").append(toIndentedString(scheduledTime)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    taskDefName: ").append(toIndentedString(taskDefName)).append("\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    taskReferenceName: ").append(toIndentedString(taskReferenceName)).append("\n");
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
    sb.append("    workflowPriority: ").append(toIndentedString(workflowPriority)).append("\n");
    sb.append("    workflowType: ").append(toIndentedString(workflowType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
