package io.orkes.conductor.client;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

public class SimpleWorker implements Worker {
    @Override
    public String getTaskDefName() {
        return null;
    }

    @Override
    public TaskResult execute(Task task) {
        return null;
    }
}
