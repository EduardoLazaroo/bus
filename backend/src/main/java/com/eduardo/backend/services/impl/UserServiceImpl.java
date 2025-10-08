package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // simples para MVP
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        user = userRepository.save(user);

        userDTO.setId(user.getId());
        userDTO.setPassword(null); // não retornar senha
        return userDTO;
    }
}
