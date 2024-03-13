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
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * HumanTaskSearchRequest
 */

public class HumanTaskSearchRequest {
    @SerializedName("actors")
    private List<Actor> actors = null;

    @SerializedName("createdOn")
    private DateInfo createdOn = null;

    @SerializedName("freeText")
    private String freeText = null;

    @SerializedName("resultsLimit")
    private Integer resultsLimit = null;

    @SerializedName("sortBy")
    private String sortBy = null;

    @SerializedName("start")
    private Integer start = null;

    /**
     * Gets or Sets states
     */
    public enum StatesEnum {
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

        StatesEnum(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
        public static StatesEnum fromValue(String input) {
            for (StatesEnum b : StatesEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

    }
    @SerializedName("states")
    private List<StatesEnum> states = null;

    @SerializedName("taskReferenceNames")
    private List<String> taskReferenceNames = null;

    @SerializedName("taskWorkflows")
    private List<String> taskWorkflows = null;

    @SerializedName("templateNames")
    private List<String> templateNames = null;

    @SerializedName("updatedOn")
    private DateInfo updatedOn = null;

    public HumanTaskSearchRequest actors(List<Actor> actors) {
        this.actors = actors;
        return this;
    }

    public HumanTaskSearchRequest addActorsItem(Actor actorsItem) {
        if (this.actors == null) {
            this.actors = new ArrayList<Actor>();
        }
        this.actors.add(actorsItem);
        return this;
    }

    /**
     * Get actors
     * @return actors
     **/
    @Schema(description = "")
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public HumanTaskSearchRequest createdOn(DateInfo createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    /**
     * Get createdOn
     * @return createdOn
     **/
    @Schema(description = "")
    public DateInfo getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateInfo createdOn) {
        this.createdOn = createdOn;
    }

    public HumanTaskSearchRequest freeText(String freeText) {
        this.freeText = freeText;
        return this;
    }

    /**
     * Get freeText
     * @return freeText
     **/
    @Schema(description = "")
    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public HumanTaskSearchRequest resultsLimit(Integer resultsLimit) {
        this.resultsLimit = resultsLimit;
        return this;
    }

    /**
     * Get resultsLimit
     * @return resultsLimit
     **/
    @Schema(description = "")
    public Integer getResultsLimit() {
        return resultsLimit;
    }

    public void setResultsLimit(Integer resultsLimit) {
        this.resultsLimit = resultsLimit;
    }

    public HumanTaskSearchRequest sortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    /**
     * Get sortBy
     * @return sortBy
     **/
    @Schema(description = "")
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public HumanTaskSearchRequest start(Integer start) {
        this.start = start;
        return this;
    }

    /**
     * Get start
     * @return start
     **/
    @Schema(description = "")
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public HumanTaskSearchRequest states(List<StatesEnum> states) {
        this.states = states;
        return this;
    }

    public HumanTaskSearchRequest addStatesItem(StatesEnum statesItem) {
        if (this.states == null) {
            this.states = new ArrayList<StatesEnum>();
        }
        this.states.add(statesItem);
        return this;
    }

    /**
     * Get states
     * @return states
     **/
    @Schema(description = "")
    public List<StatesEnum> getStates() {
        return states;
    }

    public void setStates(List<StatesEnum> states) {
        this.states = states;
    }

    public HumanTaskSearchRequest taskReferenceNames(List<String> taskReferenceNames) {
        this.taskReferenceNames = taskReferenceNames;
        return this;
    }

    public HumanTaskSearchRequest addTaskReferenceNamesItem(String taskReferenceNamesItem) {
        if (this.taskReferenceNames == null) {
            this.taskReferenceNames = new ArrayList<String>();
        }
        this.taskReferenceNames.add(taskReferenceNamesItem);
        return this;
    }

    /**
     * Get taskReferenceNames
     * @return taskReferenceNames
     **/
    @Schema(description = "")
    public List<String> getTaskReferenceNames() {
        return taskReferenceNames;
    }

    public void setTaskReferenceNames(List<String> taskReferenceNames) {
        this.taskReferenceNames = taskReferenceNames;
    }

    public HumanTaskSearchRequest taskWorkflows(List<String> taskWorkflows) {
        this.taskWorkflows = taskWorkflows;
        return this;
    }

    public HumanTaskSearchRequest addTaskWorkflowsItem(String taskWorkflowsItem) {
        if (this.taskWorkflows == null) {
            this.taskWorkflows = new ArrayList<String>();
        }
        this.taskWorkflows.add(taskWorkflowsItem);
        return this;
    }

    /**
     * Get taskWorkflows
     * @return taskWorkflows
     **/
    @Schema(description = "")
    public List<String> getTaskWorkflows() {
        return taskWorkflows;
    }

    public void setTaskWorkflows(List<String> taskWorkflows) {
        this.taskWorkflows = taskWorkflows;
    }

    public HumanTaskSearchRequest templateNames(List<String> templateNames) {
        this.templateNames = templateNames;
        return this;
    }

    public HumanTaskSearchRequest addTemplateNamesItem(String templateNamesItem) {
        if (this.templateNames == null) {
            this.templateNames = new ArrayList<String>();
        }
        this.templateNames.add(templateNamesItem);
        return this;
    }

    /**
     * Get templateNames
     * @return templateNames
     **/
    @Schema(description = "")
    public List<String> getTemplateNames() {
        return templateNames;
    }

    public void setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
    }

    public HumanTaskSearchRequest updatedOn(DateInfo updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    /**
     * Get updatedOn
     * @return updatedOn
     **/
    @Schema(description = "")
    public DateInfo getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(DateInfo updatedOn) {
        this.updatedOn = updatedOn;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HumanTaskSearchRequest humanTaskSearchRequest = (HumanTaskSearchRequest) o;
        return Objects.equals(this.actors, humanTaskSearchRequest.actors) &&
                Objects.equals(this.createdOn, humanTaskSearchRequest.createdOn) &&
                Objects.equals(this.freeText, humanTaskSearchRequest.freeText) &&
                Objects.equals(this.resultsLimit, humanTaskSearchRequest.resultsLimit) &&
                Objects.equals(this.sortBy, humanTaskSearchRequest.sortBy) &&
                Objects.equals(this.start, humanTaskSearchRequest.start) &&
                Objects.equals(this.states, humanTaskSearchRequest.states) &&
                Objects.equals(this.taskReferenceNames, humanTaskSearchRequest.taskReferenceNames) &&
                Objects.equals(this.taskWorkflows, humanTaskSearchRequest.taskWorkflows) &&
                Objects.equals(this.templateNames, humanTaskSearchRequest.templateNames) &&
                Objects.equals(this.updatedOn, humanTaskSearchRequest.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actors, createdOn, freeText, resultsLimit, sortBy, start, states, taskReferenceNames, taskWorkflows, templateNames, updatedOn);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HumanTaskSearchRequest {\n");

        sb.append("    actors: ").append(toIndentedString(actors)).append("\n");
        sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
        sb.append("    freeText: ").append(toIndentedString(freeText)).append("\n");
        sb.append("    resultsLimit: ").append(toIndentedString(resultsLimit)).append("\n");
        sb.append("    sortBy: ").append(toIndentedString(sortBy)).append("\n");
        sb.append("    start: ").append(toIndentedString(start)).append("\n");
        sb.append("    states: ").append(toIndentedString(states)).append("\n");
        sb.append("    taskReferenceNames: ").append(toIndentedString(taskReferenceNames)).append("\n");
        sb.append("    taskWorkflows: ").append(toIndentedString(taskWorkflows)).append("\n");
        sb.append("    templateNames: ").append(toIndentedString(templateNames)).append("\n");
        sb.append("    updatedOn: ").append(toIndentedString(updatedOn)).append("\n");
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
