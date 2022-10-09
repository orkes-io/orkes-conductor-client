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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.conductor.client.worker.Worker;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

public class LocalWorkerTest {

    static String key = "6f91984b-ae29-4814-804b-cdda439e0d00";
    static String secret = "a19Rv4oy6XhBdhYQjLYeA7k8ci2ztRBFcsxfT4Lpn5q7KkZM";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("http://localhost:8080/api");
        apiClient.setUseGRPC("localhost", 8090);

        OrkesClients clients = new OrkesClients(apiClient);
        TaskClient taskClient = clients.getTaskClient();

        List<Worker> workers = new ArrayList<>();
        Map<String, Integer> taskThreadCount = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            workers.add(new LoadTestWorker("x_test_worker_" + i));
            taskThreadCount.put("x_test_worker_" + i, 100);
        }

        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, workers)
                        .withSleepWhenRetry(1)
                        .withTaskThreadCount(taskThreadCount)
                        .withTaskPollTimeout(1)
                        .build();
        configurer.init();

        System.out.println("Ready...");
    }
}
