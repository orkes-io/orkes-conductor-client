package io.orkes.conductor.client.http.auth

import io.orkes.conductor.client.AbstractIT
import io.orkes.conductor.client.http.OrkesMetadataClient

import java.util.concurrent.CountDownLatch

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

class AuthorizationClientFilterIT extends AbstractIT {

    def keyId = "80caf922-cf9c-4034-ba95-2a9ef526c4bc"
    def secret = "DaS3Cr3T"

    def "it shouldn't generate a token before a request if credentials are not set"() {
        given:
            // We use OrkesMetadataClient because it uses AuthorizationClientFilter as all other clients
            def metadataClient = new OrkesMetadataClient()
            metadataClient.setRootURI("http://localhost:${port}/api/")

            stubFor get("/api/metadata/taskdefs/task0")
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withBody(json([:])))
        when:
            metadataClient.getTaskDef("task0")

        then:
            verify(0, postRequestedFor(urlEqualTo("/api/token")))
            verify(1, getRequestedFor(urlEqualTo("/api/metadata/taskdefs/task0")))
    }

    def "it should generate a token before a request if credentials are set"() {
        given:
            def metadataClient = new OrkesMetadataClient()
            metadataClient.setRootURI("http://localhost:${port}/api/")
            metadataClient.withCredentials(keyId, secret)

            stubFor post("/api/token")
                    .withRequestBody(equalToJson(json(keyId: keyId, keySecret: secret)))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withBody(json(token: "TEST_TOKEN")))

            stubFor get("/api/metadata/taskdefs/task0")
                    .withHeader("X-Authorization", equalTo("TEST_TOKEN"))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withBody(json([:])))
        when:
            metadataClient.getTaskDef("task0")

        then:
            verify(2, postRequestedFor(urlEqualTo("/api/token")))
            verify(1, getRequestedFor(urlEqualTo("/api/metadata/taskdefs/task0")))
    }


    def "it should generate token only once"() {
        given:
            def metadataClient = new OrkesMetadataClient()
            metadataClient.setRootURI("http://localhost:${port}/api/")
            metadataClient.withCredentials(keyId, secret)

            stubFor post("/api/token")
                    .withRequestBody(equalToJson(json(keyId: keyId, keySecret: secret)))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withBody(json(token: "TEST_TOKEN")))

            stubFor get("/api/metadata/taskdefs/task0")
                    .withHeader("X-Authorization", equalTo("TEST_TOKEN"))
                    .willReturn(ok()
                            .withHeader("Content-Type", "application/json")
                            .withBody(json([:])))

        when:
            def threads = 10
            def latch = new CountDownLatch(threads)
            threads.times {
                Thread.start {
                    latch.countDown()
                    metadataClient.getTaskDef("task0")
                }
            }

        then:
            await().atMost(5, SECONDS).until {
                verify(2, postRequestedFor(urlEqualTo("/api/token")))
                verify(threads, getRequestedFor(urlEqualTo("/api/metadata/taskdefs/task0")))
                true
            }
    }
}