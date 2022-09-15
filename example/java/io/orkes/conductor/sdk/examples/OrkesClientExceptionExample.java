package io.orkes.conductor.sdk.examples;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.OrkesClientException;
import io.orkes.conductor.client.OrkesClients;

/**
 * Example demonstrating how to catch client exception and extract the underlying status code and message
 */
public class OrkesClientExceptionExample {

    public static void main(String a[]) {

        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        MetadataClient metadataClient = orkesClients.getMetadataClient();
        try {
            metadataClient.getTags();
        } catch (OrkesClientException e) {
            System.out.println("Status " + e.getCode() + " Message " + e.getMessage());
        }

    }
}
