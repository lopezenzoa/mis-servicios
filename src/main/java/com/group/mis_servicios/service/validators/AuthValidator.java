package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.UserRepository;
import com.group.mis_servicios.view.dto.RegisterDTO;

public class AuthValidator {
    // private static UserRepository userRepo;

    public static boolean checkRegisterValidity(RegisterDTO dto, UserRepository userRepo) {
        // checks if the username is unique
        boolean isUsernameUnique = userRepo.findAll()
                .stream()
                .anyMatch(user -> user.getCredentials().getUsername().equals(dto.getUsername()));

        // checks if the email is unique
        boolean isEmailUnique = userRepo.findAll()
                .stream()
                .anyMatch(user -> user.getCredentials().getUsername().equals(dto.getEmail()));

        return !isEmailUnique && !isUsernameUnique;
    }
}
