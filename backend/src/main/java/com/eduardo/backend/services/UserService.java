package com.eduardo.backend.services;

import com.eduardo.backend.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}
