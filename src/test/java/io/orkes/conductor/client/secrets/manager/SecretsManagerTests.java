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

import java.util.List;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.SecretsManager;
import io.orkes.conductor.client.secrets.manager.aws.AwsSecretsManager;
import io.orkes.conductor.client.secrets.manager.azure.AzureSecretsManager;
import io.orkes.conductor.client.util.ApiUtil;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecretsManagerTests {
    private static final String KEY_PATH = "ABCDJSFNFJQNW";
    private static final String SECRET_PATH = "NASDQJWDNQW";

    private final ApiClient apiClient;

    public SecretsManagerTests() {
        apiClient = ApiUtil.getApiClientWithCredentials();
    }

    @Test
    void testAwsSecretsManager() {
        AwsSecretsManager awsSecretsManager = mock(AwsSecretsManager.class);
        testSecretManager(awsSecretsManager);
    }

    @Test
    void testAzureSecretsManager() {
        AzureSecretsManager azureSecretsManager = mock(AzureSecretsManager.class);
        testSecretManager(azureSecretsManager);
    }

    void testSecretManager(SecretsManager secretsManager) {
        when(secretsManager.getSecret(KEY_PATH)).thenReturn(ApiUtil.getKeyId());
        when(secretsManager.getSecret(SECRET_PATH)).thenReturn(ApiUtil.getKeySecret());
        ApiClient customApiClient =
                new ApiClient(ApiUtil.getBasePath(), secretsManager, KEY_PATH, SECRET_PATH);
        List<String> expected = getCommonTokenPrefix(this.apiClient.getToken());
        List<String> received = getCommonTokenPrefix(customApiClient.getToken());
        assertIterableEquals(expected, received);
    }

    List<String> getCommonTokenPrefix(String token) {
        String[] splitted = token.split("[.]", 0);
        return List.of(splitted[0]);
    }
}
