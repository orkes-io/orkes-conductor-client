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
import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowDef;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Call;

class MetadataResource {
    private final OrkesHttpClient httpClient;

    public MetadataResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

  
    public Call createCall(WorkflowDef workflowDef, Boolean overwrite) throws ApiException {

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (overwrite != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("overwrite", overwrite));

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
                workflowDef,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call createValidateBeforeCall(WorkflowDef workflowDef, Boolean overwrite)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowDef == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowDef' when calling create(Async)");
        }

        return createCall(workflowDef, overwrite);
    }

  
    public void create(WorkflowDef workflowDef, Boolean overwrite) throws ApiException {
        createWithHttpInfo(workflowDef, overwrite);
    }

  
    private ApiResponse<Void> createWithHttpInfo(WorkflowDef workflowDef, Boolean overwrite)
            throws ApiException {
        Call call = createValidateBeforeCall(workflowDef, overwrite);
        return httpClient.execute(call);
    }

  
    public Call getCall(String name, Integer version, Boolean metadata)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (version != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("version", version));
        if (metadata != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("metadata", metadata));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private Call getValidateBeforeCall(String name, Integer version, Boolean metadata)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling get(Async)");
        }

        return getCall(name, version, metadata);
    }

  
    public WorkflowDef get(String name, Integer version, Boolean metadata) throws ApiException {
        ApiResponse<WorkflowDef> resp = getWithHttpInfo(name, version, metadata);
        return resp.getData();
    }

  
    private ApiResponse<WorkflowDef> getWithHttpInfo(String name, Integer version, Boolean metadata)
            throws ApiException {
        Call call = getValidateBeforeCall(name, version, metadata);
        return httpClient.execute(call, WorkflowDef.class);
    }

  
    public Call getAllWorkflowsCall(String access, Boolean metadata, String tagKey, String tagValue)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (access != null) localVarQueryParams.addAll(httpClient.parameterToPair("access", access));
        if (metadata != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("metadata", metadata));
        if (tagKey != null) localVarQueryParams.addAll(httpClient.parameterToPair("tagKey", tagKey));
        if (tagValue != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("tagValue", tagValue));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private Call getAllWorkflowsValidateBeforeCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue)
            throws ApiException {

        return getAllWorkflowsCall(
                access,
                metadata,
                tagKey,
                tagValue);
    }

  
    public List<WorkflowDef> getAllWorkflows(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        ApiResponse<List<WorkflowDef>> resp =
                getAllWorkflowsWithHttpInfo(access, metadata, tagKey, tagValue);
        return resp.getData();
    }

  
    private ApiResponse<List<WorkflowDef>> getAllWorkflowsWithHttpInfo(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        Call call = getAllWorkflowsValidateBeforeCall(access, metadata, tagKey, tagValue);
        Type localVarReturnType = new TypeReference<List<WorkflowDef>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getTaskDefCall(String tasktype, Boolean metadata) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/taskdefs/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                httpClient.escapeString(tasktype));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (metadata != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("metadata", metadata));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private Call getTaskDefValidateBeforeCall(
            String tasktype,
            Boolean metadata)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling getTaskDef(Async)");
        }

        return getTaskDefCall(tasktype, metadata);
    }

  
    public TaskDef getTaskDef(String tasktype, Boolean metadata) throws ApiException {
        ApiResponse<TaskDef> resp = getTaskDefWithHttpInfo(tasktype, metadata);
        return resp.getData();
    }

  
    private ApiResponse<TaskDef> getTaskDefWithHttpInfo(String tasktype, Boolean metadata)
            throws ApiException {
        Call call =
                getTaskDefValidateBeforeCall(tasktype, metadata);
        Type localVarReturnType = new TypeReference<TaskDef>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call getTaskDefsCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (access != null) localVarQueryParams.addAll(httpClient.parameterToPair("access", access));
        if (metadata != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("metadata", metadata));
        if (tagKey != null) localVarQueryParams.addAll(httpClient.parameterToPair("tagKey", tagKey));
        if (tagValue != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("tagValue", tagValue));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private Call getTaskDefsValidateBeforeCall(
            String access,
            Boolean metadata,
            String tagKey,
            String tagValue)
            throws ApiException {

        return getTaskDefsCall(
                access,
                metadata,
                tagKey,
                tagValue);
    }

  
    public List<TaskDef> getTaskDefs(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        ApiResponse<List<TaskDef>> resp =
                getTaskDefsWithHttpInfo(access, metadata, tagKey, tagValue);
        return resp.getData();
    }

  
    private ApiResponse<List<TaskDef>> getTaskDefsWithHttpInfo(
            String access, Boolean metadata, String tagKey, String tagValue) throws ApiException {
        Call call =
                getTaskDefsValidateBeforeCall(access, metadata, tagKey, tagValue);
        Type localVarReturnType = new TypeReference<List<TaskDef>>() {
        }.getType();
        return httpClient.execute(call, localVarReturnType);
    }

  
    public Call registerTaskDefCall(
            List<TaskDef> taskDefs)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

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
                taskDefs,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call registerTaskDefValidateBeforeCall(
            List<TaskDef> taskDefs)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (taskDefs == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskDefs' when calling registerTaskDef(Async)");
        }

        return registerTaskDefCall(taskDefs);
    }

  
    public void registerTaskDef(List<TaskDef> taskDefs) throws ApiException {
        registerTaskDefWithHttpInfo(taskDefs);
    }

  
    private ApiResponse<Void> registerTaskDefWithHttpInfo(List<TaskDef> taskDefs)
            throws ApiException {
        Call call = registerTaskDefValidateBeforeCall(taskDefs);
        return httpClient.execute(call);
    }

  
    public Call unregisterTaskDefCall(
            String tasktype)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/taskdefs/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                httpClient.escapeString(tasktype));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private Call unregisterTaskDefValidateBeforeCall(
            String tasktype)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling unregisterTaskDef(Async)");
        }

        return unregisterTaskDefCall(tasktype);
    }

  
    public void unregisterTaskDef(String tasktype) throws ApiException {
        unregisterTaskDefWithHttpInfo(tasktype);
    }

  
    private ApiResponse<Void> unregisterTaskDefWithHttpInfo(String tasktype) throws ApiException {
        Call call = unregisterTaskDefValidateBeforeCall(tasktype);
        return httpClient.execute(call);
    }

  
    public Call unregisterWorkflowDefCall(
            String name,
            Integer version)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/metadata/workflow/{name}/{version}"
                        .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name))
                        .replaceAll(
                                "\\{" + "version" + "\\}",
                                httpClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};

        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"api_key"};
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

    private Call unregisterWorkflowDefValidateBeforeCall(
            String name,
            Integer version)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling unregisterWorkflowDef(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException(
                    "Missing the required parameter 'version' when calling unregisterWorkflowDef(Async)");
        }

        return unregisterWorkflowDefCall(name, version);
    }

  
    public void unregisterWorkflowDef(String name, Integer version) throws ApiException {
        unregisterWorkflowDefWithHttpInfo(name, version);
    }

  
    private ApiResponse<Void> unregisterWorkflowDefWithHttpInfo(String name, Integer version)
            throws ApiException {
        Call call =
                unregisterWorkflowDefValidateBeforeCall(name, version);
        return httpClient.execute(call);
    }

  
    public Call updateCall(
            List<WorkflowDef> workflowDefs,
            Boolean overwrite)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/metadata/workflow";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (overwrite != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("overwrite", overwrite));

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                workflowDefs,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call updateValidateBeforeCall(
            List<WorkflowDef> workflowDefs,
            Boolean overwrite)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (workflowDefs == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowDefs' when calling update(Async)");
        }

        return updateCall(workflowDefs, overwrite);
    }

  
    public void update(List<WorkflowDef> workflowDefs, Boolean overwrite) throws ApiException {
        updateWithHttpInfo(workflowDefs, overwrite);
    }

  
    private ApiResponse<Void> updateWithHttpInfo(List<WorkflowDef> workflowDefs, Boolean overwrite)
            throws ApiException {
        Call call =
                updateValidateBeforeCall(workflowDefs, overwrite);
        return httpClient.execute(call);
    }

  
    public Call updateTaskDefCall(
            TaskDef taskDef)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/metadata/taskdefs";

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
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                taskDef,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private Call updateTaskDefValidateBeforeCall(
            TaskDef taskDef)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (taskDef == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskDef' when calling updateTaskDef(Async)");
        }

        return updateTaskDefCall(taskDef);
    }

  
    public void updateTaskDef(TaskDef taskDef) throws ApiException {
        updateTaskDefWithHttpInfo(taskDef);
    }

  
    private ApiResponse<Void> updateTaskDefWithHttpInfo(TaskDef taskDef) throws ApiException {
        Call call = updateTaskDefValidateBeforeCall(taskDef);
        return httpClient.execute(call);
    }
}
