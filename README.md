# Orkes Conductor Client

This repository provides a Java client for Orkes Conductor Server. With this client you can manage
metadata (Workflows and tasks), run workflows, create workers and more. 

## Examples

Check out [the examples in this project](https://github.com/conductor-sdk/java-sdk-examples)

## Quick Guides

- [Create and run workers](docs/worker/README.md)
- [Create workflows using code](docs/workflow/README.md)
- [Create Kafka queue configuration using code](docs/queue/kafka.md)

---

### Conductor server settings
Everything related to server settings should be done within the `ApiClient` class, by setting the required parameters when initializing an object, like this:

```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api");
```

#### Authentication settings (optional)
Use if your conductor server requires authentication.

#### Access Control Setup
See [Access Control](https://orkes.io/content/docs/getting-started/concepts/access-control) for more details on role based access control with Conductor and generating API keys for your environment. [Detailed example](https://github.com/conductor-sdk/java-sdk-examples/blob/main/src/main/java/io/orkes/samples/quickstart/ExecuteWorkflow.java#L48-L55).

```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api", "key", "secret");
```

---
### Next: [Create and run workers](docs/worker/README.md)
