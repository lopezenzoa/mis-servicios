package com.group.mis_servicios.controller;


import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import com.group.mis_servicios.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticacion")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a autenticar")
            @RequestBody LoginDTO dto
    ) {
        boolean success = service.login(dto);

        if (success) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "Logged in successfully!"));
        }

        return ResponseEntity.status(401)
                .header("Content-Type", "application/json")
                .body("Oops :( Invalid credentials");
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión del usuario", description = "Cierra la sesión del usuario, eliminando todas las cookies de sesión")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("Logged out successfully!");
    }
}
