package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CircuitBreakerTransitionResponse {
    @SerializedName("currentState")
    private String currentState = null;

    @SerializedName("message")
    private String message = null;

    @SerializedName("previousState")
    private String previousState = null;

    @SerializedName("service")
    private String service = null;

    @SerializedName("transitionTimestamp")
    private Long transitionTimestamp = null;

    public CircuitBreakerTransitionResponse currentState(String currentState) {
        this.currentState = currentState;
        return this;
    }

    /**
     * Get currentState
     * @return currentState
     **/
    @Schema(description = "")
    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public CircuitBreakerTransitionResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get message
     * @return message
     **/
    @Schema(description = "")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CircuitBreakerTransitionResponse previousState(String previousState) {
        this.previousState = previousState;
        return this;
    }

    /**
     * Get previousState
     * @return previousState
     **/
    @Schema(description = "")
    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public CircuitBreakerTransitionResponse service(String service) {
        this.service = service;
        return this;
    }

    /**
     * Get service
     * @return service
     **/
    @Schema(description = "")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public CircuitBreakerTransitionResponse transitionTimestamp(Long transitionTimestamp) {
        this.transitionTimestamp = transitionTimestamp;
        return this;
    }

    /**
     * Get transitionTimestamp
     * @return transitionTimestamp
     **/
    @Schema(description = "")
    public Long getTransitionTimestamp() {
        return transitionTimestamp;
    }

    public void setTransitionTimestamp(Long transitionTimestamp) {
        this.transitionTimestamp = transitionTimestamp;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CircuitBreakerTransitionResponse circuitBreakerTransitionResponse = (CircuitBreakerTransitionResponse) o;
        return Objects.equals(this.currentState, circuitBreakerTransitionResponse.currentState) &&
                Objects.equals(this.message, circuitBreakerTransitionResponse.message) &&
                Objects.equals(this.previousState, circuitBreakerTransitionResponse.previousState) &&
                Objects.equals(this.service, circuitBreakerTransitionResponse.service) &&
                Objects.equals(this.transitionTimestamp, circuitBreakerTransitionResponse.transitionTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, message, previousState, service, transitionTimestamp);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CircuitBreakerTransitionResponse {\n");

        sb.append("    currentState: ").append(toIndentedString(currentState)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    previousState: ").append(toIndentedString(previousState)).append("\n");
        sb.append("    service: ").append(toIndentedString(service)).append("\n");
        sb.append("    transitionTimestamp: ").append(toIndentedString(transitionTimestamp)).append("\n");
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