package com.example.github.configuration.http;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public interface HttpService {
    <T> List<T> get(String url, ParameterizedTypeReference<List<T>> responseType);
}
