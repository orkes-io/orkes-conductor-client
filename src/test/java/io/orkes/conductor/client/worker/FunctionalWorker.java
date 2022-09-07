package io.orkes.conductor.client.worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.ApplicationResourceApi;
import io.orkes.conductor.client.http.api.GroupResourceApi;
import io.orkes.conductor.client.http.api.MetadataResourceApi;
import io.orkes.conductor.client.http.api.WorkflowResourceApi;
import io.orkes.conductor.client.util.ApiUtil;

public class FunctionalWorker {
    MetadataResourceApi metadataResourceApi;
    GroupResourceApi groupResourceApi;

    ApplicationResourceApi applicationResourceApi;

    WorkflowResourceApi workflowResourceApi;

    @BeforeEach
    public void init() {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        metadataResourceApi = new MetadataResourceApi(apiClient);
        groupResourceApi = new GroupResourceApi(apiClient);
        applicationResourceApi = new ApplicationResourceApi(apiClient);
        workflowResourceApi = new WorkflowResourceApi(apiClient);
    }

    @Test
    @DisplayName("Test workflow completion")
    public void workflow() {

    }
}
