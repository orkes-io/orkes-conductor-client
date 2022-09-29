/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.grpc;

import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.proto.WorkflowModelProtoMapper;

import io.orkes.conductor.proto.WorkflowRun;

import io.grpc.stub.StreamObserver;
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
        io.orkes.conductor.client.model.WorkflowRun workflowRun = fromProto(result);
        workflowMonitor.notifyCompletion(workflowRun);
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getMessage(), t);
    }

    @Override
    public void onCompleted() {}

    private io.orkes.conductor.client.model.WorkflowRun fromProto(WorkflowRun proto) {
        io.orkes.conductor.client.model.WorkflowRun run =
                new io.orkes.conductor.client.model.WorkflowRun();
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
