package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Flagged;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlaggedProviderRepository extends JpaRepository<Flagged, Long> {
    boolean existsByCustomerIdAndProviderId(Long customerId, Long providerId);
    // Para contar cuántas veces fue marcado un proveedor
    long countByProviderId(Long providerId);

    // Para obtener el historial de marcas de un proveedor
    List<Flagged> findByProviderId(Long providerId);

    // Para obtener todas las marcas hechas por un cliente
    List<Flagged> findByCustomerId(Long customerId);

}
