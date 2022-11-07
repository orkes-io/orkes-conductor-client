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

import java.util.concurrent.CompletableFuture;

import com.netflix.conductor.common.config.ObjectMapperProvider;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.common.model.WorkflowRun;
import io.orkes.conductor.proto.WorkflowRunProtoMapper;
import io.orkes.grpc.service.OrkesWorkflowService;

import io.grpc.Status;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartWorkflowResponseStream  implements ClientResponseObserver<OrkesWorkflowService.StartWorkflowRequest, OrkesWorkflowService.StartWorkflowResponse> {

    private final WorkflowExecutionMonitor executionMonitor;

    private final WorkflowRunProtoMapper protoMapper;

    private ClientCallStreamObserver<OrkesWorkflowService.StartWorkflowRequest> requestStream;

    public StartWorkflowResponseStream(WorkflowExecutionMonitor executionMonitor) {
        this.executionMonitor = executionMonitor;
        this.protoMapper = new WorkflowRunProtoMapper(new ObjectMapperProvider().getObjectMapper());
    }

    @Override
    public void onNext(OrkesWorkflowService.StartWorkflowResponse response) {

        try {

            CompletableFuture<WorkflowRun> future =
                    this.executionMonitor.getFuture(response.getRequestId());
            if (future == null) {
                log.warn("No waiting client for the request {}", response.getRequestId());
                return;
            }
            if (response.hasError()) {
                String message = response.getError().getMessage();
                int code = response.getError().getCode();
                future.completeExceptionally(new ApiException(code, message));
            } else {
                WorkflowRun workflowRun = protoMapper.fromProto(response.getWorkflow());
                future.complete(workflowRun);
            }

        } catch (Throwable t) {
            log.error("Error while trying to notify the client {}", t.getMessage(), t);
        }
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case UNAVAILABLE:
            case ABORTED:
            case INTERNAL:
            case UNKNOWN:
                log.error("Received an error from the server {}-{}", code, t.getMessage());
                break;
            case CANCELLED:
                log.info("Server cancelled");       //TODO: move this to trace
            default:
                log.warn("Server Error {} - {}", code, t.getMessage(), t);
        }
    }

    public boolean isReady() {
        return requestStream.isReady();
    }

    @Override
    public void onCompleted() {
        log.info("Completed....");
    }

    @Override
    public void beforeStart(ClientCallStreamObserver<OrkesWorkflowService.StartWorkflowRequest> requestStream) {
        this.requestStream = requestStream;
    }
}
