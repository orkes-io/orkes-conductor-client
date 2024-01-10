package io.orkes.conductor.client.ai.models;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexDocInput {

    private String llmProvider;
    private String model;
    private String embeddingModelProvider;
    private String embeddingModel;
    private String vectorDB;
    private String text;
    private String docId;
    private String url;
    private String mediaType;
    private String namespace;
    private String index;
    private int chunkSize;
    private int chunkOverlap;
    private Map<String, Object> metadata;
    public String getNamespace() {
        if(namespace == null) {
            return docId;
        }
        return namespace;
    }

    public int getChunkSize() {
        return chunkSize > 0 ? chunkSize : 12000;
    }

    public int getChunkOverlap() {
        return chunkOverlap > 0 ? chunkOverlap : 400;
    }
}
