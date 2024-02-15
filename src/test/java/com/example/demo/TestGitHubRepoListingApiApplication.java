package com.example.demo;

import com.example.GitHubRepoListingApiApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestGitHubRepoListingApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(GitHubRepoListingApiApplication::main).with(TestGitHubRepoListingApiApplication.class).run(args);
    }

}
