package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.Task;
import io.orkes.conductor.client.http.model.TaskResult;

@FunctionalInterface
public interface WorkerFn {
    TaskResult execute(Task task);
}