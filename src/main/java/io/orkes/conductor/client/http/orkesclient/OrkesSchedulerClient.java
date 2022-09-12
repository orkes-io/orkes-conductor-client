package io.orkes.conductor.client.http.orkesclient;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.api.SchedulerResourceApi;
import io.orkes.conductor.client.http.client.SchedulerClient;
import io.orkes.conductor.client.http.model.SaveScheduleRequest;
import io.orkes.conductor.client.http.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.http.model.WorkflowSchedule;

import java.util.List;
import java.util.Map;

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
    public List<Long> getNextFewSchedules(String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit) throws ApiException {
        return schedulerResourceApi.getNextFewSchedules(cronExpression, scheduleStartTime, scheduleEndTime, limit);
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
    public void saveSchedule(SaveScheduleRequest body) throws ApiException {
        schedulerResourceApi.saveSchedule(body);
    }

    @Override
    public SearchResultWorkflowScheduleExecutionModel searchV22(Integer start, Integer size, String sort, String freeText, String query) throws ApiException {
        return schedulerResourceApi.searchV22(start, size, sort, freeText, query);
    }

}
