package com.example.github.service;


import com.example.github.controller.dto.GithubRepoDTO;

import java.util.List;

public interface IGithubRepoService {

    List<GithubRepoDTO> getUserRepositories(String username);
}
