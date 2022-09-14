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
package io.orkes.conductor.sdk.examples;

import com.google.common.base.Preconditions;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "SDK_INTEGRATION_TESTS_SERVER_API_URL";
    private static final String ENV_KEY_ID = "SDK_INTEGRATION_TESTS_SERVER_KEY_ID";
    private static final String ENV_SECRET = "SDK_INTEGRATION_TESTS_SERVER_KEY_SECRET";

    public static OrkesClients getOrkesClient() {
        final ApiClient apiClient = getApiClientWithCredentials();
        return new OrkesClients(apiClient);
    }

    public static ApiClient getApiClientWithCredentials() {
        String basePath = getEnv(ENV_ROOT_URI);
        Preconditions.checkNotNull(basePath, ENV_ROOT_URI + " env not set");
        String keyId = getEnv(ENV_KEY_ID);
        Preconditions.checkNotNull(keyId, ENV_KEY_ID + " env not set");
        String keySecret = getEnv(ENV_SECRET);
        Preconditions.checkNotNull(keyId, ENV_SECRET + " env not set");
        return new ApiClient(basePath, keyId, keySecret);
    }

    static String getEnv(String key) {
        return System.getenv(key);
    }
}
