package com.mauricio.blogmanagementsystem.service;

import com.mauricio.blogmanagementsystem.dto.request.LoginRequest;
import com.mauricio.blogmanagementsystem.dto.request.RegisterRequest;
import com.mauricio.blogmanagementsystem.dto.response.AuthResponse;
import com.mauricio.blogmanagementsystem.entity.AppUser;
import com.mauricio.blogmanagementsystem.enums.UserRole;
import com.mauricio.blogmanagementsystem.mapper.AuthMapper;
import com.mauricio.blogmanagementsystem.repository.AppUserRepository;
import com.mauricio.blogmanagementsystem.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + request.getEmail());
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        AppUser savedUser = appUserRepository.save(user);
        log.info("Usuario registrado exitosamente: {}", savedUser.getEmail());

        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getRole().name(), savedUser.getId());

        return authMapper.toResponse(savedUser, token, jwtTokenProvider.getExpiration() / 1000);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            log.warn("Intento de login fallido para email: {}", request.getEmail());
            throw new BadCredentialsException("Credenciales inválidas");
        }

        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name(), user.getId());

        log.info("Usuario autenticado exitosamente: {}", user.getEmail());

        return authMapper.toResponse(user, token, jwtTokenProvider.getExpiration() / 1000);
    }
}

