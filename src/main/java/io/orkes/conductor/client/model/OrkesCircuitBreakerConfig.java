package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class OrkesCircuitBreakerConfig {
    @SerializedName("circuitBreakerConfig")
    private OrkesCircuitBreakerConfig circuitBreakerConfig = null;

    public OrkesCircuitBreakerConfig circuitBreakerConfig(OrkesCircuitBreakerConfig circuitBreakerConfig) {
        this.circuitBreakerConfig = circuitBreakerConfig;
        return this;
    }

    /**
     * Get circuitBreakerConfig
     * @return circuitBreakerConfig
     **/
    @Schema(description = "")
    public OrkesCircuitBreakerConfig getCircuitBreakerConfig() {
        return circuitBreakerConfig;
    }

    public void setCircuitBreakerConfig(OrkesCircuitBreakerConfig circuitBreakerConfig) {
        this.circuitBreakerConfig = circuitBreakerConfig;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrkesCircuitBreakerConfig config = (OrkesCircuitBreakerConfig) o;
        return Objects.equals(this.circuitBreakerConfig, config.circuitBreakerConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(circuitBreakerConfig);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Config {\n");

        sb.append("    circuitBreakerConfig: ").append(toIndentedString(circuitBreakerConfig)).append("\n");
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