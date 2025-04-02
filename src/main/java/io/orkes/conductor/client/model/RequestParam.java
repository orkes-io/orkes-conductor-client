package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class RequestParam {
    @SerializedName("name")
    private String name = null;

    @SerializedName("required")
    private Boolean required = null;

    @SerializedName("schema")
    private io.swagger.v3.oas.annotations.media.Schema schema = null;

    @SerializedName("type")
    private String type = null;

    public RequestParam name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     * @return name
     **/
    @Schema(description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestParam required(Boolean required) {
        this.required = required;
        return this;
    }

    /**
     * Get required
     * @return required
     **/
    @Schema(description = "")
    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public RequestParam schema(io.swagger.v3.oas.annotations.media.Schema schema) {
        this.schema = schema;
        return this;
    }

    /**
     * Get schema
     * @return schema
     **/
    @Schema(description = "")
    public io.swagger.v3.oas.annotations.media.Schema getSchema() {
        return schema;
    }

    public void setSchema(io.swagger.v3.oas.annotations.media.Schema schema) {
        this.schema = schema;
    }

    public RequestParam type(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     * @return type
     **/
    @Schema(description = "")
    public String getType() {
        return type;
    }

    public void setType(String type) {
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
        RequestParam requestParam = (RequestParam) o;
        return Objects.equals(this.name, requestParam.name) &&
                Objects.equals(this.required, requestParam.required) &&
                Objects.equals(this.schema, requestParam.schema) &&
                Objects.equals(this.type, requestParam.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, required, schema, type);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RequestParam {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    required: ").append(toIndentedString(required)).append("\n");
        sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
