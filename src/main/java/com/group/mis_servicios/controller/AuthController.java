package com.group.mis_servicios.controller;


import com.group.mis_servicios.dto.LoginDTO;
import com.group.mis_servicios.dto.RegistroDTO;
import com.group.mis_servicios.entity.User;
import com.group.mis_servicios.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        authService.registrarUsuario(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Usuario registrado"));
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authService.obtenerUsuarios();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        boolean success = authService.login(dto);
        if (success) {
            return ResponseEntity.ok(Map.of("message", "Login exitoso"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

}
