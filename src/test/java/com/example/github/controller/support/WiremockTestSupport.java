package com.example.github.controller.support;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WiremockTestSupport {

    default void stubForRepos(final WireMockServer wireMockServer, String username) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/users/%s/repos".formatted(username)))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_owner_repos.json")));
    }

    default void stubForBranch1(final WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/repos/MateuszMechula/cottage-connect/branches"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_cottage_connect_branch.json")));
    }

    default void stubForBranch2(final WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/repos/MateuszMechula/foodflow/branches"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_foodflow_branch.json")));
    }

    default void stubForBranch3(final WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/repos/MateuszMechula/kalkulatorKredytuHipotecznego/branches"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_kalkulatorKredytuHipotecznego_branch.json")));
    }

    default void stubForBranch4(final WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/repos/MateuszMechula/MateuszMechula/branches"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_MateuszMechula_branch.json")));
    }
}
