# Conductor Workflows

Workflow can be defined as the collection of tasks and operators that specify the order and execution of the defined tasks. This orchestration occurs in a hybrid ecosystem that encircles serverless functions, microservices, and monolithic applications.

This section will dive deeper into creating and executing Conductor workflows using Java SDK.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=)](https://GitHub.com/conductor-oss/conductor/)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Creating Workflows

Conductor lets you create the workflows using either Java or JSON as the configuration.

Using Java as code to define and execute workflows lets you build extremely powerful, dynamic workflows and run them on Conductor.

When the workflows are relatively static, they can be designed using the Orkes UI (available when using Orkes Conductor) and APIs or SDKs to register and run the workflows.

Both the code and configuration approaches are equally powerful and similar in nature to how you treat Infrastructure as Code.

### Execute dynamic workflows using Code

For cases where the workflows cannot be created statically ahead of time, Conductor is a powerful dynamic workflow execution platform that lets you create very complex workflows in code and execute them. It is useful when the workflow is unique for each execution.

WorkflowCreator.java
```java
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class WorkflowCreator {

    private final WorkflowExecutor executor;

    public WorkflowCreator(WorkflowExecutor executor) {
        this.executor = executor;
    }

    public ConductorWorkflow<WorkflowInput> createSimpleWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("email_send_workflow");
        workflow.setVersion(1);

        // Step 1, get user details
        // The implementation is in
        // io.orkes.samples.quickstart.workers.ConductorWorkers.getUserInfo.
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
ConductorWorkers.java
```java
package io.orkes.samples.quickstart.workers;

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

See `dynamic_wokflow.java` for a fully functional example.

### Kitchen-sink Workflow

For a more complex workflow example with all the supported features, see `kitchensink.py`.

## Executing Workflows

The [WorkflowClient]() interface provides all the APIs required to work with workflow executions.

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
> See [workflow_ops.java] for a fully working application that demonstrates working with the workflow executions and sending signals to the workflow to manage its state.

Workflows represent the application state. With Conductor, you can query the workflow execution state anytime during its lifecycle. You can also send signals to the workflow that determines the outcome of the workflow state.

[WorkflowClient]() is the client interface used to manage workflow executions.

```java
To Do 
```

### Get the execution status

The following method lets you query the status of the workflow execution given the id. When the include_tasks is set, the response also includes all the completed and in-progress tasks.

```java
To Do
```

### Update workflow state variables

Variables inside a workflow are the equivalent of global variables in a program.

```java
To Do
```

### Terminate running workflows

Used to terminate a running workflow. Any pending tasks are canceled, and no further work is scheduled for this workflow upon termination. A failure workflow will be triggered but can be avoided if `trigger_failure_workflow` is set to False.

```java
To Do
```

### Retry failed workflows

If the workflow has failed due to one of the task failures after exhausting the retries for the task, the workflow can still be resumed by calling the retry.

```java
To Do
```

When a sub-workflow inside a workflow has failed, there are two options:

1. Re-trigger the sub-workflow from the start (Default behavior).
2. Resume the `sub-workflow` from the failed task (set `resume_subworkflow_tasks` to True)

### Restart workflows

A workflow in the terminal state (COMPLETED, TERMINATED, FAILED) can be restarted from the beginning. Useful when retrying from the last failed task is insufficient, and the whole workflow must be started again.

```java
To Do
```

### Rerun a workflow from a specific task

In the cases where a workflow needs to be restarted from a specific task rather than from the beginning, rerun provides that option. When issuing the rerun command to the workflow, you can specify the id of the task from where the workflow should be restarted (as opposed to from the beginning), and optionally, the input of the workflow can also be changed.

```java
To Do
```

> [!tip] 
> Rerun is one of the most powerful features Conductor has, giving you unparalleled control over the workflow restart.

### Pause a running workflow

A running workflow can be put to a PAUSED status. A paused workflow lets the currently running tasks complete but does not schedule any new tasks until resumed.

```java
To Do
```

### Resume paused workflow

Resume operation resumes the currently paused workflow, immediately evaluating its state and scheduling the next set of tasks.

```java
To Do
```

## Searching for workflows

Workflow executions are retained until removed from Conductor. This gives complete visibility into all the executions an application has - regardless of the number of executions. Conductor has a powerful search API that allows you to search for workflow executions.

```java
To Do
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

All the aspects of handling failures, retries, rate limits, etc., are driven by the configuration that can be updated in real-time without re-deploying your application.

### Retries

Each task in the Conductor workflow can be configured to handle failures with retries, along with the retry policy (linear, fixed, exponential backoff) and maximum number of retry attempts allowed.

See [Error Handling](https://orkes.io/content/error-handling) for more details.

### Rate Limits

What happens when a task is operating on a critical resource that can only handle few requests at a time? Tasks can be configured to have a fixed concurrency (X request at a time) or a rate (Y tasks/time window).

#### Task Registration

```java
To Do
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

### Update the task definition:

```shell
POST /api/metadata/taskdef -d @task_def.json
```

See [task_configure.java] for a detailed working app.