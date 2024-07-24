# Using Conductor in Your Application

Conductor SDKs are lightweight and can easily be added to your existing or new Java app. This section will dive deeper into integrating Conductor in your application.

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

- [Adding Conductor SDK to Your Application](#adding-conductor-sdk-to-your-application)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Testing Workflows](#testing-workflows)
  - [Gradle](#gradle-1)
  - [Maven](#maven-1)
  - [Example Unit Testing Application](#example-unit-testing-application)
- [Workflow Deployments Using CI/CD](#workflow-deployments-using-cicd)
- [Versioning Workflows](#versioning-workflows)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Adding Conductor SDK to Your Application

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

## Testing Workflows

Conductor SDK for Java provides a complete feature testing framework for your workflow-based applications. The framework works well with any testing framework you prefer without imposing any specific framework.

The Conductor server provides a test endpoint `POST /api/workflow/test` that allows you to post a workflow along with the test execution data to evaluate the workflow.

The goal of the test framework is as follows:

1. Ability to test the various branches of the workflow.
2. Confirm the workflow execution and tasks given a fixed set of inputs and outputs.
3. Validate that the workflow completes or fails given specific inputs.

Here are example assertions from the test:

```java
import static org.junit.Assert.*;

Workflow workflowRun = workflowExecution.get(10, TimeUnit.SECONDS);
String status = String.valueOf(workflowRun.getStatus());
assertEquals(status,"COMPLETED");
```
You can add the JUnit dependency by adding the following to your project:

### Gradle

For Gradle-based projects, modify the `build.gradle` file in the project directory by adding the following line to the dependencies block in that file:

```
testImplementation "org.junit.jupiter:junit-jupiter-api:{{VERSION}}"
testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:{{VERSION}}"
```

### Maven

For Maven-based projects, modify the `pom.xml` file in the project directory by adding the following XML snippet within the `dependencies` section:

```
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>{{VERSION}}</version>
  <scope>test</scope>
</dependency>
```

>[!note]
>Workflow workers are your regular Java functions and can be tested with any available testing framework.


## Workflow Deployments Using CI/CD

>[!tip]
>Treat your workflow definitions just like your code. Suppose you are defining the workflows using UI. In that case, we recommend checking the JSON configuration into the version control and using your development workflow for CI/CD to promote the workflow definitions across various environments such as Dev, Test, and Prod.

Here is a recommended approach when defining workflows using JSON:

- Treat your workflow metadata as code.
- Check in the workflow and task definitions along with the application code.
- Use `POST /api/metadata/* endpoints` or MetadataClient(com.conductor.client.MetadataClient) to register/update workflows as part of the deployment process.
- Version your workflows. If there is a significant change, change the version field of the workflow. See versioning workflows below for more details.

## Versioning Workflows

A powerful feature of Conductor is the ability to version workflows. You should increment the version of the workflow when there is a significant change to the definition. You can run multiple versions of the workflow at the same time. When starting a new workflow execution, use the `version` field to specify which version to use. When omitted, the latest (highest-numbered) version is used.

- Versioning allows safely testing changes by doing canary testing in production or A/B testing across multiple versions before rolling out.
- A version can also be deleted, effectively allowing for "rollback" if required.
