package io.orkes.conductor.client.http;

import com.squareup.okhttp.Call;
import io.orkes.conductor.client.http.api.ApplicationResourceApi;
import io.orkes.conductor.client.http.model.ConductorApplication;
import io.orkes.conductor.client.http.model.CreateOrUpdateApplicationRequest;

import java.util.List;

public class OrkesApplicationClient extends ApplicationResourceApi {

    public OrkesApplicationClient(ApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public void setApiClient(ApiClient apiClient) {
        super.setApiClient(apiClient);
    }


    @Override
    public Object addRoleToApplicationUser(String applicationId, String role) throws ApiException {
        return super.addRoleToApplicationUser(applicationId, role);
    }


    @Override
    public Object createAccessKey(String id) throws ApiException {
        return super.createAccessKey(id);
    }


    @Override
    public Object createApplication(CreateOrUpdateApplicationRequest body) throws ApiException {
        return super.createApplication(body);
    }


    @Override
    public Object deleteAccessKey(String applicationId, String keyId) throws ApiException {
        return super.deleteAccessKey(applicationId, keyId);
    }


    @Override
    public Object deleteApplication(String id) throws ApiException {
        return super.deleteApplication(id);
    }


    @Override
    public Object getAccessKeys(String id) throws ApiException {
        return super.getAccessKeys(id);
    }


    @Override
    public Object getApplication(String id) throws ApiException {
        return super.getApplication(id);
    }


    @Override
    public List<ConductorApplication> listApplications() throws ApiException {
        return super.listApplications();
    }


    @Override
    public Object removeRoleFromApplicationUser(String applicationId, String role) throws ApiException {
        return super.removeRoleFromApplicationUser(applicationId, role);
    }


    @Override
    public Object toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException {
        return super.toggleAccessKeyStatus(applicationId, keyId);
    }


    @Override
    public Object updateApplication(CreateOrUpdateApplicationRequest body, String id) throws ApiException {
        return super.updateApplication(body, id);
    }


}
