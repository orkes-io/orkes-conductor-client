package io.orkes.conductor.client.worker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.config.PropertyFactory;

import io.orkes.conductor.client.http.model.Task;
import io.orkes.conductor.client.http.model.TaskResult;

import com.amazonaws.util.EC2MetadataUtils;

public interface Worker {

    /**
     * Retrieve the name of the task definition the worker is currently working on.
     *
     * @return the name of the task definition.
     */
    String getTaskDefName();

    /**
     * Executes a task and returns the updated task.
     *
     * @param task Task to be executed.
     * @return the {@link TaskResult} object If the task is not completed yet,
     *         return with the
     *         status as IN_PROGRESS.
     */
    TaskResult execute(Task task);

    /**
     * Called when the task coordinator fails to update the task to the server.
     * Client should store
     * the task id (in a database) and retry the update later
     *
     * @param task Task which cannot be updated back to the server.
     */
    default void onErrorUpdate(Task task) {
    }

    /**
     * Override this method to pause the worker from polling.
     *
     * @return true if the worker is paused and no more tasks should be polled from
     *         server.
     */
    default boolean paused() {
        return PropertyFactory.getBoolean(getTaskDefName(), "paused", false);
    }

    /**
     * Override this method to app specific rules.
     *
     * @return returns the serverId as the id of the instance that the worker is
     *         running.
     */
    default String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }
        if (serverId == null) {
            serverId = (EC2MetadataUtils.getInstanceId() == null)
                    ? System.getProperty("user.name")
                    : EC2MetadataUtils.getInstanceId();
        }
        LoggerHolder.logger.debug("Setting worker id to {}", serverId);
        return serverId;
    }

    /**
     * Override this method to change the interval between polls.
     *
     * @return interval in millisecond at which the server should be polled for
     *         worker tasks.
     */
    default int getPollingInterval() {
        return PropertyFactory.getInteger(getTaskDefName(), "pollInterval", 1000);
    }

    /**
     * Override this method to change the batch size of tasks polled.
     *
     * @return size of batch polled tasks at once per work cycle
     */
    default int getBatchSize() {
        return PropertyFactory.getInteger(getTaskDefName(), "batchSize", 1);
    }

    /**
     * Override this method to change the task domain.
     *
     * @return task domain
     */
    default String getDomain() {
        return PropertyFactory.getString(getTaskDefName(), "domain", "");
    }

    static Worker create(String taskType, Function<Task, TaskResult> executor) {
        return new Worker() {

            @Override
            public String getTaskDefName() {
                return taskType;
            }

            @Override
            public TaskResult execute(Task task) {
                return executor.apply(task);
            }

            @Override
            public boolean paused() {
                return Worker.super.paused();
            }
        };
    }
}

final class LoggerHolder {

    static final Logger logger = LoggerFactory.getLogger(Worker.class);
}
