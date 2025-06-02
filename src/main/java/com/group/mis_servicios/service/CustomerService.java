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
        boolean isValid = checkValidity(dto);

        if (!isValid)
            return Optional.empty();

        Customer customer = new Customer();
        CustomerResponseDTO response = new CustomerResponseDTO();
        Credentials credentials = new Credentials();

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        // credentials.setUser(customer);

        customer.setCredentials(credentials);

        Customer saved = repository.save(customer);

        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setEmail(saved.getEmail());
        response.setAddress(saved.getAddress());

        return Optional.of(response);
    }

    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customers = new ArrayList<>();

        repository.findAll()
                .forEach(c -> customers.add(customerMapper(c)));

        return customers;
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


        if (customerOptional.isPresent() && checkValidity(updated)) {
            Customer customerUpdated = repository.save(customerMapper(updated));

            customerUpdated.setFirstName(updated.getFirstName());
            customerUpdated.setLastName(updated.getLastName());
            customerUpdated.setEmail(updated.getEmail());
            customerUpdated.setAddress(updated.getAddress());
            customerUpdated.setPhoneNumber(updated.getPhoneNumber());

            Credentials credentials = new Credentials();

            credentials.setUsername(updated.getUsername());
            credentials.setPassword(encoder.encode(updated.getPassword()));
            // credentials.setUser(customerUpdated);

            customerUpdated.setCredentials(credentials);

            Customer saved = repository.save(customerUpdated);

            return Optional.of(customerResponseMapper(saved));
        }

        return Optional.empty();
    }

    private CustomerDTO customerMapper(Customer customer) {
        CustomerDTO dto = new CustomerDTO();

        dto.setUsername(customer.getCredentials().getUsername());
        dto.setPassword(customer.getCredentials().getPassword());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());

        return dto;
    }

    private Customer customerMapper(CustomerDTO dto) {
        Customer customer = new Customer();
        Credentials credentials = new Credentials();

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(dto.getPassword());

        customer.setCredentials(credentials);
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        return customer;
    }

    private CustomerResponseDTO customerResponseMapper(Customer customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();

        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setPhoneNumber(customer.getPhoneNumber());

        return response;
    }

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

    private boolean checkValidity(CustomerDTO dto) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber())
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail())
                && !dto.getUsername().isEmpty()
                && !dto.getPassword().isEmpty();
    }
}
