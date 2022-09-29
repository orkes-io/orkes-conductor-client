package io.orkes.conductor.client.grpc;

import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.WorkflowRun;
import com.netflix.conductor.grpc.ProtoMapper;
import com.netflix.conductor.proto.StartWorkflowRequestPb;
import com.netflix.conductor.proto.WorkflowModelProtoMapper;
import io.grpc.stub.StreamObserver;
import io.orkes.conductor.client.ApiClient;
import io.orkes.grpc.service.WorkflowServiceGrpc;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;


public class GrpcWorkflowClient {

    private final WorkflowServiceGrpc.WorkflowServiceStub stub;

    private final RequestStreamObserver responses;

    private StreamObserver<StartWorkflowRequestPb.StartWorkflowRequest> requests;

    private WorkflowModelProtoMapper protoMapper;

    private final WorkflowMonitor workflowMonitor = WorkflowMonitor.getInstance();

    public GrpcWorkflowClient(ApiClient apiClient) {
        this.stub = WorkflowServiceGrpc.newStub(getChannel(apiClient)).withCallCredentials(new AuthToken(apiClient));
        this.protoMapper = new WorkflowModelProtoMapper(new ObjectMapperProvider().getObjectMapper());
        this.responses = new RequestStreamObserver(protoMapper);
        this.requests = this.stub.executeWorkflow(responses);
    }

    public CompletableFuture<WorkflowRun> executeWorkflow(StartWorkflowRequest request) {
        String requestId = UUID.randomUUID().toString();
        request.getInput().put("_x-request-id", requestId);
        StartWorkflowRequestPb.StartWorkflowRequest requestPb = ProtoMapper.INSTANCE.toProto(request);
        CompletableFuture<WorkflowRun> future = workflowMonitor.add(requestId);
        requests.onNext(requestPb);
        return future;
    }
}
