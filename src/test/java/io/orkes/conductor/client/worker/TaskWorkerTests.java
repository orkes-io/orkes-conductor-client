package io.orkes.conductor.client.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.OrkesTaskClient;

import java.util.*;

public class TaskWorkerTests {
    public static void main(String[] args) {


        ApiClient apiClient = new ApiClient("http://localhost:8080/api");
        TaskClient taskClient = new OrkesClients(apiClient).getTaskClient();


        Iterable<Worker> workers = Arrays.asList(new MyWorker());
        TaskRunnerConfigurer.Builder builder = new TaskRunnerConfigurer.Builder(taskClient, workers);

        Map<String, String> taskToDomain = new HashMap<>();
        taskToDomain.put("simple_task_0", "viren");
        TaskRunnerConfigurer configuruer = builder.withSleepWhenRetry(100)
                .withThreadCount(10)
                .withWorkerNamePrefix("Hello")
                .withTaskToDomain(taskToDomain)
                .build();
        configuruer.init();

    }

    private static class MyWorker implements Worker {

        @Override
        public String getTaskDefName() {
            return "simple_task_0";
        }

        @Override
        public TaskResult execute(Task task) {
            System.out.println("Executing " + task.getTaskId() + ":" + task.getPollCount() + "::" + new Date());
            TaskResult result = new TaskResult(task);
            result.getOutputData().put("a", "b");
            if(task.getPollCount() < 2) {
                result.setCallbackAfterSeconds(5);
            } else {
                result.setStatus(TaskResult.Status.COMPLETED);
            }
            return result;
        }
    }
}
