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
package io.orkes.conductor.client.secrets.manager.aws;

import io.orkes.conductor.client.SecretsManager;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterType;
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterRequest;

public class AwsSecretsManager implements SecretsManager {

    private final AWSSimpleSystemsManagement client;

    public AwsSecretsManager(AWSSimpleSystemsManagement awsSimpleSystemsManagement) {
        this.client = awsSimpleSystemsManagement;
    }

    public AwsSecretsManager(AWSCredentialsProvider credentialsProvider, String region) {
        this.client = createClient(credentialsProvider, region);
    }

    public AwsSecretsManager(AWSCredentialsProvider credentialsProvider) {
        this.client = createClient(credentialsProvider, getRegion());
    }

    @Override
    public String getSecret(String key) {
        GetParameterRequest request =
                new GetParameterRequest().withName(key).withWithDecryption(true);
        return client.getParameter(request).getParameter().getValue();
    }

    @Override
    public void storeSecret(String key, String secret) {
        PutParameterRequest request =
                new PutParameterRequest()
                        .withName(key)
                        .withType(ParameterType.SecureString)
                        .withValue(secret)
                        .withOverwrite(true);
        client.putParameter(request);
    }

    private AWSSimpleSystemsManagement createClient(
            AWSCredentialsProvider credentialsProvider, String region) {
        RetryPolicy retryPolicy =
                new RetryPolicy(
                        PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                        PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY,
                        3,
                        false);
        AWSSimpleSystemsManagementClientBuilder builder =
                AWSSimpleSystemsManagementClientBuilder.standard()
                        .withClientConfiguration(
                                new ClientConfiguration().withRetryPolicy(retryPolicy))
                        .withRegion(region)
                        .withCredentials(credentialsProvider);
        return builder.build();
    }

    private String getRegion() {
        return getProperty("aws.region");
    }
}
