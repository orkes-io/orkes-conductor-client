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

import java.util.List;

import io.orkes.conductor.client.SchedulerClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.SaveScheduleRequest;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecution;
import io.orkes.conductor.client.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.WorkflowSchedule;

public class OrkesSchedulerClient extends OrkesClient implements SchedulerClient {

    private final SchedulerResource schedulerResource;

    public OrkesSchedulerClient(OrkesHttpClient apiClient) {
        super(apiClient);
        this.schedulerResource = new SchedulerResource(apiClient);
    }

    @Override
    public void deleteSchedule(String name) throws ApiException {
        schedulerResource.deleteSchedule(name);
    }

    @Override
    public List<WorkflowSchedule> getAllSchedules(String workflowName) throws ApiException {
        return schedulerResource.getAllSchedules(workflowName);
    }

    @Override
    public List<Long> getNextFewSchedules(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
            throws ApiException {
        return schedulerResource.getNextFewSchedules(
                cronExpression, scheduleStartTime, scheduleEndTime, limit);
    }

    @Override
    public WorkflowSchedule getSchedule(String name) throws ApiException {
        return schedulerResource.getSchedule(name);
    }

    @Override
    public void pauseAllSchedules() throws ApiException {
        schedulerResource.pauseAllSchedules();
    }

    @Override
    public void pauseSchedule(String name) throws ApiException {
        schedulerResource.pauseSchedule(name);
    }

    @Override
    public void requeueAllExecutionRecords() throws ApiException {
        schedulerResource.requeueAllExecutionRecords();
    }

    @Override
    public void resumeAllSchedules() throws ApiException {
        schedulerResource.resumeAllSchedules();
    }

    @Override
    public void resumeSchedule(String name) throws ApiException {
        schedulerResource.resumeSchedule(name);
    }

    @Override
    public void saveSchedule(SaveScheduleRequest saveScheduleRequest) throws ApiException {
        schedulerResource.saveSchedule(saveScheduleRequest);
    }

    @Override
    public SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException {
        return schedulerResource.searchV22(start, size, sort, freeText, query);
    }
    @Override
    public SearchResultWorkflowScheduleExecution search(Integer start, Integer size, String sort, String freeText, String query)
        throws ApiException {
        return schedulerResource.search(start, size, sort, freeText, query);
    }

    @Override
    public void setSchedulerTags(List<TagObject> body, String name) {
        schedulerResource.putTagForSchedule(body, name);
    }

    @Override
    public void deleteSchedulerTags(List<TagObject> body, String name) {
        schedulerResource.deleteTagForSchedule(body, name);
    }

    @Override
    public List<TagObject> getSchedulerTags(String name) {
        return schedulerResource.getTagsForSchedule(name);
    }
}
