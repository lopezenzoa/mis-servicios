package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Optional<Provider> findByLicenseNumber(String licenseNumber);
    List<Provider> findByCategory_Nombre(String nombre);

    @Query("SELECT p FROM Provider p WHERE " +
            "(:firstName IS NULL OR p.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR p.lastName LIKE %:lastName%) AND " +
            "(:email IS NULL OR p.email LIKE %:email%) AND " +
            "(:licenseNumber IS NULL OR p.licenseNumber LIKE %:licenseNumber%)")
    List<Provider> findByCriterios(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("licenseNumber") String licenseNumber
    );


}
