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
package io.orkes.conductor.client.api;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.SecretClient;
import io.orkes.conductor.client.http.ApiException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecretClientTests extends ClientTest {
    private final String SECRET_NAME = "test-sdk-java-secret_name";
    private final String SECRET_KEY = "test-sdk-java-secret_key";

    private final SecretClient secretClient;

    public SecretClientTests() {
        secretClient = super.orkesClients.getSecretClient();
    }

    @Test
    void testMethods() {
        try {
            secretClient.deleteSecret(SECRET_KEY);
        } catch (ApiException e) {
            if (e.getStatusCode() != 500) {
                throw e;
            }
        }
        secretClient.putSecret(SECRET_NAME, SECRET_KEY);
        assertTrue(secretClient.listSecretsThatUserCanGrantAccessTo().contains(SECRET_KEY));
        assertTrue(secretClient.listAllSecretNames().contains(SECRET_KEY));
        assertEquals(SECRET_NAME, secretClient.getSecret(SECRET_KEY));
        assertTrue(secretClient.secretExists(SECRET_KEY));
        secretClient.deleteSecret(SECRET_KEY);
    }
}
