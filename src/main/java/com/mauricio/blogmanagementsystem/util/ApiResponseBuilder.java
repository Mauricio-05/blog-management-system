package com.mauricio.blogmanagementsystem.util;

import com.mauricio.blogmanagementsystem.dto.response.ApiResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiResponseBuilder {

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(int status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .build();
    }
}

