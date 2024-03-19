# Writing Workers

A Workflow task represents a unit of business logic that achieves a specific goal such as check inventory, initiate payment transfer etc. Worker implements a task in the workflow. (Note: Often times worker and task are used interchangeably in various blogs, docs etc.)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Implementing Workers

The workers can be implemented by writing a simple java function and annotating the function with the @worker_task. Conductor workers are services (similar to microservices) that follow [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle).

Workers can be hosted along with the workflow or running a distributed environment where a single workflow uses workers that are deployed and running in different machines/vms/containers. Whether to keep all the workers in the same application or run them as distributed application is a design and architectural choice. Conductor is well suited for both kind of scenarios.

You create or convert any existing java function to a distributed worker by adding @worker_task annotation to it. Here is a simple worker that takes name as input and returns greetings:

```java
To Do
```

### Managing workers in your application

Workers use a polling mechanism (with long-poll) to check for any available tasks periodically from server. The startup and shutdown of workers is handled by `conductor.client.automator.task_handler.TaskHandler` class.

```java
To Do
```

## Design Principles for Workers

Each worker embodies design pattern and follows certain basic principles:

1. Workers are stateless and do not implement a workflow specific logic.
2. Each worker executes a very specific task and produces well-defined output given specific inputs.
3. Workers are meant to be idempotent (or should handle cases where the task that partially executed gets rescheduled due to timeouts etc.)
4. Workers do not implement the logic to handle retries etc., that is taken care by the Conductor server.

## System Task Workers

A system task worker is a pre-built, general purpose worker that is part of your Conductor server distribution.

System tasks automates the repeated tasks such as calling an HTTP endpoint, executing lightweight ECMA compliant javascript code, publishing to an event broker etc.

### Wait Task

> [!tip]
> Wait is a powerful way to have your system wait for a certain trigger such as an external event, certain date/time or duration such as 2 hours without having to manage threads, background processes or jobs.

#### Using code to create WAIT task

```java
To Do
```

#### JSON Configuration

```json
{
  "name": "wait",
  "taskReferenceName": "wait_till_jan_end",
  "type": "WAIT",
  "inputParameters": {
    "until": "2024-01-31 00:00 UTC"
  }
}
```

### HTTP Task

Make a request to an HTTP(S) endpoint. The task allows making GET, PUT, POST, DELETE, HEAD, PATCH requests.

#### Using code to create an HTTP task

```java
To Do
```

#### JSON Configuration

```json
{
  "name": "http_task",
  "taskReferenceName": "http_task_ref",
  "type" : "HTTP",
  "uri": "https://orkes-api-tester.orkesconductor.com/api",
  "method": "GET"
}
```

### Javascript Executor Task

Execute ECMA compliant Javascript code. Useful when you need to write a script to do data mapping, calculations etc.

```java
To Do 
```

#### JSON Configuration

```json
{
  "name": "inline_task",
  "taskReferenceName": "inline_task_ref",
  "type": "INLINE",
  "inputParameters": {
    "expression": " function greetings() {\n  return {\n            \"text\": \"hello \" + $.name\n        }\n    }\n    greetings();",
    "evaluatorType": "graaljs",
    "name": "${workflow.input.name}"
  }
}
```

### Json Processing using JQ

[jq](https://jqlang.github.io/jq/) is like sed for JSON data - you can use it to slice and filter and map and transform structured data with the same ease that sed, awk, grep and friends let you play with text.

```java
To Do
```

### JSON Configuration

```json
{
  "name": "json_transform_task",
  "taskReferenceName": "json_transform_task_ref",
  "type": "JSON_JQ_TRANSFORM",
  "inputParameters": {
    "key1": "k1",        
    "key2": "k2",
    "queryExpression": "{ key3: (.key1.value1 + .key2.value2) }",
  }
}
```

## Worker vs Microservice / HTTP endpoints

> [!tip] 
> Workers are a lightweight alternative to exposing an HTTP endpoint and orchestrating using `HTTP` task. 
>  Using workers is a recommended approach if you do not need to expose the service over HTTP or gRPC endpoints.

There are several advantages to this approach:

1. **No need for an API management layer** : Given there are no exposed endpoints and workers are self load-balancing.
2. **Reduced infrastructure footprint** : No need for an API gateway/load balancer.
3. All the communication is initiated from worker using polling - avoiding need to open up any incoming TCP ports.
4. Workers **self-regulate** when busy, they only poll as much as they can handle. Backpressure handling is done out of the box.
5. Workers can be scale up / down easily based on the demand by increasing the no. of processes.

## Deploying Workers in production

Conductor workers can run in cloud-native environment or on-prem and can easily be deployed like any other python application. Workers can run a containerized environment, VMs or on bare-metal just like you would deploy your other python applications.

