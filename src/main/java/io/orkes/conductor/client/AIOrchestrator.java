package io.orkes.conductor.client;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.integration.Category;
import io.orkes.conductor.client.model.integration.Integration;
import io.orkes.conductor.client.model.integration.IntegrationApi;
import io.orkes.conductor.client.model.integration.IntegrationApiUpdate;
import io.orkes.conductor.client.model.integration.IntegrationConfig;
import io.orkes.conductor.client.model.integration.IntegrationUpdate;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

public class AIOrchestrator {
    private IntegrationClient integrationClient;
    private WorkflowClient workflowClient;
    private WorkflowExecutor workflowExecutor;
    private PromptClient promptClient;
    private String promptTestWorkflowName;

    public enum VectorDB {
        PINECONE_DB("pineconedb"),
        WEAVIATE_DB("weaviatedb");

        private final String value;

        private VectorDB(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum LLMProvider {
        AZURE_OPEN_AI("azure_openai"),
        OPEN_AI("openai"),
        GCP_VERTEX_AI("vertex_ai"),
        HUGGING_FACE("huggingface");

        private final String value;

        private LLMProvider(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public AIOrchestrator(ApiClient apiConfiguration, String promptTestWorkflowName) {
        OrkesClients orkesClients = new OrkesClients(apiConfiguration);
        this.integrationClient = orkesClients.getIntegrationClient();
        this.workflowClient = orkesClients.getWorkflowClient();
        this.promptClient = orkesClients.getPromptClient();
        this.promptTestWorkflowName = promptTestWorkflowName.isEmpty() ? "prompt_test_" + UUID.randomUUID().toString() : promptTestWorkflowName;
    }

    public AIOrchestrator addPromptTemplate(String name, String promptTemplate, String description) {
        promptClient.savePrompt(name, description, promptTemplate);
        return this;
    }

    public PromptTemplate getPromptTemplate(String templateName) {
        try {
            return promptClient.getPrompt(templateName);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void associatePromptTemplate(String name, String aiIntegration, List<String> aiModels) {
        aiModels.forEach(aiModel -> integrationClient.associatePromptWithIntegration(aiIntegration, aiModel, name));
    }

    public Object testPromptTemplate(String text, Map<String, Object> variables, String aiIntegration, String textCompleteModel, List<String> stopWords, Integer maxTokens, int temperature, int topP) {
        return promptClient.testPrompt(text, variables, aiIntegration, textCompleteModel, temperature, topP, stopWords);
    }

    public void addAIIntegration(String aiIntegrationName, LLMProvider provider, List<String> models, String description, IntegrationConfig config, boolean overwrite) {
        IntegrationUpdate details = new IntegrationUpdate();
        details.setConfiguration(config.toMap());
        details.setType(provider.toString());
        details.setCategory(Category.AI_MODEL);
        details.setEnabled(true);
        details.setDescription(description);
        Integration existingIntegration = integrationClient.getIntegration(aiIntegrationName);
        if (existingIntegration == null || overwrite) {
            integrationClient.saveIntegration(aiIntegrationName, details);
        }
        models.forEach(model -> {
            IntegrationApiUpdate apiDetails = new IntegrationApiUpdate();
            apiDetails.setEnabled(true);
            apiDetails.setDescription(description);
            IntegrationApi existingIntegrationApi = integrationClient.getIntegrationApi(aiIntegrationName, model);
            if (existingIntegrationApi == null || overwrite) {
                integrationClient.saveIntegrationApi(aiIntegrationName, model, apiDetails);
            }
        });
    }

    public void addVectorStore(String dbIntegrationName, VectorDB provider, List<String> indices, IntegrationConfig config, String description, boolean overwrite) {
        IntegrationUpdate vectorDb = new IntegrationUpdate();
        vectorDb.setConfiguration(config.toMap());
        vectorDb.setType(provider.toString());
        vectorDb.setCategory(Category.VECTOR_DB);
        vectorDb.setEnabled(true);
        vectorDb.setDescription(description != null ? description : dbIntegrationName);
        Integration existingIntegration = integrationClient.getIntegration(dbIntegrationName);
        if (existingIntegration == null || overwrite) {
            integrationClient.saveIntegration(dbIntegrationName, vectorDb);
        }
        indices.forEach(index -> {
            IntegrationApiUpdate apiDetails = new IntegrationApiUpdate();
            apiDetails.setEnabled(true);
            apiDetails.setDescription(description);
            IntegrationApi existingIntegrationApi = integrationClient.getIntegrationApi(dbIntegrationName, index);
            if (existingIntegrationApi == null || overwrite) {
                integrationClient.saveIntegrationApi(dbIntegrationName, index, apiDetails);
            }
        });
    }

    public Map<String, Integer> getTokenUsed(String aiIntegration) {
        return integrationClient.getTokenUsageForIntegrationProvider(aiIntegration);
    }

    public int getTokenUsedByModel(String aiIntegration, String model) {
        return integrationClient.getTokenUsageForIntegration(aiIntegration, model);
    }
}
