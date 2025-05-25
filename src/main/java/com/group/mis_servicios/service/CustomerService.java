package com.group.mis_servicios.service;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public CustomerDTO create(CustomerDTO dto) {
        return customerMapper(repository.save(customerMapper(dto)));
    }

    public List<CustomerDTO> getAll() {
        List<CustomerDTO> dtos = new ArrayList<>();

        repository.findAll()
                .forEach(c -> dtos.add(customerMapper(c)));

        return dtos;
    }

    public CustomerDTO getById(Integer id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return customerMapper(customer);
        }

        return new CustomerDTO();
    }

    public CustomerDTO update(Integer id, CustomerDTO updated) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customerUpdated = repository.save(customerMapper(updated));

            return customerMapper(customerUpdated);
        }

        return new CustomerDTO();
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
}
