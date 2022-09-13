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
package io.orkes.conductor.client;

import com.netflix.conductor.client.config.DefaultConductorClientConfiguration;

import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class OrkesClients {

    private final ApiClient apiClient;

    public OrkesClients(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public WorkflowClient getWorkflowClient() {
        return new OrkesWorkflowClient(apiClient);
    }

    public AuthorizationClient getAuthorizationClient() {
        return new OrkesAuthorizationClient(apiClient);
    }

    public EventClient getEventClient() {
        return new OrkesEventClient(apiClient);
    }

    public MetadataClient getMetadataClient() {
        return new OrkesMetadataClient(apiClient);
    }

    public SchedulerClient getSchedulerClient() {
        return new OrkesSchedulerClient(apiClient);
    }

    public SecretClient getSecretClient() {
        return new OrkesSecretClient(apiClient);
    }

    public TaskClient getTaskClient() {
        return new OrkesTaskClient(apiClient);
    }

    public com.netflix.conductor.client.http.WorkflowClient getWorkflowClientLegacy() {
        return getWorkflowClientLegacy(10);
    }

    public com.netflix.conductor.client.http.WorkflowClient getWorkflowClientLegacy(
            int threadPoolSize) {
        AuthorizationClientFilter authorizationClientFilter =
                new AuthorizationClientFilter(apiClient);
        com.netflix.conductor.client.http.WorkflowClient workflowClient =
                new com.netflix.conductor.client.http.WorkflowClient(
                        conductorClientConfig(
                                apiClient.getReadTimeout(),
                                apiClient.getConnectTimeout(),
                                threadPoolSize),
                        new DefaultConductorClientConfiguration(),
                        null,
                        authorizationClientFilter);
        String basePath = apiClient.getBasePath();
        if (!basePath.endsWith("/")) {
            basePath = basePath + "/";
        }
        workflowClient.setRootURI(basePath);

        return workflowClient;
    }

    public com.netflix.conductor.client.http.TaskClient getTaskClientLegacy() {
        return getTaskClientLegacy(10);
    }

    public com.netflix.conductor.client.http.TaskClient getTaskClientLegacy(int threadPoolSize) {
        AuthorizationClientFilter authorizationClientFilter =
                new AuthorizationClientFilter(apiClient);
        com.netflix.conductor.client.http.TaskClient taskClient =
                new com.netflix.conductor.client.http.TaskClient(
                        conductorClientConfig(
                                apiClient.getReadTimeout(),
                                apiClient.getConnectTimeout(),
                                threadPoolSize),
                        new DefaultConductorClientConfiguration(),
                        null,
                        authorizationClientFilter);
        String basePath = apiClient.getBasePath();
        if (!basePath.endsWith("/")) {
            basePath = basePath + "/";
        }
        taskClient.setRootURI(basePath);

        return taskClient;
    }

    private ClientConfig conductorClientConfig(
            int readTimeout, int connectionTimeout, int threadPoolSize) {
        var clientConfig = new DefaultClientConfig();
        var clientConfigProps = clientConfig.getProperties();
        clientConfigProps.put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, connectionTimeout);
        clientConfigProps.put(ClientConfig.PROPERTY_READ_TIMEOUT, readTimeout);
        clientConfigProps.put(ClientConfig.PROPERTY_THREADPOOL_SIZE, threadPoolSize);
        return clientConfig;
    }
}
