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
package io.orkes.conductor.client.grpc;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

import io.orkes.conductor.client.ApiClient;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderClientInterceptor implements ClientInterceptor {

    private static final Metadata.Key<String> AUTH_HEADER =
            Metadata.Key.of("X-AUTHORIZATION", Metadata.ASCII_STRING_MARSHALLER);

    private static final Metadata.Key<String> CLIENT_ID_HEADER =
            Metadata.Key.of("X-Client-Id", Metadata.ASCII_STRING_MARSHALLER);

    private final String clientId;

    private final ApiClient apiClient;

    public HeaderClientInterceptor(ApiClient apiClient) {
        this.clientId = getIdentity();
        this.apiClient = apiClient;
        log.info("Setting client id to {}", clientId);
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                try {
                    if (apiClient.useSecurity()) {
                        headers.put(AUTH_HEADER, apiClient.getToken());
                    }
                    headers.put(CLIENT_ID_HEADER, clientId);
                } catch (Throwable t) {
                }
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                                responseListener) {},
                        headers);
            }
        };
    }

    private String getIdentity() {
        String serverId = System.getenv("LOCAL_HOST_IP");
        if (StringUtils.isBlank(serverId)) {
            try {
                serverId = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
            }
        }

        if (StringUtils.isBlank(serverId)) {
            serverId = System.getenv("HOSTNAME");
        }
        return serverId;
    }
}
