package io.orkes.conductor.client.model.integration;

import java.util.HashMap;
import java.util.Map;

public class PineconeConfig extends IntegrationConfig {

    private final String apiKey;
    private final String endpoint;
    private final String environment;
    private final String projectName;

    public PineconeConfig(String apiKey, String endpoint, String environment, String projectName) {
        this.apiKey = apiKey != null ? apiKey : System.getenv("PINECONE_API_KEY");
        this.endpoint = endpoint != null ? endpoint : System.getenv("PINECONE_ENDPOINT");
        this.environment = environment != null ? environment : System.getenv("PINECONE_ENV");
        this.projectName = projectName != null ? projectName : System.getenv("PINECONE_PROJECT");
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", apiKey);
        map.put("endpoint", endpoint);
        map.put("projectName", projectName);
        map.put("environment", environment);
        return map;
    }
}
