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
package io.orkes.conductor.client.http.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.ApiClient;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class AuthorizationClientFilter extends ClientFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationClientFilter.class);

    private static final String AUTHORIZATION_HEADER = "X-Authorization";

    private final ApiClient apiClient;

    public AuthorizationClientFilter(String rootUri, String keyId, String secret) {
        while (rootUri.endsWith("/")) {
            rootUri = rootUri.replaceAll("/$", "");
        }
        this.apiClient = new ApiClient(rootUri, keyId, secret);
    }

    public AuthorizationClientFilter(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
        request.getHeaders().add(AUTHORIZATION_HEADER, apiClient.getToken());
        try {
            ClientResponse clientResponse = getNext().handle(request);
            return clientResponse;
        } catch (ClientHandlerException e) {
            LOGGER.error("Error adding authorization header to request", e);
            throw e;
        }
    }
}
