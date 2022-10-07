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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;

import io.orkes.conductor.client.ApiClient;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

@Slf4j
public class GrpcTaskWorker {
    private final ManagedChannel channel;

    private final TaskServiceGrpc.TaskServiceBlockingStub stub;

    private final TaskServiceGrpc.TaskServiceStub asyncStub;

    private final Worker worker;

    private final String domain;

    private final ThreadPoolExecutor executor;

    private final int threadCount;

    private final int pollTimeoutInMills;

    public GrpcTaskWorker(
            ApiClient apiClient,
            Worker worker,
            String domain,
            int threadCount,
            int pollTimeoutInMills) {
        this.worker = worker;
        this.domain = domain;
        this.executor =
                new ThreadPoolExecutor(
                        threadCount,
                        threadCount,
                        1,
                        TimeUnit.MINUTES,
                        new ArrayBlockingQueue<>(threadCount * 100));
        this.threadCount = threadCount;
        this.pollTimeoutInMills = pollTimeoutInMills;
        this.channel = getChannel(apiClient);
        this.stub =
                TaskServiceGrpc.newBlockingStub(this.channel)
                        .withInterceptors(new HeaderClientInterceptor(apiClient));
        this.asyncStub =
                TaskServiceGrpc.newStub(this.channel)
                        .withInterceptors(new HeaderClientInterceptor(apiClient));
    }

    public void init() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        () -> pollAndExecute(),
                        worker.getPollingInterval(),
                        worker.getPollingInterval(),
                        TimeUnit.MILLISECONDS);
    }

    public void pollAndExecute() {
        int pollCount = getAvailableWorkers();
        log.debug("Polling for {} tasks", pollCount);
        TaskServicePb.BatchPollRequest request = buildPollRequest(pollCount, pollTimeoutInMills);
        asyncStub.batchPoll(request, new TaskPollObserver(worker, executor, stub, asyncStub));
    }

    private TaskServicePb.BatchPollRequest buildPollRequest(int count, int timeoutInMillisecond) {
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

    private int getAvailableWorkers() {
        return (this.threadCount) - this.executor.getActiveCount();
    }
}
