package io.orkes.conductor.client;

import io.orkes.conductor.client.http.*;

public class OrkesClients {

    private final ApiClient apiClient;

    public OrkesClients(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public WorkflowClient getWorkflowClient() {
        return new OrkesWorkflowClient(apiClient);
    }

    public AuthorizationClient getAuthorizationClient() {
        return new OrkesAuthorizationClient(apiClient);
    }

    public EventClient getEventClient() {
        return new OrkesEventClient(apiClient);
    }


    public MetadataClient getMetadataClient() {
        return new OrkesMetadataClient(apiClient);
    }

    public SchedulerClient getSchedulerClient() {
        return new OrkesSchedulerClient(apiClient);
    }

    public SecretClient getSecretClient() {
        return new OrkesSecretClient(apiClient);
    }

    public TaskClient getTaskClient() {
        return new OrkesTaskClient(apiClient);
    }

}
