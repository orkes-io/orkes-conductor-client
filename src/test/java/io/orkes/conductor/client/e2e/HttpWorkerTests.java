package io.orkes.conductor.client.e2e;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.util.ApiUtil;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class HttpWorkerTests {
    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static MetadataClient metadataClient;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
    }

    @After
    public void cleanup() {
        try {
            metadataClient.unregisterWorkflowDef("http_workflow", 1);
            metadataClient.unregisterTaskDef("http_task");
        } catch (Exception e) {}
    }

    @Test
    public void testHttpWorkerWithFailureConditionFailure() {
        registerWorkflowWithSingleHttpTask();

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName("http_workflow");
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(Map.of(
                "value", 33,
                "failureCondition", "function e() {return $.statusCode / 100 === 2} e();"
        ));

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow1.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow1.getTasks().get(0);
        assertEquals(Task.Status.FAILED.name(), task.getStatus().name());
    }

    @Test
    public void testHttpWorkerWithFailureConditionSuccess() {
        registerWorkflowWithSingleHttpTask();

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName("http_workflow");
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(Map.of(
                "value", 33,
                "failureCondition", "function e() {return $.statusCode / 100 !== 2} e();"
        ));

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow1.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow1.getTasks().get(0);
        assertEquals(Task.Status.COMPLETED.name(), task.getStatus().name());
    }

    @Test
    public void testHttpWorkerWithFailureConditionUsingWorkflowInput() {
        registerWorkflowWithSingleHttpTask();

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName("http_workflow");
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(Map.of(
                "value", 33,
                "failureCondition", "function e() {return !($.value === 33)} e();"
        ));

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow1.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow1.getTasks().get(0);
        assertEquals(Task.Status.COMPLETED.name(), task.getStatus().name());
    }

    @Test
    public void testHttpWorkerWithFailureConditionUsingAnotherTaskInput() {
        registerWorkflowWithMultipleHttpTasks();

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName("http_workflow");
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(Map.of(
                "value", 33,
                "failureCondition", "function e() {return false} e();"
        ));

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow1.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow1 = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow1.getTasks().get(0);
        assertEquals(Task.Status.COMPLETED.name(), task.getStatus().name());

        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow2 = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow2.getTasks().get(1).getStatus().isTerminal());
        });

        Workflow workflow2 = workflowClient.getWorkflow(workflowId, true);
        Task task2 = workflow2.getTasks().get(1);
        assertEquals(Task.Status.COMPLETED.name(), task2.getStatus().name());
    }

    private void registerWorkflowWithMultipleHttpTasks() {
        // fill the values correctly
        Map<String, Object> inputParams = Map.of(
                "http_request", Map.of(
                        "uri", "https://jsonplaceholder.typicode.com/posts",
                        "method", "GET",
                        "failureCondition", "${workflow.input.failureCondition}"
                ),
                "value", "${workflow.input.value}"
        );

        WorkflowTask httpTask = new WorkflowTask();
        httpTask.setName("http_task");
        httpTask.setTaskReferenceName("http_task");
        httpTask.setWorkflowTaskType(TaskType.HTTP);
        httpTask.setInputParameters(inputParams);

        // fill the values correctly
        Map<String, Object> inputParams2 = Map.of(
                "http_request", Map.of(
                        "uri", "https://jsonplaceholder.typicode.com/posts/1",
                        "method", "GET",
                        "failureCondition", "function e() {return !($.custom === 100)} e();"
                ),
                "custom", "${http_task.output.response.body.length()}"
        );

        WorkflowTask httpTask2 = new WorkflowTask();
        httpTask2.setName("http_task2");
        httpTask2.setTaskReferenceName("http_task2");
        httpTask2.setWorkflowTaskType(TaskType.HTTP);
        httpTask2.setInputParameters(inputParams2);

        registerWorkflowWithTasks(Arrays.asList(httpTask, httpTask2));
    }
    private void registerWorkflowWithSingleHttpTask() {
        // fill the values correctly
        Map<String, Object> inputParams = Map.of(
                "http_request", Map.of(
                        "uri", "https://jsonplaceholder.typicode.com/posts/1",
                        "method", "GET",
                        "failureCondition", "${workflow.input.failureCondition}"
                ),
                "value", "${workflow.input.value}"
        );

        WorkflowTask httpTask = new WorkflowTask();
        httpTask.setName("http_task");
        httpTask.setTaskReferenceName("http_task");
        httpTask.setWorkflowTaskType(TaskType.HTTP);
        httpTask.setInputParameters(inputParams);

        registerWorkflowWithTasks(Arrays.asList(httpTask));
    }

    private void registerWorkflowWithTasks(List<WorkflowTask> tasks) {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName("http_workflow");
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value"));
        workflowDef.setDescription("Workflow to test http worker");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTasks(tasks);

        metadataClient.registerWorkflowDef(workflowDef);
    }
}
