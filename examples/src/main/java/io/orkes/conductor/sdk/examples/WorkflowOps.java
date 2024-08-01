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
package io.orkes.conductor.sdk.examples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.orkes.conductor.client.api.MetadataClient;
import io.orkes.conductor.client.api.TaskClient;
import io.orkes.conductor.client.api.WorkflowClient;
import io.orkes.conductor.client.http.OrkesClients;
import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import io.orkes.conductor.client.model.metadata.tasks.Task;
import io.orkes.conductor.client.model.run.Workflow;
import io.orkes.conductor.sdk.examples.helloworld.workflow.WorkflowInput;
import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;
import io.orkes.conductor.sdk.workflow.executor.WorkflowExecutor;

public class WorkflowOps {

    private static final String BASE_PATH = "http://localhost:8080/api";
    private static final String ENV_KEY_ID = "";
    private static final String ENV_SECRET = "";

    private static final String UI_URL = "http://localhost:5000/execution/";


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //Initialise Conductor Client
        OrkesClients orkesClients = getApiClientWithCredentials();
        TaskClient taskClient = orkesClients.getTaskClient();
        WorkflowClient workflowClient = orkesClients.getWorkflowClient();
        MetadataClient metadataClient = orkesClients.getMetadataClient();

        //Initialise WorkflowExecutor and Conductor Workers
        WorkflowExecutor workflowExecutor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 10);
        workflowExecutor.initWorkers("io.orkes.conductor.sdk.examples.HelloWorld.workers");

        //Create the workflow with input
        GreetingsWorkflow workflowCreator = new GreetingsWorkflow(workflowExecutor);
        ConductorWorkflow<WorkflowInput> simpleWorkflow = workflowCreator.createWorkflow();
        WorkflowInput input = new WorkflowInput("Orkes");
        CompletableFuture<Workflow> workflowExecution = simpleWorkflow.executeDynamic(input);
        Workflow workflowRun = workflowExecution.get(100, TimeUnit.SECONDS);

        String workflowId = workflowRun.getWorkflowId();

        //Workflow Execution Started
        System.out.println("Your workflow started with id "+workflowId);
        //Print Status
        System.out.println("Workflow status is "+workflowRun.getStatus());
        System.out.println("You can monitor the execution here "+UI_URL+workflowId);

        Workflow workflow = workflowClient.getWorkflow(workflowId, true);
        List<Task> lastTask = workflow.getTasks();
        String lastTaskId = lastTask.get(lastTask.size() - 1).getTaskDefName();
        System.out.println("Workflow status is "+workflowRun.getStatus()+ " and currently running task is "+lastTaskId);

        //Test Termination
        workflowClient.terminateWorkflow(workflowId,"Testing Termination");
        System.out.println("Workflow status is "+workflowRun.getStatus());

        List<String> workflowIds = Arrays.asList(workflowId);

        //Restart Workflow
        workflowClient.restartWorkflow(workflowIds,true);
        System.out.println("Workflow status is "+workflowRun.getStatus());
        //Pause Workflow
        workflowClient.pauseWorkflow(workflowId);
        System.out.println("Workflow status is "+workflowRun.getStatus());
        //Resume Workflow
        workflowClient.resumeWorkflow(workflowIds);
        System.out.println("Workflow status is "+workflowRun.getStatus());
        //Terminate Workflow
        workflowClient.terminateWorkflow(workflowId,"Testing Termination");
        System.out.println("Workflow status is "+workflowRun.getStatus());

        //Shutdown workflowClient and taskrunner
        workflowClient.shutdown();
        System.exit(0);
    }

    public static OrkesClients getApiClientWithCredentials() {
        return new OrkesClients(OrkesHttpClient.builder()
                .basePath(BASE_PATH)
                .keyId(ENV_KEY_ID)
                .keySecret(ENV_SECRET)
                .writeTimeout(30_000)
                .readTimeout(30_000)
                .connectTimeout(30_000)
                .build());
    }
}
