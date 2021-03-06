package io.orkes.conductor.client.http.auth;


import java.util.Objects;

class TokenRequest {

    private final String keyId;
    private final String keySecret;

    private TokenRequest(String keyId, String keySecret) {
        this.keyId = keyId;
        this.keySecret = keySecret;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public static TokenRequestBuilder builder() {
        return new TokenRequestBuilder();
    }

    public static class TokenRequestBuilder {
        private String keyId;
        private String keySecret;

        private TokenRequestBuilder() {
        }

        public TokenRequestBuilder withKeyId(String keyId) {
            this.keyId = Objects.requireNonNull(keyId);
            return this;
        }

        public TokenRequestBuilder withKeySecret(String keySecret) {
            this.keySecret = Objects.requireNonNull(keySecret);
            return this;
        }

        public TokenRequest build() {
            return new TokenRequest(keyId, keySecret);
        }
    }


}
