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
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        service.create(dto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The customer has been registered successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<CustomerDTO> customerOptional = service.getById(id);

        if (customerOptional.isPresent()) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(customerOptional.get());
        }

        return ResponseEntity.status(404)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The call has no been found with the ID: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        Optional<CustomerResponseDTO> customerOptional = service.update(id, dto);

        if (customerOptional.isPresent())
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The customer has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
