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
package io.orkes.conductor.client.api;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiClientTests {
    private static Worker WORKER;

    static {
        WORKER = new SherlockWorker();
    }

    @Test
    public void testApiClientWithUnknownHost() throws Exception {
        final int timeout = 5;
        ApiClient apiClient = new ApiClient("https://unknown_host:1234");
        // apiClient.setReadTimeout(timeout);
        // apiClient.setWriteTimeout(timeout);
        apiClient.setConnectTimeout(timeout);
        OrkesClients orkesClients = new OrkesClients(apiClient);
        TaskClient taskClient = orkesClients.getTaskClient();
        TaskRunnerConfigurer taskRunnerConfigurer = new TaskRunnerConfigurer.Builder(
                taskClient,
                Collections.singletonList(WORKER))
                .withTaskThreadCount(Map.of(WORKER.getTaskDefName(), 1))
                .withTaskPollTimeout(50)
                .build();
        taskRunnerConfigurer.init();
        Thread.sleep(1000L * 5);
        taskRunnerConfigurer.shutdown();
        for (Integer pollIterations : taskRunnerConfigurer.getPollIterationsList()) {
            assertTrue(pollIterations > 5);
        }
    }
}

class SherlockWorker implements Worker {
    @Override
    public String getTaskDefName() {
        return "buggy_task";
    }

    @Override
    public TaskResult execute(Task task) {
        task.setStatus(Task.Status.COMPLETED);
        task.getOutputData().put("assistant", "watson");
        return new TaskResult(task);
    }
}