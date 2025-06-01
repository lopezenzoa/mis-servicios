package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Optional<CustomerResponseDTO> create(CustomerDTO dto) {
        Customer customer = new Customer();
        CustomerResponseDTO response = new CustomerResponseDTO();
        Credentials credentials = new Credentials();

        if (
            dto.getFirstName().isEmpty()
            || dto.getLastName().isEmpty()
            || checkValidPhone(dto.getPhoneNumber())
            || dto.getAddress().isEmpty()
            || checkValidEmail(dto.getEmail())
            || dto.getUsername().isEmpty()
            || dto.getPassword().isEmpty()
        ) {
            return Optional.empty();
        } else {
            customer.setFirstName(dto.getFirstName());
            customer.setLastName(dto.getLastName());
            customer.setEmail(dto.getEmail());
            customer.setAddress(dto.getAddress());
            customer.setPhoneNumber(dto.getPhoneNumber());

            customer.setUsername(dto.getUsername());
            customer.setPassword(encoder.encode(dto.getPassword()));
            // credentials.setUser(customer);

            Customer saved = repository.save(customer);

            response.setId(saved.getId());
            response.setFirstName(saved.getFirstName());
            response.setLastName(saved.getLastName());
            response.setEmail(saved.getEmail());
            response.setAddress(saved.getAddress());
        }

        return Optional.of(response);
    }

    public List<CustomerDTO> getAll() {
        List<CustomerDTO> dtos = new ArrayList<>();

        repository.findAll()
                .forEach(c -> dtos.add(customerMapper(c)));

        return dtos;
    }

    public Optional<CustomerDTO> getById(Long id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return Optional.of(customerMapper(customer));
        }

        return Optional.empty();
    }

    public Optional<CustomerResponseDTO> update(Long id, CustomerDTO updated) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            if (
                updated.getFirstName().isEmpty()
                || updated.getLastName().isEmpty()
                || checkValidPhone(customer.getId(), updated.getPhoneNumber())
                || updated.getAddress().isEmpty()
                || checkValidEmail(customer.getId(), updated.getEmail())
                || updated.getUsername().isEmpty()
                || updated.getPassword().isEmpty()
            ) {
                return Optional.empty();
            } else {
                customer.setFirstName(updated.getFirstName());
                customer.setLastName(updated.getLastName());
                customer.setEmail(updated.getEmail());
                customer.setAddress(updated.getAddress());
                customer.setPhoneNumber(updated.getPhoneNumber());

                customer.setUsername(updated.getUsername());
                customer.setPassword(encoder.encode(updated.getPassword()));
                // credentials.setUser(customerUpdated);

                Customer saved = repository.save(customer);

                return Optional.of(customerResponseMapper(saved));
            }
        } else {
            return Optional.empty();
        }
    }

    private CustomerDTO customerMapper(Customer customer) {
        CustomerDTO dto = new CustomerDTO();

        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setPhoneNumber(customer.getPhoneNumber());

        return dto;
    }

    private Customer customerMapper(CustomerDTO dto) {
        Customer customer = new Customer();

        customer.setUsername(dto.getUsername());
        customer.setPassword(dto.getPassword());

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());

        return customer;
    }

    private CustomerResponseDTO customerResponseMapper(Customer customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();

        // response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setPhoneNumber(customer.getPhoneNumber());

        return response;
    }

    // These two validation methods are used for updating (that's why I'm requesting the id)
    public boolean checkValidEmail(Long id, String email) {
        return repository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public boolean checkValidPhone(Long id, String phone) {
        return repository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    // These two validation methods are used for creating
    public boolean checkValidEmail(String email) {
        return repository.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public boolean checkValidPhone(String phone) {
        return repository.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }
}
