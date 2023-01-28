package io.orkes.conductor.client.model;

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Queue;

@Data
@Slf4j
public class WorkflowTestRequest extends StartWorkflowRequest {

    //Map of task reference name to mock output for the task
    private Map<String, List<TaskMock>> taskRefToMockOutput;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskMock {
        private TaskResult.Status status = TaskResult.Status.COMPLETED;
        Map<String, Object> output;
    }

}
