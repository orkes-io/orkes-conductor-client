package io.orkes.conductor.sdk.examples.HelloWorld.workers;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {

    @WorkerTask("greetings")
    public void greeting(@InputParam("name") String name) {
        System.out.println("Routing packages to country " + name);
    }
}
