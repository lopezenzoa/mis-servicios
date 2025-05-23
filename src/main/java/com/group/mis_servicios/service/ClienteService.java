package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return clienteRepository.save(cliente);
    }
}
