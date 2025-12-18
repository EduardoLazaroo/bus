package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.dtos.LoginDTO;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.UserService;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
// Controller responsável apenas por expor endpoints HTTP de usuário
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Endpoint público para registro de usuário
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    // Endpoint público para autenticação (login)
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginRequest) {
        UserDTO userDTO = userService.login(loginRequest);
        return ResponseEntity.ok(userDTO);
    }

    // Atualização segura: apenas o próprio usuário autenticado
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updateRequest) {

        // Email vem do token JWT, não do request
        String email = SecurityUtils.getCurrentUserEmailOrThrow();

        UserDTO updated = userService.updateUser(email, updateRequest);
        return ResponseEntity.ok(updated);
    }

    // Retorna os dados do usuário logado
    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser() {

        // Identificação do usuário baseada no contexto de segurança
        String email = SecurityUtils.getCurrentUserEmailOrThrow();

        return userRepository.findByEmail(email)
                .map(UserDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
