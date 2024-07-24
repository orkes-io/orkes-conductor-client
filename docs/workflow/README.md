# Conductor Workflows

Workflow can be defined as the collection of tasks and operators that specify the order and execution of the defined tasks. This orchestration occurs in a hybrid ecosystem that encircles serverless functions, microservices, and monolithic applications.

This section will dive deeper into creating and executing Conductor workflows using Java SDK.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=)](https://GitHub.com/conductor-oss/conductor/)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

- [Creating Workflows](#creating-workflows)
  - [Execute Dynamic Workflows Using Code](#execute-dynamic-workflows-using-code)
  - [Kitchen-Sink Workflow](#kitchen-sink-workflow)
- [Executing Workflows](#executing-workflows)
  - [Execute Workflow Asynchronously](#execute-workflow-asynchronously)
  - [Execute Workflow Synchronously](#execute-workflow-synchronously)
- [Managing Workflow Executions](#managing-workflow-executions)
  - [Get Execution Status](#get-execution-status)
  - [Update Workflow State Variables](#update-workflow-state-variables)
  - [Terminate Running Workflows](#terminate-running-workflows)
  - [Retry Failed Workflows](#retry-failed-workflows)
  - [Restart Workflows](#restart-workflows)
  - [Rerun Workflow from a Specific Task](#rerun-workflow-from-a-specific-task)
  - [Pause Running Workflow](#pause-running-workflow)
  - [Resume Paused Workflow](#resume-paused-workflow)
- [Searching for Workflows](#searching-for-workflows)
- [Handling Failures, Retries and Rate Limits](#handling-failures-retries-and-rate-limits)
  - [Retries](#retries)
  - [Rate Limits](#rate-limits)
    - [Task Registration](#task-registration)
  - [Update Task Definition:](#update-task-definition)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Creating Workflows

Conductor lets you create the workflows using either Java or JSON as the configuration.

Using Java as code to define and execute workflows lets you build extremely powerful, dynamic workflows and run them on Conductor.

When the workflows are relatively static, they can be designed using the Orkes UI (available when using Orkes Conductor) and APIs or SDKs to register and run the workflows.

Both the code and configuration approaches are equally powerful and similar in nature to how you treat Infrastructure as Code.

### Execute Dynamic Workflows Using Code

For cases where the workflows cannot be created statically ahead of time, Conductor is a powerful dynamic workflow execution platform that lets you create very complex workflows in code and execute them. It is useful when the workflow is unique for each execution.

`CreateWorkflow.java`

```java
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class CreateWorkflow {

    private final WorkflowExecutor executor;

    public WorkflowCreator(WorkflowExecutor executor) {
        this.executor = executor;
    }

    public ConductorWorkflow<WorkflowInput> createSimpleWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("email_send_workflow");
        workflow.setVersion(1);

        SimpleTask getUserDetails = new SimpleTask("get_user_info", "get_user_info");
        getUserDetails.input("userId", "${workflow.input.userId}");

        // send email
        SimpleTask sendEmail = new SimpleTask("send_email", "send_email");
        // get user details user info, which contains the email field
        sendEmail.input("email", "${get_user_info.output.email}");

        workflow.add(getUserDetails);
        workflow.add(sendEmail);

        return workflow;
    }
    
}
```

`ConductorWorkers.java`

```java
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {

    @WorkerTask("get_user_info")
    public UserInfo getUserInfo(@InputParam("userId") String userId) {
        UserInfo userInfo =  new UserInfo("User X", userId);
        userInfo.setEmail(userId + "@example.com");
        userInfo.setPhoneNumber("555-555-5555");
        return userInfo;
    }

    @WorkerTask("send_email")
    public void sendEmail(@InputParam("email") String email) {
        System.out.println("Sending email to " + email);
    }
}
```

See [DynamicWorkflow](https://github.com/orkes-io/orkes-conductor-client/tree/main/examples/java/io/orkes/conductor/sdk/examples/dynamicworkflow) for a fully functional example.

### Kitchen-Sink Workflow

For a more complex workflow example with all the supported features, see [KitchenSink.java](https://github.com/orkes-io/orkes-conductor-client/blob/main/examples/java/io/orkes/conductor/sdk/examples/KitchenSink.java)

## Executing Workflows

The [WorkflowClient](/src/main/java/io/orkes/conductor/client/WorkflowClient.java) interface provides all the APIs required to work with workflow executions.

```java
import com.netflix.conductor.client.http.WorkflowClient;
WorkflowClient wfClient = utils.getWorkflowClient();
String workflowId = wfClient.startWorkflow(startWorkflowReq);
```

### Execute Workflow Asynchronously

Useful when workflows are long-running.

```java
import com.netflix.conductor.client.http.WorkflowClient;
WorkflowClient wfClient = utils.getWorkflowClient();
String workflowId = wfClient.startWorkflow(startWorkflowReq);
```

### Execute Workflow Synchronously

Applicable when workflows complete very quickly - usually under 20-30 seconds.

```java
Workflow workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);
```

## Managing Workflow Executions

> [!note] 
> See [WorkflowOps.java](https://github.com/orkes-io/orkes-conductor-client/blob/main/examples/java/io/orkes/conductor/sdk/examples/WorkflowOps.java) for a fully working application that demonstrates working with the workflow executions and sending signals to the workflow to manage its state.

Workflows represent the application state. With Conductor, you can query the workflow execution state anytime during its lifecycle. You can also send signals to the workflow that determines the outcome of the workflow state.

`WorkflowClient` is the client interface used to manage workflow executions.

```java
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.ApiClient;
OrkesClients orkesClients = OrkesClients(getApiClientWithCredentials());
WorkflowClient workflowClient = orkesClients.getWorkflowClient();
```

### Get Execution Status

The following method lets you query the status of the workflow execution given the id. When the include_tasks is set, the response also includes all the completed and in-progress tasks.

```java
getWorkflowStatusSummary(String workflowId, Boolean includeOutput, Boolean includeVariables)
```

### Update Workflow State Variables

Variables inside a workflow are the equivalent of global variables in a program.

```java
setVariables(Map<String, Object> variables)
```

### Terminate Running Workflows

Used to terminate a running workflow. Any pending tasks are canceled, and no further work is scheduled for this workflow upon termination. 

```java
terminateWorkflow(List<String> workflowIds, String reason)
```

### Retry Failed Workflows

If the workflow has failed due to one of the task failures after exhausting the retries for the task, the workflow can still be resumed by calling the retry.

```java
retryWorkflow(List<String> workflowIds)
```

When a sub-workflow inside a workflow has failed, there are two options:

1. Re-trigger the sub-workflow from the start (Default behavior).
2. Resume the `sub-workflow` from the failed task (set `resume_subworkflow_tasks` to True).

### Restart Workflows

A workflow in the terminal state (COMPLETED, TERMINATED, FAILED) can be restarted from the beginning. Useful when retrying from the last failed task is insufficient, and the whole workflow must be started again.

```java
restartWorkflow(List<String> workflowIds, Boolean useLatestDefinitions)
```

### Rerun Workflow from a Specific Task

In the cases where a workflow needs to be restarted from a specific task rather than from the beginning, rerun provides that option. When issuing the rerun command to the workflow, you can specify the task ID from where the workflow should be restarted (as opposed to from the beginning), and optionally, the workflow's input can also be changed.

```java
setReRunFromTaskId(String reRunFromTaskId)
```

> [!tip] 
> Rerun is one of the most powerful features Conductor has, giving you unparalleled control over the workflow restart.

### Pause Running Workflow

A running workflow can be put to a **PAUSED** status. A paused workflow lets the currently running tasks complete but does not schedule any new tasks until resumed.

```java
pauseWorkflow(List<String> workflowIds)
```

### Resume Paused Workflow

Resume operation resumes the currently paused workflow, immediately evaluating its state and scheduling the next set of tasks.

```java
resumeWorkflow(List<String> workflowIds)
```

## Searching for Workflows

Workflow executions are retained until removed from the Conductor. This gives complete visibility into all the executions an application has - regardless of the number of executions. Conductor has a powerful search API that allows you to search for workflow executions.

```java
searchWorkflows(queryId, start, size, sort, freeText, query, skipCache);
```

- **free_text:** Free text search to look for specific words in the workflow and task input/output
- **query** -  SQL-like query to search against specific fields in the workflow.

Here are the supported fields for the query:

| Field |  Description |
| ----- | ------------ |
| status | The status of the workflow. |
| correlationId | The ID to correlate the workflow execution to other executions. | 
| workflowType | The name of the workflow. | 
| version | The version of the workflow. | 
| startTime	| The start time of the workflow is in milliseconds. |

## Handling Failures, Retries and Rate Limits

Conductor lets you embrace failures rather than worry about the complexities introduced in the system to handle failures.

All the aspects of handling failures, retries, rate limits, etc., are driven by the configuration that can be updated in real time without re-deploying your application.

### Retries

Each task in the Conductor workflow can be configured to handle failures with retries, along with the retry policy (linear, fixed, exponential backoff) and maximum number of retry attempts allowed.

See [Error Handling](https://orkes.io/content/error-handling) for more details.

### Rate Limits

What happens when a task is operating on a critical resource that can only handle a few requests at a time? Tasks can be configured to have a fixed concurrency (X request at a time) or a rate (Y tasks/time window).

#### Task Registration

```java
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class TaskDefinitionTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        OrkesClients orkesClients = OrkesClients(getApiClientWithCredentials());
        TaskClient taskClient = orkesClients.getTaskClient();
        WorkflowClient workflowClient = orkesClients.getWorkflowClient();
        MetadataClient metadataClient = orkesClients.getMetadataClient();
        //Get an instance of WorkflowExecutor
        WorkflowExecutor workflowExecutor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 10);
        TaskDef taskDef = new TaskDef();
        taskDef.setName("task_with_retries");
        taskDef.setRetryCount(3);
        taskDef.setRetryLogic(TaskDef.RetryLogic.FIXED);
        //only allow 3 tasks at a time to be in the IN_PROGRESS status
        taskDef.setConcurrentExecLimit(3);
        //timeout the task if not polled within 60 seconds of scheduling
        taskDef.setPollTimeoutSeconds(60);
        //timeout the task if the task does not COMPLETE in 2 minutes
        taskDef.setTimeoutSeconds(120);
        //for the long running tasks, timeout if the task does not get updated in COMPLETED or IN_PROGRESS status in 60 seconds after the last update
        taskDef.setResponseTimeoutSeconds(60);
        //only allow 100 executions in a 10-second window! -- Note, this is complementary to concurrent_exec_limit
        taskDef.setRateLimitPerFrequency(100);
        taskDef.setRateLimitFrequencyInSeconds(10);
        List<TaskDef> taskDefs = new ArrayList<TaskDef>();
        taskDefs.add(taskDef);
        metadataClient.registerTaskDefs(taskDefs);

    }
}
```

```json
{
  "name": "task_with_retries",
  
  "retryCount": 3,
  "retryLogic": "LINEAR_BACKOFF",
  "retryDelaySeconds": 1,
  "backoffScaleFactor": 1,
  
  "timeoutSeconds": 120,
  "responseTimeoutSeconds": 60,
  "pollTimeoutSeconds": 60,
  "timeoutPolicy": "TIME_OUT_WF",
  
  "concurrentExecLimit": 3,
  
  "rateLimitPerFrequency": 0,
  "rateLimitFrequencyInSeconds": 1
}
```

### Update Task Definition:

```shell
POST /api/metadata/taskdef -d @task_def.json
```

See [TaskConfigure.java](https://github.com/orkes-io/orkes-conductor-client/blob/main/examples/java/io/orkes/conductor/sdk/examples/TaskConfigure.java) for a detailed working app.
