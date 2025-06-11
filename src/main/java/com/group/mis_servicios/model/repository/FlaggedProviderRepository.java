package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.FlaggedProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlaggedProviderRepository extends JpaRepository<FlaggedProvider, Long> {
    boolean existsByCustomerIdAndProviderId(Long customerId, Long providerId);
    // Para contar cuántas veces fue marcado un proveedor
    long countByProviderId(Long providerId);

    // Para obtener el historial de marcas de un proveedor
    List<FlaggedProvider> findByProviderId(Long providerId);

    // Para obtener todas las marcas hechas por un cliente
    List<FlaggedProvider> findByCustomerId(Long customerId);

}
