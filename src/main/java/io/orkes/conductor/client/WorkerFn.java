package io.orkes.conductor.client;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

@FunctionalInterface
public interface WorkerFn {
    TaskResult execute(Task task);
}