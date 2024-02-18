package com.example.util;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.model.Branch;
import com.example.github.model.Commit;

import java.util.List;

public class TestDataFactory {

    public static GithubRepoDTO testDataGithubRepoDTO() {
        Branch branch1 = new Branch("branch1", new Commit("sha"));
        Branch branch2 = new Branch("branch2", new Commit("sha"));
        Branch branch3 = new Branch("branch3", new Commit("sha"));
        return new GithubRepoDTO("repo", "login", List.of(branch1, branch2, branch3));
    }

    public static GithubRepoDTO testDataGithubRepoDTO2() {
        Branch branch1 = new Branch("branch1", new Commit("sha"));
        Branch branch2 = new Branch("branch2", new Commit("sha"));
        Branch branch3 = new Branch("branch3", new Commit("sha"));
        return new GithubRepoDTO("repo", "login", List.of(branch1, branch2, branch3));
    }

    public static GithubRepoDTO testDataGithubRepoDTO3() {
        Branch branch1 = new Branch("branch1", new Commit("sha"));
        Branch branch2 = new Branch("branch2", new Commit("sha"));
        Branch branch3 = new Branch("branch3", new Commit("sha"));
        return new GithubRepoDTO("repo", "login", List.of(branch1, branch2, branch3));
    }
}
