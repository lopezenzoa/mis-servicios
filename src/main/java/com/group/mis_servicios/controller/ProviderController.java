package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.ProviderDTO;
import com.group.mis_servicios.model.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.mis_servicios.service.ProviderService;
import com.group.mis_servicios.model.entity.Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/providers")
@CrossOrigin("*")
public class ProviderController {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ProviderService service;

    @GetMapping("/")
    public ResponseEntity<List<ProviderDTO>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<ProviderDTO> filterByLicenseNumber(@PathVariable String licenseNumber) {
        return new ResponseEntity<>(service.filterByLicenseNumber(licenseNumber), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProfile(@PathVariable Integer id, @RequestBody ProviderDTO updated) {
        return new ResponseEntity<>(service.update(id, updated), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ProviderDTO dto) {
        service.create(dto);
        return new ResponseEntity<>("The provider has been created successfully", HttpStatus.OK);
    }
}
