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
package io.orkes.conductor.client;

import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.api.PromptResourceApi;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.PromptTemplateTestRequest;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

public class OrkesPromptClient implements PromptClient {

    private final PromptResourceApi promptResourceApi;

    public OrkesPromptClient(ApiClient apiClient) {
        this.promptResourceApi = new PromptResourceApi(apiClient);
    }

    @Override
    public void savePrompt(String promptName, String description, String promptTemplate) {
        promptResourceApi.savePromptTemplate(promptTemplate, description, promptName, List.of());
    }

    @Override
    public PromptTemplate getPrompt(String promptName) {
        return promptResourceApi.getPromptTemplate(promptName);
    }

    @Override
    public List<PromptTemplate> getPrompts() {
        return promptResourceApi.getPromptTemplates();
    }

    @Override
    public void deletePrompt(String promptName) {
        promptResourceApi.deletePromptTemplate(promptName);
    }

    @Override
    public List<TagObject> getTagsForPromptTemplate(String promptName) {
        return promptResourceApi.getTagsForPromptTemplate(promptName);
    }

    @Override
    public void updateTagForPromptTemplate(String promptName, List<TagObject> tags) {
        promptResourceApi.putTagForPromptTemplate(tags, promptName);
    }

    @Override
    public void deleteTagForPromptTemplate(String promptName, List<TagObject> tags) {
        promptResourceApi.deleteTagForPromptTemplate(tags, promptName);
    }

    @Override
    public String testPrompt(String promptText, Map<String, Object> variables, String aiIntegration, String textCompleteModel, float temperature, float topP,
        List<String> stopWords) {
        PromptTemplateTestRequest request = new PromptTemplateTestRequest();
        request.setPrompt(promptText);
        request.setLlmProvider(aiIntegration);
        request.setModel(textCompleteModel);
        request.setTemperature((double) temperature);
        request.setTopP((double) topP);
        request.setStopWords(stopWords == null ? List.of() : stopWords);
        request.setPromptVariables(variables);
        return promptResourceApi.testMessageTemplate(request);
    }
}
