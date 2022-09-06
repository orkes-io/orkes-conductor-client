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
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** ScrollableSearchResultWorkflowSummary */
public class ScrollableSearchResultWorkflowSummary {
    @SerializedName("queryId")
    private String queryId = null;

    @SerializedName("results")
    private List<WorkflowSummary> results = null;

    public ScrollableSearchResultWorkflowSummary queryId(String queryId) {
        this.queryId = queryId;
        return this;
    }

    /**
     * Get queryId
     *
     * @return queryId
     */
    @Schema(description = "")
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public ScrollableSearchResultWorkflowSummary results(List<WorkflowSummary> results) {
        this.results = results;
        return this;
    }

    public ScrollableSearchResultWorkflowSummary addResultsItem(WorkflowSummary resultsItem) {
        if (this.results == null) {
            this.results = new ArrayList<WorkflowSummary>();
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
    public List<WorkflowSummary> getResults() {
        return results;
    }

    public void setResults(List<WorkflowSummary> results) {
        this.results = results;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScrollableSearchResultWorkflowSummary scrollableSearchResultWorkflowSummary =
                (ScrollableSearchResultWorkflowSummary) o;
        return Objects.equals(this.queryId, scrollableSearchResultWorkflowSummary.queryId)
                && Objects.equals(this.results, scrollableSearchResultWorkflowSummary.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryId, results);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ScrollableSearchResultWorkflowSummary {\n");

        sb.append("    queryId: ").append(toIndentedString(queryId)).append("\n");
        sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
