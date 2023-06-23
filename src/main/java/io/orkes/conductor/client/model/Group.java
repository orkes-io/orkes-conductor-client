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

/** Group */
public class Group {
    /** Gets or Sets inner */
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

    @SerializedName("id")
    private String id = null;

    @SerializedName("roles")
    private List<Role> roles = null;

    public Group defaultAccess(Map<String, List<String>> defaultAccess) {
        this.defaultAccess = defaultAccess;
        return this;
    }

    public Group putDefaultAccessItem(String key, List<String> defaultAccessItem) {
        if (this.defaultAccess == null) {
            this.defaultAccess = new HashMap<String, List<String>>();
        }
        this.defaultAccess.put(key, defaultAccessItem);
        return this;
    }

    /**
     * Get defaultAccess
     *
     * @return defaultAccess
     */
    @Schema(description = "")
    public Map<String, List<String>> getDefaultAccess() {
        return defaultAccess;
    }

    public void setDefaultAccess(Map<String, List<String>> defaultAccess) {
        this.defaultAccess = defaultAccess;
    }

    public Group description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     */
    @Schema(description = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Group id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     */
    @Schema(description = "")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Group roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Group addRolesItem(Role rolesItem) {
        if (this.roles == null) {
            this.roles = new ArrayList<Role>();
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
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
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
        Group group = (Group) o;
        return Objects.equals(this.defaultAccess, group.defaultAccess)
                && Objects.equals(this.description, group.description)
                && Objects.equals(this.id, group.id)
                && Objects.equals(this.roles, group.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultAccess, description, id, roles);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Group {\n");

        sb.append("    defaultAccess: ").append(toIndentedString(defaultAccess)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
