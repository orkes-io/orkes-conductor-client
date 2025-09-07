package io.orkes.conductor.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

import java.util.*;

@Setter
public class ServiceMethod {
    @SerializedName("exampleInput")
    private Map<String, Object> exampleInput = null;

    @SerializedName("id")
    private Long id = null;

    @SerializedName("inputType")
    private String inputType = null;

    @SerializedName("methodName")
    private String methodName = null;

    @SerializedName("methodType")
    private String methodType = null;

    @SerializedName("operationName")
    private String operationName = null;

    @SerializedName("outputType")
    private String outputType = null;

    @SerializedName("requestParams")
    private List<RequestParam> requestParams = null;

    public ServiceMethod exampleInput(Map<String, Object> exampleInput) {
        this.exampleInput = exampleInput;
        return this;
    }

    public ServiceMethod putExampleInputItem(String key, Object exampleInputItem) {
        if (this.exampleInput == null) {
            this.exampleInput = new HashMap<String, Object>();
        }
        this.exampleInput.put(key, exampleInputItem);
        return this;
    }

    /**
     * Get exampleInput
     * @return exampleInput
     **/
    @Schema(description = "")
    public Map<String, Object> getExampleInput() {
        return exampleInput;
    }

    public void setExampleInput(Map<String, Object> exampleInput) {
        this.exampleInput = exampleInput;
    }

    public ServiceMethod id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/
    @Schema(description = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceMethod inputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    /**
     * Get inputType
     * @return inputType
     **/
    @Schema(description = "")
    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public ServiceMethod methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * Get methodName
     * @return methodName
     **/
    @Schema(description = "")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ServiceMethod methodType(String methodType) {
        this.methodType = methodType;
        return this;
    }

    /**
     * Get methodType
     * @return methodType
     **/
    @Schema(description = "")
    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public ServiceMethod operationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    /**
     * Get operationName
     * @return operationName
     **/
    @Schema(description = "")
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public ServiceMethod outputType(String outputType) {
        this.outputType = outputType;
        return this;
    }

    /**
     * Get outputType
     * @return outputType
     **/
    @Schema(description = "")
    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public ServiceMethod requestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public ServiceMethod addRequestParamsItem(RequestParam requestParamsItem) {
        if (this.requestParams == null) {
            this.requestParams = new ArrayList<RequestParam>();
        }
        this.requestParams.add(requestParamsItem);
        return this;
    }

    /**
     * Get requestParams
     * @return requestParams
     **/
    @Schema(description = "")
    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceMethod serviceMethod = (ServiceMethod) o;
        return Objects.equals(this.exampleInput, serviceMethod.exampleInput) &&
                Objects.equals(this.id, serviceMethod.id) &&
                Objects.equals(this.inputType, serviceMethod.inputType) &&
                Objects.equals(this.methodName, serviceMethod.methodName) &&
                Objects.equals(this.methodType, serviceMethod.methodType) &&
                Objects.equals(this.operationName, serviceMethod.operationName) &&
                Objects.equals(this.outputType, serviceMethod.outputType) &&
                Objects.equals(this.requestParams, serviceMethod.requestParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exampleInput, id, inputType, methodName, methodType, operationName, outputType, requestParams);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceMethod {\n");

        sb.append("    exampleInput: ").append(toIndentedString(exampleInput)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    inputType: ").append(toIndentedString(inputType)).append("\n");
        sb.append("    methodName: ").append(toIndentedString(methodName)).append("\n");
        sb.append("    methodType: ").append(toIndentedString(methodType)).append("\n");
        sb.append("    operationName: ").append(toIndentedString(operationName)).append("\n");
        sb.append("    outputType: ").append(toIndentedString(outputType)).append("\n");
        sb.append("    requestParams: ").append(toIndentedString(requestParams)).append("\n");
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