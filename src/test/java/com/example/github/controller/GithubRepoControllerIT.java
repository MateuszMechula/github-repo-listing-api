package com.example.github.controller;

import com.example.configuration.RestAssuredIntegrationTestBase;
import com.example.github.controller.support.GithubRepoTestSupport;
import com.example.github.controller.support.WiremockTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class GithubRepoControllerIT extends RestAssuredIntegrationTestBase
        implements GithubRepoTestSupport, WiremockTestSupport {

    @Test
    void testGetRepositoriesByUsername() throws IOException {
        // given
        String testUsername = "MateuszMechula";
        stubForRepos(wireMockServer, testUsername);
        stubForBranches(wireMockServer);

        String expectedContent = new String(Files.readAllBytes(Paths.get
                ("src/test/resources/__files/test_files/github_api_response.json")));

        // then
        String repositoriesByUsername = getRepositoriesByUsername(testUsername);
        // verify
        Assertions.assertEquals(expectedContent, repositoriesByUsername);
    }
}
