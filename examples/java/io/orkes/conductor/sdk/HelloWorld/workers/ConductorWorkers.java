package io.orkes.conductor.sdk.HelloWorld.workers;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {
    @WorkerTask("greetings")
    public String greeting(@InputParam("name") String name) {
        return ("Hello " + name);
    }
}
