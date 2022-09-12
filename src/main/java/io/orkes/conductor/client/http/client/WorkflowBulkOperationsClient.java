package io.orkes.conductor.client.http.client;

import com.netflix.conductor.common.model.BulkResponse;
import io.orkes.conductor.client.http.ApiException;

import java.util.List;

public interface WorkflowBulkOperationsClient {
    BulkResponse pauseWorkflow(List<String> body) throws ApiException;

    BulkResponse restartWorkflow(List<String> body, Boolean useLatestDefinitions)
            throws ApiException;

    BulkResponse resumeWorkflow(List<String> body) throws ApiException;

    BulkResponse retryWorkflow(List<String> body) throws ApiException;

    BulkResponse terminateWorkflow(List<String> body, String reason) throws ApiException;
}
