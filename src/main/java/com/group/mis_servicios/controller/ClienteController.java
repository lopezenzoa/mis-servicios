package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.dto.ClienteResponseDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> getAll() {
        return new ResponseEntity<>(clienteService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO dto) {
        Optional<ClienteResponseDTO> clienteResponseDTO = clienteService.crearCliente(dto);

        if (clienteResponseDTO.isPresent())
            return new ResponseEntity<>(clienteResponseDTO.get(), HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("There was an error during creating the client");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showProfile(@PathVariable Long id) {
        Optional<ClienteResponseDTO> clienteOptional = clienteService.getById(id);

        if (clienteOptional.isPresent())
            return new ResponseEntity<>(clienteOptional.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>("The client has no been found with the ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateProfile(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(clienteService.update(id, clienteDTO), HttpStatus.OK);
    }
}
