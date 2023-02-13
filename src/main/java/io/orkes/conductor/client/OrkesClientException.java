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
package io.orkes.conductor.client;

import com.netflix.conductor.client.exception.ConductorClientException;

public abstract class OrkesClientException extends ConductorClientException {

    public OrkesClientException() {}

    public OrkesClientException(String message) {
        super(message);
    }

    public OrkesClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrkesClientException(int status, String message) {
        super(status, message);
    }

    public abstract boolean isClientError();
}
