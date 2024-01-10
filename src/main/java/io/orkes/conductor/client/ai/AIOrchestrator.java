package io.orkes.conductor.client.ai;

import com.netflix.conductor.sdk.workflow.def.*;

import java.util.*;
import java.util.concurrent.*;

public interface AIOrchestrator {

    //Prompt Management
    void addPromptTemplate(String name, String template);
    void updatePromptTemplate(String name, String template);
    void deletePromptTemplate(String name);
    void getPromptTemplate(String name);

    //Prompt Testing
    void testPromptTemplate(String template, Map<String, Object> variables, ModelOptions options);
    void testPromptTemplate(String template, Map<String, Object> variables, String integrationName, String modelName, ModelOptions options);

    //AI Model management
    void addIntegration(String name, ModelProvider modelProvider, String apiKey, Map<String, Object> config);
    void removeIntegration(String name);
    Map<String, Object> getIntegration(String name);
    void addModel(String integrationName, String modelName, Map<String, Object> config);

    //Associate model with prompt
    void associateModel(String promptTemplateName, String integrationName, String modelName);

    /**
     * I have 10,000 documents in my local drive, I want to get them all indexed in a vector database
     * and after indexing, bring up and endpoint that I can use to ask questions
     **/

    /**
     * Case 1: Index 10,000 document
     * Language: Python
     * Steps:
     * 1. I will upload each document to an s3 bucket
     * 2. I will kick off a workflow that
     */
}