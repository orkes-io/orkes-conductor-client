/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.http.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.http.ProgressRequestBody;
import io.orkes.conductor.client.http.ProgressResponseBody;
import io.orkes.conductor.client.model.TagObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecretResourceApi {
    private ApiClient apiClient;

    public SecretResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for deleteSecret
     *
     * @param key (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call deleteSecretCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

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
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
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

    private okhttp3.Call deleteSecretValidateBeforeCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling deleteSecret(Async)");
        }

        okhttp3.Call call =
                deleteSecretCall(key, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Delete a secret value by key
     *
     * @param key (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void deleteSecret(String key) throws ApiException {
        deleteSecretWithHttpInfo(key);
    }

    /**
     * Delete a secret value by key
     *
     * @param key (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Object> deleteSecretWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = deleteSecretValidateBeforeCall(key, null, null);
        Type localVarReturnType = new TypeReference<Object>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for getSecret
     *
     * @param key (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getSecretCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

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
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
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

    private okhttp3.Call getSecretValidateBeforeCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling getSecret(Async)");
        }

        okhttp3.Call call =
                getSecretCall(key, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Get secret value by key
     *
     * @param key (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public String getSecret(String key) throws ApiException {
        ApiResponse<String> resp = getSecretWithHttpInfo(key);
        return resp.getData();
    }

    /**
     * Get secret value by key
     *
     * @param key (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<String> getSecretWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = getSecretValidateBeforeCall(key, null, null);
        Type localVarReturnType = new TypeReference<String>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for listAllSecretNames
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call listAllSecretNamesCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets";

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
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call listAllSecretNamesValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        okhttp3.Call call =
                listAllSecretNamesCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * List all secret names
     *
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Set<String> listAllSecretNames() throws ApiException {
        ApiResponse<Set<String>> resp = listAllSecretNamesWithHttpInfo();
        return resp.getData();
    }

    /**
     * List all secret names
     *
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Set<String>> listAllSecretNamesWithHttpInfo() throws ApiException {
        okhttp3.Call call = listAllSecretNamesValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<Set<String>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for listSecretsThatUserCanGrantAccessTo
     *
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call listSecretsThatUserCanGrantAccessToCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets";

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
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
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

    private okhttp3.Call listSecretsThatUserCanGrantAccessToValidateBeforeCall(
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {

        okhttp3.Call call =
                listSecretsThatUserCanGrantAccessToCall(progressListener, progressRequestListener);
        return call;
    }

    /**
     * List all secret names user can grant access to
     *
     * @return List&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException {
        ApiResponse<List<String>> resp = listSecretsThatUserCanGrantAccessToWithHttpInfo();
        return resp.getData();
    }

    /**
     * List all secret names user can grant access to
     *
     * @return ApiResponse&lt;List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<List<String>> listSecretsThatUserCanGrantAccessToWithHttpInfo()
            throws ApiException {
        okhttp3.Call call =
                listSecretsThatUserCanGrantAccessToValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeReference<List<String>>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for putSecret
     *
     * @param body (required)
     * @param key (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call putSecretCall(
            String body,
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}"
                        .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
        return apiClient.buildCall(
                localVarPath,
                "PUT",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarPostBody,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                progressRequestListener);
    }

    private okhttp3.Call putSecretValidateBeforeCall(
            String body,
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException(
                    "Missing the required parameter 'body' when calling putSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling putSecret(Async)");
        }

        okhttp3.Call call =
                putSecretCall(body, key, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Put a secret value by key
     *
     * @param body (required)
     * @param key (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public void putSecret(String body, String key) throws ApiException {
        putSecretWithHttpInfo(body, key);
    }

    /**
     * Put a secret value by key
     *
     * @param body (required)
     * @param key (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Object> putSecretWithHttpInfo(String body, String key) throws ApiException {
        okhttp3.Call call = putSecretValidateBeforeCall(body, key, null, null);
        Type localVarReturnType = new TypeReference<Object>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for secretExists
     *
     * @param key (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call secretExistsCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath =
                "/secrets/{key}/exists"
                        .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

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
            apiClient
                    .getHttpClient()
                    .networkInterceptors()
                    .add(
                            new okhttp3.Interceptor() {
                                @Override
                                public okhttp3.Response intercept(
                                        okhttp3.Interceptor.Chain chain)
                                        throws IOException {
                                    okhttp3.Response originalResponse =
                                            chain.proceed(chain.request());
                                    return originalResponse
                                            .newBuilder()
                                            .body(
                                                    new ProgressResponseBody(
                                                            originalResponse.body(),
                                                            progressListener))
                                            .build();
                                }
                            });
        }

        String[] localVarAuthNames = new String[] {"api_key"};
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

    private okhttp3.Call secretExistsValidateBeforeCall(
            String key,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener)
            throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException(
                    "Missing the required parameter 'key' when calling secretExists(Async)");
        }

        okhttp3.Call call =
                secretExistsCall(key, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Check if secret exists
     *
     * @param key (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    public Boolean secretExists(String key) throws ApiException {
        ApiResponse<Boolean> resp = secretExistsWithHttpInfo(key);
        return resp.getData();
    }

    /**
     * Check if secret exists
     *
     * @param key (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
     *     response body
     */
    private ApiResponse<Boolean> secretExistsWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = secretExistsValidateBeforeCall(key, null, null);
        Type localVarReturnType = new TypeReference<Boolean>() {}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Tag a secret
     *
     * @param body  (required)
     * @param key  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void putTagForSecret(List<TagObject> body, String key) throws ApiException {
        putTagForSecretWithHttpInfo(body, key);
    }

    /**
     * Tag a secret
     *
     * @param body  (required)
     * @param key  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> putTagForSecretWithHttpInfo(List<TagObject> body, String key) throws ApiException {
        okhttp3.Call call = putTagForSecretValidateBeforeCall(body, key, null, null);
        return apiClient.execute(call);
    }

    private okhttp3.Call putTagForSecretValidateBeforeCall(List<TagObject> body, String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling putTagForSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling putTagForSecret(Async)");
        }

        okhttp3.Call call = putTagForSecretCall(body, key, progressListener, progressRequestListener);
        return call;
    }
    /**
     * Build call for putTagForSecret
     * @param body  (required)
     * @param key  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private okhttp3.Call putTagForSecretCall(List<TagObject> body, String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new okhttp3.Interceptor() {
                @Override
                public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
                    okhttp3.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    /**
     * Delete tags of the secret
     *
     * @param body  (required)
     * @param key  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void deleteTagForSecret(List<TagObject> body, String key) throws ApiException {
        deleteTagForSecretWithHttpInfo(body, key);
    }

    /**
     * Delete tags of the secret
     *
     * @param body  (required)
     * @param key  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<Void> deleteTagForSecretWithHttpInfo(List<TagObject> body, String key) throws ApiException {
        okhttp3.Call call = deleteTagForSecretValidateBeforeCall(body, key, null, null);
        return apiClient.execute(call);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteTagForSecretValidateBeforeCall(List<TagObject> body, String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling deleteTagForSecret(Async)");
        }
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling deleteTagForSecret(Async)");
        }

        okhttp3.Call call = deleteTagForSecretCall(body, key, progressListener, progressRequestListener);
        return call;
    }
    /**
     * Build call for deleteTagForSecret
     * @param body  (required)
     * @param key  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private okhttp3.Call deleteTagForSecretCall(List<TagObject> body, String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new okhttp3.Interceptor() {
                @Override
                public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
                    okhttp3.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    /**
     * Get tags by secret
     *
     * @param key  (required)
     * @return List&lt;TagObject&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<TagObject> getTags(String key) throws ApiException {
        ApiResponse<List<TagObject>> resp = getTagsWithHttpInfo(key);
        return resp.getData();
    }

    /**
     * Get tags by secret
     *
     * @param key  (required)
     * @return ApiResponse&lt;List&lt;TagObject&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    private ApiResponse<List<TagObject>> getTagsWithHttpInfo(String key) throws ApiException {
        okhttp3.Call call = getTagsValidateBeforeCall(key, null, null);
        Type localVarReturnType = new TypeReference<List<TagObject>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getTagsValidateBeforeCall(String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getTags(Async)");
        }

        okhttp3.Call call = getTagsCall(key, progressListener, progressRequestListener);
        return call;
    }

    /**
     * Build call for getTags
     * @param key  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    private okhttp3.Call getTagsCall(String key, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/secrets/{key}/tags"
                .replaceAll("\\{" + "key" + "\\}", apiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new okhttp3.Interceptor() {
                @Override
                public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
                    okhttp3.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "api_key" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
}
