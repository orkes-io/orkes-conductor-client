package io.orkes.conductor.client.util.authentication;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.AuthorizationClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.api.ClientTest;
import io.orkes.conductor.client.model.ConductorApplication;
import io.orkes.conductor.client.model.CreateOrUpdateApplicationRequest;
import io.orkes.conductor.client.model.UpsertGroupRequest.RolesEnum;
import io.orkes.conductor.client.util.ApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TestWithAuthentication extends ClientTest {
    private final List<ConductorApplication> createdApplicationsToDelete;

    private final AuthorizationClient authorizationClient;

    public TestWithAuthentication() {
        authorizationClient = super.orkesClients.getAuthorizationClient();
        createdApplicationsToDelete = new ArrayList<>();
    }

    protected void finalize() throws Throwable {
        createdApplicationsToDelete.forEach(
                application -> {
                    authorizationClient.deleteApplication(application.getId());
                });
    }

    protected OrkesClients getOrkesClientsWithRole(RolesEnum role) {
        var applicationName = generateRandomStringWithPrefix("random_application_name_");
        var request = new CreateOrUpdateApplicationRequest().name(applicationName);
        var application = authorizationClient.createApplication(request);
        this.createdApplicationsToDelete.add(application);
        authorizationClient.addRoleToApplicationUser(
                application.getId(), role.getValue());
        var accessKeyResponse = authorizationClient.createAccessKey(application.getId());
        var apiClient = new ApiClient(
                ApiUtil.getBasePath(),
                accessKeyResponse.getId(),
                accessKeyResponse.getSecret());
        return new OrkesClients(apiClient);
    }

    protected String generateRandomStringWithPrefix(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
