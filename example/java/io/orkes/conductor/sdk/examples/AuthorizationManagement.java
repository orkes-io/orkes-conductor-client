package io.orkes.conductor.sdk.examples;

import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.model.AuthorizationRequest;
import io.orkes.conductor.client.model.SubjectRef;
import io.orkes.conductor.client.model.TargetRef;
import io.orkes.conductor.client.model.UpsertGroupRequest;
import io.orkes.conductor.client.model.UpsertUserRequest;

import java.util.Arrays;
import java.util.UUID;

import static io.orkes.conductor.sdk.examples.MetadataManagement.workflowDef;

public class AuthorizationManagement {

    private static AuthorizationClient authorizationClient;

    public static void main(String a[]) {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        createMetadata();
        authorizationClient = orkesClients.getAuthorizationClient();
    }

    private void userAndGroupOperations() {
        // Create users
        String userId = createUser("user1");
        String userId2 = createUser("user2");
        String userId3 = createUser("user3");
        // Create groups
        String groupId = createGroup("group1");
        String groupId2 = createGroup("group2");
        //Add users to group 1
        authorizationClient.addUserToGroup(groupId, userId);
        authorizationClient.addUserToGroup(groupId, userId2);
        authorizationClient.addUserToGroup(groupId2, userId2);
        authorizationClient.addUserToGroup(groupId2, userId3);

        // Remove user from group
        authorizationClient.removeUserFromGroup(groupId, userId2);

        // Add workflow and task execution permissions to the group
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setAccess(Arrays.asList(AuthorizationRequest.AccessEnum.EXECUTE));
        SubjectRef subjectRef = new SubjectRef();
        subjectRef.setId(userId);
        subjectRef.setType(SubjectRef.TypeEnum.USER);
        // Grant workflow execution permission to user
        authorizationRequest.setSubject(subjectRef);
        TargetRef targetRef = new TargetRef();
        targetRef.setId(workflowDef.getName());
        targetRef.setType(TargetRef.TypeEnum.WORKFLOW_DEF);
        authorizationRequest.setTarget(targetRef);
        authorizationClient.grantPermissions(authorizationRequest);

        // Grant task execution permission to user

    }

    private String createUser(String name) {
        String userId = UUID.randomUUID().toString();
        UpsertUserRequest upsertUserRequest = new UpsertUserRequest();
        upsertUserRequest.setName(name);
        upsertUserRequest.setRoles(Arrays.asList(UpsertUserRequest.RolesEnum.USER));
        authorizationClient.upsertUser(upsertUserRequest, userId);
        return userId;
    }

    private String createGroup(String name) {
        String id = UUID.randomUUID().toString();
        UpsertGroupRequest upsertGroupRequest = new UpsertGroupRequest();
        upsertGroupRequest.setDescription(name);
        upsertGroupRequest.setRoles(Arrays.asList(UpsertGroupRequest.RolesEnum.USER));
        authorizationClient.upsertGroup(upsertGroupRequest, id);
        return id;
    }


    private static void createMetadata() {
        MetadataManagement metadataManagement = new MetadataManagement();
        metadataManagement.createTaskDefinitions();
        metadataManagement.createWorkflowDefinitions();
    }
}
