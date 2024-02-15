package com.example.github.controller;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.service.GithubRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubRepoController {
    private final GithubRepoService githubRepoService;


    @GetMapping(value = Routes.ROOT)
    public ResponseEntity<List<GithubRepoDTO>> getRepoByGithubUsername(@PathVariable String username) {
        List<GithubRepoDTO> userRepositories = githubRepoService.getUserRepositories(username);
        return ResponseEntity.ok(userRepositories);
    }

    private static final class Routes {
        static final String ROOT = "api/v1/repository/{username}";
    }
}
