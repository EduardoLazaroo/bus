package com.eduardo.backend.services;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.dtos.LoginDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO login(LoginDTO loginRequest);
    UserDTO updateUser(String email, UserDTO updateRequest);
}
