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

import org.apache.commons.lang3.StringUtils;

import io.orkes.conductor.client.OrkesClientException;

public class ApiException extends OrkesClientException {
    private int code = 0;
    private Map<String, List<String>> responseHeaders = null;
    private String responseBody = null;

    public ApiException() {}

    public ApiException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(
            String message,
            Throwable throwable,
            int code,
            Map<String, List<String>> responseHeaders,
            String responseBody) {
        super(message, throwable);
        super.setCode(String.valueOf(code));
        super.setStatus(code);
        this.code = code;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public ApiException(
            String message,
            int code,
            Map<String, List<String>> responseHeaders,
            String responseBody) {
        this(message, (Throwable) null, code, responseHeaders, responseBody);
        super.setCode(String.valueOf(code));
        super.setStatus(code);
    }

    public ApiException(
            String message,
            Throwable throwable,
            int code,
            Map<String, List<String>> responseHeaders) {
        this(message, throwable, code, responseHeaders, null);
        super.setCode(String.valueOf(code));
        super.setStatus(code);
    }

    public ApiException(int code, Map<String, List<String>> responseHeaders, String responseBody) {
        this((String) null, (Throwable) null, code, responseHeaders, responseBody);
        super.setCode(String.valueOf(code));
        super.setStatus(code);
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        super.setCode(String.valueOf(code));
        super.setStatus(code);
    }

    public ApiException(
            int code,
            String message,
            Map<String, List<String>> responseHeaders,
            String responseBody) {
        this(code, message);
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        super.setCode(String.valueOf(code));
        super.setStatus(code);
    }

    @Override
    public boolean isClientError() {
        return code > 399 && code < 499;
    }

    /**
     *
     * @return HTTP status code
     */
    public int getStatusCode() {
        return code;
    }

    /**
     * Get the HTTP response headers.
     *
     * @return A map of list of string
     */
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Get the HTTP response body.
     *
     * @return Response body in the form of string
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String getMessage() {
        return getStatusCode()
                + ":"
                + (StringUtils.isBlank(responseBody) ? super.getMessage() : responseBody);
    }
}
