package com.example.github.controller.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static com.example.github.controller.GithubRepoController.Routes.GET_REPOSITORIES_BY_USERNAME;

public interface GithubRepoTestSupport {

    RequestSpecification requestSpecification();

    default String getRepositoriesByUsername(String username) {
        return requestSpecification()
                .given()
                .contentType("application/json")
                .pathParam("username", username)
                .when()
                .get(GET_REPOSITORIES_BY_USERNAME)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .body()
                .asString();
    }
}
