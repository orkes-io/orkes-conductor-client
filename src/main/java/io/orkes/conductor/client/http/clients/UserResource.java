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

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.ConductorUser;
import io.orkes.conductor.client.model.GrantedAccessResponse;
import io.orkes.conductor.client.model.Response;
import io.orkes.conductor.client.model.UpsertUserRequest;

import com.fasterxml.jackson.core.type.TypeReference;

class UserResource {
    private OrkesHttpClient httpClient;

    public UserResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public okhttp3.Call deleteUserCall(
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{id}"
                        .replaceAll("\\{" + "id" + "\\}", httpClient.escapeString(id));

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

    private okhttp3.Call deleteUserValidateBeforeCall(
            String id)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling deleteUser(Async)");
        }

        return deleteUserCall(id);
    }

  
    public void deleteUser(String id) throws ApiException {
        deleteUserWithHttpInfo(id);
    }

  
    private ApiResponse<Response> deleteUserWithHttpInfo(String id) throws ApiException {
        okhttp3.Call call = deleteUserValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<Response>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getGrantedPermissionsCall(
            String userId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{userId}/permissions"
                        .replaceAll(
                                "\\{" + "userId" + "\\}",
                                httpClient.escapeString(userId));

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

    private okhttp3.Call getGrantedPermissionsValidateBeforeCall(
            String userId)
            throws ApiException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new ApiException(
                    "Missing the required parameter 'userId' when calling getGrantedPermissions(Async)");
        }

        return getGrantedPermissionsCall(userId);
    }

  
    public GrantedAccessResponse getGrantedPermissions(String userId) throws ApiException {
        ApiResponse<GrantedAccessResponse> resp = getGrantedPermissionsWithHttpInfo(userId);
        return resp.getData();
    }

  
    private ApiResponse<GrantedAccessResponse> getGrantedPermissionsWithHttpInfo(String userId)
            throws ApiException {
        okhttp3.Call call = getGrantedPermissionsValidateBeforeCall(userId);
        Type localVarReturnType = new TypeReference<GrantedAccessResponse>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getUserCall(
            String id)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/users/{id}"
                        .replaceAll("\\{" + "id" + "\\}", httpClient.escapeString(id));

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

    private okhttp3.Call getUserValidateBeforeCall(
            String id)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling getUser(Async)");
        }

        return getUserCall(id);
    }

  
    public ConductorUser getUser(String id) throws ApiException {
        ApiResponse<ConductorUser> resp = getUserWithHttpInfo(id);
        return resp.getData();
    }

  
    private ApiResponse<ConductorUser> getUserWithHttpInfo(String id) throws ApiException {
        okhttp3.Call call = getUserValidateBeforeCall(id);
        Type localVarReturnType = new TypeReference<ConductorUser>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call listUsersCall(
            Boolean apps)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/users";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (apps != null) localVarQueryParams.addAll(httpClient.parameterToPair("apps", apps));

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

    private okhttp3.Call listUsersValidateBeforeCall(
            Boolean apps)
            throws ApiException {

        return listUsersCall(apps);
    }

  
    public List<ConductorUser> listUsers(Boolean apps) throws ApiException {
        ApiResponse<List<ConductorUser>> resp = listUsersWithHttpInfo(apps);
        return resp.getData();
    }

  
    private ApiResponse<List<ConductorUser>> listUsersWithHttpInfo(Boolean apps)
            throws ApiException {
        okhttp3.Call call = listUsersValidateBeforeCall(apps);
        Type localVarReturnType = new TypeReference<List<ConductorUser>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call sendInviteEmailCall(
            String id,
            ConductorUser conductorUser)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/users/{id}/sendInviteEmail"
                        .replaceAll("\\{" + "id" + "\\}", httpClient.escapeString(id));

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
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                conductorUser,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call sendInviteEmailValidateBeforeCall(
            String id,
            ConductorUser conductorUser)
            throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException(
                    "Missing the required parameter 'id' when calling sendInviteEmail(Async)");
        }

        return sendInviteEmailCall(id, conductorUser);
    }

  
    public void sendInviteEmail(String id, ConductorUser conductorUser) throws ApiException {
        sendInviteEmailWithHttpInfo(id, conductorUser);
    }

  
    private ApiResponse<Object> sendInviteEmailWithHttpInfo(String id, ConductorUser conductorUser)
            throws ApiException {
        okhttp3.Call call =
                sendInviteEmailValidateBeforeCall(id, conductorUser);
        Type localVarReturnType = new TypeReference<>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call upsertUserCall(
            UpsertUserRequest upsertUserRequest,
            String id)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/users/{id}"
                        .replaceAll("\\{" + "id" + "\\}", httpClient.escapeString(id));

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
                upsertUserRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call upsertUserValidateBeforeCall(
            UpsertUserRequest upsertUserRequest,
            String id)
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

        return upsertUserCall(upsertUserRequest, id);
    }

  
    public ConductorUser upsertUser(UpsertUserRequest upsertUserRequest, String id)
            throws ApiException {
        ApiResponse<ConductorUser> resp = upsertUserWithHttpInfo(upsertUserRequest, id);
        return resp.getData();
    }

  
    private ApiResponse<ConductorUser> upsertUserWithHttpInfo(
            UpsertUserRequest upsertUserRequest, String id) throws ApiException {
        okhttp3.Call call =
                upsertUserValidateBeforeCall(upsertUserRequest, id);
        Type localVarReturnType = new TypeReference<ConductorUser>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }
}
