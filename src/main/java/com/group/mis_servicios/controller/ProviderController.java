package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.entity.Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;

    @GetMapping
    public ResponseEntity<List<Provider>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license")
    public ResponseEntity<Provider> filterByLicenseNumber(@RequestParam String licenseNumber) {
        return ResponseEntity.ok(service.filterByLicenseNumber(licenseNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProfile(@PathVariable Long id, @RequestBody ProviderDTO updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }
    @GetMapping("/{services}")
    public ResponseEntity<List<ProviderDTO>> filterByServices(@PathVariable String services){
        return ResponseEntity.ok(service.filterByServices(services));
    }

    @PostMapping("/register")
    public ResponseEntity<Provider> registerProvider(@RequestBody ProviderDTO dto) {
        Provider provider = new Provider();
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setEmail(dto.getEmail());
        provider.setAddress(dto.getAddress());
        provider.setLicenseNumber(dto.getLicenseNumber());

        Credentials credentials = new Credentials();
        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        credentials.setUser(provider);

        provider.setCredentials(credentials);

        Provider saved = service.save(provider);
        return ResponseEntity.ok(saved);
    }
}
