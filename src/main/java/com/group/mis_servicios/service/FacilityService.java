package com.group.mis_servicios.service;


import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.view.dto.FacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.FacilityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    @Autowired
    private FacilityRepository repository;

    public FacilityDTO create(FacilityDTO dto) {
        Facility saved = repository.save(facilityMapper(dto));

        return facilityMapper(saved);
    }

    public List<FacilityDTO> listAll() {
        List<FacilityDTO> facilites = new ArrayList<>();

        repository.findAll().forEach(facility -> facilites.add(facilityMapper(facility)));

        return facilites;
    }

    public Optional<FacilityDTO> getById(Long id) {
        Optional<Facility> facility = repository.findById(id);

        return facility.map(this::facilityMapper);

    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

         return false;
    }

    public Optional<FacilityDTO> update(Long id, FacilityDTO newFacility) {
        Optional<Facility> facility = repository.findById(id);

        if (facility.isPresent() && checkValidity(newFacility)) {
            Facility updated = repository.save(facilityMapper(newFacility));
            return Optional.of(facilityMapper(updated));
        }

        return Optional.empty();
    }

    private Facility facilityMapper(FacilityDTO dto) {
        Facility facility = new Facility();

        facility.setName(dto.getName());
        facility.setDescription(dto.getDescription());

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
