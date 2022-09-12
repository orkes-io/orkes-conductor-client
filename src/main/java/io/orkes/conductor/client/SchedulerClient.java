package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.SaveScheduleRequest;
import io.orkes.conductor.client.http.model.SearchResultWorkflowScheduleExecutionModel;
import io.orkes.conductor.client.http.model.WorkflowSchedule;

import java.util.List;
import java.util.Map;

public interface SchedulerClient {
    void deleteSchedule(String name);

    List<WorkflowSchedule> getAllSchedules(String workflowName);

    List<Long> getNextFewSchedules(
            String cronExpression, Long scheduleStartTime, Long scheduleEndTime, Integer limit)
           ;

    WorkflowSchedule getSchedule(String name);

    void pauseAllSchedules();

    void pauseSchedule(String name);

    void requeueAllExecutionRecords();

    void resumeAllSchedules();

    void resumeSchedule(String name);

    void saveSchedule(SaveScheduleRequest body);

    SearchResultWorkflowScheduleExecutionModel searchV22(
            Integer start, Integer size, String sort, String freeText, String query)
           ;

}
