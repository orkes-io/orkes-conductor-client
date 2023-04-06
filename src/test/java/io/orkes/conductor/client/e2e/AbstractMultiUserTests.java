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
package io.orkes.conductor.client.e2e;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.netflix.conductor.common.config.ObjectMapperProvider;

import io.orkes.conductor.client.*;
import io.orkes.conductor.client.http.*;
import io.orkes.conductor.client.model.*;
import io.orkes.conductor.client.util.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static io.orkes.conductor.client.util.ApiUtil.*;

@Slf4j
public abstract class AbstractMultiUserTests {

    protected static AuthorizationClient authorizationClient;
    protected static String applicationId;

    protected static MetadataClient metadataClient;

    protected static ApiClient apiUser1Client;

    protected static ApiClient apiUser2Client;

    protected static OkHttpClient client = new OkHttpClient();

    protected static ObjectMapper objectMapper = new ObjectMapperProvider().getObjectMapper();

    protected static String user1, user2;

    protected static String user1AppId, user2AppId;

    @BeforeAll
    public static void setup() {
        authorizationClient = ApiUtil.getOrkesClient().getAuthorizationClient();
        apiUser1Client = ApiUtil.getUser1Client();
        apiUser2Client = ApiUtil.getUser2Client();
        apiUser1Client.setReadTimeout(10_000);
        apiUser2Client.setReadTimeout(10_000);

        CreateOrUpdateApplicationRequest request = new CreateOrUpdateApplicationRequest();
        request.setName("test-" + UUID.randomUUID().toString());
        ConductorApplication app = authorizationClient.createApplication(request);
        applicationId = app.getId();

        user1 = getUserId(apiUser1Client.getToken());
        user2 = getUserId(apiUser2Client.getToken());
        log.info("user1 {}", user1);
        log.info("user2 {}", user2);

        user1AppId = getEnv(USER1_APP_ID);
        user2AppId = getEnv(USER2_APP_ID);

        log.info("user1AppId {}", user1AppId);
        log.info("user2AppId {}", user2AppId);

    }
    @AfterAll
    public static void cleanup() {
        if(applicationId != null) {
            authorizationClient.deleteApplication(applicationId);
        }
    }

    @SneakyThrows
    protected static String getUserId(String token) {
        Request request = new Request.Builder().url(ApiUtil.getBasePath() + "/token/userInfo")
                .addHeader("X-Authorization", token)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        byte[] data = response.body().bytes();
        Map<String, Object> userInfo = objectMapper.readValue(data, Map.class);
        return userInfo.get("id").toString();
    }
}
