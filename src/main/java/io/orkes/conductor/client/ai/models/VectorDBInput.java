package io.orkes.conductor.client.ai.models;

import lombok.*;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class VectorDBInput extends LLMWorkerInput {

    private String vectorDB;
    private String index;
    private String namespace;
    private List<Float> embeddings;
    private String query;
    private Map<String, Object> metadata;
}