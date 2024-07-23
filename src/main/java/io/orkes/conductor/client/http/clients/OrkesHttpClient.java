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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.ApiResponse;
import io.orkes.conductor.client.http.ConflictException;
import io.orkes.conductor.client.http.JSON;
import io.orkes.conductor.client.http.Pair;
import io.orkes.conductor.client.http.auth.ApiKeyAuth;
import io.orkes.conductor.client.http.auth.Authentication;
import io.orkes.conductor.client.model.GenerateTokenRequest;
import io.orkes.conductor.client.model.validation.ErrorResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;
import okio.BufferedSink;
import okio.Okio;

public class OrkesHttpClient {

    public static final String PROP_TOKEN_REFRESH_INTERVAL = "CONDUCTOR_SECURITY_TOKEN_REFRESH_INTERVAL";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrkesHttpClient.class);
    private static final String TOKEN_CACHE_KEY = "TOKEN";
    private final Cache<String, String> tokenCache;

    private final Map<String, String> defaultHeaderMap;
    private final OkHttpClient httpClient;
    private final String basePath;
    private final String tempFolderPath;
    private final boolean verifyingSsl;
    private final InputStream sslCaCert;
    private final KeyManager[] keyManagers;
    private final String keyId;
    private final String keySecret;
    private final JSON json;
    private int executorThreadCount = 0;
    private long tokenRefreshInSeconds = 2700;  //45 minutes

    public static class Builder {
        private String basePath = "http://localhost:8080/api";
        private String tempFolderPath;
        private boolean verifyingSsl = true;
        private InputStream sslCaCert;
        private KeyManager[] keyManagers;
        private String keyId;
        private String keySecret;
        private int executorThreadCount;
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

        public String tempFolderPath() {
            return tempFolderPath;
        }

        public Builder tempFolderPath(String tempFolderPath) {
            this.tempFolderPath = tempFolderPath;
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

        public int executorThreadCount() {
            return executorThreadCount;
        }

        public Builder executorThreadCount(int executorThreadCount) {
            this.executorThreadCount = executorThreadCount;
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
        this.tempFolderPath = builder.tempFolderPath();

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
        this.httpClient = okHttpBuilder.build();

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

    private void scheduleTokenRefresh() {
        ScheduledExecutorService tokenRefreshService = Executors.newSingleThreadScheduledExecutor();
        long refreshInterval = Math.max(30, tokenRefreshInSeconds - 30);
        LOGGER.info("Starting token refresh thread to run at every {} seconds", refreshInterval);
        tokenRefreshService.scheduleAtFixedRate(this::refreshToken, refreshInterval, refreshInterval, TimeUnit.SECONDS);
    }

    public boolean isSecurityEnabled() {
        return StringUtils.isNotBlank(keyId) && StringUtils.isNotBlank(keySecret);
    }

    public String getBasePath() {
        return basePath;
    }

    public int getExecutorThreadCount() {
        return executorThreadCount;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public void shutdown() {
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
        if (httpClient.cache() != null) {
            try {
                httpClient.cache().close();
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

    public String getTempFolderPath() {
        return tempFolderPath;
    }

    public String parameterToString(Object param) {
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

    public List<Pair> parameterToPair(String name, Object value) {
        List<Pair> params = new ArrayList<>();

        // preconditions
        if (name == null || name.isEmpty() || value == null || value instanceof Collection)
            return params;

        params.add(new Pair(name, parameterToString(value)));
        return params;
    }

    public List<Pair> parameterToPairs(String collectionFormat, String name, Collection value) {
        List<Pair> params = new ArrayList<>();

        // preconditions
        if (name == null || name.isEmpty() || value == null || value.isEmpty()) {
            return params;
        }

        // create the params based on the collection format
        if ("multi".equals(collectionFormat)) {
            for (Object item : value) {
                params.add(new Pair(name, escapeString(parameterToString(item))));
            }
            return params;
        }

        // collectionFormat is assumed to be "csv" by default
        String delimiter = ",";

        // escape all delimiters except commas, which are URI reserved
        // characters
        if ("ssv".equals(collectionFormat)) {
            delimiter = escapeString(" ");
        } else if ("tsv".equals(collectionFormat)) {
            delimiter = escapeString("\t");
        } else if ("pipes".equals(collectionFormat)) {
            delimiter = escapeString("|");
        }

        StringBuilder sb = new StringBuilder();
        for (Object item : value) {
            sb.append(delimiter);
            sb.append(escapeString(parameterToString(item)));
        }

        params.add(new Pair(name, sb.substring(delimiter.length())));

        return params;
    }

    public String sanitizeFilename(String filename) {
        return filename.replaceAll(".*[/\\\\]", "");
    }

    public boolean isJsonMime(String mime) {
        String jsonMime = "(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$";
        return mime != null && (mime.matches(jsonMime) || mime.equals("*/*"));
    }

    public String selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        for (String accept : accepts) {
            if (isJsonMime(accept)) {
                return accept;
            }
        }

        return StringUtils.joinWith(",", (Object[]) accepts);
    }

    public String selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0 || contentTypes[0].equals("*/*")) {
            return "application/json";
        }
        for (String contentType : contentTypes) {
            if (isJsonMime(contentType)) {
                return contentType;
            }
        }
        return contentTypes[0];
    }

    public String escapeString(String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(Response response, Type returnType) throws ApiException {
        if (response == null || returnType == null) {
            return null;
        }

        if ("byte[]".equals(returnType.toString())) {
            // Handle binary response (byte array).
            try {
                return (T) response.body().bytes();
            } catch (IOException e) {
                throw new ApiException(e);
            }
        } else if (returnType.equals(File.class)) {
            // Handle file downloading.
            return (T) downloadFileFromResponse(response);
        }

        String respBody;
        try {
            if (response.body() != null) respBody = response.body().string();
            else respBody = null;
        } catch (IOException e) {
            throw new ApiException(e);
        }

        if (respBody == null || "".equals(respBody)) {
            return null;
        }

        String contentType = response.headers().get("Content-Type");
        if (contentType == null) {
            // ensuring a default content type
            contentType = "application/json";
        }
        if (isJsonMime(contentType)) {
            return json.deserialize(respBody, returnType);
        } else if (returnType.equals(String.class)) {
            // Expecting string, return the raw response body.
            return (T) respBody;
        } else {
            throw new ApiException(
                    "Content type \"" + contentType + "\" is not supported for type: " + returnType,
                    response.code(),
                    response.headers().toMultimap(),
                    respBody);
        }
    }

    public RequestBody serialize(Object obj, String contentType) throws ApiException {
        if (obj instanceof byte[]) {
            // Binary (byte array) body parameter support.
            return RequestBody.create(MediaType.parse(contentType), (byte[]) obj);
        } else if (obj instanceof File) {
            // File body parameter support.
            return RequestBody.create(MediaType.parse(contentType), (File) obj);
        } else if (isJsonMime(contentType)) {
            String content = null;
            if (obj != null) {
                if (obj instanceof String) {
                    content = (String) obj;
                } else {
                    content = json.serialize(obj);
                }
            }
            return RequestBody.create(MediaType.parse(contentType), content);
        } else {
            throw new ApiException("Content type \"" + contentType + "\" is not supported");
        }
    }

    public File downloadFileFromResponse(Response response) throws ApiException {
        try {
            File file = prepareDownloadFile(response);
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(response.body().source());
            sink.close();
            return file;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    public File prepareDownloadFile(Response response) throws IOException {
        String filename = null;
        String contentDisposition = response.header("Content-Disposition");
        if (contentDisposition != null && !"".equals(contentDisposition)) {
            // Get filename from the Content-Disposition header.
            Pattern pattern = Pattern.compile("filename=['\"]?([^'\"\\s]+)['\"]?");
            Matcher matcher = pattern.matcher(contentDisposition);
            if (matcher.find()) {
                filename = sanitizeFilename(matcher.group(1));
            }
        }

        String prefix = null;
        String suffix = null;
        if (filename == null) {
            prefix = "download-";
            suffix = "";
        } else {
            int pos = filename.lastIndexOf(".");
            if (pos == -1) {
                prefix = filename + "-";
            } else {
                prefix = filename.substring(0, pos) + "-";
                suffix = filename.substring(pos);
            }
            // File.createTempFile requires the prefix to be at least three characters long
            if (prefix.length() < 3) prefix = "download-";
        }

        if (tempFolderPath == null) return Files.createTempFile(prefix, suffix).toFile();
        else return Files.createTempFile(Paths.get(tempFolderPath), prefix, suffix).toFile();
    }

    public <T> T handleResponse(Response response, Type returnType) throws ApiException {
        if (response.isSuccessful()) {
            if (returnType == null || response.code() == 204) {
                // returning null if the returnType is not defined,
                // or the status code is 204 (No Content)
                if (response.body() != null) {
                    response.body().close();
                }
                return null;
            } else {
                return deserialize(response, returnType);
            }
        } else {
            String respBody = null;
            if (response.body() != null) {
                try {
                    respBody = response.body().string();
                    if (response.code() == 409) {
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
                } catch (IOException e) {
                    throw new ApiException(
                            response.message(),
                            e,
                            response.code(),
                            response.headers().toMultimap());
                }
            }
            throw new ApiException(response.message(), response.code(), response.headers().toMultimap(), respBody);
        }
    }

    public Call buildCall(
            String path,
            String method,
            List<Pair> queryParams,
            List<Pair> collectionQueryParams,
            Object body,
            Map<String, String> headerParams,
            Map<String, Object> formParams,
            String[] authNames)
            throws ApiException {
        Request request =
                buildRequest(
                        path,
                        method,
                        queryParams,
                        collectionQueryParams,
                        body,
                        headerParams,
                        formParams,
                        authNames);
        return httpClient.newCall(request);
    }

    public Request buildRequest(
            String path,
            String method,
            List<Pair> queryParams,
            List<Pair> collectionQueryParams,
            Object body,
            Map<String, String> headerParams,
            Map<String, Object> formParams,
            String[] authNames)
            throws ApiException {
        if (!"/token".equalsIgnoreCase(path)) {
            updateParamsForAuth(authNames, queryParams, headerParams);
        }

        final String url = buildUrl(path, queryParams, collectionQueryParams);
        final Request.Builder reqBuilder = new Request.Builder().url(url);
        processHeaderParams(headerParams, reqBuilder);

        String contentType = headerParams.get("Content-Type");
        // ensuring a default content type
        if (contentType == null) {
            contentType = "application/json";
        }

        RequestBody reqBody;
        if (!HttpMethod.permitsRequestBody(method)) {
            reqBody = null;
        } else if ("application/x-www-form-urlencoded".equals(contentType)) {
            reqBody = buildRequestBodyFormEncoding(formParams);
        } else if ("multipart/form-data".equals(contentType)) {
            reqBody = buildRequestBodyMultipart(formParams);
        } else if (body == null) {
            if ("DELETE".equals(method)) {
                // allow calling DELETE without sending a request body
                reqBody = null;
            } else {
                // use an empty request body (for POST, PUT and PATCH)
                reqBody = RequestBody.create(MediaType.parse(contentType), "");
            }
        } else {
            reqBody = serialize(body, contentType);
        }

        return reqBuilder.method(method, reqBody).build();
    }

    public String buildUrl(String path, List<Pair> queryParams, List<Pair> collectionQueryParams) {
        final StringBuilder url = new StringBuilder();
        url.append(basePath).append(path);

        if (queryParams != null && !queryParams.isEmpty()) {
            // support (constant) query string in `path`, e.g. "/posts?draft=1"
            String prefix = path.contains("?") ? "&" : "?";
            for (Pair param : queryParams) {
                if (param.getValue() != null) {
                    if (prefix != null) {
                        url.append(prefix);
                        prefix = null;
                    } else {
                        url.append("&");
                    }
                    String value = parameterToString(param.getValue());
                    url.append(escapeString(param.getName()))
                            .append("=")
                            .append(escapeString(value));
                }
            }
        }

        if (collectionQueryParams != null && !collectionQueryParams.isEmpty()) {
            String prefix = url.toString().contains("?") ? "&" : "?";
            for (Pair param : collectionQueryParams) {
                if (param.getValue() != null) {
                    if (prefix != null) {
                        url.append(prefix);
                        prefix = null;
                    } else {
                        url.append("&");
                    }
                    String value = parameterToString(param.getValue());
                    // collection query parameter value already escaped as part of parameterToPairs
                    url.append(escapeString(param.getName())).append("=").append(value);
                }
            }
        }

        return url.toString();
    }

    public void processHeaderParams(Map<String, String> headerParams, Request.Builder reqBuilder) {
        for (Entry<String, String> param : headerParams.entrySet()) {
            reqBuilder.header(param.getKey(), parameterToString(param.getValue()));
        }
        for (Entry<String, String> header : defaultHeaderMap.entrySet()) {
            if (!headerParams.containsKey(header.getKey())) {
                reqBuilder.header(header.getKey(), parameterToString(header.getValue()));
            }
        }
    }

    public void updateParamsForAuth(String[] authNames, List<Pair> queryParams, Map<String, String> headerParams) {
        String token = getToken();
        if (isSecurityEnabled()) {
            Authentication auth = getApiKeyHeader(token);
            auth.applyToParams(queryParams, headerParams);
        }
    }

    public RequestBody buildRequestBodyFormEncoding(Map<String, Object> formParams) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Entry<String, Object> param : formParams.entrySet()) {
            formBuilder.add(param.getKey(), parameterToString(param.getValue()));
        }
        return formBuilder.build();
    }

    public RequestBody buildRequestBodyMultipart(Map<String, Object> formParams) {
        MultipartBody.Builder mpBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Entry<String, Object> param : formParams.entrySet()) {
            if (param.getValue() instanceof File file) {
                Headers partHeaders =
                        Headers.of(
                                "Content-Disposition",
                                "form-data; name=\""
                                        + param.getKey()
                                        + "\"; filename=\""
                                        + file.getName()
                                        + "\"");
                MediaType mediaType = MediaType.parse(guessContentTypeFromFile(file));
                //FIXME deprecated method usage
                mpBuilder.addPart(MultipartBody.Part.create(partHeaders, RequestBody.create(mediaType, file)));
            } else {
                Headers partHeaders =
                        Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"" + param.getKey() + "\"");
                mpBuilder.addPart(
                        partHeaders, RequestBody.create(null, parameterToString(param.getValue())));
            }
        }
        return mpBuilder.build();
    }

    public String guessContentTypeFromFile(File file) {
        String contentType = URLConnection.guessContentTypeFromName(file.getName());
        if (contentType == null) {
            return "application/octet-stream";
        } else {
            return contentType;
        }
    }

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

    public String getToken() {
        try {
            if (!isSecurityEnabled()) {
                return null;
            }
            return tokenCache.get(TOKEN_CACHE_KEY, () -> refreshToken());
        } catch (ExecutionException e) {
            return null;
        }
    }

    public Call buildCall(String method, String path, String id, Object body) {
        String localVarPath = path.replaceAll("\\{id\\}", escapeString(id != null ? id : ""));
        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        localVarHeaderParams.put("Accept", selectHeaderAccept(new String[]{"application/json"}));
        localVarHeaderParams.put("Content-Type", selectHeaderContentType(new String[]{"application/json"}));

        return buildCall(
                localVarPath, method, localVarQueryParams, localVarCollectionQueryParams, body,
                localVarHeaderParams, localVarFormParams, new String[]{"api_key"});
    }

    public <T> ApiResponse<T> execute(Call call) throws ApiException {
        return execute(call, (Type) null);
    }

    public <T> ApiResponse<T> execute(Call call, TypeReference<T> typeReference) throws ApiException {
        return execute(call, typeReference.getType());
    }

    public <T> ApiResponse<T> execute(Call call, Type returnType) throws ApiException {
        try {
            Response response = call.execute();
            T data = handleResponse(response, returnType);
            return new ApiResponse<>(response.code(), response.headers().toMultimap(), data);
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    public <T> ApiResponse<T> doRequest(String method,
                                         String path,
                                         String id,
                                         Object body,
                                         TypeReference<T> typeReference) throws ApiException {
        Call call = buildCall(method, path, id, body);
        if (typeReference == null) {
            execute(call);
            return null;
        } else {
            return execute(call, typeReference.getType());
        }
    }

    private String refreshToken() {
        LOGGER.debug("Refreshing token @ {}", new Date());
        if (this.keyId == null || this.keySecret == null) {
            throw new RuntimeException("KeyId and KeySecret must be set in order to get an authentication token");
        }
        GenerateTokenRequest generateTokenRequest = new GenerateTokenRequest().keyId(this.keyId).keySecret(this.keySecret);
        Map<String, String> response = TokenResource.generateTokenWithHttpInfo(this, generateTokenRequest).getData();
        return response.get("token");
    }

    private ApiKeyAuth getApiKeyHeader(String token) {
        ApiKeyAuth apiKeyAuth = new ApiKeyAuth("header", "X-Authorization");
        apiKeyAuth.setApiKey(token);
        return apiKeyAuth;
    }


}
