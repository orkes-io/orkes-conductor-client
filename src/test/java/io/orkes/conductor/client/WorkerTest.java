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
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkerTest {

    static String key = "a6b4f97c-2f91-4223-9374-cf7d29a0072e";
    static String secret = "yudMp8M5o6a4282Ihom6q7QEpjSS6sNfv0b2twuCoqoFAWnw";

    public static void main(String[] args) {
        ApiClient client = new ApiClient("http://localhost:8080/api");
        client.setUseGRPC(true);

        OrkesClients clients = new OrkesClients(client);
        TaskClient taskClient = clients.getTaskClient();
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            workers.add(new MyWorker("simple_task_" + i));
        }

        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, workers)
                        .withThreadCount(100)
                        .withSleepWhenRetry(10)
                        .build();
        configurer.init();
    }




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
        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult();
            result.setStatus(TaskResult.Status.COMPLETED);
            result.getOutputData().put("a", "b");
            result.getOutputData().put("nullKey", null);
            return result;
        }
    }
}
