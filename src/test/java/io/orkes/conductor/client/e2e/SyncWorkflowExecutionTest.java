/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.e2e;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.common.model.WorkflowRun;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class SyncWorkflowExecutionTest {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;

    static int threshold = 2000;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        apiClient.setExecutorThreadCount(10);
        workflowClient = new OrkesWorkflowClient(apiClient);
    }

    @Test
    @DisplayName("Check sync workflow is execute within 11 seconds")
    public void testSyncWorkflowExecution() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "load_test_perf_sync_workflow";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null);
        try {
            long start = System.currentTimeMillis();
            WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
            long end = System.currentTimeMillis();
            System.out.println("WorkflowId " + workflowRun.getWorkflowId());
            long timeTaken = end-start;
            assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
        } catch (Exception e) {
            throw new RuntimeException("Workflow " + workflowName + " did not complete in 5 seconds");
        }
    }

    @Test
    @DisplayName("Check sync workflow end with simple task.")
    public void testSyncWorkflowExecution2() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_simple_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "simple_task_rka0w_ref");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowRun.getStatus());
        workflowClient.terminateWorkflow(workflowRun.getWorkflowId(), "Terminated");
    }

    @Test
    @DisplayName("Check sync workflow end with set variable task.")
    public void testSyncWorkflowExecution3() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_set_variable_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "set_variable_task_1fi09_ref");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        long timeTaken = end - start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow end with jq task.")
    public void testSyncWorkflowExecution4() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_jq_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "json_transform_task_jjowa_ref");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow end with sub workflow task.")
    public void testSyncWorkflowExecution5() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_subworkflow_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "http_sync");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow end with failed case")
    public void testSyncWorkflowExecution6() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_failed_case";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "http_fail");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowRun.getStatus());
        workflowClient.terminateWorkflow(workflowRun.getWorkflowId(), "Terminated");
    }

    @Test
    @DisplayName("Check sync workflow end with no poller")
    public void testSyncWorkflowExecution7() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_no_poller";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "simple_task_pia0h_ref");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowRun.getStatus());
        workflowClient.terminateWorkflow(workflowRun.getWorkflowId(), "Terminated");
    }

    @Test
    @DisplayName("Check sync workflow end with set variable task. when wait until task is specified as null")
    public void testSyncWorkflowExecution8() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_set_variable_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null);
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        long timeTaken = end - start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow end with set variable task. when wrong wait until task is specified")
    public void testSyncWorkflowExecution9() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_set_variable_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, "no_such_Task_exist");
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        long timeTaken = end - start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow end with set variable task. The wait for duration is given.")
    public void testSyncWorkflowExecution10() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_set_variable_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null, 1);
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(1, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        long timeTaken = end - start;
        assertTrue(timeTaken < threshold, "Time taken was " + timeTaken);
    }

    @Test
    @DisplayName("Check sync workflow with simple task. The wait for duration is given.")
    public void testSyncWorkflowExecution11() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_simple_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null, 2);
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(3, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        assertTrue(timeTaken < 2500, "Time taken was " + timeTaken);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowRun.getStatus());
        workflowClient.terminateWorkflow(workflowRun.getWorkflowId(), "Terminated");
    }

    @Test
    @DisplayName("Check sync workflow with simple task. The wait for duration is not given.")
    public void testSyncWorkflowExecution12() throws ExecutionException, InterruptedException, TimeoutException {

        String workflowName = "sync_workflow_end_with_simple_task";

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        CompletableFuture<WorkflowRun> completableFuture = workflowClient.executeWorkflow(startWorkflowRequest, null, (Integer)null);
        long start = System.currentTimeMillis();
        WorkflowRun workflowRun = completableFuture.get(11, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        long timeTaken = end-start;
        System.out.println("WorkflowId " + workflowRun.getWorkflowId());
        assertTrue(timeTaken < 11000, "Time taken was " + timeTaken);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowRun.getStatus());
        workflowClient.terminateWorkflow(workflowRun.getWorkflowId(), "Terminated");
    }
}
