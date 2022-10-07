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

import java.util.concurrent.TimeUnit;

import io.orkes.conductor.client.ApiClient;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.ChannelOption;

public abstract class ChannelManager {

    private ChannelManager() {}

    public static ManagedChannel getChannel(ApiClient apiClient) {
        String host = apiClient.getGrpcHost();
        int port = apiClient.getGrpcPort();
        boolean useSSL = apiClient.useSSL();

        NettyChannelBuilder channelBuilder =
                NettyChannelBuilder.forAddress(host, port)
                        .enableRetry()
                        .withOption(
                                ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                (int) TimeUnit.SECONDS.toMillis(5000))
                        .maxRetryAttempts(3)
                        .maxRetryAttempts(100)
                        .defaultLoadBalancingPolicy("round_robin")
                        .disableServiceConfigLookUp();

        if (!useSSL) {
            channelBuilder = channelBuilder.usePlaintext();
        } else {
            channelBuilder = channelBuilder.useTransportSecurity();
        }

        return channelBuilder.build();
    }
}
