package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceRegistry {
    @SerializedName("methods")
    private final List<ServiceMethod> methods = new ArrayList<>();
    @SerializedName("requestParams")
    private final List<RequestParam> requestParams = new ArrayList<>();
    @SerializedName("config")
    private final Config config = new Config();
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private Type type;
    @SerializedName("serviceURI")
    private String serviceURI;

    // Getters and setters

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceRegistry that = (ServiceRegistry) o;
        return Objects.equals(name, that.name) &&
                type == that.type &&
                Objects.equals(serviceURI, that.serviceURI) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(requestParams, that.requestParams) &&
                Objects.equals(config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, serviceURI, methods, requestParams, config);
    }

    @Override
    public String toString() {
        String sb = "class ServiceRegistry {\n" +
                "    name: " + name + "\n" +
                "    type: " + type + "\n" +
                "    serviceURI: " + serviceURI + "\n" +
                "    methods: " + methods + "\n" +
                "    requestParams: " + requestParams + "\n" +
                "    config: " + config + "\n" +
                "}";
        return sb;
    }

    public enum Type {
        @SerializedName("HTTP")
        HTTP,

        @SerializedName("gRPC")
        gRPC
    }

    public static class Config {
        @SerializedName("circuitBreakerConfig")
        private OrkesCircuitBreakerConfig circuitBreakerConfig = new OrkesCircuitBreakerConfig();

        // Constructors
        public Config() {
        }

        public Config(OrkesCircuitBreakerConfig circuitBreakerConfig) {
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
            Config that = (Config) o;
            return Objects.equals(circuitBreakerConfig, that.circuitBreakerConfig);
        }

        @Override
        public int hashCode() {
            return Objects.hash(circuitBreakerConfig);
        }

        @Override
        public String toString() {
            String sb = "class Config {\n" +
                    "    circuitBreakerConfig: " + circuitBreakerConfig + "\n" +
                    "}";
            return sb;
        }
    }
}