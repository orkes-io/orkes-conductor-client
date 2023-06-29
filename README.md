# Orkes Conductor Client

This repository provides a Java client for Orkes Conductor Server. With this client you can manage
metadata (Workflows and tasks), run workflows, create workers and more. 

## Add `orkes-conductor-client` dependency to your project
### Gradle
```
implementation 'io.orkes.conductor:orkes-conductor-client:2.0.1'
```
### Maven
```
<dependency>
  <groupId>io.orkes.conductor</groupId>
  <artifactId>orkes-conductor-client</artifactId>
  <version>1.1.14</version>
</dependency>
```
## Examples

Check out [the examples in this project](https://github.com/conductor-sdk/java-sdk-examples).

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
See [Access Control](https://orkes.io/content/docs/getting-started/concepts/access-control) for more details on role based access control with Conductor and generating API keys for your environment. 
[Detailed example](https://github.com/conductor-sdk/java-sdk-examples/blob/16d23ce13f7c400659d4ef7435f5f5f30bc6af88/src/main/java/io/orkes/samples/quickstart/ExecuteWorkflow.java#L48-L55).

```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api", "key", "secret");
```

---
### Next: [Create and run workers](docs/worker/README.md)
