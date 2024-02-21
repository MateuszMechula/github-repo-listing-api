package com.example.github.controller;

import com.example.configuration.RestAssuredIntegrationTestBase;
import com.example.github.controller.support.GithubRepoTestSupport;
import com.example.github.controller.support.WiremockTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GithubRepoControllerIT extends RestAssuredIntegrationTestBase
        implements GithubRepoTestSupport, WiremockTestSupport {

    @Test
    void testGetRepositoriesByUsername() throws IOException {
        // given
        String testUsername = "MateuszMechula";
        stubForRepos(wireMockServer, testUsername);
        stubForBranch1(wireMockServer);
        stubForBranch2(wireMockServer);
        stubForBranch3(wireMockServer);
        stubForBranch4(wireMockServer);

        String expectedContent = new String(Files.readAllBytes(Paths.get
                ("src/test/resources/__files/wiremock/response_github_api.json")));
        // then
        String repositoriesByUsername = getRepositoriesByUsername(testUsername);
        // verify
        assertEquals(repositoriesByUsername, expectedContent);
    }
}
