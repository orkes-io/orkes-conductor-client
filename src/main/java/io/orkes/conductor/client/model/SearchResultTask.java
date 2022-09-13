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

import com.netflix.conductor.common.metadata.tasks.Task;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** SearchResultTask */
public class SearchResultTask {
    @SerializedName("results")
    private List<Task> results = null;

    @SerializedName("totalHits")
    private Long totalHits = null;

    public SearchResultTask results(List<Task> results) {
        this.results = results;
        return this;
    }

    public SearchResultTask addResultsItem(Task resultsItem) {
        if (this.results == null) {
            this.results = new ArrayList<Task>();
        }
        this.results.add(resultsItem);
        return this;
    }

    /**
     * Get results
     *
     * @return results
     */
    @Schema(description = "")
    public List<Task> getResults() {
        return results;
    }

    public void setResults(List<Task> results) {
        this.results = results;
    }

    public SearchResultTask totalHits(Long totalHits) {
        this.totalHits = totalHits;
        return this;
    }

    /**
     * Get totalHits
     *
     * @return totalHits
     */
    @Schema(description = "")
    public Long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Long totalHits) {
        this.totalHits = totalHits;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchResultTask searchResultTask = (SearchResultTask) o;
        return Objects.equals(this.results, searchResultTask.results)
                && Objects.equals(this.totalHits, searchResultTask.totalHits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, totalHits);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SearchResultTask {\n");

        sb.append("    results: ").append(toIndentedString(results)).append("\n");
        sb.append("    totalHits: ").append(toIndentedString(totalHits)).append("\n");
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
