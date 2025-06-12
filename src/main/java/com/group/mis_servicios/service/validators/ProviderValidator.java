package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.ProviderDTO;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderValidator {
//    @Autowired
//    public static ProviderRepository providerRepository;

    // These two validation methods are used for updating (that's why I'm requesting the id)
    public static boolean checkValidEmail(Long id, String email, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static  boolean checkValidPhone(Long id, String phone, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static  boolean checkValidUsername(Long id, String username, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getCredentials().getUsername().equals(username));
    }

    // These two validation methods are used for creating
    public static  boolean checkValidEmail(String email, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static  boolean checkValidPhone(String phone, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static  boolean checkValidUsername(String username, ProviderRepository providerRepository) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getCredentials().getUsername().equals(username));
    }

    // this method is used when updating (notice I'm adding the providerId to the params)
    public static  boolean checkValidity(Long providerId, ProviderDTO dto, ProviderRepository providerRepository) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && checkValidPhone(providerId, dto.getPhoneNumber(), providerRepository)
                && !dto.getAddress().isEmpty()
                && checkValidEmail(providerId, dto.getEmail(), providerRepository)
                && !dto.getUsername().isEmpty()
                && checkValidUsername(dto.getUsername(), providerRepository)
                && !dto.getPassword().isEmpty();
    }

    // this method is used when creating
    public static  boolean checkValidity(ProviderDTO dto, ProviderRepository providerRepository) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber(), providerRepository)
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail(), providerRepository)
                && !dto.getUsername().isEmpty()
                && !checkValidUsername(dto.getUsername(), providerRepository)
                && !dto.getPassword().isEmpty();
    }
}
