package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.ShiftDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public class ShiftMapper {
    private static ProviderRepository providerRepository;

    public static ShiftDTO toDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();

        dto.setProviderId(shift.getProvider().getId());
        dto.setDateTime(shift.getDateTime().toString());
        dto.setAvailable(shift.isAvailable());

        return dto;
    }

    public static Shift toShift(ShiftDTO dto) {
        Shift shift = new Shift();
        Optional<Provider> provider = providerRepository.findById(dto.getProviderId());

        if (provider.isPresent()) {
            shift.setDateTime(LocalDateTime.parse(dto.getDateTime()));
            shift.setAvailable(dto.isAvailable());
            // shift.setProviderId(dto.getProviderId());
            shift.setProvider(provider.get());
        }

        return shift;
    }
}
