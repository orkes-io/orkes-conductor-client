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
import java.util.concurrent.CountDownLatch;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.grpc.workflow.GrpcWorkflowClient;
import io.orkes.conductor.client.model.WorkflowRun;

public class Main {

    static String key = "f55005c1-9110-464b-b4e6-ad53bedcad79";
    static String secret = "mxukrZPTncrLahBD7yqmYLt2cNTAJi1Y683UKu79KgmynXux";

    public static void mainxx(String[] args) {
        ApiClient client = new ApiClient("http://localhost:8080/api");

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
        ApiClient apiClient =
                new ApiClient("https://orkes-loadtest.orkesconductor.com/api", key, secret);
        apiClient.setUseGRPC("orkes-loadtest-grpc.orkesconductor.com", 443);
        apiClient.setUseSSL(true);

        GrpcWorkflowClient client = new GrpcWorkflowClient(apiClient);
        int count = 1;
        CountDownLatch latch = new CountDownLatch(count);
        long s = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {

            Map<String, Object> input = new HashMap<>();
            input.put("key", UUID.randomUUID().toString());

            StartWorkflowRequest request = new StartWorkflowRequest();
            request.setName("load_test");
            request.setVersion(1);
            if (i == 2) {
                // request.setVersion(10);
            }
            request.setInput(input);
            try {
                CompletableFuture<WorkflowRun> future = client.executeWorkflow(request, null);
                future.thenAccept(
                                workflowRun -> {
                                    System.out.println(
                                            "time: "
                                                    + (workflowRun.getUpdateTime()
                                                            - workflowRun.getCreateTime()));
                                    latch.countDown();
                                })
                        .exceptionally(
                                error -> {
                                    System.out.println("Error " + error);
                                    latch.countDown();
                                    return null;
                                });
            } catch (Throwable t) {
                System.out.println("Error " + t.getMessage());
            }
        }
        System.out.println(
                "Time to to submit "
                        + count
                        + " execution requests: "
                        + (System.currentTimeMillis() - s));
        latch.await();
        long time = (System.currentTimeMillis() - s);
        System.out.println(
                "Time to to submit AND receive "
                        + count
                        + " execution requests: "
                        + time
                        + ", req/sec= "
                        + ((double) count) / time);
    }

    private static class MyWorker implements Worker {

        @Override
        public String getTaskDefName() {
            return "test";
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
