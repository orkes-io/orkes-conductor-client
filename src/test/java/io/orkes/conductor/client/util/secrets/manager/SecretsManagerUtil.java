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
package io.orkes.conductor.client.util.secrets.manager;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.SecretsManager;
import io.orkes.conductor.client.util.ApiUtil;
import io.orkes.conductor.client.util.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class SecretsManagerUtil {
    public static void validateApiClientWithSecretsManager(SecretsManager secretsManager) {
        ApiClient apiClient = ApiUtil.getApiClientWithCredentials();
        ApiClient customApiClient =
                new ApiClient(
                        ApiUtil.getBasePath(),
                        secretsManager,
                        Commons.SECRET_MANAGER_KEY_PATH,
                        Commons.SECRET_MANAGER_SECRET_PATH);
        assertEquals(
                getCommonTokenPrefix(apiClient.getToken()),
                getCommonTokenPrefix(customApiClient.getToken()));
    }

    private static String getCommonTokenPrefix(String token) {
        if (token == null) {
            return null;
        }
        String[] splitted = token.split("[.]", 0);
        return splitted[0];
    }
}
