package io.orkes.conductor.client.http.api;

import io.orkes.conductor.client.http.ApiCallback;
import io.orkes.conductor.client.http.ApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncApiCallback<T> implements ApiCallback<T> {

    private final CompletableFuture<T> future;

    public AsyncApiCallback(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
        future.completeExceptionally(e);
    }

    @Override
    public void onSuccess(T result, int statusCode, Map<String, List<String>> responseHeaders) {
        future.complete(result);
    }

    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
        //do nothing - not supported
    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
        //do nothing - not supported
    }
}
