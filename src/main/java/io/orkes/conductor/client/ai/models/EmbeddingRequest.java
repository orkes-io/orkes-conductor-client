package io.orkes.conductor.client.ai.models;

import lombok.*;

@Data
public class EmbeddingRequest {
    private String llmProvider;
    private String model;
    private String text;

}
