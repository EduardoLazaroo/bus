package com.eduardo.backend.controllers;

import com.eduardo.backend.dtos.UserDTO;
import com.eduardo.backend.dtos.LoginDTO;
import com.eduardo.backend.models.User;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.services.UserService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginRequest) {
        UserDTO userDTO = userService.login(loginRequest);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(
            @RequestAttribute("email") String email,
            @RequestBody UserDTO updateRequest
    ) {
        UserDTO updated = userService.updateUser(email, updateRequest);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@RequestAttribute("email") String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
