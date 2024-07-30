package io.orkes.conductor.client.http.clients;

import io.orkes.conductor.client.http.Param;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OrkesHttpClientRequest {
    private final String method;
    private final String path;
    private final List<Param> pathParams;
    private final List<Param> queryParams;
    private final Map<String, String> headerParams;
    private final Object body;

    private OrkesHttpClientRequest(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.pathParams = builder.pathParams;
        this.queryParams = builder.queryParams;
        this.headerParams = builder.headerParams;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String method;
        private String path;
        private final List<Param> pathParams = new ArrayList<>();
        private final List<Param> queryParams = new ArrayList<>();
        private final Map<String, String> headerParams = new HashMap<>();
        private Object body;

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder addPathParam(String name, String value) {
            this.pathParams.add(new Param(name, value));
            return this;
        }

        public Builder addQueryParam(String name, String value) {
            this.queryParams.add(new Param(name, value));
            return this;
        }

        public Builder addHeaderParam(String name, String value) {
            this.headerParams.put(name, value);
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public OrkesHttpClientRequest build() {
            return new OrkesHttpClientRequest(this);
        }
    }
}
