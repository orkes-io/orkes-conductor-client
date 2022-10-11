# Worker

A worker is responsible for executing a task. Operator and System tasks are handled by the Conductor server, while user defined tasks needs to have a worker created that awaits the work to be scheduled by the server for it to be executed.

Worker framework provides features such as polling threads, metrics and server communication.

## Design Principles for Workers
Each worker embodies design pattern and follows certain basic principles:

1. Workers are stateless and do not implement a workflow specific logic. 
2. Each worker executes a very specific task and produces well-defined output given specific inputs. 
3. Workers are meant to be idempotent (or should handle cases where the task that partially executed gets rescheduled due to timeouts etc.)
4. Workers do not implement the logic to handle retries etc, that is taken care by the Conductor server.

### Creating Task Workers

Task workers must implement methods from `WorkflowInterface`. [Code sample](https://github.com/orkes-io/orkes-conductor-client/blob/main/src/test/java/io/orkes/conductor/client/util/SimpleWorker.java).


## Starting Workers
`TaskRunner` interface is used to start the workers, which takes care of polling server for the work, executing worker code and updating the results back to the server. [Code sample](https://github.com/conductor-sdk/java-sdk-examples/blob/main/src/main/java/io/orkes/samples/quickstart/WorkflowManagement.java#L83-L92)

See [Using Conductor Playground](https://orkes.io/content/docs/getting-started/playground/using-conductor-playground) for more details on how to use Playground environment for testing.

### Worker Metrics
Worker SDK collects [these metrics](https://conductor.netflix.com/metrics/client.html) 


Metrics on client side supplements the one collected from server in identifying the network as well as client side issues.

### Next: [Create workflows using code](../workflow/README.md)
