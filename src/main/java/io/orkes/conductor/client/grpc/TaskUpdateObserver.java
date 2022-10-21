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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServicePb;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskUpdateObserver implements StreamObserver<TaskServicePb.UpdateTaskResponse> {

    private final Worker worker;

    private Cache<String, Future<?>> futureCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();

    public TaskUpdateObserver(Worker worker) {
        this.worker = worker;
    }

    Future<?> add(TaskResult task) {
        CompletableFuture<String> future = new CompletableFuture<>();
        futureCache.put(task.getTaskId(), future);
        return future;
    }

    @Override
    public void onNext(TaskServicePb.UpdateTaskResponse response) {
         log.info("Task update {} successful", response.getTaskId());
         futureCache.invalidate(response.getTaskId());
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        log.error("Error from server when updating the task {}", code);
    }

    @Override
    public void onCompleted() {}
}
