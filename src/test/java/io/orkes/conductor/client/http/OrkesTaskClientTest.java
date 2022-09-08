package io.orkes.conductor.client.http;

import com.netflix.conductor.client.config.DefaultConductorClientConfiguration;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import io.orkes.conductor.client.http.auth.AuthorizationClientFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrkesTaskClientTest {

    @Test
    void testConstructor() {
        OrkesTaskClient taskClient = new OrkesTaskClient(new DefaultClientConfig(), new DefaultConductorClientConfiguration(), null);

        taskClient.setRootURI("/");
        taskClient.withCredentials("key", "secret");
    }
}