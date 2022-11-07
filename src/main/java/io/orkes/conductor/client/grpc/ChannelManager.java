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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.netflix.conductor.common.config.ObjectMapperProvider;

import io.orkes.conductor.client.ApiClient;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class ChannelManager {

    private ChannelManager() {}

    public static ManagedChannel getChannel(ApiClient apiClient) {
        String host = apiClient.getGrpcHost();
        int port = apiClient.getGrpcPort();
        boolean useSSL = apiClient.useSSL();
        Map<String, Object> serviceConfig = new HashMap<>();
        try {
            serviceConfig = new ObjectMapperProvider().getObjectMapper().readValue(ChannelManager.class.getResourceAsStream("/service_config.json"), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Unable to find the service config", e);
        }
        NettyChannelBuilder channelBuilder =
                NettyChannelBuilder.forAddress(host, port)
                        .eventLoopGroup(new NioEventLoopGroup())
                        .channelType(NioSocketChannel.class)
                        .enableRetry()
                        .withOption(
                                ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                (int) TimeUnit.SECONDS.toMillis(5000))
                        .defaultServiceConfig(serviceConfig)
                        .keepAliveTime(10, TimeUnit.MINUTES)
                        .defaultLoadBalancingPolicy("round_robin");
        if(apiClient.getExecutorThreadCount() > 0) {
            channelBuilder = channelBuilder.executor(Executors.newFixedThreadPool(apiClient.getExecutorThreadCount()));
        }

        if (!useSSL) {
            channelBuilder = channelBuilder.usePlaintext();
        } else {
            channelBuilder = channelBuilder.useTransportSecurity();
        }

        return channelBuilder.build();
    }
}
