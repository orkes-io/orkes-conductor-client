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
import io.orkes.conductor.client.model.AuthorizationRequest;
import io.orkes.conductor.client.model.Subject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AuthorizationResource {
    private final OrkesHttpClient httpClient;

    public AuthorizationResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Build call for getPermissions
     *
     * @param type (required)
     * @param id   (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getPermissionsCall(
            String type,
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/auth/authorization/{type}/{id}"
                        .replaceAll("\\{" + "type" + "\\}", httpClient.escapeString(type))
                        .replaceAll("\\{" + "id" + "\\}", httpClient.escapeString(id));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);


        String[] localVarAuthNames = new String[]{"api_key"};
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

    private okhttp3.Call getPermissionsValidateBeforeCall(
            String type,
            String id)
            throws ApiException {
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new ApiException(
                    "Missing the required parameter 'type' when calling getPermissions(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getPermissions(Async)");
        }

        return getPermissionsCall(type, id);
    }

    /**
     * Get the access that have been granted over the given object
     *
     * @param type (required)
     * @param id   (required)
     * @return Map&lt;String, List&lt;Subject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException {
        ApiResponse<Map<String, List<Subject>>> resp = getPermissionsWithHttpInfo(type, id);
        return resp.getData();
    }

    /**
     * Get the access that have been granted over the given object
     *
     * @param type (required)
     * @param id   (required)
     * @return ApiResponse&lt;Map&lt;String, List&lt;Subject&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Map<String, List<Subject>>> getPermissionsWithHttpInfo(
            String type, String id) throws ApiException {
        okhttp3.Call call = getPermissionsValidateBeforeCall(type, id);
        Type localVarReturnType = new TypeReference<Map<String, List<Subject>>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for grantPermissions
     *
     * @param authorizationRequest (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call grantPermissionsCall(
            AuthorizationRequest authorizationRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/auth/authorization";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);


        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                authorizationRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call grantPermissionsValidateBeforeCall(
            AuthorizationRequest authorizationRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (authorizationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'authorizationRequest' when calling grantPermissions(Async)");
        }

        return grantPermissionsCall(
                authorizationRequest);
    }

    /**
     * Grant access to a user over the target
     *
     * @param authorizationRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void grantPermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        grantPermissionsWithHttpInfo(authorizationRequest);
    }

    /**
     * Grant access to a user over the target
     *
     * @param authorizationRequest (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Void> grantPermissionsWithHttpInfo(
            AuthorizationRequest authorizationRequest) throws ApiException {
        okhttp3.Call call =
                grantPermissionsValidateBeforeCall(authorizationRequest);
        return httpClient.execute(call);
    }

    /**
     * Build call for removePermissions
     *
     * @param authorizationRequest (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call removePermissionsCall(
            AuthorizationRequest authorizationRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/auth/authorization";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);


        String[] localVarAuthNames = new String[]{"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                authorizationRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call removePermissionsValidateBeforeCall(
            AuthorizationRequest authorizationRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (authorizationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'authorizationRequest' when calling removePermissions(Async)");
        }

        return removePermissionsCall(
                authorizationRequest);
    }

    /**
     * Remove user&#x27;s access over the target
     *
     * @param authorizationRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    public void removePermissions(AuthorizationRequest authorizationRequest) throws ApiException {
        removePermissionsWithHttpInfo(authorizationRequest);
    }

    /**
     * Remove user&#x27;s access over the target
     *
     * @param authorizationRequest (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *                      response body
     */
    private ApiResponse<Void> removePermissionsWithHttpInfo(
            AuthorizationRequest authorizationRequest) throws ApiException {
        okhttp3.Call call =
                removePermissionsValidateBeforeCall(authorizationRequest);
        return httpClient.execute(call);
    }
}