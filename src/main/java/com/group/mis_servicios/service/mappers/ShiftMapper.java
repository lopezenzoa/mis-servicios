package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.entity.Shift;
import com.group.mis_servicios.model.enums.States;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.ShiftDTO;

import java.time.LocalDateTime;

public class ShiftMapper {

    public static ShiftDTO toDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();

        dto.setId(shift.getId());
        dto.setProviderId(shift.getProvider().getId());
        dto.setDateTime(shift.getDateTime().toString());
        dto.setAvailable(shift.getAvailable());
        dto.setId(shift.getId());
        dto.setStatus(shift.getStatus());

        if (shift.getCustomer() != null) {
            dto.setCustomerName(shift.getCustomer().getFirstName() + " " + shift.getCustomer().getLastName());
            dto.setCustomerPhone(shift.getCustomer().getPhoneNumber());
        }
        if (shift.getProvider() != null) {
            dto.setProviderName(shift.getProvider().getFirstName() + " " + shift.getProvider().getLastName());
            dto.setService(shift.getProvider().getFacility());
        }
        return dto;
    }

    public static Shift toShift(ShiftDTO dto, ProviderRepository providerRepository) {
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider not found with ID: " + dto.getProviderId()));

        Shift shift = new Shift();
        shift.setDateTime(LocalDateTime.parse(dto.getDateTime()));
        shift.setAvailable(dto.getAvailable() != null ? dto.getAvailable() : true);
        shift.setProvider(provider);
        shift.setStatus(dto.getStatus() != null ? dto.getStatus() : States.PENDING);

        return shift;
    }


}
