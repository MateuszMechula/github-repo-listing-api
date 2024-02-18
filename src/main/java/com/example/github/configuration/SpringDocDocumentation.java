package com.example.github.configuration;

import com.example.GitHubRepoListingApiApplication;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocDocumentation {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(GitHubRepoListingApiApplication.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("GithubRepoListingApi")
                        .contact(contact())
                        .description("API GithubRepoListing")
                        .version("1.0"));
    }

    private Contact contact() {
        return new Contact()
                .name("GithubRepoListing")
                .url("https://github.com/MateuszMechula/github-repo-listing-api")
                .email("mateuszmechula@gmail.com");
    }
}
