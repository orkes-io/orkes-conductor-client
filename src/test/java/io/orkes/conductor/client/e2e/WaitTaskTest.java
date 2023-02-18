package io.orkes.conductor.client.e2e;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.def.tasks.Wait;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.util.ApiUtil;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

public class WaitTaskTest {

    private WorkflowExecutor executor;

    @Test
    public void testWaitTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        TaskClient taskClient = new OrkesClients(apiClient).getTaskClient();
        WorkflowClient workflowClient = new OrkesClients(apiClient).getWorkflowClient();
        MetadataClient metadataClient = new OrkesClients(apiClient).getMetadataClient();
        executor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 1000);

        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("wait_task_test");
        workflow.setVersion(1);
        workflow.setVariables(new HashMap<>());
        workflow.add(new Wait("wait_for_2_second", Duration.of(2, SECONDS)));
        CompletableFuture<Workflow> future = workflow.executeDynamic(new HashMap<>());
        assertNotNull(future);
        Workflow run = future.get(60, TimeUnit.SECONDS);
        assertNotNull(run);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertEquals(1, run.getTasks().size());
        long timeToExecute = run.getTasks().get(0).getEndTime() - run.getTasks().get(0).getScheduledTime();

        //Ensure the wait completes within 500ms buffer
        assertTrue(timeToExecute < 2500, "Wait task did not complete in time, took " + timeToExecute + " millis");
    }

    @Test
    public void testWaitTaskInputParameterized() throws ExecutionException, InterruptedException, TimeoutException {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        TaskClient taskClient = new OrkesClients(apiClient).getTaskClient();
        WorkflowClient workflowClient = new OrkesClients(apiClient).getWorkflowClient();
        MetadataClient metadataClient = new OrkesClients(apiClient).getMetadataClient();
        executor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 1000);
        executor.initWorkers(WaitTaskTest.class.getPackageName());

        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("wait_task_test_parameterized");
        workflow.setVersion(1);
        workflow.setVariables(new HashMap<>());
        workflow.add(new Wait("wait_for_some_time").input("duration", "${workflow.input.waitTime}"));
        workflow.add(new SimpleTask("random_num_generator", "random_num_generator"));
        workflow.add(new Wait("wait_for_some_time_again").input("duration", "${random_num_generator.output.result}s"));
        workflow.registerWorkflow(true, true);

        CompletableFuture<Workflow> future = workflow.execute(Map.of("waitTime", "1s"));
        assertNotNull(future);
        Workflow run = future.get(60, TimeUnit.SECONDS);
        assertNotNull(run);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertEquals(3, run.getTasks().size());
        long timeToExecuteWait1 = run.getTasks().get(0).getEndTime() - run.getTasks().get(0).getScheduledTime();
        long timeToExecuteWait2 = run.getTasks().get(2).getEndTime() - run.getTasks().get(2).getScheduledTime();

        //Ensure the wait completes within 500ms buffer
        assertTrue(timeToExecuteWait1 < 2000, "Wait task did not complete in time, took " + timeToExecuteWait1 + " millis");
        assertTrue(timeToExecuteWait2 < 5500, "Wait task did not complete in time, took " + timeToExecuteWait2 + " millis");
    }

    @WorkerTask("random_num_generator")
    public int randomNumberGenerator(){
        return 5;
    }

    @After
    public void cleanup() {
        if(executor != null) {
            executor.shutdown();
        }
    }
}
