package com.group.mis_servicios.service;

import com.group.mis_servicios.dto.ClienteDTO;
import com.group.mis_servicios.dto.ClienteResponseDTO;
import com.group.mis_servicios.dto.ProviderResponseDTO;
import com.group.mis_servicios.entity.Cliente;
import com.group.mis_servicios.entity.Credentials;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.entity.User;
import com.group.mis_servicios.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<ClienteResponseDTO> crearCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        ClienteResponseDTO response = new ClienteResponseDTO();
        Credentials credentials = new Credentials();

        if (
                dto.getNombre().isEmpty()
                || dto.getApellido().isEmpty()
                || checkValidPhone(dto.getTelefono())
                || dto.getDireccion().isEmpty()
                || checkValidEmail(dto.getEmail())
                || dto.getUsername().isEmpty()
                || dto.getPassword().isEmpty()
        ) {
            return Optional.empty();
        } else {
            cliente.setFirstName(dto.getNombre());
            cliente.setLastName(dto.getApellido());
            cliente.setEmail(dto.getEmail());
            cliente.setAddress(dto.getDireccion());
            cliente.setPhoneNumber(dto.getTelefono());

            credentials.setUsername(dto.getUsername());
            credentials.setPassword(encoder.encode(dto.getPassword()));
            credentials.setUser(cliente);

            cliente.setCredentials(credentials);

            Cliente saved = clienteRepository.save(cliente);

            response.setId(saved.getId());
            response.setFirstName(saved.getFirstName());
            response.setLastName(saved.getLastName());
            response.setEmail(saved.getEmail());
            response.setAddress(saved.getAddress());
        }

        return Optional.of(response);
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> dtos = new ArrayList<>();

        clienteRepository.findAll()
                .forEach(cliente -> dtos.add(mapClientToDto(cliente)));

        return dtos;
    }

    public Optional<ClienteResponseDTO> getById(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        ClienteResponseDTO response = new ClienteResponseDTO();

        if (clienteOptional.isPresent()) {
            Cliente client = clienteOptional.get();

            response.setId(client.getId());
            response.setFirstName(client.getFirstName());
            response.setLastName(client.getLastName());
            response.setEmail(client.getEmail());
            response.setAddress(client.getAddress());

            return Optional.of(response);
        }

        return Optional.empty();
    }

    public ClienteResponseDTO update(Long id, ClienteDTO updated) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        ClienteResponseDTO response = new ClienteResponseDTO();

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            Cliente cliente1 = clienteRepository.save(mapDtoToClient(updated));

            cliente1.setId(cliente.getId());

            response.setId(cliente1.getId());
            response.setFirstName(cliente1.getFirstName());
            response.setLastName(cliente1.getLastName());
            response.setEmail(cliente1.getEmail());
            response.setAddress(cliente1.getAddress());

            return response;
        }

        return response;
    }

    private ClienteDTO mapClientToDto(Cliente client) {
        ClienteDTO dto = new ClienteDTO();

        dto.setNombre(client.getFirstName());
        dto.setApellido(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setTelefono(client.getPhoneNumber());
        dto.setDireccion(client.getAddress());

        return dto;
    }

    private Cliente mapDtoToClient(ClienteDTO dto) {
        Cliente client = new Cliente();
        Credentials credentials = new Credentials();

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(dto.getPassword());

        client.setCredentials(credentials);
        client.setFirstName(dto.getNombre());
        client.setLastName(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getTelefono());
        client.setAddress(dto.getDireccion());

        return client;
    }

    public boolean checkValidEmail(String email) {
        return clienteRepository.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public boolean checkValidPhone(String phone) {
        return clienteRepository.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }
}
