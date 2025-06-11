package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CustomerRepository;
import com.group.mis_servicios.view.dto.CustomerDTO;

public class CustomerValidator {
    private static CustomerRepository customerRepo;

    public static boolean checkValidEmail(String email) {
        return customerRepo.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static boolean checkValidPhone(String phone) {
        return customerRepo.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static boolean checkValidity(CustomerDTO dto) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber())
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail())
                && !dto.getUsername().isEmpty()
                && !dto.getPassword().isEmpty();
    }
}
