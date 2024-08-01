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
package io.orkes.conductor.sdk;

import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import org.junit.jupiter.api.Assertions;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "CONDUCTOR_SERVER_URL";
    private static final String ENV_KEY_ID = "CONDUCTOR_SERVER_AUTH_KEY";
    private static final String ENV_SECRET = "CONDUCTOR_SERVER_AUTH_SECRET";

    public static OrkesHttpClient getApiClientWithCredentials() {
        String basePath = getEnv(ENV_ROOT_URI);
        Assertions.assertNotNull(basePath, ENV_ROOT_URI + " env not set");
        String keyId = getEnv(ENV_KEY_ID);
        Assertions.assertNotNull(keyId, ENV_KEY_ID + " env not set");
        String keySecret = getEnv(ENV_SECRET);
        Assertions.assertNotNull(keySecret, ENV_SECRET + " env not set");

        return OrkesHttpClient.builder()
                .basePath(basePath)
                .keyId(keyId)
                .keySecret(keySecret)
                .readTimeout(30_000)
                .connectTimeout(30_000)
                .writeTimeout(30_000)
                .build();
    }

    private static String getEnv(String key) {
        return System.getenv(key);
    }
}
