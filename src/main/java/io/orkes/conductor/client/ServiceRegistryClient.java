package io.orkes.conductor.client;

import io.orkes.conductor.client.model.CircuitBreakerTransitionResponse;
import io.orkes.conductor.client.model.ProtoRegistryEntry;
import io.orkes.conductor.client.model.ServiceMethod;
import io.orkes.conductor.client.model.ServiceRegistry;

import java.util.List;

public interface ServiceRegistryClient {
    // Basic service operations
    List<ServiceRegistry> getRegisteredServices();
    ServiceRegistry getService(String name);
    void addOrUpdateService(ServiceRegistry serviceRegistry);
    void removeService(String name);

    // Circuit breaker operations
    CircuitBreakerTransitionResponse openCircuitBreaker(String name);
    CircuitBreakerTransitionResponse closeCircuitBreaker(String name);
    CircuitBreakerTransitionResponse getCircuitBreakerStatus(String name);

    // Method operations
    void addOrUpdateMethod(String registryName, ServiceMethod method);
    void removeMethod(String registryName, String serviceName, String method, String methodType);

    // Proto operations
    byte[] getProtoData(String registryName, String filename);
    void setProtoData(String registryName, String filename, byte[] data);
    void deleteProto(String registryName, String filename);
    List<ProtoRegistryEntry> getAllProtos(String registryName);

    // Discovery operations
    List<ServiceMethod> discover(String name, boolean create);
}