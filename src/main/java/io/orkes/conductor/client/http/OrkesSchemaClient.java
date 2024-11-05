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
import io.orkes.conductor.client.SchemaClient;
import io.orkes.conductor.client.http.api.SchemaResourceApi;
import io.orkes.conductor.client.model.SchemaDef;

import java.util.List;
import java.util.concurrent.*;

public class OrkesSchemaClient extends SchemaClient implements AutoCloseable {

    protected ApiClient apiClient;

    private final SchemaResourceApi httpClient;

    private ExecutorService executorService;

    public OrkesSchemaClient(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.httpClient = new SchemaResourceApi(apiClient);
        if(!apiClient.isUseGRPC()) {
            int threadCount = apiClient.getExecutorThreadCount();
            if(threadCount < 1) {
                this.executorService = Executors.newCachedThreadPool();
            } else {
                this.executorService = Executors.newFixedThreadPool(threadCount);
            }
        }
    }

    public SchemaClient withReadTimeout(int readTimeout) {
        apiClient.setReadTimeout(readTimeout);
        return this;
    }

    public SchemaClient setWriteTimeout(int writeTimeout) {
        apiClient.setWriteTimeout(writeTimeout);
        return this;
    }

    public SchemaClient withConnectTimeout(int connectTimeout) {
        apiClient.setConnectTimeout(connectTimeout);
        return this;
    }

    @Override
    public void close() throws Exception {
        if(executorService != null) {
            executorService.shutdown();
        }
    }

    @Override
    public SchemaDef getSchema(String name, Integer version) {
        // Provide your implementation here
        return httpClient.getSchemaByNameAndVersion(name, version);
    }

    @Override
    public SchemaDef createSchema(SchemaDef inputOutputSchema, boolean newVersion) {
        // Provide your implementation here
        return httpClient.saveSchema(inputOutputSchema, newVersion);
    }

    @Override
    public void deleteSchema(String name) {
        // Provide your implementation here
        httpClient.deleteSchemaByName(name);
    }

    @Override
    public void deleteSchema(String name, Integer version) {
        // Provide your implementation here
        schemaResourceApi.deleteSchemaByNameAndVersion(name, version);
    }


}
