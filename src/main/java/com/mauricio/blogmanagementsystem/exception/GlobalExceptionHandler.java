package com.mauricio.blogmanagementsystem.exception;

import com.mauricio.blogmanagementsystem.dto.response.ErrorResponse;
import com.mauricio.blogmanagementsystem.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Recurso no encontrado: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(ErrorCode.NOT_FOUND.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        log.warn("Credenciales inválidas en {} - Mensaje: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED, "Credenciales inválidas", request.getRequestURI());
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Argumento inválido en {} - Mensaje: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.CONFLICT, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(ErrorCode.CONFLICT.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Acceso denegado en {} - Mensaje: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.FORBIDDEN, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        log.warn("Autorización denegada en {} - Mensaje: {}", request.getRequestURI(), ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.FORBIDDEN, "No tienes permisos para realizar esta acción", request.getRequestURI());
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.warn("Error de validación en {} - Campos: {}", request.getRequestURI(), fieldErrors);

        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.VALIDATION_ERROR,
                "Error de validación en los campos enviados",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado en {} - Excepción: {}", request.getRequestURI(), ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado",
                request.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
    }
}
