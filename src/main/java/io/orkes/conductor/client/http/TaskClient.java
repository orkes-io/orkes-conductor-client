package io.orkes.conductor.client.http;

import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.conductor.common.metadata.tasks.PollData;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.TaskSummary;
import com.netflix.conductor.common.utils.ExternalPayloadStorage;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskClient {
    Task pollTask(String taskType, String workerId, String domain);

    List<Task> batchPollTasksByTaskType(
            String taskType, String workerId, int count, int timeoutInMillisecond);

    List<Task> batchPollTasksInDomain(
            String taskType, String domain, String workerId, int count, int timeoutInMillisecond);

    void updateTask(TaskResult taskResult);

    Optional<String> evaluateAndUploadLargePayload(
            Map<String, Object> taskOutputData, String taskType);

    Boolean ack(String taskId, String workerId);

    void logMessageForTask(String taskId, String logMessage);

    List<TaskExecLog> getTaskLogs(String taskId);

    Task getTaskDetails(String taskId);

    void removeTaskFromQueue(String taskType, String taskId);

    int getQueueSizeForTask(String taskType);

    int getQueueSizeForTask(
            String taskType, String domain, String isolationGroupId, String executionNamespace);

    List<PollData> getPollData(String taskType);

    List<PollData> getAllPollData();

    String requeueAllPendingTasks();

    String requeuePendingTasksByTaskType(String taskType);

    SearchResult<TaskSummary> search(String query);

    SearchResult<Task> searchV2(String query);

    SearchResult<TaskSummary> search(
            Integer start, Integer size, String sort, String freeText, String query);

    SearchResult<Task> searchV2(
            Integer start, Integer size, String sort, String freeText, String query);
}
