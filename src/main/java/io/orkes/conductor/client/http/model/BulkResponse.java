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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** BulkResponse */
public class BulkResponse {
    @SerializedName("bulkErrorResults")
    private Map<String, String> bulkErrorResults = null;

    @SerializedName("bulkSuccessfulResults")
    private List<String> bulkSuccessfulResults = null;

    public BulkResponse bulkErrorResults(Map<String, String> bulkErrorResults) {
        this.bulkErrorResults = bulkErrorResults;
        return this;
    }

    public BulkResponse putBulkErrorResultsItem(String key, String bulkErrorResultsItem) {
        if (this.bulkErrorResults == null) {
            this.bulkErrorResults = new HashMap<String, String>();
        }
        this.bulkErrorResults.put(key, bulkErrorResultsItem);
        return this;
    }

    /**
     * Get bulkErrorResults
     *
     * @return bulkErrorResults
     */
    @Schema(description = "")
    public Map<String, String> getBulkErrorResults() {
        return bulkErrorResults;
    }

    public void setBulkErrorResults(Map<String, String> bulkErrorResults) {
        this.bulkErrorResults = bulkErrorResults;
    }

    public BulkResponse bulkSuccessfulResults(List<String> bulkSuccessfulResults) {
        this.bulkSuccessfulResults = bulkSuccessfulResults;
        return this;
    }

    public BulkResponse addBulkSuccessfulResultsItem(String bulkSuccessfulResultsItem) {
        if (this.bulkSuccessfulResults == null) {
            this.bulkSuccessfulResults = new ArrayList<String>();
        }
        this.bulkSuccessfulResults.add(bulkSuccessfulResultsItem);
        return this;
    }

    /**
     * Get bulkSuccessfulResults
     *
     * @return bulkSuccessfulResults
     */
    @Schema(description = "")
    public List<String> getBulkSuccessfulResults() {
        return bulkSuccessfulResults;
    }

    public void setBulkSuccessfulResults(List<String> bulkSuccessfulResults) {
        this.bulkSuccessfulResults = bulkSuccessfulResults;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BulkResponse bulkResponse = (BulkResponse) o;
        return Objects.equals(this.bulkErrorResults, bulkResponse.bulkErrorResults)
                && Objects.equals(this.bulkSuccessfulResults, bulkResponse.bulkSuccessfulResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulkErrorResults, bulkSuccessfulResults);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BulkResponse {\n");

        sb.append("    bulkErrorResults: ").append(toIndentedString(bulkErrorResults)).append("\n");
        sb.append("    bulkSuccessfulResults: ")
                .append(toIndentedString(bulkSuccessfulResults))
                .append("\n");
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
