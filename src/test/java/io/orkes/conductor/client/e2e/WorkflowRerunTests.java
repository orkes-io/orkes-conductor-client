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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Uninterruptibles;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.*;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.model.WorkflowStatus;
import io.orkes.conductor.client.util.ApiUtil;

import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowDef;
import static io.orkes.conductor.client.util.RegistrationUtil.registerWorkflowWithSubWorkflowDef;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class WorkflowRerunTests {

    static ApiClient apiClient;
    static WorkflowClient workflowClient;
    static TaskClient taskClient;
    static MetadataClient metadataClient;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient  =new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
    }

    @Test
    @DisplayName("Check workflow with simple task and rerun functionality")
    public void testRerunSimpleWorkflow() {

        String workflowName = "re-run-workflow";
        String taskName1 = "re-run-task1";
        String taskName2 = "re-run-task2";
        // Register workflow
        registerWorkflowDef(workflowName, taskName1, taskName2, metadataClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Fail the simple task
        Task task = workflow.getTasks().get(1);
        workflow = completeTask(task, TaskResult.Status.FAILED);
        assertEquals(Workflow.WorkflowStatus.FAILED, workflow.getStatus());


        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(workflowId);
        rerunWorkflowRequest.setReRunFromTaskId(task.getTaskId());
        // Rerun the workflow
        workflowClient.rerunWorkflow(workflowId, rerunWorkflowRequest);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(workflow.getStatus().name(), Workflow.WorkflowStatus.RUNNING.name());
        assertEquals(workflow.getTasks().get(2).getStatus().name(), Task.Status.SCHEDULED.name());

        workflow = completeTask(workflow.getTasks().get(2), TaskResult.Status.COMPLETED);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());

    }

    @Test
    @DisplayName("Check workflow with sub_workflow task and rerun functionality")
    public void testRerunWithSubWorkflow() throws Exception {

        apiClient = ApiUtil.getApiClientWithCredentials();
        workflowClient = new OrkesWorkflowClient(apiClient);
        metadataClient = new OrkesMetadataClient(apiClient);
        taskClient = new OrkesTaskClient(apiClient);
        String workflowName = "workflow-re-run-with-sub-workflow";
        String taskName = "re-run-with-sub-task";
        String subWorkflowName = "workflow-re-run-sub-workflow";

        terminateExistingRunningWorkflows(workflowName);
        terminateExistingRunningWorkflows(subWorkflowName);

        // Register workflow
        registerWorkflowWithSubWorkflowDef(workflowName, subWorkflowName, taskName, metadataClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        System.out.print("Workflow id is " + workflowId);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        // Fail the simple task
        String subworkflowId = workflow.getTasks().get(0).getSubWorkflowId();
        Workflow subWorkflow = workflowClient.getWorkflow(subworkflowId, true);
        Task task = subWorkflow.getTasks().get(0);
        workflow = completeTask(task, TaskResult.Status.FAILED);
        assertEquals(Workflow.WorkflowStatus.FAILED, workflow.getStatus());

        // Rerun the sub workflow.
        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(subworkflowId);
        rerunWorkflowRequest.setReRunFromTaskId(task.getTaskId());
        workflowClient.rerunWorkflow(subworkflowId, rerunWorkflowRequest);
        // Check the workflow status and few other parameters
        subWorkflow = workflowClient.getWorkflow(subworkflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, subWorkflow.getStatus());
        assertEquals(Task.Status.SCHEDULED, subWorkflow.getTasks().get(1).getStatus());

        subWorkflow = completeTask(subWorkflow.getTasks().get(1), TaskResult.Status.COMPLETED);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, subWorkflow.getStatus());

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());

    }

    @Test
    @DisplayName("Check workflow fork join task rerun")
    public void testRerunForkJoinWorkflow() {

        String workflowName = "re-run-fork-workflow";
        // Register workflow
        registerForkJoinWorkflowDef(workflowName, metadataClient);

        terminateExistingRunningWorkflows(workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);

        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(4, workflow.getTasks().size());

        // Complete and Fail the simple task
        workflow = completeTask(workflow.getTasks().get(1), TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        workflow = completeTask(workflow.getTasks().get(2), TaskResult.Status.COMPLETED);

        assertEquals(Workflow.WorkflowStatus.FAILED, workflow.getStatus());

        RerunWorkflowRequest rerunWorkflowRequest = new RerunWorkflowRequest();
        rerunWorkflowRequest.setReRunFromWorkflowId(workflowId);
        rerunWorkflowRequest.setReRunFromTaskId(workflow.getTasks().get(1).getTaskId());

        // Retry the workflow
        workflowClient.rerunWorkflow(workflowId, rerunWorkflowRequest);
        // Check the workflow status and few other parameters
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow.getStatus());
        assertEquals(Task.Status.SCHEDULED, workflow.getTasks().get(4).getStatus());

        workflow.getTasks().stream()
                .filter(task -> task.getWorkflowTask().getType().equals(TaskType.SIMPLE.toString()))
                .filter(simpleTask -> !simpleTask.getStatus().isTerminal())
                .forEach(running -> completeTask(running, TaskResult.Status.COMPLETED));

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());

    }


    @Test
    @DisplayName("Check load workflow re run works fine with failures")
    public void testRunLoadTestPerfCase2() {
        String workflowName = "load_test_perf";
        WorkflowDef workflowDef = metadataClient.getWorkflowDef(workflowName, 1);
        if (workflowDef == null) {
            fail("load_test_perf is not available, please create the workflow");
            return;
        }

        terminateExistingRunningWorkflows(workflowName);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        System.out.println("Started " + workflowId);

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(1, workflow.getTasks().size(), "Workflow task size is " + workflow.getTasks().size());

        Task task = workflow.getTasks().get(workflow.getTasks().size() - 1);
        workflow = completeTask(task, TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);

        RerunWorkflowRequest request = new RerunWorkflowRequest();
        request.setReRunFromTaskId(workflow.getTasks().get(workflow.getTasks().size()-1).getTaskId());
        request.setCorrelationId("correlation_id_x");
        request.setWorkflowInput(Map.of("re_run_request", 1));
        workflowClient.rerunWorkflow(workflowId, request);

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow.getStatus());
        assertEquals(2, workflow.getTasks().size());

        task = workflow.getTasks().get(workflow.getTasks().size() - 1);
        workflow = completeTask(task, TaskResult.Status.COMPLETED);

        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_0")).collect(Collectors.toList()).get(0);
        completeTask(task, TaskResult.Status.COMPLETED);

        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_2")).collect(Collectors.toList()).get(0);
        completeTask(task, TaskResult.Status.COMPLETED);

        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_1")).collect(Collectors.toList()).get(0);
        completeTask(task, TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);

        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.FAILED, workflow.getStatus());

        request.setReRunFromTaskId(task.getTaskId());
        workflowClient.rerunWorkflow(workflowId, request);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow.getStatus());

        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_1")).collect(Collectors.toList()).get(0);
        workflow = completeTask(task, TaskResult.Status.COMPLETED);

        //There is only one running task fail that.
        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_1")).collect(Collectors.toList()).get(0);
        workflow = completeTask(task, TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        assertEquals(Workflow.WorkflowStatus.FAILED, workflow.getStatus());

        List<Task> loopedTasks = workflow.getTasks().stream()
                .filter(t -> t.getStatus().isTerminal() && t.isLoopOverTask() && t.getTaskDefName().equals("x_test_workers_1") && !t.isRetried())
                .collect(Collectors.toList());
        assertEquals(1, loopedTasks.size());
        request.setReRunFromTaskId(loopedTasks.get(0).getTaskId());
        workflowClient.rerunWorkflow(workflowId, request);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflow.getStatus());
        assertEquals(28, workflow.getTasks().size());

        task = workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal() && t.getTaskDefName().equals("x_test_workers_1")).collect(Collectors.toList()).get(0);
        workflow = completeTask(task, TaskResult.Status.COMPLETED);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());
    }

    private void completeWorkflow(String workflowId) {
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        while(!workflow.getStatus().isTerminal()) {
            workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal()).forEach(r -> completeTask(r, TaskResult.Status.COMPLETED));
            workflow = workflowClient.getWorkflow(workflowId, true);
        }
    }

    private Workflow completeTask(Task task, TaskResult.Status status) {
        TaskResult result = new TaskResult(task);
        result.setStatus(status);
        result.getOutputData().put(task.getReferenceTaskName() + "_op", "output");
        taskClient.updateTask(result);
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        return workflowClient.getWorkflow(task.getWorkflowInstanceId(), true);
    }

    private void terminateExistingRunningWorkflows(String workflowName) {
        //clean up first
        SearchResult<WorkflowSummary> found = workflowClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");
        System.out.println("Found " + found.getResults().size() + " running workflows to be cleaned up");
        found.getResults().forEach(workflowSummary -> {
            try {
                System.out.println("Going to terminate " + workflowSummary.getWorkflowId() + " with status " + workflowSummary.getStatus());
                workflowClient.terminateWorkflow(workflowSummary.getWorkflowId(), "terminate");
            } catch(Exception e){}
        });
    }

    private void registerForkJoinWorkflowDef(String workflowName, MetadataClient metadataClient) {

        String fork_task = "fork_task";
        String fork_task1 = "fork_task_1";
        String fork_task2 = "fork_task_2";
        String join_task = "join_task";

        WorkflowTask fork_task_1 = new WorkflowTask();
        fork_task_1.setTaskReferenceName(fork_task1);
        fork_task_1.setName(fork_task1);
        fork_task_1.setWorkflowTaskType(TaskType.SIMPLE);

        WorkflowTask fork_task_2 = new WorkflowTask();
        fork_task_2.setTaskReferenceName(fork_task2);
        fork_task_2.setName(fork_task2);
        fork_task_2.setWorkflowTaskType(TaskType.SIMPLE);


        WorkflowTask fork_workflow_task = new WorkflowTask();
        fork_workflow_task.setTaskReferenceName(fork_task);
        fork_workflow_task.setName(fork_task);
        fork_workflow_task.setWorkflowTaskType(TaskType.FORK_JOIN);
        fork_workflow_task.setForkTasks(List.of(List.of(fork_task_1), List.of(fork_task_2)));

        WorkflowTask join = new WorkflowTask();
        join.setTaskReferenceName(join_task);
        join.setName(join_task);
        join.setWorkflowTaskType(TaskType.JOIN);
        join.setJoinOn(List.of(fork_task1, fork_task2));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test retry");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(fork_workflow_task, join));
        workflowDef.setOwnerEmail("test@orkes.io");
        metadataClient.registerWorkflowDef(workflowDef);
    }
}
