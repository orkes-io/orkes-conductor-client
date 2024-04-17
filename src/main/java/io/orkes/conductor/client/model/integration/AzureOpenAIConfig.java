package io.orkes.conductor.client.model.integration;

import java.util.HashMap;
import java.util.Map;

public class AzureOpenAIConfig extends IntegrationConfig {

    private final String apiKey;
    private final String endpoint;

    public AzureOpenAIConfig(String apiKey, String endpoint) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", apiKey);
        map.put("endpoint", endpoint);
        return map;
    }
}
