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

/** GrantedAccess */
public class GrantedAccess {
    /** Gets or Sets access */
    public enum AccessEnum {
        CREATE("CREATE"),
        READ("READ"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        EXECUTE("EXECUTE");

        private String value;

        AccessEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static AccessEnum fromValue(String input) {
            for (AccessEnum b : AccessEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }
    }

    @SerializedName("access")
    private List<AccessEnum> access = null;

    @SerializedName("target")
    private TargetRef target = null;

    public GrantedAccess access(List<AccessEnum> access) {
        this.access = access;
        return this;
    }

    public GrantedAccess addAccessItem(AccessEnum accessItem) {
        if (this.access == null) {
            this.access = new ArrayList<AccessEnum>();
        }
        this.access.add(accessItem);
        return this;
    }

    /**
     * Get access
     *
     * @return access
     */
    @Schema(description = "")
    public List<AccessEnum> getAccess() {
        return access;
    }

    public void setAccess(List<AccessEnum> access) {
        this.access = access;
    }

    public GrantedAccess target(TargetRef target) {
        this.target = target;
        return this;
    }

    /**
     * Get target
     *
     * @return target
     */
    @Schema(description = "")
    public TargetRef getTarget() {
        return target;
    }

    public void setTarget(TargetRef target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrantedAccess grantedAccess = (GrantedAccess) o;
        return Objects.equals(this.access, grantedAccess.access)
                && Objects.equals(this.target, grantedAccess.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, target);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GrantedAccess {\n");

        sb.append("    access: ").append(toIndentedString(access)).append("\n");
        sb.append("    target: ").append(toIndentedString(target)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first
     * line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
