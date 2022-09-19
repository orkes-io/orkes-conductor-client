package io.orkes.conductor.client;

public interface SecretsManager {

    String getSecret(String key);

    void storeSecret(String key, String secret);
}
