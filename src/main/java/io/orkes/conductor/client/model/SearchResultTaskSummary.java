/*
 * Copyright 2024 Orkes, Inc.
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

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * SearchResultTaskSummary
 */

public class SearchResultTaskSummary {
  private List<TaskSummary> results = null;

  private Long totalHits = null;

  public SearchResultTaskSummary results(List<TaskSummary> results) {
    this.results = results;
    return this;
  }

  public SearchResultTaskSummary addResultsItem(TaskSummary resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<TaskSummary>();
    }
    this.results.add(resultsItem);
    return this;
  }

   /**
   * Get results
   * @return results
  **/
  @Schema(description = "")
  public List<TaskSummary> getResults() {
    return results;
  }

  public void setResults(List<TaskSummary> results) {
    this.results = results;
  }

  public SearchResultTaskSummary totalHits(Long totalHits) {
    this.totalHits = totalHits;
    return this;
  }

   /**
   * Get totalHits
   * @return totalHits
  **/
  @Schema(description = "")
  public Long getTotalHits() {
    return totalHits;
  }

  public void setTotalHits(Long totalHits) {
    this.totalHits = totalHits;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResultTaskSummary searchResultTaskSummary = (SearchResultTaskSummary) o;
    return Objects.equals(this.results, searchResultTaskSummary.results) &&
        Objects.equals(this.totalHits, searchResultTaskSummary.totalHits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(results, totalHits);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResultTaskSummary {\n");
    
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("    totalHits: ").append(toIndentedString(totalHits)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
