package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(ClienteDTO dto) {
        return clienteRepository.save(mapDtoToClient(dto));
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> dtos = new ArrayList<>();

        clienteRepository.findAll()
                .forEach(cliente -> dtos.add(mapClientToDto(cliente)));

        return dtos;
    }

    public ClienteDTO getById(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente client = clienteOptional.get();

            return mapClientToDto(client);
        }

        return new ClienteDTO();
    }

    public ClienteDTO update(Long id, ClienteDTO updated) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            Cliente cliente1 = clienteRepository.save(mapDtoToClient(updated));

            cliente1.setId(cliente.getId());

            return mapClientToDto(cliente1);
        }

        return new ClienteDTO();
    }

    private ClienteDTO mapClientToDto(Cliente client) {
        ClienteDTO dto = new ClienteDTO();

        dto.setNombre(client.getNombre());
        dto.setApellido(client.getApellido());
        dto.setEmail(client.getEmail());
        dto.setTelefono(client.getTelefono());

        return dto;
    }

    private Cliente mapDtoToClient(ClienteDTO dto) {
        Cliente client = new Cliente();

        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setTelefono(dto.getTelefono());

        return client;
    }
}
