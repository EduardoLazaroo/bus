package com.eduardo.backend.dtos;

import com.eduardo.backend.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
