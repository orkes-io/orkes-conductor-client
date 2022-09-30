package io.orkes.conductor.client.grpc;


import com.amazonaws.util.EC2MetadataUtils;
import io.grpc.*;
import io.orkes.conductor.client.ApiClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                try {
                    headers.put(AUTH_HEADER, token);
                    headers.put(CLIENT_ID_HEADER, clientId);
                } catch (Throwable t) {}
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {}, headers);
            }
        };
    }

    void setToken(String token) {
        this.token = token;
    }
    private String getIdentity() {
        String serverId;
        try {
            serverId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverId = System.getenv("HOSTNAME");
        }
        if (serverId == null) {
            serverId =
                    (EC2MetadataUtils.getInstanceId() == null)
                            ? System.getProperty("user.name")
                            : EC2MetadataUtils.getInstanceId();
        }
        return serverId;
    }
}