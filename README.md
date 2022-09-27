# Netflix Conductor SDK

`conductor-java` repository provides the client SDKs to build Task Workers in Java

## Quick Start

1. [Setup package](#Setup-conductor-python-package)
2. [Create and run task workers](docs/worker/README.md)
3. [Create workflows using code](docs/workflow/README.md)

### Setup package

### Server settings
Everything related to server settings should be done within `Configuration` class, by setting the required parameter when initializing an object, like this:

```java
// TODO Add code sample
```

#### Authentication settings (optional)
Use if your conductor server requires authentication.

##### Access Control Setup
See [Access Control](https://orkes.io/content/docs/getting-started/concepts/access-control) for more details on role based access control with Conductor and generating API keys for your environment.

```java
// TODO Add code sample
```
``

### Next: [Create and run Task Workers](docs/worker/README.md)