package io.orkes.conductor.client.http.auth;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Objects;

public class AuthorizationClientFilter extends ClientFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationClientFilter.class);

    private static final String AUTHORIZATION_HEADER = "X-AUTHORIZATION";
    private static final String API_TOKEN_PATH = "/token";
    private static final Object LOCK = new Object();

    private final String rootUri;
    private final Client client;
    private final ObjectMapper objectMapper;
    private final String keyId;
    private final String secret;
    private final TokenHolder tokenHolder;

    public AuthorizationClientFilter(String rootUri, String keyId, String secret) {
        this.rootUri = Objects.requireNonNull(rootUri).replaceAll("/$", "");
        this.keyId = Objects.requireNonNull(keyId);
        this.secret = Objects.requireNonNull(secret);
        this.objectMapper = new ObjectMapperProvider().getObjectMapper();
        this.tokenHolder = new TokenHolder();
        this.client = initClient();
    }

    private Client initClient() {
        ClientConfig config = new DefaultClientConfig();
        // https://github.com/FasterXML/jackson-databind/issues/2683
        if (isNewerJacksonVersion()) {
            objectMapper.registerModule(new JavaTimeModule());
        }

        JacksonJsonProvider provider = new JacksonJsonProvider(objectMapper);
        config.getSingletons().add(provider);
        return Client.create(config);
    }

    private boolean isNewerJacksonVersion() {
        Version version = com.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
        return version.getMajorVersion() == 2 && version.getMinorVersion() >= 12;
    }

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
        //TODO if response is 401 try to get a token again
        try {
            request.getHeaders().add(AUTHORIZATION_HEADER, getToken());
            return getNext().handle(request);
        } catch (ClientHandlerException e) {
            LOGGER.error("Error adding authorization header to request", e);
            throw e;
        }
    }

    private String getToken() {
        String token = tokenHolder.getToken();
        if (token == null) {
            synchronized (LOCK) {
                token = tokenHolder.getToken();
                if (token == null) {
                    token = postForToken();
                    tokenHolder.setToken(token);
                }
            }
        }

        return token;
    }

    //TODO error handling
    private String postForToken() {
        URI uri = UriBuilder.fromPath(rootUri + API_TOKEN_PATH).build();
        TokenResponse post = client.resource(uri)
                .type(MediaType.APPLICATION_JSON)
                .entity(TokenRequest.builder()
                        .withKeyId(keyId)
                        .withKeySecret(secret)
                        .build())
                .accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
                .post(TokenResponse.class);
        LOGGER.info("Successfully generated a token");
        return post.getToken();
    }
}
