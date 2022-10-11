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
import java.util.concurrent.TimeUnit;

import io.orkes.conductor.common.model.WorkflowRun;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class WorkflowExecutionMonitor {

    private final Cache<String, CompletableFuture<WorkflowRun>> pendingExecutions;

    public WorkflowExecutionMonitor() {
        this.pendingExecutions =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .concurrencyLevel(100)
                        .build();
    }

    CompletableFuture<WorkflowRun> monitorRequest(String requestId) {
        CompletableFuture<WorkflowRun> future = new CompletableFuture<>();
        pendingExecutions.put(requestId, future);
        return future;
    }

    CompletableFuture<WorkflowRun> getFuture(String requestId) {
        return pendingExecutions.getIfPresent(requestId);
    }
}
