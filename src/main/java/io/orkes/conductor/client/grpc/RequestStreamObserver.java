package io.orkes.conductor.client.grpc;

import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.proto.WorkflowModelProtoMapper;
import io.grpc.stub.StreamObserver;
import io.orkes.conductor.proto.WorkflowRun;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestStreamObserver implements StreamObserver<WorkflowRun> {

    private final WorkflowModelProtoMapper protoMapper;

    private final WorkflowMonitor workflowMonitor = WorkflowMonitor.getInstance();

    public RequestStreamObserver(WorkflowModelProtoMapper protoMapper) {
        this.protoMapper = protoMapper;
    }

    @Override
    public void onNext(WorkflowRun result) {
        com.netflix.conductor.common.run.WorkflowRun workflowRun = fromProto(result);
        workflowMonitor.notifyCompletion(workflowRun);
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getMessage(), t);

    }

    @Override
    public void onCompleted() {
    }

    private com.netflix.conductor.common.run.WorkflowRun fromProto(WorkflowRun proto) {
        com.netflix.conductor.common.run.WorkflowRun run = new com.netflix.conductor.common.run.WorkflowRun();
        run.setWorkflowId(proto.getWorkflowId());
        run.setCreatedBy(proto.getCreatedBy());
        run.setCreateTime(proto.getCreateTime());
        run.setPriority(proto.getPriority());
        run.setCorrelationId(proto.getCorrelationId());
        run.setInput(protoMapper.convertToJavaMap(proto.getInput()));
        run.setOutput(protoMapper.convertToJavaMap(proto.getOutput()));
        run.setStatus(Workflow.WorkflowStatus.valueOf(proto.getStatus().name()));
        run.setVariables(protoMapper.convertToJavaMap(proto.getVariables()));
        run.setRequestId(proto.getRequestId());

        return run;
    }
}
