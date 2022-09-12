package io.orkes.conductor.client.http.client;

import com.netflix.conductor.common.model.BulkResponse;
import com.squareup.okhttp.Call;
import io.orkes.conductor.client.http.ApiCallback;
import io.orkes.conductor.client.http.ApiException;

import java.util.List;

public interface WorkflowBulkOperationsClient {
    BulkResponse pauseWorkflow(List<String> body) throws ApiException;

    Call pauseWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback) throws ApiException;

    BulkResponse restartWorkflow(List<String> body, Boolean useLatestDefinitions)
            throws ApiException;

    Call restartWorkflowAsync(List<String> body, Boolean useLatestDefinitions, ApiCallback<BulkResponse> callback)
            throws ApiException;

    BulkResponse resumeWorkflow(List<String> body) throws ApiException;

    Call resumeWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback) throws ApiException;

    BulkResponse retryWorkflow(List<String> body) throws ApiException;
    Call retryWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback) throws ApiException;

    BulkResponse terminateWorkflow(List<String> body, String reason) throws ApiException;

    Call terminateWorkflowAsync(List<String> body, String reason, ApiCallback<BulkResponse> callback) throws ApiException;
}
