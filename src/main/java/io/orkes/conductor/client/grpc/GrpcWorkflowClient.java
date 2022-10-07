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

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.model.WorkflowRun;

public class GrpcWorkflowClient {

    public GrpcWorkflowClient(ApiClient apiClient) {}

    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request) {
        String requestId = UUID.randomUUID().toString();
        request.getInput().put("_X-request-id", requestId);
        CompletableFuture future = new CompletableFuture<>();
        future.complete(new WorkflowRun());
        return future;
    }
}
