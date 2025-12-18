package com.eduardo.backend.services;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.dtos.LoginDTO;

// Contrato da camada de serviço para usuário
public interface UserService {

    // Criação de novo usuário
    UserDTO createUser(UserDTO userDTO);

    // Autenticação e geração de token
    UserDTO login(LoginDTO loginRequest);

    // Atualização dos dados do usuário autenticado
    UserDTO updateUser(String email, UserDTO updateRequest);
}
