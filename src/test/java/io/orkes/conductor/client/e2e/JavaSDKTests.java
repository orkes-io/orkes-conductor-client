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

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.sdk.workflow.def.tasks.*;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.*;

public class JavaSDKTests {

    private WorkflowExecutor executor;

    private static ApiClient apiClient;
    private static TaskClient taskClient;
    private static WorkflowClient workflowClient;
    private static MetadataClient metadataClient;

    @BeforeAll
    public static void init() {
        apiClient = ApiUtil.getApiClientWithCredentials();
        taskClient = new OrkesClients(apiClient).getTaskClient();
        workflowClient = new OrkesClients(apiClient).getWorkflowClient();
        metadataClient = new OrkesClients(apiClient).getMetadataClient();
    }

    @Test
    public void hello() {
        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("sdk_integration_test");
        workflow.setVersion(1);
        workflow.setVariables(new HashMap<>());

    }

    @Test
    public void testSDK() throws ExecutionException, InterruptedException, TimeoutException {
        executor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 1000);
        executor.initWorkers("io.orkes.conductor.client.e2e");

        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("sdk_integration_test");
        workflow.setVersion(1);
        workflow.setOwnerEmail("test@orkes.io");
        workflow.setVariables(new HashMap<>());
        workflow.add(new SimpleTask("task1", "task1").input("name", "orkes"));

