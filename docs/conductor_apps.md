# Using Conductor in your Application

Conductor SDKs are very lightweight and can easily be added to your existing or a new python app. In this section, we will dive deeper into integrating Conductor in your application.

## Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Adding Conductor SDK to your application

```java
To Do
```

## Testing your workflows

Conductor SDK for Java provides a full feature testing framework for your workflow based applications. The framework works well with any testing framework you prefer to use without imposing any specific framework.

Conductor server provide a test endpoint `POST /api/workflow/test` that allows you to post a workflow along with the test execution data to evaluate the workflow.

The goal of the test framework is as follows:

1. Ability test the various branches of the workflow.
2. Confirm the execution of the workflow and tasks given fixed set of inputs and outputs.
3. Validate that the workflow completes or fails given specific inputs.

Here is example assertions from the test:

```java
To Do
```

>[!note]
>Workflow workers are your regular java functions and can be tested with any of the available testing frameworks.

### Example Unit Testing application

See `test_workflows.java` for a fully functional example on how to test a moderately complex workflow with branches.

## Workflow deployments using CI/CD

>[!tip]
>Treat your workflow definitions just like your code. If you are defining the workflows using UI, we recommend checking in the JSON configuration into the version control and using your development workflow for CI/CD to promote the workflow definitions across various environments such as Dev, Test and Prod.

Here is a recommended approach when defining workflows using JSON:

- Treat your workflow metadata as code.
- Check in the workflow and task definitions along with the application code.
- Use `POST /api/metadata/* endpoints` or `To Do` to register/update workflows as part of the deployment process.
- Version your workflows. If there is a significant change, change the version field of the workflow. See versioning workflows below for more details.

## Versioning workflows

A powerful feature of Conductor is ability to version workflows. You should increment the version of the workflow when there is a significant change to the definition. You can run multiple versions of the workflow at the same time. When starting a new workflow execution, use the `version` field to specify which version to use. When omitted, the latest (highest numbered) version is used.

- Versioning allows safely testing changes by doing canary testing in production or A/B testing across multiple versions before rolling out.
- A version can be deleted as well, effectively allowing for "rollback" if required.