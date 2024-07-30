/*
 * Copyright 2024 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.http.clients;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.PromptTemplateTestRequest;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Call;

class PromptResource {

    private final OrkesHttpClient httpClient;

    public PromptResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public Call deletePromptTemplateCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call deletePromptTemplateValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deletePromptTemplate(Async)");
        }

        return deletePromptTemplateCall(name);
    }

  
    public void deletePromptTemplate(String name) throws ApiException {
        deletePromptTemplateWithHttpInfo(name);
    }

  
    public ApiResponse<Void> deletePromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = deletePromptTemplateValidateBeforeCall(name);
        return httpClient.execute(call);
    }

  
    public Call deleteTagForPromptTemplateCall(List<TagObject> body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call deleteTagForPromptTemplateValidateBeforeCall(List<TagObject> body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForPromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteTagForPromptTemplate(Async)");
        }

        return deleteTagForPromptTemplateCall(body, name);

    }

  
    public void deleteTagForPromptTemplate(List<TagObject> body, String name) throws ApiException {
        deleteTagForPromptTemplateWithHttpInfo(body, name);
    }

  
    public ApiResponse<Void> deleteTagForPromptTemplateWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = deleteTagForPromptTemplateValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

  
    public Call getPromptTemplateCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    private Call getPromptTemplateValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getPromptTemplate(Async)");
        }

        return getPromptTemplateCall(name);

    }

  
    public PromptTemplate getPromptTemplate(String name) throws ApiException {
        ApiResponse<PromptTemplate> resp = getPromptTemplateWithHttpInfo(name);
        return resp.getData();
    }

  
    public ApiResponse<PromptTemplate> getPromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = getPromptTemplateValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<PromptTemplate>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getPromptTemplatesCall() throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call getPromptTemplatesValidateBeforeCall() throws ApiException {
        return getPromptTemplatesCall();
    }

  
    public List<PromptTemplate> getPromptTemplates() throws ApiException {
        ApiResponse<List<PromptTemplate>> resp = getPromptTemplatesWithHttpInfo();
        return resp.getData();
    }

  
    public ApiResponse<List<PromptTemplate>> getPromptTemplatesWithHttpInfo() throws ApiException {
        Call call = getPromptTemplatesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<PromptTemplate>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getTagsForPromptTemplateCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call getTagsForPromptTemplateValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTagsForPromptTemplate(Async)");
        }

        return getTagsForPromptTemplateCall(name);

    }

  
    public List<TagObject> getTagsForPromptTemplate(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForPromptTemplateWithHttpInfo(name);
        return resp.getData();
    }

  
    public ApiResponse<List<TagObject>> getTagsForPromptTemplateWithHttpInfo(String name) throws ApiException {
        Call call = getTagsForPromptTemplateValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call putTagForPromptTemplateCall(List<TagObject> body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/prompts/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    private Call putTagForPromptTemplateValidateBeforeCall(List<TagObject> body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForPromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling putTagForPromptTemplate(Async)");
        }

        return putTagForPromptTemplateCall(body, name);
    }

  
    public void putTagForPromptTemplate(List<TagObject> body, String name) throws ApiException {
        putTagForPromptTemplateWithHttpInfo(body, name);
    }

  
    public ApiResponse<Void> putTagForPromptTemplateWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = putTagForPromptTemplateValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

  

  
    public Call savePromptTemplateCall(String body, String description, String name, List<String> models)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/prompts/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (description != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("description", description));
        }
        if (models != null) {
            localVarCollectionQueryParams.addAll(httpClient.parameterToPairs("multi", "models", models));
        }

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call savePromptTemplateValidateBeforeCall(String body, String description, String name, List<String> models)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling savePromptTemplate(Async)");
        }
        // verify the required parameter 'description' is set
        if (description == null) {
            throw new ApiException("Missing the required parameter 'description' when calling savePromptTemplate(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling savePromptTemplate(Async)");
        }

        return savePromptTemplateCall(body, description, name, models);

    }

  
    public void savePromptTemplate(String body, String description, String name, List<String> models) throws ApiException {
        savePromptTemplateWithHttpInfo(body, description, name, models);
    }

  
    public ApiResponse<Void> savePromptTemplateWithHttpInfo(String body, String description, String name, List<String> models) throws ApiException {
        Call call = savePromptTemplateValidateBeforeCall(body, description, name, models);
        return httpClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private Call testMessageTemplateValidateBeforeCall(PromptTemplateTestRequest body) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling testMessageTemplate(Async)");
        }

        return testMessageTemplateCall(body);

    }

    public Call testMessageTemplateCall(PromptTemplateTestRequest body) throws ApiException {

        // create path and map variables
        String localVarPath = "/prompts/test";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

  
    public String testMessageTemplate(PromptTemplateTestRequest body) throws ApiException {
        ApiResponse<String> resp = testMessageTemplateWithHttpInfo(body);
        return resp.getData();
    }

  
    public ApiResponse<String> testMessageTemplateWithHttpInfo(PromptTemplateTestRequest body) throws ApiException {
        Call call = testMessageTemplateValidateBeforeCall(body);
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

}
