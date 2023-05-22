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
package io.orkes.conductor.client.grpc.workflow;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.grpc.HeaderClientInterceptor;
import io.orkes.conductor.common.model.WorkflowRun;
import io.orkes.conductor.proto.ProtoMappingHelper;
import io.orkes.grpc.service.OrkesWorkflowService;
import io.orkes.grpc.service.WorkflowServiceStreamGrpc;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

@Slf4j
public class GrpcWorkflowClient {

    private WorkflowServiceStreamGrpc.WorkflowServiceStreamStub stub;

    private StreamObserver<OrkesWorkflowService.StartWorkflowRequest> requestStream;

    private StartWorkflowResponseStream responseStream;

    private final ProtoMappingHelper protoMappingHelper = ProtoMappingHelper.INSTANCE;

    private final WorkflowExecutionMonitor executionMonitor;

    private final ManagedChannel channel;

    public GrpcWorkflowClient(ApiClient apiClient) {
        this.executionMonitor = new WorkflowExecutionMonitor();
        this.channel = getChannel(apiClient);

        stub =
                WorkflowServiceStreamGrpc.newStub(channel)
                        .withInterceptors(new HeaderClientInterceptor(apiClient));
        this.responseStream = new StartWorkflowResponseStream(executionMonitor);
        requestStream = stub.startWorkflow(responseStream);
    }

    private synchronized boolean reConnect() {
        try {
            requestStream = stub.startWorkflow(this.responseStream);
            return true;
        } catch (Exception connectException) {
            log.error("Server not ready {}", connectException.getMessage(), connectException);
            return false;
        }
    }

    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest startWorkflowRequest, String waitUntilTask) {
        if (!responseStream.isReady()) {
            int connectAttempts = 3;
            int sleepTime = 200;

            while (connectAttempts > 0) {
                reConnect();
                log.info("Connection attempt {} backoff for {} millis", connectAttempts, sleepTime);
                Uninterruptibles.sleepUninterruptibly(sleepTime, TimeUnit.MILLISECONDS);
                if(responseStream.isReady()) {
                    break;
                }
                connectAttempts--;
                sleepTime = sleepTime * 2;
            }
            if(!responseStream.isReady()) {
                throw new RuntimeException("Server is not yet ready to accept the requests");
            }
        }
        String requestId = UUID.randomUUID().toString();

        OrkesWorkflowService.StartWorkflowRequest.Builder requestBuilder = OrkesWorkflowService.StartWorkflowRequest.newBuilder();
        requestBuilder.setRequestId(requestId).setIdempotencyKey(requestId).setMonitor(true);
        if (waitUntilTask != null) {
            requestBuilder.setWaitUntilTask(waitUntilTask);
        }
        requestBuilder.setRequest(protoMappingHelper.toProto(startWorkflowRequest));
        CompletableFuture<WorkflowRun> future = executionMonitor.monitorRequest(requestId);
        synchronized (requestStream) {
            requestStream.onNext(requestBuilder.build());
        }
        return future;
    }

    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest startWorkflowRequest, String waitUntilTask, Integer waitForSeconds) {
        if (!responseStream.isReady()) {
            int connectAttempts = 3;
            int sleepTime = 200;

            while (connectAttempts > 0) {
                reConnect();
                log.info("Connection attempt {} backoff for {} millis", connectAttempts, sleepTime);
                Uninterruptibles.sleepUninterruptibly(sleepTime, TimeUnit.MILLISECONDS);
                if(responseStream.isReady()) {
                    break;
                }
                connectAttempts--;
                sleepTime = sleepTime * 2;
            }
            if(!responseStream.isReady()) {
                throw new RuntimeException("Server is not yet ready to accept the requests");
            }
        }
        String requestId = UUID.randomUUID().toString();

        OrkesWorkflowService.StartWorkflowRequest.Builder requestBuilder = OrkesWorkflowService.StartWorkflowRequest.newBuilder();
        requestBuilder.setRequestId(requestId).setIdempotencyKey(requestId).setMonitor(true);
        if (waitUntilTask != null) {
            requestBuilder.setWaitUntilTask(waitUntilTask);
        }
        requestBuilder.setRequest(protoMappingHelper.toProto(startWorkflowRequest));
        CompletableFuture<WorkflowRun> future = executionMonitor.monitorRequest(requestId);
        future.orTimeout(waitForSeconds, TimeUnit.SECONDS);
        synchronized (requestStream) {
            requestStream.onNext(requestBuilder.build());
        }
        return future;
    }

    public void shutdown() {
        channel.shutdown();
    }
}
