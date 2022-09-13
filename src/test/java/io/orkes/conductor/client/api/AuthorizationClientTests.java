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

import java.util.*;

import io.orkes.conductor.client.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.model.UpsertGroupRequest.RolesEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthorizationClientTests extends ClientTest {
    private final AuthorizationClient authorizationClient;

    public AuthorizationClientTests() {
        this.authorizationClient = super.orkesClients.getAuthorizationClient();
    }

    @Test
    @DisplayName("auto assign group permission on workflow creation by any group member")
    public void autoAssignWorkflowPermissions() {
        giveApplicationPermissions("46f0bf10-b59d-4fbd-a053-935307c8cb86");
        Group group = authorizationClient.upsertGroup(getUpsertGroupRequest(), "sdk-test-group");
        validateGroupPermissions(group.getId());
    }

    @Test
    void testAddUser() {
        UpsertUserRequest request = new UpsertUserRequest();
        request.setName("Orkes User");
        request.setGroups(Arrays.asList("Example Group"));
        request.setRoles(Arrays.asList(UpsertUserRequest.RolesEnum.USER));
        String userId = "user@orkes.io";        //MUST be the email addressed used to login to Conductor
        ConductorUser user = authorizationClient.upsertUser(request, userId);
        assertNotNull(user);


        ConductorUser found = authorizationClient.getUser(userId);
        assertNotNull(found);
        assertEquals(user.getName(), found.getName());
        assertEquals(user.getGroups().get(0).getId(), found.getGroups().get(0).getId());
        assertEquals(user.getRoles().get(0).getName(), found.getRoles().get(0).getName());

    }

    @Test
    void testAddGroup() {
        UpsertGroupRequest request = new UpsertGroupRequest();

        //Default Access for the group.  When specified, any new workflow or task created by the members of this group
        //get this default permission inside the group.
        Map<String, List<String>> defaultAccess = new HashMap<>();

        //Grant READ access to the members of the group for any new workflow created by a member of this group
        defaultAccess.put("WORKFLOW_DEF", List.of("READ"));

        //Grant EXECUTE access to the members of the group for any new task created by a member of this group
        defaultAccess.put("TASK_DEF", List.of("EXECUTE"));
        request.setDefaultAccess(defaultAccess);

        request.setDescription("Example group created for testing");
        request.setRoles(Arrays.asList(UpsertGroupRequest.RolesEnum.USER));

        String groupId = "Test Group";
        Group group = authorizationClient.upsertGroup(request, groupId);
        assertNotNull(group);


        Group found = authorizationClient.getGroup(groupId);
        assertNotNull(found);
        assertEquals(group.getId(), found.getId());
        assertEquals(group.getDefaultAccess().keySet(), found.getDefaultAccess().keySet());

    }


    @Test
    void testAddApplication() {

        CreateOrUpdateApplicationRequest request = new CreateOrUpdateApplicationRequest();
        request.setName("Test Application for the testing");

        //WARNING: Application Name is not a UNIQUE value and if called multiple times, it will create a new application
        ConductorApplication application = authorizationClient.createApplication(request);
        assertNotNull(application);
        assertNotNull(application.getId());


        //Get the list of applications
        List<ConductorApplication> apps = authorizationClient.listApplications();
        assertNotNull(apps);
        long found = apps.stream().map(ConductorApplication::getId).filter(id -> id.equals(application.getId())).count();
        assertEquals(1, found);

        //Create new access key
        CreateAccessKeyResponse accessKey = authorizationClient.createAccessKey(application.getId());
        assertNotNull(accessKey.getId());
        assertNotNull(accessKey.getSecret());
        System.out.println(accessKey.getId() + ":" + accessKey.getSecret());

        authorizationClient.deleteApplication(application.getId());
    }

    @Test
    void testGrangPermissionsToGroup() {


        AuthorizationRequest request = new AuthorizationRequest();
        request.access(Arrays.asList(AuthorizationRequest.AccessEnum.READ));
        SubjectRef subject = new SubjectRef();
        subject.setId("Example Group");
        subject.setType(SubjectRef.TypeEnum.GROUP);
        request.setSubject(subject);
        TargetRef target = new TargetRef();
        target.setId("Test_032");
        target.setType(TargetRef.TypeEnum.WORKFLOW_DEF);
        request.setTarget(target);
        authorizationClient.grantPermissions(request);


    }

    @Test
    void testGrantPermissionsToTag() {


        AuthorizationRequest request = new AuthorizationRequest();
        request.access(Arrays.asList(AuthorizationRequest.AccessEnum.READ));


        SubjectRef subject = new SubjectRef();
        subject.setId("Example Group");
        subject.setType(SubjectRef.TypeEnum.GROUP);

        request.setSubject(subject);

        //Grant permissions to the tag with accounting org
        TargetRef target = new TargetRef();
        target.setId("org:accounting");
        target.setType(TargetRef.TypeEnum.TAG);

        request.setTarget(target);
        authorizationClient.grantPermissions(request);


    }

    void giveApplicationPermissions(String applicationId) {
        authorizationClient.addRoleToApplicationUser(applicationId, "ADMIN");
    }

    void validateGroupPermissions(String id) {
        Group group = authorizationClient.getGroup(id);
        for (Map.Entry<String, List<String>> entry : group.getDefaultAccess().entrySet()) {
            List<String> expectedList = new ArrayList<>(getAccessListAll());
            List<String> actualList = new ArrayList<>(entry.getValue());
            Collections.sort(expectedList);
            Collections.sort(actualList);
            assertEquals(expectedList, actualList);
        }
    }

    UpsertGroupRequest getUpsertGroupRequest() {
        return new UpsertGroupRequest()
                .defaultAccess(
                        Map.of(
                                "WORKFLOW_DEF", getAccessListAll(),
                                "TASK_DEF", getAccessListAll()))
                .description("Group used for SDK testing")
                .roles(List.of(RolesEnum.ADMIN));
    }

    List<String> getAccessListAll() {
        return List.of("CREATE", "READ", "UPDATE", "EXECUTE", "DELETE");
    }
}
