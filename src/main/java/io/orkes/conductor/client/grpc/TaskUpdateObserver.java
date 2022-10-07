package io.orkes.conductor.client.grpc;

import com.netflix.conductor.grpc.TaskServicePb;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskUpdateObserver implements StreamObserver<TaskServicePb.UpdateTaskResponse> {

    @Override
    public void onNext(TaskServicePb.UpdateTaskResponse response) {
        log.debug("Task update {} successful", response.getTaskId());
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case CANCELLED:
            case ABORTED:
            case DATA_LOSS:
            case DEADLINE_EXCEEDED:
                log.error("Got {} when updating task, will retry", code);
                break;
            default:
                log.error("Error from server when updating the task {}", code);
        }
    }

    @Override
    public void onCompleted() {

    }
}
