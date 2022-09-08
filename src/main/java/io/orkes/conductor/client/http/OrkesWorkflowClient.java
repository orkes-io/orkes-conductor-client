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

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.http.WorkflowClient;

import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;

import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;

public class OrkesWorkflowClient extends WorkflowClient implements OrkesClient {
    public OrkesWorkflowClient() {}

    public OrkesWorkflowClient(ClientConfig config) {
        super(config);
    }

    public OrkesWorkflowClient(ClientConfig config, ClientHandler handler) {
        super(config, handler);
    }

    public OrkesWorkflowClient(
            ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        super(config, handler, filters);
    }

    public OrkesWorkflowClient(
            ClientConfig config,
            ConductorClientConfiguration clientConfiguration,
            ClientHandler handler,
            ClientFilter... filters) {
        super(config, clientConfiguration, handler, filters);
    }

    @Override
    public void withCredentials(String keyId, String secret) {
        this.client.addFilter(new AuthorizationClientFilter(root, keyId, secret));
    }
}
