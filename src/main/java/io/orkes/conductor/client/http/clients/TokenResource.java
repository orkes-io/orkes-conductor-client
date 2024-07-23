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

import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.GenerateTokenRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Call;

class TokenResource {
    /**
     * Build call for generateToken
     *
     * @param apiClient ApiClient (required)
     * @param generateTokenRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public static Call generateTokenCall(
            OrkesHttpClient apiClient,
            GenerateTokenRequest generateTokenRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/token";

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

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                generateTokenRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private static Call generateTokenValidateBeforeCall(
            OrkesHttpClient apiClient,
            GenerateTokenRequest generateTokenRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (generateTokenRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'generateTokenRequest' when calling generateToken(Async)");
        }

        return generateTokenCall(
                apiClient, generateTokenRequest);
    }

    /**
     * Generate JWT with the given access key
     *
     * @param apiClient ApiClient (required)
     * @param generateTokenRequest (required)
     * @return ApiResponse&lt;Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public static ApiResponse<Map<String, String>> generateTokenWithHttpInfo(
            OrkesHttpClient apiClient, GenerateTokenRequest generateTokenRequest) throws ApiException {
        Call call = generateTokenValidateBeforeCall(apiClient, generateTokenRequest);
        Type localVarReturnType = new TypeReference<Map<String, String>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getUserInfo
     *
     * @param apiClient ApiClient (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public static Call getUserInfoCall(
            OrkesHttpClient apiClient)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/token/userInfo";

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

        String[] localVarAuthNames = new String[] {"api_key"};
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

    private static Call getUserInfoValidateBeforeCall(
            OrkesHttpClient apiClient)
            throws ApiException {
        return getUserInfoCall(apiClient);
    }

    /**
     * Get the user info from the token
     *
     * @param apiClient ApiClient (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public static ApiResponse<Object> getUserInfoWithHttpInfo(OrkesHttpClient apiClient)
            throws ApiException {
        Call call = getUserInfoValidateBeforeCall(apiClient);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }
}