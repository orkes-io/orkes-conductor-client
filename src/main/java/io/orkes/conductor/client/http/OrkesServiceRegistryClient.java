package io.orkes.conductor.client.http;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.ServiceRegistryClient;
import io.orkes.conductor.client.http.api.ServiceRegistryResourceApi;
import io.orkes.conductor.client.model.CircuitBreakerTransitionResponse;
import io.orkes.conductor.client.model.ServiceRegistry;

import java.util.List;

public class OrkesServiceRegistryClient extends OrkesClient implements ServiceRegistryClient {

    private ServiceRegistryResourceApi serviceRegistryResourceApi;

    public OrkesServiceRegistryClient(ApiClient apiClient) {
        super(apiClient);
        this.serviceRegistryResourceApi = new ServiceRegistryResourceApi(apiClient);
    }

    @Override
    public List<ServiceRegistry> getRegisteredServices() {
        try {
            return serviceRegistryResourceApi.getRegisteredServicesCall();
        } catch (ApiException e) {
            throw new RuntimeException("Failed to get registered services", e);
        }
    }

    @Override
    public ServiceRegistry getService(String name) {
        try {
            return serviceRegistryResourceApi.getServiceCall(name);
        } catch (ApiException e) {
            throw new RuntimeException("Failed to get service: " + name, e);
        }
    }

    @Override
    public void addOrUpdateService(ServiceRegistry serviceRegistry) {
        try {
            serviceRegistryResourceApi.addOrUpdateServiceCall(serviceRegistry);
        } catch (ApiException e) {
            throw new RuntimeException("Failed to add/update service", e);
        }
    }

    @Override
    public void removeService(String name) {
        try {
            serviceRegistryResourceApi.removeServiceCall(name);
        } catch (ApiException e) {
            throw new RuntimeException("Failed to remove service: " + name, e);
        }
    }

    @Override
    public CircuitBreakerTransitionResponse openCircuitBreaker(String name) {
        try {
            return serviceRegistryResourceApi.openCircuitBreakerCall(name);
        } catch (ApiException e) {
            throw new RuntimeException("Failed to open circuit breaker for: " + name, e);
        }
    }

    // Implement remaining methods following the same pattern...
}
