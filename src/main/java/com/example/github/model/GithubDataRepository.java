package com.example.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubDataRepository(@JsonProperty("name") String repositoryName, Owner owner,
                                   @JsonProperty("branches_url") String branchesUrl) {
}
