/*
 * Copyright 2023 Orkes, Inc.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * HumanTaskEntry
 */
public class HumanTaskEntry {
    @SerializedName("assignee")
    private String assignee = null;

    /**
     * Gets or Sets assigneeType
     */
    public enum AssigneeTypeEnum {
        @SerializedName("EXTERNAL_USER")
        EXTERNAL_USER("EXTERNAL_USER"),
        @SerializedName("EXTERNAL_GROUP")
        EXTERNAL_GROUP("EXTERNAL_GROUP"),
        @SerializedName("CONDUCTOR_USER")
        CONDUCTOR_USER("CONDUCTOR_USER"),
        @SerializedName("CONDUCTOR_GROUP")
        CONDUCTOR_GROUP("CONDUCTOR_GROUP"),
        @SerializedName("APPLICATION")
        APPLICATION("APPLICATION");

        private String value;

        AssigneeTypeEnum(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
        public static AssigneeTypeEnum fromValue(String input) {
            for (AssigneeTypeEnum b : AssigneeTypeEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

    }
    @SerializedName("assigneeType")
    private AssigneeTypeEnum assigneeType = null;

    @SerializedName("assignmentPolicy")
    private OneOfHumanTaskEntryAssignmentPolicy assignmentPolicy = null;

    @SerializedName("claimedBy")
    private String claimedBy = null;

    @SerializedName("createdBy")
    private String createdBy = null;

    @SerializedName("createdOn")
    private Long createdOn = null;

    @SerializedName("escalatedAt")
    private Long escalatedAt = null;

    @SerializedName("output")
    private Map<String, Object> output = null;

    @SerializedName("owners")
    private List<String> owners = null;

    @SerializedName("predefinedInput")
    private Map<String, Object> predefinedInput = null;

    /**
     * Gets or Sets state
     */
    public enum StateEnum {
        @SerializedName("PENDING")
        PENDING("PENDING"),
        @SerializedName("ASSIGNED")
        ASSIGNED("ASSIGNED"),
        @SerializedName("IN_PROGRESS")
        IN_PROGRESS("IN_PROGRESS"),
        @SerializedName("COMPLETED")
        COMPLETED("COMPLETED"),
        @SerializedName("TIMED_OUT")
        TIMED_OUT("TIMED_OUT");

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
    @SerializedName("state")
    private StateEnum state = null;

    @SerializedName("taskId")
    private String taskId = null;

    @SerializedName("taskRefName")
    private String taskRefName = null;

    @SerializedName("templateId")
    private String templateId = null;

    @SerializedName("templateName")
    private String templateName = null;

    @SerializedName("timeoutPolicy")
    private OneOfHumanTaskEntryTimeoutPolicy timeoutPolicy = null;

    @SerializedName("updatedOn")
    private Long updatedOn = null;

    @SerializedName("workflowId")
    private String workflowId = null;

    @SerializedName("workflowName")
    private String workflowName = null;

    public HumanTaskEntry assignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    /**
     * Get assignee
     * @return assignee
     **/
    @Schema(description = "")
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public HumanTaskEntry assigneeType(AssigneeTypeEnum assigneeType) {
        this.assigneeType = assigneeType;
        return this;
    }

    /**
     * Get assigneeType
     * @return assigneeType
     **/
    @Schema(description = "")
    public AssigneeTypeEnum getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeType(AssigneeTypeEnum assigneeType) {
        this.assigneeType = assigneeType;
    }

    public HumanTaskEntry assignmentPolicy(OneOfHumanTaskEntryAssignmentPolicy assignmentPolicy) {
        this.assignmentPolicy = assignmentPolicy;
        return this;
    }

    /**
     * Get assignmentPolicy
     * @return assignmentPolicy
     **/
    @Schema(description = "")
    public OneOfHumanTaskEntryAssignmentPolicy getAssignmentPolicy() {
        return assignmentPolicy;
    }

    public void setAssignmentPolicy(OneOfHumanTaskEntryAssignmentPolicy assignmentPolicy) {
        this.assignmentPolicy = assignmentPolicy;
    }

    public HumanTaskEntry claimedBy(String claimedBy) {
        this.claimedBy = claimedBy;
        return this;
    }

    /**
     * Get claimedBy
     * @return claimedBy
     **/
    @Schema(description = "")
    public String getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(String claimedBy) {
        this.claimedBy = claimedBy;
    }

    public HumanTaskEntry createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get createdBy
     * @return createdBy
     **/
    @Schema(description = "")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public HumanTaskEntry createdOn(Long createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    /**
     * Get createdOn
     * @return createdOn
     **/
    @Schema(description = "")
    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public HumanTaskEntry escalatedAt(Long escalatedAt) {
        this.escalatedAt = escalatedAt;
        return this;
    }

    /**
     * Get escalatedAt
     * @return escalatedAt
     **/
    @Schema(description = "")
    public Long getEscalatedAt() {
        return escalatedAt;
    }

    public void setEscalatedAt(Long escalatedAt) {
        this.escalatedAt = escalatedAt;
    }

    public HumanTaskEntry output(Map<String, Object> output) {
        this.output = output;
        return this;
    }

    public HumanTaskEntry putOutputItem(String key, Object outputItem) {
        if (this.output == null) {
            this.output = new HashMap<String, Object>();
        }
        this.output.put(key, outputItem);
        return this;
    }

    /**
     * Get output
     * @return output
     **/
    @Schema(description = "")
    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }

    public HumanTaskEntry owners(List<String> owners) {
        this.owners = owners;
        return this;
    }

    public HumanTaskEntry addOwnersItem(String ownersItem) {
        if (this.owners == null) {
            this.owners = new ArrayList<String>();
        }
        this.owners.add(ownersItem);
        return this;
    }

    /**
     * Get owners
     * @return owners
     **/
    @Schema(description = "")
    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public HumanTaskEntry predefinedInput(Map<String, Object> predefinedInput) {
        this.predefinedInput = predefinedInput;
        return this;
    }

    public HumanTaskEntry putPredefinedInputItem(String key, Object predefinedInputItem) {
        if (this.predefinedInput == null) {
            this.predefinedInput = new HashMap<String, Object>();
        }
        this.predefinedInput.put(key, predefinedInputItem);
        return this;
    }

    /**
     * Get predefinedInput
     * @return predefinedInput
     **/
    @Schema(description = "")
    public Map<String, Object> getPredefinedInput() {
        return predefinedInput;
    }

    public void setPredefinedInput(Map<String, Object> predefinedInput) {
        this.predefinedInput = predefinedInput;
    }

    public HumanTaskEntry state(StateEnum state) {
        this.state = state;
        return this;
    }

    /**
     * Get state
     * @return state
     **/
    @Schema(description = "")
    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public HumanTaskEntry taskId(String taskId) {
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

    public HumanTaskEntry taskRefName(String taskRefName) {
        this.taskRefName = taskRefName;
        return this;
    }

    /**
     * Get taskRefName
     * @return taskRefName
     **/
    @Schema(description = "")
    public String getTaskRefName() {
        return taskRefName;
    }

    public void setTaskRefName(String taskRefName) {
        this.taskRefName = taskRefName;
    }

    public HumanTaskEntry templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    /**
     * Get templateId
     * @return templateId
     **/
    @Schema(description = "")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public HumanTaskEntry templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    /**
     * Get templateName
     * @return templateName
     **/
    @Schema(description = "")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public HumanTaskEntry timeoutPolicy(OneOfHumanTaskEntryTimeoutPolicy timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
        return this;
    }

    /**
     * Get timeoutPolicy
     * @return timeoutPolicy
     **/
    @Schema(description = "")
    public OneOfHumanTaskEntryTimeoutPolicy getTimeoutPolicy() {
        return timeoutPolicy;
    }

    public void setTimeoutPolicy(OneOfHumanTaskEntryTimeoutPolicy timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
    }

    public HumanTaskEntry updatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    /**
     * Get updatedOn
     * @return updatedOn
     **/
    @Schema(description = "")
    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public HumanTaskEntry workflowId(String workflowId) {
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

    public HumanTaskEntry workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    /**
     * Get workflowName
     * @return workflowName
     **/
    @Schema(description = "")
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
        HumanTaskEntry humanTaskEntry = (HumanTaskEntry) o;
        return Objects.equals(this.assignee, humanTaskEntry.assignee) &&
                Objects.equals(this.assigneeType, humanTaskEntry.assigneeType) &&
                Objects.equals(this.assignmentPolicy, humanTaskEntry.assignmentPolicy) &&
                Objects.equals(this.claimedBy, humanTaskEntry.claimedBy) &&
                Objects.equals(this.createdBy, humanTaskEntry.createdBy) &&
                Objects.equals(this.createdOn, humanTaskEntry.createdOn) &&
                Objects.equals(this.escalatedAt, humanTaskEntry.escalatedAt) &&
                Objects.equals(this.output, humanTaskEntry.output) &&
                Objects.equals(this.owners, humanTaskEntry.owners) &&
                Objects.equals(this.predefinedInput, humanTaskEntry.predefinedInput) &&
                Objects.equals(this.state, humanTaskEntry.state) &&
                Objects.equals(this.taskId, humanTaskEntry.taskId) &&
                Objects.equals(this.taskRefName, humanTaskEntry.taskRefName) &&
                Objects.equals(this.templateId, humanTaskEntry.templateId) &&
                Objects.equals(this.templateName, humanTaskEntry.templateName) &&
                Objects.equals(this.timeoutPolicy, humanTaskEntry.timeoutPolicy) &&
                Objects.equals(this.updatedOn, humanTaskEntry.updatedOn) &&
                Objects.equals(this.workflowId, humanTaskEntry.workflowId) &&
                Objects.equals(this.workflowName, humanTaskEntry.workflowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignee, assigneeType, assignmentPolicy, claimedBy, createdBy, createdOn, escalatedAt, output, owners, predefinedInput, state, taskId, taskRefName, templateId, templateName, timeoutPolicy, updatedOn, workflowId, workflowName);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HumanTaskEntry {\n");

        sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
        sb.append("    assigneeType: ").append(toIndentedString(assigneeType)).append("\n");
        sb.append("    assignmentPolicy: ").append(toIndentedString(assignmentPolicy)).append("\n");
        sb.append("    claimedBy: ").append(toIndentedString(claimedBy)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
        sb.append("    escalatedAt: ").append(toIndentedString(escalatedAt)).append("\n");
        sb.append("    output: ").append(toIndentedString(output)).append("\n");
        sb.append("    owners: ").append(toIndentedString(owners)).append("\n");
        sb.append("    predefinedInput: ").append(toIndentedString(predefinedInput)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
        sb.append("    taskRefName: ").append(toIndentedString(taskRefName)).append("\n");
        sb.append("    templateId: ").append(toIndentedString(templateId)).append("\n");
        sb.append("    templateName: ").append(toIndentedString(templateName)).append("\n");
        sb.append("    timeoutPolicy: ").append(toIndentedString(timeoutPolicy)).append("\n");
        sb.append("    updatedOn: ").append(toIndentedString(updatedOn)).append("\n");
        sb.append("    workflowId: ").append(toIndentedString(workflowId)).append("\n");
        sb.append("    workflowName: ").append(toIndentedString(workflowName)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
