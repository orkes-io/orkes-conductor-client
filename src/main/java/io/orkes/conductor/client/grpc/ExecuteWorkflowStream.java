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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Uninterruptibles;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.grpc.ProtoMapper;
import com.netflix.conductor.proto.StartWorkflowRequestPb;
import com.netflix.conductor.proto.WorkflowModelProtoMapper;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.proto.WorkflowRun;
import io.orkes.grpc.service.WorkflowServiceGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

@Slf4j
public class ExecuteWorkflowStream implements StreamObserver<WorkflowRun> {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().getObjectMapper();
    private final WorkflowModelProtoMapper protoMapper;

    private final WorkflowServiceGrpc.WorkflowServiceStub stub;

    private final WorkflowMonitor workflowMonitor = WorkflowMonitor.getInstance();

    private final ApiClient apiClient;
    private final HeaderClientInterceptor headerInterceptor;
    private StreamObserver<StartWorkflowRequestPb.StartWorkflowRequest> requests;

    private volatile boolean ready;

    private int reconnectBackoff = 10;

    public ExecuteWorkflowStream(ApiClient apiClient) {
        this.protoMapper = new WorkflowModelProtoMapper(objectMapper);
        this.apiClient = apiClient;
        this.apiClient.getToken();
        this.headerInterceptor = new HeaderClientInterceptor();
        this.stub = WorkflowServiceGrpc.newStub(getChannel(apiClient)).withInterceptors(headerInterceptor);
        connect();
    }

    private synchronized void connect() {
        log.debug("Attempting to reconnect to the server with backoff {} sec", reconnectBackoff);
        try {
            Uninterruptibles.sleepUninterruptibly(reconnectBackoff, TimeUnit.MILLISECONDS);
            this.requests = this.stub.executeWorkflow(this);
            this.ready = true;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    @Override
    public void onNext(WorkflowRun result) {
        io.orkes.conductor.client.model.WorkflowRun workflowRun = fromProto(result);
        workflowMonitor.notifyCompletion(workflowRun);
    }

    @Override
    public void onError(Throwable t) {
        ready = false;
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        backoff();
        switch (code) {
            case UNAUTHENTICATED:
                try {
                    String token = apiClient.getToken();
                    this.headerInterceptor.setToken(token);
                } catch (Throwable tokenException) {
                    log.error(tokenException.getMessage(), tokenException);
                }
                break;
            case PERMISSION_DENIED:
                log.error("Key/Secret does not have permission to execute the workflow");
                break;
        }
        connect();      //connect on errors
    }

    private void backoff() {
        reconnectBackoff = reconnectBackoff << 1;
        if(reconnectBackoff > 60_000) {
            reconnectBackoff = 1;
        }
    }

    @Override
    public void onCompleted() {
    }

    private io.orkes.conductor.client.model.WorkflowRun fromProto(WorkflowRun proto) {
        io.orkes.conductor.client.model.WorkflowRun run =
                new io.orkes.conductor.client.model.WorkflowRun();
        run.setWorkflowId(proto.getWorkflowId());
        run.setCreatedBy(proto.getCreatedBy());
        run.setCreateTime(proto.getCreateTime());
        run.setPriority(proto.getPriority());
        run.setCorrelationId(proto.getCorrelationId());
        run.setInput(protoMapper.convertToJavaMap(proto.getInput()));
        run.setOutput(protoMapper.convertToJavaMap(proto.getOutput()));
        run.setStatus(Workflow.WorkflowStatus.valueOf(proto.getStatus().name()));
        run.setVariables(protoMapper.convertToJavaMap(proto.getVariables()));
        run.setRequestId(proto.getRequestId());

        return run;
    }

    public void executeWorkflow(StartWorkflowRequest request) {
        if (!ready) {
            backoff();
            throw new ApiException("Server not ready to accept the connection");
        }
        StartWorkflowRequestPb.StartWorkflowRequest requestPb = ProtoMapper.INSTANCE.toProto(request);
        this.requests.onNext(requestPb);
    }
}
