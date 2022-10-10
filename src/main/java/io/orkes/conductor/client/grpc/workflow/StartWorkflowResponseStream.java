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

import io.orkes.grpc.service.OrkesWorkflowService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartWorkflowResponseStream
        implements StreamObserver<OrkesWorkflowService.StartWorkflowResponse> {

    @Override
    public void onNext(OrkesWorkflowService.StartWorkflowResponse response) {
        log.info("Workflow completed {}", response);
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case UNAVAILABLE:
            case CANCELLED:
            case ABORTED:
                // We should reconnect here
                break;
            case INTERNAL:
            case UNKNOWN:
                log.error("Received an error from the server {}-{}", code, t.getMessage(), t);
                break;
            default:
                log.warn("Server Error {} - {}", code, t.getMessage(), t);
        }
    }

    @Override
    public void onCompleted() {}
}
