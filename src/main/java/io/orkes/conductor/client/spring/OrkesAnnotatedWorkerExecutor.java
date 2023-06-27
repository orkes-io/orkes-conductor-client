/*
 * Copyright 2023 Orkes, Inc.
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

import com.netflix.conductor.sdk.workflow.executor.task.AnnotatedWorkerExecutor;
import com.netflix.conductor.sdk.workflow.executor.task.WorkerConfiguration;

import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrkesAnnotatedWorkerExecutor extends AnnotatedWorkerExecutor {

    private TaskClient taskClient;

    private TaskRunnerConfigurer taskRunner;

    public OrkesAnnotatedWorkerExecutor(TaskClient taskClient, WorkerConfiguration workerConfiguration) {
        super(taskClient, workerConfiguration);
        this.taskClient = taskClient;
    }


    @Override
    public void shutdown() {
        if(this.taskRunner != null) {
            this.taskRunner.shutdown();
        }
    }

    @Override
    public void startPolling() {

        if (executors.isEmpty()) {
            return;
        }

        log.info("Starting workers with threadCount {}", workerToThreadCount);
        log.info("Worker domains {}", workerDomains);

        this.taskRunner = new TaskRunnerConfigurer.Builder(this.taskClient, executors)
                .withTaskThreadCount(workerToThreadCount)
                .withTaskToDomain(workerDomains)
                .withTaskPollTimeout(5)
                .build();

        taskRunner.init();

    }
}
