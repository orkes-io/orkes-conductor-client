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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.ExternalStorageLocation;
import io.orkes.conductor.client.model.SearchResultTask;
import io.orkes.conductor.client.model.metadata.tasks.PollData;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.metadata.tasks.TaskExecLog;
import io.orkes.conductor.client.model.metadata.tasks.TaskResult;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.TaskSummary;
import io.orkes.conductor.client.model.run.Workflow;

import com.fasterxml.jackson.core.type.TypeReference;

class TaskResource {
    private final OrkesHttpClient apiClient;

    public TaskResource(OrkesHttpClient httpClient) {
        this.apiClient = httpClient;
    }

  
    public okhttp3.Call allCall() throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/all";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call allValidateBeforeCall()
            throws ApiException {

        return allCall();
    }

  
    public Map<String, Long> all() throws ApiException {
        ApiResponse<Map<String, Long>> resp = allWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<Map<String, Long>> allWithHttpInfo() throws ApiException {
        okhttp3.Call call = allValidateBeforeCall();
        Type localVarReturnType = new TypeReference<Map<String, Long>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call allVerboseCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/all/verbose";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call allVerboseValidateBeforeCall()
            throws ApiException {

        return allVerboseCall();
    }

  
    public Map<String, Map<String, Map<String, Long>>> allVerbose() throws ApiException {
        ApiResponse<Map<String, Map<String, Map<String, Long>>>> resp = allVerboseWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<Map<String, Map<String, Map<String, Long>>>> allVerboseWithHttpInfo()
            throws ApiException {
        okhttp3.Call call = allVerboseValidateBeforeCall();
        Type localVarReturnType =
                new TypeReference<Map<String, Map<String, Map<String, Long>>>>() {
                }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call batchPollCall(
            String tasktype,
            String workerid,
            String domain,
            Integer count,
            Integer timeout)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/poll/batch/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (workerid != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerid));
        if (domain != null) localVarQueryParams.addAll(apiClient.parameterToPair("domain", domain));
        if (count != null) localVarQueryParams.addAll(apiClient.parameterToPair("count", count));
        if (timeout != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("timeout", timeout));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call batchPollValidateBeforeCall(
            String tasktype,
            String workerid,
            String domain,
            Integer count,
            Integer timeout)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling batchPoll(Async)");
        }

        return batchPollCall(
                tasktype,
                workerid,
                domain,
                count,
                timeout);
    }

  
    public List<Task> batchPoll(
            String tasktype, String workerid, String domain, Integer count, Integer timeout)
            throws ApiException {
        ApiResponse<List<Task>> resp =
                batchPollWithHttpInfo(tasktype, workerid, domain, count, timeout);
        return resp.getData();
    }

  
    private ApiResponse<List<Task>> batchPollWithHttpInfo(
            String tasktype, String workerid, String domain, Integer count, Integer timeout)
            throws ApiException {
        okhttp3.Call call =
                batchPollValidateBeforeCall(tasktype, workerid, domain, count, timeout);
        Type localVarReturnType = new TypeReference<List<Task>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getAllPollDataCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/polldata/all";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getAllPollDataValidateBeforeCall()
            throws ApiException {

        return getAllPollDataCall();
    }

  
    public List<PollData> getAllPollData() throws ApiException {
        ApiResponse<List<PollData>> resp = getAllPollDataWithHttpInfo();
        return resp.getData();
    }

  
    private ApiResponse<List<PollData>> getAllPollDataWithHttpInfo() throws ApiException {
        okhttp3.Call call = getAllPollDataValidateBeforeCall();
        Type localVarReturnType = new TypeReference<List<PollData>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getExternalStorageLocation1Call(
            String path,
            String operation,
            String payloadType)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/externalstoragelocation";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (path != null) localVarQueryParams.addAll(apiClient.parameterToPair("path", path));
        if (operation != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("operation", operation));
        if (payloadType != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("payloadType", payloadType));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getExternalStorageLocation1ValidateBeforeCall(
            String path,
            String operation,
            String payloadType)
            throws ApiException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException(
                    "Missing the required parameter 'path' when calling getExternalStorageLocation1(Async)");
        }
        // verify the required parameter 'operation' is set
        if (operation == null) {
            throw new ApiException(
                    "Missing the required parameter 'operation' when calling getExternalStorageLocation1(Async)");
        }
        // verify the required parameter 'payloadType' is set
        if (payloadType == null) {
            throw new ApiException(
                    "Missing the required parameter 'payloadType' when calling getExternalStorageLocation1(Async)");
        }

        return getExternalStorageLocation1Call(
                path, operation, payloadType);
    }

  
    public ExternalStorageLocation getExternalStorageLocation1(
            String path, String operation, String payloadType) throws ApiException {
        ApiResponse<ExternalStorageLocation> resp =
                getExternalStorageLocation1WithHttpInfo(path, operation, payloadType);
        return resp.getData();
    }

  
    private ApiResponse<ExternalStorageLocation> getExternalStorageLocation1WithHttpInfo(
            String path, String operation, String payloadType) throws ApiException {
        okhttp3.Call call =
                getExternalStorageLocation1ValidateBeforeCall(
                        path, operation, payloadType);
        Type localVarReturnType = new TypeReference<ExternalStorageLocation>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getPollDataCall(
            String taskType)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/polldata";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (taskType != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("taskType", taskType));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getPollDataValidateBeforeCall(
            String taskType)
            throws ApiException {
        // verify the required parameter 'taskType' is set
        if (taskType == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskType' when calling getPollData(Async)");
        }

        return getPollDataCall(taskType);
    }

  
    public List<PollData> getPollData(String taskType) throws ApiException {
        ApiResponse<List<PollData>> resp = getPollDataWithHttpInfo(taskType);
        return resp.getData();
    }

  
    private ApiResponse<List<PollData>> getPollDataWithHttpInfo(String taskType)
            throws ApiException {
        okhttp3.Call call = getPollDataValidateBeforeCall(taskType);
        Type localVarReturnType = new TypeReference<List<PollData>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getTaskCall(
            String taskId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getTaskValidateBeforeCall(
            String taskId)
            throws ApiException {
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling getTask(Async)");
        }

        return getTaskCall(taskId);
    }

  
    public Task getTask(String taskId) throws ApiException {
        ApiResponse<Task> resp = getTaskWithHttpInfo(taskId);
        return resp.getData();
    }

  
    private ApiResponse<Task> getTaskWithHttpInfo(String taskId) throws ApiException {
        okhttp3.Call call = getTaskValidateBeforeCall(taskId);
        Type localVarReturnType = new TypeReference<Task>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call getTaskLogsCall(
            String taskId)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}/log"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call getTaskLogsValidateBeforeCall(
            String taskId)
            throws ApiException {
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling getTaskLogs(Async)");
        }

        return getTaskLogsCall(taskId);
    }

  
    public List<TaskExecLog> getTaskLogs(String taskId) throws ApiException {
        ApiResponse<List<TaskExecLog>> resp = getTaskLogsWithHttpInfo(taskId);
        return resp.getData();
    }

  
    private ApiResponse<List<TaskExecLog>> getTaskLogsWithHttpInfo(String taskId)
            throws ApiException {
        okhttp3.Call call = getTaskLogsValidateBeforeCall(taskId);
        Type localVarReturnType = new TypeReference<List<TaskExecLog>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call logCall(
            String body,
            String taskId)
            throws ApiException {

        // create path and map variables
        String localVarPath =
                "/tasks/{taskId}/log"
                        .replaceAll(
                                "\\{" + "taskId" + "\\}",
                                apiClient.escapeString(taskId));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {};

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
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call logValidateBeforeCall(
            String body,
            String taskId)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling log(Async)");
        }
        // verify the required parameter 'taskId' is set
        if (taskId == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskId' when calling log(Async)");
        }

        return logCall(body, taskId);
    }

  
    public void log(String body, String taskId) throws ApiException {
        logWithHttpInfo(body, taskId);
    }

  
    private ApiResponse<Void> logWithHttpInfo(String body, String taskId) throws ApiException {
        okhttp3.Call call = logValidateBeforeCall(body, taskId);
        return apiClient.execute(call);
    }

  
    public okhttp3.Call pollCall(
            String tasktype,
            String workerid,
            String domain)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/poll/{tasktype}"
                        .replaceAll(
                                "\\{" + "tasktype" + "\\}",
                                apiClient.escapeString(tasktype));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (workerid != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerid));
        if (domain != null) localVarQueryParams.addAll(apiClient.parameterToPair("domain", domain));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call pollValidateBeforeCall(
            String tasktype,
            String workerid,
            String domain)
            throws ApiException {
        // verify the required parameter 'tasktype' is set
        if (tasktype == null) {
            throw new ApiException(
                    "Missing the required parameter 'tasktype' when calling poll(Async)");
        }

        return pollCall(tasktype, workerid, domain);
    }

  
    public Task poll(String tasktype, String workerid, String domain) throws ApiException {
        ApiResponse<Task> resp = pollWithHttpInfo(tasktype, workerid, domain);
        return resp.getData();
    }

  
    private ApiResponse<Task> pollWithHttpInfo(String tasktype, String workerid, String domain)
            throws ApiException {
        okhttp3.Call call =
                pollValidateBeforeCall(tasktype, workerid, domain);
        Type localVarReturnType = new TypeReference<Task>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call requeuePendingTaskCall(
            String taskType)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/tasks/queue/requeue/{taskType}"
                        .replaceAll(
                                "\\{" + "taskType" + "\\}",
                                apiClient.escapeString(taskType));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
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

    private okhttp3.Call requeuePendingTaskValidateBeforeCall(
            String taskType)
            throws ApiException {
        // verify the required parameter 'taskType' is set
        if (taskType == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskType' when calling requeuePendingTask(Async)");
        }

        return requeuePendingTaskCall(taskType);
    }

  
    public String requeuePendingTask(String taskType) throws ApiException {
        ApiResponse<String> resp = requeuePendingTaskWithHttpInfo(taskType);
        return resp.getData();
    }

  
    private ApiResponse<String> requeuePendingTaskWithHttpInfo(String taskType)
            throws ApiException {
        okhttp3.Call call = requeuePendingTaskValidateBeforeCall(taskType);
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call searchTasksCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/search";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call searchTasksValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchTasksCall(
                start,
                size,
                sort,
                freeText,
                query);
    }

  
    public SearchResult<TaskSummary> searchTasks(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResult<TaskSummary>> resp =
                searchTasksWithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

  
    private ApiResponse<SearchResult<TaskSummary>> searchTasksWithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchTasksValidateBeforeCall(start, size, sort, freeText, query);
        Type localVarReturnType = new TypeReference<SearchResult<TaskSummary>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call searchV21Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/search-v2";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(apiClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(apiClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(apiClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(apiClient.parameterToPair("query", query));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call searchV21ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchV21Call(
                start,
                size,
                sort,
                freeText,
                query);
    }

  
    public SearchResultTask searchV21(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultTask> resp =
                searchV21WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

  
    private ApiResponse<SearchResultTask> searchV21WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchV21ValidateBeforeCall(start, size, sort, freeText, query);
        Type localVarReturnType = new TypeReference<SearchResultTask>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    public okhttp3.Call sizeCall(
            List<String> taskType)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/tasks/queue/sizes";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();
        if (taskType != null)
            localVarCollectionQueryParams.addAll(
                    apiClient.parameterToPairs("multi", "taskType", taskType));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"*/*"};
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

    private okhttp3.Call sizeValidateBeforeCall(
            List<String> taskType)
            throws ApiException {

        return sizeCall(taskType);
    }

  
    public Map<String, Integer> size(List<String> taskType) throws ApiException {
        ApiResponse<Map<String, Integer>> resp = sizeWithHttpInfo(taskType);
        return resp.getData();
    }

  
    private ApiResponse<Map<String, Integer>> sizeWithHttpInfo(List<String> taskType)
            throws ApiException {
        okhttp3.Call call = sizeValidateBeforeCall(taskType);
        Type localVarReturnType = new TypeReference<Map<String, Integer>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

  
    private okhttp3.Call updateTaskCall(
            TaskResult taskResult)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/tasks";

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
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
                taskResult,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call updateTaskValidateBeforeCall(
            TaskResult taskResult)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (taskResult == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskResult' when calling updateTask(Async)");
        }

        return updateTaskCall(taskResult);
    }

  
    public String updateTask(TaskResult taskResult) throws ApiException {
        ApiResponse<String> resp = updateTaskWithHttpInfo(taskResult);
        return resp.getData();
    }

  
    private ApiResponse<String> updateTaskWithHttpInfo(TaskResult taskResult) throws ApiException {
        okhttp3.Call call = updateTaskValidateBeforeCall(taskResult);
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private okhttp3.Call updateTaskByRefNameCall(
            Map<String, Object> body,
            String workflowId,
            String taskRefName,
            String status,
            String workerId,
            boolean sync)
            throws ApiException {
        String path = "/tasks/{workflowId}/{taskRefName}/{status}";
        if (sync) {
            path += "/sync";
        }
        // create path and map variables
        String localVarPath =
                path
                        .replaceAll(
                                "\\{" + "workflowId" + "\\}",
                                apiClient.escapeString(workflowId))
                        .replaceAll(
                                "\\{" + "taskRefName" + "\\}",
                                apiClient.escapeString(taskRefName))
                        .replaceAll(
                                "\\{" + "status" + "\\}",
                                apiClient.escapeString(status));

        List<Param> localVarQueryParams = new ArrayList<>();
        List<Param> localVarCollectionQueryParams = new ArrayList<>();

        if (workerId == null) {
            workerId = getIdentity();
        }
        localVarQueryParams.addAll(apiClient.parameterToPair("workerid", workerId));

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"text/plain"};
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
                body,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call updateTask1ValidateBeforeCall(
            Map<String, Object> body,
            String workflowId,
            String taskRefName,
            String status,
            boolean sync)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling updateTask1(Async)");
        }
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new ApiException(
                    "Missing the required parameter 'workflowId' when calling updateTask1(Async)");
        }
        // verify the required parameter 'taskRefName' is set
        if (taskRefName == null) {
            throw new ApiException(
                    "Missing the required parameter 'taskRefName' when calling updateTask1(Async)");
        }
        // verify the required parameter 'status' is set
        if (status == null) {
            throw new ApiException(
                    "Missing the required parameter 'status' when calling updateTask1(Async)");
        }

        return updateTaskByRefNameCall(
                body,
                workflowId,
                taskRefName,
                status,
                getIdentity(),
                sync);
    }

  
    @Deprecated
    public String updateTask1(
            Map<String, Object> body, String workflowId, String taskRefName, String status)
            throws ApiException {
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        ApiResponse<String> resp = updateTask1WithHttpInfo(body, workflowId, taskRefName, status, false, localVarReturnType);
        return resp.getData();
    }

  
    public String updateTaskByRefName(Map<String, Object> output, String workflowId, String taskRefName, String status) throws ApiException {
        Type localVarReturnType = new TypeReference<String>() {
        }.getType();
        ApiResponse<String> resp = updateTask1WithHttpInfo(output, workflowId, taskRefName, status, false, localVarReturnType);
        return resp.getData();
    }

  
    public Workflow updateTaskSync(Map<String, Object> output, String workflowId, String taskRefName, String status) throws ApiException {
        Type localVarReturnType = new TypeReference<Workflow>() {
        }.getType();
        ApiResponse<Workflow> resp = updateTask1WithHttpInfo(output, workflowId, taskRefName, status, true, localVarReturnType);
        return resp.getData();
    }

  
    private <T> ApiResponse<T> updateTask1WithHttpInfo(Map<String, Object> body, String workflowId, String taskRefName, String status, boolean sync, Type returnType)
            throws ApiException {
        okhttp3.Call call =
                updateTask1ValidateBeforeCall(body, workflowId, taskRefName, status, sync);
        return apiClient.execute(call, returnType);
    }

    private ApiResponse<Workflow> updateTaskSyncWithHttpInfo(Map<String, Object> body, String workflowId, String taskRefName, String status, boolean sync)
            throws ApiException {
        okhttp3.Call call =
                updateTask1ValidateBeforeCall(body, workflowId, taskRefName, status, sync);
        Type localVarReturnType = new TypeReference<Workflow>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    private String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }
//        if (serverId == null) {
//            serverId =
//                    (EC2MetadataUtils.getInstanceId() == null)
//                            ? System.getProperty("user.name")
//                            : EC2MetadataUtils.getInstanceId();
//        }
        return serverId;
    }
}
