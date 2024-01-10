package io.orkes.conductor.client.ai.models;

import lombok.*;

import java.util.*;

@Data
public class PromptTemplateTestRequest {

    private String llmProvider;
    private String model;
    private String prompt;
    private Map<String, Object> promptVariables = new HashMap<>();
    private double temperature = 0.1;
    private double topP = 0.9;
    private List<String> stopWords;

}
