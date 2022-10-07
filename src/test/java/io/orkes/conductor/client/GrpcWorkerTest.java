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

import java.util.concurrent.TimeUnit;

import com.netflix.conductor.client.telemetry.MetricsContainer;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import io.orkes.conductor.client.grpc.GrpcTaskWorker;

import com.google.common.util.concurrent.Uninterruptibles;

public class GrpcWorkerTest {

    static String key = "a0b92def-ea61-464e-a51c-b05bcc04ec51";
    static String secret = "YNGLncaOWsv4nQIeO71LGGv77sM5iQCRjH5FNkfOS9Jfi6G5";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setUseGRPC("localhost", 8090);
        int threadCount = 10;

        for (int i = 0; i < 7; i++) {
            Worker worker = new LoadTestWorker("x_test_worker_" + i);
            GrpcTaskWorker taskWorker = new GrpcTaskWorker(apiClient, worker, null, threadCount, 1);
            taskWorker.init();
        }
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.DAYS);
        System.out.println("Ready...");
    }

    private static final Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
            (thread, error) -> {
                // JVM may be in unstable state, try to send metrics then exit
                MetricsContainer.incrementUncaughtExceptionCount();
                System.out.println("Uncaught exception. Thread {} will exit now" + thread + error);
            };

    private static class MyWorker implements Worker {

        private final String name;

        private MyWorker(String name) {
            this.name = name;
        }

        @Override
        public String getTaskDefName() {
            return name;
        }

        @Override
        public int getPollingInterval() {
            return 1;
        }

        @Override
        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult(task);
            result.setStatus(TaskResult.Status.COMPLETED);
            result.getOutputData().put("a", "b");
            result.getOutputData().put("nullKey", null);
            return result;
        }
    }
}
