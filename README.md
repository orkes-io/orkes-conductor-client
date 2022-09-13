# Orkes Conductor Client
Client SDK for working with Netflix Conductor and Orkes specific features such as RBAC and Scheduler.

SDK provides clients to manage metadata, users, groups, workflows and schedules in your Orkes Conductor cluster.

## Quickstart
The client uses [ApiClient](src/main/java/io/orkes/conductor/client/ApiClient.java) to manage the connection to the Conductor server.

### ApiClient that connects to a server that does NOT require authorization e.g. localhost
```java
        ApiClient apiClient = new ApiClient("http://localhost:8080/api");
```
### ApiClient with Key and secret
```java
        ApiClient apiClient = new ApiClient("http://play.orkes.io/api", KEY, SECRET);
```
### Control Timeouts if required
```java
        //Values are in millis
        apiClient.setReadTimeout(1_000);
        apiClient.setConnectTimeout(1_000);
```

### Initialize Clients
```java
        OrkesClients orkesClients = new OrkesClients(apiClient);
        
        //Workflow Management APIs e.g. start a workflow, pause, terminate, search etc.
        WorkflowClient workflowClient = orkesClients.getWorkflowClient();
        
        //Task Management APIs.  Used by Task Workers
        TaskClient TaskClientClient = orkesClients.getTaskClient();
        
        //Authorization APIs to manage users, groups, applications, permissions, tags etc.
        AuthorizationClient authorizationClient = orkesClients.getAuthorizationClient();
```

### Working with TaskRunner to create Task Workers
`io.orkes.conductor.client.automator.TaskRunnerConfigurer` interface is used to manage the execution of workers.

This class is a drop-in replacement for similarly named `com.netflix.conductor.client.automator.TaskRunnerConfigurer` making it easy to migrate your existing code to `io.orkes`.
Orkes version improves on the current OSS version with batch polling and performance improvements.

```java
        //Add a list with Worker implementations
        List<Worker> workers = new ArrayList<>();

        TaskRunnerConfigurer.Builder builder = new TaskRunnerConfigurer.Builder(taskClient, workers);

        //Map of task type to domain when polling for task from specific domains
        Map<String, String> taskToDomains = new HashMap<>();

        //No. of threads for each task type.  Used to configure taskType specific thread count for execution
        Map<String, Integer> taskThreadCount = new HashMap<>();

        TaskRunnerConfigurer taskRunner = builder
                .withThreadCount(10)        //Default thread count if not specified in taskThreadCount
                .withTaskToDomain(taskToDomains)
                .withTaskThreadCount(taskThreadCount)
                .withSleepWhenRetry(500)            //Time in millis to sleep when retrying for a task update.  Default is 500ms
                .withTaskPollTimeout(100)           //Poll timeout for long-poll.  Default is 100ms
                .withWorkerNamePrefix("worker-")    //Thread name prefix for the task worker executor. Useful for logging
                .withUpdateRetryCount(3)            //No. of times to retry if task update fails.  defaults to 3
                .build();

        //Start Polling for tasks and execute them
        taskRunner.init();

        //Optionally, use the shutdown method to stop polling
        taskRunner.shutdown();
```