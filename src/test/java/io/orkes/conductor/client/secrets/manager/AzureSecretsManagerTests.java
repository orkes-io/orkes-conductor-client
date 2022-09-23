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

import io.orkes.conductor.client.secrets.manager.azure.AzureSecretsManager;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.secrets.manager.SecretsManagerUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AzureSecretsManagerTests {
    @Test
    void testAzureSecretsManager() {
        AzureSecretsManager azureSecretsManager = getAzureSecretsManager();
        SecretsManagerUtil.validateApiClientWithSecretsManager(azureSecretsManager);
    }

    AzureSecretsManager getAzureSecretsManager() {
        AzureSecretsManager azureSecretsManager = mock(AzureSecretsManager.class);
        when(azureSecretsManager.getSecret(Commons.SECRET_MANAGER_KEY_PATH))
                .thenReturn(ApiUtil.getKeyId());
        when(azureSecretsManager.getSecret(Commons.SECRET_MANAGER_SECRET_PATH))
                .thenReturn(ApiUtil.getKeySecret());
        return azureSecretsManager;
    }
}
