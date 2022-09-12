package io.orkes.conductor.client;

import java.util.List;
import java.util.Set;

public interface SecretClient {
    void deleteSecret(String key);

    String getSecret(String key);

    Set<String> listAllSecretNames();

    List<String> listSecretsThatUserCanGrantAccessTo();

    void putSecret(String body, String key);

    boolean secretExists(String key);
}
