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
package io.orkes.conductor.client.http.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.orkes.conductor.client.http.ApiCallback;
import io.orkes.conductor.client.http.ApiException;

public class AsyncApiCallback<T> implements ApiCallback<T> {

    private final CompletableFuture<T> future;

    public AsyncApiCallback(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public void onFailure(
            ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
        future.completeExceptionally(e);
    }

    @Override
    public void onSuccess(T result, int statusCode, Map<String, List<String>> responseHeaders) {
        future.complete(result);
    }

    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
        // do nothing - not supported
    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
        // do nothing - not supported
    }
}
