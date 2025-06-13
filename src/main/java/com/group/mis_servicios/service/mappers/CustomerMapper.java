package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Credentials;
import com.group.mis_servicios.model.entity.Customer;
import com.group.mis_servicios.model.enums.Roles;
import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.view.dto.CustomerDTO;
import com.group.mis_servicios.view.dto.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class CustomerMapper {
    private static CredentialsRepository credentialsRepo;

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();

        dto.setUsername(customer.getCredentials().getUsername());
        dto.setPassword(customer.getCredentials().getPassword());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setPhoneNumber(customer.getPhoneNumber());

        return dto;
    }

    public static Customer toCustomer(CustomerDTO dto, BCryptPasswordEncoder encoder) {
        Customer customer = new Customer();
        Credentials credentials = new Credentials();

        credentials.setUsername(dto.getUsername());
        credentials.setPassword(encoder.encode(dto.getPassword()));
        credentials.setRole(Roles.CUSTOMER);

        // Credentials saved = credentialsRepo.save(credentials);

        customer.setCredentials(credentials);
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());

        return customer;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();

        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setReviews(customer.getReviews() == null ? new ArrayList<>() : ReviewMapper.toReviewDTOList(customer.getReviews()));
        response.setCalls(customer.getCalls() == null ? new ArrayList<>() : CallMapper.toCallDTOList(customer.getCalls()));

        return response;
    }
}
