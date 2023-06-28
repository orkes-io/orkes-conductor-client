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
import io.orkes.conductor.client.model.Group;
import io.orkes.conductor.client.model.UpsertGroupRequest;

import com.fasterxml.jackson.core.type.TypeReference;

public class GroupResourceApi {
    private ApiClient apiClient;

    public GroupResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public GroupResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for addUserToGroup
     *
     * @param groupId (required)
     * @param userId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call addUserToGroupCall(
            String groupId,
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{groupId}/users/{userId}"
                        .replaceAll(
                                "\\{" + "groupId" + "\\}",
                                apiClient.escapeString(groupId.toString()))
                        .replaceAll(
                                "\\{" + "userId" + "\\}",
                                apiClient.escapeString(userId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

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
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call addUserToGroupValidateBeforeCall(
            String groupId,
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new ApiException(
                    "Missing the required parameter 'groupId' when calling addUserToGroup(Async)");
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new ApiException(
                    "Missing the required parameter 'userId' when calling addUserToGroup(Async)");
        }

        com.squareup.okhttp.Call call =
                addUserToGroupCall(groupId, userId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Add user to group
     *
     * @param groupId (required)
     * @param userId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void addUserToGroup(String groupId, String userId) throws ApiException {
        addUserToGroupWithHttpInfo(groupId, userId);
    }

    /**
     * Add user to group
     *
     * @param groupId (required)
     * @param userId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> addUserToGroupWithHttpInfo(String groupId, String userId)
            throws ApiException {
        com.squareup.okhttp.Call call =
                addUserToGroupValidateBeforeCall(groupId, userId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for deleteGroup
     *
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call deleteGroupCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{id}"
                        .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

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

    private com.squareup.okhttp.Call deleteGroupValidateBeforeCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling deleteGroup(Async)");
        }

        com.squareup.okhttp.Call call =
                deleteGroupCall(id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Delete a group
     *
     * @param id (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteGroup(String id) throws ApiException {
        deleteGroupWithHttpInfo(id);
    }

    /**
     * Delete a group
     *
     * @param id (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> deleteGroupWithHttpInfo(String id) throws ApiException {
        com.squareup.okhttp.Call call = deleteGroupValidateBeforeCall(id, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for getGrantedPermissions1
     *
     * @param groupId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getGrantedPermissions1Call(
            String groupId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{groupId}/permissions"
                        .replaceAll(
                                "\\{" + "groupId" + "\\}",
                                apiClient.escapeString(groupId.toString()));

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

    private com.squareup.okhttp.Call getGrantedPermissions1ValidateBeforeCall(
            String groupId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new ApiException(
                    "Missing the required parameter 'groupId' when calling getGrantedPermissions1(Async)");
        }

        com.squareup.okhttp.Call call =
                getGrantedPermissions1Call(groupId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get the permissions this group has over workflows and tasks
     *
     * @param groupId (required)
     * @return GrantedAccessResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public GrantedAccessResponse getGrantedPermissions1(String groupId) throws ApiException {
        ApiResponse<GrantedAccessResponse> resp = getGrantedPermissions1WithHttpInfo(groupId);
        return resp.getData();
    }

    /**
     * Get the permissions this group has over workflows and tasks
     *
     * @param groupId (required)
     * @return ApiResponse&lt;GrantedAccessResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<GrantedAccessResponse> getGrantedPermissions1WithHttpInfo(String groupId)
            throws ApiException {
        com.squareup.okhttp.Call call =
                getGrantedPermissions1ValidateBeforeCall(groupId, null, null);
        Type localVarReturnType = new TypeReference<GrantedAccessResponse>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getGroup
     *
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getGroupCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{id}"
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

    private com.squareup.okhttp.Call getGroupValidateBeforeCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getGroup(Async)");
        }

        com.squareup.okhttp.Call call = getGroupCall(id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get a group by id
     *
     * @param id (required)
     * @return Group
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Group getGroup(String id) throws ApiException {
        ApiResponse<Group> resp = getGroupWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get a group by id
     *
     * @param id (required)
     * @return ApiResponse&lt;Group&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Group> getGroupWithHttpInfo(String id) throws ApiException {
        com.squareup.okhttp.Call call = getGroupValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeReference<Group>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getUsersInGroup
     *
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getUsersInGroupCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{id}/users"
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

    private com.squareup.okhttp.Call getUsersInGroupValidateBeforeCall(
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getUsersInGroup(Async)");
        }

        com.squareup.okhttp.Call call =
                getUsersInGroupCall(id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all users in group
     *
     * @param id (required)
     * @return List&lt;ConductorUser&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<ConductorUser> getUsersInGroup(String id) throws ApiException {
        ApiResponse<List<ConductorUser>> resp = getUsersInGroupWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get all users in group
     *
     * @param id (required)
     * @return ApiResponse&lt;List&lt;ConductorUser&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<ConductorUser>> getUsersInGroupWithHttpInfo(String id)
            throws ApiException {
        com.squareup.okhttp.Call call = getUsersInGroupValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeReference<List<ConductorUser>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for listGroups
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call listGroupsCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/groups";

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

    private com.squareup.okhttp.Call listGroupsValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        com.squareup.okhttp.Call call = listGroupsCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all groups
     *
     * @return List&lt;Group&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<Group> listGroups() throws ApiException {
        ApiResponse<List<Group>> resp = listGroupsWithHttpInfo();
        return resp.getData();
    }

    /**
     * Get all groups
     *
     * @return ApiResponse&lt;List&lt;Group&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<Group>> listGroupsWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = listGroupsValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<Group>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for removeUserFromGroup
     *
     * @param groupId (required)
     * @param userId (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call removeUserFromGroupCall(
            String groupId,
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/groups/{groupId}/users/{userId}"
                        .replaceAll(
                                "\\{" + "groupId" + "\\}",
                                apiClient.escapeString(groupId.toString()))
                        .replaceAll(
                                "\\{" + "userId" + "\\}",
                                apiClient.escapeString(userId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {};

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

    private com.squareup.okhttp.Call removeUserFromGroupValidateBeforeCall(
            String groupId,
            String userId,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new ApiException(
                    "Missing the required parameter 'groupId' when calling removeUserFromGroup(Async)");
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new ApiException(
                    "Missing the required parameter 'userId' when calling removeUserFromGroup(Async)");
        }

        com.squareup.okhttp.Call call =
                removeUserFromGroupCall(groupId, userId, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Remove user from group
     *
     * @param groupId (required)
     * @param userId (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void removeUserFromGroup(String groupId, String userId) throws ApiException {
        removeUserFromGroupWithHttpInfo(groupId, userId);
    }

    /**
     * Remove user from group
     *
     * @param groupId (required)
     * @param userId (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> removeUserFromGroupWithHttpInfo(String groupId, String userId)
            throws ApiException {
        com.squareup.okhttp.Call call =
                removeUserFromGroupValidateBeforeCall(groupId, userId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Build call for upsertGroup
     *
     * @param upsertGroupRequest (required)
     * @param id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call upsertGroupCall(
            UpsertGroupRequest upsertGroupRequest,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = upsertGroupRequest;

        // create path and map variables
        String localVarPath =
                "/groups/{id}"
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

    private com.squareup.okhttp.Call upsertGroupValidateBeforeCall(
            UpsertGroupRequest upsertGroupRequest,
            String id,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (upsertGroupRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'upsertGroupRequest' when calling upsertGroup(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling upsertGroup(Async)");
        }

        com.squareup.okhttp.Call call =
                upsertGroupCall(upsertGroupRequest, id, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Create or update a group
     *
     * @param upsertGroupRequest (required)
     * @param id (required)
     * @return Group
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Group upsertGroup(UpsertGroupRequest upsertGroupRequest, String id) throws ApiException {
        ApiResponse<Group> resp = upsertGroupWithHttpInfo(upsertGroupRequest, id);
        return resp.getData();
    }

    /**
     * Create or update a group
     *
     * @param upsertGroupRequest (required)
     * @param id (required)
     * @return ApiResponse&lt;Group&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Group> upsertGroupWithHttpInfo(
            UpsertGroupRequest upsertGroupRequest, String id) throws ApiException {
        com.squareup.okhttp.Call call =
                upsertGroupValidateBeforeCall(upsertGroupRequest, id, null, null);
        Type localVarReturnType = new TypeReference<Group>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }
}
