package io.orkes.conductor.client.grpc;


import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.grpc.TaskServiceGrpc;
import com.netflix.conductor.grpc.TaskServicePb;
import com.netflix.conductor.proto.TaskPb;
import com.netflix.conductor.proto.compatibility.ProtoMapper;
import io.grpc.ManagedChannel;
import io.orkes.conductor.client.ApiClient;

import java.util.Iterator;
import java.util.List;

import static io.orkes.conductor.client.grpc.ChannelManager.getChannel;

public class GrpcTaskClient {
    private final ManagedChannel channel;

    private final TaskServiceGrpc.TaskServiceBlockingStub stub;

    private final ProtoMapper protoMapper = ProtoMapper.INSTANCE;

    public GrpcTaskClient(ApiClient apiClient) {
        this.channel = getChannel(apiClient);
        this.stub = TaskServiceGrpc.newBlockingStub(this.channel).withCallCredentials(new AuthToken(apiClient));
    }

    public List<Task> batchPoll(
            String taskType, String workerId, String domain, int count, int timeoutInMillisecond) {
        TaskServicePb.BatchPollRequest.Builder requestBuilder = TaskServicePb.BatchPollRequest.newBuilder()
                .setCount(count)
                .setTaskType(taskType)
                .setTimeout(timeoutInMillisecond)
                .setWorkerId(workerId);
        if(domain != null) {
            requestBuilder = requestBuilder.setDomain(domain);
        }
        TaskServicePb.BatchPollRequest request = requestBuilder.build();
        Iterator<TaskPb.Task> tasks = this.stub.batchPoll(request);
        return Lists.newArrayList(Iterators.transform(tasks, protoMapper::fromProto));
    }

    public void updateTask(TaskResult taskResult) {
        stub.updateTask(
                TaskServicePb.UpdateTaskRequest.newBuilder()
                        .setResult(protoMapper.toProto(taskResult))
                        .build());
    }
}
