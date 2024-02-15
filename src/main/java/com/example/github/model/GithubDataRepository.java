package com.example.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubDataRepository {

    @JsonProperty("name")
    private String repositoryName;
    private Owner owner;
    @JsonProperty("branches_url")
    private String branchesUrl;
}
