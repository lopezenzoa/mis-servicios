package com.group.mis_servicios.controller;


import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.security.CustomUserDetailsService;
import com.group.mis_servicios.security.JwtUtil;
import com.group.mis_servicios.service.AuthService;
import com.group.mis_servicios.view.dto.LoginDTO;
import com.group.mis_servicios.view.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticacion")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private ProviderRepository providerRepo;
    @Autowired private CredentialsRepository credsRepo;
    @Autowired private BCryptPasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;

    public void register(RegisterDTO dto, Roles role) {

        if (credsRepo.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }


        if (role.equals(Roles.CUSTOMER) && customerRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado como cliente");
        }

        if (role.equals(Roles.PROVIDER) && providerRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado como prestador");
        }


        Credentials creds = new Credentials();
        creds.setUsername(dto.getUsername());
        creds.setPassword(encoder.encode(dto.getPassword()));
        creds.setRole(role);

        if (role.equals(Roles.CUSTOMER)) {

            Customer nuevo = new Customer();
            nuevo.setFirstName(dto.getFirstName());
            nuevo.setLastName(dto.getLastName());
            nuevo.setEmail(dto.getEmail());
            nuevo.setAddress(dto.getAddress());
            nuevo.setPhoneNumber(dto.getPhoneNumber());

            customerRepo.save(nuevo);


            nuevo.setCredentials(creds);
            creds.setCustomer(nuevo);
            credsRepo.save(creds);

        } else if (role.equals(Roles.PROVIDER)) {

            Provider nuevo = new Provider();
            nuevo.setFirstName(dto.getFirstName());
            nuevo.setLastName(dto.getLastName());
            nuevo.setEmail(dto.getEmail());
            nuevo.setAddress(dto.getAddress());
            nuevo.setPhoneNumber(dto.getPhoneNumber());
            nuevo.setLicenseNumber(dto.getLicenseNumber());
            nuevo.setFacility(dto.getFacility());

            providerRepo.save(nuevo);


            nuevo.setCredentials(creds);
            creds.setProvider(nuevo);
            credsRepo.save(creds);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPassword())
            );

            var userDetails = userDetailsService.loadUserByUsername(dto.getIdentifier());
            var token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());


            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("UNKNOWN");

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", role
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }
    }



    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión del usuario", description = "Cierra la sesión del usuario, eliminando todas las cookies de sesión")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("Logged out successfully!");
    }
}
