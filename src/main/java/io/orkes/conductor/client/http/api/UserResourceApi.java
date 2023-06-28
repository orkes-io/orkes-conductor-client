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
import io.orkes.conductor.client.model.ConductorUser;
import io.orkes.conductor.client.model.GrantedAccessResponse;
import io.orkes.conductor.client.model.Response;
import io.orkes.conductor.client.model.UpsertUserRequest;

import com.fasterxml.jackson.core.type.TypeReference;

public class UserResourceApi {
    private ApiClient apiClient;

    public UserResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public UserResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for deleteUser
     *
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call deleteUserCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{id}"
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
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call deleteUserValidateBeforeCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling deleteUser(Async)");
        }

        com.squareup.okhttp.Call call =
                deleteUserCall(id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Delete a user
     *
     * @param id (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteUser(String id) throws ApiException {
        deleteUserWithHttpInfo(id);
    }

    /**
     * Delete a user
     *
     * @param id (required)
     * @return ApiResponse&lt;Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Response> deleteUserWithHttpInfo(String id) throws ApiException {
        com.squareup.okhttp.Call call = deleteUserValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeReference<Response>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getGrantedPermissions
     *
     * @param userId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getGrantedPermissionsCall(
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{userId}/permissions"
                        .replaceAll(
                                "\\{" + "userId" + "\\}",
                                apiClient.escapeString(userId.toString()));

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

    private com.squareup.okhttp.Call getGrantedPermissionsValidateBeforeCall(
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new ApiException(
                    "Missing the required parameter 'userId' when calling getGrantedPermissions(Async)");
        }

        com.squareup.okhttp.Call call =
                getGrantedPermissionsCall(userId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the permissions this user has over workflows and tasks
     *
     * @param userId (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public GrantedAccessResponse getGrantedPermissions(String userId) throws ApiException {
        ApiResponse<GrantedAccessResponse> resp = getGrantedPermissionsWithHttpInfo(userId);
        return resp.getData();
    }

    /**
     * Get the permissions this user has over workflows and tasks
     *
     * @param userId (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<GrantedAccessResponse> getGrantedPermissionsWithHttpInfo(String userId)
            throws ApiException {
        com.squareup.okhttp.Call call = getGrantedPermissionsValidateBeforeCall(userId, null, null);
        Type localVarReturnType = new TypeReference<GrantedAccessResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getUser
     *
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getUserCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{id}"
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

    private com.squareup.okhttp.Call getUserValidateBeforeCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getUser(Async)");
        }

        com.squareup.okhttp.Call call = getUserCall(id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get a user by id
     *
     * @param id (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ConductorUser getUser(String id) throws ApiException {
        ApiResponse<ConductorUser> resp = getUserWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get a user by id
     *
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<ConductorUser> getUserWithHttpInfo(String id) throws ApiException {
        com.squareup.okhttp.Call call = getUserValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeReference<ConductorUser>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for listUsers
     *
     * @param apps (optional, default to false)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call listUsersCall(
            Boolean apps,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/users";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (apps != null) localVarQueryParams.addAll(apiClient.parameterToPair("apps", apps));

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

    private com.squareup.okhttp.Call listUsersValidateBeforeCall(
            Boolean apps,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call =
                listUsersCall(apps, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all users
     *
     * @param apps (optional, default to false)
     * @return List&lt;ConductorUser&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<ConductorUser> listUsers(Boolean apps) throws ApiException {
        ApiResponse<List<ConductorUser>> resp = listUsersWithHttpInfo(apps);
        return resp.getData();
    }

    /**
     * Get all users
     *
     * @param apps (optional, default to false)
     * @return ApiResponse&lt;List&lt;ConductorUser&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<ConductorUser>> listUsersWithHttpInfo(Boolean apps)
            throws ApiException {
        com.squareup.okhttp.Call call = listUsersValidateBeforeCall(apps, null, null);
        Type localVarReturnType = new TypeReference<List<ConductorUser>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for sendInviteEmail
     *
     * @param id (required)
     * @param conductorUser (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call sendInviteEmailCall(
            String id,
            ConductorUser conductorUser,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = conductorUser;

        // create path and map variables
        String localVarPath =
                "/users/{id}/sendInviteEmail"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
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

    private com.squareup.okhttp.Call sendInviteEmailValidateBeforeCall(
            String id,
            ConductorUser conductorUser,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling sendInviteEmail(Async)");
        }

        com.squareup.okhttp.Call call =
                sendInviteEmailCall(id, conductorUser, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Send an email with a link to this cluster
     *
     * @param id (required)
     * @param conductorUser (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void sendInviteEmail(String id, ConductorUser conductorUser) throws ApiException {
        sendInviteEmailWithHttpInfo(id, conductorUser);
    }

    /**
     * Send an email with a link to this cluster
     *
     * @param id (required)
     * @param conductorUser (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Object> sendInviteEmailWithHttpInfo(String id, ConductorUser conductorUser)
            throws ApiException {
        com.squareup.okhttp.Call call =
                sendInviteEmailValidateBeforeCall(id, conductorUser, null, null);
        Type localVarReturnType = new TypeReference<Object>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for upsertUser
     *
     * @param upsertUserRequest (required)
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call upsertUserCall(
            UpsertUserRequest upsertUserRequest,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = upsertUserRequest;

        // create path and map variables
        String localVarPath =
                "/users/{id}"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call upsertUserValidateBeforeCall(
            UpsertUserRequest upsertUserRequest,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (upsertUserRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'upsertUserRequest' when calling upsertUser(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling upsertUser(Async)");
        }

        com.squareup.okhttp.Call call =
                upsertUserCall(upsertUserRequest, id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create or update a user
     *
     * @param upsertUserRequest (required)
     * @param id (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public ConductorUser upsertUser(UpsertUserRequest upsertUserRequest, String id)
            throws ApiException {
        ApiResponse<ConductorUser> resp = upsertUserWithHttpInfo(upsertUserRequest, id);
        return resp.getData();
    }

    /**
     * Create or update a user
     *
     * @param upsertUserRequest (required)
     * @param id (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<ConductorUser> upsertUserWithHttpInfo(
            UpsertUserRequest upsertUserRequest, String id) throws ApiException {
        com.squareup.okhttp.Call call =
                upsertUserValidateBeforeCall(upsertUserRequest, id, null, null);
        Type localVarReturnType = new TypeReference<ConductorUser>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }
}
