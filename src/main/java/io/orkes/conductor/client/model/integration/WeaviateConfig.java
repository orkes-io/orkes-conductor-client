package io.orkes.conductor.client.model.integration;

import java.util.HashMap;
import java.util.Map;

public class WeaviateConfig extends IntegrationConfig {

    private final String apiKey;
    private final String endpoint;
    private final String classname;

    public WeaviateConfig(String apiKey, String endpoint, String classname) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.classname = classname;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", apiKey);
        map.put("endpoint", endpoint);
        return map;
    }
}
