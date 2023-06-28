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

/** AuthorizationRequest */
public class AuthorizationRequest {
    /** The set of access which is granted or removed */
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
    private List<AccessEnum> access = new ArrayList<AccessEnum>();

    @SerializedName("subject")
    private SubjectRef subject = null;

    @SerializedName("target")
    private TargetRef target = null;

    public AuthorizationRequest access(List<AccessEnum> access) {
        this.access = access;
        return this;
    }

    public AuthorizationRequest addAccessItem(AccessEnum accessItem) {
        this.access.add(accessItem);
        return this;
    }

    /**
     * The set of access which is granted or removed
     *
     * @return access
     */
    @Schema(required = true, description = "The set of access which is granted or removed")
    public List<AccessEnum> getAccess() {
        return access;
    }

    public void setAccess(List<AccessEnum> access) {
        this.access = access;
    }

    public AuthorizationRequest subject(SubjectRef subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Get subject
     *
     * @return subject
     */
    @Schema(required = true, description = "")
    public SubjectRef getSubject() {
        return subject;
    }

    public void setSubject(SubjectRef subject) {
        this.subject = subject;
    }

    public AuthorizationRequest target(TargetRef target) {
        this.target = target;
        return this;
    }

    /**
     * Get target
     *
     * @return target
     */
    @Schema(required = true, description = "")
    public TargetRef getTarget() {
        return target;
    }

    public void setTarget(TargetRef target) {
        this.target = target;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) o;
        return Objects.equals(this.access, authorizationRequest.access)
                && Objects.equals(this.subject, authorizationRequest.subject)
                && Objects.equals(this.target, authorizationRequest.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, subject, target);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorizationRequest {\n");

        sb.append("    access: ").append(toIndentedString(access)).append("\n");
        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    target: ").append(toIndentedString(target)).append("\n");
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
