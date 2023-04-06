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

import java.util.Iterator;
import java.util.List;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;
import com.netflix.conductor.proto.TaskPb;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.proto.ProtoMappingHelper;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import io.grpc.ManagedChannel;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

public class GrpcTaskClient implements AutoCloseable {
    private final ManagedChannel channel;

    private final TaskServiceGrpc.TaskServiceBlockingStub stub;

    private final ProtoMappingHelper protoMapper = ProtoMappingHelper.INSTANCE;

    public GrpcTaskClient(ApiClient apiClient) {
        this.channel = getChannel(apiClient);
        this.stub =
                TaskServiceGrpc.newBlockingStub(this.channel)
                        .withInterceptors(new HeaderClientInterceptor(apiClient));
    }

    public List<Task> batchPoll(
            String taskType, String workerId, String domain, int count, int timeoutInMillisecond) {
        TaskServicePb.BatchPollRequest.Builder requestBuilder =
                TaskServicePb.BatchPollRequest.newBuilder()
                        .setCount(count)
                        .setTaskType(taskType)
                        .setTimeout(timeoutInMillisecond)
                        .setWorkerId(workerId);
        if (domain != null) {
            requestBuilder = requestBuilder.setDomain(domain);
        }
        TaskServicePb.BatchPollRequest request = requestBuilder.build();
        Iterator<TaskPb.Task> tasks = this.stub.batchPoll(request);
        return Lists.newArrayList(Iterators.transform(tasks, protoMapper::fromProto));
    }

    public void updateTask(TaskResult taskResult) {
        stub.updateTask(TaskServicePb.UpdateTaskRequest.newBuilder().setResult(protoMapper.toProto(taskResult)).build());
    }

    @Override
    public void close() throws Exception {
        if(this.channel != null) {
            try {
                this.channel.shutdown();
            }catch (Throwable t) {}
        }
    }
}
