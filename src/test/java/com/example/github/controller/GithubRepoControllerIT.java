package com.example.github.controller;

import com.example.configuration.RestAssuredIntegrationTestBase;
import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.controller.support.GithubRepoTestSupport;
import com.example.github.controller.support.WiremockTestSupport;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class GithubRepoControllerIT extends RestAssuredIntegrationTestBase
        implements GithubRepoTestSupport, WiremockTestSupport {

    @Test
    void testGetRepositoriesByUsername() {
        // given
        String testUsername = "MateuszMechula";
        stubForRepos(wireMockServer, testUsername);
        stubForBranches(wireMockServer);
        // then
        List<GithubRepoDTO> repositoriesByUsername = getRepositoriesByUsername(testUsername);
        // verify
        List<LoggedRequest> notMatched = wireMockServer.findAllUnmatchedRequests();
        notMatched.forEach(request -> System.out.println("Not matched request: " + request.getUrl()));
        assertThat(repositoriesByUsername, hasSize(4));
    }
}
