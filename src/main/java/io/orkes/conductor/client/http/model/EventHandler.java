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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** EventHandler */
public class EventHandler {
    @SerializedName("actions")
    private List<Action> actions = new ArrayList<Action>();

    @SerializedName("active")
    private Boolean active = null;

    @SerializedName("condition")
    private String condition = null;

    @SerializedName("evaluatorType")
    private String evaluatorType = null;

    @SerializedName("event")
    private String event = null;

    @SerializedName("name")
    private String name = null;

    public EventHandler actions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public EventHandler addActionsItem(Action actionsItem) {
        this.actions.add(actionsItem);
        return this;
    }

    /**
     * Get actions
     *
     * @return actions
     */
    @Schema(required = true, description = "")
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public EventHandler active(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * Get active
     *
     * @return active
     */
    @Schema(description = "")
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public EventHandler condition(String condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Get condition
     *
     * @return condition
     */
    @Schema(description = "")
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public EventHandler evaluatorType(String evaluatorType) {
        this.evaluatorType = evaluatorType;
        return this;
    }

    /**
     * Get evaluatorType
     *
     * @return evaluatorType
     */
    @Schema(description = "")
    public String getEvaluatorType() {
        return evaluatorType;
    }

    public void setEvaluatorType(String evaluatorType) {
        this.evaluatorType = evaluatorType;
    }

    public EventHandler event(String event) {
        this.event = event;
        return this;
    }

    /**
     * Get event
     *
     * @return event
     */
    @Schema(required = true, description = "")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public EventHandler name(String name) {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventHandler eventHandler = (EventHandler) o;
        return Objects.equals(this.actions, eventHandler.actions)
                && Objects.equals(this.active, eventHandler.active)
                && Objects.equals(this.condition, eventHandler.condition)
                && Objects.equals(this.evaluatorType, eventHandler.evaluatorType)
                && Objects.equals(this.event, eventHandler.event)
                && Objects.equals(this.name, eventHandler.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actions, active, condition, evaluatorType, event, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EventHandler {\n");

        sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
        sb.append("    active: ").append(toIndentedString(active)).append("\n");
        sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
        sb.append("    evaluatorType: ").append(toIndentedString(evaluatorType)).append("\n");
        sb.append("    event: ").append(toIndentedString(event)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
