package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.ConductorApplication;
import io.orkes.conductor.client.http.model.CreateOrUpdateApplicationRequest;

import java.util.List;

public interface ApplicationClient {
    Object addRoleToApplicationUser(String applicationId, String role) throws ApiException;

    Object createAccessKey(String id) throws ApiException;

    Object createApplication(CreateOrUpdateApplicationRequest body) throws ApiException;

    Object deleteAccessKey(String applicationId, String keyId) throws ApiException;

    Object deleteApplication(String id) throws ApiException;

    Object getAccessKeys(String id) throws ApiException;

    Object getApplication(String id) throws ApiException;

    List<ConductorApplication> listApplications() throws ApiException;

    Object removeRoleFromApplicationUser(String applicationId, String role)
            throws ApiException;

    Object toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException;

    Object updateApplication(CreateOrUpdateApplicationRequest body, String id)
            throws ApiException;
}
