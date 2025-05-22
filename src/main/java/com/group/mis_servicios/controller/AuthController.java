package com.group.mis_servicios.controller;


import com.group.mis_servicios.dto.LoginDTO;
import com.group.mis_servicios.dto.RegistroDTO;
import com.group.mis_servicios.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        authService.registrarUsuario(dto);
        return ResponseEntity.ok("Usuario registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        boolean success = authService.login(dto);
        if (success) {
            return ResponseEntity.ok("Login exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
