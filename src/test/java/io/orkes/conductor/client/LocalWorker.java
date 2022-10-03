package io.orkes.conductor.client;


import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalWorker {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("https://orkes-loadtest.orkesconductor.com/api", "7478920f-e032-48cc-a033-2544f56a346c", "ctTYzti53Es5bULUzKG1m5h2ZUa49fCJ4NPMI0720MTe1JJq");
        apiClient = new ApiClient();
        OrkesClients clients = new OrkesClients(apiClient);
        WorkflowClient workflowClient = clients.getWorkflowClient();
        TaskClient taskClient = clients.getTaskClient();


        //Add a list with Worker implementations
        List<Worker> workers = new ArrayList<>();
        Map<String, Integer> taskThreadCount = new HashMap<>();
        Map<String, String> taskToDomain = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            Worker worker = new MyWorker("simple_task_" + i);
            workers.add(worker);
            taskToDomain.put(worker.getTaskDefName(), "viren");
            taskThreadCount.put(worker.getTaskDefName(), 10);
        }

        TaskRunnerConfigurer.Builder builder = new TaskRunnerConfigurer.Builder(taskClient, workers);

        TaskRunnerConfigurer taskRunner = builder
                .withTaskThreadCount(taskThreadCount)
                .withTaskPollTimeout(1)
                .withWorkerNamePrefix("worker-")    //Thread name prefix for the task worker executor. Useful for logging
                .build();

        //Start Polling for tasks and execute them
        taskRunner.init();


    }

    private static class MyWorker implements Worker {

        private final String name;

        private MyWorker(String name) {
            this.name = name;
        }

        @Override
        public String getTaskDefName() {
            return name;
        }

        @Override
        public int getPollingInterval() {
            return 1;
        }

        @Override
        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult(task);
            result.setStatus(TaskResult.Status.COMPLETED);
            return result;
        }
    }
}
