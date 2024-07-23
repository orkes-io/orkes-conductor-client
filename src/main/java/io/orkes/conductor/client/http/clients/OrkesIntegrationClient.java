/*
 * Copyright 2024 Orkes, Inc.
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
import java.util.Map;

import io.orkes.conductor.client.IntegrationClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.Integration;
import io.orkes.conductor.client.model.integration.IntegrationApi;
import io.orkes.conductor.client.model.integration.IntegrationApiUpdate;
import io.orkes.conductor.client.model.integration.IntegrationUpdate;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

public class OrkesIntegrationClient extends OrkesClient implements IntegrationClient {
    private final IntegrationResource integrationResource;

    public OrkesIntegrationClient(OrkesHttpClient httpClient) {
        super(httpClient);
        this.integrationResource = new IntegrationResource(httpClient);
    }

    public void associatePromptWithIntegration(String aiIntegration, String modelName, String promptName) {
        integrationResource.associatePromptWithIntegration(aiIntegration, modelName, promptName);
    }

    public void deleteIntegrationApi(String apiName, String integrationName) {
        integrationResource.deleteIntegrationApi(apiName, integrationName);
    }

    public void deleteIntegration(String integrationName) {
        integrationResource.deleteIntegrationProvider(integrationName);
    }

    public IntegrationApi getIntegrationApi(String apiName, String integrationName) throws ApiException {
        try {
            return integrationResource.getIntegrationApi(apiName, integrationName);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public List<IntegrationApi> getIntegrationApis(String integrationName) {
        return integrationResource.getIntegrationApis(integrationName, true);
    }

    public Integration getIntegration(String integrationName) throws ApiException {
        try {
            return integrationResource.getIntegrationProvider(integrationName);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public List<Integration> getIntegrations(String category, Boolean activeOnly) {
        return integrationResource.getIntegrationProviders(category, activeOnly);
    }

    public List<PromptTemplate> getPromptsWithIntegration(String aiIntegration, String modelName) {
        return integrationResource.getPromptsWithIntegration(aiIntegration, modelName);
    }

    public void saveIntegrationApi(String integrationName, String apiName, IntegrationApiUpdate apiDetails) {
        integrationResource.saveIntegrationApi(apiDetails, integrationName, apiName);
    }

    public void saveIntegration(String integrationName, IntegrationUpdate integrationDetails) {
        integrationResource.saveIntegrationProvider(integrationDetails, integrationName);
    }

    public int getTokenUsageForIntegration(String name, String integrationName) {
        return integrationResource.getTokenUsageForIntegration(name, integrationName);
    }

    public Map<String, Integer> getTokenUsageForIntegrationProvider(String name) {
        return integrationResource.getTokenUsageForIntegrationProvider(name);
    }

    // Tags - Implementations are assumed to be placeholders

    public void deleteTagForIntegrationProvider(List<TagObject> tags, String name) {
        integrationResource.deleteTagForIntegrationProvider(tags, name);
    }
    public void saveTagForIntegrationProvider(List<TagObject> tags, String name) {
        integrationResource.putTagForIntegrationProvider(tags, name);
    }

    public List<TagObject> getTagsForIntegrationProvider(String name) {
        return integrationResource.getTagsForIntegrationProvider(name);
    }
}
