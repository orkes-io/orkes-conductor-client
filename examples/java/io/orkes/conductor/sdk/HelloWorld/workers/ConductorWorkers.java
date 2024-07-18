package io.orkes.conductor.sdk.HelloWorld.workers;

import io.orkes.conductor.sdk.workflow.task.InputParam;
import io.orkes.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {
    @WorkerTask("greet")
    public String greeting(@InputParam("name") String name) {
        return ("Hello " + name);
    }
}
