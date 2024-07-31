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
package io.orkes.conductor.client.http.clients;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.OrkesClientException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.ConflictException;
import io.orkes.conductor.client.http.JSON;
import io.orkes.conductor.client.http.Param;
import io.orkes.conductor.client.model.GenerateTokenRequest;
import io.orkes.conductor.client.model.TokenResponse;
import io.orkes.conductor.client.model.validation.ErrorResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

public class OrkesHttpClient {

    public static final String PROP_TOKEN_REFRESH_INTERVAL = "CONDUCTOR_SECURITY_TOKEN_REFRESH_INTERVAL";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrkesHttpClient.class);
    private static final String TOKEN_CACHE_KEY = "TOKEN";
    private final Cache<String, String> tokenCache;

    private final Map<String, String> defaultHeaderMap;
    private final OkHttpClient okHttpClient;
    private final String basePath;
    private final boolean verifyingSsl;
    private final InputStream sslCaCert;
    private final KeyManager[] keyManagers;
    private final String keyId;
    private final String keySecret;
    private final JSON json;

    private long tokenRefreshInSeconds = 2700;  //45 minutes

    public static class Builder {
        private String basePath = "http://localhost:8080/api";
        private boolean verifyingSsl = true;
        private InputStream sslCaCert;
        private KeyManager[] keyManagers;
        private String keyId;
        private String keySecret;
        private long tokenRefreshInSeconds;
        private long connectTimeout = -1;
        private long readTimeout = -1;
        private long writeTimeout = -1;
        private final Map<String, String> defaultHeaderMap = new HashMap<>();

        public String basePath() {
            return basePath;
        }

        public Builder basePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public boolean verifyingSsl() {
            return verifyingSsl;
        }


        public Builder verifyingSsl(boolean verifyingSsl) {
            this.verifyingSsl = verifyingSsl;
            return this;
        }

        public InputStream sslCaCert() {
            return sslCaCert;
        }

        public Builder sslCaCert(InputStream sslCaCert) {
            this.sslCaCert = sslCaCert;
            return this;
        }

        public KeyManager[] keyManagers() {
            return keyManagers;
        }

        public Builder keyManagers(KeyManager[] keyManagers) {
            this.keyManagers = keyManagers;
            return this;
        }

        public String keyId() {
            return keyId;
        }

        public Builder keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public String keySecret() {
            return keySecret;
        }

        public Builder keySecret(String keySecret) {
            this.keySecret = keySecret;
            return this;
        }

        public long tokenRefreshInSeconds() {
            return tokenRefreshInSeconds;
        }

        public Builder tokenRefreshInSeconds(long tokenRefreshInSeconds) {
            this.tokenRefreshInSeconds = tokenRefreshInSeconds;
            return this;
        }

        public Builder addDefaultHeader(String key, String value) {
            defaultHeaderMap.put(key, value);
            return this;
        }

        public Builder userAgent(String userAgent) {
            addDefaultHeader("User-Agent", userAgent);
            return this;
        }

        public Map<String, String> defaultHeaderMap() {
            return defaultHeaderMap;
        }

        public long connectTimeout() {
            return connectTimeout;
        }

        public Builder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public long readTimeout() {
            return readTimeout;
        }

        public Builder readTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public long writeTimeout() {
            return writeTimeout;
        }

        public Builder writeTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public OrkesHttpClient build() {
            return new OrkesHttpClient(this);
        }

        void validateAndAssignDefaults() {
            if (StringUtils.isBlank(basePath)) {
                throw new IllegalArgumentException("basePath cannot be blank");
            }

            if (basePath.endsWith("/")) {
                basePath = basePath.substring(0, basePath.length() - 1);
            }

            if (tokenRefreshInSeconds < 0) {
                throw new IllegalArgumentException("tokenRefreshInSeconds cannot be negative");
            }

            if (tokenRefreshInSeconds == 0) {
                String refreshInterval = System.getenv(PROP_TOKEN_REFRESH_INTERVAL);
                if (refreshInterval == null) {
                    refreshInterval = System.getProperty(PROP_TOKEN_REFRESH_INTERVAL);
                }

                if (refreshInterval != null) {
                    try {
                        tokenRefreshInSeconds = Integer.parseInt(refreshInterval);
                    } catch (Exception ignored) {
                        tokenRefreshInSeconds = 2700; // 45 minutes
                    }
                } else {
                    tokenRefreshInSeconds = 2700;
                }
            }

        }
    }

    public Builder builder() {
        return new Builder();
    }

    private OrkesHttpClient(Builder builder) {
        builder.validateAndAssignDefaults();
        this.tokenRefreshInSeconds = builder.tokenRefreshInSeconds();
        LOGGER.info("Setting token refresh interval to {} seconds", this.tokenRefreshInSeconds);
        this.basePath = builder.basePath();
        this.keyId = builder.keyId();
        this.keySecret = builder.keySecret();
        this.verifyingSsl = builder.verifyingSsl();
        this.sslCaCert = builder.sslCaCert();
        this.keyManagers = builder.keyManagers();

        this.json = new JSON();
        this.tokenCache = CacheBuilder.newBuilder().expireAfterWrite(tokenRefreshInSeconds, TimeUnit.SECONDS).build();
        this.defaultHeaderMap = builder.defaultHeaderMap();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.retryOnConnectionFailure(true);

        if (builder.connectTimeout() > -1) {
            okHttpBuilder.connectTimeout(builder.connectTimeout(), TimeUnit.MILLISECONDS);
        }

        if (builder.readTimeout() > -1) {
            okHttpBuilder.readTimeout(builder.readTimeout(), TimeUnit.MILLISECONDS);
        }

        if (builder.writeTimeout() > -1) {
            okHttpBuilder.writeTimeout(builder.writeTimeout(), TimeUnit.MILLISECONDS);
        }

        applySslSettings(okHttpBuilder);
        this.okHttpClient = okHttpBuilder.build();

        if (isSecurityEnabled()) {
            scheduleTokenRefresh();
            try {
                //This should be in the try catch so if the client is initialized and if the server is down or not reachable
                //Client will still initialize without errors
                getToken();
            } catch (Throwable t) {
                LOGGER.error(t.getMessage(), t);
            }
        }
    }

    public OrkesHttpClient() {
        this(new Builder());
    }

    public OrkesHttpClient(String basePath) {
        this(new Builder().basePath(basePath));
    }

    public boolean isSecurityEnabled() {
        return StringUtils.isNotBlank(keyId) && StringUtils.isNotBlank(keySecret);
    }

    public String getBasePath() {
        return basePath;
    }

    public void shutdown() {
        okHttpClient.dispatcher().executorService().shutdown();
        okHttpClient.connectionPool().evictAll();
        if (okHttpClient.cache() != null) {
            try {
                okHttpClient.cache().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        try {
            return new URL(basePath).getHost();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isVerifyingSsl() {
        return verifyingSsl;
    }

    public ApiResponse<Void> doRequest(OrkesHttpClientRequest req) {
        return doRequest(req, null);
    }

    public <T> ApiResponse<T> doRequest(OrkesHttpClientRequest req, TypeReference<T> typeReference) {
        Map<String, String> headerParams = req.getHeaderParams() == null ? new HashMap<>() : new HashMap<>(req.getHeaderParams());
        List<Param> pathParams = req.getPathParams() == null ? new ArrayList<>() : new ArrayList<>(req.getPathParams());
        List<Param> queryParams = req.getQueryParams() == null ? new ArrayList<>() : new ArrayList<>(req.getQueryParams());

        Request request = buildRequest(req.getMethod().toString(),
                req.getPath(),
                pathParams,
                queryParams,
                headerParams,
                req.getBody());

        Call call = okHttpClient.newCall(request);
        if (typeReference == null) {
            execute(call, null);
            return null;
        }

        return execute(call, typeReference.getType());
    }

    private void scheduleTokenRefresh() {
        ScheduledExecutorService tokenRefreshService = Executors.newSingleThreadScheduledExecutor();
        long refreshInterval = Math.max(30, tokenRefreshInSeconds - 30);
        LOGGER.info("Starting token refresh thread to run at every {} seconds", refreshInterval);
        tokenRefreshService.scheduleAtFixedRate(this::refreshToken, refreshInterval, refreshInterval, TimeUnit.SECONDS);
    }

    private String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Date
                || param instanceof OffsetDateTime
                || param instanceof LocalDate) {
            // Serialize to json string and remove the " enclosing characters
            String jsonStr = json.serialize(param);
            return jsonStr.substring(1, jsonStr.length() - 1);
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();
            for (Object o : (Collection) param) {
                if (b.length() > 0) {
                    b.append(",");
                }
                b.append(o);
            }
            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    private boolean isJsonMime(String mime) {
        String jsonMime = "(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$";
        return mime != null && (mime.matches(jsonMime) || mime.equals("*/*"));
    }

    private String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    private <T> T deserialize(Response response, Type returnType) {
        if (returnType == null) {
            return null;
        }

        String respBody = bodyAsString(response);
        if (respBody == null || "".equals(respBody)) {
            return null;
        }

        String contentType = response.header("Content-Type");
        if (contentType == null || isJsonMime(contentType)) {
            return json.deserialize(respBody, returnType);
        } else if (returnType.equals(String.class)) {
            //noinspection unchecked
            return (T) respBody;
        }

        throw new OrkesClientException(
                "Content type \"" + contentType + "\" is not supported for type: " + returnType,
                response.code(),
                response.headers().toMultimap(),
                respBody);
    }

    @Nullable
    private String bodyAsString(Response response) {
        if (response.body() == null) {
            return null;
        }

        try {
            return response.body().string();
        } catch (IOException e) {
            throw new OrkesClientException(response.message(),
                    e,
                    response.code(),
                    response.headers().toMultimap());
        }
    }

    private RequestBody serialize(String contentType, Object obj) {
        if (!isJsonMime(contentType)) {
            throw new OrkesClientException("Content type \"" + contentType + "\" is not supported");
        }

        String content = null;
        if (obj != null) {
            if (obj instanceof String) {
                content = (String) obj;
            } else {
                content = json.serialize(obj);
            }
        }

        return RequestBody.create(MediaType.parse(contentType), content);

    }

    private <T> T handleResponse(Response response, Type returnType) {
        if (!response.isSuccessful()) {
            String respBody = bodyAsString(response);
            //TODO improve Error handling
            if (response.code() == 409) {
                throwConflictException(response, respBody);
            }

            throw new OrkesClientException(response.message(),
                    response.code(),
                    response.headers().toMultimap(),
                    respBody);
        }

        try {
            if (returnType == null || response.code() == 204) {
                return null;
            } else {
                return deserialize(response, returnType);
            }
        } finally {
            if (response.body() != null) {
                response.body().close();
            }
        }
    }

    private void throwConflictException(Response response, String respBody) {
        ErrorResponse errorResponse = json.deserialize(respBody, ErrorResponse.class);
        String message;
        if (errorResponse != null && errorResponse.getMessage() != null) {
            message = errorResponse.getMessage();
        } else {
            message = response.message();
        }

        throw new ConflictException(
                message,
                response.code(),
                response.headers().toMultimap(), respBody);
    }

    private Request buildRequest(String method,
                                 String path,
                                 List<Param> pathParams,
                                 List<Param> queryParams,
                                 Map<String, String> headerParams,
                                 Object body) {
        if (!"/token".equalsIgnoreCase(path)) {
            addAuthHeader(headerParams);
        }

        for (Param param : pathParams) {
            String encodedValue = urlEncode(param.value());
            path = path.replace("{" + param.name() + "}", encodedValue);
        }

        final HttpUrl url = buildUrl(path, queryParams);
        final Request.Builder reqBuilder = new Request.Builder().url(url);
        processHeaderParams(headerParams, reqBuilder);

        String contentType = headerParams.get("Content-Type");
        if (contentType == null) {
            contentType = "application/json";
        }

        RequestBody reqBody = reqBody(method, contentType, body);
        return reqBuilder.method(method, reqBody).build();
    }

    @Nullable
    private RequestBody reqBody(String method, String contentType, Object body) {
        if (!HttpMethod.permitsRequestBody(method)) {
            return null;
        }

        if (body == null && "DELETE".equals(method)) {
            return null;
        } else if (body == null) {
            //FIXME deprecated method usage
            return RequestBody.create(MediaType.parse(contentType), "");

        }

        return serialize(contentType, body);
    }

    private HttpUrl buildUrl(String path, List<Param> queryParams) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(basePath + path))
                .newBuilder();
        for (Param param : queryParams) {
            urlBuilder.addQueryParameter(param.name(), param.value());
        }

        return urlBuilder.build();
    }

    private void processHeaderParams(Map<String, String> headerParams, Request.Builder reqBuilder) {
        for (Entry<String, String> param : headerParams.entrySet()) {
            reqBuilder.header(param.getKey(), parameterToString(param.getValue()));
        }

        for (Entry<String, String> header : defaultHeaderMap.entrySet()) {
            if (!headerParams.containsKey(header.getKey())) {
                reqBuilder.header(header.getKey(), parameterToString(header.getValue()));
            }
        }
    }

    private void addAuthHeader(Map<String, String> headerParams) {
        if (!isSecurityEnabled()) {
            return;
        }

        headerParams.put("X-Authorization", getToken());
    }

    //TODO FIX no self signed cert support
    private void applySslSettings(OkHttpClient.Builder okhttpClientBuilder) {
        try {
            TrustManager[] trustManagers = null;
            if (!verifyingSsl) {
                TrustManager trustAll =
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        };
                trustManagers = new TrustManager[]{trustAll};
                okhttpClientBuilder.hostnameVerifier((hostname, session) -> true);
            } else if (sslCaCert != null) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(sslCaCert);
                if (certificates.isEmpty()) {
                    throw new IllegalArgumentException("expected non-empty set of trusted certificates");
                }
                KeyStore caKeyStore = newEmptyKeyStore(null);
                int index = 0;
                for (Certificate certificate : certificates) {
                    String certificateAlias = "ca" + index++;
                    caKeyStore.setCertificateEntry(certificateAlias, certificate);
                }

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(caKeyStore);
                trustManagers = trustManagerFactory.getTrustManagers();
            }

            if (keyManagers != null || trustManagers != null) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, trustManagers, new SecureRandom());
                //FIXME deprecated method usage
                okhttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory());
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private String getToken() {
        if (!isSecurityEnabled()) {
            return null;
        }

        try {
            return tokenCache.get(TOKEN_CACHE_KEY, this::refreshToken);
        } catch (ExecutionException e) {
            return null;
        }
    }

    private <T> ApiResponse<T> execute(Call call, Type returnType) {
        try {
            Response response = call.execute();
            T data = handleResponse(response, returnType);
            return new ApiResponse<>(response.code(), response.headers().toMultimap(), data);
        } catch (IOException e) {
            throw new OrkesClientException(e.getMessage(), e);
        }
    }

    private String refreshToken() {
        LOGGER.debug("Refreshing token @ {}", new Date());
        if (keyId == null || keySecret == null) {
            throw new RuntimeException("KeyId and KeySecret must be set in order to get an authentication token");
        }

        TokenResource tokenResource = new TokenResource(this);
        GenerateTokenRequest generateTokenRequest = new GenerateTokenRequest(keyId, keySecret);
        TokenResponse response = tokenResource.generate(generateTokenRequest).getData();
        return response.getToken();
    }
}
