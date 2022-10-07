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

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;
import com.netflix.conductor.proto.ProtoMappingHelper;
import com.netflix.conductor.proto.TaskPb;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskPollObserver implements StreamObserver<TaskPb.Task> {

    private final ProtoMappingHelper protoMapper = ProtoMappingHelper.INSTANCE;

    private final Worker worker;

    private final ThreadPoolExecutor executor;

    private final TaskServiceGrpc.TaskServiceBlockingStub stub;

    private final TaskServiceGrpc.TaskServiceStub asyncStub;

    public TaskPollObserver(
            Worker worker,
            ThreadPoolExecutor executor,
            TaskServiceGrpc.TaskServiceBlockingStub stub,
            TaskServiceGrpc.TaskServiceStub asyncStub) {
        this.worker = worker;
        this.executor = executor;
        this.stub = stub;
        this.asyncStub = asyncStub;
    }

    @Override
    public void onNext(TaskPb.Task task) {
        try {
            executor.execute(
                    () -> {
                        try {
                            TaskResult result = worker.execute(protoMapper.fromProto(task));
                            updateTask(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("Error executing task: {}", e.getMessage(), e);
                        }
                    });
        } catch (RejectedExecutionException ree) {
            log.error(ree.getMessage(), ree);
        }
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case UNAVAILABLE:
                log.trace("Server not available ");
                break;
            case CANCELLED:
            case ABORTED:
            case DATA_LOSS:
            case DEADLINE_EXCEEDED:
                break;
            default:
                log.error("Error from server when polling for the task {}", code);
        }
    }

    @Override
    public void onCompleted() {}

    public void updateTask(TaskResult taskResult) {
        TaskServicePb.UpdateTaskRequest request = TaskServicePb.UpdateTaskRequest.newBuilder()
                .setResult(protoMapper.toProto(taskResult))
                .build();
        asyncStub.updateTask(request, new TaskUpdateObserver());

    }

    private TaskServicePb.BatchPollRequest buildPollRequest(
            String domain, int count, int timeoutInMillisecond) {
        TaskServicePb.BatchPollRequest.Builder requestBuilder =
                TaskServicePb.BatchPollRequest.newBuilder()
                        .setCount(count)
                        .setTaskType(worker.getTaskDefName())
                        .setTimeout(timeoutInMillisecond)
                        .setWorkerId(worker.getIdentity());
        if (domain != null) {
            requestBuilder = requestBuilder.setDomain(domain);
        }
        return requestBuilder.build();
    }

    private void next() {
        TaskServicePb.BatchPollRequest request =
                buildPollRequest(null, getAvailableWorkers(), 1000);
        asyncStub.batchPoll(request, new TaskPollObserver(worker, executor, stub, asyncStub));
    }

    public void init() {
        next();
    }

    private int getAvailableWorkers() {
        return (this.executor.getMaximumPoolSize()) - this.executor.getActiveCount();
    }
}
