package com.example.github.controller.support;

import com.example.github.controller.dto.GithubRepoDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.example.github.controller.GithubRepoController.Routes.GET_REPOSITORIES_BY_USERNAME;

public interface GithubRepoTestSupport {

    RequestSpecification requestSpecification();

    default List<GithubRepoDTO> getRepositoriesByUsername(String username) {
        return requestSpecification()
                .pathParam("username", username)
                .get(GET_REPOSITORIES_BY_USERNAME)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .jsonPath()
                .getList("$", GithubRepoDTO.class);
    }
}
