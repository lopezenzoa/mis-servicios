package com.group.mis_servicios.service;


import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.entity.Provider;
import com.group.mis_servicios.view.dto.FacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.FacilityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityService {
    @Autowired
    private FacilityRepository repository;

    public Optional<FacilityDTO> create(FacilityDTO dto) {
        Facility facility = repository.save(facilityMapper(dto));
        return Optional.of(facilityMapper(facility));
    }

    public List<FacilityDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(this::facilityMapper)
                .toList();
    }

    public Optional<FacilityDTO> getById(Long id) {
        Optional<Facility> facility = repository.findById(id);

        return facility.map(this::facilityMapper);
    }

    public boolean delete(Long id) {
        Optional<FacilityDTO> facility = getById(id);

        if (facility.isPresent()) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }

    public Optional<FacilityDTO> update(Long id, FacilityDTO newFacility) {
        return repository.findById(id)
                .map(f -> {
                    f.setName(newFacility.getName());
                    f.setDescription(newFacility.getDescription() == null ? "" : newFacility.getDescription());
                    return facilityMapper(repository.save(f));
                });
    }

    private Facility facilityMapper(FacilityDTO dto) {
        Facility facility = new Facility();

        facility.setName(dto.getName());
        facility.setDescription(dto.getDescription() == null ? "" : dto.getDescription());
        facility.setProviders(getFacilityProviders(dto.getName()));

        return facility;
    }

    private FacilityDTO facilityMapper(Facility facility) {
        FacilityDTO dto = new FacilityDTO();

        dto.setName(facility.getName());
        dto.setDescription(facility.getDescription() == null ? "" : facility.getDescription());

        return dto;
    }

    // This method could map to ProviderDTO (think in a utils class called Mapper to encapsulates all mapper logic)
    private List<Provider> getFacilityProviders(String name) {
        Optional<Facility> facility = repository.findByName(name);

        if (facility.isPresent())
            return facility.get().getProviders();

        return new ArrayList<>();
    }
}
