package io.orkes.conductor.client.api;

import io.orkes.conductor.client.ServiceRegistryClient;
import io.orkes.conductor.client.model.OrkesCircuitBreakerConfig;
import io.orkes.conductor.client.model.ServiceMethod;
import io.orkes.conductor.client.model.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceRegistryClientTest extends ClientTest {
    private final ServiceRegistryClient client;
    private final String SERVICE_NAME = "test-sdk-java-service_name";

    public ServiceRegistryClientTest() {
        this.client = orkesClients.getServiceRegistryClient();
    }

    @BeforeEach
    void setUp() {
        client.removeService(SERVICE_NAME);
    }

    @Test
    void testMethods() throws InterruptedException {
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        serviceRegistry.setName(SERVICE_NAME);
        serviceRegistry.setType(ServiceRegistry.TypeEnum.HTTP);
        serviceRegistry.setServiceURI("https://petstore.swagger.io/v2/swagger.json");
        client.addOrUpdateService(serviceRegistry);

        client.discover(SERVICE_NAME, true);
        Thread.sleep(1000);
        List<ServiceRegistry> services = client.getRegisteredServices();
        ServiceRegistry actualService = services.stream()
                .filter(service -> service.getName().equals(SERVICE_NAME))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No service found with name: " + SERVICE_NAME));

        assertEquals(actualService.getName(), SERVICE_NAME);
        assertEquals(actualService.getType(), ServiceRegistry.TypeEnum.HTTP);
        assertEquals(actualService.getServiceURI(), "https://petstore.swagger.io/v2/swagger.json");
        assertTrue(actualService.getMethods().size() > 0);

        int size = actualService.getMethods().size();

        ServiceMethod method = new ServiceMethod();
        method.setOperationName("TestOperation");
        method.setMethodName("addBySdkTest");
        method.setMethodType("GET");
        method.setInputType("newHttpInputType");
        method.setOutputType("newHttpOutputType");

        client.addOrUpdateMethod(SERVICE_NAME, method);
        actualService = client.getService(SERVICE_NAME);
        int actualSize = actualService.getMethods().size();
        assertEquals(size + 1, actualSize);

        OrkesCircuitBreakerConfig actualConfig = actualService.getConfig().getCircuitBreakerConfig();
        assertEquals(actualConfig.getFailureRateThreshold(), 50);
        assertEquals(actualConfig.getMinimumNumberOfCalls(), 100);
        assertEquals(actualConfig.getPermittedNumberOfCallsInHalfOpenState(), 100);
        assertEquals(actualConfig.getWaitDurationInOpenState(), 1000);
        assertEquals(actualConfig.getSlidingWindowSize(), 100);
        assertEquals(actualConfig.getSlowCallRateThreshold(), 50);
        assertEquals(actualConfig.getMaxWaitDurationInHalfOpenState(), 1);
        
    }
}
