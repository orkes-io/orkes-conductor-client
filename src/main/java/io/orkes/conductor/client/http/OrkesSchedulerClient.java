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
package io.orkes.conductor.client.http;

import java.util.List;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.SchedulerClient;
import io.orkes.conductor.client.http.api.SchedulerResourceApi;
import io.orkes.conductor.client.model.SaveScheduleRequest;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecution;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.WorkflowSchedule;

public class OrkesSchedulerClient extends OrkesClient implements SchedulerClient {

    private SchedulerResourceApi schedulerResourceApi;

    public OrkesSchedulerClient(ApiClient apiClient) {
        super(apiClient);
        this.schedulerResourceApi = new SchedulerResourceApi(apiClient);
    }

    @Override
    public void deleteSchedule(String name) throws ApiException {
        schedulerResourceApi.deleteSchedule(name);
    }

    @Override
    public List<WorkflowSchedule> getAllSchedules(String workflowName) throws ApiException {
        return schedulerResourceApi.getAllSchedules(workflowName);
    }

    @Override
    public List<Long> getNextFewSchedules(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
            throws ApiException {
        return schedulerResourceApi.getNextFewSchedules(
                cronExpression, scheduleStartTime, scheduleEndTime, limit);
    }

    @Override
    public WorkflowSchedule getSchedule(String name) throws ApiException {
        return schedulerResourceApi.getSchedule(name);
    }

    @Override
    public void pauseAllSchedules() throws ApiException {
        schedulerResourceApi.pauseAllSchedules();
    }

    @Override
    public void pauseSchedule(String name) throws ApiException {
        schedulerResourceApi.pauseSchedule(name);
    }

    @Override
    public void requeueAllExecutionRecords() throws ApiException {
        schedulerResourceApi.requeueAllExecutionRecords();
    }

    @Override
    public void resumeAllSchedules() throws ApiException {
        schedulerResourceApi.resumeAllSchedules();
    }

    @Override
    public void resumeSchedule(String name) throws ApiException {
        schedulerResourceApi.resumeSchedule(name);
    }

    @Override
    public void saveSchedule(SaveScheduleRequest saveScheduleRequest) throws ApiException {
        schedulerResourceApi.saveSchedule(saveScheduleRequest);
    }

    @Override
    public SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        return schedulerResourceApi.searchV22(start, size, sort, freeText, query);
    }
    @Override
    public SearchResultWorkflowScheduleExecution search(Integer start, Integer size, String sort, String freeText, String query)
        throws ApiException {
        return schedulerResourceApi.search(start, size, sort, freeText, query);
    }

    @Override
    public void setSchedulerTags(List<TagObject> body, String name) {
        schedulerResourceApi.putTagForSchedule(body, name);
    }

    @Override
    public void deleteSchedulerTags(List<TagObject> body, String name) {
        schedulerResourceApi.deleteTagForSchedule(body, name);
    }

    @Override
    public List<TagObject> getSchedulerTags(String name) {
        return schedulerResourceApi.getTagsForSchedule(name);
    }
}
