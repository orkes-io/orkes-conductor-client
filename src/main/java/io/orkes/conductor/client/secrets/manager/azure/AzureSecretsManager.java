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
package io.orkes.conductor.client.secrets.manager.azure;

import io.orkes.conductor.client.SecretsManager;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

public class AzureSecretsManager implements SecretsManager {
    private static final String PROPERTY_NAME = "azure.keyvault.name";

    private final SecretClient client;

    public AzureSecretsManager() {
        this.client = createClient();
    }

    @Override
    public String getSecret(String keyPath) {
        return client.getSecret(keyPath).getValue();
    }

    @Override
    public void storeSecret(String key, String secret) {
        client.setSecret(key, secret);
    }

    private SecretClient createClient() {
        String keyVaultName = getProperty(PROPERTY_NAME);
        if (keyVaultName == null) {
            throw new RuntimeException("Key Vault name is not specified, cannot create client");
        }
        return new SecretClientBuilder()
                .vaultUrl(String.format("https://%s.vault.azure.net/", keyVaultName))
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }
}
