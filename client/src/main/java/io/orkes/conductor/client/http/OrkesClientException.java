/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.http;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.model.validation.ErrorResponse;
import io.orkes.conductor.client.model.validation.ValidationError;

//TODO refactor - too many constructors
public class OrkesClientException extends RuntimeException {

    private int status;
    private String instance;
    private String code;
    private boolean retryable;
    private List<ValidationError> validationErrors;

    private Map<String, List<String>> responseHeaders = null;

    private String responseBody;

    public OrkesClientException() {
    }

    public OrkesClientException(String message) {
        super(message);
    }

    public OrkesClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrkesClientException(int status, String message) {
        super(message);
        this.status = status;
    }

    public OrkesClientException(int status, ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.status = status;
        this.retryable = errorResponse.isRetryable();
        this.code = errorResponse.getCode();
        this.instance = errorResponse.getInstance();
        this.validationErrors = errorResponse.getValidationErrors();
    }

    public OrkesClientException(String message,
                                Throwable cause,
                                int status,
                                Map<String, List<String>> responseHeaders,
                                String responseBody) {
        super(message, cause);
        this.status = status;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public OrkesClientException(String message,
                                int status,
                                Map<String, List<String>> responseHeaders,
                                String responseBody) {
        super(message);
        this.status = status;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public OrkesClientException(String message,
                                int status,
                                Map<String, List<String>> responseHeaders) {
        super(message);
        this.status = status;
        this.responseHeaders = responseHeaders;
    }

    public OrkesClientException(String message,
                                Throwable cause,
                                int status,
                                Map<String, List<String>> responseHeaders) {
        super(message, cause);
        this.status = status;
        this.responseHeaders = responseHeaders;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public void setRetryable(boolean retryable) {
        this.retryable = retryable;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public boolean isClientError() {
        return status > 399 && status < 499;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(getClass().getName()).append(": ");

        if (this.getMessage() != null) {
            builder.append(this.getMessage());
        }

        if (status > 0) {
            builder.append(" {status=").append(status);
            if (this.code != null) {
                builder.append(", code='").append(code).append("'");
            }

            builder.append(", retryable: ").append(retryable);
        }

        if (this.instance != null) {
            builder.append(", instance: ").append(instance);
        }

        if (this.validationErrors != null) {
            builder.append(", validationErrors: ").append(validationErrors.toString());
        }

        builder.append("}");
        return builder.toString();
    }

    public String responseBody() {
        return responseBody;
    }

    public Map<String, List<String>> responseHeaders() {
        return responseHeaders;
    }
}
