package io.orkes.conductor.client.integration.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterType;
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterRequest;
import io.orkes.conductor.client.SecretsManager;

public class AwsSecretsManager implements SecretsManager {

    private final AWSSimpleSystemsManagement client;

    public AwsSecretsManager(AWSCredentialsProvider credentialsProvider, String region) {
        this.client = createClient(credentialsProvider, region);
    }

    public AwsSecretsManager(AWSCredentialsProvider credentialsProvider) {
        this.client = createClient(credentialsProvider, getRegion());
    }

    @Override
    public String getSecret(String key) {
        GetParameterRequest request = new GetParameterRequest()
                .withName(key)
                .withWithDecryption(true);
        return client.getParameter(request).getParameter().getValue();
    }

    @Override
    public void storeSecret(String key, String secret) {
        PutParameterRequest request = new PutParameterRequest()
                .withName(key)
                .withType(ParameterType.SecureString)
                .withValue(secret)
                .withOverwrite(true);
        client.putParameter(request);
    }

    private AWSSimpleSystemsManagement createClient(AWSCredentialsProvider credentialsProvider, String region) {
        RetryPolicy retryPolicy = new RetryPolicy(
                PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY,
                3,
                false);

        AWSSimpleSystemsManagementClientBuilder builder = AWSSimpleSystemsManagementClientBuilder.standard()
                .withClientConfiguration(new ClientConfiguration().withRetryPolicy(retryPolicy))
                .withRegion(region)
                .withCredentials(credentialsProvider);

        return builder.build();
    }

    private String getRegion() {
        String region = System.getenv("aws.region");

        if(region == null) {
            region = System.getProperty("aws.region");
        }

        if(region == null) {
            region = System.getenv("AWS_REGION");
        }

        if(region == null) {
            region = System.getProperty("AWS_REGION");
        }

        return region;
    }
}
