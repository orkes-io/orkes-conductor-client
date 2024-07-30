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
import io.orkes.conductor.client.model.TokenResponse;
import okhttp3.Call;

class TokenResource extends Resource {

    TokenResource(OrkesHttpClient httpClient) {
        super(httpClient);
    }

    public ApiResponse<TokenResponse> generateTokenWithHttpInfo(GenerateTokenRequest generateTokenRequest) throws ApiException {
        validateNonNull("generateTokenRequest", generateTokenRequest);

        String localVarPath = "/token";
        Type localVarReturnType = new TypeReference<Map<String, String>>() {}.getType();
        return httpClient.doRequest("POST", )
    }


    public static Call getUserInfoCall(OrkesHttpClient apiClient) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables


        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

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

  
    public static ApiResponse<Object> getUserInfoWithHttpInfo(OrkesHttpClient apiClient)
            throws ApiException {

        String localVarPath = "/token/userInfo";
        Call call = getUserInfoValidateBeforeCall(apiClient);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }
}
