package com.example.configuration;

import com.example.GitHubRepoListingApiApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ActiveProfiles("test")
@SpringBootTest(
        classes = GitHubRepoListingApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class RestAssuredIntegrationTestBase {
    @LocalServerPort
    protected int serverPort;

    protected static WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(9090)
                        .notifier(new ConsoleNotifier(true))
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

    public RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .port(serverPort);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }
}
