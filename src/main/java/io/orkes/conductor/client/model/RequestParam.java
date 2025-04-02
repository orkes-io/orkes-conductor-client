package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RequestParam {
    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;  // Query, Header, Path, etc.

    @SerializedName("required")
    private boolean required;

    @SerializedName("schema")
    private Schema schema;

    // Getters and setters

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestParam that = (RequestParam) o;
        return required == that.required &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(schema, that.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, Boolean.valueOf(required), schema);
    }

    @Override
    public String toString() {
        String sb = "class RequestParam {\n" +
                "    name: " + name + "\n" +
                "    type: " + type + "\n" +
                "    required: " + required + "\n" +
                "    schema: " + schema + "\n" +
                "}";
        return sb;
    }

    public static class Schema {
        @SerializedName("type")
        private String type;

        @SerializedName("format")
        private String format;

        @SerializedName("defaultValue")
        private Object defaultValue;

        // Getters and setters

        @Override
        public boolean equals(java.lang.Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Schema that = (Schema) o;
            return Objects.equals(type, that.type) &&
                    Objects.equals(format, that.format) &&
                    Objects.equals(defaultValue, that.defaultValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, format, defaultValue);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Schema {\n");
            sb.append("    type: ").append(type).append("\n");
            sb.append("    format: ").append(format).append("\n");
            sb.append("    defaultValue: ").append(defaultValue).append("\n");
            sb.append("}");
            return sb.toString();
        }
    }
}
