package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.CustomerDTO;

public class CustomerValidator {
    private static CustomerRepository customerRepo;

    public static boolean checkValidEmail(String email, CustomerRepository customerRepo) {
        return customerRepo.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static boolean checkValidPhone(String phone, CustomerRepository customerRepo) {
        return customerRepo.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static boolean checkValidity(CustomerDTO dto, CustomerRepository customerRepo) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber(), customerRepo)
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail(), customerRepo)
                && !dto.getUsername().isEmpty()
                && !dto.getPassword().isEmpty();
    }
}
