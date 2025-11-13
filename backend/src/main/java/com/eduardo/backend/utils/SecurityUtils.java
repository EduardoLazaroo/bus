package com.eduardo.backend.utils;

import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<String> getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return Optional.empty();
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

    public static User getCurrentUserOrThrow(UserRepository userRepository) {
        String email = getCurrentUserEmail().orElseThrow(() -> new RuntimeException("Usuário não autenticado"));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado no banco"));
    }
}
