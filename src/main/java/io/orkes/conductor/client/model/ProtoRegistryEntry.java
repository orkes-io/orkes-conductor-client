package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class ProtoRegistryEntry {
    @SerializedName("data")
    private byte[] data = null;

    @SerializedName("filename")
    private String filename = null;

    @SerializedName("serviceName")
    private String serviceName = null;

    public ProtoRegistryEntry data(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Get data
     * @return data
     **/
    @Schema(description = "")
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ProtoRegistryEntry filename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Get filename
     * @return filename
     **/
    @Schema(description = "")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ProtoRegistryEntry serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    /**
     * Get serviceName
     * @return serviceName
     **/
    @Schema(description = "")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProtoRegistryEntry protoRegistryEntry = (ProtoRegistryEntry) o;
        return Arrays.equals(this.data, protoRegistryEntry.data) &&
                Objects.equals(this.filename, protoRegistryEntry.filename) &&
                Objects.equals(this.serviceName, protoRegistryEntry.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(data), filename, serviceName);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProtoRegistryEntry {\n");

        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
        sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
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