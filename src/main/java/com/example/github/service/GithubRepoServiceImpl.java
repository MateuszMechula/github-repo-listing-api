package com.example.github.service;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.model.Branch;
import com.example.github.model.GithubDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubRepoServiceImpl implements IGithubRepoService {
    private final WebClient githubWebClient;
    @Value("${api.github.url}")
    private String githubApiUrl;

    public List<GithubRepoDTO> getUserRepositories(String username) {
        log.info("Retrieving repositories for user: {}", username);
        List<GithubDataRepository> repositories = githubWebClient.get()
                .uri(githubApiUrl + "users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(GithubDataRepository.class)
                .collectList()
                .block();

        log.info("Retrieved {} repositories for user {}", repositories != null ? repositories.size() : 0, username);

        if (repositories != null) {
            return transformRepositoriesToDto(repositories);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Branch> getBranches(String branchesUrl) {
        String finalUrl = branchesUrl.replace("{/branch}", "");
        log.info("Final URL for branches request: {}", finalUrl);
        List<Branch> branches = githubWebClient.get()
                .uri(finalUrl)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .block();
        log.info("Retrieved {} branches", branches != null ? branches.size() : 0);
        return branches;
    }

    private List<GithubRepoDTO> transformRepositoriesToDto(List<GithubDataRepository> repositories) {
        log.info("Transforming {} repositories into DTOs", repositories.size());
        return repositories.stream().map(repo -> {
            String branchesUrl = repo.branchesUrl();
            List<Branch> branches = branchesUrl != null ? getBranches(branchesUrl) : new ArrayList<>();

            return new GithubRepoDTO(repo.repositoryName(), repo.owner().ownerLogin(), branches);
        }).toList();
    }
}
