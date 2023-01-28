package io.orkes.conductor.client.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.SearchResult;
import com.netflix.conductor.common.run.WorkflowSummary;
import com.squareup.okhttp.*;
import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import io.orkes.conductor.sdk.examples.ApiUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @SneakyThrows
    @Test
    public void testWebHookCreation() {
        String key = UUID.randomUUID().toString();
        WorkflowClient workflowClient = new OrkesWorkflowClient(client);

        OkHttpClient httpClient = new OkHttpClient();
        Map<String, Object> input = new HashMap<>();
        input.put("key", key);

        sendWebhook(input, webhookUrl);
        AtomicReference<String> workflowId = new AtomicReference<>(null);
        await().pollInterval(1, TimeUnit.SECONDS).atMost(30, TimeUnit.SECONDS).untilAsserted(() ->{
            SearchResult<WorkflowSummary> workflows = workflowClient.search(0, 10, "", key, "status = 'RUNNING'");
            assertNotNull(workflows);
            assertNotNull(workflows.getResults());
            assertEquals(1, workflows.getResults().size());
            String wfId = workflows.getResults().get(0).getWorkflowId();
            log.info("Found {}", wfId);
            workflowId.set(wfId);
        });

        String wfId = workflowId.get();
        assertNotNull(wfId);
        input.put("event", Map.of("id", key));
        sendWebhook(input, receiveWebhookUrl);

        await().pollInterval(1, TimeUnit.SECONDS).atMost(30, TimeUnit.SECONDS).untilAsserted(() ->{
            SearchResult<WorkflowSummary> workflows = workflowClient.search(0, 10, "", key, "status = 'COMPLETED'");
            assertNotNull(workflows);
            assertNotNull(workflows.getResults());
            assertEquals(1, workflows.getResults().size());
            log.info("Found {}", workflows.getResults().get(0).getStatus());
        });
    }

    @SneakyThrows
    private void sendWebhook( Map<String, Object> input, String url) {
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
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setType("WAIT_FOR_WEBHOOK");
        workflowTask.setName("wait_for_webhook");
        workflowTask.setTaskReferenceName("wait_for_webhook");
        workflowTask.getInputParameters().put("matches", Map.of("$['event']['id']", "${workflow.input.key}"));
        def.getTasks().add(workflowTask);
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
