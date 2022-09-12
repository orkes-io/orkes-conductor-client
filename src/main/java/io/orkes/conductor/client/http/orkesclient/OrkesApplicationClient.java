package io.orkes.conductor.client.http.orkesclient;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.api.ApplicationResourceApi;
import io.orkes.conductor.client.http.client.ApplicationClient;
import io.orkes.conductor.client.http.model.ConductorApplication;
import io.orkes.conductor.client.http.model.CreateOrUpdateApplicationRequest;

import java.util.List;

public class OrkesApplicationClient extends OrkesClient implements ApplicationClient {

    private ApplicationResourceApi applicationResourceApi;

    public OrkesApplicationClient(ApiClient apiClient) {
        super(apiClient);
        this.applicationResourceApi = new ApplicationResourceApi(apiClient);
    }


    @Override
    public Object addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        return applicationResourceApi.addRoleToApplicationUser(applicationId, role);
    }

    @Override
    public Object createAccessKey(String id) throws ApiException {
        return applicationResourceApi.createAccessKey(id);
    }

    @Override
    public Object createApplication(CreateOrUpdateApplicationRequest body) throws ApiException {
        return applicationResourceApi.createApplication(body);
    }

    @Override
    public Object deleteAccessKey(String applicationId, String keyId) throws ApiException {
        return applicationResourceApi.deleteAccessKey(applicationId, keyId);
    }

    @Override
    public Object deleteApplication(String id) throws ApiException {
        return applicationResourceApi.deleteApplication(id);
    }

    @Override
    public Object getAccessKeys(String id) throws ApiException {
        return applicationResourceApi.getAccessKeys(id);
    }

    @Override
    public Object getApplication(String id) throws ApiException {
        return applicationResourceApi.getApplication(id);
    }

    @Override
    public List<ConductorApplication> listApplications() throws ApiException {
        return applicationResourceApi.listApplications();
    }

    @Override
    public Object removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        return applicationResourceApi.removeRoleFromApplicationUser(applicationId, role);
    }

    @Override
    public Object toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        return applicationResourceApi.toggleAccessKeyStatus(applicationId, keyId);
    }

    @Override
    public Object updateApplication(CreateOrUpdateApplicationRequest body, String id) throws ApiException {
        return applicationResourceApi.updateApplication(body, id);
    }
}
