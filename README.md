# Conductor Java SDK

Conductor is the leading open-source orchestration platform allowing developers to build highly scalable distributed applications. You can find the documentation for Conductor here: [Conductor Docs](https://orkes.io/content).

This repository provides a Java client for the Orkes Conductor Server. 

## ⭐ Conductor OSS
Show support for the Conductor OSS.  Please help spread the awareness by starring Conductor repo.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=)](https://GitHub.com/conductor-oss/conductor/)

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Simple Hello World Application using Conductor

In this section, we will create a simple "Hello World" application that uses Conductor.

### Step 1: Create a Workflow

#### Use Code to create workflows

Create `workflow/WorkflowCreator.java` with the following:

```java
package io.orkes.samples.quickstart.workflow;

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
        workflow.setName("hello");
        workflow.setVersion(1);
        SimpleTask greetingsWF = new SimpleTask("greetings", "greetings");
        greetingsWF.input("name", "${workflow.input.name}");
        workflow.add(greetingsWF);

        return workflow;
    }
}
```
Create `workflow/WorkflowInput.java` with the following:
```java
package io.orkes.samples.quickstart.workflow;
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
#### (Alternatively) Use JSON to create workflows

Create workflow.json with the following:

```json
{
  "name": "hello",
  "description": "hello workflow",
  "version": 1,
  "tasks": [
    {
      "name": "greet",
      "taskReferenceName": "greet_ref",
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

Create `workers/ConductorWorkers.java` with a simple worker and workflow function.

> [!note]
> A single workflow application can have workers written in different languages.

```java
package io.orkes.samples.quickstart.workers;

import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {

    @WorkerTask("greetings")
    public void greeting(@InputParam("name") String name) {
        System.out.println("Hello my friend " + name);
    }

}
```

### Step 3: Write *your* application

Create `Main.java` with the following:

```java
package io.orkes.samples.quickstart;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;

import io.orkes.samples.quickstart.utils.SDKUtils;
import io.orkes.samples.quickstart.workflow.WorkflowCreator;
import io.orkes.samples.quickstart.workflow.WorkflowInput;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        SDKUtils utils = new SDKUtils();
        WorkflowCreator workflowCreator = new WorkflowCreator(utils.getWorkflowExecutor());
        System.out.print("Workflow Result: ");
        ConductorWorkflow<WorkflowInput> simpleWorkflow = workflowCreator.createSimpleWorkflow();
        simpleWorkflow.setVariables(new HashMap<>());

        WorkflowInput input = new WorkflowInput("Orkes");
        CompletableFuture<Workflow> workflowExecution = simpleWorkflow.executeDynamic(input);
        Workflow workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);
        String url = utils.getUIPath() + workflowRun.getWorkflowId();
        System.out.println("See the workflow execution here: " + url);

        // Shutdown any background threads
        utils.shutdown();
        System.exit(0);
    }

}
```

Create `utils/SDKUtils.java` with the following:

```java
package io.orkes.samples.quickstart.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;


public class SDKUtils {
    private final WorkflowExecutor workflowExecutor;
    private final MetadataClient metadataClient;
    private final WorkflowClient workflowClient;
    private final TaskClient taskClient;
    private ApiClient apiClient;
    private TaskRunnerConfigurer taskRunner;

    public SDKUtils() {

        String CONDUCTOR_SERVER_URL = System.getenv("CONDUCTOR_SERVER_URL");
        String key = System.getenv("KEY");
        String secret = System.getenv("SECRET");
        String conductorServer = System.getenv("CONDUCTOR_SERVER_URL");
        if (conductorServer == null) {
            conductorServer = CONDUCTOR_SERVER_URL;
        }
        if (StringUtils.isNotBlank(key)) {
            apiClient = new ApiClient(conductorServer, key, secret);
        } else {
            apiClient = new ApiClient(conductorServer);
        }
        apiClient.setReadTimeout(30_000);

        if (StringUtils.isBlank(key) || StringUtils.isBlank(secret)) {
            System.out.println(
                    "\n\nMissing KEY and|or SECRET.  Attempting to connect to "
                            + conductorServer
                            + " without authentication\n\n");
            apiClient = new ApiClient(conductorServer);
        }

        OrkesClients orkesClients = new OrkesClients(apiClient);
        this.metadataClient = orkesClients.getMetadataClient();
        this.workflowClient = orkesClients.getWorkflowClient();
        this.taskClient = orkesClients.getTaskClient();
        this.workflowExecutor =
                new WorkflowExecutor(this.taskClient, this.workflowClient, this.metadataClient, 10);
        this.workflowExecutor.initWorkers("io.orkes.samples.quickstart.workers");
        initWorkers(Arrays.asList());
    }

    private void initWorkers(List<Worker> workers) {

        TaskRunnerConfigurer.Builder builder =
                new TaskRunnerConfigurer.Builder(taskClient, workers);

        taskRunner = builder.withThreadCount(1).withTaskPollTimeout(5).build();

        // Start Polling for tasks and execute them
        taskRunner.init();  
    }

    public WorkflowExecutor getWorkflowExecutor() {
        return workflowExecutor;
    }

    public WorkflowClient getWorkflowClient() {
        return workflowClient;
    }

    public String getUIPath() {
        return apiClient.getBasePath().replaceAll("api", "").replaceAll("8080", "5000")
                + "execution/";
    }

    // Clean up resources
    public void shutdown() {
        this.apiClient.shutdown();
        this.workflowClient.shutdown();
        this.taskRunner.shutdown();
    }
}
```

### Running your distributed workflow

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

Everything related to server settings should be done within the `ApiClient` class by setting the required parameters when initializing an object, like this:

```java
ApiClient apiClient = new ApiClient("CONDUCTOR_SERVER_URL");
```
If you are using Spring Framework, we can initialize the above class as a bean that can be used across the project.

## Start Conductor Server

```
docker run --init -p 8080:8080 -p 5000:5000 conductoross/conductor-standalone:3.15.0
```
After starting the server, navigate to http://localhost:5000 to ensure the server has started successfully. Run the application from your IDE, open your Web Browser and observe the Executions tab.

## Run the workflow on Orkes

Update the Conductor Server URL 

```java
 export CONDUCTOR_SERVER_URL="https://[cluster-name].orkesconductor.io/api"
```
## [How to obtain the key and secret from the conductor server](https://orkes.io/content/docs/getting-started/concepts/access-control)

```
export KEY=your_key
export SECRET=your_secret
```
Now run the application from your IDE and view the results in your Web Browser.

> [!NOTE]
> That's it - you just created and executed your first distributed Java app!
> 
## Using Conductor in your application

There are three main ways you can use Conductor when building durable, resilient, distributed applications.

1. Write service workers that implement business logic to accomplish a specific goal - such as initiating payment transfer, getting user information from the database, etc.
2. Create Conductor workflows that implement application state - A typical workflow implements the saga pattern.
3. Use Conductor SDK and APIs to manage workflows from your application.

### [Create and Run Conductor Workers](https://github.com/RizaFarheen/orkes-conductor-client/tree/sdk-readme-update/docs/worker)

### [Create Conductor Workflows](https://github.com/RizaFarheen/orkes-conductor-client/blob/sdk-readme-update/docs/workflow/README.md)

### [Using Conductor in Your Application](https://github.com/RizaFarheen/orkes-conductor-client/blob/sdk-readme-update/docs/conductor_apps.md)
