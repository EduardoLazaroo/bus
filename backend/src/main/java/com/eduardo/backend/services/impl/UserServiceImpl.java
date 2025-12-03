package com.eduardo.backend.services.impl;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.dtos.LoginDTO;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.security.JwtUtil;
import com.eduardo.backend.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        user = userRepository.save(user);

        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public UserDTO login(LoginDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        // Gera token JWT
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPassword(null); // não retornar a senha
        dto.setToken(token);   // retorna o token

        return dto;
    }

    @Override
    public UserDTO updateUser(String email, UserDTO updateRequest) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualizar somente os campos enviados
        if (updateRequest.getName() != null) user.setName(updateRequest.getName());
        if (updateRequest.getPhone() != null) user.setPhone(updateRequest.getPhone());
        if (updateRequest.getProfileImage() != null) user.setProfileImage(updateRequest.getProfileImage());
        if (updateRequest.getCep() != null) user.setCep(updateRequest.getCep());
        if (updateRequest.getLogradouro() != null) user.setLogradouro(updateRequest.getLogradouro());
        if (updateRequest.getBairro() != null) user.setBairro(updateRequest.getBairro());
        if (updateRequest.getComplemento() != null) user.setComplemento(updateRequest.getComplemento());
        if (updateRequest.getNumero() != null) user.setNumero(updateRequest.getNumero());

        userRepository.save(user);

        return UserDTO.fromEntity(user);
    }
}
