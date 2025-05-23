package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO dto) {
        System.out.println("Se llamó a crearCliente con: " + dto.getNombre());
        Cliente nuevo = clienteService.crearCliente(dto);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> showProfile(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateProfile(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(clienteService.update(id, clienteDTO), HttpStatus.OK);
    }
}
