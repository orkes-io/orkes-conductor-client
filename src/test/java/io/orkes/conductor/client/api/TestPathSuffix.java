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
package io.orkes.conductor.client.api;

import org.junit.jupiter.api.Test;

import io.orkes.conductor.client.http.clients.OrkesHttpClient;

import static org.junit.jupiter.api.Assertions.*;

public class TestPathSuffix {

    @Test
    public void test() {
        OrkesHttpClient apiClient = new OrkesHttpClient("https://play.orkes.io/api");
        OrkesHttpClient apiClient2 = new OrkesHttpClient("https://play.orkes.io/api/");

        assertEquals(apiClient2.getBasePath(), apiClient.getBasePath());
    }
}
