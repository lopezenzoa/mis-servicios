package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.view.dto.RegisterDTO;

public class AuthValidator {
    // private static UserRepository userRepo;

    public static boolean checkRegisterValidity(RegisterDTO dto, CredentialsRepository credsRepo) {
        // checks if the username is unique
        return credsRepo.findAll()
                .stream()
                .noneMatch(c -> c.getUsername().equals(dto.getUsername()));
    }
}
