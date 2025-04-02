package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CircuitBreakerTransitionResponse {
    @SerializedName("service")
    private String service;

    @SerializedName("previousState")
    private String previousState;

    @SerializedName("currentState")
    private String currentState;

    @SerializedName("transitionTimestamp")
    private long transitionTimestamp;

    @SerializedName("message")
    private String message;

    // Getters and setters

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CircuitBreakerTransitionResponse that = (CircuitBreakerTransitionResponse) o;
        return transitionTimestamp == that.transitionTimestamp &&
                Objects.equals(service, that.service) &&
                Objects.equals(previousState, that.previousState) &&
                Objects.equals(currentState, that.currentState) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, previousState, currentState, Long.valueOf(transitionTimestamp), message);
    }

    @Override
    public String toString() {
        String sb = "class CircuitBreakerTransitionResponse {\n" +
                "    service: " + service + "\n" +
                "    previousState: " + previousState + "\n" +
                "    currentState: " + currentState + "\n" +
                "    transitionTimestamp: " + transitionTimestamp + "\n" +
                "    message: " + message + "\n" +
                "}";
        return sb;
    }
}