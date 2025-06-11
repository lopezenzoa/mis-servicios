package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.ProviderDTO;

public class ProviderValidator {
    private static ProviderRepository providerRepository;

    // These two validation methods are used for updating (that's why I'm requesting the id)
    public static boolean checkValidEmail(Long id, String email) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static  boolean checkValidPhone(Long id, String phone) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static  boolean checkValidUsername(Long id, String username) {
        return providerRepository.findAll()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .anyMatch(c -> c.getCredentials().getUsername().equals(username));
    }

    // These two validation methods are used for creating
    public static  boolean checkValidEmail(String email) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    public static  boolean checkValidPhone(String phone) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getPhoneNumber().equals(phone));
    }

    public static  boolean checkValidUsername(String username) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(c -> c.getCredentials().getUsername().equals(username));
    }

    // this method is used when updating (notice I'm adding the providerId to the params)
    public static  boolean checkValidity(Long providerId, ProviderDTO dto) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && checkValidPhone(providerId, dto.getPhoneNumber())
                && !dto.getAddress().isEmpty()
                && checkValidEmail(providerId, dto.getEmail())
                && !dto.getUsername().isEmpty()
                && checkValidUsername(dto.getUsername())
                && !dto.getPassword().isEmpty();
    }

    // this method is used when creating
    public static  boolean checkValidity(ProviderDTO dto) {
        return !dto.getFirstName().isEmpty()
                && !dto.getLastName().isEmpty()
                && !checkValidPhone(dto.getPhoneNumber())
                && !dto.getAddress().isEmpty()
                && !checkValidEmail(dto.getEmail())
                && !dto.getUsername().isEmpty()
                && !checkValidUsername(dto.getUsername())
                && !dto.getPassword().isEmpty();
    }
}
