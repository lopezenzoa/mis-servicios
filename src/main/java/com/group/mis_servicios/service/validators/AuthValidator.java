package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.CredentialsRepository;
import com.group.mis_servicios.view.dto.RegisterDTO;

public class AuthValidator {


    public static boolean checkRegisterValidity(RegisterDTO dto, CredentialsRepository credsRepo) {

        return credsRepo.findAll()
                .stream()
                .noneMatch(c -> c.getUsername().equals(dto.getUsername()));
    }
}
