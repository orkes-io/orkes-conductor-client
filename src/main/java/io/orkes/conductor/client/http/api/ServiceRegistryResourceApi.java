package io.orkes.conductor.client.http.api;

import com.google.common.reflect.TypeToken;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.ServiceRegistry;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRegistryResourceApi {
    private ApiClient apiClient;

    public ServiceRegistryResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ServiceRegistryResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getting all registered services
     */
    public com.squareup.okhttp.Call getRegisteredServicesCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;
        String localVarPath = "/service-registry";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain)
                        throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getRegisteredServicesValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        com.squareup.okhttp.Call call = getRegisteredServicesCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get all registered services
     */
    public List<ServiceRegistry> getRegisteredServices() throws ApiException {
        com.squareup.okhttp.Call call = getRegisteredServicesValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeToken<List<ServiceRegistry>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getting a service by name
     */
    public com.squareup.okhttp.Call getServiceCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;
        String localVarPath = "/service-registry/{name}"
                .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain)
                        throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call getServiceValidateBeforeCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling getService(Async)");
        }

        com.squareup.okhttp.Call call = getServiceCall(name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get a service by name
     */
    public ServiceRegistry getService(String name) throws ApiException {
        com.squareup.okhttp.Call call = getServiceValidateBeforeCall(name, null, null);
        Type localVarReturnType = new TypeToken<ServiceRegistry>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for removing a service
     */
    public com.squareup.okhttp.Call removeServiceCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;
        String localVarPath = "/service-registry/{name}"
                .replaceAll("\\{" + "name" + "\\}", apiClient.escapeString(name));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain)
                        throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[]{"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "DELETE",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private com.squareup.okhttp.Call removeServiceValidateBeforeCall(
            String name,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException(
                    "Missing the required parameter 'name' when calling removeService(Async)");
        }

        com.squareup.okhttp.Call call = removeServiceCall(name, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Remove a service by name
     */
    public void removeService(String name) throws ApiException {
        com.squareup.okhttp.Call call = removeServiceValidateBeforeCall(name, null, null);
        apiClient.execute(call);
    }

    // Continue with similar implementations for all other endpoints...
    // For brevity, I'm showing just a few operations, but the same pattern applies to all endpoints
}