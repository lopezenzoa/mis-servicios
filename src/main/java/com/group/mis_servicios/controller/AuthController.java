package com.group.mis_servicios.controller;


import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import com.group.mis_servicios.model.entity.User;
import com.group.mis_servicios.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        service.register(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The user has been registered successfully!"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAuthUsers() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(service.getAuthUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        boolean success = service.login(dto);
        if (success) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("Logged in successfully!");
        }

        return ResponseEntity.status(401)
                .header("Content-Type", "application/json")
                .body("Oops :( Invalid credentials");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("Logged out successfully!");
    }
}
