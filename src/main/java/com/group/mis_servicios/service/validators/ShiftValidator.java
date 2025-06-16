package com.group.mis_servicios.service.validators;

import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.model.repository.ShiftRepository;

import java.time.LocalDateTime;

public class ShiftValidator {
   // private static ShiftRepository shiftRepository;
    //private static ProviderRepository providerRepository;

    public static boolean existsShiftAtSameTime(Long providerId, LocalDateTime dateTime, ShiftRepository shiftRepository) {
        return shiftRepository.existsByProviderIdAndDateTime(providerId, dateTime);
    }

    public static boolean checkProvider(Long providerId, ProviderRepository providerRepository) {
        return providerRepository.existsById(providerId);
    }

}
