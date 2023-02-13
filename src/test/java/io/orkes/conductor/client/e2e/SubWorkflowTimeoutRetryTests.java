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
package io.orkes.conductor.client.e2e;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Uninterruptibles;
import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.Workflow;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.sdk.examples.ApiUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public class SubWorkflowTimeoutRetryTests {

    private static OrkesClients orkesClients;

    private static AuthorizationClient authClient;

    private static WorkflowClient workflowClient;

    private static TaskClient taskClient;

    private static MetadataClient metadataClient;

    private static ObjectMapper objectMapper = new ObjectMapperProvider().getObjectMapper();

    private static TypeReference<List<WorkflowDef>> WORKFLOW_DEF_LIST = new TypeReference<List<WorkflowDef>>(){};

    private static final String WORKFLOW_NAME = "integration_test_wf_with_sub_wf";

    private static Map<String, String> taskToDomainMap = new HashMap<>();

    private static TaskRunnerConfigurer configurer;

    private static TaskRunnerConfigurer configurerNoDomain;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        orkesClients = ApiUtil.getOrkesClient();
        authClient = orkesClients.getAuthorizationClient();
        workflowClient = orkesClients.getWorkflowClient();
        taskClient = orkesClients.getTaskClient();
        metadataClient = orkesClients.getMetadataClient();
        InputStream resource = SubWorkflowTimeoutRetryTests.class.getResourceAsStream("/metadata/sub_workflow_tests.json");
        List<WorkflowDef> workflowDefs = objectMapper.readValue(new InputStreamReader(resource), WORKFLOW_DEF_LIST);
        metadataClient.updateWorkflowDefs(workflowDefs);
        Set<String> tasks = new HashSet<>();
        for (WorkflowDef workflowDef : workflowDefs) {
            List<WorkflowTask> allTasks = workflowDef.collectTasks();
            tasks.addAll(allTasks.stream()
                    .filter(tt -> !tt.getType().equals("SIMPLE"))
                    .map(t -> t.getType()).collect(Collectors.toSet()));

            tasks.addAll(allTasks.stream()
                    .filter(tt -> tt.getType().equals("SIMPLE"))
                    .map(t -> t.getName()).collect(Collectors.toSet()));

        }
        log.info("Updated workflow definitions: {}", workflowDefs.stream().map(def -> def.getName()).collect(Collectors.toList()));
    }

    @AfterAll
    public static void cleanup() {
        if(configurer != null) {
            configurer.shutdown();
            configurerNoDomain.shutdown();
        }
    }

    @Test
    public void test() {

        String correlationId = "wf_with_subwf_test_1";
        Map<String, Object> input = Map.of("param1", "p1 value", "subwf", "sub_workflow");

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName(WORKFLOW_NAME);
        request.setVersion(1);
        request.setCorrelationId(correlationId);
        request.setInput(input);
        String workflowInstanceId = workflowClient.startWorkflow(request);

        log.info("Started {} ", workflowInstanceId);
        pollAndCompleteTask(workflowInstanceId, "integration_task_1", Map.of());
        Workflow workflow = workflowClient.getWorkflow(workflowInstanceId, true);
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            Workflow workflow1 = workflowClient.getWorkflow(workflowInstanceId, true);
            assertNotNull(workflow1);
            assertEquals(2, workflow1.getTasks().size());
            assertEquals(Task.Status.COMPLETED, workflow1.getTasks().get(0).getStatus());
            assertEquals(TaskType.SUB_WORKFLOW.name(), workflow1.getTasks().get(1).getTaskType());
            assertEquals(Task.Status.IN_PROGRESS, workflow1.getTasks().get(1).getStatus());
        });
        workflow = workflowClient.getWorkflow(workflowInstanceId, true);
        String subWorkflowId = workflow.getTasks().get(1).getSubWorkflowId();
        log.info("Sub workflow Id {} ", subWorkflowId);

        assertNotNull(subWorkflowId);
        Workflow subWorkflow = workflowClient.getWorkflow(subWorkflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, subWorkflow.getStatus());

        //Wait for 7 seconds which is > 5 sec timeout for the workflow
        Uninterruptibles.sleepUninterruptibly(7, TimeUnit.SECONDS);
        workflowClient.runDecider(workflowInstanceId);

        workflow = workflowClient.getWorkflow(workflowInstanceId, true);
        assertNotNull(workflow);
        assertEquals(2, workflow.getTasks().size());
        assertEquals(Workflow.WorkflowStatus.TIMED_OUT, workflow.getStatus());
        assertEquals(Task.Status.COMPLETED, workflow.getTasks().get(0).getStatus());
        assertEquals(Task.Status.CANCELED, workflow.getTasks().get(1).getStatus());

        //Verify that the sub-workflow is terminated
        subWorkflow = workflowClient.getWorkflow(subWorkflowId, true);
        assertEquals(Workflow.WorkflowStatus.TERMINATED, subWorkflow.getStatus());

        //Retry sub-workflow
        workflowClient.retryLastFailedTask(subWorkflowId);

        //Sub workflow should be in the running state now
        subWorkflow = workflowClient.getWorkflow(subWorkflowId, true);
        assertEquals(Workflow.WorkflowStatus.RUNNING, subWorkflow.getStatus());
        assertEquals(Task.Status.CANCELED, subWorkflow.getTasks().get(0).getStatus());
        assertEquals(Task.Status.SCHEDULED, subWorkflow.getTasks().get(1).getStatus());

        workflow = workflowClient.getWorkflow(workflowInstanceId, true);
        assertTrue(!workflow.getTasks().get(1).isSubworkflowChanged());
    }

    private Task pollAndCompleteTask(String workflowInstanceId, String taskName, Map<String, Object> output) {
        Workflow workflow = workflowClient.getWorkflow(workflowInstanceId, true);
        if(workflow == null) {
            return null;
        }
        Optional<Task> optional = workflow.getTasks().stream().filter(task -> task.getTaskDefName().equals(taskName)).findFirst();
        if(optional.isEmpty()) {
            return null;
        }
        Task task = optional.get();
        task.setStatus(Task.Status.COMPLETED);
        task.getOutputData().putAll(output);
        taskClient.updateTask(new TaskResult(task));

        return task;
    }



}
