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

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.grpc.HeaderClientInterceptor;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.grpc.service.OrkesWorkflowService;
import io.orkes.grpc.service.WorkflowServiceStreamGrpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

@Slf4j
public class GrpcWorkflowClient {

    private WorkflowServiceStreamGrpc.WorkflowServiceStreamStub stub;

    private StreamObserver<OrkesWorkflowService.StartWorkflowRequest> requestStream;

    public GrpcWorkflowClient(ApiClient apiClient) {
        stub =
                WorkflowServiceStreamGrpc.newStub(getChannel(apiClient))
                        .withInterceptors(new HeaderClientInterceptor(apiClient));
        requestStream = stub.startWorkflow(new StartWorkflowResponseStream());
    }

    public CompletableFuture<WorkflowRun> executeWorkflow(
            StartWorkflowRequest startWorkflowRequest, String waitUntilTask) {
        String requestId = UUID.randomUUID().toString();
        startWorkflowRequest.getInput().put("_X-request-id", requestId);

        OrkesWorkflowService.StartWorkflowRequest.Builder requestBuilder =
                OrkesWorkflowService.StartWorkflowRequest.newBuilder();
        requestBuilder
                .setRequestId(requestId)
                .setIdempotencyKey(requestId)
                .setMonitor(true)
                .setWaitUntilTask(waitUntilTask)
                .build();
        try {
            requestStream.onNext(requestBuilder.build());
        } catch (Throwable t) {
            log.error("Error starting a workflow {}", t.getMessage(), t);
        }

        CompletableFuture future = new CompletableFuture<>();
        future.complete(new WorkflowRun());
        return future;
    }
}
