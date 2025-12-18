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
// Camada responsável pela regra de negócio do usuário
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    // Criação de usuário com validação básica e senha criptografada
    @Override
    public UserDTO createUser(UserDTO userDTO) {

        // Impede cadastro duplicado por email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Constrói entidade User a partir do DTO
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        user = userRepository.save(user);

        // Retorno seguro sem senha
        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        return userDTO;
    }

    // Autenticação do usuário e geração do JWT
    @Override
    public UserDTO login(LoginDTO loginRequest) {

        // Busca usuário pelo email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida senha criptografada
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        // Geração do token JWT com email e role
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // Monta DTO de resposta
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPassword(null);
        dto.setToken(token);

        return dto;
    }

    // Atualização de dados do usuário logado
    @Override
    public UserDTO updateUser(String email, UserDTO updateRequest) {

        // Garante que o usuário existe e é o dono da conta
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualizações parciais (somente campos enviados)
        if (updateRequest.getName() != null)
            user.setName(updateRequest.getName());
        if (updateRequest.getPhone() != null)
            user.setPhone(updateRequest.getPhone());
        if (updateRequest.getProfileImage() != null)
            user.setProfileImage(updateRequest.getProfileImage());
        if (updateRequest.getCep() != null)
            user.setCep(updateRequest.getCep());
        if (updateRequest.getLogradouro() != null)
            user.setLogradouro(updateRequest.getLogradouro());
        if (updateRequest.getBairro() != null)
            user.setBairro(updateRequest.getBairro());
        if (updateRequest.getComplemento() != null)
            user.setComplemento(updateRequest.getComplemento());
        if (updateRequest.getNumero() != null)
            user.setNumero(updateRequest.getNumero());

        // Atualização segura de senha (sempre criptografada)
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        // Garante que a role não seja alterada via API
        user.setRole(user.getRole());

        userRepository.save(user);

        return UserDTO.fromEntity(user);
    }
}
