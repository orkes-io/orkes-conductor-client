package io.orkes.conductor.client.ai;

import lombok.*;

import java.util.*;

@Data
@Builder
public class ModelOptions {
    private double temperature;
    private List<String> stopWords = new ArrayList<>();
    private double topP;
    private int maxTokens;

    public static ModelOptions getDefault() {
        return ModelOptions.builder().temperature(0.1).topP(0.9).maxTokens(1000).build();
    }
}
