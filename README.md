# Netflix Conductor SDK

`conductor-java` repository provides the client SDKs to build Task Workers in Java

## Quick Start

1. [Setup package](#Setup-conductor-python-package)
2. [Create and run task workers](docs/worker/README.md)
3. [Create workflows using code](docs/workflow/README.md)

### Setup package

Check out [this example project](https://github.com/conductor-sdk/java-sdk-examples), go ahead and create a fork!

### Conductor server settings
Everything related to server settings should be done within the `ApiClient` class, by setting the required parameters when initializing an object, like this:

```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api");
```

#### Authentication settings (optional)
Use if your conductor server requires authentication.

##### Access Control Setup
See [Access Control](https://orkes.io/content/docs/getting-started/concepts/access-control) for more details on role based access control with Conductor and generating API keys for your environment.

```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api", "key", "secret");
```

### Next: [Create and run Task Workers](docs/worker/README.md)
