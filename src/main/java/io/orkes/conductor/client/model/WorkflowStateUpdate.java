package io.orkes.conductor.client.model;

import java.util.Map;

import com.netflix.conductor.common.metadata.tasks.TaskResult;

import lombok.Data;

@Data
public class WorkflowStateUpdate {
    private String taskReferenceName;
    private Map<String, Object> variables;
    private TaskResult taskResult;
}
