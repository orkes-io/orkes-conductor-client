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

/** GrantedAccessResponse */
public class GrantedAccessResponse {
    @SerializedName("grantedAccess")
    private List<GrantedAccess> grantedAccess = null;

    public GrantedAccessResponse grantedAccess(List<GrantedAccess> grantedAccess) {
        this.grantedAccess = grantedAccess;
        return this;
    }

    public GrantedAccessResponse addGrantedAccessItem(GrantedAccess grantedAccessItem) {
        if (this.grantedAccess == null) {
            this.grantedAccess = new ArrayList<GrantedAccess>();
        }
        this.grantedAccess.add(grantedAccessItem);
        return this;
    }

    /**
     * Get grantedAccess
     *
     * @return grantedAccess
     */
    @Schema(description = "")
    public List<GrantedAccess> getGrantedAccess() {
        return grantedAccess;
    }

    public void setGrantedAccess(List<GrantedAccess> grantedAccess) {
        this.grantedAccess = grantedAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrantedAccessResponse grantedAccessResponse = (GrantedAccessResponse) o;
        return Objects.equals(this.grantedAccess, grantedAccessResponse.grantedAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grantedAccess);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GrantedAccessResponse {\n");

        sb.append("    grantedAccess: ").append(toIndentedString(grantedAccess)).append("\n");
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
