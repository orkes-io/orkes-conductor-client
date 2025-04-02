package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class OrkesCircuitBreakerConfig {
    @SerializedName("failureRateThreshold")
    private float failureRateThreshold = 50.0f; // Percentage (e.g., 50.0 for 50%)

    @SerializedName("slidingWindowSize")
    private int slidingWindowSize = 100;

    @SerializedName("minimumNumberOfCalls")
    private int minimumNumberOfCalls = 100;

    @SerializedName("waitDurationInOpenState")
    private long waitDurationInOpenState = 1000; // In millisec

    @SerializedName("permittedNumberOfCallsInHalfOpenState")
    private int permittedNumberOfCallsInHalfOpenState = 100;

    @SerializedName("slowCallRateThreshold")
    private float slowCallRateThreshold = 50.0f; // Percentage of slow calls

    @SerializedName("slowCallDurationThreshold")
    private long slowCallDurationThreshold = 100; // Defines "slow" call duration in milliSec

    @SerializedName("automaticTransitionFromOpenToHalfOpenEnabled")
    private boolean automaticTransitionFromOpenToHalfOpenEnabled = true; // Auto transition

    @SerializedName("maxWaitDurationInHalfOpenState")
    private long maxWaitDurationInHalfOpenState = 1; // Max time in HALF-OPEN state

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrkesCircuitBreakerConfig that = (OrkesCircuitBreakerConfig) o;
        return Float.compare(that.failureRateThreshold, failureRateThreshold) == 0 &&
                slidingWindowSize == that.slidingWindowSize &&
                minimumNumberOfCalls == that.minimumNumberOfCalls &&
                waitDurationInOpenState == that.waitDurationInOpenState &&
                permittedNumberOfCallsInHalfOpenState == that.permittedNumberOfCallsInHalfOpenState &&
                Float.compare(that.slowCallRateThreshold, slowCallRateThreshold) == 0 &&
                slowCallDurationThreshold == that.slowCallDurationThreshold &&
                automaticTransitionFromOpenToHalfOpenEnabled == that.automaticTransitionFromOpenToHalfOpenEnabled &&
                maxWaitDurationInHalfOpenState == that.maxWaitDurationInHalfOpenState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Float.valueOf(failureRateThreshold),
                Integer.valueOf(slidingWindowSize),
                Integer.valueOf(minimumNumberOfCalls),
                Long.valueOf(waitDurationInOpenState),
                Integer.valueOf(permittedNumberOfCallsInHalfOpenState),
                Float.valueOf(slowCallRateThreshold),
                Long.valueOf(slowCallDurationThreshold),
                Boolean.valueOf(automaticTransitionFromOpenToHalfOpenEnabled),
                Long.valueOf(maxWaitDurationInHalfOpenState)
        );
    }

    @Override
    public String toString() {
        String sb = "class OrkesCircuitBreakerConfig {\n" +
                "    failureRateThreshold: " + failureRateThreshold + "\n" +
                "    slidingWindowSize: " + slidingWindowSize + "\n" +
                "    minimumNumberOfCalls: " + minimumNumberOfCalls + "\n" +
                "    waitDurationInOpenState: " + waitDurationInOpenState + "\n" +
                "    permittedNumberOfCallsInHalfOpenState: " + permittedNumberOfCallsInHalfOpenState + "\n" +
                "    slowCallRateThreshold: " + slowCallRateThreshold + "\n" +
                "    slowCallDurationThreshold: " + slowCallDurationThreshold + "\n" +
                "    automaticTransitionFromOpenToHalfOpenEnabled: " + automaticTransitionFromOpenToHalfOpenEnabled + "\n" +
                "    maxWaitDurationInHalfOpenState: " + maxWaitDurationInHalfOpenState + "\n" +
                "}";
        return sb;
    }
}
