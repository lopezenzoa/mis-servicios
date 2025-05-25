package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider> findByLicenseNumber(String licenseNumber);
}
