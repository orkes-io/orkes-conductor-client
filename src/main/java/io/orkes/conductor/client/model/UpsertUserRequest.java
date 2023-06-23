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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** UpsertUserRequest */
public class UpsertUserRequest {
    @SerializedName("groups")
    private List<String> groups = null;

    @SerializedName("name")
    private String name = null;

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

    public UpsertUserRequest groups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public UpsertUserRequest addGroupsItem(String groupsItem) {
        if (this.groups == null) {
            this.groups = new ArrayList<String>();
        }
        this.groups.add(groupsItem);
        return this;
    }

    /**
     * Ids of the groups this user belongs to
     *
     * @return groups
     */
    @Schema(description = "Ids of the groups this user belongs to")
    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public UpsertUserRequest name(String name) {
        this.name = name;
        return this;
    }

    /**
     * User&#x27;s full name
     *
     * @return name
     */
    @Schema(required = true, description = "User's full name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpsertUserRequest roles(List<RolesEnum> roles) {
        this.roles = roles;
        return this;
    }

    public UpsertUserRequest addRolesItem(RolesEnum rolesItem) {
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
        UpsertUserRequest upsertUserRequest = (UpsertUserRequest) o;
        return Objects.equals(this.groups, upsertUserRequest.groups)
                && Objects.equals(this.name, upsertUserRequest.name)
                && Objects.equals(this.roles, upsertUserRequest.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, name, roles);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpsertUserRequest {\n");

        sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
