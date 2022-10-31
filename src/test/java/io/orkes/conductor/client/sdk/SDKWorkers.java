package io.orkes.conductor.client.sdk;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.OutputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class SDKWorkers {

    @WorkerTask("task1")
    public @OutputParam("greetings") String task1(@InputParam("name") String name) {
        return "Hello, " + name;
    }
}
