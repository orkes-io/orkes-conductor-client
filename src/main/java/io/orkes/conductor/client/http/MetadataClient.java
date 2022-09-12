package io.orkes.conductor.client.http;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import java.util.List;

public interface MetadataClient {
    void registerWorkflowDef(WorkflowDef workflowDef);

    void updateWorkflowDefs(List<WorkflowDef> workflowDefs);

    WorkflowDef getWorkflowDef(String name, Integer version);

    void unregisterWorkflowDef(String name, Integer version);

    void registerTaskDefs(List<TaskDef> taskDefs);

    void updateTaskDef(TaskDef taskDef);

    TaskDef getTaskDef(String taskType);

    void unregisterTaskDef(String taskType);
}
