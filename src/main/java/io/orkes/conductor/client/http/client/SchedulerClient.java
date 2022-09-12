package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.SaveScheduleRequest;
import io.orkes.conductor.client.http.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.http.model.WorkflowSchedule;

import java.util.List;
import java.util.Map;

public interface SchedulerClient {
    void deleteSchedule(String name) throws ApiException;

    List<WorkflowSchedule> getAllSchedules(String workflowName) throws ApiException;

    List<Long> getNextFewSchedules(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
            throws ApiException;

    WorkflowSchedule getSchedule(String name) throws ApiException;

    Map<String, Object> pauseAllSchedules() throws ApiException;

    void pauseSchedule(String name) throws ApiException;

    Map<String, Object> requeueAllExecutionRecords() throws ApiException;

    Map<String, Object> resumeAllSchedules() throws ApiException;

    void resumeSchedule(String name) throws ApiException;

    void saveSchedule(SaveScheduleRequest body) throws ApiException;

    SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query)
            throws ApiException;

}
