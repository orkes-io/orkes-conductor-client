/*
 * Copyright 2024 Orkes, Inc.
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
package io.orkes.conductor.client.ex;

import java.time.Duration;
import java.util.Map;

import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.metadata.tasks.TaskResult;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.run.SearchResult;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.client.model.run.WorkflowSummary;
import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;
import io.orkes.conductor.sdk.workflow.def.tasks.Http;
import io.orkes.conductor.sdk.workflow.def.tasks.Wait;
import io.orkes.conductor.sdk.workflow.executor.WorkflowExecutor;

public class WorkflowManager {

    private final OrkesClients orkesClients;
    private final WorkflowClient workflowClient;
    private final TaskClient taskClient;

    public WorkflowManager() {
        var apiClient = new OrkesHttpClient.Builder()
                .basePath("http://localhost:8080/api")
                .keyId("api_key_user_03")
                .keySecret("api_key_user_03")
                .build();

        this.orkesClients = new OrkesClients(apiClient);
        this.workflowClient = orkesClients.getWorkflowClient();
        this.taskClient = orkesClients.getTaskClient();
    }

    public String startWorkflow(WorkflowExecutor workflowExecutor) {
        ConductorWorkflow<?> workflow = new ConductorWorkflow<>(workflowExecutor);
        workflow.setName("workflow_signals_demo");
        workflow.setVersion(1);
        Wait waitForTwoSec = new Wait("wait_for_2_sec", Duration.ofSeconds(2));
        Http httpCall = new Http("call_remote_api");
        httpCall.url("https://orkes-api-tester.orkesconductor.com/api");

        Wait waitForSignal = new Wait("wait_for_signal");

        workflow.add(waitForTwoSec);
        workflow.add(waitForSignal);
        workflow.add(httpCall);

        workflow.registerWorkflow(true);
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setVersion(1);
        request.setName(workflow.getName());
        request.setInput(Map.of());

        return workflowClient.startWorkflow(request);
    }

    public void main() {
        WorkflowExecutor workflowExecutor = orkesClients.getWorkflowExecutor();
        String workflowId = startWorkflow(workflowExecutor);
        System.out.println("Started workflow with id " + workflowId);

        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        Task lastTask = workflow.getTasks().get(workflow.getTasks().size() - 1);
        System.out.println("Workflow status is " + workflow.getStatus() + " and currently running task is " + lastTask.getReferenceTaskName());

        workflowClient.terminateWorkflow(workflowId, "testing termination");

        // Other operations like retry, update tasks, etc.

        // Example of task completion
        TaskResult taskResult = new TaskResult();
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setTaskId(lastTask.getTaskId());
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setOutputData(Map.of("greetings", "hello from Orkes"));
        taskClient.updateTask(taskResult);

        // Handling workflow lifecycle: terminate, restart, pause, resume
        workflowClient.terminateWorkflow(workflowId, "terminating so we can do a restart");
        workflowClient.restart(workflowId, true);
        workflowClient.pauseWorkflow(workflowId);

        try {
            Thread.sleep(3000); // Simulating a wait
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        workflowClient.resumeWorkflow(workflowId);

        // Search workflow examples
        SearchResult<WorkflowSummary> searchResults = workflowClient.search(0, 100, "", "*", "correlationId = 'correlation_123'");
        System.out.println("Found " + searchResults.getTotalHits() + " executions with correlation_id 'correlation_123'");
    }

    public static void main(String[] args) {
        new WorkflowManager().main();
    }
}
