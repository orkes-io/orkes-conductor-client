/*
 * Copyright 2022 Orkes, Inc.
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
import java.util.Set;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.TagObject;

import com.fasterxml.jackson.core.type.TypeReference;

class SecretResource {
    private OrkesHttpClient httpClient;

    public SecretResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public okhttp3.Call deleteSecretCall(
            String key)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call deleteSecretValidateBeforeCall(
            String key)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling deleteSecret(Async)");
        }

        return deleteSecretCall(key);
    }

  
    public void deleteSecret(String key) throws ApiException {
        deleteSecretWithHttpInfo(key);
    }

  
    private ApiResponse<Object> deleteSecretWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = deleteSecretValidateBeforeCall(key);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getSecretCall(
            String key)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call getSecretValidateBeforeCall(
            String key)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling getSecret(Async)");
        }

        return getSecretCall(key);
    }

  
    public String getSecret(String key) throws ApiException {
        ApiResponse<String> resp = getSecretWithHttpInfo(key);
        return resp.getData();
    }

  
    private ApiResponse<String> getSecretWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = getSecretValidateBeforeCall(key);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call listAllSecretNamesCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call listAllSecretNamesValidateBeforeCall()
            throws ApiException {

        return listAllSecretNamesCall();
    }

  
    public Set<String> listAllSecretNames() throws ApiException {
        ApiResponse<Set<String>> resp = listAllSecretNamesWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<Set<String>> listAllSecretNamesWithHttpInfo() throws ApiException {
        okhttp3.Call call = listAllSecretNamesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<Set<String>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call listSecretsThatUserCanGrantAccessToCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call listSecretsThatUserCanGrantAccessToValidateBeforeCall()
            throws ApiException {

        return listSecretsThatUserCanGrantAccessToCall();
    }

  
    public List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException {
        ApiResponse<List<String>> resp = listSecretsThatUserCanGrantAccessToWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<List<String>> listSecretsThatUserCanGrantAccessToWithHttpInfo()
            throws ApiException {
        okhttp3.Call call =
                listSecretsThatUserCanGrantAccessToValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<String>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call putSecretCall(
            String body,
            String key)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call putSecretValidateBeforeCall(
            String body,
            String key)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling putSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling putSecret(Async)");
        }

        return putSecretCall(body, key);
    }

  
    public void putSecret(String body, String key) throws ApiException {
        putSecretWithHttpInfo(body, key);
    }

  
    private ApiResponse<Object> putSecretWithHttpInfo(String body, String key) throws ApiException {
        okhttp3.Call call = putSecretValidateBeforeCall(body, key);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call secretExistsCall(
            String key)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}/exists"
                        .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call secretExistsValidateBeforeCall(
            String key)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling secretExists(Async)");
        }

        return secretExistsCall(key);
    }

  
    public Boolean secretExists(String key) throws ApiException {
        ApiResponse<Boolean> resp = secretExistsWithHttpInfo(key);
        return resp.getData();
    }

  
    private ApiResponse<Boolean> secretExistsWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = secretExistsValidateBeforeCall(key);
        Type localVarReturnType = new TypeReference<Boolean>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public void putTagForSecret(List<TagObject> body, String key) throws ApiException {
        putTagForSecretWithHttpInfo(body, key);
    }

  
    private ApiResponse<Void> putTagForSecretWithHttpInfo(List<TagObject> body, String key) throws ApiException {
        okhttp3.Call call = putTagForSecretValidateBeforeCall(body, key);
        return httpClient.execute(call);
    }

    private okhttp3.Call putTagForSecretValidateBeforeCall(List<TagObject> body, String key) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling putTagForSecret(Async)");
        }

        return putTagForSecretCall(body, key);
    }
  
    private okhttp3.Call putTagForSecretCall(List<TagObject> body, String key) throws ApiException {

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

  
    public void deleteTagForSecret(List<TagObject> body, String key) throws ApiException {
        deleteTagForSecretWithHttpInfo(body, key);
    }

  
    private ApiResponse<Void> deleteTagForSecretWithHttpInfo(List<TagObject> body, String key) throws ApiException {
        okhttp3.Call call = deleteTagForSecretValidateBeforeCall(body, key);
        return httpClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteTagForSecretValidateBeforeCall(List<TagObject> body, String key) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling deleteTagForSecret(Async)");
        }

        return deleteTagForSecretCall(body, key);
    }
  
    private okhttp3.Call deleteTagForSecretCall(List<TagObject> body, String key) throws ApiException {

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

  
    public List<TagObject> getTags(String key) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsWithHttpInfo(key);
        return resp.getData();
    }

  
    private ApiResponse<List<TagObject>> getTagsWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = getTagsValidateBeforeCall(key);
        Type localVarReturnType = new TypeReference<List<TagObject>>(){}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getTagsValidateBeforeCall(String key) throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getTags(Async)");
        }

        return getTagsCall(key);
    }

  
    private okhttp3.Call getTagsCall(String key) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", httpClient.escapeString(key));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }
}
