package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ShiftRepository;

import java.time.LocalDateTime;

public class ShiftValidator {
    private static ShiftRepository shiftRepository;
    private static ProviderRepository providerRepository;

    public static boolean existsShiftAtSameTime(Long providerId, LocalDateTime dateTime) {
        return shiftRepository.existsByProviderIdAndDateTime(providerId, dateTime);
    }

    // checks if the provider exists given his id
    public static boolean checkProvider(Long providerId) {
        return providerRepository.findAll()
                .stream()
                .anyMatch(p -> p.getId().equals(providerId));
    }
}
