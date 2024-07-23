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
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;

import com.fasterxml.jackson.core.type.TypeReference;

class TagsResource {
    private final OrkesHttpClient httpClient;

    public TagsResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public okhttp3.Call addTaskTagCall(
            TagObject tagObject,
            String taskName)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                httpClient.escapeString(taskName));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagObject,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call addTaskTagValidateBeforeCall(
            TagObject tagObject,
            String taskName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling addTaskTag(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling addTaskTag(Async)");
        }

        return addTaskTagCall(tagObject, taskName);
    }

  
    public void addTaskTag(TagObject tagObject, String taskName) throws ApiException {
        addTaskTagWithHttpInfo(tagObject, taskName);
    }

  
    private ApiResponse<Void> addTaskTagWithHttpInfo(TagObject tagObject, String taskName)
            throws ApiException {
        okhttp3.Call call =
                addTaskTagValidateBeforeCall(tagObject, taskName);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call addWorkflowTagCall(
            TagObject tagObject,
            String name)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagObject,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call addWorkflowTagValidateBeforeCall(
            TagObject tagObject,
            String name)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling addWorkflowTag(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling addWorkflowTag(Async)");
        }

        return addWorkflowTagCall(tagObject, name);
    }

  
    public void addWorkflowTag(TagObject tagObject, String name) throws ApiException {
        addWorkflowTagWithHttpInfo(tagObject, name);
    }

  
    private ApiResponse<Void> addWorkflowTagWithHttpInfo(TagObject tagObject, String name)
            throws ApiException {
        okhttp3.Call call =
                addWorkflowTagValidateBeforeCall(tagObject, name);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call deleteTaskTagCall(
            TagString tagString,
            String taskName)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                httpClient.escapeString(taskName));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagString,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call deleteTaskTagValidateBeforeCall(
            TagString tagString,
            String taskName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagString == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagString' when calling deleteTaskTag(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling deleteTaskTag(Async)");
        }

        return deleteTaskTagCall(tagString, taskName);
    }

  
    public void deleteTaskTag(TagString tagString, String taskName) throws ApiException {
        deleteTaskTagWithHttpInfo(tagString, taskName);
    }

  
    private ApiResponse<Void> deleteTaskTagWithHttpInfo(TagString tagString, String taskName)
            throws ApiException {
        okhttp3.Call call =
                deleteTaskTagValidateBeforeCall(tagString, taskName);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call deleteWorkflowTagCall(
            TagObject tagObject,
            String name)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagObject,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call deleteWorkflowTagValidateBeforeCall(
            TagObject tagObject,
            String name)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObject == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObject' when calling deleteWorkflowTag(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling deleteWorkflowTag(Async)");
        }

        return deleteWorkflowTagCall(tagObject, name);
    }

  
    public void deleteWorkflowTag(TagObject tagObject, String name) throws ApiException {
        deleteWorkflowTagWithHttpInfo(tagObject, name);
    }

  
    private ApiResponse<Void> deleteWorkflowTagWithHttpInfo(TagObject tagObject, String name)
            throws ApiException {
        okhttp3.Call call =
                deleteWorkflowTagValidateBeforeCall(tagObject, name);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call getTagsCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/tags";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getTagsValidateBeforeCall()
            throws ApiException {

        return getTagsCall();
    }

  
    public List<TagObject> getTags() throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<List<TagObject>> getTagsWithHttpInfo() throws ApiException {
        okhttp3.Call call = getTagsValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getTaskTagsCall(
            String taskName)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                httpClient.escapeString(taskName));

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

    private okhttp3.Call getTaskTagsValidateBeforeCall(
            String taskName)
            throws ApiException {
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling getTaskTags(Async)");
        }

        return getTaskTagsCall(taskName);
    }

  
    public List<TagObject> getTaskTags(String taskName) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTaskTagsWithHttpInfo(taskName);
        return resp.getData();
    }

  
    private ApiResponse<List<TagObject>> getTaskTagsWithHttpInfo(String taskName)
            throws ApiException {
        okhttp3.Call call = getTaskTagsValidateBeforeCall(taskName);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getWorkflowTagsCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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

    private okhttp3.Call getWorkflowTagsValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getWorkflowTags(Async)");
        }

        return getWorkflowTagsCall(name);
    }

  
    public List<TagObject> getWorkflowTags(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getWorkflowTagsWithHttpInfo(name);
        return resp.getData();
    }

  
    private ApiResponse<List<TagObject>> getWorkflowTagsWithHttpInfo(String name)
            throws ApiException {
        okhttp3.Call call = getWorkflowTagsValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call setTaskTagsCall(
            List<TagObject> tagObjects,
            String taskName)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/task/{taskName}/tags"
                        .replaceAll(
                                "\\{" + "taskName" + "\\}",
                                httpClient.escapeString(taskName));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagObjects,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call setTaskTagsValidateBeforeCall(
            List<TagObject> tagObjects,
            String taskName)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObjects == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObjects' when calling setTaskTags(Async)");
        }
        // verify the required parameter 'taskName' is set
        if (taskName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskName' when calling setTaskTags(Async)");
        }

        return setTaskTagsCall(tagObjects, taskName);
    }

  
    public void setTaskTags(List<TagObject> tagObjects, String taskName) throws ApiException {
        setTaskTagsWithHttpInfo(tagObjects, taskName);
    }

  
    private ApiResponse<Void> setTaskTagsWithHttpInfo(List<TagObject> tagObjects, String taskName)
            throws ApiException {
        okhttp3.Call call =
                setTaskTagsValidateBeforeCall(tagObjects, taskName);
        return httpClient.execute(call);
    }

  
    public okhttp3.Call setWorkflowTagsCall(
            List<TagObject> tagObjects,
            String name)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/tags"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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



        String[] localVarAuthNames = new String[] {"api_key"};
        return httpClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                tagObjects,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call setWorkflowTagsValidateBeforeCall(
            List<TagObject> tagObjects,
            String name)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (tagObjects == null) {
            throw new ApiException(
                    "Missing the required parameter 'tagObjects' when calling setWorkflowTags(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling setWorkflowTags(Async)");
        }

        return setWorkflowTagsCall(tagObjects, name);
    }

  
    public void setWorkflowTags(List<TagObject> tagObjects, String name) throws ApiException {
        setWorkflowTagsWithHttpInfo(tagObjects, name);
    }

  
    private ApiResponse<Void> setWorkflowTagsWithHttpInfo(List<TagObject> tagObjects, String name)
            throws ApiException {
        okhttp3.Call call =
                setWorkflowTagsValidateBeforeCall(tagObjects, name);
        return httpClient.execute(call);
    }
}
