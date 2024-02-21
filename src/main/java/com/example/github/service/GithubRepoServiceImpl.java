package com.example.github.service;

import com.example.github.configuration.http.HttpService;
import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.model.Branch;
import com.example.github.model.GithubDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubRepoServiceImpl implements IGithubRepoService {
    @Value("${api.github.url}")
    private String githubApiUrl;
    private final HttpService httpService;

    public List<GithubRepoDTO> getUserRepositories(String username) {
        log.info("Retrieving repositories for user: {}", username);

        List<GithubDataRepository> repositories = httpService.get(githubApiUrl + "users/%s/repos".formatted(username),
                new ParameterizedTypeReference<>() {
                });
        return transformRepositoriesToDto(Objects.requireNonNull(repositories));
    }

    private List<Branch> getBranches(String branchesUrl) {
        String finalUrl = branchesUrl
                .replace("https://api.github.com/", githubApiUrl)
                .replace("{/branch}", "");

        log.info("Final URL for branches request: {}", finalUrl);
        return httpService.get(finalUrl, new ParameterizedTypeReference<>() {
        });
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
