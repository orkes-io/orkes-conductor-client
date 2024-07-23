/*
 * Copyright 2021 Orkes, Inc.
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
package io.orkes.conductor.sdk.workflow.executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.ObjectMapperProvider;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.clients.OrkesMetadataClient;
import io.orkes.conductor.client.http.clients.OrkesTaskClient;
import io.orkes.conductor.client.http.clients.OrkesWorkflowClient;
import io.orkes.conductor.client.model.metadata.tasks.TaskDef;
import io.orkes.conductor.client.model.metadata.tasks.TaskType;
import io.orkes.conductor.client.model.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.model.metadata.workflow.WorkflowDef;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;
import io.orkes.conductor.sdk.workflow.def.tasks.DoWhile;
import io.orkes.conductor.sdk.workflow.def.tasks.Dynamic;
import io.orkes.conductor.sdk.workflow.def.tasks.DynamicFork;
import io.orkes.conductor.sdk.workflow.def.tasks.Event;
import io.orkes.conductor.sdk.workflow.def.tasks.ForkJoin;
import io.orkes.conductor.sdk.workflow.def.tasks.Http;
import io.orkes.conductor.sdk.workflow.def.tasks.JQ;
import io.orkes.conductor.sdk.workflow.def.tasks.Javascript;
import io.orkes.conductor.sdk.workflow.def.tasks.Join;
import io.orkes.conductor.sdk.workflow.def.tasks.SetVariable;
import io.orkes.conductor.sdk.workflow.def.tasks.SimpleTask;
import io.orkes.conductor.sdk.workflow.def.tasks.SubWorkflow;
import io.orkes.conductor.sdk.workflow.def.tasks.Switch;
import io.orkes.conductor.sdk.workflow.def.tasks.TaskRegistry;
import io.orkes.conductor.sdk.workflow.def.tasks.Terminate;
import io.orkes.conductor.sdk.workflow.def.tasks.Wait;
import io.orkes.conductor.sdk.workflow.executor.task.AnnotatedWorkerExecutor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkflowExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowExecutor.class);

    private final TypeReference<List<TaskDef>> listOfTaskDefs = new TypeReference<>() {
    };

    private Map<String, CompletableFuture<Workflow>> runningWorkflowFutures =
            new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapperProvider().getObjectMapper();

    private final TaskClient taskClient;

    private final WorkflowClient workflowClient;

    private final MetadataClient metadataClient;

    private final AnnotatedWorkerExecutor annotatedWorkerExecutor;

    private final ScheduledExecutorService scheduledWorkflowMonitor =
            Executors.newSingleThreadScheduledExecutor();

    static {
        initTaskImplementations();
    }

    public static void initTaskImplementations() {
        TaskRegistry.register(TaskType.DO_WHILE.name(), DoWhile.class);
        TaskRegistry.register(TaskType.DYNAMIC.name(), Dynamic.class);
        TaskRegistry.register(TaskType.FORK_JOIN_DYNAMIC.name(), DynamicFork.class);
        TaskRegistry.register(TaskType.FORK_JOIN.name(), ForkJoin.class);
        TaskRegistry.register(TaskType.HTTP.name(), Http.class);
        TaskRegistry.register(TaskType.INLINE.name(), Javascript.class);
        TaskRegistry.register(TaskType.JOIN.name(), Join.class);
        TaskRegistry.register(TaskType.JSON_JQ_TRANSFORM.name(), JQ.class);
        TaskRegistry.register(TaskType.SET_VARIABLE.name(), SetVariable.class);
        TaskRegistry.register(TaskType.SIMPLE.name(), SimpleTask.class);
        TaskRegistry.register(TaskType.SUB_WORKFLOW.name(), SubWorkflow.class);
        TaskRegistry.register(TaskType.SWITCH.name(), Switch.class);
        TaskRegistry.register(TaskType.TERMINATE.name(), Terminate.class);
        TaskRegistry.register(TaskType.WAIT.name(), Wait.class);
        TaskRegistry.register(TaskType.EVENT.name(), Event.class);
    }

    public WorkflowExecutor(String url) {
        OrkesHttpClient apiClient = new OrkesHttpClient.Builder().basePath(url).build();
        this.taskClient = new OrkesTaskClient(apiClient);
        this.workflowClient = new OrkesWorkflowClient(apiClient);
        this.metadataClient = new OrkesMetadataClient(apiClient);
        annotatedWorkerExecutor = new AnnotatedWorkerExecutor(taskClient);
        initMonitor();
    }

    public WorkflowExecutor(
            TaskClient taskClient,
            WorkflowClient workflowClient,
            MetadataClient metadataClient,
            int pollingInterval) {

        this.taskClient = taskClient;
        this.workflowClient = workflowClient;
        this.metadataClient = metadataClient;
        annotatedWorkerExecutor = new AnnotatedWorkerExecutor(taskClient, pollingInterval);
        initMonitor();
    }

    public WorkflowExecutor(
            TaskClient taskClient,
            WorkflowClient workflowClient,
            MetadataClient metadataClient,
            AnnotatedWorkerExecutor annotatedWorkerExecutor) {

        this.taskClient = taskClient;
        this.workflowClient = workflowClient;
        this.metadataClient = metadataClient;
        this.annotatedWorkerExecutor = annotatedWorkerExecutor;
        initMonitor();
    }

    private void initMonitor() {
        scheduledWorkflowMonitor.scheduleAtFixedRate(
                () -> {
                    for (Map.Entry<String, CompletableFuture<Workflow>> entry :
                            runningWorkflowFutures.entrySet()) {
                        String workflowId = entry.getKey();
                        CompletableFuture<Workflow> future = entry.getValue();
                        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
                        if (workflow.getStatus().isTerminal()) {
                            future.complete(workflow);
                            runningWorkflowFutures.remove(workflowId);
                        }
                    }
                },
                100,
                100,
                TimeUnit.MILLISECONDS);
    }

    public void initWorkers(String packagesToScan) {
        annotatedWorkerExecutor.initWorkers(packagesToScan);
    }

    public CompletableFuture<Workflow> executeWorkflow(String name, Integer version, Object input) {
        CompletableFuture<Workflow> future = new CompletableFuture<>();
        Map<String, Object> inputMap = objectMapper.convertValue(input, Map.class);

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setInput(inputMap);
        request.setName(name);
        request.setVersion(version);

        String workflowId = workflowClient.startWorkflow(request);
        runningWorkflowFutures.put(workflowId, future);
        return future;
    }

    public CompletableFuture<Workflow> executeWorkflow(
            ConductorWorkflow conductorWorkflow, Object input) {

        CompletableFuture<Workflow> future = new CompletableFuture<>();

        Map<String, Object> inputMap = objectMapper.convertValue(input, Map.class);

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setInput(inputMap);
        request.setName(conductorWorkflow.getName());
        request.setVersion(conductorWorkflow.getVersion());
        request.setWorkflowDef(conductorWorkflow.toWorkflowDef());

        String workflowId = workflowClient.startWorkflow(request);
        runningWorkflowFutures.put(workflowId, future);

        return future;
    }

    public void loadTaskDefs(String resourcePath) throws IOException {
        InputStream resource = WorkflowExecutor.class.getResourceAsStream(resourcePath);
        if (resource != null) {
            List<TaskDef> taskDefs = objectMapper.readValue(resource, listOfTaskDefs);
            loadMetadata(taskDefs);
        }
    }

    public void loadWorkflowDefs(String resourcePath) throws IOException {
        InputStream resource = WorkflowExecutor.class.getResourceAsStream(resourcePath);
        if (resource != null) {
            WorkflowDef workflowDef = objectMapper.readValue(resource, WorkflowDef.class);
            loadMetadata(workflowDef);
        }
    }

    public void loadMetadata(WorkflowDef workflowDef) {
        metadataClient.registerWorkflowDef(workflowDef);
    }

    public void loadMetadata(List<TaskDef> taskDefs) {
        metadataClient.registerTaskDefs(taskDefs);
    }

    public void shutdown() {
        scheduledWorkflowMonitor.shutdown();
        annotatedWorkerExecutor.shutdown();
    }

    public boolean registerWorkflow(WorkflowDef workflowDef, boolean overwrite) {
        try {
            if (overwrite) {
                metadataClient.updateWorkflowDefs(Arrays.asList(workflowDef));
            } else {
                metadataClient.registerWorkflowDef(workflowDef);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    public MetadataClient getMetadataClient() {
        return metadataClient;
    }

    public TaskClient getTaskClient() {
        return taskClient;
    }
}
