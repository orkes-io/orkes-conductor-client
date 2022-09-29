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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.grpc.ProtoMapper;
import com.netflix.conductor.proto.StartWorkflowRequestPb;
import com.netflix.conductor.proto.WorkflowModelProtoMapper;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.model.WorkflowRun;
import io.orkes.grpc.service.WorkflowServiceGrpc;

import io.grpc.stub.StreamObserver;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

public class GrpcWorkflowClient {

    private static ScheduledExecutorService channelMonitor = Executors.newScheduledThreadPool(1);

    private final WorkflowServiceGrpc.WorkflowServiceStub stub;

    private final RequestStreamObserver responses;

    private StreamObserver<StartWorkflowRequestPb.StartWorkflowRequest> requests;

    private WorkflowModelProtoMapper protoMapper;

    private final WorkflowMonitor workflowMonitor = WorkflowMonitor.getInstance();

    public GrpcWorkflowClient(ApiClient apiClient) {
        ManagedChannel channel = getChannel(apiClient);
        this.stub =
                WorkflowServiceGrpc.newStub(channel)
                        .withCallCredentials(new AuthToken(apiClient));
        this.protoMapper =
                new WorkflowModelProtoMapper(new ObjectMapperProvider().getObjectMapper());
        this.responses = new RequestStreamObserver(protoMapper);
        this.requests = this.stub.executeWorkflow(responses);
    }

    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request) {
        String requestId = UUID.randomUUID().toString();
        request.getInput().put("_x-request-id", requestId);
        StartWorkflowRequestPb.StartWorkflowRequest requestPb =
                ProtoMapper.INSTANCE.toProto(request);
        CompletableFuture<WorkflowRun> future = workflowMonitor.add(requestId);
        requests.onNext(requestPb);
        return future;
    }
}
