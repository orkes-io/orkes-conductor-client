package io.orkes.conductor.sdk.examples.HelloWorld.workflow;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class CreateWorkflow {
    private final WorkflowExecutor executor;
    public CreateWorkflow(WorkflowExecutor executor) {
        this.executor = executor;
    }
    public ConductorWorkflow<WorkflowInput> createSimpleWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("greetings");
        workflow.setVersion(1);
        SimpleTask greetingsWF = new SimpleTask("greet", "greet_ref");
        greetingsWF.input("name", "${workflow.input.name}");
        workflow.add(greetingsWF);
        return workflow;
    }
}

