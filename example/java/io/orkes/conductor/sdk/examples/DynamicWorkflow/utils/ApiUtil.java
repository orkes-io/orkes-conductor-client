package io.orkes.conductor.sdk.examples.DynamicWorkflow.utils;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;

import com.google.common.base.Preconditions;

public class ApiUtil {
    private static final String ENV_ROOT_URI = "CONDUCTOR_SERVER_URL";
    private static final String ENV_KEY_ID = "KEY";
    private static final String ENV_SECRET = "SECRET";

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
        ApiClient apiClient = new ApiClient(basePath, keyId, keySecret);
        apiClient.setWriteTimeout(30_000);
        apiClient.setReadTimeout(30_000);
        apiClient.setConnectTimeout(30_000);
        return apiClient;
    }

    static String getEnv(String key) {
        return System.getenv(key);
    }
}