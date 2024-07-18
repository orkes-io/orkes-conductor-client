package io.orkes.conductor.sdk.HelloWorld;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import com.netflix.conductor.client.worker.Worker;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;

import io.orkes.conductor.sdk.workflow.executor.WorkflowExecutor;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.sdk.ApiUtil;
import io.orkes.conductor.sdk.HelloWorld.workflow.CreateWorkflow;
import io.orkes.conductor.sdk.HelloWorld.workflow.WorkflowInput;


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
