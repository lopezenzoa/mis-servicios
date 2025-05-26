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

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto) {
        service.register(dto);
        return new ResponseEntity<>("The user has been registered succesfully!", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAuthUsers() {
        return new ResponseEntity<>(service.getAuthUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        boolean success = service.login(dto);
        if (success) {
            return ResponseEntity.ok("Logged in Successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout Successfully");
    }
}
