package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Shift;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByProviderIdAndAvailableTrue(Long providerId);
    boolean existsByProviderIdAndDateTime(Long providerId, LocalDateTime dateTime);

    @EntityGraph(attributePaths = {"customer"})
    List<Shift> findByProviderId(Long providerId);
    List<Shift> findByCustomerId(Long customerId);

}
