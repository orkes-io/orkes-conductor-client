package io.orkes.conductor.client.http;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.ServiceRegistryClient;
import io.orkes.conductor.client.http.api.ServiceRegistryResourceApi;
import io.orkes.conductor.client.model.CircuitBreakerTransitionResponse;
import io.orkes.conductor.client.model.ProtoRegistryEntry;
import io.orkes.conductor.client.model.ServiceMethod;
import io.orkes.conductor.client.model.ServiceRegistry;

import java.util.List;

public class OrkesServiceRegistryClient extends OrkesClient implements ServiceRegistryClient {

    private final ServiceRegistryResourceApi serviceRegistryResourceApi;

    public OrkesServiceRegistryClient(ApiClient apiClient) {
        super(apiClient);
        this.serviceRegistryResourceApi = new ServiceRegistryResourceApi(apiClient);
    }

    @Override
    public List<ServiceRegistry> getRegisteredServices() {
        return serviceRegistryResourceApi.getRegisteredServices();
    }

    @Override
    public ServiceRegistry getService(String name) {
        return serviceRegistryResourceApi.getService(name);
    }

    @Override
    public void addOrUpdateService(ServiceRegistry serviceRegistry) {
        serviceRegistryResourceApi.addOrUpdateService(serviceRegistry);
    }

    @Override
    public void removeService(String name) {
        serviceRegistryResourceApi.removeService(name);
    }

    @Override
    public CircuitBreakerTransitionResponse openCircuitBreaker(String name) {
        return serviceRegistryResourceApi.openCircuitBreaker(name);
    }

    @Override
    public CircuitBreakerTransitionResponse closeCircuitBreaker(String name) {
        return serviceRegistryResourceApi.closeCircuitBreaker(name);
    }

    @Override
    public CircuitBreakerTransitionResponse getCircuitBreakerStatus(String name) {
        return serviceRegistryResourceApi.getCircuitBreakerStatus(name);
    }

    @Override
    public void addOrUpdateMethod(String registryName, ServiceMethod method) {
        serviceRegistryResourceApi.addOrUpdateMethod(method, registryName);
    }

    @Override
    public void removeMethod(String registryName, String serviceName, String method, String methodType) {
        serviceRegistryResourceApi.removeMethod(registryName, serviceName, method, methodType);
    }

    @Override
    public byte[] getProtoData(String registryName, String filename) {
        return serviceRegistryResourceApi.getProtoData(registryName, filename);
    }

    @Override
    public void setProtoData(String registryName, String filename, byte[] data) {
        serviceRegistryResourceApi.setProtoData(data, registryName, filename);
    }

    @Override
    public void deleteProto(String registryName, String filename) {
        serviceRegistryResourceApi.deleteProto(registryName, filename);
    }

    @Override
    public List<ProtoRegistryEntry> getAllProtos(String registryName) {
        return serviceRegistryResourceApi.getAllProtos(registryName);
    }

    @Override
    public List<ServiceMethod> discover(String name, boolean create) {
        return serviceRegistryResourceApi.discover(name, create);
    }
}
