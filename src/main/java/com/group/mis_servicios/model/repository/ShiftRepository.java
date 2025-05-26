package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    List<Shift> findByProviderIdAndAvailableTrue(Integer providerId);
    boolean existsByProviderIdAndDateTime(Integer providerId, LocalDateTime dateTime);
}
