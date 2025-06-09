package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.repository.CredentialsRepository;
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
public class CustomerService implements I_Service<CustomerDTO> {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customers = new ArrayList<>();

        repository.findAll()
                .forEach(c -> customers.add(customerMapper(c)));

        return customers;
    }

    @Override
    public Optional<CustomerDTO> getById(Long id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return Optional.of(customerMapper(customer));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CustomerResponseDTO> create(CustomerDTO dto) {
        boolean isValid = checkValidity(dto);

        if (!isValid)
            return Optional.empty();

        Customer saved = repository.save(customerMapper(dto));

        return Optional.of(customerResponseMapper(saved));
    }

    @Override
    public Optional<CustomerResponseDTO> update(Long id, CustomerDTO updated) {
        Optional<Customer> customerOptional = repository.findById(id);


        if (customerOptional.isPresent() && checkValidity(updated)) {
            Customer customerUpdated = repository.save(customerMapper(updated));

            return Optional.of(customerResponseMapper(customerUpdated));
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    /* Los mappers deberian ser abstraidos */
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

        Credentials saved = credentialsRepository.save(credentials);

        customer.setCredentials(credentials);
        // customer.setCredentialsId(saved.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());

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
