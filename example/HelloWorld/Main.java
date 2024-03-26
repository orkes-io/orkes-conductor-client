package io.orkes.samples.quickstart;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;

import io.orkes.samples.quickstart.utils.SDKUtils;
import io.orkes.samples.quickstart.workflow.WorkflowCreator;
import io.orkes.samples.quickstart.workflow.WorkflowInput;

import static org.junit.Assert.*;				

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        SDKUtils utils = new SDKUtils();
        WorkflowCreator workflowCreator = new WorkflowCreator(utils.getWorkflowExecutor());
        System.out.print("Workflow Result: ");
        ConductorWorkflow<WorkflowInput> simpleWorkflow = workflowCreator.createSimpleWorkflow();
        simpleWorkflow.setVariables(new HashMap<>());

        WorkflowInput input = new WorkflowInput("Orkes");
        CompletableFuture<Workflow> workflowExecution = simpleWorkflow.executeDynamic(input);
        Workflow workflowRun = workflowExecution.get(30, TimeUnit.SECONDS);
        String url = utils.getUIPath() + workflowRun.getWorkflowId();
        System.out.println("See the workflow execution here: " + url);
        String result = workflowRun.getStatus().toString();
        assertEquals(result,"COMPLETED");

        // Shutdown any background threads
        utils.shutdown();
        System.exit(0);
    }

}
