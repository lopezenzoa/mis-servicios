package com.group.mis_servicios.controller;

import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.service.CustomerService;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<String> create(@RequestBody CustomerDTO dto) {
        service.create(dto);
        return new ResponseEntity<>("The customer has been created successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> showProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody CustomerDTO dto) {
        Optional<CustomerResponseDTO> customerOptional = service.update(id, dto);

        if (customerOptional.isPresent())
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The customer has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
