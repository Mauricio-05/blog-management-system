package com.mauricio.blogmanagementsystem.mapper;

import com.mauricio.blogmanagementsystem.dto.response.AuthResponse;
import com.mauricio.blogmanagementsystem.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(source = "token", target = "token")
    @Mapping(target = "tokenType", constant = "Bearer")
    @Mapping(source = "expiresIn", target = "expiresIn")
    AuthResponse toResponse(AppUser user, String token, Long expiresIn);
}

