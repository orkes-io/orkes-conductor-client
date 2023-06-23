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

import java.util.*;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** UpsertGroupRequest */
public class UpsertGroupRequest {
    /**
     * a default Map&lt;TargetType, Set&lt;Access&gt; to share permissions, allowed target types:
     * WORKFLOW_DEF, TASK_DEF
     */
    public enum InnerEnum {
        CREATE("CREATE"),
        READ("READ"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        EXECUTE("EXECUTE");

        private String value;

        InnerEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static InnerEnum fromValue(String input) {
            for (InnerEnum b : InnerEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

    }

    @SerializedName("defaultAccess")
    private Map<String, List<String>> defaultAccess = null;

    @SerializedName("description")
    private String description = null;

    /** Gets or Sets roles */
    public enum RolesEnum {
        ADMIN("ADMIN"),
        USER("USER"),
        WORKER("WORKER"),
        METADATA_MANAGER("METADATA_MANAGER"),
        WORKFLOW_MANAGER("WORKFLOW_MANAGER");

        private String value;

        RolesEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static RolesEnum fromValue(String input) {
            for (RolesEnum b : RolesEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

    }

    @SerializedName("roles")
    private List<RolesEnum> roles = null;

    public UpsertGroupRequest defaultAccess(Map<String, List<String>> defaultAccess) {
        this.defaultAccess = defaultAccess;
        return this;
    }

    public UpsertGroupRequest putDefaultAccessItem(String key, List<String> defaultAccessItem) {
        if (this.defaultAccess == null) {
            this.defaultAccess = new HashMap<String, List<String>>();
        }
        this.defaultAccess.put(key, defaultAccessItem);
        return this;
    }

    /**
     * a default Map&lt;TargetType, Set&lt;Access&gt; to share permissions, allowed target types:
     * WORKFLOW_DEF, TASK_DEF
     *
     * @return defaultAccess
     */
    @Schema(
            description =
                    "a default Map<TargetType, Set<Access> to share permissions, allowed target types: WORKFLOW_DEF, TASK_DEF")
    public Map<String, List<String>> getDefaultAccess() {
        return defaultAccess;
    }

    public void setDefaultAccess(Map<String, List<String>> defaultAccess) {
        this.defaultAccess = defaultAccess;
    }

    public UpsertGroupRequest description(String description) {
        this.description = description;
        return this;
    }

    /**
     * A general description of the group
     *
     * @return description
     */
    @Schema(required = true, description = "A general description of the group")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpsertGroupRequest roles(List<RolesEnum> roles) {
        this.roles = roles;
        return this;
    }

    public UpsertGroupRequest addRolesItem(RolesEnum rolesItem) {
        if (this.roles == null) {
            this.roles = new ArrayList<RolesEnum>();
        }
        this.roles.add(rolesItem);
        return this;
    }

    /**
     * Get roles
     *
     * @return roles
     */
    @Schema(description = "")
    public List<RolesEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesEnum> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpsertGroupRequest upsertGroupRequest = (UpsertGroupRequest) o;
        return Objects.equals(this.defaultAccess, upsertGroupRequest.defaultAccess)
                && Objects.equals(this.description, upsertGroupRequest.description)
                && Objects.equals(this.roles, upsertGroupRequest.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultAccess, description, roles);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpsertGroupRequest {\n");

        sb.append("    defaultAccess: ").append(toIndentedString(defaultAccess)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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
