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
package io.orkes.conductor.client.util;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "CONDUCTOR_SERVER_URL";
    private static final String ENV_KEY_ID = "CONDUCTOR_SERVER_AUTH_KEY";
    private static final String ENV_SECRET = "CONDUCTOR_SERVER_AUTH_SECRET";

    public static OrkesClients getOrkesClient() {
        final ApiClient apiClient = getApiClientWithCredentials();
        apiClient.setReadTimeout(10_000);
        return new OrkesClients(apiClient);
    }

    public static ApiClient getApiClientWithCredentials() {
        String basePath = getBasePath();
        assertNotNull(basePath, ENV_ROOT_URI + " env not set");
        String keyId = getKeyId();
        assertNotNull(keyId, ENV_KEY_ID + " env not set");
        String keySecret = getKeySecret();
        assertNotNull(keySecret, ENV_SECRET + " env not set");
        ApiClient apiClient =  new ApiClient(basePath, keyId, keySecret);
        apiClient.setWriteTimeout(30_000);
        apiClient.setReadTimeout(30_000);
        apiClient.setConnectTimeout(30_000);
        return apiClient;
    }

    public static String getBasePath() {
        return getEnv(ENV_ROOT_URI);
    }

    public static String getKeyId() {
        return getEnv(ENV_KEY_ID);
    }

    public static String getKeySecret() {
        return getEnv(ENV_SECRET);
    }

    public static String getEnv(String key) {
        return System.getenv(key);
    }
}
