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

import java.util.List;
import java.util.Set;

import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.model.TagObject;

import com.fasterxml.jackson.core.type.TypeReference;

class SecretResource {
    private OrkesHttpClient httpClient;

    public SecretResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void deleteSecret(String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/secrets/{key}")
                .addPathParam("key", key)
                .build();

        httpClient.doRequest(request);
    }

    public String getSecret(String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/secrets/{key}")
                .addPathParam("key", key)
                .build();

        ApiResponse<String> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();

    }

    //TODO why SET?
    public Set<String> listAllSecretNames() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/secrets")
                .build();

        ApiResponse<Set<String>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }


    public List<String> listSecretsThatUserCanGrantAccessTo() {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/secrets")
                .build();

        ApiResponse<List<String>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void putSecret(String body, String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/secrets/{key}")
                .addPathParam("key", key)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }

    public Boolean secretExists(String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/secrets/{key}/exists")
                .addPathParam("key", key)
                .build();

        ApiResponse<Boolean> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }

    public void putTagForSecret(String key, List<TagObject> body) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.PUT)
                .path("/secrets/{key}/tags")
                .addPathParam("key", key)
                .body(body)
                .build();

        httpClient.doRequest(request);

    }

    public void deleteTagForSecret(List<TagObject> body, String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.DELETE)
                .path("/secrets/{key}/tags")
                .addPathParam("key", key)
                .body(body)
                .build();

        httpClient.doRequest(request);
    }


    public List<TagObject> getTags(String key) {
        OrkesHttpClientRequest request = OrkesHttpClientRequest.builder()
                .method(OrkesHttpClientRequest.Method.GET)
                .path("/secrets/{key}/tags")
                .addPathParam("key", key)
                .build();

        ApiResponse<List<TagObject>> resp = httpClient.doRequest(request, new TypeReference<>() {
        });

        return resp.getData();
    }
}
