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
import java.util.concurrent.atomic.AtomicInteger;

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

    private final TaskUpdateObserver taskUpdateObserver;

    private final int pollTimeoutInMills;

    private final AtomicInteger busyThreads;

    private final int threadCount;

    private final ArrayBlockingQueue<Runnable> executionQueue;

    public GrpcTaskWorker(
            TaskServiceGrpc.TaskServiceStub asyncStub,
            Worker worker,
            String domain,
            int threadCount,
            int pollTimeoutInMills,
            TaskUpdateObserver taskUpdateObserver) {
        this.worker = worker;
        this.domain = domain;
        this.taskUpdateObserver = taskUpdateObserver;
        this.pollTimeoutInMills = pollTimeoutInMills;
        this.asyncStub = asyncStub;
        this.threadCount = threadCount;
        this.busyThreads = new AtomicInteger(0);
        this.executionQueue = new ArrayBlockingQueue<>(threadCount){

        };
        this.executor =
                new ThreadPoolExecutor(
                        threadCount,
                        threadCount,
                        1,
                        TimeUnit.MINUTES,
                        executionQueue);
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
        try {
            int pollCount = getPollCount();
            if (pollCount < 1) {
                return;
            }
            log.debug("Polling {} for {} tasks", worker.getTaskDefName(), pollCount);
            TaskServicePb.BatchPollRequest request = buildPollRequest(pollCount, pollTimeoutInMills);
            asyncStub.batchPoll(request, new TaskPollObserver(worker, executor, asyncStub, taskUpdateObserver, busyThreads));
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
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

    private int getPollCount() {

        int busyCount = busyThreads.get();
        int pollCount = threadCount - busyCount;
        log.info("poll count {}, busy threads = {}, execution queue size", pollCount, busyThreads.get());
        if(pollCount < 1) {
            return 0;
        }
        return pollCount;

        /*
        if(busyThreads.compareAndSet(busyCount, pollCount)){
            log.info("returning {} polls", pollCount);
            return pollCount;
        }
        return 0;
        */
    }

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(10);
        System.out.println(ai.get());



        int what = ai.getAndUpdate(operand -> 0);
        System.out.println(what);
        System.out.println(ai.get());

        ai.set(3);
        what = ai.getAndUpdate(operand -> 0);
        System.out.println(what);
        System.out.println(ai.get());
    }

}
