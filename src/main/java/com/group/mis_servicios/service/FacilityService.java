package com.group.mis_servicios.service;


import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.view.dto.FacilityDTO;
import com.group.mis_servicios.view.dto.ProviderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.FacilityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService implements I_Service<FacilityDTO> {
    @Autowired
    private FacilityRepository repository;
    @Autowired
    private ProviderRepository providerRepo;

    @Override
    public List<FacilityDTO> getAll() {
        List<FacilityDTO> facilities = new ArrayList<>();

        repository.findAll().forEach(facility -> facilities.add(facilityMapper(facility)));

        return facilities;
    }

    @Override
    public Optional<FacilityDTO> getById(Long id) {
        Optional<Facility> facility = repository.findById(id);

        return facility.map(this::facilityMapper);

    }

    @Override
    public Optional<FacilityDTO> create(FacilityDTO dto) {
        Facility saved = repository.save(facilityMapper(dto));

        return Optional.of(facilityMapper(saved));
    }

    @Override
    public Optional<FacilityDTO> update(Long id, FacilityDTO newFacility) {
        Optional<Facility> facility = repository.findById(id);

        if (facility.isPresent() && checkValidity(newFacility)) {
            Facility updated = repository.save(facilityMapper(newFacility));
            return Optional.of(facilityMapper(updated));
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

         return false;
    }

    /* Los mappers deberian ser abstraidos */
    private Facility facilityMapper(FacilityDTO dto) {
        Facility facility = new Facility();
        Optional<Facility> facility1 = repository.findByName(dto.getName());

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

    private FacilityDTO facilityMapper(Facility facility) {
        FacilityDTO dto = new FacilityDTO();

        dto.setName(facility.getName());
        dto.setDescription(facility.getDescription());

        return dto;
    }

    private boolean checkValidity(FacilityDTO dto) {
        return !dto.getName().isBlank();
    }
}
