# Orkes Client

This client library is a wrapper over [Netflix Conductor OSS client](https://github.com/Netflix/conductor/tree/main/client). 

It provides support for features available in Orkes Conductor such as authentication and authorization.

# Using Key and Secret to poll for a task with Authorization
If the Conductor server has authorization enabled, you need key and secret to access APIs including poll for the tasks.

## Steps to generate the key
1. Loging to the Conductor UI
2. Navigate to the Applications menu
3. Create Application and create Application Key.  Make sure to either a) select unrestricted workers or b) add the EXECUTE permissions to the task name.
5. Save the key/secret - as they are not avaialble after

## Using Key/Secret in the worker
### Initialize the Task Client with Key/Secret
```
OrkesTaskClient taskClient = new OrkesTaskClient();
taskClient.setRootURI(rootUri);   //Location of the conductor server. e.g. http://server/api/
taskClient.withCredentials(keyId, secret);    //key, secret from the previous step
```
### Use the OrkesTaskClient with Conductor Worker
```
TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer
  .Builder(taskClient, workersList)
  .withThreadCount(20)
  .build();
runnerConfigurer.init();
```        
