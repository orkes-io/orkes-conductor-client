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

import io.orkes.conductor.client.http.ApiClient;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "SDK_INTEGRATION_TESTS_SERVER_API_URL";
    private static final String ENV_SECRET = "SDK_INTEGRATION_TESTS_SERVER_KEY_SECRET";
    private static final String ENV_KEY_ID = "SDK_INTEGRATION_TESTS_SERVER_KEY_ID";

    public static ApiClient getApiClientWithCredentials() {
        return new ApiClient(getEnv(ENV_ROOT_URI), getEnv(ENV_KEY_ID), getEnv(ENV_SECRET));
    }

    static String getEnv(String key) {
        return System.getenv(key);
    }
}
