package com.group.mis_servicios.service;


import com.group.mis_servicios.model.entity.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.mis_servicios.model.repository.FacilityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    @Autowired
    private FacilityRepository repository;

    public Facility create(Facility facility) {
        return repository.save(facility);
    }

    public List<Facility> listAll() {
        return repository.findAll();
    }

    public Optional<Facility> getById(Integer id) {
        return repository.findById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Facility update(Integer id, Facility newFacility) {
        return repository.findById(id)
                .map(f -> {
                    f.setName(newFacility.getName());
                    return repository.save(f);
                })
                .orElseThrow(() -> new RuntimeException("Facility not found"));
    }
}
