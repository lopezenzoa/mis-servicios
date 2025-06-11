package com.group.mis_servicios.service.mappers;

import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.FacilityRepository;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.FacilityDTO;

import java.util.List;
import java.util.Optional;

public class FacilityMapper {
    private static FacilityRepository facilityRepo;
    private static ProviderRepository providerRepo;

    public static Facility toFacility(FacilityDTO dto) {
        Facility facility = new Facility();
        Optional<Facility> facility1 = facilityRepo.findByName(dto.getName());

        if (facility1.isPresent()) {
            // fetching all providers with the facility given
            List<Provider> providers = providerRepo.findAll()
                    .stream()
                    .filter(p -> p.getFacilities().contains(facility1.get()))
                    .toList();

            facility.setName(dto.getName());
            facility.setDescription(dto.getDescription());
            facility.setProviders(providers); // the filtered list of providers with the facility
        }

        return facility;
    }

    public static FacilityDTO toDTO(Facility facility) {
        FacilityDTO dto = new FacilityDTO();

        dto.setName(facility.getName());
        dto.setDescription(facility.getDescription());

        return dto;
    }
}
