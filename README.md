# Conductor Java SDK

[Conductor](https://www.conductor-oss.org/) is the leading open-source orchestration platform allowing developers to build highly scalable distributed applications. 

Check out the [official documentation for Conductor](https://orkes.io/content).

This repository provides a Java client for the Orkes Conductor Server. 

## Deprecation Notice

This Client (v2) is being deprecated and will be removed.

It will be replaced by [Client v4](https://github.com/conductor-oss/conductor/tree/main/conductor-clients/java/conductor-java-sdk), which offers improved features, better performance, and other enhancements.

We strongly encourage all users to migrate to Client v4. 

## ⭐ Conductor OSS

Show support for the Conductor OSS.  Please help spread the awareness by starring Conductor repo.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=)](https://GitHub.com/conductor-oss/conductor/)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

- [Set Up Conductor Java SDK](#set-up-conductor-java-sdk)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Hello World Application Using Conductor](#hello-world-application-using-conductor)
  - [Step 1: Create Workflow](#step-1-create-workflow)
    - [Creating Workflows by Code](#creating-workflows-by-code)
  - [Step 2: Write a Worker](#step-2-write-a-worker)
  - [Step 3: Running Application​ in Conductor](#step-3-running-application%E2%80%8B-in-conductor)
  - [Conductor Server Settings](#conductor-server-settings)
  - [Start Conductor Server](#start-conductor-server)
  - [Execute Hello World Application](#execute-hello-world-application)
- [Learn More about Conductor Java SDK](#learn-more-about-conductor-java-sdk)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Set Up Conductor Java SDK

Add `orkes-conductor-client` dependency to your project.

**Pre-requisites:**
- Java 17 or greater
- Gradle or Maven for dependency management

### Gradle

For Gradle-based projects, modify the `build.gradle` file in the project directory by adding the following line to the dependencies block:

```
implementation 'io.orkes.conductor:orkes-conductor-client:2.1.6'
```

### Maven

For Maven-based projects, modify the `pom.xml` file in the project directory by adding the following XML snippet within the dependencies section:

```
<dependency>
  <groupId>io.orkes.conductor</groupId>
  <artifactId>orkes-conductor-client</artifactId>
  <version>2.1.6</version>
</dependency>
```

## Hello World Application Using Conductor

In this section, we will create a simple "Hello World" application that executes a "greetings" workflow managed by Conductor.

### Step 1: Create Workflow

#### Creating Workflows by Code

The classes created in this first step will be used in Step 3 to create a workflow by code.

Create the class `io.orkes.helloworld.WorkflowInput`:

```java
package io.orkes.helloworld;

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

```
Create the class `io.orkes.helloworld.GreetingsWorkflow`:


```java
package io.orkes.helloworld;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class GreetingsWorkflow {
    private final WorkflowExecutor executor;

    public GreetingsWorkflow(WorkflowExecutor executor) {
        this.executor = executor;
    }

    public ConductorWorkflow<WorkflowInput> createWorkflow() {
        var workflow = new ConductorWorkflow<WorkflowInput>(executor);
        workflow.setName("greetings");
        workflow.setVersion(1);

        var greetingsTask = new SimpleTask("greet", "greet_ref");
        greetingsTask.input("name", "${workflow.input.name}");
        workflow.add(greetingsTask);
        return workflow;
    }
}
```

### Step 2: Write a Worker

Create another class, `io.orkes.helloworld.ConductorWorkers`. This class will contain a worker task method, which will execute a task in our workflow.

> [!note]
> A single workflow can have task workers written in different languages and deployed anywhere, making your workflow polyglot and distributed!

```java
package io.orkes.helloworld;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {

   @WorkerTask("greet")
   public String greet(@InputParam("name") String name) {
     return "Hello " + name;
   }

 }
```

Next, write the main application, which will execute the workflow.

### Step 3: Running Application​ in Conductor

Let’s write the application first. To implement this step, we’ll create a `Main` class in the `io.orkes.helloworld` package. This class will contain the` main` method, which serves as the entry point to our application. The main method will initiate our Conductor client and use it to set up and execute the `Greetings` workflow we defined in previous steps.
By creating this entry point, we allow our application to run independently, connecting to the Conductor server and executing workflows. 

```java
package io.orkes.helloworld;

import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

 // Change these values according to your conductor server instance.Refer to the documentation on creating an access key.
    private static final String CONDUCTOR_SERVER = "https://play.orkes.io/api";
    private static final String KEY = "_CHANGE_ME_";
    private static final String SECRET = "_CHANGE_ME_";

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //Initialise Conductor Client
        var apiClient = new ApiClient(CONDUCTOR_SERVER, KEY, SECRET);
        var orkesClients = new OrkesClients(apiClient);
        var taskClient = orkesClients.getTaskClient();
        var workflowClient = orkesClients.getWorkflowClient();
        var metadataClient = orkesClients.getMetadataClient();

        //Initialise WorkflowExecutor and Conductor Workers
        var workflowExecutor = new WorkflowExecutor(taskClient, workflowClient, metadataClient, 10);
        workflowExecutor.initWorkers("io.orkes.helloworld");

        //Create the workflow with input
        var workflowCreator = new GreetingsWorkflow(workflowExecutor);
        var simpleWorkflow = workflowCreator.createWorkflow();
        var input = new WorkflowInput("Orkes");
        var workflowExecution = simpleWorkflow.executeDynamic(input);
        var workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);

        System.out.println("Started workflow " + workflowRun.getWorkflowId());

        System.exit(0);
    }
}
```

### Conductor Server Settings

Everything related to server settings should be done within the `ApiClient` class by setting the required parameters when initializing an object, like this:

```java
ApiClient apiClient = new ApiClient("CONDUCTOR_SERVER_URL");
```

If you are using Spring Framework, you can initialize the above class as a bean that can be used across the project.

### Start Conductor Server

To start the Conductor server in a standalone mode from a Docker image, type the command below:

```
docker run --init -p 8080:8080 -p 5000:5000 conductoross/conductor-standalone:3.15.0
```

To ensure the server has started successfully, open Conductor UI on http://localhost:5000.

### Execute Hello World Application

To execute the application:​

1. Run the Java application.
2. The workflow will begin executing, and you can monitor its status through the Conductor UI at http://localhost:5000.
3. Go to the **Executions** tab to view the details of the workflow execution.

<img width="1431" alt="Screenshot 2024-04-01 at 15 53 02" src="https://github.com/RizaFarheen/orkes-conductor-client/assets/163816773/e413c828-5f47-4cdc-b113-bed3fefa63a4">

> [!NOTE]
> That's it - you just created and executed your first distributed Java app!
> 

## Learn More about Conductor Java SDK

There are three main ways you can use Conductor when building durable, resilient, distributed applications.

1. [Write service workers](https://github.com/orkes-io/orkes-conductor-client/tree/main/docs/worker
) that implement business logic to accomplish a specific goal - such as initiating payment transfer, getting user information from the database, etc.
2. [Create Conductor workflows](https://github.com/orkes-io/orkes-conductor-client/tree/main/docs/workflow) that implement application state - A typical workflow implements the saga pattern.
3. [Use Conductor SDK and APIs to manage workflows from your application](https://github.com/orkes-io/orkes-conductor-client/blob/main/docs/conductor_apps.md).
