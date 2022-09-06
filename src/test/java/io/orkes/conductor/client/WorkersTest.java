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
package io.orkes.conductor.client;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import static org.junit.jupiter.api.Assertions.*;

class WorkersTest {

    public static void main(String[] args) {
        Workers workers = new Workers();
        workers.register(
                "simple_task_0",
                task -> {
                    task.setStatus(Task.Status.COMPLETED);
                    task.getOutputData().put("key", "value");
                    task.getOutputData().put("key2", 42);
                    return new TaskResult(task);
                });
        workers.rootUri("https://pg-staging.orkesconductor.com/api");
        workers.keyId("");
        workers.secret("");

        workers.startAll();
    }
}
