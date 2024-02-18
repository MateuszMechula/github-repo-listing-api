package com.example.github.service;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.model.Branch;
import com.example.github.model.GithubDataRepository;
import com.example.github.util.exception.BranchNotFoundException;
import com.example.github.util.exception.InternalServerException;
import com.example.github.util.exception.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static reactor.core.publisher.Mono.error;

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
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        error(new UsernameNotFoundException("User with username: %s not found".formatted(username))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        error(new InternalServerException("Internal server error occurred while retrieving repositories.")))
                .bodyToFlux(GithubDataRepository.class)
                .collectList()
                .block();

        return transformRepositoriesToDto(Objects.requireNonNull(repositories));
    }

    private List<Branch> getBranches(String branchesUrl) {
        String finalUrl = branchesUrl.replace("{/branch}", "");
        log.info("Final URL for branches request: {}", finalUrl);
        return githubWebClient.get()
                .uri(finalUrl).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response
                        -> error(new BranchNotFoundException("Branches with url: %s not found".formatted(branchesUrl))))
                .onStatus(HttpStatusCode::is5xxServerError, response
                        -> error(new InternalServerException("Internal server error occurred while retrieving branches.")))
                .bodyToFlux(Branch.class)
                .collectList()
                .block();
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
