/*
 * Copyright 2023 Orkes, Inc.
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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.common.run.WorkflowSummary;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.sdk.examples.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public class WebhookTests {

    private static ObjectMapper om = new ObjectMapperProvider().getObjectMapper();

    private static String startWorkflowWebhookId;

    private static String receiveWebhookId;

    private static String webhookUrl;

    private static String receiveWebhookUrl;

    private static String webhookHeaderKey = UUID.randomUUID().toString();

    private static String webhookHeaderValue = UUID.randomUUID().toString();

    private static ApiClient client = ApiUtil.getApiClientWithCredentials();;

    private static final String WORKFLOW_NAME = "e2e-webhook-wf";

    private static final OkHttpClient httpClient = new OkHttpClient();

    private String correlationId = UUID.randomUUID().toString();

    @SneakyThrows
    @Test
    public void testWebHook() {
        WorkflowClient workflowClient = new OrkesWorkflowClient(client);
        int count = 64;
        String[] keys = new String[count];
        for (int i = 0; i < count; i++) {
            String key = UUID.randomUUID().toString();
            keys[i] = key;
        }

        sendWebhook(keys, webhookUrl);
        List<String> workflowIds = new ArrayList<>();
        await().pollInterval(1, TimeUnit.SECONDS).atMost(60, TimeUnit.SECONDS).untilAsserted(() ->{
            SearchResult<WorkflowSummary> workflows = workflowClient.search(0, count, "", correlationId, "status = 'RUNNING'");
            assertNotNull(workflows);
            assertNotNull(workflows.getResults());
            assertEquals(count, workflows.getResults().size());
            workflowIds.addAll(workflows.getResults().stream().map(result -> result.getWorkflowId()).collect(Collectors.toList()));
        });
        assertNotNull(workflowIds);
        assertEquals(count, workflowIds.size());

        for (int i = 0; i < count; i++) {
            String key = keys[i];
            Map<String, Object> input = new HashMap<>();
            input.put("event", Map.of("id", key));
            sendWebhook(input, receiveWebhookUrl);
        }

        await().pollInterval(1, TimeUnit.SECONDS).atMost(30, TimeUnit.SECONDS).untilAsserted(() ->{
            for (String wfId : workflowIds) {
                Workflow workflow = workflowClient.getWorkflow(wfId, true);
                assertNotNull(workflow);
                assertEquals(2, workflow.getTasks().size());
                assertEquals(Task.Status.COMPLETED, workflow.getTasks().get(0).getStatus());
                assertEquals(Task.Status.IN_PROGRESS, workflow.getTasks().get(1).getStatus());
                Map<String,Object> event = (Map<String, Object>) workflow.getTasks().get(0).getOutputData().get("event");
                assertEquals(workflow.getInput().get("key"), event.get("id"));
            }
        });

        for (int i = 0; i < count; i++) {
            String key = keys[i];
            Map<String, Object> input = new HashMap<>();
            input.put("key", 12);
            sendWebhook(input, receiveWebhookUrl + "?id=" + key);
        }

        await().pollInterval(1, TimeUnit.SECONDS).atMost(30, TimeUnit.SECONDS).untilAsserted(() ->{
            for (String wfId : workflowIds) {
                Workflow workflow = workflowClient.getWorkflow(wfId, true);
                assertNotNull(workflow);
                assertEquals(Workflow.WorkflowStatus.COMPLETED, workflow.getStatus());
                assertEquals(2, workflow.getTasks().size());
                assertEquals(Task.Status.COMPLETED, workflow.getTasks().get(0).getStatus());
                assertEquals(Task.Status.COMPLETED, workflow.getTasks().get(1).getStatus());
                assertEquals(workflow.getInput().get("key"), workflow.getTasks().get(1).getOutputData().get("id"));
            }
        });

    }

    private void sendWebhook(String[] keys, String url) {
        for (int i = 0; i < keys.length; i++) {
            Map<String, Object> input = new HashMap<>();
            input.put("key", keys[i]);
            input.put("correlationId", correlationId);
            sendWebhook(input, url);
        }
    }

    @SneakyThrows
    private void sendWebhook(Map<String, Object> input, String url) {
        String json = om.writeValueAsString(input);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Request.Builder builder = new Request.Builder().addHeader(webhookHeaderKey, webhookHeaderValue).url(url);
        Request request = builder.post(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @SneakyThrows
    @BeforeAll
    public static void registerWebhook() {

        MetadataClient metadataClient = new OrkesClients(client).getMetadataClient();


        WebhookConfig config = new WebhookConfig();
        config.setName("e2e-webhook-test-start-wf");
        config.setWorkflowsToStart(Map.of(WORKFLOW_NAME, 1));
        config.setHeaders(Map.of(webhookHeaderKey,webhookHeaderValue));
        config.setVerifier(WebhookConfig.Verifier.HEADER_BASED);
        config.setSourcePlatform("Custom");

        WebhookConfig config2 = new WebhookConfig();
        config2.setName("e2e-webhook-test-receive-webhook");
        config2.setReceiverWorkflowNamesToVersions(Map.of(WORKFLOW_NAME, 1));
        config2.setHeaders(Map.of(webhookHeaderKey,webhookHeaderValue));
        config2.setVerifier(WebhookConfig.Verifier.HEADER_BASED);
        config2.setSourcePlatform("Custom");


        WorkflowDef def = new WorkflowDef();
        def.setName(WORKFLOW_NAME);
        def.setVersion(1);
        def.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        def.setTimeoutSeconds(120);
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setType("WAIT_FOR_WEBHOOK");
        workflowTask.setName("wait_for_webhook");
        workflowTask.setTaskReferenceName("wait_for_webhook");
        workflowTask.getInputParameters().put("matches", Map.of("$['event']['id']", "${workflow.input.key}"));

        WorkflowTask workflowTask2 = new WorkflowTask();
        workflowTask2.setType("WAIT_FOR_WEBHOOK");
        workflowTask2.setName("wait_for_webhook2");
        workflowTask2.setTaskReferenceName("wait_for_webhook2");
        workflowTask2.getInputParameters().put("matches", Map.of("$['id']", "${workflow.input.key}"));

        def.getTasks().add(workflowTask);
        def.getTasks().add(workflowTask2);

        metadataClient.updateWorkflowDefs(List.of(def));

        startWorkflowWebhookId = registerWebHook(config);
        webhookUrl = client.getBasePath().replaceFirst("api","webhook") + "/" + startWorkflowWebhookId;

        receiveWebhookId = registerWebHook(config2);
        receiveWebhookUrl = client.getBasePath().replaceFirst("api","webhook") + "/" + receiveWebhookId;

        log.info("webhookUrl URL {}", webhookUrl);
        log.info("receiveWebhookUrl URL {}", receiveWebhookUrl);

    }

    @SneakyThrows
    private static String registerWebHook(WebhookConfig config) {
        String url = client.getBasePath() + "/metadata/webhook";
        String json = om.writeValueAsString(config);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Request.Builder builder = new Request.Builder().url(url);
        Request request = builder.post(requestBody).addHeader("X-Authorization", client.getToken()).build();
        Response response = httpClient.newCall(request).execute();
        assertEquals(200, response.code());
        byte[] responseBytes = response.body().bytes();
        config = om.readValue(responseBytes, WebhookConfig.class);
        return config.getId();
    }

    @SneakyThrows
    @AfterAll
    public static void cleanUp() {
        if(startWorkflowWebhookId != null) {
            String url = client.getBasePath() + "/metadata/webhook/" + startWorkflowWebhookId;
            OkHttpClient httpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url);
            Request request = builder.delete().addHeader("X-Authorization", client.getToken()).build();
            httpClient.newCall(request).execute();

        }

        if(receiveWebhookUrl != null) {
            String url = client.getBasePath() + "/metadata/webhook/" + receiveWebhookUrl;
            OkHttpClient httpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url);
            Request request = builder.delete().addHeader("X-Authorization", client.getToken()).build();
            httpClient.newCall(request).execute();

        }
    }
}
