package com.group.mis_servicios.service;

import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.service.mappers.AuthMapper;
import com.group.mis_servicios.service.mappers.CustomerMapper;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.service.validators.CustomerValidator;
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
    private AuthService authService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<CustomerResponseDTO> getAll() {
        List<CustomerResponseDTO> customers = new ArrayList<>();

        repository.findAll()
                .forEach(c -> customers.add(CustomerMapper.toResponseDTO(c)));

        return customers;
    }

    @Override
    public Optional<CustomerResponseDTO> getById(Long id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return Optional.of(CustomerMapper.toResponseDTO(customer));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CustomerResponseDTO> create(CustomerDTO dto) {
//        boolean isValid = CustomerValidator.checkValidity(dto, repository);
//
//        if (!isValid)
//            return Optional.empty();

        Customer saved = repository.save(CustomerMapper.toCustomer(dto, encoder));
        // authService.register(AuthMapper.toRegisterDTO(dto), Roles.CUSTOMER); // inserting the customer into the users table

        return Optional.of(CustomerMapper.toResponseDTO(saved));
    }

    @Override
    public Optional<CustomerResponseDTO> update(Long id, CustomerDTO updated) {
        Optional<Customer> customerOptional = repository.findById(id);


        if (customerOptional.isPresent() && CustomerValidator.checkValidity(updated, repository)) {
            Customer customerUpdated = repository.save(CustomerMapper.toCustomer(updated, encoder));

            return Optional.of(CustomerMapper.toResponseDTO(customerUpdated));
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

    public Optional<Customer> getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
