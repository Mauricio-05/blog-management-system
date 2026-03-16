package com.mauricio.blogmanagementsystem.util;

import com.mauricio.blogmanagementsystem.enums.UserRole;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

@Slf4j
@UtilityClass
public class AuthorizationValidator {

    public static void validateOwnership(Long ownerId, Long currentUserId, String resourceName) {
        if (!ownerId.equals(currentUserId)) {
            log.warn("Usuario con id {} intentó editar {} sin ser el dueño", currentUserId, resourceName);
            throw new AccessDeniedException("Solo puedes editar tus propios " + resourceName);
        }
    }

    public static void validateOwnershipOrAdmin(Long ownerId, Long currentUserId, UserRole currentUserRole, String resourceName) {
        boolean isAdmin = currentUserRole == UserRole.ADMIN;
        boolean isOwner = ownerId.equals(currentUserId);

        if (!isAdmin && !isOwner) {
            log.warn("Usuario con id {} intentó eliminar {} sin permisos", currentUserId, resourceName);
            throw new AccessDeniedException("No tienes permisos para eliminar este " + resourceName);
        }
    }
}

