package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.UserRole;
import com.eduardo.backend.models.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private String token; // opcional, só usado no login
    private String password; // usado apenas para criar/login

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPassword(null); // nunca retorna a senha
        return dto;
    }
}
