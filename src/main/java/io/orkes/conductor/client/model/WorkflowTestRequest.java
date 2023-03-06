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
package io.orkes.conductor.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class WorkflowTestRequest extends StartWorkflowRequest {

    //Map of task reference name to mock output for the task
    private Map<String, List<TaskMock>> taskRefToMockOutput = new HashMap<>();

    //If there are sub-workflows inside the workflow
    //The map of task reference name to the mock for the sub-workflow
    private Map<String, WorkflowTestRequest> subWorkflowTestRequest = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskMock {
        private TaskResult.Status status = TaskResult.Status.COMPLETED;
        private Map<String, Object> output = new HashMap<>();
        //Time in millis for the execution of the task.
        //Set this value to view the execution timeline view and also trigger any timeouts associated with the tasks
        private long executionTime;

        private long queueWaitTime;

        public TaskMock(TaskResult.Status status, Map<String, Object> output) {
            this.status = status;
            this.output = output;
        }

        public TaskMock(Task.Status status, Map<String, Object> output, long executionTime, long queueWaitTime) {
            try {
                this.status = TaskResult.Status.valueOf(status.name());
            } catch (IllegalArgumentException invalidValue) {
                this.status = TaskResult.Status.IN_PROGRESS;
            }
            this.output = output;
            this.executionTime = executionTime;
            this.queueWaitTime = queueWaitTime;
        }
    }

}
