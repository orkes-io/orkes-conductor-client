package io.orkes.conductor.client;

import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.conductor.client.http.OrkesClient;
import io.orkes.conductor.client.http.OrkesTaskClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String CONDUCTOR_SERVER_URL = "conductor.server.url";
    private static final String CONDUCTOR_CLIENT_KEY_ID = "conductor.security.client.key-id";
    private static final String CONDUCTOR_CLIENT_SECRET = "conductor.security.client.secret";

    private final Environment env;

    public Main(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting main...");
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public TaskClient taskClient() {
        String rootUri = env.getProperty(CONDUCTOR_SERVER_URL);
        OrkesTaskClient taskClient = new OrkesTaskClient();
        taskClient.setRootURI(rootUri);
        setCredentialsIfPresent(taskClient);
        return taskClient;
    }

    private void setCredentialsIfPresent(OrkesClient client) {
        String keyId = env.getProperty(CONDUCTOR_CLIENT_KEY_ID);
        String secret = env.getProperty(CONDUCTOR_CLIENT_SECRET);
        if ("_CHANGE_ME_".equals(keyId) || "_CHANGE_ME_".equals(secret)) {
            throw new RuntimeException("No Application Key");
        }
        if (!StringUtils.isBlank(keyId) && !StringUtils.isBlank(secret)) {
            client.withCredentials(keyId, secret);
        }
    }

    @Bean
    public List<Worker> workersList() {
        return List.of(new JavaWorker());
    }

    @Bean
    public TaskRunnerConfigurer taskRunnerConfigurer(List<Worker> workersList, TaskClient taskClient) {
        TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer.Builder(taskClient, workersList)
                .withTaskToThreadCount(
                        Map.of(
                                "java_task_example", 5))
                .build();
        runnerConfigurer.init();
        return runnerConfigurer;
    }

    class JavaWorker implements Worker {
        public String getTaskDefName() {
            return "java_task_example";
        }

        public TaskResult execute(Task task) {
            TaskResult result = new TaskResult(task);
            result.setStatus(TaskResult.Status.COMPLETED);
            result.addOutputData("message", "Hello World!");
            return result;
        }
    }
}
