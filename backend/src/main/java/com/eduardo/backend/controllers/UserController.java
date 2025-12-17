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
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginRequest) {
        UserDTO userDTO = userService.login(loginRequest);
        return ResponseEntity.ok(userDTO);
    }

    // UPDATE SEGURO (APENAS USUÁRIO LOGADO)
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO updateRequest
    ) {
        String email = SecurityUtils.getCurrentUserEmailOrThrow();
        UserDTO updated = userService.updateUser(email, updateRequest);
        return ResponseEntity.ok(updated);
    }

    // GET ME SEGURO
    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmailOrThrow();

        return userRepository.findByEmail(email)
                .map(UserDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
