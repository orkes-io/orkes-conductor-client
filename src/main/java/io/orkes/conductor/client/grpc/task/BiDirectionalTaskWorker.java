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
package io.orkes.conductor.client.grpc.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;

import io.orkes.conductor.client.grpc.TaskPollObserver;
import io.orkes.grpc.service.TaskServiceStreamGrpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BiDirectionalTaskWorker {

    private final TaskServiceGrpc.TaskServiceStub asyncStub;

    private final Worker worker;

    private final String domain;

    private final ThreadPoolExecutor executor;

    private final int threadCount;

    private final int pollTimeoutInMills;

    private final TaskPollObserver taskPollObserver;

    private StreamObserver<TaskServicePb.BatchPollRequest> requestObserver;

    private final PollResponseObserver pollResponseObserver;

    private final TaskServiceStreamGrpc.TaskServiceStreamStub bidiService;

    public BiDirectionalTaskWorker(
            TaskServiceStreamGrpc.TaskServiceStreamStub bidiService,
            TaskServiceGrpc.TaskServiceStub asyncStub,
            TaskPollObserver taskPollObserver,
            ThreadPoolExecutor executor,
            Worker worker,
            String domain,
            int threadCount,
            int pollTimeoutInMills) {
        this.bidiService = bidiService;
        this.worker = worker;
        this.domain = domain;
        this.threadCount = threadCount;
        this.pollTimeoutInMills = pollTimeoutInMills;
        this.asyncStub = asyncStub;
        this.taskPollObserver = taskPollObserver;
        this.executor = executor;
        this.pollResponseObserver = new PollResponseObserver();
    }

    public void init() {
        requestObserver = bidiService.taskPoll(pollResponseObserver);
        pollResponseObserver.setReady();
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        () -> _pollAndExecute(),
                        worker.getPollingInterval(),
                        worker.getPollingInterval(),
                        TimeUnit.MILLISECONDS);
    }

    private void _pollAndExecute() {
        int pollCount = getAvailableWorkers();
        if (pollCount < 1) {
            return;
        }

        try {
            if (!pollResponseObserver.isReady()) {
                log.warn("Connection not ready yet...");
                requestObserver = bidiService.taskPoll(pollResponseObserver);
                pollResponseObserver.setReady();
                return;
            }
            requestObserver.onNext(buildPollRequest(pollCount, pollTimeoutInMills));
        } catch (Throwable t) {
            log.error("Error sending request {}", t.getMessage());
        }
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
        return (this.threadCount * 2) - this.executor.getActiveCount();
    }
}
