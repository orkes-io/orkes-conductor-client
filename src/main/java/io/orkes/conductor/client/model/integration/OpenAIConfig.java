package io.orkes.conductor.client.model.integration;

import java.util.HashMap;
import java.util.Map;

public class OpenAIConfig extends IntegrationConfig {

    private final String apiKey;

    public OpenAIConfig(String apiKey) {
        this.apiKey = apiKey != null ? apiKey : System.getenv("OPENAI_API_KEY");
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", apiKey);
        return map;
    }
}
