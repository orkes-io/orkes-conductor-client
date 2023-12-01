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
package io.orkes.conductor.client.http.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.AuthorizationRequest;
import io.orkes.conductor.client.model.Subject;

import com.fasterxml.jackson.core.type.TypeReference;

public class AuthorizationResourceApi {
    private ApiClient apiClient;

    public AuthorizationResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AuthorizationResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getPermissions
     *
     * @param type (required)
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getPermissionsCall(
            String type,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/auth/authorization/{type}/{id}"
                        .replaceAll("\\{" + "type" + "\\}", apiClient.escapeString(type.toString()))
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getPermissionsValidateBeforeCall(
            String type,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
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

        com.squareup.okhttp.Call call =
                getPermissionsCall(type, id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the access that have been granted over the given object
     *
     * @param type (required)
     * @param id (required)
     * @return Map&lt;String, List&lt;Subject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, List<Subject>> getPermissions(String type, String id) throws ApiException {
        ApiResponse<Map<String, List<Subject>>> resp = getPermissionsWithHttpInfo(type, id);
        return resp.getData();
    }

    /**
     * Get the access that have been granted over the given object
     *
     * @param type (required)
     * @param id (required)
     * @return ApiResponse&lt;Map&lt;String, List&lt;Subject&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, List<Subject>>> getPermissionsWithHttpInfo(
            String type, String id) throws ApiException {
        com.squareup.okhttp.Call call = getPermissionsValidateBeforeCall(type, id, null, null);
        Type localVarReturnType = new TypeReference<Map<String, List<Subject>>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for grantPermissions
     *
     * @param authorizationRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call grantPermissionsCall(
            AuthorizationRequest authorizationRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = authorizationRequest;

        // create path and map variables
        String localVarPath = "/auth/authorization";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call grantPermissionsValidateBeforeCall(
            AuthorizationRequest authorizationRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (authorizationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'authorizationRequest' when calling grantPermissions(Async)");
        }

        com.squareup.okhttp.Call call =
                grantPermissionsCall(
                        authorizationRequest, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Grant access to a user over the target
     *
     * @param authorizationRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
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
     *     response body
     */
    private ApiResponse<Void> grantPermissionsWithHttpInfo(
            AuthorizationRequest authorizationRequest) throws ApiException {
        com.squareup.okhttp.Call call =
                grantPermissionsValidateBeforeCall(authorizationRequest, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for removePermissions
     *
     * @param authorizationRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call removePermissionsCall(
            AuthorizationRequest authorizationRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = authorizationRequest;

        // create path and map variables
        String localVarPath = "/auth/authorization";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new com.squareup.okhttp.Interceptor() {
                                @Override
                                public com.squareup.okhttp.Response intercept(
                                        com.squareup.okhttp.Interceptor.Chain chain)
                                        throws IOException {
                                    com.squareup.okhttp.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call removePermissionsValidateBeforeCall(
            AuthorizationRequest authorizationRequest,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (authorizationRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'authorizationRequest' when calling removePermissions(Async)");
        }

        com.squareup.okhttp.Call call =
                removePermissionsCall(
                        authorizationRequest, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Remove user&#x27;s access over the target
     *
     * @param authorizationRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
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
     *     response body
     */
    private ApiResponse<Void> removePermissionsWithHttpInfo(
            AuthorizationRequest authorizationRequest) throws ApiException {
        com.squareup.okhttp.Call call =
                removePermissionsValidateBeforeCall(authorizationRequest, null, null);
        return apiClient.execute(call);
    }
}
