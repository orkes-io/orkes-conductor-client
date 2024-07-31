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
package io.orkes.conductor.client.http.clients;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.ConductorUser;
import io.orkes.conductor.client.model.GenerateTokenRequest;
import io.orkes.conductor.client.model.TokenResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.GET;
import static io.orkes.conductor.client.http.clients.OrkesHttpClientRequest.Method.POST;

class TokenResource extends Resource {

    TokenResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public ApiResponse<TokenResponse> generate(GenerateTokenRequest body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(POST)
                .path("/token")
                .body(body)
                .build();

        return httpClient.doRequest(request, new TypeReference<>() {
        });
    }

    public ApiResponse<ConductorUser> getUserInfo() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(GET)
                .path("/token/userInfo")
                .build();

        return httpClient.doRequest(request, new TypeReference<>() {
        });
    }
}
