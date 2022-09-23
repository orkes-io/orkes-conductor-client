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

import org.junit.Test;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.secrets.manager.aws.AwsSecretsManager;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.secrets.manager.SecretsManagerUtil;
import io.orkes.conductor.client.util.secrets.manager.TestWithAwsContainer;

import static org.junit.jupiter.api.Assertions.assertNull;

public class AwsSecretsManagerTests extends TestWithAwsContainer {
    @Test
    void testAwsSecretsManagerWithoutCredentials() throws Exception {
        AwsSecretsManager awsSecretsManager = getAwsSecretsManager();
        ApiClient customApiClient =
                new ApiClient(
                        ApiUtil.getBasePath(),
                        awsSecretsManager,
                        Commons.SECRET_MANAGER_KEY_PATH,
                        Commons.SECRET_MANAGER_SECRET_PATH);
        assertNull(customApiClient.getToken());
    }

    @Test
    void testAwsSecretsManager() throws Exception {
        AwsSecretsManager awsSecretsManager = getAwsSecretsManager();
        awsSecretsManager.storeSecret(Commons.SECRET_MANAGER_KEY_PATH, ApiUtil.getKeyId());
        awsSecretsManager.storeSecret(Commons.SECRET_MANAGER_SECRET_PATH, ApiUtil.getKeySecret());
        SecretsManagerUtil.validateApiClientWithSecretsManager(awsSecretsManager);
    }

    AwsSecretsManager getAwsSecretsManager() {
        return new AwsSecretsManager(awsContainer.getAWSSimpleSystemsManagement());
    }
}
