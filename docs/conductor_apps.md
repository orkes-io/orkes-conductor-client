# Using Conductor in Your Application

Conductor SDKs are very lightweight and can easily be added to your existing or new Java app. In this section, we will dive deeper into integrating Conductor in your application.

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Adding Conductor SDK to your application

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

## Testing your workflows

Conductor SDK for Java provides a full feature testing framework for your workflow-based applications. The framework works well with any testing framework you prefer to use without imposing any specific framework.

The Conductor server provide a test endpoint `POST /api/workflow/test` that allows you to post a workflow along with the test execution data to evaluate the workflow.

The goal of the test framework is as follows:

1. Ability to test the various branches of the workflow.
2. Confirm the execution of the workflow and tasks given a fixed set of inputs and outputs.
3. Validate that the workflow completes or fails given specific inputs.

Here are example assertions from the test:

```java
import static org.junit.Assert.*;

Workflow workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);
String status = String.valueOf(workflowRun.getStatus());
assertEquals(status,"COMPLETED");
```

>[!note]
>Workflow workers are your regular Java functions and can be tested with any of the available testing frameworks.

### Example Unit Testing application

See `test_workflows.java` for a fully functional example of how to test a moderately complex workflow with branches.

## Workflow deployments using CI/CD

>[!tip]
>Treat your workflow definitions just like your code. Suppose you are defining the workflows using UI. In that case, we recommend checking the JSON configuration into the version control and using your development workflow for CI/CD to promote the workflow definitions across various environments such as Dev, Test, and Prod.

Here is a recommended approach when defining workflows using JSON:

- Treat your workflow metadata as code.
- Check in the workflow and task definitions along with the application code.
- Use `POST /api/metadata/* endpoints` or `To Do` to register/update workflows as part of the deployment process.
- Version your workflows. If there is a significant change, change the version field of the workflow. See versioning workflows below for more details.

## Versioning workflows

A powerful feature of Conductor is the ability to version workflows. You should increment the version of the workflow when there is a significant change to the definition. You can run multiple versions of the workflow at the same time. When starting a new workflow execution, use the `version` field to specify which version to use. When omitted, the latest (highest-numbered) version is used.

- Versioning allows safely testing changes by doing canary testing in production or A/B testing across multiple versions before rolling out.
- A version can also be deleted, effectively allowing for "rollback" if required.