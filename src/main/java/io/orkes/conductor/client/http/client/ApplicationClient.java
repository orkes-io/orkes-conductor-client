package io.orkes.conductor.client.http.client;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.model.*;

import java.util.List;

public interface ApplicationClient {
    void addRoleToApplicationUser(String applicationId, String role) throws ApiException;

    CreateAccessKeyResponse createAccessKey(String id) throws ApiException;

    ConductorApplication createApplication(CreateOrUpdateApplicationRequest body) throws ApiException;

    void deleteAccessKey(String applicationId, String keyId) throws ApiException;

    void deleteApplication(String id) throws ApiException;

    List<AccessKeyResponse> getAccessKeys(String id) throws ApiException;

    ConductorApplication getApplication(String id) throws ApiException;

    List<ConductorApplication> listApplications() throws ApiException;

    void removeRoleFromApplicationUser(String applicationId, String role)
            throws ApiException;

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId) throws ApiException;

    ConductorApplication updateApplication(CreateOrUpdateApplicationRequest body, String id)
            throws ApiException;
}
