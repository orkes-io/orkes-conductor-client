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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.time.StopWatch;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
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

    private final TaskUpdateObserver taskUpdateObserver;

    private final TaskServiceGrpc.TaskServiceStub asyncStub;

    private final AtomicInteger busyThreads;

    public TaskPollObserver(
            Worker worker,
            ThreadPoolExecutor executor,
            TaskServiceGrpc.TaskServiceStub asyncStub,
            TaskUpdateObserver taskUpdateObserver,
            AtomicInteger busyThreads) {
        this.worker = worker;
        this.executor = executor;
        this.taskUpdateObserver = taskUpdateObserver;
        this.asyncStub = asyncStub;
        this.busyThreads = busyThreads;
    }

    @Override
    public void onNext(TaskPb.Task task) {
        try {
            log.info("on Next {}", task.getTaskId());
            StopWatch stopWatch = StopWatch.createStarted();
            executor.execute(
                    () -> {
                        try {
                            log.info(
                                    "Executing task {}, {} ms after picking up",
                                    task.getTaskId(),
                                    stopWatch.getTime(TimeUnit.MILLISECONDS));
                            Task taskModel = protoMapper.fromProto(task);
                            long networkLatency = -1;

                            try {
                                Number serverSentTime =
                                        (Number) taskModel.getOutputData().get("_severSendTime");
                                if (serverSentTime != null) {
                                    networkLatency =
                                            System.currentTimeMillis() - serverSentTime.longValue();
                                }
                            } catch (Exception e) {
                            }

                            TaskResult result = worker.execute(taskModel);
                            // log.info("Executed task {}, {} ms after picking up",
                            // task.getTaskId(), stopWatch.getTime(TimeUnit.MILLISECONDS));
                            if (networkLatency > 0) {
                                result.getOutputData().put("_pollNetworkLatency", networkLatency);
                            }
                            updateTask(result);
                        } catch (Exception e) {
                            // todo: retry here...
                            log.error("Error executing task: {}", e.getMessage(), e);
                        } finally {
                            busyThreads.decrementAndGet();
                        }
                    });
        } catch (RejectedExecutionException ree) {
            // todo: retry here after some wait
            log.error(ree.getMessage(), ree);
            busyThreads.decrementAndGet(); // tood: is this the right thing to do?
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error {}", t.getMessage());
        busyThreads.set(0);

        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case UNAVAILABLE:
                log.trace("Server not available ");
                break;
            case UNAUTHENTICATED:
                log.error("{} - Invalid or missing api key/secret", code);
                break;
            case CANCELLED:
            case ABORTED:
            case DATA_LOSS:
            case DEADLINE_EXCEEDED:
                break;
            default:
                log.error("Error from server when polling for the task {} - {}", code);
        }
    }

    @Override
    public void onCompleted() {
        busyThreads.set(0);
    }

    public void updateTask(TaskResult taskResult) {
        // log.info("Updating task {}", taskResult.getTaskId());
        taskResult.getOutputData().put("_clientSendTime", System.currentTimeMillis());
        TaskServicePb.UpdateTaskRequest request =
                TaskServicePb.UpdateTaskRequest.newBuilder()
                        .setResult(protoMapper.toProto(taskResult))
                        .build();
        asyncStub.updateTask(request, taskUpdateObserver);
        // log.info("Updated task {}", taskResult.getTaskId());
    }
}
