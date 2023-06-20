package io.orkes.conductor.client.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.sdk.examples.ApiUtil;

import java.util.List;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class TaskToDomainTests {
    @Test
    public void testDomainPropagation() throws Exception {
        List<String> taskNames = List.of(
                "EMAIL_NOTIFICATION",
                "email_notification",
                "DATA_PROCESSING");
        List<Worker> workers = new LinkedList<>();
        taskNames.forEach(
                (taskName) -> {
                    workers.add(new TestWorker(taskName));
                });
        var orkesClients = ApiUtil.getOrkesClient();
        var taskClient = orkesClients.getTaskClient();
        TaskRunnerConfigurer configurer = new TaskRunnerConfigurer.Builder((OrkesTaskClient) taskClient, workers)
                .withThreadCount(1)
                .withTaskPollTimeout(10)
                .build();
        configurer.init();
        Thread.sleep(5000);
    }

    private static class TestWorker implements Worker {

        private String name;

        public TestWorker(String name) {
            this.name = name;
        }

        @Override
        public String getTaskDefName() {
            return name;
        }

        @Override
        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult(task);
            result.getOutputData().put("number", 42);
            result.setStatus(TaskResult.Status.COMPLETED);
            return result;
        }

        @Override
        public int getPollingInterval() {
            return 1;
        }
    }

}
