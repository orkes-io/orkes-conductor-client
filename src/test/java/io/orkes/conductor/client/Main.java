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
package io.orkes.conductor.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.client.model.WorkflowRun;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.grpc.GrpcWorkflowClient;

import com.google.common.util.concurrent.Uninterruptibles;
import io.orkes.conductor.proto.WorkflowRunPb;

public class Main {

    static String key = "a6b4f97c-2f91-4223-9374-cf7d29a0072e";
    static String secret = "yudMp8M5o6a4282Ihom6q7QEpjSS6sNfv0b2twuCoqoFAWnw";

    public static void main3(String[] args) {
        ApiClient client = new ApiClient("http://localhost:8080/api", key, secret);
        client.setUseGRPC(true);

        OrkesClients clients = new OrkesClients(client);
        TaskClient taskClient = clients.getTaskClient();
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(new MyWorker()))
                        .withThreadCount(100)
                        .withSleepWhenRetry(10)
                        .build();
        configurer.init();
    }

    public static void main(String[] args) throws InterruptedException {
        ApiClient apiClient = new ApiClient("http://localhost:8080/api", key, secret);
        apiClient.setUseGRPC(true);

        GrpcWorkflowClient client = new GrpcWorkflowClient(apiClient);
        while (true) {

            Map<String, Object> input = new HashMap<>();
            input.put("key", UUID.randomUUID().toString());

            StartWorkflowRequest request = new StartWorkflowRequest();
            request.setName("http");
            request.setVersion(1);
            request.setInput(input);
            try {
                CompletableFuture<WorkflowRun> future = client.executeWorkflow(request);
                future.thenAccept(
                        workflowRun -> {
                            System.out.println(
                                    "Completed "
                                            + workflowRun.getWorkflowId()
                                            + ":"
                                            + workflowRun.getStatus());
                        });
            } catch (Throwable t) {
                System.out.println("Error " + t.getMessage());
                // t.printStackTrace();
            }

            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
        }
    }

    private static class MyWorker implements Worker {

        @Override
        public String getTaskDefName() {
            return "task_1";
        }

        @Override
        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult();
            result.setStatus(TaskResult.Status.COMPLETED);
            result.getOutputData().put("a", "b");
            result.getOutputData().put("nullKey", null);
            return result;
        }
    }
}
