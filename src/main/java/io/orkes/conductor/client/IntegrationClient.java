package io.orkes.conductor.client;

/**
 * Manage integrations
 */
public interface IntegrationClient {
    void addOrUpdate();
    void remove();
    void delete();
    void get();
    void addAPI();
    void removeAPI();
    void getAPIs();

}
