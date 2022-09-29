package io.orkes.conductor.client.grpc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import io.orkes.conductor.client.ApiClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class AuthToken extends CallCredentials {

    private static final String TOKEN_CACHE_KEY = "TOKEN";

    private final ApiClient apiClient;

    private Cache<String, String> tokenCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public AuthToken(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {

                Metadata headers = new Metadata();
                headers.put(Metadata.Key.of("X-Client-Id", Metadata.ASCII_STRING_MARSHALLER), getIdentity());

                if(apiClient.useSecurity()) {
                    String token = tokenCache.get(TOKEN_CACHE_KEY, () -> {
                        return apiClient.getRefreshedToken();
                    });
                    headers.put(Metadata.Key.of("X-Authorization", Metadata.ASCII_STRING_MARSHALLER), token);
                }
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }

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
