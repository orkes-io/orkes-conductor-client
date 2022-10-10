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

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;
import com.netflix.conductor.proto.ProtoMappingHelper;
import com.netflix.conductor.proto.TaskPb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoolWorker {

    private final PooledPoller pooledPoller;
    private final Worker worker;
    private final TaskUpdateObserver taskUpdateObserver;
    private final TaskServiceGrpc.TaskServiceStub asyncStub;
    private int threadId;
    private final ProtoMappingHelper protoMapper = ProtoMappingHelper.INSTANCE;
    private final Semaphore semaphore;

    public PoolWorker(
            PooledPoller pooledPoller,
            Worker worker,
            TaskUpdateObserver taskUpdateObserver,
            TaskServiceGrpc.TaskServiceStub asyncStub,
            int threadId,
            Semaphore semaphore) {
        this.pooledPoller = pooledPoller;
        this.worker = worker;
        this.taskUpdateObserver = taskUpdateObserver;
        this.asyncStub = asyncStub;
        this.threadId = threadId;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            semaphore.acquireUninterruptibly();
            TaskPb.Task task = pooledPoller.getTask(threadId);
            if (task != null && !"NO_OP".equals(task.getTaskId())) {
                log.info("Executing task {}", task.getTaskId());
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
                log.info("Executed task {}", task.getTaskId());
                updateTask(result);
            }
        } catch (Throwable e) {
            // TODO: retry here...
            log.error("Error executing task: {}", e.getMessage(), e);
        }
    }

    public void updateTask(TaskResult taskResult) {
        log.info("Updating task {}", taskResult.getTaskId());
        taskResult.getOutputData().put("_clientSendTime", System.currentTimeMillis());
        TaskServicePb.UpdateTaskRequest request =
                TaskServicePb.UpdateTaskRequest.newBuilder()
                        .setResult(protoMapper.toProto(taskResult))
                        .build();
        asyncStub.updateTask(request, taskUpdateObserver);
        log.info("Updated task {}", taskResult.getTaskId());
    }
}
