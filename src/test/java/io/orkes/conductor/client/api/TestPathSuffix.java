package io.orkes.conductor.client.api;

import io.orkes.conductor.client.ApiClient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPathSuffix {

    @Test
    public void test() {
        ApiClient apiClient = new ApiClient("https://play.orkes.io/api");
        ApiClient apiClient2 = new ApiClient("https://play.orkes.io/api/");

        assertEquals(apiClient2.getBasePath(), apiClient.getBasePath());
    }
}
