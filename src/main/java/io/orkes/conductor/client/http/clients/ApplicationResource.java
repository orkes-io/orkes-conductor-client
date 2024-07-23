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

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.model.AccessKeyResponse;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.ConductorUser;
import io.orkes.conductor.client.model.CreateAccessKeyResponse;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.TagObject;
import okhttp3.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ApplicationResource {

    private final OrkesHttpClient apiClient;

    public ApplicationResource(OrkesHttpClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for addRoleToApplicationUser
     *
     * @param applicationId           (required)
     * @param role                    (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call addRoleToApplicationUserCall(
            String applicationId,
            String role)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{applicationId}/roles/{role}"
                        .replaceAll(
                                "\\{" + "applicationId" + "\\}",
                                apiClient.escapeString(applicationId))
                        .replaceAll(
                                "\\{" + "role" + "\\}", apiClient.escapeString(role));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call addRoleToApplicationUserValidateBeforeCall(
            String applicationId,
            String role)
            throws ApiException {
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new ApiException(
                    "Missing the required parameter 'applicationId' when calling addRoleToApplicationUser(Async)");
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new ApiException(
                    "Missing the required parameter 'role' when calling addRoleToApplicationUser(Async)");
        }

        return addRoleToApplicationUserCall(
                applicationId, role);
    }

    /**
     * @param applicationId (required)
     * @param role          (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        addRoleToApplicationUserWithHttpInfo(applicationId, role);
    }

    /**
     * @param applicationId (required)
     * @param role          (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<ConductorUser> addRoleToApplicationUserWithHttpInfo(
            String applicationId, String role) throws ApiException {
        Call call = addRoleToApplicationUserValidateBeforeCall(applicationId, role);
        Type localVarReturnType = new TypeReference<ConductorUser>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for createAccessKey
     *
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call createAccessKeyCall(String id) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{id}/accessKeys"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call createAccessKeyValidateBeforeCall(String id) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling createAccessKey(Async)");
        }

        return createAccessKeyCall(id);
    }

    /**
     * Create an access key for an application
     *
     * @param id (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public CreateAccessKeyResponse createAccessKey(String id) throws ApiException {
        return createAccessKeyWithHttpInfo(id).getData();
    }

    /**
     * Create an access key for an application
     *
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<CreateAccessKeyResponse> createAccessKeyWithHttpInfo(String id)
            throws ApiException {
        Call call = createAccessKeyValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<CreateAccessKeyResponse>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for createApplication
     *
     * @param createOrUpdateApplicationRequest (required)
     * @param progressListener                 Progress listener
     * @param progressRequestListener          Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call createApplicationCall(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/applications";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                createOrUpdateApplicationRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call createApplicationValidateBeforeCall(
            CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (createOrUpdateApplicationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'createOrUpdateApplicationRequest' when calling createApplication(Async)");
        }

        return createApplicationCall(
                createOrUpdateApplicationRequest);
    }

    /**
     * Create an application
     *
     * @param createOrUpdateApplicationRequest (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public ConductorApplication createApplication(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest) throws ApiException {
        ApiResponse<ConductorApplication> resp = createApplicationWithHttpInfo(createOrUpdateApplicationRequest);
        return resp.getData();
    }

    /**
     * Create an application
     *
     * @param createOrUpdateApplicationRequest (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<ConductorApplication> createApplicationWithHttpInfo(CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest) throws ApiException {
        Call call = createApplicationValidateBeforeCall(createOrUpdateApplicationRequest);
        Type localVarReturnType = new TypeReference<ConductorApplication>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for deleteAccessKey
     *
     * @param applicationId           (required)
     * @param keyId                   (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteAccessKeyCall(
            String applicationId,
            String keyId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{applicationId}/accessKeys/{keyId}"
                        .replaceAll(
                                "\\{" + "applicationId" + "\\}",
                                apiClient.escapeString(applicationId))
                        .replaceAll(
                                "\\{" + "keyId" + "\\}", apiClient.escapeString(keyId));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call deleteAccessKeyValidateBeforeCall(
            String applicationId,
            String keyId)
            throws ApiException {
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new ApiException(
                    "Missing the required parameter 'applicationId' when calling deleteAccessKey(Async)");
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException(
                    "Missing the required parameter 'keyId' when calling deleteAccessKey(Async)");
        }

        return deleteAccessKeyCall(
                applicationId, keyId);
    }

    /**
     * Delete an access key
     *
     * @param applicationId (required)
     * @param keyId         (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        deleteAccessKeyWithHttpInfo(applicationId, keyId);
    }

    /**
     * Delete an access key
     *
     * @param applicationId (required)
     * @param keyId         (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> deleteAccessKeyWithHttpInfo(String applicationId, String keyId)
            throws ApiException {
        Call call =
                deleteAccessKeyValidateBeforeCall(applicationId, keyId);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for deleteApplication
     *
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteApplicationCall(
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{id}"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call deleteApplicationValidateBeforeCall(
            String id)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling deleteApplication(Async)");
        }

        return deleteApplicationCall(id);
    }

    /**
     * Delete an application
     *
     * @param id (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void deleteApplication(String id) throws ApiException {
        deleteApplicationWithHttpInfo(id);
    }

    /**
     * Delete an application
     *
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> deleteApplicationWithHttpInfo(String id) throws ApiException {
        Call call = deleteApplicationValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getAccessKeys
     *
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getAccessKeysCall(
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{id}/accessKeys"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call getAccessKeysValidateBeforeCall(
            String id)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getAccessKeys(Async)");
        }

        return getAccessKeysCall(id);
    }

    /**
     * Get application&#x27;s access keys
     *
     * @param id (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public List<AccessKeyResponse> getAccessKeys(String id) throws ApiException {
        ApiResponse<List<AccessKeyResponse>> resp = getAccessKeysWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get application&#x27;s access keys
     *
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<List<AccessKeyResponse>> getAccessKeysWithHttpInfo(String id)
            throws ApiException {
        Call call = getAccessKeysValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<List<AccessKeyResponse>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getApplication
     *
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getApplicationCall(
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{id}"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call getApplicationValidateBeforeCall(
            String id)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getApplication(Async)");
        }

        return getApplicationCall(id);
    }

    /**
     * Get an application by id
     *
     * @param id (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public ConductorApplication getApplication(String id) throws ApiException {
        ApiResponse<ConductorApplication> resp = getApplicationWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get an application by id
     *
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<ConductorApplication> getApplicationWithHttpInfo(String id)
            throws ApiException {
        Call call = getApplicationValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<ConductorApplication>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for listApplications
     *

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call listApplicationsCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call listApplicationsValidateBeforeCall()
            throws ApiException {

        return listApplicationsCall();
    }

    /**
     * Get all applications
     *
     * @return List&lt;ConductorApplication&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public List<ConductorApplication> listApplications() throws ApiException {
        ApiResponse<List<ConductorApplication>> resp = listApplicationsWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get all applications
     *
     * @return ApiResponse&lt;List&lt;ConductorApplication&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<List<ConductorApplication>> listApplicationsWithHttpInfo()
            throws ApiException {
        Call call = listApplicationsValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<ConductorApplication>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for removeRoleFromApplicationUser
     *
     * @param applicationId           (required)
     * @param role                    (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call removeRoleFromApplicationUserCall(
            String applicationId,
            String role)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{applicationId}/roles/{role}"
                        .replaceAll(
                                "\\{" + "applicationId" + "\\}",
                                apiClient.escapeString(applicationId))
                        .replaceAll(
                                "\\{" + "role" + "\\}", apiClient.escapeString(role));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call removeRoleFromApplicationUserValidateBeforeCall(
            String applicationId,
            String role)
            throws ApiException {
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new ApiException(
                    "Missing the required parameter 'applicationId' when calling removeRoleFromApplicationUser(Async)");
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new ApiException(
                    "Missing the required parameter 'role' when calling removeRoleFromApplicationUser(Async)");
        }

        return removeRoleFromApplicationUserCall(
                applicationId, role);
    }

    /**
     * @param applicationId (required)
     * @param role          (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void removeRoleFromApplicationUser(String applicationId, String role)
            throws ApiException {
        removeRoleFromApplicationUserWithHttpInfo(applicationId, role);
    }

    /**
     * @param applicationId (required)
     * @param role          (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Object> removeRoleFromApplicationUserWithHttpInfo(
            String applicationId, String role) throws ApiException {
        Call call =
                removeRoleFromApplicationUserValidateBeforeCall(applicationId, role);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for toggleAccessKeyStatus
     *
     * @param applicationId           (required)
     * @param keyId                   (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call toggleAccessKeyStatusCall(
            String applicationId,
            String keyId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/applications/{applicationId}/accessKeys/{keyId}/status"
                        .replaceAll(
                                "\\{" + "applicationId" + "\\}",
                                apiClient.escapeString(applicationId))
                        .replaceAll(
                                "\\{" + "keyId" + "\\}", apiClient.escapeString(keyId));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call toggleAccessKeyStatusValidateBeforeCall(
            String applicationId,
            String keyId)
            throws ApiException {
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new ApiException(
                    "Missing the required parameter 'applicationId' when calling toggleAccessKeyStatus(Async)");
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException(
                    "Missing the required parameter 'keyId' when calling toggleAccessKeyStatus(Async)");
        }

        return toggleAccessKeyStatusCall(
                applicationId, keyId);
    }

    /**
     * Toggle the status of an access key
     *
     * @param applicationId (required)
     * @param keyId         (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId)
            throws ApiException {
        ApiResponse<AccessKeyResponse> resp =
                toggleAccessKeyStatusWithHttpInfo(applicationId, keyId);
        return resp.getData();
    }

    /**
     * Toggle the status of an access key
     *
     * @param applicationId (required)
     * @param keyId         (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<AccessKeyResponse> toggleAccessKeyStatusWithHttpInfo(
            String applicationId, String keyId) throws ApiException {
        Call call =
                toggleAccessKeyStatusValidateBeforeCall(applicationId, keyId);
        Type localVarReturnType = new TypeReference<AccessKeyResponse>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for updateApplication
     *
     * @param createOrUpdateApplicationRequest (required)
     * @param id                               (required)
     * @param progressListener                 Progress listener
     * @param progressRequestListener          Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call updateApplicationCall(
            CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest,
            String id)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/applications/{id}"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);



        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                createOrUpdateApplicationRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call updateApplicationValidateBeforeCall(
            CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest,
            String id)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (createOrUpdateApplicationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'createOrUpdateApplicationRequest' when calling updateApplication(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling updateApplication(Async)");
        }

        return updateApplicationCall(
                createOrUpdateApplicationRequest,
                id);
    }

    /**
     * Update an application
     *
     * @param createOrUpdateApplicationRequest (required)
     * @param id                               (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public ConductorApplication updateApplication(
            CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest, String id)
            throws ApiException {
        ApiResponse<ConductorApplication> resp =
                updateApplicationWithHttpInfo(createOrUpdateApplicationRequest, id);
        return resp.getData();
    }

    /**
     * Update an application
     *
     * @param createOrUpdateApplicationRequest (required)
     * @param id                               (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<ConductorApplication> updateApplicationWithHttpInfo(
            CreateOrUpdateApplicationRequest createOrUpdateApplicationRequest, String id)
            throws ApiException {
        Call call =
                updateApplicationValidateBeforeCall(
                        createOrUpdateApplicationRequest, id);
        Type localVarReturnType = new TypeReference<ConductorApplication>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Put a tag to application
     *
     * @param body (required)
     * @param id   (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForApplication(List<TagObject> body, String id) throws ApiException {
        putTagForApplicationWithHttpInfo(body, id);
    }

    /**
     * Put a tag to application
     *
     * @param body (required)
     * @param id   (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> putTagForApplicationWithHttpInfo(List<TagObject> body, String id) throws ApiException {
        Call call = putTagForApplicationValidateBeforeCall(body, id);
        return apiClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private Call putTagForApplicationValidateBeforeCall(List<TagObject> body, String id) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForApplication(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling putTagForApplication(Async)");
        }

        return putTagForApplicationCall(body, id);
    }

    /**
     * Build call for putTagForApplication
     *
     * @param body                    (required)
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private Call putTagForApplicationCall(List<TagObject> body, String id) throws ApiException {

        // create path and map variables
        String localVarPath = "/applications/{id}/tags"
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    /**
     * Get tags by application
     *
     * @param id (required)
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTagsForApplication(String id) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForApplicationWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get tags by application
     *
     * @param id (required)
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<List<TagObject>> getTagsForApplicationWithHttpInfo(String id) throws ApiException {
        Call call = getTagsForApplicationValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private Call getTagsForApplicationValidateBeforeCall(String id) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getTagsForApplication(Async)");
        }

        return getTagsForApplicationCall(id);
    }

    /**
     * Build call for getTagsForApplication
     *
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private Call getTagsForApplicationCall(String id) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{id}/tags"
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    /**
     * Delete a tag for application
     *
     * @param body (required)
     * @param id   (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForApplication(List<TagObject> body, String id) throws ApiException {
        deleteTagForApplicationWithHttpInfo(body, id);
    }

    /**
     * Delete a tag for application
     *
     * @param body (required)
     * @param id   (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> deleteTagForApplicationWithHttpInfo(List<TagObject> body, String id) throws ApiException {
        Call call = deleteTagForApplicationValidateBeforeCall(body, id);
        return apiClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private Call deleteTagForApplicationValidateBeforeCall(List<TagObject> body, String id) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForApplication(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteTagForApplication(Async)");
        }

        return deleteTagForApplicationCall(body, id);
    }

    /**
     * Build call for deleteTagForApplication
     *
     * @param body                    (required)
     * @param id                      (required)

     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private Call deleteTagForApplicationCall(List<TagObject> body, String id) throws ApiException {

        // create path and map variables
        String localVarPath = "/applications/{id}/tags"
                .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }
}
