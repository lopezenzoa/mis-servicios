package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.service.CustomerService;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        service.create(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The customer has been registered successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showProfile(@PathVariable Long id) {
        Optional<CustomerDTO> customer = service.getById(id);

        if (customer.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(customer.get());

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The customer hasn't been found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        Optional<CustomerResponseDTO> customerOptional = service.update(id, dto);

        if (customerOptional.isPresent())
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(Map.of("message", "The customer has been updated successfully!"));

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The customer hasn't been found"));
    }
}
