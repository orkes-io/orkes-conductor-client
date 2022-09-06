package io.orkes.conductor.client;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkersTest {

    public static void main(String[] args) {
        Workers workers = new Workers();
        workers.register("simple_task_0", task -> {
            task.setStatus(Task.Status.COMPLETED);
            task.getOutputData().put("key", "value");
            task.getOutputData().put("key2", 42);
            return new TaskResult(task);
        });
        workers.rootUri("https://pg-staging.orkesconductor.com/api");
        workers.keyId("0739d2c2-337c-48e6-96a8-2da4c9381e10");
        workers.secret("C2o7vZDlx8AfwVp5xB6smeJ2sU7MkXvKAsX9FN9E29phPSjt");



        workers.startAll();
    }
}