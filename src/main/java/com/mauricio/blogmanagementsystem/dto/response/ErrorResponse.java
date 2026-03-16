package com.mauricio.blogmanagementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mauricio.blogmanagementsystem.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;

    private ErrorCode error;

    private String message;

    private String path;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private Map<String, String> fieldErrors;

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .error(errorCode)
                .message(message)
                .path(path)
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String path, Map<String, String> fieldErrors) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .error(errorCode)
                .message(message)
                .path(path)
                .fieldErrors(fieldErrors)
                .build();
    }
}

