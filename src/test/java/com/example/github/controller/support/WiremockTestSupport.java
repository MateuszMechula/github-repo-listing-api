package com.example.github.controller.support;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WiremockTestSupport {

    default void stubForRepos(final WireMockServer wireMockServer, String username) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/users/%s/repos".formatted(username)))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/response_owner_repos.json")));
    }

    default void stubForBranches(final WireMockServer wireMockServer) {
        List<String> repositoryNames = List.of("cottage-connect", "foodflow", "kalkulatorKredytuHipotecznego",
                "MateuszMechula");
        for (String repo : repositoryNames) {
            wireMockServer.stubFor(
                    get(urlPathEqualTo(String.format("/repos/MateuszMechula/%s/branches", repo)))
                            .willReturn(aResponse()
                                    .withHeader("Content-Type", "application/json")
                                    .withBodyFile("wiremock/response_branches.json")));
        }
    }
}
