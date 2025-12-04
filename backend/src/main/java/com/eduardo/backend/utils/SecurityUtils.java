package com.eduardo.backend.utils;

import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<String> getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return Optional.empty();
        Object principal = auth.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            return Optional.ofNullable(username);
        } else if (principal instanceof String) {
            return Optional.ofNullable((String) principal);
        } else {
            return Optional.empty();
        }
    }

    public static String getCurrentUserEmailOrThrow() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        return authentication.getName(); // email
    }

    public static User getCurrentUserOrThrow(UserRepository userRepository) {
        String email = getCurrentUserEmailOrThrow();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado no banco"));
    }
}
