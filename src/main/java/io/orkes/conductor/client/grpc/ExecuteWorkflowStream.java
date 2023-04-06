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

import java.util.concurrent.TimeUnit;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.grpc.ProtoMapper;
import com.netflix.conductor.proto.StartWorkflowRequestPb;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.ApiException;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteWorkflowStream {

    private StreamObserver<StartWorkflowRequestPb.StartWorkflowRequest> requests;

    private volatile boolean ready;

    private int reconnectBackoff = 10;

    public ExecuteWorkflowStream(ApiClient apiClient) {
        if (apiClient.useSecurity()) {
            apiClient.getToken();
        }
        connect();
    }

    private synchronized void connect() {
        log.debug("Attempting to reconnect to the server with backoff {} sec", reconnectBackoff);
        backoff();
        try {
            Uninterruptibles.sleepUninterruptibly(reconnectBackoff, TimeUnit.MILLISECONDS);
            // this.requests = this.stub.executeWorkflow(this);
            this.ready = true;
            System.out.println("Ready...");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    // public void onNext(WorkflowServicePb.ExecuteWorkflowResponse result) {}

    // @Override
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
        t.printStackTrace();
        ready = false;
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case PERMISSION_DENIED:
                log.error("Key/Secret does not have permission to execute the workflow");
                break;
        }
        connect(); // connect on errors
    }

    private void backoff() {
        reconnectBackoff = reconnectBackoff << 1;
        if (reconnectBackoff > 60_000) {
            reconnectBackoff = 1;
        }
    }

    // @Override
    public void onCompleted() {}

    public void executeWorkflow(StartWorkflowRequest request) {
        if (!ready) {
            connect();
            throw new ApiException("Server not ready to accept the connection");
        }
        StartWorkflowRequestPb.StartWorkflowRequest requestPb =
                ProtoMapper.INSTANCE.toProto(request);
        this.requests.onNext(requestPb);
    }
}
