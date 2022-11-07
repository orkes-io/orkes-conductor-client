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
import java.util.concurrent.Executor;

import io.orkes.conductor.client.ApiClient;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

public class AuthToken extends CallCredentials {

    private final ApiClient apiClient;

    public AuthToken(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public void applyRequestMetadata(
            RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(
                () -> {
                    try {

                        Metadata headers = new Metadata();
                        headers.put(
                                Metadata.Key.of("X-Client-Id", Metadata.ASCII_STRING_MARSHALLER),
                                getIdentity());

                        if (apiClient.useSecurity()) {}

                        metadataApplier.apply(headers);
                    } catch (Throwable e) {
                        metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
                    }
                });
    }

    @Override
    public void thisUsesUnstableApi() {}

    private String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }
        if (serverId == null) {
            serverId = System.getProperty("user.name");
        }
        return serverId;
    }
}
