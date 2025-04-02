package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServiceMethod {
    @SerializedName("id")
    private Long id;

    @SerializedName("operationName")
    private String operationName;

    @SerializedName("methodName")
    private String methodName;

    @SerializedName("methodType")
    private String methodType;  //GET, PUT, POST, UNARY, SERVER_STREAMING etc.

    @SerializedName("inputType")
    private String inputType;

    @SerializedName("outputType")
    private String outputType;

    // Add request parameters
    @SerializedName("requestParams")
    private List<RequestParam> requestParams = new ArrayList<>();

    // Sample input request -- useful for constructing input payload in the UI
    @SerializedName("exampleInput")
    private Map<String, Object> exampleInput;

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceMethod that = (ServiceMethod) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operationName, that.operationName) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(methodType, that.methodType) &&
                Objects.equals(inputType, that.inputType) &&
                Objects.equals(outputType, that.outputType) &&
                Objects.equals(requestParams, that.requestParams) &&
                Objects.equals(exampleInput, that.exampleInput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operationName, methodName, methodType, inputType, outputType, requestParams, exampleInput);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceMethod {\n");
        sb.append("    id: ").append(id).append("\n");
        sb.append("    operationName: ").append(operationName).append("\n");
        sb.append("    methodName: ").append(methodName).append("\n");
        sb.append("    methodType: ").append(methodType).append("\n");
        sb.append("    inputType: ").append(inputType).append("\n");
        sb.append("    outputType: ").append(outputType).append("\n");
        sb.append("    requestParams: ").append(requestParams).append("\n");
        sb.append("    exampleInput: ").append(exampleInput).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
