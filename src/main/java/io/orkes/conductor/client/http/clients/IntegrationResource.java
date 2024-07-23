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

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.integration.Integration;
import io.orkes.conductor.client.model.integration.IntegrationApi;
import io.orkes.conductor.client.model.integration.IntegrationApiUpdate;
import io.orkes.conductor.client.model.integration.IntegrationDef;
import io.orkes.conductor.client.model.integration.IntegrationUpdate;
import io.orkes.conductor.client.model.integration.ai.PromptTemplate;
import okhttp3.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class IntegrationResource {

    private final OrkesHttpClient httpClient;

    public IntegrationResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Build call for associatePromptWithIntegration
     *
     * @param integrationProvider     (required)
     * @param integrationName         (required)
     * @param promptName              (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call associatePromptWithIntegrationCall(String integrationProvider, String integrationName, String promptName)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{integration_provider}/integration/{integration_name}/prompt/{prompt_name}".replaceAll(
                        "\\{" + "integration_provider" + "\\}", httpClient.escapeString(integrationProvider))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName))
                .replaceAll("\\{" + "prompt_name" + "\\}", httpClient.escapeString(promptName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
        return httpClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
                localVarFormParams, localVarAuthNames);
    }

    @SuppressWarnings("rawtypes")
    private Call associatePromptWithIntegrationValidateBeforeCall(String integrationProvider, String integrationName, String promptName)
            throws ApiException {
        // verify the required parameter 'integrationProvider' is set
        if (integrationProvider == null) {
            throw new ApiException("Missing the required parameter 'integrationProvider' when calling associatePromptWithIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling associatePromptWithIntegration(Async)");
        }
        // verify the required parameter 'promptName' is set
        if (promptName == null) {
            throw new ApiException("Missing the required parameter 'promptName' when calling associatePromptWithIntegration(Async)");
        }

        return associatePromptWithIntegrationCall(integrationProvider, integrationName, promptName);

    }

    /**
     * Associate a Prompt Template with an Integration
     *
     * @param integrationProvider (required)
     * @param integrationName     (required)
     * @param promptName          (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void associatePromptWithIntegration(String integrationProvider, String integrationName, String promptName) throws ApiException {
        associatePromptWithIntegrationWithHttpInfo(integrationProvider, integrationName, promptName);
    }

    /**
     * Associate a Prompt Template with an Integration
     *
     * @param integrationProvider (required)
     * @param integrationName     (required)
     * @param promptName          (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> associatePromptWithIntegrationWithHttpInfo(String integrationProvider, String integrationName, String promptName)
            throws ApiException {
        Call call = associatePromptWithIntegrationValidateBeforeCall(integrationProvider, integrationName, promptName);
        return httpClient.execute(call);
    }

    /**
     * Build call for deleteIntegrationApi
     *
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteIntegrationApiCall(String name, String integrationName) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call deleteIntegrationApiValidateBeforeCall(String name, String integrationName) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteIntegrationApi(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling deleteIntegrationApi(Async)");
        }

        return deleteIntegrationApiCall(name, integrationName);

    }

    /**
     * Delete an Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteIntegrationApi(String name, String integrationName) throws ApiException {
        deleteIntegrationApiWithHttpInfo(name, integrationName);
    }

    /**
     * Delete an Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deleteIntegrationApiWithHttpInfo(String name, String integrationName) throws ApiException {
        Call call = deleteIntegrationApiValidateBeforeCall(name, integrationName);
        return httpClient.execute(call);
    }

    /**
     * Build call for deleteIntegrationProvider
     *
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteIntegrationProviderCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call deleteIntegrationProviderValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteIntegrationProvider(Async)");
        }

        return deleteIntegrationProviderCall(name);

    }

    /**
     * Delete an Integration Provider
     *
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteIntegrationProvider(String name) throws ApiException {
        deleteIntegrationProviderWithHttpInfo(name);
    }

    /**
     * Delete an Integration Provider
     *
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deleteIntegrationProviderWithHttpInfo(String name) throws ApiException {
        Call call = deleteIntegrationProviderValidateBeforeCall(name);
        return httpClient.execute(call);
    }

    /**
     * Build call for deleteTagForIntegration
     *
     * @param body                    (required)
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteTagForIntegrationCall(List<TagObject> body, String name, String integrationName)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}/tags".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call deleteTagForIntegrationValidateBeforeCall(List<TagObject> body, String name, String integrationName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForIntegration(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteTagForIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling deleteTagForIntegration(Async)");
        }

        return deleteTagForIntegrationCall(body, name, integrationName);

    }

    /**
     * Delete a tag for Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForIntegration(List<TagObject> body, String name, String integrationName) throws ApiException {
        deleteTagForIntegrationWithHttpInfo(body, name, integrationName);
    }

    /**
     * Delete a tag for Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deleteTagForIntegrationWithHttpInfo(List<TagObject> body, String name, String integrationName) throws ApiException {
        Call call = deleteTagForIntegrationValidateBeforeCall(body, name, integrationName);
        return httpClient.execute(call);
    }

    /**
     * Build call for deleteTagForIntegrationProvider
     *
     * @param body                    (required)
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteTagForIntegrationProviderCall(List<TagObject> body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call deleteTagForIntegrationProviderValidateBeforeCall(List<TagObject> body, String name)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForIntegrationProvider(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteTagForIntegrationProvider(Async)");
        }

        return deleteTagForIntegrationProviderCall(body, name);

    }

    /**
     * Delete a tag for Integration Provider
     *
     * @param body (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForIntegrationProvider(List<TagObject> body, String name) throws ApiException {
        deleteTagForIntegrationProviderWithHttpInfo(body, name);
    }

    /**
     * Delete a tag for Integration Provider
     *
     * @param body (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> deleteTagForIntegrationProviderWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = deleteTagForIntegrationProviderValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

    /**
     * Build call for getIntegrationApi
     *
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationApiCall(String name, String integrationName) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getIntegrationApiValidateBeforeCall(String name, String integrationName) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getIntegrationApi(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling getIntegrationApi(Async)");
        }

        return getIntegrationApiCall(name, integrationName);

    }

    /**
     * Get Integration details
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return IntegrationApi
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public IntegrationApi getIntegrationApi(String name, String integrationName) throws ApiException {
        ApiResponse<IntegrationApi> resp = getIntegrationApiWithHttpInfo(name, integrationName);
        return resp.getData();
    }

    /**
     * Get Integration details
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;IntegrationApi&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<IntegrationApi> getIntegrationApiWithHttpInfo(String name, String integrationName) throws ApiException {
        Call call = getIntegrationApiValidateBeforeCall(name, integrationName);
        Type localVarReturnType = new TypeReference<IntegrationApi>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getIntegrationApis
     *
     * @param name                    (required)
     * @param activeOnly              (optional, default to true)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationApisCall(String name, Boolean activeOnly) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (activeOnly != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("activeOnly", activeOnly));
        }

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
    private Call getIntegrationApisValidateBeforeCall(String name, Boolean activeOnly) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getIntegrationApis(Async)");
        }

        return getIntegrationApisCall(name, activeOnly);

    }

    /**
     * Get Integrations of an Integration Provider
     *
     * @param name       (required)
     * @param activeOnly (optional, default to true)
     * @return List&lt;IntegrationApi&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<IntegrationApi> getIntegrationApis(String name, Boolean activeOnly) throws ApiException {
        ApiResponse<List<IntegrationApi>> resp = getIntegrationApisWithHttpInfo(name, activeOnly);
        return resp.getData();
    }

    /**
     * Get Integrations of an Integration Provider
     *
     * @param name       (required)
     * @param activeOnly (optional, default to true)
     * @return ApiResponse&lt;List&lt;IntegrationApi&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<IntegrationApi>> getIntegrationApisWithHttpInfo(String name, Boolean activeOnly) throws ApiException {
        Call call = getIntegrationApisValidateBeforeCall(name, activeOnly);
        Type localVarReturnType = new TypeReference<List<IntegrationApi>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getIntegrationAvailableApis
     *
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationAvailableApisCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/all".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getIntegrationAvailableApisValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getIntegrationAvailableApis(Async)");
        }

        return getIntegrationAvailableApisCall(name);

    }

    /**
     * Get Integrations Available for an Integration Provider
     *
     * @param name (required)
     * @return List&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<String> getIntegrationAvailableApis(String name) throws ApiException {
        ApiResponse<List<String>> resp = getIntegrationAvailableApisWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get Integrations Available for an Integration Provider
     *
     * @param name (required)
     * @return ApiResponse&lt;List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<String>> getIntegrationAvailableApisWithHttpInfo(String name) throws ApiException {
        Call call = getIntegrationAvailableApisValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<List<String>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getIntegrationProvider
     *
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationProviderCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getIntegrationProviderValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getIntegrationProvider(Async)");
        }

        return getIntegrationProviderCall(name);

    }

    /**
     * Get Integration provider
     *
     * @param name (required)
     * @return Integration
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Integration getIntegrationProvider(String name) throws ApiException {
        ApiResponse<Integration> resp = getIntegrationProviderWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get Integration provider
     *
     * @param name (required)
     * @return ApiResponse&lt;Integration&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Integration> getIntegrationProviderWithHttpInfo(String name) throws ApiException {
        Call call = getIntegrationProviderValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<Integration>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getIntegrationProviderDefs
     *

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationProviderDefsCall() throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/def";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getIntegrationProviderDefsValidateBeforeCall() throws ApiException {
        return getIntegrationProviderDefsCall();
    }

    /**
     * Get Integration provider definitions
     *
     * @return List&lt;IntegrationDef&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<IntegrationDef> getIntegrationProviderDefs() throws ApiException {
        ApiResponse<List<IntegrationDef>> resp = getIntegrationProviderDefsWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get Integration provider definitions
     *
     * @return ApiResponse&lt;List&lt;IntegrationDef&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<IntegrationDef>> getIntegrationProviderDefsWithHttpInfo() throws ApiException {
        Call call = getIntegrationProviderDefsValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<IntegrationDef>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getIntegrationProviders
     *
     * @param category                (optional)
     * @param activeOnly              (optional, default to true)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getIntegrationProvidersCall(String category, Boolean activeOnly) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (category != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("category", category));
        }
        if (activeOnly != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("activeOnly", activeOnly));
        }

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
    private Call getIntegrationProvidersValidateBeforeCall(String category, Boolean activeOnly) throws ApiException {

        return getIntegrationProvidersCall(category, activeOnly);

    }

    /**
     * Get all Integrations Providers
     *
     * @param category   (optional)
     * @param activeOnly (optional, default to true)
     * @return List&lt;Integration&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Integration> getIntegrationProviders(String category, Boolean activeOnly) throws ApiException {
        ApiResponse<List<Integration>> resp = getIntegrationProvidersWithHttpInfo(category, activeOnly);
        return resp.getData();
    }

    /**
     * Get all Integrations Providers
     *
     * @param category   (optional)
     * @param activeOnly (optional, default to true)
     * @return ApiResponse&lt;List&lt;Integration&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Integration>> getIntegrationProvidersWithHttpInfo(String category, Boolean activeOnly) throws ApiException {
        Call call = getIntegrationProvidersValidateBeforeCall(category, activeOnly);
        Type localVarReturnType = new TypeReference<List<Integration>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getPromptsWithIntegration
     *
     * @param integrationProvider     (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getPromptsWithIntegrationCall(String integrationProvider, String integrationName) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{integration_provider}/integration/{integration_name}/prompt".replaceAll(
                        "\\{" + "integration_provider" + "\\}", httpClient.escapeString(integrationProvider))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getPromptsWithIntegrationValidateBeforeCall(String integrationProvider, String integrationName)
            throws ApiException {
        // verify the required parameter 'integrationProvider' is set
        if (integrationProvider == null) {
            throw new ApiException("Missing the required parameter 'integrationProvider' when calling getPromptsWithIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling getPromptsWithIntegration(Async)");
        }

        return getPromptsWithIntegrationCall(integrationProvider, integrationName);

    }

    /**
     * Get the list of prompt templates associated with an integration
     *
     * @param integrationProvider (required)
     * @param integrationName     (required)
     * @return List&lt;PromptTemplate&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<PromptTemplate> getPromptsWithIntegration(String integrationProvider, String integrationName) throws ApiException {
        ApiResponse<List<PromptTemplate>> resp = getPromptsWithIntegrationWithHttpInfo(integrationProvider, integrationName);
        return resp.getData();
    }

    /**
     * Get the list of prompt templates associated with an integration
     *
     * @param integrationProvider (required)
     * @param integrationName     (required)
     * @return ApiResponse&lt;List&lt;PromptTemplate&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<PromptTemplate>> getPromptsWithIntegrationWithHttpInfo(String integrationProvider, String integrationName) throws ApiException {
        Call call = getPromptsWithIntegrationValidateBeforeCall(integrationProvider, integrationName);
        Type localVarReturnType = new TypeReference<List<PromptTemplate>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getProvidersAndIntegrations
     *
     * @param type                    (optional)
     * @param activeOnly              (optional, default to true)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getProvidersAndIntegrationsCall(String type, Boolean activeOnly) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/all";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (type != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("type", type));
        }
        if (activeOnly != null) {
            localVarQueryParams.addAll(httpClient.parameterToPair("activeOnly", activeOnly));
        }

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
    private Call getProvidersAndIntegrationsValidateBeforeCall(String type, Boolean activeOnly) throws ApiException {

        return getProvidersAndIntegrationsCall(type, activeOnly);

    }

    /**
     * Get Integrations Providers and Integrations combo
     *
     * @param type       (optional)
     * @param activeOnly (optional, default to true)
     * @return List&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<String> getProvidersAndIntegrations(String type, Boolean activeOnly) throws ApiException {
        ApiResponse<List<String>> resp = getProvidersAndIntegrationsWithHttpInfo(type, activeOnly);
        return resp.getData();
    }

    /**
     * Get Integrations Providers and Integrations combo
     *
     * @param type       (optional)
     * @param activeOnly (optional, default to true)
     * @return ApiResponse&lt;List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<String>> getProvidersAndIntegrationsWithHttpInfo(String type, Boolean activeOnly) throws ApiException {
        Call call = getProvidersAndIntegrationsValidateBeforeCall(type, activeOnly);
        Type localVarReturnType = new TypeReference<List<String>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTagsForIntegration
     *
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getTagsForIntegrationCall(String name, String integrationName) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}/tags".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getTagsForIntegrationValidateBeforeCall(String name, String integrationName) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTagsForIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling getTagsForIntegration(Async)");
        }

        return getTagsForIntegrationCall(name, integrationName);

    }

    /**
     * Get tags by Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return List&lt;Tag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTagsForIntegration(String name, String integrationName) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForIntegrationWithHttpInfo(name, integrationName);
        return resp.getData();
    }

    /**
     * Get tags by Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;List&lt;Tag&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<TagObject>> getTagsForIntegrationWithHttpInfo(String name, String integrationName) throws ApiException {
        Call call = getTagsForIntegrationValidateBeforeCall(name, integrationName);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTagsForIntegrationProvider
     *
     * @param name                    (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getTagsForIntegrationProviderCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getTagsForIntegrationProviderValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTagsForIntegrationProvider(Async)");
        }

        return getTagsForIntegrationProviderCall(name);

    }

    /**
     * Get tags by Integration Provider
     *
     * @param name (required)
     * @return List&lt;Tag&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTagsForIntegrationProvider(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForIntegrationProviderWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get tags by Integration Provider
     *
     * @param name (required)
     * @return ApiResponse&lt;List&lt;Tag&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<TagObject>> getTagsForIntegrationProviderWithHttpInfo(String name) throws ApiException {
        Call call = getTagsForIntegrationProviderValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTokenUsageForIntegration
     *
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getTokenUsageForIntegrationCall(String name, String integrationName) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}/metrics".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getTokenUsageForIntegrationValidateBeforeCall(String name, String integrationName)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTokenUsageForIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling getTokenUsageForIntegration(Async)");
        }

        return getTokenUsageForIntegrationCall(name, integrationName);

    }

    /**
     * Get Token Usage by Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return Integer
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Integer getTokenUsageForIntegration(String name, String integrationName) throws ApiException {
        ApiResponse<Integer> resp = getTokenUsageForIntegrationWithHttpInfo(name, integrationName);
        return resp.getData();
    }

    /**
     * Get Token Usage by Integration
     *
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Integer&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Integer> getTokenUsageForIntegrationWithHttpInfo(String name, String integrationName) throws ApiException {
        Call call = getTokenUsageForIntegrationValidateBeforeCall(name, integrationName);
        Type localVarReturnType = new TypeReference<Integer>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getTokenUsageForIntegrationProvider
     *
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getTokenUsageForIntegrationProviderCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/metrics".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call getTokenUsageForIntegrationProviderValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTokenUsageForIntegrationProvider(Async)");
        }

        return getTokenUsageForIntegrationProviderCall(name);

    }

    /**
     * Get Token Usage by Integration Provider
     *
     * @param name (required)
     * @return Map&lt;String, String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Map<String, Integer> getTokenUsageForIntegrationProvider(String name) throws ApiException {
        ApiResponse<Map<String, Integer>> resp = getTokenUsageForIntegrationProviderWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get Token Usage by Integration Provider
     *
     * @param name (required)
     * @return ApiResponse&lt;Map&lt;String, String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Map<String, Integer>> getTokenUsageForIntegrationProviderWithHttpInfo(String name) throws ApiException {
        Call call = getTokenUsageForIntegrationProviderValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<Map<String, Integer>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for putTagForIntegration
     *
     * @param body                    (required)
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call putTagForIntegrationCall(List<TagObject> body, String name, String integrationName)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}/tags".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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

    @SuppressWarnings("rawtypes")
    private Call putTagForIntegrationValidateBeforeCall(List<TagObject> body, String name, String integrationName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForIntegration(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling putTagForIntegration(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling putTagForIntegration(Async)");
        }

        return putTagForIntegrationCall(body, name, integrationName);

    }

    /**
     * Put a tag to Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForIntegration(List<TagObject> body, String name, String integrationName) throws ApiException {
        putTagForIntegrationWithHttpInfo(body, name, integrationName);
    }

    /**
     * Put a tag to Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> putTagForIntegrationWithHttpInfo(List<TagObject> body, String name, String integrationName) throws ApiException {
        Call call = putTagForIntegrationValidateBeforeCall(body, name, integrationName);
        return httpClient.execute(call);
    }

    /**
     * Build call for putTagForIntegrationProvider
     *
     * @param body                    (required)
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call putTagForIntegrationProviderCall(List<TagObject> body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/tags".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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

    @SuppressWarnings("rawtypes")
    private Call putTagForIntegrationProviderValidateBeforeCall(List<TagObject> body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForIntegrationProvider(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling putTagForIntegrationProvider(Async)");
        }

        return putTagForIntegrationProviderCall(body, name);

    }

    /**
     * Put a tag to Integration Provider
     *
     * @param body (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForIntegrationProvider(List<TagObject> body, String name) throws ApiException {
        putTagForIntegrationProviderWithHttpInfo(body, name);
    }

    /**
     * Put a tag to Integration Provider
     *
     * @param body (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> putTagForIntegrationProviderWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        Call call = putTagForIntegrationProviderValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

    /**
     * Build call for registerTokenUsage
     *
     * @param body                    (required)
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call registerTokenUsageCall(Integer body, String name, String integrationName) throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}/metrics".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call registerTokenUsageValidateBeforeCall(Integer body, String name, String integrationName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling registerTokenUsage(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling registerTokenUsage(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling registerTokenUsage(Async)");
        }

        return registerTokenUsageCall(body, name, integrationName);

    }

    /**
     * Register Token usage
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void registerTokenUsage(Integer body, String name, String integrationName) throws ApiException {
        registerTokenUsageWithHttpInfo(body, name, integrationName);
    }

    /**
     * Register Token usage
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> registerTokenUsageWithHttpInfo(Integer body, String name, String integrationName) throws ApiException {
        Call call = registerTokenUsageValidateBeforeCall(body, name, integrationName);
        return httpClient.execute(call);
    }

    /**
     * Build call for saveIntegrationApi
     *
     * @param body                    (required)
     * @param name                    (required)
     * @param integrationName         (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call saveIntegrationApiCall(IntegrationApiUpdate body, String name, String integrationName)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}/integration/{integration_name}".replaceAll("\\{" + "name" + "\\}",
                        httpClient.escapeString(name))
                .replaceAll("\\{" + "integration_name" + "\\}", httpClient.escapeString(integrationName));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call saveIntegrationApiValidateBeforeCall(IntegrationApiUpdate body, String name, String integrationName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling saveIntegrationApi(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling saveIntegrationApi(Async)");
        }
        // verify the required parameter 'integrationName' is set
        if (integrationName == null) {
            throw new ApiException("Missing the required parameter 'integrationName' when calling saveIntegrationApi(Async)");
        }

        return saveIntegrationApiCall(body, name, integrationName);

    }

    /**
     * Create or Update Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void saveIntegrationApi(IntegrationApiUpdate body, String name, String integrationName) throws ApiException {
        saveIntegrationApiWithHttpInfo(body, name, integrationName);
    }

    /**
     * Create or Update Integration
     *
     * @param body            (required)
     * @param name            (required)
     * @param integrationName (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> saveIntegrationApiWithHttpInfo(IntegrationApiUpdate body, String name, String integrationName) throws ApiException {
        Call call = saveIntegrationApiValidateBeforeCall(body, name, integrationName);
        return httpClient.execute(call);
    }

    /**
     * Build call for saveIntegrationProvider
     *
     * @param body                    (required)
     * @param name                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call saveIntegrationProviderCall(IntegrationUpdate body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/integrations/provider/{name}".replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

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
    private Call saveIntegrationProviderValidateBeforeCall(IntegrationUpdate body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling saveIntegrationProvider(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling saveIntegrationProvider(Async)");
        }

        return saveIntegrationProviderCall(body, name);

    }

    /**
     * Create or Update Integration provider
     *
     * @param body (required)
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void saveIntegrationProvider(IntegrationUpdate body, String name) throws ApiException {
        saveIntegrationProviderWithHttpInfo(body, name);
    }

    /**
     * Create or Update Integration provider
     *
     * @param body (required)
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> saveIntegrationProviderWithHttpInfo(IntegrationUpdate body, String name) throws ApiException {
        Call call = saveIntegrationProviderValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }
}
