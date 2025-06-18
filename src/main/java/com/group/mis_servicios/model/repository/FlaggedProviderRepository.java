package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Flagged;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlaggedProviderRepository extends JpaRepository<Flagged, Long> {
    boolean existsByCustomerIdAndProviderId(Long customerId, Long providerId);

    long countByProviderId(Long providerId);

    List<Flagged> findByProviderId(Long providerId);


    List<Flagged> findByCustomerId(Long customerId);

}
