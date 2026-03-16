package com.mauricio.blogmanagementsystem.controller;

import com.mauricio.blogmanagementsystem.dto.request.LoginRequest;
import com.mauricio.blogmanagementsystem.dto.request.RegisterRequest;
import com.mauricio.blogmanagementsystem.dto.response.ApiResponse;
import com.mauricio.blogmanagementsystem.dto.response.AuthResponse;
import com.mauricio.blogmanagementsystem.service.AuthService;
import com.mauricio.blogmanagementsystem.util.ApiResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(HttpStatus.CREATED.value(), "Usuario registrado exitosamente", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponseBuilder.success(HttpStatus.OK.value(), "Inicio de sesión exitoso", authResponse));
    }
}

