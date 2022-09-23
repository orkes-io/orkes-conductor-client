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
package io.orkes.conductor.client.secrets.manager;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.SecretsManager;
import io.orkes.conductor.client.secrets.manager.aws.AwsSecretsManager;
import io.orkes.conductor.client.secrets.manager.azure.AzureSecretsManager;
import io.orkes.conductor.client.secrets.manager.util.AWSContainer;
import io.orkes.conductor.client.util.ApiUtil;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecretsManagerTests {
    private static final String KEY_PATH = "path/to/key";
    private static final String SECRET_PATH = "path/to/secret";

    @Test
    void testAwsSecretsManagerWithoutCredentials() throws Exception {
        AWSContainer awsContainer = new AWSContainer(null, LocalStackContainer.Service.SSM);
        awsContainer.start();
        AwsSecretsManager awsSecretsManager = getAwsSecretsManager(awsContainer);
        ApiClient customApiClient =
                new ApiClient(ApiUtil.getBasePath(), awsSecretsManager, KEY_PATH, SECRET_PATH);
        assertNull(customApiClient.getToken());
        awsContainer.close();
    }

    @Test
    void testAwsSecretsManager() throws Exception {
        AWSContainer awsContainer = new AWSContainer(null, LocalStackContainer.Service.SSM);
        awsContainer.start();
        AwsSecretsManager awsSecretsManager = getAwsSecretsManager(awsContainer);
        awsSecretsManager.storeSecret(KEY_PATH, ApiUtil.getKeyId());
        awsSecretsManager.storeSecret(SECRET_PATH, ApiUtil.getKeySecret());
        testSecretManager(awsSecretsManager);
        awsContainer.close();
    }

    @Test
    void testAzureSecretsManager() {
        AzureSecretsManager azureSecretsManager = mock(AzureSecretsManager.class);
        when(azureSecretsManager.getSecret(KEY_PATH)).thenReturn(ApiUtil.getKeyId());
        when(azureSecretsManager.getSecret(SECRET_PATH)).thenReturn(ApiUtil.getKeySecret());
        testSecretManager(azureSecretsManager);
    }

    void testSecretManager(SecretsManager secretsManager) {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        ApiClient customApiClient =
                new ApiClient(ApiUtil.getBasePath(), secretsManager, KEY_PATH, SECRET_PATH);
        assertEquals(
                getCommonTokenPrefix(apiClient.getToken()),
                getCommonTokenPrefix(customApiClient.getToken()));
    }

    String getCommonTokenPrefix(String token) {
        if (token == null) {
            return null;
        }
        String[] splitted = token.split("[.]", 0);
        return splitted[0];
    }

    AwsSecretsManager getAwsSecretsManager(AWSContainer awsContainer) {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement =
                getAWSSimpleSystemsManagement(awsContainer);
        return new AwsSecretsManager(awsSimpleSystemsManagement);
    }

    AWSSimpleSystemsManagement getAWSSimpleSystemsManagement(AWSContainer awsContainer) {
        return AWSSimpleSystemsManagementClientBuilder.standard()
                .withEndpointConfiguration(
                        awsContainer.getEndpointConfiguration(LocalStackContainer.Service.SSM))
                .withCredentials(awsContainer.getDefaultCredentialsProvider())
                .build();
    }
}
