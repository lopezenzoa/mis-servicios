package com.group.mis_servicios.controller;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO dto) {
        System.out.println("Se llamó a crearCliente con: " + dto.getNombre());
        Cliente nuevo = clienteService.crearCliente(dto);
        return ResponseEntity.ok(nuevo);
    }

}
