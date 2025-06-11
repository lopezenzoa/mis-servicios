package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.CallDTO;

import java.time.LocalDateTime;

public class CallValidator {
    private static ProviderRepository providerRepo;

    public static boolean checkCallValidity(CallDTO dto) {
        return !dto.getDate().isBefore(LocalDateTime.now())
                && !dto.getAddress().isBlank()
                && !dto.getState().toString().isBlank()
                && providerExists(dto.getProviderId());
    }

    private static boolean providerExists(Long providerId) {
        return providerRepo.existsById(providerId);
    }
}
