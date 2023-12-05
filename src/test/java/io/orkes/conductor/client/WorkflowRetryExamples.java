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
package io.orkes.conductor.client;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class WorkflowRetryExamples {
    private final MetadataClient metadataClient;
    private final WorkflowClient workflowClient;

    private final TaskClient taskClient;

    static final String taskName = "test_retry_operation";

    static final String workflowName = "Sample_Retry";

    public WorkflowRetryExamples() {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        metadataClient = orkesClients.getMetadataClient();
        workflowClient = orkesClients.getWorkflowClient();
        taskClient = orkesClients.getTaskClient();
    }

    @Test
    @DisplayName("create workflow definition")
    public void createWorkflowDef() {
        registerTask();
        registerWorkflow();
    }

    @Test
    @DisplayName("test retry operation")
    public void testRetry() {
        registerTask();
        registerWorkflow();
        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);
        startWorkflowRequest.setInput(new HashMap<>());
        String workflowId = workflowClient.startWorkflow(startWorkflowRequest);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);

        String taskId = workflow.getTasks().get(0).getTaskId();
        // Fail the task
        failTask(workflow.getWorkflowId(), taskId);
        workflow = workflowClient.getWorkflow(workflowId, true);

        // Upload all the workflows to s3
        workflowClient.uploadCompletedWorkflows();

        workflowClient.terminateWorkflow(workflowId, "testing out some stuff");
        // Retry the workflow

        log.debug("Going to retry " + workflowId);
        workflowClient.retryLastFailedTask(workflowId);
        taskId = workflowClient.getWorkflow(workflowId, true).getTasks().get(0).getTaskId();
        completeTask(workflow.getWorkflowId(), taskId);
        assertEquals(Workflow.WorkflowStatus.RUNNING, workflowClient.getWorkflow(workflowId, false).getStatus());
        workflowClient.terminateWorkflow(workflowId, "test completed");
        assertEquals(Workflow.WorkflowStatus.TERMINATED, workflowClient.getWorkflow(workflowId, false).getStatus());
    }

    private void completeTask(String workflowId, String taskId) {
        TaskResult taskResult = new TaskResult();
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.setTaskId(taskId);
        taskResult.setWorkflowInstanceId(workflowId);
        taskClient.updateTask(taskResult);
    }

    private void failTask(String workflowId, String taskId) {
        TaskResult taskResult = new TaskResult();
        taskResult.setStatus(TaskResult.Status.FAILED);
        taskResult.setReasonForIncompletion("No reason");
        taskResult.setTaskId(taskId);
        taskResult.setWorkflowInstanceId(workflowId);
        taskClient.updateTask(taskResult);
    }

    void registerTask() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(taskName);
        taskDef.setOwnerEmail("test@orkes.com");
        List<TaskDef> taskDefs = new ArrayList<>();
        taskDefs.add(taskDef);
        this.metadataClient.registerTaskDefs(taskDefs);
    }

    void registerWorkflow() {
        WorkflowDef workflowDef = getWorkflowDef();
        metadataClient.registerWorkflowDef(workflowDef);
    }

    static WorkflowDef getWorkflowDef() {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setVersion(1);
        workflowDef.setOwnerEmail(Commons.OWNER_EMAIL);
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName(taskName);
        workflowTask.setTaskReferenceName(taskName);
        workflowDef.setTasks(List.of(workflowTask));
        return workflowDef;
    }

}
