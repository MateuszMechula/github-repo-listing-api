package com.example.github.configuration.http;

import com.example.github.util.exception.ClientErrorException;
import com.example.github.util.exception.InternalServerException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@AllArgsConstructor
public class RestClientAdapter implements HttpService {
    private final RestClient restClient;

    @Override
    public <T> List<T> get(String url, ParameterizedTypeReference<List<T>> responseType) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ClientErrorException("Username doesn't exists");
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new InternalServerException("Internal server error occurred");
                }))
                .body(responseType);
    }
}
