package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;
import com.nimbusds.jose.shaded.gson.TypeAdapter;
import com.nimbusds.jose.shaded.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceRegistry {
    @SerializedName("config")
    private OrkesCircuitBreakerConfig config = null;

    @SerializedName("methods")
    private List<ServiceMethod> methods = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("requestParams")
    private List<RequestParam> requestParams = null;

    @SerializedName("serviceURI")
    private String serviceURI = null;
    @SerializedName("type")
    private TypeEnum type = null;

    public ServiceRegistry config(OrkesCircuitBreakerConfig config) {
        this.config = config;
        return this;
    }

    /**
     * Get config
     *
     * @return config
     **/
    @Schema(description = "")
    public OrkesCircuitBreakerConfig getConfig() {
        return config;
    }

    public void setConfig(OrkesCircuitBreakerConfig config) {
        this.config = config;
    }

    public ServiceRegistry methods(List<ServiceMethod> methods) {
        this.methods = methods;
        return this;
    }

    public ServiceRegistry addMethodsItem(ServiceMethod methodsItem) {
        if (this.methods == null) {
            this.methods = new ArrayList<ServiceMethod>();
        }
        this.methods.add(methodsItem);
        return this;
    }

    /**
     * Get methods
     *
     * @return methods
     **/
    @Schema(description = "")
    public List<ServiceMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ServiceMethod> methods) {
        this.methods = methods;
    }

    public ServiceRegistry name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @Schema(description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceRegistry requestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public ServiceRegistry addRequestParamsItem(RequestParam requestParamsItem) {
        if (this.requestParams == null) {
            this.requestParams = new ArrayList<RequestParam>();
        }
        this.requestParams.add(requestParamsItem);
        return this;
    }

    /**
     * Get requestParams
     *
     * @return requestParams
     **/
    @Schema(description = "")
    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

    public ServiceRegistry serviceURI(String serviceURI) {
        this.serviceURI = serviceURI;
        return this;
    }

    /**
     * Get serviceURI
     *
     * @return serviceURI
     **/
    @Schema(description = "")
    public String getServiceURI() {
        return serviceURI;
    }

    public void setServiceURI(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public ServiceRegistry type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @Schema(description = "")
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceRegistry serviceRegistry = (ServiceRegistry) o;
        return Objects.equals(this.config, serviceRegistry.config) &&
                Objects.equals(this.methods, serviceRegistry.methods) &&
                Objects.equals(this.name, serviceRegistry.name) &&
                Objects.equals(this.requestParams, serviceRegistry.requestParams) &&
                Objects.equals(this.serviceURI, serviceRegistry.serviceURI) &&
                Objects.equals(this.type, serviceRegistry.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(config, methods, name, requestParams, serviceURI, type);
    }

    @Override
    public String toString() {

        String sb = "class ServiceRegistry {\n" +
                "    config: " + toIndentedString(config) + "\n" +
                "    methods: " + toIndentedString(methods) + "\n" +
                "    name: " + toIndentedString(name) + "\n" +
                "    requestParams: " + toIndentedString(requestParams) + "\n" +
                "    serviceURI: " + toIndentedString(serviceURI) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
                "}";
        return sb;
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

    /**
     * Gets or Sets type
     */
    @JsonAdapter(TypeEnum.Adapter.class)
    public enum TypeEnum {
        @SerializedName("HTTP")
        HTTP("HTTP"),
        @SerializedName("gRPC")
        GRPC("gRPC");

        private final String value;

        TypeEnum(String value) {
            this.value = value;
        }

        public static TypeEnum fromValue(String input) {
            for (TypeEnum b : TypeEnum.values()) {
                if (b.value.equals(input)) {
                    return b;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static class Adapter extends TypeAdapter<TypeEnum> {
            @Override
            public void write(com.nimbusds.jose.shaded.gson.stream.JsonWriter jsonWriter, TypeEnum typeEnum) throws IOException {
                jsonWriter.value(String.valueOf(typeEnum.getValue()));
            }

            @Override
            public TypeEnum read(com.nimbusds.jose.shaded.gson.stream.JsonReader jsonReader) throws IOException {
                Object value = jsonReader.nextString();
                return TypeEnum.fromValue((String) (value));
            }
        }
    }

}