package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByUsername(String username);
    boolean existsByUsername(String username);

}
