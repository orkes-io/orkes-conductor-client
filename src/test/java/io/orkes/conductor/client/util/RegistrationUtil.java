package io.orkes.conductor.client.util;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.SubWorkflowParams;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import io.orkes.conductor.client.MetadataClient;

import java.util.Arrays;
import java.util.Map;

public class RegistrationUtil {

    public static void registerWorkflowDef(String workflowName, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef("inline");
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);
        TaskDef taskDef2 = new TaskDef("simple");
        taskDef2.setOwnerEmail("test@orkes.io");
        taskDef2.setRetryCount(0);


        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName("inline");
        inline.setName("inline");
        inline.setTaskDefinition(taskDef);
        inline.setWorkflowTaskType(TaskType.INLINE);
        inline.setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "true;"));

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName("simple");
        simpleTask.setName("simple");
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);
        simpleTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));


        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(inline, simpleTask));
        metadataClient1.registerWorkflowDef(workflowDef);
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef, taskDef2));
    }

    public static void registerWorkflowWithSubWorkflowDef(String workflowName, String subWorkflowName, MetadataClient metadataClient) {
        TaskDef taskDef = new TaskDef("simple");
        taskDef.setOwnerEmail("test@orkes.io");
        taskDef.setRetryCount(0);


        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName("simple");
        inline.setName("simple");
        inline.setTaskDefinition(taskDef);
        inline.setWorkflowTaskType(TaskType.SIMPLE);
        inline.setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "true;"));

        WorkflowTask subworkflowTask = new WorkflowTask();
        subworkflowTask.setTaskReferenceName("sub_workflow");
        subworkflowTask.setName("sub_workflow");
        subworkflowTask.setTaskDefinition(taskDef);
        subworkflowTask.setWorkflowTaskType(TaskType.SUB_WORKFLOW);
        SubWorkflowParams subWorkflowParams = new SubWorkflowParams();
        subWorkflowParams.setName(subWorkflowName);
        subWorkflowParams.setVersion(1);
        subworkflowTask.setSubWorkflowParam(subWorkflowParams);
        subworkflowTask.setInputParameters(Map.of("subWorkflowName", subWorkflowName, "subWorkflowVersion", "1"));


        WorkflowDef subworkflowDef = new WorkflowDef();
        subworkflowDef.setName(subWorkflowName);
        subworkflowDef.setOwnerEmail("test@orkes.io");
        subworkflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        subworkflowDef.setDescription("Sub Workflow to test retry");
        subworkflowDef.setTasks(Arrays.asList(inline));
        metadataClient.registerWorkflowDef(subworkflowDef);
        metadataClient.registerTaskDefs(Arrays.asList(taskDef));

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test retry");
        workflowDef.setTasks(Arrays.asList(subworkflowTask));
        metadataClient.registerWorkflowDef(workflowDef);
    }

}
