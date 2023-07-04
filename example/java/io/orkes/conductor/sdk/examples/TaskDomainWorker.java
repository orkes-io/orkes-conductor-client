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
package io.orkes.conductor.sdk.examples;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.executor.task.WorkerConfiguration;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.spring.OrkesAnnotatedWorkerExecutor;


public class TaskDomainWorker {
    // This program demonstrates different mechanisms for setting task domains during worker initialization
    // 1. Using task specific property in application.properties
    // 2. Using task agnostic property in application.properties
    // 3. Passing taskToDomains as constructor parameter when using TaskRunner
    // 4. Passing domain argument when using worker annotator

    // Sample workflow can be created using the definition task_domain_wf.json in the resources folder

    // Example Task to Domain Mapping for workflow creation when property, conductor.worker.all.domain, is set
    //{
    //    "task-domain-property-simple-task": "test-domain-prop",
    //    "task-domain-all-simple-task": "test-domain-common",
    //    "task-domain-runner-simple-task": "test-domain-common",
    //    "task-domain-annotated-simple-task": "test-domain-common"
    //}
    // Example Task to Domain Mapping for workflow creation when property, conductor.worker.all.domain, is not set
    //{
    //    "task-domain-property-simple-task": "test-domain-prop",
    //    "task-domain-runner-simple-task": "test-domain-runner",
    //    "task-domain-annotated-simple-task": "test-domain-annotated",
    //}

    public static void main(String[] args) throws IOException {
        setSystemProperties();

        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        OrkesClients orkesClients = new OrkesClients(apiClient);
        OrkesTaskClient taskClient = (OrkesTaskClient) orkesClients.getTaskClient();

        OrkesAnnotatedWorkerExecutor workerExecutor = new OrkesAnnotatedWorkerExecutor(
                taskClient, new WorkerConfiguration()
        );
        workerExecutor.initWorkers("io.orkes.conductor.sdk.examples");
        workerExecutor.startPolling();
        workerExecutor.shutdown();

        startTaskRunnerWorkers(taskClient);
    }

    @WorkerTask(value="task-domain-property-simple-task", pollingInterval = 200)
    public TaskResult sendPropertyTaskDomain(Task task) {
        // test-domain-prop should be picked up as Task Domain
        TaskResult result = new TaskResult(task);

        result.getOutputData().put("key", "value2");
        result.getOutputData().put("amount", 167.12);
        result.setStatus(TaskResult.Status.COMPLETED);

        return result;
    }

    @WorkerTask(value="task-domain-all-simple-task", pollingInterval = 200)
    public TaskResult sendAllTaskDomain(Task task) {
        // test-domain-common should be picked up as Task Domain
        TaskResult result = new TaskResult(task);
        result.getOutputData().put("key", "value3");
        result.getOutputData().put("amount", 400);
        result.setStatus(TaskResult.Status.COMPLETED);

        return result;
    }

    @WorkerTask(value="task-domain-annotated-simple-task", domain="test-domain-annotated", pollingInterval = 200)
    public TaskResult sendAnnotatedTaskDomain(Task task) {
        // test-domain-common should be picked up as Task Domain if conductor.worker.all.domain is populated
        // For test-domain-annotated to be picked up, conductor.worker.all.domain shouldn't be populated
        TaskResult result = new TaskResult(task);

        result.getOutputData().put("key", "value");
        result.getOutputData().put("amount", 123.45);
        result.setStatus(TaskResult.Status.COMPLETED);

        return result;
    }

    private static void startTaskRunnerWorkers(TaskClient taskClient) {
        List<Worker> workers = Arrays.asList(new TaskWorker());
        TaskRunnerConfigurer.Builder builder = new TaskRunnerConfigurer.Builder(taskClient, workers);

        // test-domain-common should be picked up as Task Domain if conductor.worker.all.domain is populated
        // For test-domain-runner to be picked up, conductor.worker.all.domain shouldn't be populated
        Map<String, String> taskToDomains = new HashMap<>();
        taskToDomains.put("task-domain-runner-simple-task", "test-domain-runner");
        Map<String, Integer> taskThreadCount = new HashMap<>();

        TaskRunnerConfigurer taskRunner =
                builder.withThreadCount(2)
                        .withTaskToDomain(taskToDomains)
                        .withTaskThreadCount(taskThreadCount)
                        .withSleepWhenRetry(500)
                        .withWorkerNamePrefix("task-domain")
                        .withUpdateRetryCount(3)
                        .build();

        // Start Polling for tasks and execute them
        taskRunner.init();

        // Optionally, use the shutdown method to stop polling
        taskRunner.shutdown();
    }

    private static class TaskWorker implements Worker {

        @Override
        public String getTaskDefName() {
            return "task-domain-runner-simple-task";
        }

        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult(task);

            result.getOutputData().put("key2", "value2");
            result.getOutputData().put("amount", 145);
            result.setStatus(TaskResult.Status.COMPLETED);

            return result;
        }
    }

    public static void setSystemProperties() {
        // This is in lieu of setting properties in application.properties
        System.setProperty("conductor.worker.task-domain-property-simple-task.domain", "test-domain-prop");
        // If below line is un-commented, test-domain-common trumps test-domain-runner and test-domain-annotated
        // System.setProperty("conductor.worker.all.domain", "test-domain-common");
    }
}
