package com.example.github.service;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.model.Branch;
import com.example.github.model.GithubDataRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubRepoService {
    private final WebClient githubWebClient;

    public List<GithubRepoDTO> getUserRepositories(String username) {
        List<GithubDataRepository> repositories = githubWebClient.get()
                .uri(Routes.GET_REPOSITORIES, username)
                .retrieve()
                .bodyToFlux(GithubDataRepository.class)
                .collectList()
                .block();

        if (repositories != null) {
            return transformRepositoriesToDto(repositories);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Branch> getBranches(String branchesUrl) {
        return githubWebClient.get()
                .uri(branchesUrl.replace("{/branch}", ""))
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .block();
    }

    private List<GithubRepoDTO> transformRepositoriesToDto(List<GithubDataRepository> repositories) {
        return repositories.stream().map(repo -> {
            String branchesUrl = repo.getBranchesUrl();
            List<Branch> branches = branchesUrl != null ? getBranches(branchesUrl) : new ArrayList<>();

            return GithubRepoDTO.builder()
                    .name(repo.getRepositoryName())
                    .login(repo.getOwner().getOwnerLogin())
                    .branches(branches)
                    .build();
        }).toList();
    }

    private static final class Routes {
        static final String GET_REPOSITORIES = "https://api.github.com/users/{username}/repos";
    }

    @Data
    static class ExceptionResponse {
        private String status;
        private String message;
    }
}
