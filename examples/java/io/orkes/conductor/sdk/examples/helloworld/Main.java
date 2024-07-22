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
package io.orkes.conductor.sdk.examples.helloworld;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.sdk.examples.ApiUtil;
import io.orkes.conductor.sdk.examples.helloworld.workflow.CreateWorkflow;
import io.orkes.conductor.sdk.examples.helloworld.workflow.WorkflowInput;


public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        TaskClient taskClient = orkesClients.getTaskClient();
        WorkflowClient workflowClient = orkesClients.getWorkflowClient();
        MetadataClient metadataClient = orkesClients.getMetadataClient();

        WorkflowExecutor workflowExecutor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 10);
        workflowExecutor.initWorkers("io.orkes.conductor.sdk.HelloWorld.workers");
        TaskRunnerConfigurer taskrunner = initWorkers(Arrays.asList(),taskClient);
        CreateWorkflow workflowCreator = new CreateWorkflow(workflowExecutor);
        ConductorWorkflow<WorkflowInput> simpleWorkflow = workflowCreator.createGreetingsWorkflow();
        simpleWorkflow.setVariables(new HashMap<>());

        WorkflowInput input = new WorkflowInput("Orkes");
        CompletableFuture<Workflow> workflowExecution = simpleWorkflow.executeDynamic(input);
        Workflow workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);

        
        taskrunner.shutdown();
        workflowClient.shutdown();
        System.exit(0);
    }

    private static TaskRunnerConfigurer initWorkers(List<Worker> workers, TaskClient taskClient) {
        TaskRunnerConfigurer.Builder builder = new TaskRunnerConfigurer.Builder(taskClient, workers);
        TaskRunnerConfigurer taskRunner = builder.withThreadCount(1).withTaskPollTimeout(5).build();
        // Start Polling for tasks and execute them
        taskRunner.init();
        return taskRunner;
    }
}
