package io.orkes.conductor.client.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.api.ClientTest;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.UpsertGroupRequest.RolesEnum;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.RegistrationUtil;

public class TaskQueuePermissionTests extends ClientTest {

    private static final int RETRY_LIMIT = 5;

    private final WorkflowClient workflowClient;
    private final MetadataClient metadataClient;
    private final AuthorizationClient authorizationClient;

    private final List<ConductorApplication> createdApplicationsToDelete;

    public TaskQueuePermissionTests() {
        workflowClient = super.orkesClients.getWorkflowClient();
        metadataClient = super.orkesClients.getMetadataClient();
        authorizationClient = super.orkesClients.getAuthorizationClient();
        createdApplicationsToDelete = new ArrayList<>();
    }

    @AfterEach
    public void cleanup() {
        createdApplicationsToDelete.forEach(
                application -> {
                    authorizationClient.deleteApplication(application.getId());
                    Assert.assertThrows(
                            ApiException.class,
                            () -> {
                                authorizationClient.getApplication(application.getId());
                            });
                });
        createdApplicationsToDelete.clear();
    }

    @Test
    public void testTaskQueuePermissionForUserWithPreAuthorizedRole() {
        var application = generateApplication(RolesEnum.USER);
        var workflowName = generateRandomStringWithPrefix("random_workflow_name_");
        var taskName = generateRandomStringWithPrefix("random_task_name_");
        var workflowId = startRandomWorkflow(workflowName, taskName);
        var taskClient = getOrkesClients(application).getTaskClient();
        waitUntilTaskIsPresentAtTaskQueue(taskClient, taskName, true);
        workflowClient.deleteWorkflow(workflowId, false);
        waitUntilTaskIsPresentAtTaskQueue(taskClient, taskName, false);
    }

    private static void waitUntilTaskIsPresentAtTaskQueue(TaskClient taskClient, String taskName,
            boolean isTaskRequired) {
        Map<String, Long> queueSizeMap = null;
        for (int retryCounter = 1; retryCounter <= RETRY_LIMIT; retryCounter += 1) {
            queueSizeMap = ((OrkesTaskClient) taskClient).getQueueAll();
            if (queueSizeMap != null && queueSizeMap.containsKey(taskName) == isTaskRequired) {
                break;
            }
            var sleepFor = 1 << retryCounter;
            System.out.println("sleeping for " + sleepFor + " seconds");
            try {
                Thread.sleep(sleepFor * 1000);
            } catch (InterruptedException e) {
            }
        }
        assertNotNull(queueSizeMap);
        assertEquals(queueSizeMap.containsKey(taskName), isTaskRequired);
        if (isTaskRequired) {
            assertEquals(queueSizeMap.get(taskName), 1);
        }
    }

    private String startRandomWorkflow(String workflowName, String taskName) {
        RegistrationUtil.registerWorkflowDef(
                workflowName,
                generateRandomStringWithPrefix("random_task_name_"),
                taskName,
                metadataClient);
        var startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        startWorkflowRequest.setVersion(1);
        return workflowClient.startWorkflow(startWorkflowRequest);
    }

    private ConductorApplication generateApplication(RolesEnum role) {
        var applicationName = generateRandomStringWithPrefix(
                "random_application_name_");
        var request = new CreateOrUpdateApplicationRequest().name(applicationName);
        var application = authorizationClient.createApplication(request);
        assertNotNull(application);
        this.createdApplicationsToDelete.add(application);
        authorizationClient.addRoleToApplicationUser(
                application.getId(), role.getValue());
        return application;
    }

    private OrkesClients getOrkesClients(ConductorApplication application) {
        var accessKeyResponse = authorizationClient.createAccessKey(application.getId());
        var apiClient = new ApiClient(
                ApiUtil.getBasePath(),
                accessKeyResponse.getId(),
                accessKeyResponse.getSecret());
        return new OrkesClients(apiClient);
    }

    private String generateRandomStringWithPrefix(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
