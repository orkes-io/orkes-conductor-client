package io.orkes.conductor.client.util;

import io.orkes.conductor.client.http.ApiClient;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "SDK_INTEGRATION_TESTS_SERVER_API_URL";
    private static final String ENV_SECRET = "SDK_INTEGRATION_TESTS_SERVER_KEY_SECRET";
    private static final String ENV_KEY_ID = "SDK_INTEGRATION_TESTS_SERVER_KEY_ID";

    public static ApiClient getApiClientWithCredentials() {
        return new ApiClient(
                getEnv(ENV_ROOT_URI),
                getEnv(ENV_KEY_ID),
                getEnv(ENV_SECRET));
    }

    static String getEnv(String key) {
        return System.getenv(key);
    }
}
