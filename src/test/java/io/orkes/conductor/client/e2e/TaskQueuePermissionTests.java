package io.orkes.conductor.client.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.model.UpsertGroupRequest.RolesEnum;
import io.orkes.conductor.client.util.RegistrationUtil;
import io.orkes.conductor.client.util.authentication.TestWithAuthentication;

public class TaskQueuePermissionTests extends TestWithAuthentication {

    private static final int RETRY_LIMIT = 5;

    private final WorkflowClient workflowClient;
    private final MetadataClient metadataClient;

    public TaskQueuePermissionTests() {
        workflowClient = orkesClients.getWorkflowClient();
        metadataClient = orkesClients.getMetadataClient();
    }

    @Test
    public void testTaskQueuePermissionForUserWithPreAuthorizedRole() {
        var adminOrkesClients = getOrkesClientsWithRole(RolesEnum.ADMIN);
        var taskClient = adminOrkesClients.getTaskClient();

        var workflowName = generateRandomStringWithPrefix("random_workflow_name_");
        var taskName = generateRandomStringWithPrefix("random_task_name_");

        var workflowId = startRandomWorkflow(workflowName, taskName);

        Map<String, Long> queueSizeMap = null;

        for (int retryCounter = 1; retryCounter <= RETRY_LIMIT; retryCounter += 1) {
            queueSizeMap = ((OrkesTaskClient) taskClient).getQueueAll();
            if (queueSizeMap.containsKey(taskName)) {
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
        assertTrue(queueSizeMap.containsKey(taskName));
        assertEquals(queueSizeMap.get(taskName), 1);

        workflowClient.deleteWorkflow(workflowId, false);

        for (int retryCounter = 1; retryCounter <= RETRY_LIMIT; retryCounter += 1) {
            queueSizeMap = ((OrkesTaskClient) taskClient).getQueueAll();
            if (!queueSizeMap.containsKey(taskName)) {
                break;
            }
            var sleepFor = 1 << retryCounter;
            System.out.println("sleeping for " + sleepFor + " seconds");
            try {
                Thread.sleep(sleepFor * 1000);
            } catch (InterruptedException e) {
            }
        }

        assertFalse(queueSizeMap.containsKey(taskName));
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

}
