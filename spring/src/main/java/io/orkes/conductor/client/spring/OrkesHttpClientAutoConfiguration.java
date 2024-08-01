/*
 * Copyright 2020 Orkes, Inc.
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
package io.orkes.conductor.client.spring;


import io.orkes.conductor.client.http.clients.OrkesHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class OrkesHttpClientAutoConfiguration {

    //TODO add more properties e.g.: ssl off, timeout settings, etc. and these should be client properties!!!
    public static final String CONDUCTOR_SERVER_URL = "conductor.server.url";
    public static final String CONDUCTOR_CLIENT_KEY_ID = "conductor.security.client.key-id";
    public static final String CONDUCTOR_CLIENT_SECRET = "conductor.security.client.secret";

    @Bean
    public OrkesHttpClient getApiClient(Environment env) {
        String basePath = env.getProperty(CONDUCTOR_SERVER_URL);
        String keyId = env.getProperty(CONDUCTOR_CLIENT_KEY_ID);
        String secret = env.getProperty(CONDUCTOR_CLIENT_SECRET);

        return OrkesHttpClient.builder()
                .basePath(basePath)
                .keyId(keyId)
                .keySecret(secret)
                .build();
    }
}
