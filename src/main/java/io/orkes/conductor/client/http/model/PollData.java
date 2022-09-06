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

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

/** PollData */
public class PollData {
    @SerializedName("domain")
    private String domain = null;

    @SerializedName("lastPollTime")
    private Long lastPollTime = null;

    @SerializedName("queueName")
    private String queueName = null;

    @SerializedName("workerId")
    private String workerId = null;

    public PollData domain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Get domain
     *
     * @return domain
     */
    @Schema(description = "")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public PollData lastPollTime(Long lastPollTime) {
        this.lastPollTime = lastPollTime;
        return this;
    }

    /**
     * Get lastPollTime
     *
     * @return lastPollTime
     */
    @Schema(description = "")
    public Long getLastPollTime() {
        return lastPollTime;
    }

    public void setLastPollTime(Long lastPollTime) {
        this.lastPollTime = lastPollTime;
    }

    public PollData queueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    /**
     * Get queueName
     *
     * @return queueName
     */
    @Schema(description = "")
    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public PollData workerId(String workerId) {
        this.workerId = workerId;
        return this;
    }

    /**
     * Get workerId
     *
     * @return workerId
     */
    @Schema(description = "")
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PollData pollData = (PollData) o;
        return Objects.equals(this.domain, pollData.domain)
                && Objects.equals(this.lastPollTime, pollData.lastPollTime)
                && Objects.equals(this.queueName, pollData.queueName)
                && Objects.equals(this.workerId, pollData.workerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, lastPollTime, queueName, workerId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PollData {\n");

        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    lastPollTime: ").append(toIndentedString(lastPollTime)).append("\n");
        sb.append("    queueName: ").append(toIndentedString(queueName)).append("\n");
        sb.append("    workerId: ").append(toIndentedString(workerId)).append("\n");
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
