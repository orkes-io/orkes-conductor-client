/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.grpc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;
import com.netflix.conductor.proto.TaskPb;

import io.orkes.conductor.proto.ProtoMappingHelper;

import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoolWorker {

    private final PooledPoller pooledPoller;
    private final Worker worker;

    private final TaskServiceGrpc.TaskServiceFutureStub taskServiceStub;
    private int threadId;
    private final ProtoMappingHelper protoMapper = ProtoMappingHelper.INSTANCE;
    private final Semaphore semaphore;

    private final TaskUpdateObserver taskUpdateObserver;

    public PoolWorker(
            PooledPoller pooledPoller,
            Worker worker,
            TaskServiceGrpc.TaskServiceFutureStub asyncStub,
            int threadId,
            Semaphore semaphore) {
        this.pooledPoller = pooledPoller;
        this.worker = worker;
        this.taskServiceStub = asyncStub;
        this.threadId = threadId;
        this.semaphore = semaphore;
        this.taskUpdateObserver = new TaskUpdateObserver();
    }

    public void run() {
        try {
            semaphore.acquireUninterruptibly();
            TaskPb.Task task = pooledPoller.getTask(threadId);
            if (task != null && !"NO_OP".equals(task.getTaskId())) {
                log.debug("Executing task {}", task.getTaskId());
                Task taskModel = protoMapper.fromProto(task);
                try {
                    if (taskModel.getOutputData().containsKey("_severSendTime")) {
                        long serverSentTime =
                                ((Number) taskModel.getOutputData().get("_severSendTime"))
                                        .longValue();
                        long networkLatency = System.currentTimeMillis() - serverSentTime;
                        taskModel.getOutputData().put("_pollNetworkLatency", networkLatency);
                    }
                } catch (Exception e) {
                    log.warn("Error", e);
                }
                TaskResult result = worker.execute(taskModel);
                log.debug("Executed task {}", task.getTaskId());
                updateTaskResult(3, taskModel, result, worker);
            }
        } catch (Throwable e) {
            log.error("Error executing task: {}", e.getMessage(), e);
        }
    }


    private void updateTaskResult(int count, Task task, TaskResult result, Worker worker) {
        try {

            retryOperation(
                    (TaskResult taskResult) -> {
                        _updateTask(taskResult);
                        return null;
                    },
                    count,
                    result,
                    "updateTask");
        } catch (Exception e) {
            worker.onErrorUpdate(task);
            MetricsContainer.incrementTaskUpdateErrorCount(worker.getTaskDefName(), e);
            log.error("Failed to update result: {} for task: {} in worker: {}", result.toString(), task.getTaskDefName(), worker.getIdentity(),e);
        }
    }

    private void _updateTask(TaskResult taskResult) {
        log.info("Updating task {}", taskResult.getTaskId());
        taskResult.getOutputData().put("_clientSendTime", System.currentTimeMillis());
        TaskServicePb.UpdateTaskRequest request = TaskServicePb.UpdateTaskRequest.newBuilder().setResult(protoMapper.toProto(taskResult)).build();
        ListenableFuture<TaskServicePb.UpdateTaskResponse> future = taskServiceStub.updateTask(request);
        try {
            future.get(30_000,  TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private <T, R> R retryOperation(Function<T, R> operation, int count, T input, String opName) {
        int index = 0;
        while (index < count) {
            try {
                return operation.apply(input);
            } catch (Exception e) {
                index++;
                try {
                    Thread.sleep(500L * (index+1));
                } catch (InterruptedException ie) {
                    log.error("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("Exhausted retries performing " + opName);
    }
}
