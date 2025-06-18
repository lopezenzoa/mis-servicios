package com.group.mis_servicios.controller;

import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.AuthService;
import com.group.mis_servicios.service.CustomerService;
import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.group.mis_servicios.view.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "Obtiene todos los clientes")
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @PostMapping("/register")
    @ApiResponse(responseCode = "200", description = "Cliente creado")
    @ApiResponse(responseCode = "400", description = "El cliente no se pudo crear")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del cliente a crear")
            @RequestBody CustomerDTO dto
    ) {
        service.create(dto);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "The customer has been registered successfully!"));
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<CustomerResponseDTO> customerOptional = service.getById(id);

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
    @ApiResponse(responseCode = "200", description = "Cliente actualizado")
    @ApiResponse(responseCode = "400", description = "El cliente no se pudo actualizar")
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo del cliente a actualizar")
            @PathVariable Long id,
            @RequestBody CustomerDTO dto
    ) {
        Optional<CustomerResponseDTO> customerOptional = service.update(id, dto);

        if (customerOptional.isPresent())
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The customer has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerCustomerActual(Principal principal) {
        String username = principal.getName();
        System.out.println(" Username desde token: " + username);

        Optional<Customer> customer = repository.findByCredentials_Username(username);

        if (customer.isPresent()) {
            return ResponseEntity.ok(service.getById(customer.get().getId()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Customer not found"));
    }
}
