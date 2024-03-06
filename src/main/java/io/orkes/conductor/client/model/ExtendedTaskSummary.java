package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;
import com.netflix.conductor.common.run.TaskSummary;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * ExtendedTaskSummary
 */

public class ExtendedTaskSummary extends TaskSummary {
  @SerializedName("taskReferenceName")
  private String taskReferenceName = null;

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

}
