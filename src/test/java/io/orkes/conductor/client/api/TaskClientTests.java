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
package io.orkes.conductor.client.api;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.util.Commons;

import static org.junit.jupiter.api.Assertions.*;

public class TaskClientTests extends ClientTest {
    private final TaskClient taskClient;

    public TaskClientTests() {
        this.taskClient = super.orkesClients.getTaskClient();
    }

    @Test
    void testMethods() {
        assertEquals(null, taskClient.pollTask(Commons.TASK_NAME, "", ""));
        assertNotNull(taskClient.batchPollTasksByTaskType(Commons.TASK_NAME, "", 10, 500));
        assertEquals(
                Optional.empty(),
                taskClient.evaluateAndUploadLargePayload(new HashMap<>(), Commons.TASK_NAME));
        assertEquals(0, taskClient.getQueueSizeForTask(Commons.TASK_NAME));
        assertEquals(0, taskClient.getQueueSizeForTask(Commons.TASK_NAME, "", "", ""));
        taskClient.requeuePendingTasksByTaskType(Commons.TASK_NAME);
        String randomTaskId = "asdbwhbejqwhbejqhb";
        assertEquals(null, taskClient.getTaskDetails(randomTaskId));
        assertNotNull(taskClient.getTaskLogs(randomTaskId));
        taskClient.logMessageForTask(randomTaskId, "logMessage");
    }

    @Test
    void testUnsupportedMethods() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.ack("", "");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.getPollData("");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.getAllPollData();
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.requeueAllPendingTasks();
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.search("");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.searchV2("");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.search(0, 0, "", "", "");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.searchV2(0, 0, "", "", "");
                });
        assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    taskClient.removeTaskFromQueue("", "");
                });
    }
}
