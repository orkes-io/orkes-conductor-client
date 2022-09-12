package io.orkes.conductor.client;


import com.google.common.base.Preconditions;
import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.config.DefaultConductorClientConfiguration;
import com.netflix.conductor.client.http.ClientBase;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import io.orkes.conductor.client.http.MetadataClient;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class MetadataClientOSS extends ClientBase implements MetadataClient {

    /** Creates a default metadata client */
    public MetadataClientOSS() {
        this(new DefaultClientConfig(), new DefaultConductorClientConfiguration(), null);
    }

    /**
     * @param clientConfig REST Client configuration
     */
    public MetadataClientOSS(ClientConfig clientConfig) {
        this(clientConfig, new DefaultConductorClientConfiguration(), null);
    }

    /**
     * @param clientConfig REST Client configuration
     * @param clientHandler Jersey client handler. Useful when plugging in various http client
     *     interaction modules (e.g. ribbon)
     */
    public MetadataClientOSS(ClientConfig clientConfig, ClientHandler clientHandler) {
        this(clientConfig, new DefaultConductorClientConfiguration(), clientHandler);
    }

    /**
     * @param config config REST Client configuration
     * @param handler handler Jersey client handler. Useful when plugging in various http client
     *     interaction modules (e.g. ribbon)
     * @param filters Chain of client side filters to be applied per request
     */
    public MetadataClientOSS(ClientConfig config, ClientHandler handler, ClientFilter... filters) {
        this(config, new DefaultConductorClientConfiguration(), handler, filters);
    }

    /**
     * @param config REST Client configuration
     * @param clientConfiguration Specific properties configured for the client, see {@link
     *     ConductorClientConfiguration}
     * @param handler Jersey client handler. Useful when plugging in various http client interaction
     *     modules (e.g. ribbon)
     * @param filters Chain of client side filters to be applied per request
     */
    public MetadataClientOSS(
            ClientConfig config,
            ConductorClientConfiguration clientConfiguration,
            ClientHandler handler,
            ClientFilter... filters) {
        super(config, clientConfiguration, handler);
        for (ClientFilter filter : filters) {
            super.client.addFilter(filter);
        }
    }

    // Workflow Metadata Operations

    /**
     * Register a workflow definition with the server
     *
     * @param workflowDef the workflow definition
     */
    @Override
    public void registerWorkflowDef(WorkflowDef workflowDef) {
        Preconditions.checkNotNull(workflowDef, "Worfklow definition cannot be null");
        postForEntityWithRequestOnly("metadata/workflow", workflowDef);
    }

    /**
     * Updates a list of existing workflow definitions
     *
     * @param workflowDefs List of workflow definitions to be updated
     */
    @Override
    public void updateWorkflowDefs(List<WorkflowDef> workflowDefs) {
        Preconditions.checkNotNull(workflowDefs, "Workflow defs list cannot be null");
        put("metadata/workflow", null, workflowDefs);
    }

    /**
     * Retrieve the workflow definition
     *
     * @param name the name of the workflow
     * @param version the version of the workflow def
     * @return Workflow definition for the given workflow and version
     */
    @Override
    public WorkflowDef getWorkflowDef(String name, Integer version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "name cannot be blank");
        return getForEntity(
                "metadata/workflow/{name}",
                new Object[] {"version", version},
                WorkflowDef.class,
                name);
    }

    /**
     * Removes the workflow definition of a workflow from the conductor server. It does not remove
     * associated workflows. Use with caution.
     *
     * @param name Name of the workflow to be unregistered.
     * @param version Version of the workflow definition to be unregistered.
     */
    @Override
    public void unregisterWorkflowDef(String name, Integer version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Workflow name cannot be blank");
        Preconditions.checkNotNull(version, "Version cannot be null");
        delete("metadata/workflow/{name}/{version}", name, version);
    }

    // Task Metadata Operations

    /**
     * Registers a list of task types with the conductor server
     *
     * @param taskDefs List of task types to be registered.
     */
    @Override
    public void registerTaskDefs(List<TaskDef> taskDefs) {
        Preconditions.checkNotNull(taskDefs, "Task defs list cannot be null");
        postForEntityWithRequestOnly("metadata/taskdefs", taskDefs);
    }

    /**
     * Updates an existing task definition
     *
     * @param taskDef the task definition to be updated
     */
    @Override
    public void updateTaskDef(TaskDef taskDef) {
        Preconditions.checkNotNull(taskDef, "Task definition cannot be null");
        put("metadata/taskdefs", null, taskDef);
    }

    /**
     * Retrieve the task definition of a given task type
     *
     * @param taskType type of task for which to retrieve the definition
     * @return Task Definition for the given task type
     */
    @Override
    public TaskDef getTaskDef(String taskType) {
        Preconditions.checkArgument(StringUtils.isNotBlank(taskType), "Task type cannot be blank");
        return getForEntity("metadata/taskdefs/{tasktype}", null, TaskDef.class, taskType);
    }

    /**
     * Removes the task definition of a task type from the conductor server. Use with caution.
     *
     * @param taskType Task type to be unregistered.
     */
    @Override
    public void unregisterTaskDef(String taskType) {
        Preconditions.checkArgument(StringUtils.isNotBlank(taskType), "Task type cannot be blank");
        delete("metadata/taskdefs/{tasktype}", taskType);
    }
}
