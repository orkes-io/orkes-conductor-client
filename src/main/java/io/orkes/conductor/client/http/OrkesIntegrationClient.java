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
package io.orkes.conductor.client.http;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.IntegrationClient;
import io.orkes.conductor.client.http.api.IntegrationResourceApi;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.Integration;
import io.orkes.conductor.client.model.integration.IntegrationApi;
import io.orkes.conductor.client.model.integration.IntegrationApiUpdate;
import io.orkes.conductor.client.model.integration.IntegrationUpdate;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

public class OrkesIntegrationClient extends OrkesClient implements IntegrationClient {
    private IntegrationResourceApi integrationApi;

    public OrkesIntegrationClient(ApiClient apiClient) {
        super(apiClient);
        this.integrationApi = new IntegrationResourceApi(apiClient);
    }

    public void associatePromptWithIntegration(String aiIntegration, String modelName, String promptName) {
        integrationApi.associatePromptWithIntegration(aiIntegration, modelName, promptName);
    }

    public void deleteIntegrationApi(String apiName, String integrationName) {
        integrationApi.deleteIntegrationApi(apiName, integrationName);
    }

    public void deleteIntegration(String integrationName) {
        integrationApi.deleteIntegrationProvider(integrationName);
    }

    public IntegrationApi getIntegrationApi(String apiName, String integrationName) throws ApiException {
        try {
            return integrationApi.getIntegrationApi(apiName, integrationName);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public List<IntegrationApi> getIntegrationApis(String integrationName) {
        return integrationApi.getIntegrationApis(integrationName, true);
    }

    public Integration getIntegration(String integrationName) throws ApiException {
        try {
            return integrationApi.getIntegrationProvider(integrationName);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public List<Integration> getIntegrations(String category, Boolean activeOnly) {
        return integrationApi.getIntegrationProviders(category, activeOnly);
    }

    public List<PromptTemplate> getPromptsWithIntegration(String aiIntegration, String modelName) {
        return integrationApi.getPromptsWithIntegration(aiIntegration, modelName);
    }

    public void saveIntegrationApi(String integrationName, String apiName, IntegrationApiUpdate apiDetails) {
        integrationApi.saveIntegrationApi(apiDetails, integrationName, apiName);
    }

    public void saveIntegration(String integrationName, IntegrationUpdate integrationDetails) {
        integrationApi.saveIntegrationProvider(integrationDetails, integrationName);
    }

    public int getTokenUsageForIntegration(String name, String integrationName) {
        return integrationApi.getTokenUsageForIntegration(name, integrationName);
    }

    public Map<String, Integer> getTokenUsageForIntegrationProvider(String name) {
        return integrationApi.getTokenUsageForIntegrationProvider(name);
    }

    // Tags - Implementations are assumed to be placeholders

    public void deleteTagForIntegrationProvider(List<TagObject> tags, String name) {
        integrationApi.deleteTagForIntegrationProvider(tags, name);
    }
    public void saveTagForIntegrationProvider(List<TagObject> tags, String name) {
        integrationApi.putTagForIntegrationProvider(tags, name);
    }

    public List<TagObject> getTagsForIntegrationProvider(String name) {
        return integrationApi.getTagsForIntegrationProvider(name);
    }
}
