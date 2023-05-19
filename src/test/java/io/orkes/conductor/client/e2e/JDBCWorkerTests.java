package io.orkes.conductor.client.e2e;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.sdk.examples.ApiUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class JDBCWorkerTests {
    private static WorkflowClient workflowClient;
    private static MetadataClient metadataClient;
    private static final String WORKFLOW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final String TASK_NAME = "jdbc_task";
    private static final String TABLE_NAME = RandomStringUtils.randomAlphabetic(5).toLowerCase();

    @BeforeAll
    public static void init() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);

        registerWorkflowDef();
    }

    @AfterAll
    public static void clearResource() {
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + WORKFLOW_NAME + ")");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.deleteWorkflow(workflowSummary.getWorkflowId(), false);
                System.out.println("Going to delete " + workflowSummary.getWorkflowId());
            } catch (Exception e) {
            }
        });
    }

    @BeforeEach
    public void initializeDatabase() {
        createTable();
        insertSeedDataInTable();
    }

    @AfterEach
    public void clearData() {
        dropTable();

        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + WORKFLOW_NAME + ") AND status IN (RUNNING)");
        found.getResults().forEach(workflowSummary -> {
            try {
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId());
            } catch (Exception e) {
            }
        });
    }

    @Test
    public void testJDBCWorkerSelect() {
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", "SELECT * FROM " + TABLE_NAME,
                "parameters", List.of(),
                "type", "SELECT"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.COMPLETED, task.getStatus());
        assertEquals(10, ((List<Object>) task.getOutputData().get("result")).size());
    }

    @Test
    public void testJDBCWorkerUpdate() {
        String query = "UPDATE " + TABLE_NAME +
                "   SET amount = ?" +
                "   WHERE order_status = ?";
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", query,
                "parameters", List.of("100", "Shipped"),
                "type", "UPDATE"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.COMPLETED, task.getStatus());
        assertEquals(5, (Long) task.getOutputData().get("update_count"));
    }

    @Test
    public void testJDBCWorkerUpdateWithExpectedCountSuccess() {
        String query = "UPDATE " + TABLE_NAME +
                "   SET amount = ?" +
                "   WHERE order_status = ?";
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", query,
                "parameters", List.of("100", "Pending"),
                "type", "UPDATE",
                "expectedUpdateCount", "3"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.COMPLETED, task.getStatus());
        assertEquals(3, (Long) task.getOutputData().get("update_count"));
    }

    @Test
    public void testJDBCWorkerUpdateWithExpectedCountFailure() {
        String query = "UPDATE " + TABLE_NAME +
                "   SET amount = ?" +
                "   WHERE order_status = ?";
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", query,
                "parameters", List.of("100", "Pending"),
                "type", "UPDATE",
                "expectedUpdateCount", "2"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.FAILED, task.getStatus());
        assertEquals("Update count 3 does not match with expected update count 2", task.getReasonForIncompletion());
        assertEquals(3, (Long) task.getOutputData().get("update_count"));
    }

    @Test
    public void testJDBCWorkerWithSqlError() {
        String query = "selc * from " + TABLE_NAME;
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", query,
                "parameters", List.of(),
                "type", "UPDATE",
                "expectedUpdateCount", "2"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.FAILED, task.getStatus());
    }

    @Test
    public void testJDBCWorkerWithMissingConnectionId() {
        String query = "UPDATE " + TABLE_NAME +
                "   SET order_status = ?" +
                "   WHERE order_status = ?";
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres",
                "statement", query,
                "parameters", List.of("Shipped", "Pending"),
                "type", "UPDATE",
                "expectedUpdateCount", "2"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);

        await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            assertTrue(workflow.getTasks().get(0).getStatus().isTerminal());
        });

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().get(0);

        assertEquals(Task.Status.FAILED, task.getStatus());
        assertEquals("No such datasource configured by connectionId postgres", task.getReasonForIncompletion());
    }

    private static void registerWorkflowDef() {
        try {
            if (metadataClient.getTaskDef(TASK_NAME) != null) {
            }
        } catch (Exception exception) {
            System.out.println("register task def exception: " + exception);
            TaskDef taskDef = new TaskDef();
            taskDef.setName(TASK_NAME);
            taskDef.setOwnerEmail("test@orkes.io");

            metadataClient.registerTaskDefs(List.of(taskDef));
        }

        try {
            Map<String, Object> inputMap = Map.of(
                    "connectionId", "${workflow.input.connectionId}",
                    "statement", "${workflow.input.statement}",
                    "parameters", "${workflow.input.parameters}",
                    "type", "${workflow.input.type}",
                    "expectedUpdateCount", "${workflow.input.expectedUpdateCount}"
            );

            WorkflowTask workflowTask = new WorkflowTask();
            workflowTask.setName(TASK_NAME);
            workflowTask.setTaskReferenceName(TASK_NAME);
            workflowTask.setType("JDBC");
            workflowTask.setInputParameters(inputMap);

            WorkflowDef workflowDef = new WorkflowDef();
            workflowDef.setName(WORKFLOW_NAME);
            workflowDef.setOwnerEmail("tset@orkes.io");
            workflowDef.setTasks(List.of(workflowTask));

            metadataClient.registerWorkflowDef(workflowDef);
        } catch (Exception exception) {
            System.out.println("register workflow def exception: " + exception);
        }
    }

    private static void runWorkflowWithQuery(String query) {
        Map<String, Object> inputMap = Map.of(
                "connectionId", "postgres1",
                "statement", query,
                "parameters", List.of(),
                "type", "UPDATE"
        );

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(WORKFLOW_NAME);
        startWorkflowRequest.setInput(inputMap);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        await().atMost(30, TimeUnit.SECONDS).until(() -> {
            Workflow workflow = workflowClient.getWorkflow(workflowId, true);
            return workflow.getTasks().get(0).getStatus().isTerminal();
        });
    }

    private static void createTable() {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "    customer_id bigint," +
                "    order_status varchar," +
                "    order_city varchar," +
                "    amount varchar" +
                ")";

        runWorkflowWithQuery(createTableQuery);
    }

    private static void dropTable() {
        final String dropTableQuery = "DROP TABLE " + TABLE_NAME;

        runWorkflowWithQuery(dropTableQuery);
    }

    private static void insertSeedDataInTable() {
        final String insertQuerySql = "INSERT INTO " +
                TABLE_NAME +
                "  (customer_id, order_city, order_status)" +
                "  VALUES" +
                "  (1, 'New York', 'Shipped')," +
                "  (2, 'Los Angeles', 'Pending')," +
                "  (3, 'Chicago', 'Cancelled')," +
                "  (4, 'Houston', 'Shipped')," +
                "  (5, 'Philadelphia', 'Pending')," +
                "  (6, 'Phoenix', 'Shipped')," +
                "  (7, 'San Antonio', 'Cancelled')," +
                "  (8, 'San Diego', 'Shipped')," +
                "  (9, 'Dallas', 'Pending')," +
                "  (10, 'San Jose', 'Shipped')";

        runWorkflowWithQuery(insertQuerySql);
    }
}
