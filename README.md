# Conductor Java SDK

Conductor is an open source distributed, scalable and highly available orchestration platform that allows developers to build powerful distributed applications. You can find the documentation for Conductor here: [Conductor Docs](https://orkes.io/content).

This repository provides a Java client for Orkes Conductor Server. 

## ⭐ Conductor OSS
Show support for the Conductor OSS.  Please help spread the awareness by starring Conductor repo.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=)](https://GitHub.com/conductor-oss/conductor/)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Set Up Conductor Java SDK

Add `orkes-conductor-client` dependency to your project.

### Gradle

For Gradle-based projects, modify the `build.gradle` file in the project directory by adding the following line to the dependencies block in that file:

```
implementation 'io.orkes.conductor:orkes-conductor-client:2.0.1'
```

### Maven

For Maven-based projects, modify the `pom.xml` file in the project directory by adding the following XML snippet within the `dependencies` section:

```
<dependency>
  <groupId>io.orkes.conductor</groupId>
  <artifactId>orkes-conductor-client</artifactId>
  <version>1.1.14</version>
</dependency>
```

### Conductor Server Settings

Everything related to server settings should be done within the `ApiClient` class, by setting the required parameters when initializing an object, like this:

```java
ApiClient apiClient = new ApiClient("CONDUCTOR_SERVER_URL");
```

If you are using Spring Framework, we can initialize the above class as a bean that can be used across the project.


(Optionally) If you are using a Conductor server that requires authentication:

- [Obtain the key and secret from Conductor server](https://orkes.io/content/docs/getting-started/concepts/access-control) 

Once you have a key and secret, you can configure the app from properties or environment variables, as shown below:

```java
    String key = System.getenv("KEY");
    String secret = System.getenv("SECRET");
    String conductorServer = System.getenv("CONDUCTOR_SERVER_URL");
    ApiClient apiClient = new ApiClient(conductorServer, key, secret);
```

## Start Conductor Server

```
docker run --init -p 8080:8080 -p 5000:5000 conductoross/conductor-standalone:3.15.0
```

After starting the server navigate to http://localhost:5000 to ensure the server has started successfully.

## Simple Hello World Application using Conductor

In this section, we will create a simple "Hello World" application that uses Conductor.

### Step 1: Create a Workflow

#### Use Code to create workflows

Create `HelloWorld.java` with the following:

```java
    private WorkflowDef registerWorkflowDef() throws IOException {
        InputStream is = ExecuteWorkflow.class.getResourceAsStream("/workflow.json");
        WorkflowDef workflowDef = objectMapper.readValue(is, WorkflowDef.class);
        metadataClient.registerWorkflowDef(workflowDef, true);
        return workflowDef;
    }
```
#### (Alternatively) Use JSON to create workflows

Create workflow.json with the following:

```json
{
  "name": "hello",
  "description": "hello workflow",
  "version": 1,
  "tasks": [
    {
      "name": "hello_world",
      "taskReferenceName": "hello_world",
      "type": "SIMPLE",
      "inputParameters": {
        "name": "${workflow.input.name}"
      }
    }
  ],
  "timeoutPolicy": "TIME_OUT_WF",
  "timeoutSeconds": 60
}
```

Now, register this workflow with the server:

```shell
curl -X POST -H "Content-Type:application/json" \
http://localhost:8080/api/metadata/workflow -d @workflow.json
```

### Step 2: Write Worker

Create a simple worker:

> [!note]
> A single workflow application can have workers written in different languages.

```java
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

public class HelloWorld implements Worker {
    @Override
    public String getTaskDefName() {
        return "hello_world";
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);

        String name = (String) task.getInputData().get("name");
        result.addOutputData("hw_response", "Hello, " + name);

        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }

    @Override
    public int getPollingInterval() {
        return 1;
    }
}
```

### Step 3: Write *your* application

```java
    public static void main(String[] args) throws IOException {

        ExecuteWorkflow workflowManagement = new ExecuteWorkflow();

        //Register the workflow definition
        workflowManagement.registerWorkflowDef();


        //Start worker
        workflowManagement.startWorkers();

        workflowManagement.runSyncWorkflow();


    }
```

> [!NOTE]
> That's it - you just created your first distributed Java app!
> 

## Using Conductor in your application
There are three main ways you will use Conductor when building durable, resilient, distributed applications.
1. Write service workers that implements business logic to accomplish a specific goal - such as initiate payment transfer, get user information from database etc. 
2. Create Conductor workflows that implements application state - A typical workflow implements SAGA pattern
3. Use Conductor SDK and APIs to manage workflows from your application.