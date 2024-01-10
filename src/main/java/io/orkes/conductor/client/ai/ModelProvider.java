package io.orkes.conductor.client.ai;

public enum ModelProvider {
    OpenAI("Open AI"), Azure_OpenAI(""), VertexAI(""),HuggingFace("");

    ModelProvider(String modelName) {
        this.modelName = modelName;
    }
    private String modelName;
    public String modelName() {
        return modelName;
    }
}
