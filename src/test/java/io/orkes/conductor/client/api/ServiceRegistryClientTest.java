package io.orkes.conductor.client.api;

import io.orkes.conductor.client.ServiceRegistryClient;
import io.orkes.conductor.client.model.OrkesCircuitBreakerConfig;
import io.orkes.conductor.client.model.ServiceMethod;
import io.orkes.conductor.client.model.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceRegistryClientTest extends ClientTest {
    private static final String PROTO_FILENAME = "compiled.bin";
    private final ServiceRegistryClient client;
    private final String HTTP_SERVICE_NAME = "http-service";
    private final String GRPC_SERVICE_NAME = "grpc-service";

    public ServiceRegistryClientTest() {
        this.client = orkesClients.getServiceRegistryClient();
    }

    @BeforeEach
    void setUp() {
        client.removeService(HTTP_SERVICE_NAME);
        client.removeService(GRPC_SERVICE_NAME);
    }

    @Test
    void testHTTPService() throws InterruptedException {
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        serviceRegistry.setName(HTTP_SERVICE_NAME);
        serviceRegistry.setType(ServiceRegistry.TypeEnum.HTTP);
        serviceRegistry.setServiceURI("https://petstore.swagger.io/v2/swagger.json");
        client.addOrUpdateService(serviceRegistry);

        client.discover(HTTP_SERVICE_NAME, true);
        Thread.sleep(1000);
        List<ServiceRegistry> services = client.getRegisteredServices();
        ServiceRegistry actualService = services.stream()
                .filter(service -> service.getName().equals(HTTP_SERVICE_NAME))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No http service found with name: " + HTTP_SERVICE_NAME));

        assertEquals(actualService.getName(), HTTP_SERVICE_NAME);
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

        client.addOrUpdateMethod(HTTP_SERVICE_NAME, method);
        actualService = client.getService(HTTP_SERVICE_NAME);
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

        client.removeService(HTTP_SERVICE_NAME);
    }

    @Test
    void testGrpcService() throws IOException {
        client.addOrUpdateService(new ServiceRegistry()
                .name(GRPC_SERVICE_NAME)
                .type(ServiceRegistry.TypeEnum.GRPC)
                .serviceURI("localhost:50051")
        );

        List<ServiceRegistry> services = client.getRegisteredServices();
        ServiceRegistry actualService = services.stream()
                .filter(service -> service.getName().equals(GRPC_SERVICE_NAME))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No service found with name: " + GRPC_SERVICE_NAME));

        assertEquals(actualService.getName(), GRPC_SERVICE_NAME);
        assertEquals(actualService.getType(), ServiceRegistry.TypeEnum.GRPC);
        assertEquals(actualService.getServiceURI(), "localhost:50051");
        assertEquals(actualService.getMethods().size(), 0);

        ClassPathResource resource = new ClassPathResource(PROTO_FILENAME);
        byte[] originalData = Files.readAllBytes(resource.getFile().toPath());

        client.setProtoData(GRPC_SERVICE_NAME, PROTO_FILENAME, originalData);
        byte[] actualData = client.getProtoData(GRPC_SERVICE_NAME, PROTO_FILENAME);
        assertEquals(originalData.length, actualData.length);

        actualService = client.getService(GRPC_SERVICE_NAME);

        assertTrue(actualService.getMethods().size() > 0);

        OrkesCircuitBreakerConfig actualConfig = actualService.getConfig().getCircuitBreakerConfig();
        assertEquals(actualConfig.getFailureRateThreshold(), 50);
        assertEquals(actualConfig.getMinimumNumberOfCalls(), 100);
        assertEquals(actualConfig.getPermittedNumberOfCallsInHalfOpenState(), 100);
        assertEquals(actualConfig.getWaitDurationInOpenState(), 1000);
        assertEquals(actualConfig.getSlidingWindowSize(), 100);
        assertEquals(actualConfig.getSlowCallRateThreshold(), 50);
        assertEquals(actualConfig.getMaxWaitDurationInHalfOpenState(), 1);

        client.removeService(GRPC_SERVICE_NAME);
    }
}
