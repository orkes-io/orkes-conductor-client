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

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.client.util.ApiUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class SkipTaskTests {

    @Test
    public void testWorkflowSearchPermissions() {

        ApiClient adminClient = ApiUtil.getApiClientWithCredentials();
        WorkflowClient workflowAdminClient = new OrkesWorkflowClient(adminClient);
        MetadataClient metadataAdminClient = new OrkesMetadataClient(adminClient);
        TaskClient taskClient = new OrkesTaskClient(adminClient);
        String workflowName = "skip_test_test_workflow";
        SearchResult<WorkflowSummary> searchResult = workflowAdminClient.search("workflowType IN (" + workflowName + ") AND status IN (RUNNING)");

        // Terminate all the workflows
        searchResult.getResults().forEach(workflowSummary -> workflowAdminClient.terminateWorkflow(workflowSummary.getWorkflowId(), "test"));

        registerForkJoinWorkflowDef(workflowName, metadataAdminClient);

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        String workflowId = workflowAdminClient.startWorkflow(startWorkflowRequest);

        // Assert that  first task is scheduled.
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow2 = workflowAdminClient.getWorkflow(workflowId, true);
            assertEquals(Workflow.WorkflowStatus.RUNNING, workflow2.getStatus());
            assertEquals(1, workflow2.getTasks().size());
        });

        // Skip fork tasks.
        workflowAdminClient.skipTaskFromWorkflow(workflowId, "fork_task");

        // Assert that fork tasks are skipped along with join
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow2 = workflowAdminClient.getWorkflow(workflowId, true);
            assertEquals(Workflow.WorkflowStatus.RUNNING, workflow2.getStatus());
            assertEquals(5, workflow2.getTasks().size());
        });

        // Marking first task complete should complete the workflow.
        Workflow workflow = workflowAdminClient.getWorkflow(workflowId, true);
        Task task = workflow.getTasks().stream().filter(task1 -> "simple_task_1".equals(task1.getTaskType())).collect(Collectors.toList()).get(0);
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskId(task.getTaskId());
        taskResult.setWorkflowInstanceId(workflowId);
        taskResult.setStatus(TaskResult.Status.COMPLETED);

        taskClient.updateTask(taskResult);
        // Assert that fork tasks are skipped along with join
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow2 = workflowAdminClient.getWorkflow(workflowId, false);
            assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow2.getStatus());
        });

    }
    private void registerForkJoinWorkflowDef(String workflowName, MetadataClient metadataClient) {

        String fork_task = "fork_task";
        String fork_task1 = "fork_task_1";
        String fork_task2 = "fork_task_2";
        String join_task = "join_task";
        String simple_task_1 = "simple_task_1";

        WorkflowTask simple_task = new WorkflowTask();
        simple_task.setTaskReferenceName(simple_task_1);
        simple_task.setName(simple_task_1);
        simple_task.setWorkflowTaskType(TaskType.SIMPLE);

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
        workflowDef.setTasks(Arrays.asList(simple_task, fork_workflow_task, join));
        workflowDef.setOwnerEmail("test@orkes.io");
        metadataClient.registerWorkflowDef(workflowDef);
    }
}
