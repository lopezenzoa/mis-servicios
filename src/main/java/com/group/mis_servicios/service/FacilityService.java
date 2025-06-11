package com.group.mis_servicios.service;


import com.group.mis_servicios.model.entity.Facility;
import com.group.mis_servicios.model.repository.ProviderRepository;
import com.group.mis_servicios.service.mappers.FacilityMapper;
import com.group.mis_servicios.service.validators.FacilityValidator;
import com.group.mis_servicios.view.dto.FacilityDTO;
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

        repository.findAll().forEach(facility -> facilities.add(FacilityMapper.toDTO(facility)));

        return facilities;
    }

    @Override
    public Optional<FacilityDTO> getById(Long id) {
        Optional<Facility> facility = repository.findById(id);

        return facility.map(FacilityMapper::toDTO);

    }

    @Override
    public Optional<FacilityDTO> create(FacilityDTO dto) {
        Facility saved = repository.save(FacilityMapper.toFacility(dto));

        return Optional.of(FacilityMapper.toDTO(saved));
    }

    @Override
    public Optional<FacilityDTO> update(Long id, FacilityDTO newFacility) {
        Optional<Facility> facility = repository.findById(id);

        if (facility.isPresent() && FacilityValidator.checkValidity(newFacility)) {
            Facility updated = repository.save(FacilityMapper.toFacility(newFacility));
            return Optional.of(FacilityMapper.toDTO(updated));
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
}
