/*
 * Copyright 2020 Orkes, Inc.
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
package io.orkes.conductor.client.spring;



import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.orkes.conductor.client.ApiClient;

import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class ApiClientAutoConfiguration {

    public static final String CONDUCTOR_SERVER_URL = "conductor.server.url";
    public static final String CONDUCTOR_CLIENT_KEY_ID = "conductor.security.client.key-id";
    public static final String CONDUCTOR_CLIENT_SECRET = "conductor.security.client.secret";
    public static final String CONDUCTOR_GRPC_SERVER = "conductor.grpc.host";

    public static final String CONDUCTOR_GRPC_PORT = "conductor.grpc.port";

    public static final String CONDUCTOR_GRPC_SSL = "conductor.grpc.ssl";

    @Bean
    public ApiClient getApiClient(Environment env) {
        String rootUri = env.getProperty(CONDUCTOR_SERVER_URL);
        String keyId = env.getProperty(CONDUCTOR_CLIENT_KEY_ID);
        String secret = env.getProperty(CONDUCTOR_CLIENT_SECRET);
        if (rootUri != null && rootUri.endsWith("/")) {
            rootUri = rootUri.substring(0, rootUri.length() - 1);
        }
        if (StringUtils.isNotBlank(keyId) && StringUtils.isNotBlank(secret)) {
            ApiClient apiClient = new ApiClient(rootUri, keyId, secret);
            apiClient = configureGrpc(apiClient, env);
            return apiClient;
        }

        ApiClient apiClient = new ApiClient(rootUri);
        apiClient = configureGrpc(apiClient, env);
        return apiClient;
    }

    private ApiClient configureGrpc(ApiClient apiClient, Environment env) {

        String grpcHost = env.getProperty(CONDUCTOR_GRPC_SERVER);
        String grpcPort = env.getProperty(CONDUCTOR_GRPC_PORT);
        boolean useSSL = Boolean.parseBoolean(env.getProperty(CONDUCTOR_GRPC_SSL));
        if (StringUtils.isNotBlank(grpcHost)) {
            log.info("Using gRPC for worker communication.  Server {}, port {}, using SSL? {}", grpcHost, grpcPort, useSSL);
            int port = Integer.parseInt(grpcPort);
            apiClient.setUseGRPC(grpcHost, port);
            apiClient.setUseSSL(useSSL);
        }
        return apiClient;
    }

}
