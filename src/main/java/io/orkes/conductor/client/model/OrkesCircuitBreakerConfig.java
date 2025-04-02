package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class OrkesCircuitBreakerConfig {
    @SerializedName("automaticTransitionFromOpenToHalfOpenEnabled")
    private Boolean automaticTransitionFromOpenToHalfOpenEnabled = null;

    @SerializedName("failureRateThreshold")
    private Float failureRateThreshold = null;

    @SerializedName("maxWaitDurationInHalfOpenState")
    private Long maxWaitDurationInHalfOpenState = null;

    @SerializedName("minimumNumberOfCalls")
    private Integer minimumNumberOfCalls = null;

    @SerializedName("permittedNumberOfCallsInHalfOpenState")
    private Integer permittedNumberOfCallsInHalfOpenState = null;

    @SerializedName("slidingWindowSize")
    private Integer slidingWindowSize = null;

    @SerializedName("slowCallDurationThreshold")
    private Long slowCallDurationThreshold = null;

    @SerializedName("slowCallRateThreshold")
    private Float slowCallRateThreshold = null;

    @SerializedName("waitDurationInOpenState")
    private Long waitDurationInOpenState = null;

    public OrkesCircuitBreakerConfig automaticTransitionFromOpenToHalfOpenEnabled(Boolean automaticTransitionFromOpenToHalfOpenEnabled) {
        this.automaticTransitionFromOpenToHalfOpenEnabled = automaticTransitionFromOpenToHalfOpenEnabled;
        return this;
    }

    /**
     * Get automaticTransitionFromOpenToHalfOpenEnabled
     * @return automaticTransitionFromOpenToHalfOpenEnabled
     **/
    @Schema(description = "")
    public Boolean isAutomaticTransitionFromOpenToHalfOpenEnabled() {
        return automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public void setAutomaticTransitionFromOpenToHalfOpenEnabled(Boolean automaticTransitionFromOpenToHalfOpenEnabled) {
        this.automaticTransitionFromOpenToHalfOpenEnabled = automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public OrkesCircuitBreakerConfig failureRateThreshold(Float failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
        return this;
    }

    /**
     * Get failureRateThreshold
     * @return failureRateThreshold
     **/
    @Schema(description = "")
    public Float getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(Float failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public OrkesCircuitBreakerConfig maxWaitDurationInHalfOpenState(Long maxWaitDurationInHalfOpenState) {
        this.maxWaitDurationInHalfOpenState = maxWaitDurationInHalfOpenState;
        return this;
    }

    /**
     * Get maxWaitDurationInHalfOpenState
     * @return maxWaitDurationInHalfOpenState
     **/
    @Schema(description = "")
    public Long getMaxWaitDurationInHalfOpenState() {
        return maxWaitDurationInHalfOpenState;
    }

    public void setMaxWaitDurationInHalfOpenState(Long maxWaitDurationInHalfOpenState) {
        this.maxWaitDurationInHalfOpenState = maxWaitDurationInHalfOpenState;
    }

    public OrkesCircuitBreakerConfig minimumNumberOfCalls(Integer minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
        return this;
    }

    /**
     * Get minimumNumberOfCalls
     * @return minimumNumberOfCalls
     **/
    @Schema(description = "")
    public Integer getMinimumNumberOfCalls() {
        return minimumNumberOfCalls;
    }

    public void setMinimumNumberOfCalls(Integer minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
    }

    public OrkesCircuitBreakerConfig permittedNumberOfCallsInHalfOpenState(Integer permittedNumberOfCallsInHalfOpenState) {
        this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
        return this;
    }

    /**
     * Get permittedNumberOfCallsInHalfOpenState
     * @return permittedNumberOfCallsInHalfOpenState
     **/
    @Schema(description = "")
    public Integer getPermittedNumberOfCallsInHalfOpenState() {
        return permittedNumberOfCallsInHalfOpenState;
    }

    public void setPermittedNumberOfCallsInHalfOpenState(Integer permittedNumberOfCallsInHalfOpenState) {
        this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
    }

    public OrkesCircuitBreakerConfig slidingWindowSize(Integer slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
        return this;
    }

    /**
     * Get slidingWindowSize
     * @return slidingWindowSize
     **/
    @Schema(description = "")
    public Integer getSlidingWindowSize() {
        return slidingWindowSize;
    }

    public void setSlidingWindowSize(Integer slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
    }

    public OrkesCircuitBreakerConfig slowCallDurationThreshold(Long slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
        return this;
    }

    /**
     * Get slowCallDurationThreshold
     * @return slowCallDurationThreshold
     **/
    @Schema(description = "")
    public Long getSlowCallDurationThreshold() {
        return slowCallDurationThreshold;
    }

    public void setSlowCallDurationThreshold(Long slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
    }

    public OrkesCircuitBreakerConfig slowCallRateThreshold(Float slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
        return this;
    }

    /**
     * Get slowCallRateThreshold
     * @return slowCallRateThreshold
     **/
    @Schema(description = "")
    public Float getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(Float slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    public OrkesCircuitBreakerConfig waitDurationInOpenState(Long waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
        return this;
    }

    /**
     * Get waitDurationInOpenState
     * @return waitDurationInOpenState
     **/
    @Schema(description = "")
    public Long getWaitDurationInOpenState() {
        return waitDurationInOpenState;
    }

    public void setWaitDurationInOpenState(Long waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrkesCircuitBreakerConfig orkesCircuitBreakerConfig = (OrkesCircuitBreakerConfig) o;
        return Objects.equals(this.automaticTransitionFromOpenToHalfOpenEnabled, orkesCircuitBreakerConfig.automaticTransitionFromOpenToHalfOpenEnabled) &&
                Objects.equals(this.failureRateThreshold, orkesCircuitBreakerConfig.failureRateThreshold) &&
                Objects.equals(this.maxWaitDurationInHalfOpenState, orkesCircuitBreakerConfig.maxWaitDurationInHalfOpenState) &&
                Objects.equals(this.minimumNumberOfCalls, orkesCircuitBreakerConfig.minimumNumberOfCalls) &&
                Objects.equals(this.permittedNumberOfCallsInHalfOpenState, orkesCircuitBreakerConfig.permittedNumberOfCallsInHalfOpenState) &&
                Objects.equals(this.slidingWindowSize, orkesCircuitBreakerConfig.slidingWindowSize) &&
                Objects.equals(this.slowCallDurationThreshold, orkesCircuitBreakerConfig.slowCallDurationThreshold) &&
                Objects.equals(this.slowCallRateThreshold, orkesCircuitBreakerConfig.slowCallRateThreshold) &&
                Objects.equals(this.waitDurationInOpenState, orkesCircuitBreakerConfig.waitDurationInOpenState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(automaticTransitionFromOpenToHalfOpenEnabled, failureRateThreshold, maxWaitDurationInHalfOpenState, minimumNumberOfCalls, permittedNumberOfCallsInHalfOpenState, slidingWindowSize, slowCallDurationThreshold, slowCallRateThreshold, waitDurationInOpenState);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrkesCircuitBreakerConfig {\n");

        sb.append("    automaticTransitionFromOpenToHalfOpenEnabled: ").append(toIndentedString(automaticTransitionFromOpenToHalfOpenEnabled)).append("\n");
        sb.append("    failureRateThreshold: ").append(toIndentedString(failureRateThreshold)).append("\n");
        sb.append("    maxWaitDurationInHalfOpenState: ").append(toIndentedString(maxWaitDurationInHalfOpenState)).append("\n");
        sb.append("    minimumNumberOfCalls: ").append(toIndentedString(minimumNumberOfCalls)).append("\n");
        sb.append("    permittedNumberOfCallsInHalfOpenState: ").append(toIndentedString(permittedNumberOfCallsInHalfOpenState)).append("\n");
        sb.append("    slidingWindowSize: ").append(toIndentedString(slidingWindowSize)).append("\n");
        sb.append("    slowCallDurationThreshold: ").append(toIndentedString(slowCallDurationThreshold)).append("\n");
        sb.append("    slowCallRateThreshold: ").append(toIndentedString(slowCallRateThreshold)).append("\n");
        sb.append("    waitDurationInOpenState: ").append(toIndentedString(waitDurationInOpenState)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}