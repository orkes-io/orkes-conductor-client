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

import io.orkes.conductor.client.api.AuthorizationClient;
import io.orkes.conductor.client.api.EventClient;
import io.orkes.conductor.client.api.IntegrationClient;
import io.orkes.conductor.client.api.MetadataClient;
import io.orkes.conductor.client.api.PromptClient;
import io.orkes.conductor.client.api.SchedulerClient;
import io.orkes.conductor.client.api.SecretClient;
import io.orkes.conductor.client.api.TaskClient;
import io.orkes.conductor.client.api.WorkflowClient;
import io.orkes.conductor.client.http.clients.OrkesAuthorizationClient;
import io.orkes.conductor.client.http.clients.OrkesEventClient;
import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import io.orkes.conductor.client.http.clients.OrkesIntegrationClient;
import io.orkes.conductor.client.http.clients.OrkesMetadataClient;
import io.orkes.conductor.client.http.clients.OrkesPromptClient;
import io.orkes.conductor.client.http.clients.OrkesSchedulerClient;
import io.orkes.conductor.client.http.clients.OrkesSecretClient;
import io.orkes.conductor.client.http.clients.OrkesTaskClient;
import io.orkes.conductor.client.http.clients.OrkesWorkflowClient;

public class OrkesClients {

    private final OrkesHttpClient httpClient;

    public OrkesClients(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public WorkflowClient getWorkflowClient() {
        return new OrkesWorkflowClient(httpClient);
    }

    public AuthorizationClient getAuthorizationClient() {
        return new OrkesAuthorizationClient(httpClient);
    }

    public EventClient getEventClient() {
        return new OrkesEventClient(httpClient);
    }

    public MetadataClient getMetadataClient() {
        return new OrkesMetadataClient(httpClient);
    }

    public SchedulerClient getSchedulerClient() {
        return new OrkesSchedulerClient(httpClient);
    }

    public SecretClient getSecretClient() {
        return new OrkesSecretClient(httpClient);
    }

    public TaskClient getTaskClient() {
        return new OrkesTaskClient(httpClient);
    }

    public IntegrationClient getIntegrationClient() {
        return new OrkesIntegrationClient(httpClient);
    }

    public PromptClient getPromptClient() {
        return new OrkesPromptClient(httpClient);
    }
}
