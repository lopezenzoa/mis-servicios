package com.group.mis_servicios.repository;

import com.group.mis_servicios.dto.ProviderDTO;
import com.group.mis_servicios.entity.Provider;
import com.group.mis_servicios.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByLicenseNumber(String licenseNumber);
}
