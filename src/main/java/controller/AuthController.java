package controller;


import dto.LoginDTO;
import dto.RegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private service.AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        authService.registrarUsuario(dto);
        return ResponseEntity.ok("Usuario registrado");
    }


}
