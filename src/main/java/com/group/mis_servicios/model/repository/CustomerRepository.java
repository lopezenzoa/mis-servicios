package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByCredentials_Username(String username);
    Optional<Customer> findByCredentials_Username(String username);


}
