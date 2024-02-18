package com.example.github.controller;

import com.example.github.controller.dto.GithubRepoDTO;
import com.example.github.service.IGithubRepoService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.github.controller.GithubRepoController.Routes.GET_REPOSITORIES_BY_USERNAME;
import static com.example.util.TestDataFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubRepoController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(MockitoExtension.class)
class GithubRepoControllerWebMvcTest {

    private MockMvc mockMvc;
    @MockBean
    private IGithubRepoService githubRepoService;

    @Test
    void getRepoByGithubUsernameSuccessfully() throws Exception {
        // given
        String username = "test";
        List<GithubRepoDTO> githubRepoDTOList = List.of(testDataGithubRepoDTO(), testDataGithubRepoDTO2(),
                testDataGithubRepoDTO3());
        when(githubRepoService.getUserRepositories(username)).thenReturn(githubRepoDTOList);
        // when, then
        mockMvc.perform(get(GET_REPOSITORIES_BY_USERNAME, username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].repositoryName", is("repo")))
                .andExpect(jsonPath("$[0].ownerLogin", is("login")))
                .andExpect(jsonPath("$[0].branches[*].name",
                        containsInAnyOrder("branch1", "branch2", "branch3")));
    }
}