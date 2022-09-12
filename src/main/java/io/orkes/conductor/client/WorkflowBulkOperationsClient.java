package io.orkes.conductor.client;

import com.netflix.conductor.common.model.BulkResponse;
import com.squareup.okhttp.Call;
import io.orkes.conductor.client.http.ApiCallback;

import java.util.List;

public interface WorkflowBulkOperationsClient {
    BulkResponse pauseWorkflow(List<String> body);

    Call pauseWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback);

    BulkResponse restartWorkflow(List<String> body, Boolean useLatestDefinitions)
           ;

    Call restartWorkflowAsync(List<String> body, Boolean useLatestDefinitions, ApiCallback<BulkResponse> callback)
           ;

    BulkResponse resumeWorkflow(List<String> body);

    Call resumeWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback);

    BulkResponse retryWorkflow(List<String> body);
    Call retryWorkflowAsync(List<String> body, ApiCallback<BulkResponse> callback);

    BulkResponse terminateWorkflow(List<String> body, String reason);

    Call terminateWorkflowAsync(List<String> body, String reason, ApiCallback<BulkResponse> callback);
}
