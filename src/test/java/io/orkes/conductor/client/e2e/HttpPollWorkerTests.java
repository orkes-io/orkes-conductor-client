package io.orkes.conductor.client.e2e;

import com.google.common.util.concurrent.Uninterruptibles;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.util.ApiUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class HttpPollWorkerTests {
    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static MetadataClient metadataClient;
    static TaskClient taskClient;

    private final String WORKFLOW_NAME = "http_poll_wf";

    @BeforeAll
    public static void init() throws Exception {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }

    @AfterEach
    public void cleanup() {
        try {
            metadataClient.unregisterWorkflowDef(WORKFLOW_NAME, 1);
            workflowClient.deleteWorkflow(WORKFLOW_NAME, false);
        } catch (Exception e) {
        }
    }

    @Test
    public void testHttpPollTask() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "1", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(Map.of(
                "value", "value"
        ));

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(15, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow1.getTasks().get(0);
        assertEquals(Task.Status.COMPLETED.name(), task.getStatus().name());
    }

    @Test
    public void testHttpPollTaskSucceedBasedOnTerminalCondition() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "$.output.status === 200", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertEquals(Task.Status.COMPLETED, workflow.getTasks().get(0).getStatus());
        });
    }

    @Test
    public void testHttpPollTaskFailedBasedOnFixedStrategy() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "$.output.status !== 200", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(1, task.getPollCount());
            assertEquals(60, task.getCallbackAfterSeconds());
        });

        Uninterruptibles.sleepUninterruptibly(60, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(2, task.getPollCount());
            assertEquals(60, task.getCallbackAfterSeconds());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        String taskId = workflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("failed");
        taskClient.updateTask(taskResult);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(Task.Status.FAILED, workflow1.getTasks().get(0).getStatus());
        });
    }

    @Test
    public void testHttpPollTaskFailedBasedOnLinearBackoffStrategy() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "$.output.status !== 200", "LINEAR_BACKOFF");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(1, task.getPollCount());
            assertEquals(60, task.getCallbackAfterSeconds());
        });

        Uninterruptibles.sleepUninterruptibly(60, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(2, task.getPollCount());
            assertEquals(120, task.getCallbackAfterSeconds());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        String taskId = workflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("failed");
        taskClient.updateTask(taskResult);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(Task.Status.FAILED, workflow1.getTasks().get(0).getStatus());
        });
    }

    @Test
    public void testHttpPollTaskFailedBasedOnExponentialBackoffStrategy() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "$.output.status !== 200", "EXPONENTIAL_BACKOFF");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(1, task.getPollCount());
            assertEquals(120, task.getCallbackAfterSeconds());
        });

        Uninterruptibles.sleepUninterruptibly(120, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(2, task.getPollCount());
            assertEquals(240, task.getCallbackAfterSeconds());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        String taskId = workflow.getTasks().get(0).getTaskId();
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(taskId);
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("failed");
        taskClient.updateTask(taskResult);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertEquals(Task.Status.FAILED, workflow1.getTasks().get(0).getStatus());
        });
    }

    @Test
    public void testHttpPollTaskFailedWhenErrorEvaluatingTerminalCondition() {
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "undefinedVariable;", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(Task.Status.FAILED, task.getStatus());
            assertTrue(task.getReasonForIncompletion().contains("Failed to invoke"));
        });
    }

    @Test
    public void testHttpPollTaskFailedWhenTerminalConditionEvaluatesNegativeOne() {
        // when terminal condition evaluates -1, the task is marked as failed
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "-1", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(Task.Status.FAILED, task.getStatus());
        });
    }

    @Test
    public void testHttpPollTaskFailedWhenTerminalConditionEvaluatesZero() {
        // when terminal condition evaluates 0, it schedules for next poll
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "0", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(1, task.getPollCount());
            assertEquals(60, task.getCallbackAfterSeconds());
        });

        Uninterruptibles.sleepUninterruptibly(60, TimeUnit.SECONDS);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(2, task.getPollCount());
            assertEquals(60, task.getCallbackAfterSeconds());
        });
    }

    @Test
    public void testHttpPollTaskFailedWhenTerminalConditionEvaluatesPositiveOne() {
        // when terminal condition evaluates 1, the task is marked as completed
        String url = "https://orkes-api-tester.orkesconductor.com/api";
        registerWorkflowWithHttpPollTask(url, "1", "FIXED");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            Task task = workflow.getTasks().get(0);
            assertEquals(Task.Status.COMPLETED, task.getStatus());
        });
    }

    private void registerWorkflowWithHttpPollTask(String url, String terminationCondition, String pollingStrategy) {
        String taskName = "poll_task_" + System.currentTimeMillis();
        TaskDef taskDef = new TaskDef();
        taskDef.setName(taskName);
        taskDef.setOwnerEmail("test@orkes.io");
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));

        Map<String, Object> inputParams = Map.of(
                "http_request", Map.of(
                        "uri", url,
                        "method", "GET",
                        "connectionTimeOut", 3000,
                        "readTimeOut", 3000,
                        "accept", "application/json",
                        "contentType", "application/json",
                        "terminationCondition", terminationCondition,
                        "pollingInterval", 60,
                        "pollingStrategy", pollingStrategy,
                        "maxPollCount", 2
                ),
                "value", "${workflow.input.value}"
        );

        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName(taskName);
        workflowTask.setTaskReferenceName(taskName);
        workflowTask.setType("HTTP_POLL");
        workflowTask.setInputParameters(inputParams);

        registerTaskWithWorkflow(Arrays.asList(workflowTask));
    }

    private void registerTaskWithWorkflow(List<WorkflowTask> tasks) {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(WORKFLOW_NAME);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value"));
        workflowDef.setDescription("test workflow for http poll task");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTasks(tasks);

        metadataClient.registerWorkflowDef(workflowDef);
    }
}


