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
package io.orkes.conductor.sdk.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.run.Workflow;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;

public class Test {

    public static void main(String[] args) {

        ApiClient apiCient = new ApiClient("http://localhost:8080/api");
        apiCient.setReadTimeout(600_000);
        OrkesClients orkesClients = new OrkesClients(apiCient);
        WorkflowClient workflowClient = orkesClients.getWorkflowClient();
        Workflow workflow =
                workflowClient.getWorkflow("f70f12a7-3851-11ed-a091-0242ac110002", true);
        Map<Task.Status, Long> byStatus =
                workflow.getTasks().stream()
                        .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
        System.out.println(byStatus);
        System.out.println(workflow.getStatus() + "," + workflow.getReasonForIncompletion());
        Map<String, Integer> counts = new HashMap<>();
        for (Task task : workflow.getTasks()) {
            String key = task.getReferenceTaskName();
            int count = counts.getOrDefault(key, 0);
            count++;
            counts.put(key, count);
        }

        Set<Task> tasks =
                workflow.getTasks().stream()
                        .filter(
                                task ->
                                        task.getTaskId()
                                                .equals("7e08586b-37d2-11ed-bf01-0242ac110002"))
                        .collect(Collectors.toSet());
        System.out.println("here: " + tasks);
        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            if (e.getValue() > 1) {
                System.out.println(e.getKey() + ", got executed " + e.getValue() + " times.");
            }
        }
        Set<Task> dups =
                workflow.getTasks().stream()
                        .filter(
                                t ->
                                        t.getReferenceTaskName()
                                                .equals("5a974bc5-9632-4bab-b8cc-ffcbb530d7b7"))
                        .collect(Collectors.toSet());
        for (Task dup : dups) {
            System.out.println(
                    "dup "
                            + dup.getTaskId()
                            + ","
                            + dup.getReferenceTaskName()
                            + ","
                            + dup.getStatus()
                            + ","
                            + dup.getRetryCount());
        }
    }
}
