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

import io.orkes.conductor.client.ApiClient;

public abstract class OrkesClient {

    protected ApiClient apiClient;

    public OrkesClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public OrkesClient withReadTimeout(int readTimeout) {
        apiClient.setReadTimeout(readTimeout);
        return this;
    }

    public OrkesClient setWriteTimeout(int writeTimeout) {
        apiClient.setWriteTimeout(writeTimeout);
        return this;
    }

    public OrkesClient withConnectTimeout(int connectTimeout) {
        apiClient.setConnectTimeout(connectTimeout);
        return this;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}
