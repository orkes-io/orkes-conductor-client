package io.orkes.conductor.client.util;

import java.util.Arrays;

import io.orkes.conductor.client.http.model.WorkflowDef;
import io.orkes.conductor.client.http.model.WorkflowTask;

public class WorkflowUtil {
    public static WorkflowDef getWorkflowDef() {
        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(Commons.WORKFLOW_NAME);
        workflowDef.setVersion(1);
        workflowDef.setOwnerEmail("test@orkes.io");
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName(Commons.TASK_NAME);
        workflowTask.setTaskReferenceName(Commons.TASK_NAME);
        workflowDef.setTasks(Arrays.asList(workflowTask));
        return workflowDef;
    }
}
