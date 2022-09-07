package io.orkes.conductor.client.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.client.worker.Worker;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.TaskResourceApi;
import io.orkes.conductor.client.http.api.WorkflowResourceApi;
import io.orkes.conductor.client.http.model.StartWorkflowRequest;
import io.orkes.conductor.client.http.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;

public class FunctionalWorker {
    WorkflowResourceApi workflowResourceApi;
    TaskRunnerConfigurer taskRunnerConfigurer;

    @BeforeEach
    public void init() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        workflowResourceApi = new WorkflowResourceApi(apiClient);
        TaskResourceApi taskClient = new TaskResourceApi(apiClient);
        Worker worker = new SimpleWorker();
        this.taskRunnerConfigurer = new TaskRunnerConfigurer.Builder(
                taskClient,
                List.of(worker))
                .withTaskThreadCount(
                        Map.of(Commons.TASK_NAME, 10))
                .build();
    }

    @Test
    @DisplayName("Test workflow completion")
    public void workflow() throws Exception {
        List<String> workflowIds = startWorkflows(
                10,
                Commons.WORKFLOW_NAME);
        workflowIds.add(startWorkflow(Commons.WORKFLOW_NAME));
        this.taskRunnerConfigurer.init();
        Thread.sleep(5 * 1000);
        workflowIds.forEach(
                workflowId -> validateCompletedWorkflow(workflowId));
        this.taskRunnerConfigurer.shutdown();
    }

    String startWorkflow(String workflowName) {
        return workflowResourceApi.startWorkflow(
                new StartWorkflowRequest().name(workflowName));
    }

    List<String> startWorkflows(int quantity, String workflowName) {
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest()
                .name(workflowName);
        List<String> workflowIds = new ArrayList<>();
        for (int i = 0; i < quantity; i += 1) {
            String workflowId = workflowResourceApi.startWorkflow(startWorkflowRequest);
            workflowIds.add(workflowId);
        }
        return workflowIds;
    }

    void validateCompletedWorkflow(String workflowId) {
        WorkflowStatus workflowStatus = workflowResourceApi.getWorkflowStatusSummary(
                workflowId,
                false,
                false);
        assertEquals(WorkflowStatus.StatusEnum.COMPLETED, workflowStatus.getStatus());
    }
}
