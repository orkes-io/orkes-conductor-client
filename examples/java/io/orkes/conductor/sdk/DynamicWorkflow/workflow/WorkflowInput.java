package io.orkes.conductor.sdk.DynamicWorkflow.workflow;

public class WorkflowInput {
    private String userId;
    public WorkflowInput(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }    
}


