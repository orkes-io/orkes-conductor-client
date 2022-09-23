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
package io.orkes.conductor.client.util.secrets.manager;

import java.time.Duration;
import java.util.Objects;

import org.testcontainers.containers.Network;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;

public class AWSContainer extends LocalStackContainer {
    private static final String NETWORK_ALIAS = "localstack";
    private static final String DOCKER_IMAGE_NAME = "localstack/localstack:0.14.2";

    private final LocalStackContainer.Service[] services;

    public AWSContainer(Network network, LocalStackContainer.Service... services) {
        super(DockerImageName.parse(DOCKER_IMAGE_NAME));
        this.services = services;
        if (Objects.nonNull(network)) {
            withNetwork(network);
            withNetworkAliases(NETWORK_ALIAS);
        }
        withServices(services);
        waitingFor(Wait.forLogMessage(".*Ready\\.\n", 1).withStartupTimeout(Duration.ofMinutes(3)));
    }

    public void start() {
        super.start();
        setProperties(services);
    }

    public AWSSimpleSystemsManagement getAWSSimpleSystemsManagement() {
        return AWSSimpleSystemsManagementClientBuilder.standard()
                .withEndpointConfiguration(
                        getEndpointConfiguration(LocalStackContainer.Service.SSM))
                .withCredentials(getDefaultCredentialsProvider())
                .build();
    }

    void setProperties(LocalStackContainer.Service... services) {
        System.setProperty("aws.region", getRegion());
        System.setProperty("aws.accessKeyId", getAccessKey());
        System.setProperty("aws.secretAccessKey", getSecretKey());
        for (LocalStackContainer.Service service : services) {
            setServiceProperty(service);
        }
    }

    void setServiceProperty(LocalStackContainer.Service service) {
        String propertyKey = String.format("aws.%s.endpoint", service.getName());
        String propertyValue = this.getEndpointOverride(service).toString();
        System.setProperty(propertyKey, propertyValue);
    }
}
