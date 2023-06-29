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



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import com.netflix.conductor.sdk.workflow.executor.task.AnnotatedWorkerExecutor;
import com.netflix.conductor.sdk.workflow.executor.task.WorkerConfiguration;

import io.orkes.conductor.client.*;

import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class OrkesConductorClientAutoConfiguration {

    @Bean
    public TaskClient taskClient(OrkesClients clients) {
        return clients.getTaskClient();
    }

    @Bean
    public MetadataClient metadataClient(OrkesClients clients) {
        return clients.getMetadataClient();
    }

    @Bean
    public WorkflowClient workflowClient(OrkesClients clients) {
        return clients.getWorkflowClient();
    }

    @Bean
    public AuthorizationClient authorizationClient(OrkesClients clients) {
        return clients.getAuthorizationClient();
    }

    @Bean
    public EventClient eventClient(OrkesClients clients) {
        return clients.getEventClient();
    }

    @Bean
    public SchedulerClient schedulerClient(OrkesClients clients) {
        return clients.getSchedulerClient();
    }

    @Bean
    public SecretClient secretClient(OrkesClients clients) {
        return clients.getSecretClient();
    }

    @Bean
    public OrkesClients orkesClients(ApiClient apiClient) {
        OrkesClients clients = new OrkesClients(apiClient);
        return clients;
    }

    @Bean
    public WorkflowExecutor workflowExecutor(ApiClient apiClient, AnnotatedWorkerExecutor annotatedWorkerExecutor) {
        OrkesClients clients = new OrkesClients(apiClient);
        return new WorkflowExecutor(
                clients.getTaskClient(),
                clients.getWorkflowClient(),
                clients.getMetadataClient(),
                annotatedWorkerExecutor
                );
    }

    @Bean
    public AnnotatedWorkerExecutor annotatedWorkerExecutor(
            TaskClient taskClient, WorkerConfiguration workerConfiguration) {
        return new OrkesAnnotatedWorkerExecutor(taskClient, workerConfiguration);
    }
}
