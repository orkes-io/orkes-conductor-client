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
package io.orkes.conductor.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.util.concurrent.Uninterruptibles;

import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.util.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

public class TaskUpdateTests {

    private static TaskClient taskClient;

    private static WorkflowClient workflowClient;

    private static MetadataClient metadataClient;

    private static String workflowName = "";

    private static List<String> tasks = null;

    @BeforeAll
    public static void setup() throws IOException {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        taskClient = orkesClients.getTaskClient();
        metadataClient = orkesClients.getMetadataClient();
        workflowClient = orkesClients.getWorkflowClient();
        InputStream is = TaskUpdateTests.class.getResourceAsStream("/sdk_test.json");
        ObjectMapper om = new ObjectMapperProvider().getObjectMapper();
        WorkflowDef workflowDef = om.readValue(new InputStreamReader(is), WorkflowDef.class);
        metadataClient.registerWorkflowDef(workflowDef, true);
        workflowName = workflowDef.getName();
        tasks = workflowDef.collectTasks().stream().map(task -> task.getTaskReferenceName()).collect(Collectors.toList());
    }
    @Test
    public void testUpdateByRefName() {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName(workflowName);
        request.setVersion(1);
        request.setInput(new HashMap<>());
        String workflowId = workflowClient.startWorkflow(request);
        System.out.println(workflowId);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertNotNull(workflow);

        int maxLoop = 10;
        int count = 0;
        while (!workflow.getStatus().isTerminal() && count < maxLoop) {
            workflow.getTasks().stream().filter(t -> !t.getStatus().isTerminal()).forEach(running -> {
                String referenceName = running.getReferenceTaskName();
                System.out.println("Updating " + referenceName);
                taskClient.updateTask(workflowId, referenceName, TaskResult.Status.COMPLETED, Map.of("k", "value"));
            });
            count++;
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            workflow = workflowClient.getWorkflow(workflowId, true);
        }
        assertTrue(count < maxLoop);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());
    }

    @Test
    public void testUpdateByRefNameSync() {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName(workflowName);
        request.setVersion(1);
        request.setInput(new HashMap<>());
        String workflowId = workflowClient.startWorkflow(request);
        System.out.println(workflowId);
        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        assertNotNull(workflow);

        int maxLoop = 10;
        int count = 0;
        while (!workflow.getStatus().isTerminal() && count < maxLoop) {
            workflow = workflowClient.getWorkflow(workflowId, true);
            List<String> runningTasks = workflow.getTasks().stream()
                    .filter(task -> !task.getStatus().isTerminal() && task.getTaskType().equals("there_is_no_worker"))
                    .map(t -> t.getReferenceTaskName())
                    .collect(Collectors.toList());
            System.out.println("Running tasks: " + runningTasks);
            if(runningTasks.isEmpty()) {
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                count++;
                continue;
            }
            for (String referenceName : runningTasks) {
                System.out.println("Updating " + referenceName);
                try {
                    workflow = taskClient.updateTaskSync(workflowId, referenceName, TaskResult.Status.COMPLETED, new TaskOutput());
                    System.out.println("Workflow: " + workflow);
                } catch (ApiException apiException) {
                    //404 == task was updated already and there are no pending tasks
                    if(apiException.getStatusCode() != 404) {
                        fail(apiException);
                    }
                }
            }
            count++;
        }
        assertTrue(count < maxLoop);
        workflow = workflowClient.getWorkflow(workflowId, true);
        assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());
    }

    private static class TaskOutput {
        private String name = "hello";

        private BigDecimal value = BigDecimal.TEN;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }
    }
}
