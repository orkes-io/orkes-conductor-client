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

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** GenerateTokenRequest */
public class GenerateTokenRequest {
    @SerializedName("keyId")
    private String keyId = null;

    @SerializedName("keySecret")
    private String keySecret = null;

    public GenerateTokenRequest keyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    /**
     * Get keyId
     *
     * @return keyId
     */
    @Schema(required = true, description = "")
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public GenerateTokenRequest keySecret(String keySecret) {
        this.keySecret = keySecret;
        return this;
    }

    /**
     * Get keySecret
     *
     * @return keySecret
     */
    @Schema(required = true, description = "")
    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenerateTokenRequest generateTokenRequest = (GenerateTokenRequest) o;
        return Objects.equals(this.keyId, generateTokenRequest.keyId)
                && Objects.equals(this.keySecret, generateTokenRequest.keySecret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyId, keySecret);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GenerateTokenRequest {\n");

        sb.append("    keyId: ").append(toIndentedString(keyId)).append("\n");
        sb.append("    keySecret: ").append(toIndentedString(keySecret)).append("\n");
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
