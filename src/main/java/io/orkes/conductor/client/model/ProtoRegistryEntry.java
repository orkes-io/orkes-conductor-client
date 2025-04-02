package io.orkes.conductor.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class ProtoRegistryEntry {
    @SerializedName("serviceName")
    private String serviceName;

    @SerializedName("filename")
    private String filename;

    @SerializedName("data")
    private byte[] data;

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProtoRegistryEntry that = (ProtoRegistryEntry) o;
        return Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(filename, that.filename) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int dataHash = (data != null) ? Arrays.hashCode(data) : 0;
        return Objects.hash(serviceName, filename) + dataHash;
    }

    @Override
    public String toString() {
        String sb = "class ProtoRegistryEntry {\n" +
                "    serviceName: " + serviceName + "\n" +
                "    filename: " + filename + "\n" +
                "    data: " + (data != null ? "[binary data]" : "null") + "\n" +
                "}";
        return sb;
    }
}