package com.example.github.controller;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.service.IGithubRepoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.github.controller.GithubRepoController.Routes.GET_REPOSITORIES_BY_USERNAME;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Github", description = "Endpoint responsible for getting repositories by username")
public class GithubRepoController {

    private final IGithubRepoService githubRepoService;

    @Operation(
            summary = "Get repo by username",
            description = "Endpoint to get all repositories with branches")
    @GetMapping(value = GET_REPOSITORIES_BY_USERNAME)
    public ResponseEntity<List<GithubRepoDTO>> getRepoByGithubUsername(@PathVariable String username) {
        log.info("Fetching repositories for user: {}", username);
        List<GithubRepoDTO> userRepositories = githubRepoService.getUserRepositories(username);
        log.info("Found {} repositories for user {}",
                userRepositories != null ? userRepositories.size() : 0, username);

        return ResponseEntity.ok(userRepositories);
    }

    public static final class Routes {
        public static final String GET_REPOSITORIES_BY_USERNAME = "/api/v1/{username}/repositories";
    }
}
