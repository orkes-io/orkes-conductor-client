package io.orkes.conductor.client.model;

import java.util.Map;

public class JumpWorkflowExecutionRequest {

    public Map<String, Object> getSkippedTasksOutput() {
        return skippedTasksOutput;
    }

    public void setSkippedTasksOutput(Map<String, Object> skippedTasksOutput) {
        this.skippedTasksOutput = skippedTasksOutput;
    }

    public Map<String, Object> getJumpTaskInput() {
        return jumpTaskInput;
    }

    public void setJumpTaskInput(Map<String, Object> jumpTaskInput) {
        this.jumpTaskInput = jumpTaskInput;
    }

    public String getTaskReferenceName() {
        return taskReferenceName;
    }

    public void setTaskReferenceName(String taskReferenceName) {
        this.taskReferenceName = taskReferenceName;
    }
    private Map<String, Object> skippedTasksOutput;

    private Map<String, Object> jumpTaskInput;

    private String taskReferenceName;

}
