package io.orkes.samples.quickstart.workflow;

import java.time.Duration;
import java.time.ZonedDateTime;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.Http;
import com.netflix.conductor.sdk.workflow.def.tasks.JQ;
import com.netflix.conductor.sdk.workflow.def.tasks.Javascript;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.def.tasks.Wait;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class WorkflowCreator {

    private final WorkflowExecutor executor;

    public WorkflowCreator(WorkflowExecutor executor) {
        this.executor = executor;
    }

    public ConductorWorkflow<WorkflowInput> createSimpleWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("hello");
        workflow.setVersion(1);
        SimpleTask greetingsWF = new SimpleTask("greetings", "greetings");
        greetingsWF.input("name", "${workflow.input.name}");
        workflow.add(greetingsWF);
        Javascript jstask = new Javascript("hello_script","""
            function greetings(name) {
                return {
                    "text": "hello " + name
                }
            }
            greetings("Orkes");
            """
            );
        workflow.add(jstask);
        return workflow;
    }
}
