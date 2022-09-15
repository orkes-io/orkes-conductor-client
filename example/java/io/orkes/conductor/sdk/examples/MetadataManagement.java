package io.orkes.conductor.sdk.examples;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.model.TagObject;

import java.util.Arrays;
import java.util.Map;

/* This example covers following apis
1. registerWorkflowDef - Register workflow definition
2. registerTaskDefs - Register task definition
3. addTaskTag - Add tag to taks
4. addWorkflowTag - Add tag to workflow
*/
public class MetadataManagement {

    public static String taskName = "test_task";
    public static String taskName2 = "test_task1";
    public static String taskName3 = "test_task2";
    private static TaskDef taskDef;
    private static TaskDef taskDef2;
    private static TaskDef taskDef3;
    public static WorkflowDef workflowDef;

    private static MetadataClient metadataClient;

    public static void main(String[] args) {
        MetadataManagement metadataManagement = new MetadataManagement();
        metadataManagement.createTaskDefinitions();
        metadataManagement.tagTasks();
        metadataManagement.createWorkflowDefinitions();
        metadataManagement.tagWorkflow();
    }

    public void createTaskDefinitions() {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        metadataClient = orkesClients.getMetadataClient();

        // Create task definitions
        taskDef = new TaskDef(taskName, "task to update database", "test@orkes.io",
                3,4,10);

        taskDef2 = new TaskDef();
        taskDef2.setName(taskName2);
        taskDef2.setDescription("task to notify users");
        taskDef2.setOwnerEmail("test@orkes.io");
        taskDef2.setResponseTimeoutSeconds(10);
        taskDef2.setRetryCount(3);
        // At any given time, max 10 executions of this task will be allowed. Tasks to be scheduled after reaching max
        // limit will be put into queue.
        // For more information https://orkes.io/content/docs/how-tos/Tasks/creating-tasks#task-rate-limits
        taskDef2.setConcurrentExecLimit(10);
        taskDef2.setInputKeys(Arrays.asList("${workflow.input.value}", "${some_other_task.output.response.data.value}"));

        taskDef3 = new TaskDef();
        taskDef3.setName(taskName3);
        taskDef3.setDescription("task to compress image");
        taskDef3.setOwnerEmail("test@orkes.io");
        // In 60 seconds at max 5 execution of this task wil be allowed.
        // For more information https://orkes.io/content/docs/how-tos/Tasks/creating-tasks#task-rate-limits
        taskDef3.setRateLimitPerFrequency(5);
        taskDef3.setRateLimitFrequencyInSeconds(60);

        // Register task definitions
        metadataClient.registerTaskDefs(Arrays.asList(taskDef, taskDef2, taskDef3));
    }

    public void tagTasks() {
        // Tagging a task
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("department");
        tagObject.setValue("accounts");
        metadataClient.addTaskTag(tagObject, taskName);

        //Tagging another task
        TagObject tagObject2 = new TagObject();
        tagObject2.setType(TagObject.TypeEnum.METADATA);
        tagObject2.setKey("department");
        tagObject2.setValue("engineering");
        metadataClient.addTaskTag(tagObject2, taskName2);

        //Tagging another task
        TagObject tagObject3 = new TagObject();
        tagObject3.setType(TagObject.TypeEnum.METADATA);
        tagObject3.setKey("env");
        tagObject3.setValue("dev");
        metadataClient.addTaskTag(tagObject3, taskName3);
    }

    public void createWorkflowDefinitions() {
        // Create workflowTask
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setTaskReferenceName(taskName);
        workflowTask.setTaskReferenceName(taskName);
        workflowTask.setWorkflowTaskType(TaskType.SIMPLE);
        workflowTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));
        workflowTask.setTaskDefinition(taskDef);

        // Create INLINE workflowTask
        WorkflowTask workflowTask2 = new WorkflowTask();
        workflowTask2.setTaskReferenceName(taskName);
        workflowTask2.setTaskReferenceName(taskName);
        workflowTask2.setWorkflowTaskType(TaskType.INLINE);
        workflowTask2.setInputParameters(Map.of("inlineValue", "${workflow.input.inlineValue}",
        "evaluatorType", "javascript", "expression" , "function scriptFun(){if ($.inlineValue == 1){ " +
        "return {testvalue: true} } else { return {testvalue: false} }} scriptFun();"));
        workflowTask2.setTaskDefinition(taskDef2);

        workflowDef = new WorkflowDef();
        workflowDef.setName("test_workflow");
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTasks(Arrays.asList(workflowTask, workflowTask2));
        metadataClient.registerWorkflowDef(workflowDef);
    }

    public void tagWorkflow() {
        TagObject tagObject = new TagObject();
        // Workflow level rate limit. At max 5 instance of workflow will be getting executed that are triggered with
        // same correlationId.
        // For more information https://orkes.io/content/docs/how-tos/retries-failures-rate_limits#rate-limiting-your-workflow
        tagObject.setType(TagObject.TypeEnum.RATE_LIMIT);
        tagObject.setKey("${workflow.correlationId}");
        tagObject.setValue(5);

        TagObject tagObject1 = new TagObject();
        tagObject1.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("customer");
        tagObject.setValue("xyz");

        TagObject tagObject2 = new TagObject();
        tagObject2.setType(TagObject.TypeEnum.METADATA);
        tagObject2.setKey("customer");
        tagObject2.setValue("abc");

        metadataClient.addWorkflowTag(tagObject, workflowDef.getName());
        metadataClient.addWorkflowTag(tagObject1, workflowDef.getName());
        metadataClient.addWorkflowTag(tagObject2, workflowDef.getName());
    }
}
