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

import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.http.*;

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

    public IntegrationClient getIntegrationClient() {
        return new OrkesIntegrationClient(apiClient);
    }

    public PromptClient getPromptClient() {
        return new OrkesPromptClient(apiClient);
    }

    public WorkflowExecutor getWorkflowExecutor() {
        return new WorkflowExecutor(getTaskClient(), getWorkflowClient(), getMetadataClient(), 100);
    }
}
