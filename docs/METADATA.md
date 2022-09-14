# Metadata Management APIs

### Create instance of MetadataClient
```
MetadataClient metadataClient = orkesClients.getMetadataClient();
```
### Create task definition
```
TaskDef taskDef = new TaskDef("testing", "sample task");
taskDef.setRetryCount(3); // Task will be executed 4 times assuming each attempt failed.
taskDef.setRetryLogic(TaskDef.RetryLogic.FIXED); // FIXED, EXPONENTIAL_BACKOFF, LINEAR_BACKOFF
taskDef.setRetryDelaySeconds(1); // Delay between each retry
taskDef.setTimeoutPolicy(TaskDef.TimeoutPolicy.TIME_OUT_WF); // RETRY, TIME_OUT_WF, ALERT_ONLY
taskDef.setTimeoutSeconds(10); // Task timeout
taskDef.setResponseTimeoutSeconds(5); // Response timeout for worker tasks.
taskDef.setOwnerEmail("test@orkes.io"); // Owner email
```
### Register task definition
```
metadataClient.registerTaskDefs(Arrays.asList(taskDef));
```

### Create workflow definition
```
WorkflowDef workflowDef = new WorkflowDef();
workflowDef.setName("example");
workflowDef.setVersion(1);  // Workflow version
workflowDef.setOwnerEmail("test@orkes.io");
workflowDef.setFailureWorkflow("failure_workflow"); // Failure workflow to be executed on failure of current workflow.
workflowDef.setTimeoutSeconds(10); // Workflow timeout.
workflowDef.setVariables(Map.of("value", 2)); // Workflow variables
workflowDef.setInputParameters(List.of("input_value"));
```

### Create workflow task
```
WorkflowTask testing = new WorkflowTask();
testing.setTaskReferenceName("testing");
testing.setName("testing");
testing.setType("SIMPLE");
testing.setInputParameters(Map.of("value", "${workflow.variables.workflow_input_value}",
        "order", "${workflow.input.order_id}"));
testing.setWorkflowTaskType(SIMPLE);
```

### Register workflow definition
```
workflowDef.setTasks(Arrays.asList(testing)); // Add task to workflow
metadataClient.registerWorkflowDef(workflowDef); // Register workflow definition
```

### Unregister task definition
```
metadataClient.unregisterTaskDef("SIMPLE");
```
### Unregister workflow definition
```
metadataClient.unregisterWorkflowDef("example", 1); // Unregister workflow example with version 1
```
