package com.mauricio.blogmanagementsystem.security;

import com.mauricio.blogmanagementsystem.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long userId;
    private final UserRole role;

    public CustomUserDetails(String email, String password, Long userId, UserRole role,
                             Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
        this.role = role;
    }
}

