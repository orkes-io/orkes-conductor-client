package io.orkes.conductor.sdk.HelloWorld.workflow;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class CreateWorkflow {
    private final WorkflowExecutor executor;
    public CreateWorkflow(WorkflowExecutor executor) {
        this.executor = executor;
    }
    public ConductorWorkflow<WorkflowInput> createGreetingsWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("greetings");
        workflow.setVersion(1);
        SimpleTask greetingsTask = new SimpleTask("greet", "greet_ref");
        greetingsTask.input("name", "${workflow.input.name}");
        workflow.add(greetingsTask);
        return workflow;
    }
}

