package io.orkes.conductor.client.ai.models;

import lombok.*;

import java.util.*;

@Data
public class LLMWorkerInput {

    private String llmProvider;
    private String model;
    private String embeddingModel;
    private String embeddingModelProvider;
    private String prompt;
    private double temperature = 0.1;
    private double topP = 0.9;
    private List<String> stopWords;
    private int maxTokens;
}
