package io.orkes.conductor.client.http.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenRequestTest {

    @Test
    @DisplayName("it should build an instance of TokenRequest")
    void buildSuccessful() {
        var tokenRequest = TokenRequest.builder()
                .withKeyId("my-key")
                .withKeySecret("my-secret")
                .build();
        assertEquals(tokenRequest.getKeyId(), "my-key");
        assertEquals(tokenRequest.getKeySecret(), "my-secret");
    }

    @Test
    @DisplayName("it should throw a NPE if key id or secret are null")
    void builderThrowsNPE() {
        assertThrows(NullPointerException.class, () -> TokenRequest.builder()
                .withKeyId(null));
        assertThrows(NullPointerException.class, () -> TokenRequest.builder()
                .withKeySecret(null));
    }
}
