package com.example.github.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
    Integer status;
    String message;
}
