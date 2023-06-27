/*
 * Copyright 2020 Orkes, Inc.
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
package io.orkes.conductor.client.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.executor.task.WorkerConfiguration;

import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ExampleClient {

    public static void main(String[] args) {
        SpringApplication.run(ExampleClient.class, args);
    }

    @Bean
    public TaskRunnerConfigurer taskRunnerConfigurer(TaskClient taskClient, List<Worker> workers,
                                                     WorkerConfiguration configuration) {
        Map<String, String> taskToDomain = new HashMap<>();
        Map<String, Integer> taskToThreads = new HashMap<>();
        for (Worker worker : workers) {
            String taskType = worker.getTaskDefName();
            String domain = configuration.getDomain(taskType);
            if(StringUtils.isNotBlank(domain)) {
                taskToDomain.put(taskType, domain);
            }
            int threadCount = configuration.getThreadCount(taskType);
            if(threadCount > 0) {
                taskToThreads.put(taskType, threadCount);
            }
        }
        TaskRunnerConfigurer trc = new TaskRunnerConfigurer.Builder(taskClient, workers)
                .withThreadCount(1)
                .withTaskThreadCount(taskToThreads)
                .withTaskToDomain(taskToDomain)
                .build();
        trc.init();
        return trc;
    }

    @Bean
    public Worker worker() {
        return new Worker() {
            @Override
            public String getTaskDefName() {
                return "xyz";
            }

            @Override
            public TaskResult execute(Task task) {
                log.info("Execute...");
                TaskResult result = new TaskResult(task);
                result.setStatus(TaskResult.Status.COMPLETED);
                return result;
            }
        };
    }
}
