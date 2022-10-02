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

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import com.netflix.conductor.client.model.WorkflowRun;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.proto.WorkflowRunProtoMapper;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.grpc.service.WorkflowServicePb;

public class WorkflowMonitor {
    private Map<String, CompletableFuture<WorkflowRun>> runningWorkflowFutures =
            new ConcurrentHashMap<>();

    private static WorkflowMonitor instance = new WorkflowMonitor();

    private final WorkflowRunProtoMapper protoMapper =
            new WorkflowRunProtoMapper(new ObjectMapperProvider().getObjectMapper());

    private WorkflowMonitor() {}

    public static WorkflowMonitor getInstance() {
        return instance;
    }

    CompletableFuture<WorkflowRun> add(String requestId) {
        CompletableFuture<WorkflowRun> future = new CompletableFuture<>();
        runningWorkflowFutures.put(requestId, future);
        return future;
    }

    public void notifyCompletion(WorkflowServicePb.ExecuteWorkflowResponse response) {
        String requestId = response.getRequestId();
        CompletableFuture<WorkflowRun> future = runningWorkflowFutures.get(requestId);
        if (future == null) {
            return;
        }
        if (response.hasError()) {
            ApiException exception =
                    new ApiException(
                            response.getError().getCode(), response.getError().getMessage());
            future.completeExceptionally(exception);
        } else {
            future.complete(protoMapper.fromProto(response.getWorkflow()));
        }
        runningWorkflowFutures.remove(requestId);
    }
}
