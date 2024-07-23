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
import io.orkes.conductor.client.model.SaveScheduleRequest;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecution;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.WorkflowSchedule;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SchedulerResource {
    private OrkesHttpClient httpClient;

    public SchedulerResource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    /**
     * Build call for deleteSchedule
     *
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call deleteScheduleCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/scheduler/schedules/{name}"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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

    private okhttp3.Call deleteScheduleValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling deleteSchedule(Async)");
        }

        return deleteScheduleCall(name);
    }

    /**
     * Deletes an existing workflow schedule by name
     *
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteSchedule(String name) throws ApiException {
        deleteScheduleWithHttpInfo(name);
    }

    /**
     * Deletes an existing workflow schedule by name
     *
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> deleteScheduleWithHttpInfo(String name) throws ApiException {
        okhttp3.Call call = deleteScheduleValidateBeforeCall(name);
        return httpClient.execute(call);
    }

    /**
     * Build call for getAllSchedules
     *
     * @param workflowName (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getAllSchedulesCall(
            String workflowName)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/schedules";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (workflowName != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("workflowName", workflowName));

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

    private okhttp3.Call getAllSchedulesValidateBeforeCall(
            String workflowName)
            throws ApiException {

        return getAllSchedulesCall(workflowName);
    }

    /**
     * Get all existing workflow schedules and optionally filter by workflow name
     *
     * @param workflowName (optional)
     * @return List&lt;WorkflowSchedule&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<WorkflowSchedule> getAllSchedules(String workflowName) throws ApiException {
        ApiResponse<List<WorkflowSchedule>> resp = getAllSchedulesWithHttpInfo(workflowName);
        return resp.getData();
    }

    /**
     * Get all existing workflow schedules and optionally filter by workflow name
     *
     * @param workflowName (optional)
     * @return ApiResponse&lt;List&lt;WorkflowSchedule&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<WorkflowSchedule>> getAllSchedulesWithHttpInfo(String workflowName)
            throws ApiException {
        okhttp3.Call call = getAllSchedulesValidateBeforeCall(workflowName);
        Type localVarReturnType = new TypeReference<List<WorkflowSchedule>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getNextFewSchedules
     *
     * @param cronExpression (required)
     * @param scheduleStartTime (optional)
     * @param scheduleEndTime (optional)
     * @param limit (optional, default to 3)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getNextFewSchedulesCall(
            String cronExpression,
            Long scheduleStartTime,
            Long scheduleEndTime,
            Integer limit)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/nextFewSchedules";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (cronExpression != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("cronExpression", cronExpression));
        if (scheduleStartTime != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("scheduleStartTime", scheduleStartTime));
        if (scheduleEndTime != null)
            localVarQueryParams.addAll(
                    httpClient.parameterToPair("scheduleEndTime", scheduleEndTime));
        if (limit != null) localVarQueryParams.addAll(httpClient.parameterToPair("limit", limit));

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

    private okhttp3.Call getNextFewSchedulesValidateBeforeCall(
            String cronExpression,
            Long scheduleStartTime,
            Long scheduleEndTime,
            Integer limit)
            throws ApiException {
        // verify the required parameter 'cronExpression' is set
        if (cronExpression == null) {
            throw new ApiException(
                    "Missing the required parameter 'cronExpression' when calling getNextFewSchedules(Async)");
        }

        return getNextFewSchedulesCall(
                cronExpression,
                scheduleStartTime,
                scheduleEndTime,
                limit);
    }

    /**
     * Get list of the next x (default 3, max 5) execution times for a scheduler
     *
     * @param cronExpression (required)
     * @param scheduleStartTime (optional)
     * @param scheduleEndTime (optional)
     * @param limit (optional, default to 3)
     * @return List&lt;Long&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<Long> getNextFewSchedules(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
            throws ApiException {
        ApiResponse<List<Long>> resp =
                getNextFewSchedulesWithHttpInfo(
                        cronExpression, scheduleStartTime, scheduleEndTime, limit);
        return resp.getData();
    }

    /**
     * Get list of the next x (default 3, max 5) execution times for a scheduler
     *
     * @param cronExpression (required)
     * @param scheduleStartTime (optional)
     * @param scheduleEndTime (optional)
     * @param limit (optional, default to 3)
     * @return ApiResponse&lt;List&lt;Long&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<Long>> getNextFewSchedulesWithHttpInfo(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
            throws ApiException {
        okhttp3.Call call =
                getNextFewSchedulesValidateBeforeCall(
                        cronExpression, scheduleStartTime, scheduleEndTime, limit);
        Type localVarReturnType = new TypeReference<List<Long>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getSchedule
     *
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getScheduleCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/scheduler/schedules/{name}"
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

    private okhttp3.Call getScheduleValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getSchedule(Async)");
        }

        return getScheduleCall(name);
    }

    /**
     * Get an existing workflow schedule by name
     *
     * @param name (required)
     * @return WorkflowSchedule
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public WorkflowSchedule getSchedule(String name) throws ApiException {
        ApiResponse<WorkflowSchedule> resp = getScheduleWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get an existing workflow schedule by name
     *
     * @param name (required)
     * @return ApiResponse&lt;WorkflowSchedule&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<WorkflowSchedule> getScheduleWithHttpInfo(String name) throws ApiException {
        okhttp3.Call call = getScheduleValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<WorkflowSchedule>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for pauseAllSchedules
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call pauseAllSchedulesCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/admin/pause";

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

    private okhttp3.Call pauseAllSchedulesValidateBeforeCall()
            throws ApiException {

        return pauseAllSchedulesCall();
    }

    /**
     * Pause all scheduling in a single conductor server instance (for debugging only)
     *
     * @return Map&lt;String, Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Object> pauseAllSchedules() throws ApiException {
        ApiResponse<Map<String, Object>> resp = pauseAllSchedulesWithHttpInfo();
        return resp.getData();
    }

    /**
     * Pause all scheduling in a single conductor server instance (for debugging only)
     *
     * @return ApiResponse&lt;Map&lt;String, Object&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Object>> pauseAllSchedulesWithHttpInfo() throws ApiException {
        okhttp3.Call call = pauseAllSchedulesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for pauseSchedule
     *
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call pauseScheduleCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/scheduler/schedules/{name}/pause"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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

    private okhttp3.Call pauseScheduleValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling pauseSchedule(Async)");
        }

        return pauseScheduleCall(name);
    }

    /**
     * Pauses an existing schedule by name
     *
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void pauseSchedule(String name) throws ApiException {
        pauseScheduleWithHttpInfo(name);
    }

    /**
     * Pauses an existing schedule by name
     *
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> pauseScheduleWithHttpInfo(String name) throws ApiException {
        okhttp3.Call call = pauseScheduleValidateBeforeCall(name);
        return httpClient.execute(call);
    }

    /**
     * Build call for requeueAllExecutionRecords
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call requeueAllExecutionRecordsCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/admin/requeue";

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

    private okhttp3.Call requeueAllExecutionRecordsValidateBeforeCall()
            throws ApiException {

        return requeueAllExecutionRecordsCall();
    }

    /**
     * Requeue all execution records
     *
     * @return Map&lt;String, Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Object> requeueAllExecutionRecords() throws ApiException {
        ApiResponse<Map<String, Object>> resp = requeueAllExecutionRecordsWithHttpInfo();
        return resp.getData();
    }

    /**
     * Requeue all execution records
     *
     * @return ApiResponse&lt;Map&lt;String, Object&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Object>> requeueAllExecutionRecordsWithHttpInfo()
            throws ApiException {
        okhttp3.Call call = requeueAllExecutionRecordsValidateBeforeCall();
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for resumeAllSchedules
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call resumeAllSchedulesCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/admin/resume";

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

    private okhttp3.Call resumeAllSchedulesValidateBeforeCall()
            throws ApiException {

        return resumeAllSchedulesCall();
    }

    /**
     * Resume all scheduling
     *
     * @return Map&lt;String, Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Map<String, Object> resumeAllSchedules() throws ApiException {
        ApiResponse<Map<String, Object>> resp = resumeAllSchedulesWithHttpInfo();
        return resp.getData();
    }

    /**
     * Resume all scheduling
     *
     * @return ApiResponse&lt;Map&lt;String, Object&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Map<String, Object>> resumeAllSchedulesWithHttpInfo() throws ApiException {
        okhttp3.Call call = resumeAllSchedulesValidateBeforeCall();
        Type localVarReturnType = new TypeReference<Map<String, Object>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for resumeSchedule
     *
     * @param name (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call resumeScheduleCall(
            String name)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/scheduler/schedules/{name}/resume"
                        .replaceAll(
                                "\\{" + "name" + "\\}", httpClient.escapeString(name));

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

    private okhttp3.Call resumeScheduleValidateBeforeCall(
            String name)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling resumeSchedule(Async)");
        }

        return resumeScheduleCall(name);
    }

    /**
     * Resume a paused schedule by name
     *
     * @param name (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void resumeSchedule(String name) throws ApiException {
        resumeScheduleWithHttpInfo(name);
    }

    /**
     * Resume a paused schedule by name
     *
     * @param name (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> resumeScheduleWithHttpInfo(String name) throws ApiException {
        okhttp3.Call call = resumeScheduleValidateBeforeCall(name);
        return httpClient.execute(call);
    }

    /**
     * Build call for saveSchedule
     *
     * @param saveScheduleRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call saveScheduleCall(
            SaveScheduleRequest saveScheduleRequest)
            throws ApiException {

        // create path and map variables
        String localVarPath = "/scheduler/schedules";

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
                saveScheduleRequest,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames);
    }

    private okhttp3.Call saveScheduleValidateBeforeCall(
            SaveScheduleRequest saveScheduleRequest)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (saveScheduleRequest == null) {
            throw new ApiException(
                    "Missing the required parameter 'saveScheduleRequest' when calling saveSchedule(Async)");
        }

        return saveScheduleCall(saveScheduleRequest);
    }

    /**
     * Create or update a schedule for a specified workflow with a corresponding start workflow
     * request
     *
     * @param saveScheduleRequest (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void saveSchedule(SaveScheduleRequest saveScheduleRequest) throws ApiException {
        saveScheduleWithHttpInfo(saveScheduleRequest);
    }

    /**
     * Create or update a schedule for a specified workflow with a corresponding start workflow
     * request
     *
     * @param saveScheduleRequest (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> saveScheduleWithHttpInfo(SaveScheduleRequest saveScheduleRequest)
            throws ApiException {
        okhttp3.Call call =
                saveScheduleValidateBeforeCall(saveScheduleRequest);
        return httpClient.execute(call);
    }

    /**
     * Build call for searchV22
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call searchV22Call(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/search/executions";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if (start != null) localVarQueryParams.addAll(httpClient.parameterToPair("start", start));
        if (size != null) localVarQueryParams.addAll(httpClient.parameterToPair("size", size));
        if (sort != null) localVarQueryParams.addAll(httpClient.parameterToPair("sort", sort));
        if (freeText != null)
            localVarQueryParams.addAll(httpClient.parameterToPair("freeText", freeText));
        if (query != null) localVarQueryParams.addAll(httpClient.parameterToPair("query", query));

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

    private okhttp3.Call searchV22ValidateBeforeCall(
            Integer start,
            Integer size,
            String sort,
            String freeText,
            String query)
            throws ApiException {

        return searchV22Call(
                start,
                size,
                sort,
                freeText,
                query);
    }

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return SearchResultWorkflowScheduleExecutionModel
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        ApiResponse<SearchResultWorkflowScheduleExecutionModel> resp =
                searchV22WithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    public SearchResultWorkflowScheduleExecution search(
        Integer start, Integer size, String sort, String freeText, String query)
        throws ApiException {
        ApiResponse<SearchResultWorkflowScheduleExecution> resp = searchWithHttpInfo(start, size, sort, freeText, query);
        return resp.getData();
    }

    /**
     * Search for workflows based on payload and other parameters use sort options as
     * sort&#x3D;&lt;field&gt;:ASC|DESC e.g. sort&#x3D;name&amp;sort&#x3D;workflowId:DESC. If order
     * is not specified, defaults to ASC.
     *
     * @param start (optional, default to 0)
     * @param size (optional, default to 100)
     * @param sort (optional)
     * @param freeText (optional, default to *)
     * @param query (optional)
     * @return ApiResponse&lt;SearchResultWorkflowScheduleExecutionModel&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<SearchResultWorkflowScheduleExecutionModel> searchV22WithHttpInfo(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        okhttp3.Call call =
                searchV22ValidateBeforeCall(start, size, sort, freeText, query);
        Type localVarReturnType =
                new TypeReference<SearchResultWorkflowScheduleExecutionModel>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    private ApiResponse<SearchResultWorkflowScheduleExecution> searchWithHttpInfo(
        Integer start, Integer size, String sort, String freeText, String query)
        throws ApiException {
        okhttp3.Call call =
            searchV22ValidateBeforeCall(start, size, sort, freeText, query);
        Type localVarReturnType =
            new TypeReference<SearchResultWorkflowScheduleExecution>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for testTimeout
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call testTimeoutCall()
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/test/timeout";

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

    private okhttp3.Call testTimeoutValidateBeforeCall()
            throws ApiException {

        return testTimeoutCall();
    }

    /**
     * Test timeout - do not use in production
     *
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void testTimeout() throws ApiException {
        testTimeoutWithHttpInfo();
    }

    /**
     * Test timeout - do not use in production
     *
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Void> testTimeoutWithHttpInfo() throws ApiException {
        okhttp3.Call call = testTimeoutValidateBeforeCall();
        return httpClient.execute(call);
    }

    /**
     * Delete a tag for schedule
     *
     * @param body  (required)
     * @param name  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForSchedule(List<TagObject> body, String name) throws ApiException {
        deleteTagForScheduleWithHttpInfo(body, name);
    }

    /**
     * Delete a tag for schedule
     *
     * @param body  (required)
     * @param name  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> deleteTagForScheduleWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        okhttp3.Call call = deleteTagForScheduleValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteTagForScheduleValidateBeforeCall(List<TagObject> body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForSchedule(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling deleteTagForSchedule(Async)");
        }

        return deleteTagForScheduleCall(body, name);
    }

    /**
     * Build call for deleteTagForSchedule
     * @param body  (required)
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private okhttp3.Call deleteTagForScheduleCall(List<TagObject> body, String name) throws ApiException {
        // create path and map variables
        String localVarPath = "/scheduler/schedules/{name}/tags"
                .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    /**
     * Put a tag to schedule
     *
     * @param body  (required)
     * @param name  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForSchedule(List<TagObject> body, String name) throws ApiException {
        putTagForScheduleWithHttpInfo(body, name);
    }

    /**
     * Put a tag to schedule
     *
     * @param body  (required)
     * @param name  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> putTagForScheduleWithHttpInfo(List<TagObject> body, String name) throws ApiException {
        okhttp3.Call call = putTagForScheduleValidateBeforeCall(body, name);
        return httpClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call putTagForScheduleValidateBeforeCall(List<TagObject> body, String name) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForSchedule(Async)");
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling putTagForSchedule(Async)");
        }

        return putTagForScheduleCall(body, name);
    }

    /**
     * Build call for putTagForSchedule
     * @param body  (required)
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private okhttp3.Call putTagForScheduleCall(List<TagObject> body, String name) throws ApiException {

        // create path and map variables
        String localVarPath = "/scheduler/schedules/{name}/tags"
                .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, body, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }

    /**
     * Get tags by schedule
     *
     * @param name  (required)
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTagsForSchedule(String name) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsForScheduleWithHttpInfo(name);
        return resp.getData();
    }

    /**
     * Get tags by schedule
     *
     * @param name  (required)
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<TagObject>> getTagsForScheduleWithHttpInfo(String name) throws ApiException {
        okhttp3.Call call = getTagsForScheduleValidateBeforeCall(name);
        Type localVarReturnType = new TypeReference<List<TagObject>>() {}.getType();
        return httpClient.execute(call, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getTagsForScheduleValidateBeforeCall(String name) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getTagsForSchedule(Async)");
        }

        return getTagsForScheduleCall(name);
    }

    /**
     * Build call for getTagsForSchedule
     * @param name  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getTagsForScheduleCall(String name) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/scheduler/schedules/{name}/tags"
                .replaceAll("\\{" + "name" + "\\}", httpClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = httpClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = httpClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "api_key" };
        return httpClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames);
    }
}