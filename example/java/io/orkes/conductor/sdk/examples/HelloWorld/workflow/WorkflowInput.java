package io.orkes.conductor.sdk.examples.HelloWorld.workflow;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
    public class ConductorWorkers {
        @WorkerTask("greet")
        public String greet(@InputParam("name") String name) {
            return "Hello " + name;
        }
    }