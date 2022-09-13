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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.http.model.Group;
import io.orkes.conductor.client.http.model.UpsertGroupRequest;
import io.orkes.conductor.client.http.model.UpsertGroupRequest.RolesEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
