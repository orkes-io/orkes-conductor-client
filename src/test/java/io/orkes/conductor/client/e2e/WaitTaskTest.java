package io.orkes.conductor.client.e2e;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.def.tasks.Wait;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
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

    @After
    public void cleanup() {
        if(executor != null) {
            executor.shutdown();
        }
    }
}
