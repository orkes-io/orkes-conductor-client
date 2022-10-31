package io.orkes.conductor.client.sdk;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.util.ApiUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class JavaSDKTests {

    @Test
    public void testSDK() throws ExecutionException, InterruptedException, TimeoutException {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        TaskClient taskClient = new OrkesClients(apiClient).getTaskClient();
        WorkflowClient workflowClient = new OrkesClients(apiClient).getWorkflowClient();
        MetadataClient metadataClient = new OrkesClients(apiClient).getMetadataClient();
        WorkflowExecutor executor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 10);
        executor.initWorkers("io.orkes.conductor.client.sdk");

        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("sdk_integration_test");
        workflow.setVersion(1);
        workflow.setVariables(new HashMap<>());
        workflow.add(new SimpleTask("task1", "task1").input("name", "orkes"));
        CompletableFuture<Workflow> future = workflow.executeDynamic(new HashMap<>());
        assertNotNull(future);
        Workflow run = future.get(2, TimeUnit.SECONDS);
        assertNotNull(run);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertEquals(1, run.getTasks().size());
        assertEquals("Hello, orkes", run.getTasks().get(0).getOutputData().get("greetings"));
    }

}
