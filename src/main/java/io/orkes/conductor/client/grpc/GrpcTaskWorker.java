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

import java.util.concurrent.*;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcTaskWorker {

    private final TaskServiceGrpc.TaskServiceStub asyncStub;

    private final Worker worker;

    private final String domain;

    private final ThreadPoolExecutor executor;

    private final int threadCount;

    private final int pollTimeoutInMills;

    private final TaskPollObserver taskPollObserver;

    private final int bufferSize;

    public GrpcTaskWorker(
            TaskServiceGrpc.TaskServiceStub asyncStub,
            TaskPollObserver taskPollObserver,
            ThreadPoolExecutor executor,
            Worker worker,
            String domain,
            int threadCount,
            int pollTimeoutInMills) {
        this.worker = worker;
        this.domain = domain;
        this.threadCount = threadCount;
        this.pollTimeoutInMills = pollTimeoutInMills;
        this.asyncStub = asyncStub;
        this.taskPollObserver = taskPollObserver;
        this.executor = executor;
        this.bufferSize = this.threadCount * 2;
    }

    public void init() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        () -> _pollAndExecute(),
                        worker.getPollingInterval(),
                        worker.getPollingInterval(),
                        TimeUnit.MILLISECONDS);
    }

    private void _pollAndExecute() {
        int pollCount = getPollCount();
        if (pollCount < 1) {
            return;
        }
        log.debug("Polling {} for {} tasks", worker.getTaskDefName(), pollCount);
        TaskServicePb.BatchPollRequest request = buildPollRequest(pollCount, pollTimeoutInMills);
        asyncStub.batchPoll(request, taskPollObserver);
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

    private int getPollCount() {
        return (this.bufferSize) - this.executor.getActiveCount();
    }
}
