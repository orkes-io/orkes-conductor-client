package io.orkes.conductor.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import groovy.json.JsonBuilder
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

abstract class AbstractIT extends Specification {

    def @Shared
            wireMockServer = new WireMockServer(wireMockConfig().dynamicPort())

    void setupSpec() {
        wireMockServer.start()

    }

    void setup() {
        WireMock.configureFor(port)
    }

    void cleanup() {
        WireMock.reset()
    }

    void cleanupSpec() {
        wireMockServer.stop()
    }

    int getPort() {
        wireMockServer.port()
    }

    static String json(Map map) {
        def json = new JsonBuilder()
        json map
        json.toString()
    }
}
