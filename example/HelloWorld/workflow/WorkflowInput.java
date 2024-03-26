package io.orkes.samples.quickstart.workflow;
public class WorkflowInput {
    private String name;
    public WorkflowInput(String name) {
        this.name = name;
    }
    public String getName() {
            return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}