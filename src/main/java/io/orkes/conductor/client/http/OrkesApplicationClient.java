package io.orkes.conductor.client.http;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.api.ApplicationResourceApi;
import io.orkes.conductor.client.ApplicationClient;
import io.orkes.conductor.client.http.model.*;

import java.util.List;

public class OrkesApplicationClient extends OrkesClient implements ApplicationClient {

    private ApplicationResourceApi applicationResourceApi;

    public OrkesApplicationClient(ApiClient apiClient) {
        super(apiClient);
        this.applicationResourceApi = new ApplicationResourceApi(apiClient);
    }


    @Override
    public void addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        applicationResourceApi.addRoleToApplicationUser(applicationId, role);
    }

    @Override
    public CreateAccessKeyResponse createAccessKey(String id) throws ApiException {
        return applicationResourceApi.createAccessKey(id);
    }

    @Override
    public ConductorApplication createApplication(CreateOrUpdateApplicationRequest body) throws ApiException {
        return applicationResourceApi.createApplication(body);
    }

    @Override
    public void deleteAccessKey(String applicationId, String keyId) throws ApiException {
        applicationResourceApi.deleteAccessKey(applicationId, keyId);
    }

    @Override
    public void deleteApplication(String id) throws ApiException {
        applicationResourceApi.deleteApplication(id);
    }

    @Override
    public List<AccessKeyResponse> getAccessKeys(String id) throws ApiException {
        return applicationResourceApi.getAccessKeys(id);
    }

    @Override
    public ConductorApplication getApplication(String id) throws ApiException {
        return applicationResourceApi.getApplication(id);
    }

    @Override
    public List<ConductorApplication> listApplications() throws ApiException {
        return applicationResourceApi.listApplications();
    }

    @Override
    public void removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        applicationResourceApi.removeRoleFromApplicationUser(applicationId, role);
    }

    @Override
    public AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        return applicationResourceApi.toggleAccessKeyStatus(applicationId, keyId);
    }

    @Override
    public ConductorApplication updateApplication(CreateOrUpdateApplicationRequest body, String id) throws ApiException {
        return applicationResourceApi.updateApplication(body, id);
    }
}
