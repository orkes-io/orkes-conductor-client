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

import com.netflix.conductor.client.worker.Worker;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteWorkerTest {

    static String key = "a6fc2f18-2254-44b0-86ff-f95784493f97";
    static String secret = "srpOd6DyslEA42lZuBuPwPFAe3kFhxnn0iBvJcaFjwFxawds";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("https://orkes-loadtest.orkesconductor.com/api", key, secret);
        apiClient.setUseGRPC("orkes-loadtest-grpc.orkesconductor.com", 443);
        apiClient.setUseSSL(true);

        OrkesClients clients = new OrkesClients(apiClient);
        TaskClient taskClient = clients.getTaskClient();

        List<Worker> workers = new ArrayList<>();
        Map<String, Integer> taskThreadCount = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            workers.add(new LoadTestWorker("x_test_worker_" + i));
            taskThreadCount.put("x_test_worker_" + i, 10);
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
