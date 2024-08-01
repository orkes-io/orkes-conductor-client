/*
 * Copyright 2023 Orkes, Inc.
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
package io.orkes.conductor.sdk;

import io.orkes.conductor.client.http.OrkesClients;
import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;
import io.orkes.conductor.sdk.workflow.def.tasks.SimpleTask;
import io.orkes.conductor.sdk.workflow.executor.WorkflowExecutor;
import io.orkes.conductor.sdk.workflow.executor.task.AnnotatedWorkerExecutor;
import io.orkes.conductor.sdk.workflow.executor.task.WorkerConfiguration;
import io.orkes.conductor.sdk.workflow.task.InputParam;
import io.orkes.conductor.sdk.workflow.task.WorkerTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class WorkflowSDKTests {

    @Test
    public void testCreateWorkflow() {
        OrkesHttpClient apiClient = ApiUtil.getApiClientWithCredentials();
        OrkesClients clients = new OrkesClients(apiClient);
        AnnotatedWorkerExecutor workerExecutor = new AnnotatedWorkerExecutor(clients.getTaskClient(), new WorkerConfiguration());
        workerExecutor.initWorkers("io.orkes.conductor.sdk");
        workerExecutor.startPolling();

        WorkflowExecutor executor = new WorkflowExecutor(
                clients.getTaskClient(),
                clients.getWorkflowClient(),
                clients.getMetadataClient(),
                workerExecutor);

        ConductorWorkflow<Map> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("sdk_workflow");
        workflow.setVersion(1);
        workflow.add(new SimpleTask("sdk_task", "sdk_task"));

        workflow.registerWorkflow(true, true);

        CompletableFuture<Workflow> result = workflow.execute(Map.of("name", "orkes"));
        Assertions.assertNotNull(result);
        try {
            Workflow executedWorkflow = result.get(3, TimeUnit.SECONDS);
            Assertions.assertNotNull(executedWorkflow);
            Assertions.assertEquals(Workflow.WorkflowStatus.COMPLETED, executedWorkflow.getStatus());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @WorkerTask(value = "sdk_task", pollingInterval = 10)
    public String sdkTask(@InputParam("name") String name) {
        return "Hello " + name;
    }
}