        CompletableFuture<Workflow> future = workflow.executeDynamic(new HashMap<>());
        assertNotNull(future);
        Workflow run = future.get(20, TimeUnit.SECONDS);
        assertNotNull(run);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertEquals(1, run.getTasks().size());
        assertEquals("Hello, orkes", run.getTasks().get(0).getOutputData().get("greetings"));
    }

    @Test
    public void testKitchenSinkWorkflow0()
            throws ExecutionException, InterruptedException, TimeoutException {
        ConductorWorkflow<Map<String, Object>> workflow = createWorkflow(0);

        CompletableFuture<Workflow> future = workflow.executeDynamic(Map.of(
                "task2Name", "task_5",
                "mod", 2,
                "oddEven", 5
        ));

        assertNotNull(future);

        Workflow run = future.get(60, TimeUnit.SECONDS);
        assertNotNull(run);

        for (com.netflix.conductor.common.metadata.tasks.Task task : run.getTasks()) {
            assertEquals(com.netflix.conductor.common.metadata.tasks.Task.Status.COMPLETED, task.getStatus());
        }

        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertNull(run.getReasonForIncompletion());
        assertEquals(8, run.getTasks().size());
    }

    @Test
    public void testKitchenSinkWorkflow1()
            throws ExecutionException, InterruptedException, TimeoutException {
        ConductorWorkflow<Map<String, Object>> workflow = createWorkflow(1);

        CompletableFuture<Workflow> future = workflow.executeDynamic(Map.of(
                "task2Name", "task_5",
                "mod", 2,
                "oddEven", 5
        ));

        assertNotNull(future);

        Workflow run = future.get(60, TimeUnit.SECONDS);
        assertNotNull(run);

        for (com.netflix.conductor.common.metadata.tasks.Task task : run.getTasks()) {
            assertEquals(com.netflix.conductor.common.metadata.tasks.Task.Status.COMPLETED, task.getStatus());
        }

        assertEquals(Workflow.WorkflowStatus.COMPLETED, run.getStatus());
        assertNull(run.getReasonForIncompletion());
        assertEquals(10, run.getTasks().size());
    }

    @After
    public void cleanup() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    @WorkerTask("task_1_1")
    public Map<String, Object> task_1_1() {
        return Map.of(
                "mod", 5,
                "oddEven", 0
        );
    }

    @WorkerTask("task_4")
    public Map<String, Object> task_4(
            @InputParam("mod") String mod,
            @InputParam("oddEven") String oddEven
    ) {
        return Map.of(
                "mod", 5,
                "taskToExecute", "dynamic_task",
                "oddEven", 0,
                "dynamicTasks", List.of(
                        Map.of(
                                "name", "task_1",
                                "taskReferenceName", "task_1_1",
                                "type", "SIMPLE"
                        ),
                        Map.of(
                                "name", "wf_dyn",
                                "taskReferenceName", "wf_dyn",
                                "type", "SUB_WORKFLOW",
                                "subWorkflowParam", Map.of(
                                        "name", "sub_flow_1"
                                )
                        )
                ),
                "dynamicTasksInput", Map.of(
                        "task_1_1", Map.of(),
                        "wf_dyn", Map.of()
                )
        );
    }

    @WorkerTask("task_1")
    public Map<String, Object> task_1(
            @InputParam("mod") String mod,
            @InputParam("oddEven") String oddEven
    ) {
        return Map.of(
                "mod", 5,
                "taskToExecute", "dynamic_task",
                "oddEven", 1,
                "dynamicTasks", List.of(
                        Map.of(
                                "name", "task_1",
                                "taskReferenceName", "task_1_1",
                                "type", "SIMPLE"
                        ),
                        Map.of(
                                "name", "sub_workflow_4",
                                "taskReferenceName", "wf_dyn",
                                "type", "SUB_WORKFLOW",
                                "subWorkflowParam", Map.of(
                                        "name", "sub_flow_1"
                                )
                        )
                ),
                "inputs", Map.of(
                        "task_1_1", Map.of(),
                        "wf_dyn", Map.of()
                )
        );
    }

    @WorkerTask("dynamic_task")
    public Map<String, Object> dynamicTask(
            @InputParam("flow") int flow
    ) {
        int oddEven = flow;
        return Map.of(
                "mod", 5,
                "oddEven", oddEven
        );
    }

    @WorkerTask("sum_numbers")
    public BigDecimal sum(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }

    private void registerDefinitions() {
        TaskDef task1TaskDef = new TaskDef();
        task1TaskDef.setOwnerEmail("test@orkes.io");
        task1TaskDef.setName("dynamic_task");

        TaskDef task1_1TaskDef = new TaskDef();
        task1_1TaskDef.setOwnerEmail("test@orkes.io");
        task1_1TaskDef.setName("task_1_1");
        metadataClient.registerTaskDefs(List.of(task1TaskDef, task1_1TaskDef));

        WorkflowTask subWorkflowTask = new WorkflowTask();
        subWorkflowTask.setWorkflowTaskType(TaskType.SIMPLE);
        subWorkflowTask.setName("task_1");
        subWorkflowTask.setTaskReferenceName("task_1");

        WorkflowDef subWorkflowDef = new WorkflowDef();
        subWorkflowDef.setName("sub_flow_1");
        subWorkflowDef.setOwnerEmail("test@orkes.io");
        subWorkflowDef.setDescription("sub flow 1");
        subWorkflowDef.setTasks(List.of(subWorkflowTask));
        metadataClient.registerWorkflowDef(subWorkflowDef);
    }

    private ConductorWorkflow<Map<String, Object>> createWorkflow(int flow) {
        registerDefinitions();

        executor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 1000);
        executor.initWorkers("io.orkes.conductor.client.e2e");

        ConductorWorkflow<Map<String, Object>> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("kitchensink");
        workflow.setVersion(1);
        workflow.setOwnerEmail("test@orkes.io");
        workflow.setVariables(new HashMap<>());

        workflow.add(
                new SimpleTask("task_1", "task_1")
                        .input(
                                Map.of(
                                        "mod", "${workflow.input.mod}",
                                        "oddEven", "${workflow.input.oddEven}"
                                )
                        )
        );

        workflow.add(
                new Dynamic("task_2", "taskToExecute")
                        .input(Map.of(
                                "taskToExecute", "${task_1.output.taskToExecute}",
                                "flow", flow
                        ))
        );

        workflow.add(
                new Switch("oddEvenDecision", "${task_2.output.oddEven}")
                        .input(Map.of("switchCaseValue", "${task_2.output.oddEven}"))
                        .switchCase(
                                "0",
                                new SimpleTask("task_4", "task_4")
                                        .input(
                                                Map.of(
                                                        "mod", "${task_2.output.mod}",
                                                        "oddEven", "${task_2.output.oddEven}"
                                                )
                                        ),
                                new DynamicFork("fanout1", "forkedTasks", "forkedTasksInputs")
                                        .input(
                                                Map.of(
                                                        "forkedTasks", "${task_4.output.dynamicTasks}",
                                                        "forkedTasksInputs", "${task_4.output.dynamicTasksInput}"
                                                )
                                        )
                        )
                        .switchCase(
                                "1",
                                new ForkJoin(
                                        "forkx",
                                        new Task[]{
                                                new SimpleTask("task_1", "task_10"),
                                                new SubWorkflow("wf3", "sub_flow_1", 1)
                                                        .input(
                                                        Map.of(
                                                                "mod", "${task_1.output.mod}",
                                                                "oddEven", "${task_1.output.oddEven}"
                                                        )
                                                )
                                        },
                                        new Task[]{
                                                new SimpleTask("task_1", "task_11"),
                                                new SubWorkflow("wf4", "sub_flow_1", 1)
                                                        .input(
                                                        Map.of(
                                                                "mod", "${task_1.output.mod}",
                                                                "oddEven", "${task_1.output.oddEven}"
                                                        )
                                                )
                                        }
                                ),
                                new Join("join2", "wf3", "wf4")
                        )
        );

        return workflow;
    }
}
