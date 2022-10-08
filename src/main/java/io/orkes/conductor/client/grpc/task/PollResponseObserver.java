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
package io.orkes.conductor.client.grpc.task;

import com.netflix.conductor.grpc.TaskServicePb;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PollResponseObserver implements StreamObserver<TaskServicePb.PollResponse> {

    private boolean ready = true;

    @Override
    public void onNext(TaskServicePb.PollResponse response) {
        log.debug("got task to execute {}", response.getTask().getTaskId());
        // Execute the task here
        // Send to another BiDi stream for update
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        Status.Code code = status.getCode();
        switch (code) {
            case UNAVAILABLE:
                log.trace("Server not available ");
                ready = false;
                break;
            case UNAUTHENTICATED:
                log.error("{} - Invalid or missing api key/secret", code);
                break;
            case CANCELLED:
            case ABORTED:
            case DATA_LOSS:
            case DEADLINE_EXCEEDED:
                break;
            default:
                log.error("Error from server when polling for the task {} - {}", code);
        }
    }

    @Override
    public void onCompleted() {}

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        ready = true;
    }
}
