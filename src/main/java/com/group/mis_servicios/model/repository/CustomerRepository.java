package com.group.mis_servicios.model.repository;

import com.group.mis_servicios.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
