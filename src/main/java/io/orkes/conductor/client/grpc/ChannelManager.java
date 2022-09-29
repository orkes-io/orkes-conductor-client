package io.orkes.conductor.client.grpc;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.ChannelOption;
import io.orkes.conductor.client.ApiClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ChannelManager {

    private static ScheduledExecutorService channelMonitor = Executors.newScheduledThreadPool(1);

    private ChannelManager() {}

    static ManagedChannel getChannel(ApiClient apiClient) {
        String host = apiClient.getHost();
        int port = apiClient.getGrpcPort();
        boolean useSSL = apiClient.useSSL();

        NettyChannelBuilder channelBuilder = NettyChannelBuilder
                .forAddress(host, port)
                .enableRetry()
                .withOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) TimeUnit.SECONDS.toMillis(5000))
                .maxRetryAttempts(3)
                .maxRetryAttempts(100)
                .maxInboundMessageSize(50 * 1024 * 10)
                .disableServiceConfigLookUp();

        if(!useSSL) {
            channelBuilder = channelBuilder.usePlaintext();
        } else {
            channelBuilder = channelBuilder.useTransportSecurity();
        }

        final ManagedChannel channel = channelBuilder.build();
//        channelMonitor.scheduleAtFixedRate(()-> {
//            ConnectivityState state = channel.getState(true);
//            switch (state) {
//                case TRANSIENT_FAILURE:
//                case SHUTDOWN:
//                    channel.resetConnectBackoff();
//            }
//        }, 10, 1, TimeUnit.SECONDS);

        return channel;
    }


}
