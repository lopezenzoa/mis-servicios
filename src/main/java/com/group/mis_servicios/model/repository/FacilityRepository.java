package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    Optional<Facility> findByName(String name);
}
