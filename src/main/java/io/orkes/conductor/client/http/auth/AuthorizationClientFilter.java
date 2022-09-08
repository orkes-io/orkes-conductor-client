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

import io.orkes.conductor.client.http.ApiClient;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class AuthorizationClientFilter extends ClientFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationClientFilter.class);

    private final int MAX_ATTEMPTS = 2;

    private static final String AUTHORIZATION_HEADER = "X-AUTHORIZATION";

    private final ApiClient apiClient;

    public AuthorizationClientFilter(String rootUri, String keyId, String secret) {
        this.apiClient = new ApiClient(rootUri, keyId, secret);
    }

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
        for (int attempt = 0; attempt < this.MAX_ATTEMPTS; attempt += 1) {
            try {
                final String token = apiClient.getNewToken();
                if (token != null) {
                    request.getHeaders().add(AUTHORIZATION_HEADER, token);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to refresh token. Reason: ", e.getMessage());
                continue;
            }
            try {
                ClientResponse clientResponse = getNext().handle(request);
                if (clientResponse.getStatus() != 401) {
                    return clientResponse;
                }
            } catch (ClientHandlerException e) {
                LOGGER.error("Error adding authorization header to request", e);
                throw e;
            }
        }
        return null;
    }
}
