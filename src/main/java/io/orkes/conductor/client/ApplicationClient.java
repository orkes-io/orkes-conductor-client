package io.orkes.conductor.client;

import io.orkes.conductor.client.http.model.*;

import java.util.List;

public interface ApplicationClient {
    void addRoleToApplicationUser(String applicationId, String role);

    CreateAccessKeyResponse createAccessKey(String id);

    ConductorApplication createApplication(CreateOrUpdateApplicationRequest body);

    void deleteAccessKey(String applicationId, String keyId);

    void deleteApplication(String id);

    List<AccessKeyResponse> getAccessKeys(String id);

    ConductorApplication getApplication(String id);

    List<ConductorApplication> listApplications();

    void removeRoleFromApplicationUser(String applicationId, String role)
           ;

    AccessKeyResponse toggleAccessKeyStatus(String applicationId, String keyId);

    ConductorApplication updateApplication(CreateOrUpdateApplicationRequest body, String id)
           ;
}
