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
import java.util.UUID;

import com.amazonaws.util.EC2MetadataUtils;
import io.grpc.*;

public class HeaderClientInterceptor implements ClientInterceptor {

    private static final Metadata.Key<String> AUTH_HEADER =
            Metadata.Key.of("X-AUTHORIZATION", Metadata.ASCII_STRING_MARSHALLER);

    private static final Metadata.Key<String> CLIENT_ID_HEADER =
            Metadata.Key.of("X-Client-Id", Metadata.ASCII_STRING_MARSHALLER);

    private String token;

    private final String clientId;

    public HeaderClientInterceptor() {
        this.clientId = getIdentity();
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                try {
                    if(token != null) {
                        headers.put(AUTH_HEADER, token);
                    }
                    headers.put(CLIENT_ID_HEADER, clientId);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                                responseListener) {},
                        headers);
            }
        };
    }

    void setToken(String token) {
        this.token = token;
    }

    private String getIdentity() {
        String clientId;
        try {
            clientId = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            clientId = System.getenv("HOSTNAME");
        }
        if (clientId == null) {
            clientId = UUID.randomUUID().toString();
        }
        return clientId;
    }
}
