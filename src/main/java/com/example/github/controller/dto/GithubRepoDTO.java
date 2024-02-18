package com.example.github.controller.dto;

import com.example.github.model.Branch;

import java.util.List;

public record GithubRepoDTO(String repositoryName, String ownerLogin, List<Branch> branches) {
}
