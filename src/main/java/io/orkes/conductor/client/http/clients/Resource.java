/*
 * Copyright 2024 Orkes, Inc.
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
package io.orkes.conductor.client.http.clients;

import io.orkes.conductor.client.http.ApiException;

abstract class Resource {

    protected final OrkesHttpClient httpClient;

    protected Resource(OrkesHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected void validateNonNull(String param, Object object) {
        validateNonNull(new String[]{param}, object);
    }

    protected void validateNonNull(String[] paramNames, Object... params) throws ApiException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                throw new ApiException("Missing required parameter '" + paramNames[i] + "'.");
            }
        }
    }
}
