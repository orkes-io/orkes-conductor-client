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

/** ConductorUser */
public class ConductorUser {
    @SerializedName("applicationUser")
    private Boolean applicationUser = null;

    @SerializedName("groups")
    private List<Group> groups = null;

    @SerializedName("id")
    private String id = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("roles")
    private List<Role> roles = null;

    @SerializedName("uuid")
    private String uuid = null;

    public ConductorUser applicationUser(Boolean applicationUser) {
        this.applicationUser = applicationUser;
        return this;
    }

    /**
     * Get applicationUser
     *
     * @return applicationUser
     */
    @Schema(description = "")
    public Boolean isApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(Boolean applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ConductorUser groups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public ConductorUser addGroupsItem(Group groupsItem) {
        if (this.groups == null) {
            this.groups = new ArrayList<Group>();
        }
        this.groups.add(groupsItem);
        return this;
    }

    /**
     * Get groups
     *
     * @return groups
     */
    @Schema(description = "")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public ConductorUser id(String id) {
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

    public ConductorUser name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    @Schema(description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConductorUser roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public ConductorUser addRolesItem(Role rolesItem) {
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

    public ConductorUser uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Get uuid
     *
     * @return uuid
     */
    @Schema(description = "")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConductorUser conductorUser = (ConductorUser) o;
        return Objects.equals(this.applicationUser, conductorUser.applicationUser)
                && Objects.equals(this.groups, conductorUser.groups)
                && Objects.equals(this.id, conductorUser.id)
                && Objects.equals(this.name, conductorUser.name)
                && Objects.equals(this.roles, conductorUser.roles)
                && Objects.equals(this.uuid, conductorUser.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationUser, groups, id, name, roles, uuid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ConductorUser {\n");

        sb.append("    applicationUser: ").append(toIndentedString(applicationUser)).append("\n");
        sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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
