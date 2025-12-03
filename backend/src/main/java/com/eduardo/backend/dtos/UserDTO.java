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
    private String token;
    private String password;

    private String phone;
    private String profileImage;
    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String numero;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        dto.setPhone(user.getPhone());
        dto.setProfileImage(user.getProfileImage());
        dto.setCep(user.getCep());
        dto.setLogradouro(user.getLogradouro());
        dto.setBairro(user.getBairro());
        dto.setComplemento(user.getComplemento());
        dto.setNumero(user.getNumero());

        dto.setPassword(null);
        return dto;
    }
}
