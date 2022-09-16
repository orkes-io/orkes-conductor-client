package io.orkes.conductor.client.api;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.SecretClient;

public class SecretClientTests extends ClientTest {
    private final SecretClient secretClient;

    SecretClientTests() {
        secretClient = super.orkesClients.getSecretClient();
    }

    @Test
    void testMethods() {

    }
}
